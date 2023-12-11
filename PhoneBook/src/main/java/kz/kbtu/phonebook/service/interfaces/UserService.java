package kz.kbtu.phonebook.service.interfaces;

import kz.kbtu.phonebook.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public interface UserService {

    Page<User> getAllUsers(Pageable pageable, HttpServletRequest request);

    Optional<User>  getAllUsersByNameOrPhone(String nameOrPhone, HttpServletRequest request);

    Boolean updateUser(Long id, User user, HttpServletRequest request);

}