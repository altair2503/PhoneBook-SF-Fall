package kz.kbtu.phonebook.service.impls;

import kz.kbtu.phonebook.service.interfaces.KafkaService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaServiceImpl implements KafkaService {
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public void sendMessage(String topic, String message) {
        kafkaTemplate.send(topic, message);
    }
}
