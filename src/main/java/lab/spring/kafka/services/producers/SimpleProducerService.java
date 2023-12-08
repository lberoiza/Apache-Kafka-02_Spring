package lab.spring.kafka.services.producers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class SimpleProducerService {

  private KafkaTemplate<String, String> kafkaProducer;

  @Autowired
  public SimpleProducerService(KafkaTemplate<String, String> kafkaProducer) {
    this.kafkaProducer = kafkaProducer;
  }


  public void sendMessage(String topic, String message) {
    kafkaProducer.send(topic, message);
  }

}
