package kz.kbtu.phonebook.dtos;

import kz.kbtu.phonebook.models.Users;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URI;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserCreatedResponce {
    Users user;
    URI userURI;

}
