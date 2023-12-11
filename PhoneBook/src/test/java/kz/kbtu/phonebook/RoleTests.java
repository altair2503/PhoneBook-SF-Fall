package kz.kbtu.phonebook;

import kz.kbtu.phonebook.models.Role;
import kz.kbtu.phonebook.repository.RoleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class RoleTests {

    @Autowired private RoleRepository repo;

    @Test
    public void testCreateRoles() {
        Role editor = new Role(1L, "ROLE_USER_READER");
        Role admin = new Role(2L, "ROLE_ADMIN");

        repo.saveAll(List.of(editor, admin));

        long count = repo.count();
        assertEquals(2, count);
    }

}
