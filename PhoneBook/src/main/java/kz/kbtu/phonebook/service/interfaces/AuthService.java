package kz.kbtu.phonebook.service.interfaces;

import kz.kbtu.phonebook.dtos.AuthRequest;
import kz.kbtu.phonebook.dtos.AuthResponse;
import kz.kbtu.phonebook.dtos.UserCreatedResponce;
import kz.kbtu.phonebook.models.Users;

public interface AuthService {
    AuthResponse login(AuthRequest authRequest);
    UserCreatedResponce register(Users users);
}
