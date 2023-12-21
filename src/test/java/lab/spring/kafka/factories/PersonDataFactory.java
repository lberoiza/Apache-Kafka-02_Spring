package lab.spring.kafka.factories;

import com.github.javafaker.Faker;
import lab.spring.kafka.testmodels.PersonData;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Value;

public class PersonDataFactory {

  private static final Faker dataFaker = new Faker();

  public static PersonData createPersonData() {
    PersonData personData = new PersonData();
    personData.setFirstName(dataFaker.name().firstName());
    personData.setLastName(dataFaker.name().lastName());
    personData.setAddress(dataFaker.address().fullAddress());
    personData.setPhoneNumber(dataFaker.phoneNumber().phoneNumber());
    personData.setEmail(dataFaker.internet().emailAddress());
    return personData;
  }
}
