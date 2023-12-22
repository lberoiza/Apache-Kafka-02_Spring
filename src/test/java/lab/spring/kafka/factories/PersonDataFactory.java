package lab.spring.kafka.factories;

import com.github.javafaker.Faker;
import lab.spring.kafka.models.PersonData;

public class PersonDataFactory {

  private static final Faker dataFaker = new Faker();

  public static PersonData createPersonData() {
    String firstname = dataFaker.name().firstName();
    String lastname = dataFaker.name().lastName();
    String email = generateEmailFromFirstAndLastName(firstname, lastname);

    PersonData personData = new PersonData();
    personData.setFirstName(firstname);
    personData.setLastName(lastname);
    personData.setAddress(dataFaker.address().fullAddress());
    personData.setPhoneNumber(dataFaker.phoneNumber().phoneNumber());
    personData.setEmail(email);
    return personData;
  }


  private static String generateEmailFromFirstAndLastName(String firstname, String lastname) {
    return String.format("%s.%s@%s",
        firstname.toLowerCase(),
        lastname.toLowerCase(),
        dataFaker.internet().domainName().toLowerCase()
    );
  }
}
