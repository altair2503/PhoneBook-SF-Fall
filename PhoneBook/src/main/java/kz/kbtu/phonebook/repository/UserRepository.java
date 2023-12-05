package kz.kbtu.phonebook.repository;

import kz.kbtu.phonebook.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);
    Optional<User> findByFirstNameOrLastName(String firstName, String lastName);
    Optional<User> findByPhoneNumber(String phoneNumber);
    Optional<User> findUserById(Integer id);
}