package kz.kbtu.phonebook.models;

import javax.persistence.*;

@Entity
@Table(name = "users_roles")
public class UserRoles {
    @Id
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private Users user;

    @Id
    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private Roles role;

    public Users getUser() {
        return user;
    }

    public Roles getRole() {
        return role;
    }
}
