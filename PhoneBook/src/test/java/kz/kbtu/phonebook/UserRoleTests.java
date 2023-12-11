package kz.kbtu.phonebook;

import kz.kbtu.phonebook.models.User;
import kz.kbtu.phonebook.models.UserRoles;
import kz.kbtu.phonebook.repository.RoleRepository;
import kz.kbtu.phonebook.repository.UserRepository;
import kz.kbtu.phonebook.repository.UserRoleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.util.Assert;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class UserRoleTests {

    @Autowired private UserRepository userRepository;
    @Autowired
    UserRoleRepository userRoleRepository;
    @Autowired
    RoleRepository roleRepository;

    @Test
    public void testAssignRoleToUser() {
        User user = userRepository.findById(5).get();

        userRoleRepository.save(
            new UserRoles(
                user,
                roleRepository.findById(2).stream().findFirst().orElse(null)
            )
        );

        Assert.notEmpty(userRoleRepository.findAll().stream().toList(), "Users Roles is not empty");
    }

}
