package lab.spring.kafka;

import lab.spring.kafka.services.producers.SimpleProducerService;
import lab.spring.kafka.services.producers.callbacks.SimpleProducerCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KafkaSpringApplication implements CommandLineRunner {

  @Autowired
  SimpleProducerService simpleProducerService;

  public static void main(String[] args) {
    SpringApplication.run(KafkaSpringApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    SimpleProducerCallback producerCallback = new SimpleProducerCallback();
    simpleProducerService.sendMessageWithCallback("test-topic", "Application started", producerCallback);
  }
}
