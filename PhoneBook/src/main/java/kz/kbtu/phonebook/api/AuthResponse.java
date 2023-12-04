package kz.kbtu.phonebook.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class AuthResponse {

    private String email;
    private String accessToken;

    public AuthResponse() { }

    public AuthResponse(String email, String accessToken) {
        this.email = email;
        this.accessToken = accessToken;
    }

}
