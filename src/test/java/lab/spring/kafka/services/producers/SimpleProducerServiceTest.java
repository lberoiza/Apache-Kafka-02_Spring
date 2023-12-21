package lab.spring.kafka.services.producers;

import com.github.javafaker.Faker;
import lab.spring.kafka.testmodels.PersonData;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SimpleProducerServiceTest {

  @Autowired
  SimpleProducerService simpleProducerService;

  Faker dataFaker;

  @Value("${test.simpleproducerservice.topic}")
  private String simpleTestTopic;


  @BeforeEach
  public void setUp() {
    dataFaker = new Faker();
  }


  @Test
  void sendMessage() throws ExecutionException, InterruptedException {

    PersonData personData = new PersonData();
    personData.setFirstName(dataFaker.name().firstName());
    personData.setLastName(dataFaker.name().lastName());
    personData.setAddress(dataFaker.address().fullAddress());
    personData.setPhoneNumber(dataFaker.phoneNumber().phoneNumber());
    personData.setEmail(dataFaker.internet().emailAddress());

    ProducerRecord<String, String> producerRecord = simpleProducerService
        .sendMessage(simpleTestTopic, personData.getFirstName(), personData.toJson())
        .get()
        .getProducerRecord();


    assertEquals(simpleTestTopic, producerRecord.topic());
    assertEquals(personData.toJson(), producerRecord.value());
    assertEquals(personData.getFirstName(), producerRecord.key());

  }


  @Test
  void sendMessageCallback() {

    PersonData personData = new PersonData();
    personData.setFirstName(dataFaker.name().firstName());
    personData.setLastName(dataFaker.name().lastName());
    personData.setAddress(dataFaker.address().fullAddress());
    personData.setPhoneNumber(dataFaker.phoneNumber().phoneNumber());
    personData.setEmail(dataFaker.internet().emailAddress());

    simpleProducerService
        .sendMessageWithCallback(
            simpleTestTopic,
            personData.getFirstName(),
            personData.toJson(),
            (result, error) -> {
              assertNull(error);
              assertNotNull(result);
              assertEquals(simpleTestTopic, result.getRecordMetadata().topic());
              assertEquals(personData.getFirstName(), result.getProducerRecord().key());
              assertEquals(personData.toJson(), result.getProducerRecord().value());
            });
  }


}