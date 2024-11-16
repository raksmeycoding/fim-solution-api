package com.fimsolution.group.app.security;


import com.fimsolution.group.app.filters.JwtAuthenticationFilter;
import com.fimsolution.group.app.utils.WhiteListEndpoint;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserDetailsServiceImpl userDetailsService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final FimAccessDeniedHandler fimAccessDeniedHandler;
    private final FimAuthenticationEntrypoint fimAuthenticationEntrypoint;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity
//                .csrf(csrf -> {
//
//                    CookieCsrfTokenRepository repository = new CookieCsrfTokenRepository();
//                    repository.setCookieCustomizer(responseCookieBuilder -> {
//                        responseCookieBuilder.httpOnly(true);
//                        responseCookieBuilder.secure(true);
//                        responseCookieBuilder.path("/");
//                        responseCookieBuilder.sameSite("Strict");
//                        responseCookieBuilder.maxAge(900);
//
//                    });
//                })
//
//
//                    csrf
//                            .csrfTokenRepository(repository)
//                            .ignoringRequestMatchers("/h2-console/**");
//
//                })
//                .csrf(csrf -> {
//                    csrf.csrfTokenRepository(new HttpSessionCsrfTokenRepository());
//                })
//                .csrf(csrf -> csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
                .csrf(AbstractHttpConfigurer::disable)
//                .csrf(csrf -> csrf.csrfTokenRepository(csrfTokenRepository()))

                .headers(header -> {
                    header.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin);
                })
//                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers(
                            "/v3/api-docs/**",
                            "/swagger-ui.html",
                            "/swagger-ui/**",
                            "/webjars/**",
                            "/h2-console/**",
                            "/favicon.ico").permitAll();
                    auth.requestMatchers("/health").permitAll();
                    auth.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll();
                    auth.requestMatchers("/static/**").permitAll();
                    auth.requestMatchers("/api/v1/test/hello").permitAll();
                    auth.requestMatchers(HttpMethod.GET, "/api/v1/auth/csrf").permitAll();
                    auth.requestMatchers("/api/v1/users").authenticated();
                    auth.requestMatchers("/api/v1/user/noAuthUserProfile").permitAll();
                    auth.requestMatchers("/api/v1/auth/**").permitAll();
                    auth.requestMatchers("/actuator/**").hasAnyRole("ADMIN");
                    auth.requestMatchers("/schedule/amount-due").hasAnyRole("USER", "ADMIN");
                    auth.requestMatchers("/api/v1/auth/login/**").permitAll();
                    auth.anyRequest().authenticated();


                })
//                .anonymous(anonymous -> {
//                    anonymous
//                            .principal("anonymousUser")
//                            .authorities("ROLE_ANONYMOUS");
//                })
                .exceptionHandling(exception -> {
                    exception.authenticationEntryPoint(fimAuthenticationEntrypoint);
                    exception.accessDeniedHandler(fimAccessDeniedHandler);
                })
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable);
//                .logout(logout -> {
//                    logout.logoutUrl("/api/v1/logout");
//                    logout.addLogoutHandler(logoutServiceHandler);
//                    logout.logoutSuccessHandler((request, response, authentication) -> {
//                        response.setStatus(HttpServletResponse.SC_OK);
//                    });
//                });


        return httpSecurity.build();

    }

    @Bean
    public CsrfTokenRepository csrfTokenRepository() {
        HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
        repository.setHeaderName("X-CSRF-TOKEN");
        return repository;
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();

    }


    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(List.of("http://localhost:3000"));

        configuration.setAllowCredentials(true);

        Long MAX_AGE = 3600L;

//        configuration.setAllowedHeaders(Arrays.asList("Origin", "Access-Control-Allow-Origin", "Content-Type",
//                "Accept", "Authorization", "X-Requested-With",
//                "Access-Control-Request-Method", "Access-Control-Request-Headers", "Access-Control-Allow-Headers"));

//        configuration.setExposedHeaders(Arrays.asList("Origin", "Content-Type", "Accept",
//                "Access-Control-Allow-Origin", "Access-Control-Allow-Headers", "Access-Control-Allow-Credentials"));
        configuration.setAllowedHeaders(Arrays.asList(
                HttpHeaders.AUTHORIZATION,
                HttpHeaders.CONTENT_TYPE,
                HttpHeaders.ACCEPT,
                "X-XSRF-TOKEN"
        ));

        configuration.setAllowedMethods(Arrays.asList(
                        HttpMethod.GET.name(),
                        HttpMethod.PUT.name(),
                        HttpMethod.POST.name(),
                        HttpMethod.OPTIONS.name(),
                        HttpMethod.DELETE.name()
                )
        );

        configuration.setMaxAge(MAX_AGE);


        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
