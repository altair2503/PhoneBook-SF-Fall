package kz.kbtu.phonebook;

import kz.kbtu.phonebook.models.UserRoles;
import kz.kbtu.phonebook.models.Users;
import kz.kbtu.phonebook.repo.RolesRepo;
import kz.kbtu.phonebook.repo.UsersRepo;
import kz.kbtu.phonebook.repo.UsersRolesRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.util.Assert;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class UserRolesTests {

    @Autowired private UsersRepo usersRepo;
    @Autowired UsersRolesRepo usersRolesRepo;
    @Autowired RolesRepo rolesRepo;

    @Test
    public void testAssignRoleToUser() {
        Users user = usersRepo.findById(3).get();

        usersRolesRepo.save(
            new UserRoles(
                user,
                rolesRepo.findById(1).stream().findFirst().orElse(null)
            )
        );

        Assert.notEmpty(usersRolesRepo.findAll().stream().toList(), "Users Roles is not empty");
    }

}
