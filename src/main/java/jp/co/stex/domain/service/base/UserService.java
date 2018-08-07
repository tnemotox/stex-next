package jp.co.stex.domain.service.base;

import jp.co.stex.domain.mapper.base.UserMapper;
import jp.co.stex.domain.model.base.LoginUser;
import jp.co.stex.domain.model.base.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * ユーザ情報を操作するサービスです。
 *
 * @author t.nemoto.x
 * @since 1.0.0
 */
@Service
@Transactional
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    /**
     * ユーザリポジトリ
     */
    private final UserMapper userMapper;

    /**
     * {@inheritDoc}
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username == null || "".equals(username)) {
            throw new UsernameNotFoundException("Username is empty");
        }

        UserEntity user = userMapper.findOneByName(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found for name: " + username);
        }
        return new LoginUser(user);
    }

    /**
     * ユーザIDを取得する。
     *
     * @param username ユーザ名
     * @return ユーザID
     */
    public int findUserId(String username) {
        if (username == null || "".equals(username)) {
            throw new UsernameNotFoundException("Username is empty");
        }
        UserEntity user = userMapper.findOneByName(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found for name: " + username);
        }
        return user.getId();
    }
}
