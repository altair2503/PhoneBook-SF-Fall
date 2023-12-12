package kz.kbtu.phonebook;

import kz.kbtu.phonebook.models.Role;
import kz.kbtu.phonebook.repository.RoleRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class RoleTests {

    @Mock private RoleRepository roleRepository;

    @Test
    public void testCreateRoles() {
        Role editor = new Role(1L, "ROLE_USER_READER");
        Role admin = new Role(2L, "ROLE_ADMIN");

        when(roleRepository.saveAll(any(List.class))).thenReturn(List.of(editor, admin));
        List<Role> roles = roleRepository.saveAll(List.of(editor, admin));

        assertEquals(2, roles.size());
    }

}
