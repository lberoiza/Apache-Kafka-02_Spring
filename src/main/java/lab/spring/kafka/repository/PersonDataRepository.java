package lab.spring.kafka.repository;

import lab.spring.kafka.models.PersonData;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface PersonDataRepository extends ElasticsearchRepository<PersonData, String> {
}
