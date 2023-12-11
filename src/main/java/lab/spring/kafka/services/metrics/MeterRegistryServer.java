package lab.spring.kafka.services.metrics;

import io.micrometer.core.instrument.MeterRegistry;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class MeterRegistryServer {

  private static final Logger log = org.slf4j.LoggerFactory.getLogger(MeterRegistryServer.class);

  @Value("${kafka.service.meterregistry.enabled}")
  private boolean scheduleMessagesEnabled;



  private final MeterRegistry meterRegistry;

  @Autowired
  public MeterRegistryServer(MeterRegistry meterRegistry) {
    this.meterRegistry = meterRegistry;
  }

  @Scheduled(
      fixedDelayString = "${kafka.service.meterregistry.fixedDelay}",
      initialDelayString = "${kafka.service.meterregistry.initialDelay}"
  )
  public void printMetrics() {
    if(scheduleMessagesEnabled){
      double totalMessages = meterRegistry.get("kafka.producer.record.send.total").functionCounter().count();
      log.info("Total Messages sended: {}", totalMessages);
    }
  }
}
