package kz.kbtu.phonebook.repo;

import kz.kbtu.phonebook.models.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolesRepo extends JpaRepository<Roles, Long> {
    Optional<Roles> findById(long id);
}
