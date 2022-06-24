package com.example.fooddelivery2_0.Security;

import com.example.fooddelivery2_0.Components.LoginSuccessHandler;
import com.example.fooddelivery2_0.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@AllArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserService userService;
    private final LoginSuccessHandler loginSuccessHandler;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS).and()
                //.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                //.and()
                //THIS HERE
                .csrf().ignoringAntMatchers("/home/filter", "/home", "/admin/**").and() //only for development
                .authorizeRequests()
                .antMatchers("/restaurant","/css/**", "/js/**", "/css/**", "/audio/**", "/","/style.css","/index")
                .permitAll()
                .antMatchers("/admin/**").hasAuthority("ADMIN")
                .antMatchers("/secured/notification-websocket/**").hasAnyAuthority("RESTAURANT","SUPER_RESTAURANT")
                .antMatchers("/secured/order-progress-websocket/**").hasAuthority("CUSTOMER")
                .antMatchers("/Restoran/**", "/restaurant_orders/**").hasAnyAuthority("ADMIN", "RESTAURANT","SUPER_RESTAURANT")
                .antMatchers("/narudzba/**").hasAnyAuthority("CUSTOMER","RESTAURANT","SUPER_RESTAURANT")
                .and()
                .formLogin()
                .loginPage("/login")
                .successHandler(loginSuccessHandler)
                .permitAll()
                .and()
                .logout().deleteCookies("remove").invalidateHttpSession(false)
                .logoutUrl("/custom-logout").logoutSuccessUrl("/")
                .permitAll()
                .and()
                .logout().deleteCookies("JSESSIONID")

                .and()
                .rememberMe().key("uniqueAndSecret").userDetailsService(userService);

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider();
        provider.setPasswordEncoder(bCryptPasswordEncoder);
        provider.setUserDetailsService(userService);
        return provider;
    }
}
