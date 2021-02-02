package tr.com.kafein.wall.security;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
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
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import tr.com.kafein.wall.dto.ErrorDto;

import javax.servlet.http.HttpServletResponse;

@EnableFeignClients
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class MultiHttpSecurityConfig extends WebSecurityConfigurerAdapter {

    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(HttpSecurity http) throws Exception {
        ErrorDto dto = new ErrorDto();
        dto.setResultCode(401);
        dto.setResult(HttpStatus.UNAUTHORIZED.name());
        dto.setErrorMessage("Lütfen Giris Yapınız");
        objectMapper.getFactory().configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);

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

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedMethods("HEAD", "GET", "PUT", "POST", "DELETE", "PATCH", "OPTIONS");
            }
        };
    }
}
