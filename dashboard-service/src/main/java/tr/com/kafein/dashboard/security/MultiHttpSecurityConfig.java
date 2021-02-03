package tr.com.kafein.dashboard.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import tr.com.kafein.dashboard.dto.ErrorDto;

import javax.servlet.http.HttpServletResponse;

import static tr.com.kafein.dashboard.util.Constants.MUST_BE_LOGIN;

@EnableFeignClients
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class MultiHttpSecurityConfig extends WebSecurityConfigurerAdapter {

    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(HttpSecurity http) throws Exception {
        ErrorDto dto = ErrorDto.builder()
                .resultCode(HttpStatus.UNAUTHORIZED.value())
                .result(HttpStatus.UNAUTHORIZED.name())
                .errorMessage(MUST_BE_LOGIN)
                .build();

        http.csrf().disable()
                .antMatcher("/**").authorizeRequests()
                .anyRequest().authenticated()
                .and().exceptionHandling()
                .authenticationEntryPoint((req, rsp, e) -> {
                    rsp.resetBuffer();
                    rsp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    rsp.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
                    rsp.getOutputStream().print(objectMapper.writeValueAsString(dto));
                    rsp.flushBuffer();
                })
                .and().addFilter(new ApiJWTAuthorizationFilter(authenticationManager()))
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().httpBasic().disable();

        http.headers().frameOptions().disable();
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/", "/v2/api-docs", "/configuration/ui", "/swagger-resources",
                "/configuration/security", "/swagger-ui.html", "/webjars/**", "/swagger-resources/configuration/ui",
                "/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**");
    }

}
