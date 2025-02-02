package com.g11.FresherManage.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.ArrayList;
import java.util.List;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class    SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthenticationFilter ;
    @Autowired
    private final CustomerUserDetailsService customerUserDetailsService ;


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception
    { return authenticationConfiguration.getAuthenticationManager();}

    @Bean
    public PasswordEncoder passwordEncoder()
    { return new BCryptPasswordEncoder(); }
    @Bean
    public SecurityFilterChain securityFilterChainAdminsAPI(HttpSecurity httpSecurity) throws Exception {
        sharedSecurityConfiguration(httpSecurity);
        httpSecurity
                .securityMatcher("/api/v1/admin/**")
                .authorizeRequests(auth -> {
                    auth.anyRequest().hasAuthority("admin");
                });


        return httpSecurity.build();
    }
  @Bean
  public SecurityFilterChain securityFilterChainUsersAPI(HttpSecurity httpSecurity) throws Exception {
    sharedSecurityConfiguration(httpSecurity);
    httpSecurity
          .securityMatcher("/api/v1/**", "/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**", "/actuator/**")
          .authorizeHttpRequests(auth -> {
            auth.anyRequest().permitAll();
          });

    return httpSecurity.build();
  }



  private void sharedSecurityConfiguration(HttpSecurity httpSecurity) throws Exception {
    httpSecurity
          .csrf(AbstractHttpConfigurer::disable)
          .cors(AbstractHttpConfigurer::disable)
          .sessionManagement(httpSecuritySessionManagementConfigurer -> {
            httpSecuritySessionManagementConfigurer
                  .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
          });
  }


  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.addAllowedHeader("*");
    configuration.addAllowedOrigin("*");
    configuration.addAllowedMethod("*");
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }

//    @Bean
//    public Gson gson() {
//        return new Gson();
//    }
//
//    @Bean(name = "customMessageConverters")
//    public HttpMessageConverter<Object> createGsonHttpMessageConverter() {
//        GsonHttpMessageConverter gsonConverter = new GsonHttpMessageConverter();
//        gsonConverter.setGson(gson());
//        return gsonConverter;
//    }
//
//    @Bean
//    public List<HttpMessageConverter<?>> messageConverters() {
//        List<HttpMessageConverter<?>> converters = new ArrayList<>();
//        converters.add(createGsonHttpMessageConverter());
//        return converters;
//    }

}
