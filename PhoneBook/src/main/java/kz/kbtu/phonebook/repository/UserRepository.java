package kz.kbtu.phonebook.repository;

import kz.kbtu.phonebook.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findById(long id);

    Optional<User> findUsersByUsernameOrPhone(String username, String phone);

}