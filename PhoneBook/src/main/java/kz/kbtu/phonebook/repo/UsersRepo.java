package kz.kbtu.phonebook.repo;

import kz.kbtu.phonebook.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepo extends JpaRepository<Long, Users> {

}
