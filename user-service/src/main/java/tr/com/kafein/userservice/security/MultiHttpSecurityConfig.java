package tr.com.kafein.userservice.security;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import javax.servlet.http.HttpServletResponse;

@EnableFeignClients
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class MultiHttpSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .antMatcher("/**").authorizeRequests()
                .antMatchers("/username/**").permitAll()
                .anyRequest().authenticated()
                .and().exceptionHandling()
                .authenticationEntryPoint((req, rsp, e) -> rsp.sendError(HttpServletResponse.SC_UNAUTHORIZED))
                .and().addFilter(new ApiJWTAuthorizationFilter(authenticationManager()))
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().httpBasic().disable();

        http.headers().frameOptions().disable();
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/common/**", "/v2/api-docs", "/configuration/ui", "/swagger-resources/**",
                "/configuration/security", "/swagger-ui.html", "/webjars/**", "/","/h2","/h2-console");
    }
}
