package kz.kbtu.phonebook.repository;

import kz.kbtu.phonebook.models.Role;
import kz.kbtu.phonebook.models.UserRoles;
import kz.kbtu.phonebook.models.User;
import kz.kbtu.phonebook.models.UserRoleId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRoles, UserRoleId> {
    UserRoles findUserRolesByUser(User user);
    Optional<Role> findRoleByUserId(Long id);
}
