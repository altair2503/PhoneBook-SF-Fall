package kz.kbtu.phonebook.service.interfaces;

import kz.kbtu.phonebook.dtos.AuthRequest;
import kz.kbtu.phonebook.dtos.AuthResponse;
import kz.kbtu.phonebook.dtos.UserCreatedResponse;
import kz.kbtu.phonebook.models.User;

public interface AuthService {

    AuthResponse login(AuthRequest authRequest);

    UserCreatedResponse register(User user);

}
