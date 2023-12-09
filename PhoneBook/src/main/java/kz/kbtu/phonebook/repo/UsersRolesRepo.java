package kz.kbtu.phonebook.repo;

import kz.kbtu.phonebook.models.UserRoles;
import kz.kbtu.phonebook.models.Users;
import kz.kbtu.phonebook.models.UsersRolesId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRolesRepo extends JpaRepository<UserRoles, UsersRolesId> {
    UserRoles findUserRolesByUser(Users user);
}
