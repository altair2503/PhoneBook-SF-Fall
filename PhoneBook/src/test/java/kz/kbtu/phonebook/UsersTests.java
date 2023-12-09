package kz.kbtu.phonebook;

import kz.kbtu.phonebook.models.Users;
import kz.kbtu.phonebook.repo.UsersRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class UsersTests {

    @Autowired private UsersRepo repo;

    @Test
    public void testCreateUser() {
        Users newUser = new Users("Manarbeku", "123456", "manarbek1@kbtu.kz", "874560565165");
        Users savedUser = repo.save(newUser);

        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getId()).isGreaterThan(0);
    }

}
