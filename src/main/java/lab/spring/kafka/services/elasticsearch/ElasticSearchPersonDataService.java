package lab.spring.kafka.services.elasticsearch;

import lab.spring.kafka.models.PersonData;
import lab.spring.kafka.repository.PersonDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ElasticSearchPersonDataService {


  private final PersonDataRepository personaDataRepository;

  @Autowired
  public ElasticSearchPersonDataService(PersonDataRepository personaDataRepository) {
    this.personaDataRepository = personaDataRepository;
  }

  public void save(PersonData personData) {
    personaDataRepository.save(personData);
  }

}
