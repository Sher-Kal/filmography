package ru.sbercources.filmography.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ru.sbercources.filmography.service.userDetails.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private final CustomUserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public WebSecurityConfig(CustomUserDetailsService userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    //Конфигурация прав доступа пользователя
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf()
                .disable()
                .authorizeRequests()
                //Страницы, доступные всем
                .antMatchers("/login", "/users/registration", "/users/remember-password", "/users/change-password/**")
                .permitAll()
                .and()
                .authorizeRequests()
                .antMatchers("/resources/**")
                .permitAll()
                //Все остальные страницы требуют аутентификации
                .antMatchers(
                        "/directors/add",
                        "/directors/update/*",
                        "/directors/add-director/*",
                        "/directors/delete/*")
                .not().hasRole("USER")
                .antMatchers(
                        "/films/add",
                        "/films/update/*",
                        "/films/delete/*")
                .not().hasRole("USER")
                .antMatchers("/directors/**").authenticated()
                .antMatchers("/films/**").authenticated()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                //Настройка для входа в систему
                .loginPage("/login")
                //Перенаправление на главную страницу после успешного входа
                .defaultSuccessUrl("/")
                .permitAll()
                .and()
                .logout()
                .permitAll()
                //перенаправление после выхода
                .logoutSuccessUrl("/login");
        return http.build();
    }

    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }
}