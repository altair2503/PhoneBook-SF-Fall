package kz.kbtu.phonebook.repo;

import kz.kbtu.phonebook.models.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolesRepo extends JpaRepository<Long , Roles> {
}
