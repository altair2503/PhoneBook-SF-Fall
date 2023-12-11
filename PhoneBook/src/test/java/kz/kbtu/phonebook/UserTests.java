package kz.kbtu.phonebook;

import kz.kbtu.phonebook.models.User;
import kz.kbtu.phonebook.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class UserTests {

    @Autowired private UserRepository repo;

    @Test
    public void testCreateUser() {
        User newUser = new User("Manarbeku", "123456", "manarbek@kbtu.kz", "874560565165");
        User savedUser = repo.save(newUser);

        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getId()).isGreaterThan(0);
    }

}
