package ee.hitsa.ois.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.access.expression.WebSecurityExpressionRoot;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.stereotype.Component;

import ee.hitsa.ois.auth.EstonianIdCardAuthenticationProvider;
import ee.hitsa.ois.filter.EstonianIdCardAuthenticationFilter;
import ee.hitsa.ois.filter.JwtAuthorizationFilter;
import ee.hitsa.ois.filter.StudentToSExpressionRoot;
import ee.hitsa.ois.service.BdocService;
import ee.hitsa.ois.service.security.HoisUserDetailsService;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true)
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private HoisUserDetailsService userDetailsService;
    @Autowired
    private HoisJwtProperties hoisJwtProperties;
    @Value("${hois.frontend.baseUrl}")
    private String frontendBaseUrl;
    @Autowired
    private CustomWebSecurityExpressionHandler handler;

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/user").permitAll()
                .antMatchers("/ldap").permitAll()
                .antMatchers("/mIdLogin").permitAll()
                .antMatchers("/mIdAuthentication").permitAll()
                .antMatchers("/SingleSignOnService").permitAll()
                .antMatchers("/taraLogin").permitAll()
                .antMatchers("/taraCallback").permitAll()
                .antMatchers("/haridLogin").permitAll()
                .antMatchers("/haridCallback").permitAll()
                .antMatchers(HttpMethod.GET, "/public/**").permitAll()
                .antMatchers(HttpMethod.GET, "/autocomplete/classifiers").permitAll()
                .antMatchers(HttpMethod.GET, "/autocomplete/schools").permitAll()
                .antMatchers(HttpMethod.GET, "/autocomplete/schoolsWithLogo").permitAll()
                .antMatchers(HttpMethod.GET, "/autocomplete/ldapschools").permitAll()
                .antMatchers(HttpMethod.GET, "/academicCalendar/**").permitAll()
                .antMatchers(HttpMethod.GET, "/classifierConnect/**").permitAll()
                .antMatchers(HttpMethod.GET, "/classifier/children/**").permitAll()
                .antMatchers(HttpMethod.GET, "/classifier/parents/**").permitAll()
                .antMatchers(HttpMethod.GET, "/curriculumVersion/schoolDepartments/curriculum/**").permitAll()
                .antMatchers(HttpMethod.GET, "/generalmessages/showsitemessages").permitAll()
                .antMatchers(HttpMethod.GET, "/timetables/timetableStudyYears/**").permitAll()
                .antMatchers(HttpMethod.GET, "/timetables/timetableStudyYearWeeks/**").permitAll()
                .antMatchers(HttpMethod.GET, "/timetables/group/**").permitAll()
                .antMatchers(HttpMethod.GET, "/timetables/teacher/**").permitAll()
                .antMatchers(HttpMethod.GET, "/timetables/room/**").permitAll()
                .antMatchers(HttpMethod.GET, "/timetableevents/timetableByGroup/**").permitAll()
                .antMatchers(HttpMethod.GET, "/timetableevents/timetableByTeacher/**").permitAll()
                .antMatchers(HttpMethod.GET, "/timetableevents/timetableByRoom/**").permitAll()
                .antMatchers(HttpMethod.GET, "/timetableevents/timetableByPerson/**").permitAll()
                .antMatchers(HttpMethod.GET, "/timetableevents/timetableSearch/**").permitAll()
                .antMatchers(HttpMethod.GET, "/oisfile/get/studymaterial/**").permitAll()
                .antMatchers(HttpMethod.GET, "/oisfile/get/pollThemeQuestionFile/**").permitAll()
                .antMatchers("/changeUser", "/message/received/new", "/userContract", "/logout").authenticated()
                .anyRequest().access("isAuthenticated() and !hasToConfirmToS()").expressionHandler(handler)
                .and()
            .addFilter(new JwtAuthorizationFilter(authenticationManager(), userDetailsService, hoisJwtProperties))
            .csrf()
                .csrfTokenRepository(getRootCookieCsrfTokenRepository())
                .ignoringAntMatchers("/ldap", "/mIdLogin", "/taraLogin", "/haridLogin");

        http.logout()
                .addLogoutHandler(userDetailsService)
                .logoutSuccessUrl(frontendBaseUrl);
    }

    private static CsrfTokenRepository getRootCookieCsrfTokenRepository() {
        CookieCsrfTokenRepository cookieCsrfTokenRepository = CookieCsrfTokenRepository.withHttpOnlyFalse();
        cookieCsrfTokenRepository.setCookiePath("/");
        return cookieCsrfTokenRepository;
    }
}

@Component
class CustomWebSecurityExpressionHandler extends DefaultWebSecurityExpressionHandler {

    @Autowired
    private HoisUserDetailsService userDetailsService;

    @Override
    protected SecurityExpressionRoot createSecurityExpressionRoot(Authentication authentication, FilterInvocation fi) {
        WebSecurityExpressionRoot expressionRoot = new StudentToSExpressionRoot(authentication, fi, userDetailsService);
        expressionRoot.setTrustResolver(new AuthenticationTrustResolverImpl());
        expressionRoot.setRoleHierarchy(new RoleHierarchyImpl());
        return expressionRoot;
    }
}

@Configuration
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER - 1)
class WebServicesConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.antMatcher("/services/**")
            .authorizeRequests()
                .anyRequest()
                .permitAll()
                .and()
            .csrf()
                .disable();
    }
}


@Configuration
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER - 2)
@EnableGlobalMethodSecurity(securedEnabled = true)
class IdCardLoginSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private EstonianIdCardAuthenticationProvider estonianIdCardAuthenticationProvider;
    @Autowired
    private BdocService bdocService;

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
            .addFilter(new EstonianIdCardAuthenticationFilter(authenticationManager(), bdocService))
            .csrf()
                .disable();
    }
}

@Configuration
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER - 3)
class UniqueUrlSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.
            requestMatchers()
                .antMatchers("/practiceJournals/supervisor/{uuid}/**",
                        "/poll/supervisor/{uuid}/**", 
                        "/poll/expert/{uuid}/**")
                    .and()
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

@Configuration
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER - 4)
class AsyncSecurityHolderInheritance extends WebSecurityConfigurerAdapter {
    
    @Autowired
    private HoisUserDetailsService userDetailsService;
    @Autowired
    private HoisJwtProperties hoisJwtProperties;
    
    public AsyncSecurityHolderInheritance() {
        super();
        SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .requestMatchers()
                .antMatchers("/students/ehisStudentExport")
                    .and()
                .authorizeRequests()
                    .anyRequest()
                    .permitAll()
                    .and()
                .addFilter(new JwtAuthorizationFilter(authenticationManager(), userDetailsService, hoisJwtProperties))
                .csrf()
                    .disable();
    }
}

@Configuration
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER - 5)
class ApiConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.antMatcher("/juhan/**")
                .authorizeRequests()
                .anyRequest()
                .permitAll()
                .and()
        .csrf()
                .disable();
    }
}