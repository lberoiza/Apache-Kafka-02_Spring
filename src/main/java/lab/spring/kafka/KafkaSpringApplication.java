package lab.spring.kafka;

import lab.spring.kafka.services.producers.SimpleProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class KafkaSpringApplication {

  @Autowired
  SimpleProducerService simpleProducerService;

  @Value("${kafka.services.simple.topic}")
  private String simpleTopic;

  public static void main(String[] args) {
    SpringApplication.run(KafkaSpringApplication.class, args);
  }

}
