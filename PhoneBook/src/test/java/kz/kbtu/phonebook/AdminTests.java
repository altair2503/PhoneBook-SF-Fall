package kz.kbtu.phonebook;

import kz.kbtu.phonebook.models.Users;
import kz.kbtu.phonebook.repo.UsersRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.util.Assert;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class AdminTests {

    @Autowired private UsersRepo repo;

    @Test
    public void testDeleteUser() {
        Users user = repo.findById(1).stream().findFirst().orElse(null);
        repo.delete(user);

        Assert.isNull(repo.findById(1).stream().findFirst().orElse(null), "User successfully deleted");
    }

}
