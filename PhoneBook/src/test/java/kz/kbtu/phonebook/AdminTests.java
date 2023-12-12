package kz.kbtu.phonebook;

import kz.kbtu.phonebook.models.User;
import kz.kbtu.phonebook.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.util.Assert;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback()
public class AdminTests {

    @Autowired UserRepository userRepository;

    @Test
    public void testDeleteUser() {
        User user = userRepository.findById(3).stream().findFirst().orElse(null);
        userRepository.delete(user);

        Assert.isNull(userRepository.findById(1).stream().findFirst().orElse(null), "User successfully deleted");
    }

}
