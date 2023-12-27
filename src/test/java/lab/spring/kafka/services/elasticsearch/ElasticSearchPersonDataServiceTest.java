package lab.spring.kafka.services.elasticsearch;

import lab.spring.kafka.factories.PersonDataFactory;
import lab.spring.kafka.models.PersonData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ElasticSearchPersonDataServiceTest {

  @Autowired
  private ElasticSearchPersonDataService elasticSearchPersonDataService;

  private PersonData personData;

  @BeforeEach
  void setUp() {
    String testId = "testperson";
    personData = PersonDataFactory.createPersonData();
    personData.setId(testId);
  }

  @AfterEach
  void tearDown() {
    elasticSearchPersonDataService.delete(personData);
  }


  @Test
  public void findAndNotFound() {
    assertPersonDoesNotExist(personData);
  }


  @Test
  public void save() {
    savePersonData(personData);
    PersonData foundedPersonData = findPerson(personData);
    assertEquals(personData, foundedPersonData);
  }

  @Test
  public void delete() {
    savePersonData(personData);
    PersonData foundedPersonData = findPerson(personData);
    assertEquals(personData, foundedPersonData);
    elasticSearchPersonDataService.delete(personData);
    assertPersonDoesNotExist(personData);
  }


  private void savePersonData(PersonData personData) {
    elasticSearchPersonDataService.save(personData);
  }

  private PersonData findPerson(PersonData personData) {
    assertNotNull(personData);
    assertNotNull(personData.getId());
    Optional<PersonData> personDataOptional = elasticSearchPersonDataService.findById(personData.getId());
    assertTrue(personDataOptional.isPresent());
    return personDataOptional.get();
  }

  private void assertPersonDoesNotExist(PersonData personData) {
    Optional<PersonData> personDataOptional = elasticSearchPersonDataService.findById(personData.getId());
    assertTrue(personDataOptional.isEmpty());
  }


}