package kz.kbtu.phonebook.service.interfaces;

import kz.kbtu.phonebook.dtos.UserDto;
import kz.kbtu.phonebook.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public interface UserService {

    Page<?> getAllUsers(Pageable pageable, HttpServletRequest request);

    Optional<?> getAllUsersByNameOrPhone(String nameOrPhone, HttpServletRequest request);

    Boolean updateUser(Long id, User user, HttpServletRequest request);

}