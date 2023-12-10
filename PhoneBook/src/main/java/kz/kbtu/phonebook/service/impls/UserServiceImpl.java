package kz.kbtu.phonebook.service.impls;

import kz.kbtu.phonebook.exceptions.CustomBadCredentialsException;
import kz.kbtu.phonebook.exceptions.UserNotFoundException;
import kz.kbtu.phonebook.jwt.JwtTokenFilter;
import kz.kbtu.phonebook.models.Users;
import kz.kbtu.phonebook.repo.UsersRepo;
import kz.kbtu.phonebook.service.interfaces.CasbinService;
import kz.kbtu.phonebook.service.interfaces.KafkaService;
import kz.kbtu.phonebook.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UsersRepo usersRepo;
    private final JwtTokenFilter jwtTokenFilter;
    private final CasbinService casbinService;
    private final KafkaService kafkaService;


    @Override
    public Page<Users> getAllUsers(Pageable pageable, HttpServletRequest request) {
        HashMap<String, String> authority = jwtTokenFilter.getUserDetailsByHttpRequest(request);
        if(casbinService.checkAuthorize(authority.get("rule"), "users", "read")){
            return usersRepo.findAll(pageable);
        }
        else{
            throw new UserNotFoundException("Users not Found");
        }
    }

    @Override
    public Optional<Users> getAllUsersByNameOrPhone(String nameOrPhone, HttpServletRequest request) {
        HashMap<String, String> authority = jwtTokenFilter.getUserDetailsByHttpRequest(request);
        if(casbinService.checkAuthorize(authority.get("rule"), "search", "read")){
            if(usersRepo.findUsersByUsernameOrPhone(nameOrPhone, nameOrPhone).isPresent()) {
                return usersRepo.findUsersByUsernameOrPhone(nameOrPhone, nameOrPhone);
            }
            else{
                throw new UserNotFoundException("Users not Found");
            }
        }
        else{
            throw new CustomBadCredentialsException("UNAUTHORIZED user");
        }
    }

    @Override
    public Boolean updateUser(Long id, Users user, HttpServletRequest request) {
        HashMap <String, String> authority = jwtTokenFilter.getUserDetailsByHttpRequest(request);
        if(casbinService.checkAuthorize(authority.get("rule"), "user", "update")) {
            Optional<Users> foundUser = usersRepo.findById(id);
            if(foundUser.isPresent()){
                Users updatedUser = foundUser.get();
                if(user.getUsername() != null) updatedUser.setUsername(user.getUsername());
                if(user.getPassword() != null) updatedUser.setPassword(user.getPassword());
                if(user.getEmail() != null) updatedUser.setEmail(user.getEmail());
                if(user.getPhone() != null) updatedUser.setPhone(user.getPhone());
                kafkaService.sendMessage("my-topic", updatedUser.toString());
                return true;
            }
            else{
                throw new UserNotFoundException("User not found");
            }
        }
        else{
            throw new CustomBadCredentialsException("UNAUTHORIZED user");
        }
    }
}
