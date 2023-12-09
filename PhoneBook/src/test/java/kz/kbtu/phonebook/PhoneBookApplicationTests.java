package kz.kbtu.phonebook;

import kz.kbtu.phonebook.models.UserRoles;
import kz.kbtu.phonebook.models.Users;
import kz.kbtu.phonebook.repo.RolesRepo;
import kz.kbtu.phonebook.repo.UsersRepo;
import kz.kbtu.phonebook.repo.UsersRolesRepo;
import kz.kbtu.phonebook.service.impls.CasbinServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.URI;

@SpringBootTest
class PhoneBookApplicationTests {
    @Autowired UsersRepo usersRepo;
    @Autowired UsersRolesRepo usersRolesRepo;
    @Autowired RolesRepo rolesRepo;
    @Test
    void contextLoads() {
        Users savedUser = usersRepo.save(new Users("admin3", "admin3", "admin3@gmail.com", "12312"));

        usersRolesRepo.save(new UserRoles(savedUser, rolesRepo.findById(2).stream().findFirst().orElse(null)));
    }

}
