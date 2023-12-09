package kz.kbtu.phonebook.repo;

import kz.kbtu.phonebook.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepo extends JpaRepository<Users,Long> {
    Optional<Users> findByEmail(String email);

    Optional<Users> findById(long id);

    Optional<Users> findUsersByUsernameOrPhone(String username, String phone);
}
