package kz.kbtu.phonebook.service.impls;

import kz.kbtu.phonebook.service.interfaces.KafkaService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaServiceImpl implements KafkaService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Override
    @KafkaListener(topics = "my-topic", groupId = "my-group-id")
    public void listen(String message) {
        System.out.println("Received message: " + message);
    }

    @Override
    public Boolean sendMessage(String topic, String message) {
        return kafkaTemplate.send(topic, message).isDone();
    }
}
