package lab.spring.kafka.models;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.Optional;

@Getter
@Setter
@EqualsAndHashCode
@Slf4j
@Document(indexName = "persondata", createIndex = true)
public class PersonData {

  @Id
  private String id;
  private String firstName;
  private String lastName;
  private String email;
  private String phoneNumber;
  private String address;

  public PersonData() {
  }


  public static Optional<PersonData> fromJson(String json) {
    try {
      ObjectMapper objectMapper = new ObjectMapper();
      return Optional.of(objectMapper.readValue(json, PersonData.class));
    } catch (JsonProcessingException jsonProcessingException) {
      log.error("Error parsing from Json: {}", jsonProcessingException.getMessage());
      return Optional.empty();
    }
  }

  public String toJson() {
    try {
      ObjectMapper objectMapper = new ObjectMapper();
      return objectMapper.writeValueAsString(this);
    } catch (JsonProcessingException e) {
      log.error("Error parsing to Json: {}", e.getMessage());
      return "";
    }
  }

}
