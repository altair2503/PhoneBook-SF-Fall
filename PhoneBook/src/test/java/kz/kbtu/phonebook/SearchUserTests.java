package kz.kbtu.phonebook;

import kz.kbtu.phonebook.models.User;
import kz.kbtu.phonebook.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.Optional;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class SearchUserTests {

    @Autowired private UserRepository repo;

    @Test
    public void testSearchUsersByPhoneOrUsername() {
        String phone = "87056524585";
        String username = "Manarbek";

        Optional<User> usersList = repo.findUsersByUsernameOrPhone(phone, phone);

        Assert.notEmpty(Collections.singleton(usersList), "UsersList is not empty");
        System.out.println(usersList);
    }

}
