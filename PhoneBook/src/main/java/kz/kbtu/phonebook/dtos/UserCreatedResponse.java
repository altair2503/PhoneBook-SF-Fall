package kz.kbtu.phonebook.dtos;

import kz.kbtu.phonebook.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URI;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCreatedResponse {
    User user;
    URI userURI;
}