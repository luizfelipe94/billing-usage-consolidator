package luizfelipe94.billingusageconsolidator;

import org.apache.kafka.streams.StreamsConfig;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.EnableKafkaStreams;

@Configuration
@EnableKafka
@EnableKafkaStreams
public class KafkaConfig {

  @Bean
  public StreamsConfig streamsConfig(KafkaProperties properties) {
    return new StreamsConfig(properties.buildStreamsProperties(null));
  }
  
}
