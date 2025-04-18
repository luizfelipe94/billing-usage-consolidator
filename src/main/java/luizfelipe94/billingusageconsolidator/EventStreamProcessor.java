package luizfelipe94.billingusageconsolidator;

import java.time.Duration;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.GlobalKTable;
import org.apache.kafka.streams.kstream.Grouped;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.kstream.TimeWindows;
import org.apache.kafka.streams.kstream.Windowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.stereotype.Component;
import luizfelipe94.billingusageconsolidator.models.*;
import luizfelipe94.billingusageconsolidator.utils.AppSerdes;

@Component
public class EventStreamProcessor {

  final String TOPIC_USAGE_PRICING = "billing-usage-pricing";
  final String TOPIC_USAGE_UNIT_RAW = "billing-usage-unit-raw";
  final String TOPIC_USAGE_UNIT_ENRICHED = "billing-usage-unit-enriched";
  final String TOPIC_USAGE_AGGREGATED = "billing-usage-aggregation";

  @Autowired
  public void process(StreamsBuilder streamsBuilder) {

    GlobalKTable<String, PricingTable> pricingTable = streamsBuilder.globalTable(
        TOPIC_USAGE_PRICING,
        Consumed.with(AppSerdes.String(), new JsonSerde<>(PricingTable.class)));

    KStream<String, UsageUnitRaw> usageUnitsRaw = streamsBuilder.stream(
        TOPIC_USAGE_UNIT_RAW,
        Consumed.with(AppSerdes.String(), new JsonSerde<>(UsageUnitRaw.class)));
    // .withTimestampExtractor(new EventTimestampExtractor()));

    KStream<String, UsageUnitEnriched> enriched = usageUnitsRaw
        .leftJoin(pricingTable,
            (unitKey, unitValue) -> unitKey,
            (unitValue, priceRow) -> {
              Float price = 0f;
              String message = "";
              if (priceRow != null) {
                price = priceRow.getPrice();
              } else {
                message = "invalid measure";
              }
              return UsageUnitEnriched.builder()
                  .timestamp(unitValue.getTimestamp())
                  .measure(unitValue.getMeasure())
                  .size(unitValue.getSize())
                  .usage(unitValue.getUsage())
                  .orgId(unitValue.getOrgId())
                  .orgName(unitValue.getOrgName())
                  .tenantId(unitValue.getTenantId())
                  .tenantName(unitValue.getTenantName())
                  .productId(unitValue.getProductId())
                  .productName(unitValue.getProductName())
                  .cost(price)
                  .message(message)
                  .build();
            });

    enriched.to(
        TOPIC_USAGE_UNIT_ENRICHED,
        Produced.with(AppSerdes.String(), new JsonSerde<>(UsageUnitEnriched.class)));

    KTable<Windowed<String>, UsageAggregator> result = enriched
        .groupBy(
            (k, v) -> v.getOrgId() + v.getTenantId() + v.getProductId() + v.getMeasure() + v.getSize(),
            Grouped.with(AppSerdes.String(), new JsonSerde<>(UsageUnitEnriched.class)))
        .windowedBy(TimeWindows.of(Duration.ofMinutes(1)))
        .aggregate(
            // initializer
            () -> UsageAggregator.builder()
                .totalUsage(0)
                .totalCost(0)
                .build(),
            // aggregator
            (key, value, aggregate) -> UsageAggregator.builder()
                .totalCost(aggregate.getTotalCost() + (value.getUsage() * value.getCost()))
                .totalUsage(aggregate.getTotalUsage() + value.getUsage())
                .orgId(value.getOrgId())
                .orgName(value.getOrgName())
                .tenantId(value.getTenantId())
                .tenantName(value.getTenantName())
                .productId(value.getProductId())
                .productName(value.getProductName())
                .measure(value.getMeasure())
                .size(value.getSize())
                .timestamp(value.getTimestamp())
                .build(),
            // serializer
            Materialized.with(AppSerdes.String(), new JsonSerde<>(UsageAggregator.class)));
            
        result.toStream().to(TOPIC_USAGE_AGGREGATED);
  }

}