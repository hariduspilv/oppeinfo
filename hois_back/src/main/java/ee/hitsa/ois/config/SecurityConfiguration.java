package ee.hitsa.ois.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

import ee.hitsa.ois.auth.EstonianIdCardAuthenticationProvider;
import ee.hitsa.ois.filter.EstonianIdCardAuthenticationFilter;
import ee.hitsa.ois.filter.JwtAuthorizationFilter;
import ee.hitsa.ois.service.security.HoisUserDetailsService;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true)
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
@EnableRedisHttpSession
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private HoisUserDetailsService userDetailsService;
    @Autowired
    private HoisJwtProperties hoisJwtProperties;

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/user").permitAll()
                .antMatchers(HttpMethod.GET, "/autocomplete/classifiers").permitAll()
                .antMatchers(HttpMethod.GET, "/autocomplete/schools").permitAll()
                .anyRequest().authenticated()
                .and()
            .httpBasic()
                .and()
            .exceptionHandling()
                .accessDeniedPage("/403")
                .and()
            .addFilter(new JwtAuthorizationFilter(authenticationManager(), userDetailsService, hoisJwtProperties))
            .csrf().disable();
                //.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
    }
}


@Configuration
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER - 1)
@EnableGlobalMethodSecurity(securedEnabled = true)
class IdCardLoginSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private EstonianIdCardAuthenticationProvider estonianIdCardAuthenticationProvider;

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(estonianIdCardAuthenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.antMatcher("/idlogin")
            .authorizeRequests()
                .anyRequest()
                .permitAll()
                .and()
            .addFilter(new EstonianIdCardAuthenticationFilter(authenticationManager()))
            .csrf()
                .disable();
    }
}

@Configuration
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER - 2)
class UniqueUrlSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.antMatcher("/practiceJournals/supervisor/{uuid}/**")
            .authorizeRequests()
                .anyRequest()
                .permitAll()
                .and()
            .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
            .csrf()
                .disable();
    }
}

