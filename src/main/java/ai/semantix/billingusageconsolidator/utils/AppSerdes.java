package ai.semantix.billingusageconsolidator.utils;

import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import ai.semantix.billingusageconsolidator.models.UsageAggregator;

public class AppSerdes extends Serdes {

    static final class AggregatorSerde extends WrapperSerde<UsageAggregator> {
        public AggregatorSerde() {
            super(new JsonSerializer<>(), new JsonDeserializer<>());
        }
    }

    public static Serde<UsageAggregator> UsageAggregator() {
        AggregatorSerde serde = new AggregatorSerde();
        Map<String, Object> serdeConfigs = new HashMap<>();
        serdeConfigs.put("value.class.name", UsageAggregator.class);
        serde.configure(serdeConfigs, false);
        return serde;
    }

}
