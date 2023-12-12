package kz.kbtu.phonebook;

import kz.kbtu.phonebook.models.User;
import kz.kbtu.phonebook.repository.UserRepository;
import kz.kbtu.phonebook.service.implementations.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserTests {

    @Mock UserRepository userRepository;

    @Test
    public void testCreateUser() {
        User newUser = new User("Manarbekk", "123456", "manarbek1@kbtu.kz", "8484556263");
        when(userRepository.save(any(User.class))).then(returnsFirstArg());
        User savedUser = userRepository.save(newUser);
        assertThat(savedUser).isNotNull();
    }

}
