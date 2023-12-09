package kz.kbtu.phonebook.service.interfaces;

public interface KafkaService {
    void sendMessage(String topic, String message);
}
