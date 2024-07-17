package ai.semantix.billingusageconsolidator.utils;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.streams.processor.TimestampExtractor;
import ai.semantix.billingusageconsolidator.models.UsageUnitEnriched;

public class EventTimestampExtractor implements TimestampExtractor {

  @Override
  public long extract(ConsumerRecord<Object, Object> record, long partitionTime) {
    UsageUnitEnriched event = (UsageUnitEnriched) record.value();
    return event.getTimestamp();
  }

  
}
