package kz.kbtu.phonebook.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsersRolesId implements Serializable {
    private Long user;
    private Long role;

}
