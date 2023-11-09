package com.example.springlibraryproject.config;

import com.example.springlibraryproject.model.Users;
import com.example.springlibraryproject.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.security.core.userdetails.User.builder;
//import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final UserServiceImpl userService;
    @Autowired
    public SecurityConfig(UserServiceImpl userService)
    {
        this.userService = userService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests((authorize) ->
                        {
                            try {
                                authorize
                                        .requestMatchers("/css/**").permitAll()
                                        .requestMatchers("/index").permitAll()
                                        .requestMatchers("/login").permitAll()
                                        .requestMatchers("/logout").permitAll()
                                        .requestMatchers("/book").hasAnyRole("USER", "ADMIN")
                                        .requestMatchers("/book/**").hasRole("ADMIN")
                                        .requestMatchers("/author").hasAnyRole("USER", "ADMIN")
                                        .requestMatchers("/author/**").hasRole("ADMIN")
                                        .requestMatchers("/genre").hasRole( "ADMIN")
                                        .anyRequest().authenticated()
                                        .and()
//                                        .formLogin()
//                                            .loginPage("/login")
//                                            .permitAll()
//                                            .defaultSuccessUrl("/author")
//                                        .and()
                                        .logout() // добавить выход из системы
                                            .logoutUrl("/logout") // указать URL для выхода из системы
                                            .logoutSuccessUrl("/index?logout") // указать страницу, на которую перенаправлять после успешного выхода
                                            .invalidateHttpSession(true) // инвалидировать сеанс пользователя после выхода
                                            .deleteCookies("JSESSIONID") // удалить cookies после выхода
                                        .and()
                                        .exceptionHandling()
                                            .accessDeniedPage("/error");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                )
                .httpBasic();

        return http.build();
    }


//    @Bean
//    public UserDetailsService users() {
//        User.UserBuilder users = User.withDefaultPasswordEncoder();
//        UserDetails user = users
//                .username("user")
//                .password("pas")
//                .roles("USER")
//                .build();
//        UserDetails admin = users
//                .username("admin")
//                .password("pas")
//                .roles("USER", "ADMIN")
//                .build();
//        return new InMemoryUserDetailsManager(user, admin);
//    }
@Bean
public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
}


@Bean
public UserDetailsService userDetailsService() {
   List<Users> users = userService.getAllUser();
    List<UserDetails> userDetailsList = new ArrayList<>();
    for (Users user : users) {
        UserDetails userDetails = User
                .builder()
                .username(user.getUsername())
                .password(passwordEncoder().encode(user.getPassword()))
                .roles(user.getRole().getName())
                .build();
        userDetailsList.add(userDetails);
    }

    return new InMemoryUserDetailsManager(userDetailsList);
}
}