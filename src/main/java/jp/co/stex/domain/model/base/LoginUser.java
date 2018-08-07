package jp.co.stex.domain.model.base;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;


@Getter
@Setter
@ToString
public class LoginUser extends User {

    /**
     * シリアルバージョン
     */
    private static final long serialVersionUID = 5658387712051101356L;

    /**
     * ユーザエンティティを詰め替えるコンストラクタ
     *
     * @param user ユーザ
     */
    public LoginUser(UserEntity user) {
        super(user.getName(), user.getPassword(), AuthorityUtils.createAuthorityList(user.getAuthority()));
    }
}
