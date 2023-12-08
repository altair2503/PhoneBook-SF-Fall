package kz.kbtu.phonebook.user;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {

    @Autowired private UserRepository repo;

    @Test
    public void testCreateUser() {
        User newUser = new User("mana@kbtu.kz", "manasila", "Mana", "Esim", "879743512343");
        User savedUser = repo.save(newUser);

        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getId()).isGreaterThan(0);
    }

    @Test
    public void testAssignRoleToUser() {
        Integer userId = 6;
        Integer roleId = 1;

        User user = repo.findById(userId).get();
        user.addRole(new Role(roleId));

        User updatedUser = repo.save(user);
        assertThat(updatedUser.getRoles()).hasSize(1);

    }

}
