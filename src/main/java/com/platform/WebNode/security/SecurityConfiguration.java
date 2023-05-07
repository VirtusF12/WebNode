package com.platform.WebNode.security;

import com.platform.WebNode.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import static com.platform.WebNode.config.EncryptionConfig.passwordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    /* Authentication mechanism (login, password) */
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {

        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userService);

        return daoAuthenticationProvider;
    }

    /* Authorization mechanism (role) */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity https) throws Exception {

        https
            .csrf()
                .disable()
                .httpBasic().and()
            .authorizeHttpRequests((requests) -> requests
                 .requestMatchers("/","/reg","/login","/error", "/static/**", "/rest/**",
                         "/app/**","/topic/**","/topic","/ws/**","/ws",
                         "/listmessage/**", "/fetchAllUsers/**","/chat/**",
                         "/resources/**","/css/**","/js/**","/img/**").permitAll()
                 .requestMatchers("/auth/show").hasAnyRole( "ADMIN")
                 .requestMatchers("/auth/**").authenticated())
            .formLogin((login) -> login
                    .loginPage("/login")
                    .defaultSuccessUrl("/auth")
                    .permitAll())
            .logout((logout) -> logout
                    .logoutUrl("/logout")
                    .invalidateHttpSession(true)
                    .clearAuthentication(true)
                    .deleteCookies("JSESSIONID")
                    .permitAll()
                    .logoutSuccessUrl("/"));


        return https.build();
    }
}
