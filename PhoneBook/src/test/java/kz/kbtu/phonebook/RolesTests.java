package kz.kbtu.phonebook;

import kz.kbtu.phonebook.models.Roles;
import kz.kbtu.phonebook.repo.RolesRepo;
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
public class RolesTests {

    @Autowired private RolesRepo repo;

    @Test
    public void testCreateRoles() {
        Roles editor = new Roles(1L, "ROLE_USER_READER");
        Roles admin = new Roles(2L, "ROLE_ADMIN");

        repo.saveAll(List.of(editor, admin));

        long count = repo.count();
        assertEquals(2, count);
    }

}
