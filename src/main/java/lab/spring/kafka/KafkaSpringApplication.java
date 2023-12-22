package lab.spring.kafka;

import lab.spring.kafka.models.PersonData;
import lab.spring.kafka.services.elasticsearch.ElasticSearchPersonDataService;
import lab.spring.kafka.services.producers.SimpleProducerService;
import lab.spring.kafka.services.producers.callbacks.SimpleProducerCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


@SpringBootApplication
@EnableScheduling
public class KafkaSpringApplication implements CommandLineRunner {


  @Autowired
  ElasticSearchPersonDataService elasticSearchPersonDataService;

  @Autowired
  SimpleProducerService simpleProducerService;

  @Value("${kafka.services.simple.topic}")
  private String simpleTopic;

  public static void main(String[] args) {
    SpringApplication.run(KafkaSpringApplication.class, args);
  }

  @Override
  public void run(String... args) throws ExecutionException, InterruptedException, TimeoutException {
    // Envia mensaje de manera sincrona
    // throws ExecutionException, InterruptedException, TimeoutException
     simpleProducerService.sendMessage(simpleTopic, "Application started simple").get(100, TimeUnit.MILLISECONDS);

    // Envia mensaje con callback
    SimpleProducerCallback producerCallback = new SimpleProducerCallback();
    simpleProducerService.sendMessageWithCallback(simpleTopic, "Application started callback", producerCallback);


    PersonData personData = new PersonData();
    personData.setFirstName("Juan");
    personData.setLastName("Perez");
    personData.setEmail("test@test.com");
    personData.setPhoneNumber("1234567890");
    personData.setId("2");
    elasticSearchPersonDataService.save(personData);

  }
}
