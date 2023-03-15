package com.snehal.multimart.config;


import com.snehal.multimart.filter.JwtAuthFilter;
import org.apache.catalina.filters.CorsFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebSecurity
@EnableWebMvc
@EnableMethodSecurity
public class SecurityConfig {


    public static final String[] PUBLIC_URLS={
            "/products/authenticate",
            "/products/new",
            "/v3/api-docs",
            "/v2/api-docs",
            "/swagger-resources/**",
            "/swagger-ui/**",
            "/webjars/**"
    };

    @Autowired
    private JwtAuthFilter authFilter;



    @Bean
    public UserDetailsService userDetailsService() {
//
//        UserDetails admin = User.withUsername("snehal")
//                .password(encoder.encode("pwd1"))
//                .roles("ADMIN")
//                .build();
//
//
//        UserDetails user = User.withUsername("john")
//                .password(encoder.encode("pwd2"))
//                .roles("USER")
//                .build();
//
//        return new InMemoryUserDetailsManager(admin,user);
        return new UserInfoUserDetailsService();

    }
      @Bean
      public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
          return http.csrf().disable()
                  .authorizeHttpRequests()
//                  .requestMatchers("/products/welcome","/products/new","/products/authenticate").permitAll()
                  .requestMatchers(PUBLIC_URLS).permitAll()
                  .and()
                  .authorizeHttpRequests()
                  .requestMatchers(HttpMethod.GET,"/products/**","/api/categories/**","/prod/**","/cart/**","/banner/**")
                  .permitAll()
                  .anyRequest()
                  .authenticated().and()
                  .sessionManagement()
                  .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                  .and()
                  .authenticationProvider(authenticationProvider())
                  .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
                  .build();
      }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();

    }


    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider=new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;


    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }


//        @Bean
//        public FilterRegistrationBean coresFilter() {
//            UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//
//            CorsConfiguration corsConfiguration = new CorsConfiguration();
//
//            corsConfiguration.setAllowCredentials(true);
//            corsConfiguration.addAllowedOrigin("*");
//            corsConfiguration.addAllowedHeader("Authorization");
//            corsConfiguration.addAllowedHeader("Content-Type");
//            corsConfiguration.addAllowedHeader("Accept");
//            corsConfiguration.addAllowedMethod("POST");
//            corsConfiguration.addAllowedMethod("GET");
//            corsConfiguration.addAllowedMethod("DELETE");
//            corsConfiguration.addAllowedMethod("PUT");
//            corsConfiguration.addAllowedMethod("OPTIONS");
//            corsConfiguration.setMaxAge(3600L);
//
//            source.registerCorsConfiguration("/**", corsConfiguration);
//
//            FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter());
////            bean.setOrder(-110);
//            return bean;
//        }


}
