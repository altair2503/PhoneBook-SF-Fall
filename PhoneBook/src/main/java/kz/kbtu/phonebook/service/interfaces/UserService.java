package kz.kbtu.phonebook.service.interfaces;

import kz.kbtu.phonebook.models.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

public interface UserService {
    Page<Users> getAllUsers(Pageable pageable, HttpServletRequest request);
    Optional<Users>  getAllUsersByNameOrPhone(String nameOrPhone, HttpServletRequest request);
    Boolean updateUser(Long id, Users user, HttpServletRequest request);
}
