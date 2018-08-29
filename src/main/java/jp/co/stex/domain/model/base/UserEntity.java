package jp.co.stex.domain.model.base;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Data
@AllArgsConstructor
public class UserEntity implements Serializable {

    public UserEntity(String name, String password, String authority) {
        this.name = name;
        this.password = password;
        this.authority = authority;
    }

    private static final long serialVersionUID = 5658387712051101356L;

    private UUID id;

    private String name;

    private String password;

    private String authority;
}
