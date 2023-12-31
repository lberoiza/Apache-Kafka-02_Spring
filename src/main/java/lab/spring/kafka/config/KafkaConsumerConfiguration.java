package lab.spring.kafka.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfiguration {


  @Value("${kafka.services.batch.concurrency}")
  private Integer batchConcurrency;

  @Value("${kafka.bootstrap_servers_config}")
  String bootstrapServersConfig;

  public Map<String, Object> consumerProperties() {
    Map<String, Object> props = new HashMap<>();
    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServersConfig);
    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
    props.put(ConsumerConfig.GROUP_ID_CONFIG, "test-group");
    props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
    props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
    props.put(ConsumerConfig.FETCH_MIN_BYTES_CONFIG, "1");
    props.put(ConsumerConfig.FETCH_MAX_WAIT_MS_CONFIG, "500");
    props.put(ConsumerConfig.MAX_PARTITION_FETCH_BYTES_CONFIG, "1048576");
    props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "10000");
    return props;
  }

  @Bean
  public ConsumerFactory<String, String> consumerFactory() {
    return new DefaultKafkaConsumerFactory<>(consumerProperties());
  }

  @Bean(name = "listenerContainerFactory")
  public ConcurrentKafkaListenerContainerFactory<String, String> listenerContainerFactory() {
    ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConsumerFactory(consumerFactory());
    return factory;
  }

  @Bean(name = "listenerBatchContainerFactory")
  public ConcurrentKafkaListenerContainerFactory<String, String> listenerBatchContainerFactory() {
    ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConsumerFactory(consumerFactory());
    factory.setBatchListener(true);
    factory.setConcurrency(batchConcurrency);
    return factory;
  }


}
