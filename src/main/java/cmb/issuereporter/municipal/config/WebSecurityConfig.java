package cmb.issuereporter.municipal.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    @Override protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/api/v1/**").permitAll()
                .antMatchers("/admin/api/v1/**").permitAll()
                .antMatchers("/upload/**").permitAll()
                .antMatchers("/index.html").permitAll()
                .antMatchers("/**").permitAll()
                .anyRequest().authenticated()
                .and().httpBasic();
    }


}