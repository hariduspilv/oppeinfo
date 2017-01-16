package ee.hitsa.ois.config;

import ee.hitsa.ois.service.security.HoisUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true)
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
@EnableRedisHttpSession
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final HoisUserDetailsService userDetailsService;

    public SecurityConfiguration(HoisUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    /**
     *  DISCLAIMER: this is mock security and protects from nothing
     *  TODO: Setup security.
     */
    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);//.passwordEncoder(passwordencoder());
    }

    /**
     *  TODO: All is allowed before user rights and roles etc is done.
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().and().logout().and()
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS).permitAll()
                .antMatchers("/user", "/", "/logout"/*, "/**"*/).permitAll()
                //.antMatchers("/subject", "/subject/**").hasAnyAuthority("ROLE_A")
                //.antMatchers("/subject", "/subject/**").hasAnyRole("A")
                //.anyRequest().authenticated()
                .anyRequest().permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/403")
                .and()
                .csrf().disable()
                /*.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())*/;
    }
}