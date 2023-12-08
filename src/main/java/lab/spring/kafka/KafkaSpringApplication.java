package lab.spring.kafka;

import lab.spring.kafka.services.producers.SimpleProducerService;
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
    simpleProducerService.sendMessage("test-topic", "Application started");
  }
}
