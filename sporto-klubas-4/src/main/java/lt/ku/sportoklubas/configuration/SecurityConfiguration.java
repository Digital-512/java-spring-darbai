package lt.ku.sportoklubas.configuration;

import lt.ku.sportoklubas.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final ClientService clientService;

    @Autowired
    public SecurityConfiguration(ClientService clientService) {
        this.clientService = clientService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
        auth.userDetailsService(this.clientService).passwordEncoder(bc);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()

                .antMatchers("/clients/").hasAnyAuthority("admin")
                .antMatchers("/clients/add/**").hasAnyAuthority("admin")
                .antMatchers("/clients/edit/**").hasAnyAuthority("admin")
                .antMatchers("/clients/edit-account/**").hasAnyAuthority("user", "admin")
                .antMatchers("/workouts/**").hasAnyAuthority("admin")
                .antMatchers("/robots.txt").permitAll()
                .antMatchers("/assets/**").permitAll()
                .antMatchers("/login/**").permitAll()
                .antMatchers("/register/**").permitAll()

                .anyRequest().authenticated()

                .and().formLogin().loginPage("/login").defaultSuccessUrl("/")
                .and().logout().logoutUrl("/logout").logoutSuccessUrl("/");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
