package kz.kbtu.phonebook;

import kz.kbtu.phonebook.models.UserRoles;
import kz.kbtu.phonebook.models.User;
import kz.kbtu.phonebook.repository.RoleRepository;
import kz.kbtu.phonebook.repository.UserRepository;
import kz.kbtu.phonebook.repository.UserRoleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PhoneBookApplicationTests {
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserRoleRepository userRoleRepository;
    @Autowired
    RoleRepository roleRepository;
    @Test
    void contextLoads() {
        User savedUser = userRepository.save(new User("admin3", "admin3", "admin3@gmail.com", "12312"));

        userRoleRepository.save(new UserRoles(savedUser, roleRepository.findById(2).stream().findFirst().orElse(null)));
    }

}
