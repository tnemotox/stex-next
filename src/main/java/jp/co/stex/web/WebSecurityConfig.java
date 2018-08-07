package jp.co.stex.web;

import jp.co.stex.domain.service.base.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * <p>SpringSecurityの設定を行うクラスです。</p>
 *
 * @author t.nemoto.x
 */
@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * ユーザサービス
     */
    private final UserService usersService;

    /**
     * {@inheritDoc}
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                // アクセス権限の設定
                // staticディレクトリにある、'/css/','fonts','/js/'は制限なし
                .antMatchers("/*.js").permitAll()
                .antMatchers("/*.eot").permitAll()
                .antMatchers("/*.woff").permitAll()
                .antMatchers("/*.woff2").permitAll()
                .antMatchers("/*.ttf").permitAll()
                .antMatchers("/*.svg").permitAll()
                // 他は制限なし
                .anyRequest().authenticated()
                .and()
                // ログイン処理の設定
                .formLogin()
                // ログイン処理のURL
                .loginPage("/login")
                .defaultSuccessUrl("/")
                // usernameのパラメタ名
                .usernameParameter("username")
                // passwordのパラメタ名
                .passwordParameter("password")
                .permitAll()
                .and()
                // ログアウト処理の設定
                .logout()
                // ログアウト処理のURL
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                // ログアウト成功時の遷移先URL
                .logoutSuccessUrl("/login")
                // ログアウト時に削除するクッキー名
                .deleteCookies("JSESSIONID")
                // ログアウト時のセッション破棄を有効化
                .invalidateHttpSession(true)
                .permitAll();
        http.csrf().disable();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(usersService)
                .passwordEncoder(new BCryptPasswordEncoder());
    }
}
