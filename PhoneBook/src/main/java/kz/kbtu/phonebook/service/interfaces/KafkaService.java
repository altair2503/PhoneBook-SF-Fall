package kz.kbtu.phonebook.service.interfaces;

public interface KafkaService {
    void listen(String message);
    Boolean sendMessage(String topic, String message);
}
