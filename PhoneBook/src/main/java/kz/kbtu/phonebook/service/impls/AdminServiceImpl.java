package kz.kbtu.phonebook.service.impls;

import kz.kbtu.phonebook.exceptions.PermissionDeniedException;
import kz.kbtu.phonebook.exceptions.UserNotFoundException;
import kz.kbtu.phonebook.jwt.JwtTokenFilter;
import kz.kbtu.phonebook.models.Users;
import kz.kbtu.phonebook.repo.UsersRepo;
import kz.kbtu.phonebook.service.interfaces.AdminService;
import kz.kbtu.phonebook.service.interfaces.KafkaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final UsersRepo usersRepo;
    private final JwtTokenFilter jwtTokenFilter;
    private final CasbinServiceImpl casbinService;
    private final KafkaService kafkaService;
    @Override
    public Boolean delete(Long id, HttpServletRequest request) {
        HashMap<String, String> authority = jwtTokenFilter.getUserDetailsByHttpRequest(request);
        if(casbinService.checkAuthorize(authority.get("rule"), "user", "delete")){
            Users user = usersRepo.findById(id).stream().findFirst().orElse(null);
            if(user == null){
                throw new UserNotFoundException("User not found !");
            }
            usersRepo.delete(user);
            kafkaService.sendMessage("my-topic", "User successfully deleted");
            return true;
        }
        else{
            throw new PermissionDeniedException("You don't have permission to delete user");
        }

    }
}
