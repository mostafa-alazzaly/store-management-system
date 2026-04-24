package com.store.management.system.store_management.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private  final UserDetailsService userDetailsService;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint ;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;

    @Autowired
    public SecurityConfig(UserDetailsService userDetailsService,
                          CustomAuthenticationEntryPoint customAuthenticationEntryPoint,
                          CustomAccessDeniedHandler customAccessDeniedHandler) {
        this.userDetailsService = userDetailsService;
        this.customAuthenticationEntryPoint = customAuthenticationEntryPoint;
        this.customAccessDeniedHandler = customAccessDeniedHandler;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)  {

        return http

                    .exceptionHandling(exception->exception
                                .authenticationEntryPoint(customAuthenticationEntryPoint)
                                .accessDeniedHandler(customAccessDeniedHandler))

                    .httpBasic(httpBasic -> httpBasic
                        .authenticationEntryPoint(customAuthenticationEntryPoint))

                    .authorizeHttpRequests(request ->
                            request
                                    // Customer SELF Endpoints
                                    .requestMatchers(HttpMethod.GET,"/api/me/profile").hasRole("CUSTOMER")
                                    .requestMatchers(HttpMethod.PUT,"/api/me/profile").hasRole("CUSTOMER")

                                    .requestMatchers(HttpMethod.GET,"/api/me/products").hasRole("CUSTOMER")
                                    .requestMatchers(HttpMethod.GET,"/api/me/products/**").hasRole("CUSTOMER")

                                    .requestMatchers(HttpMethod.GET,"/api/me/invoices").hasRole("CUSTOMER")
                                    .requestMatchers(HttpMethod.GET,"/api/me/invoices/**").hasRole("CUSTOMER")

                                    .requestMatchers(HttpMethod.GET,"/api/me/payments").hasRole("CUSTOMER")
                                    .requestMatchers(HttpMethod.GET,"/api/me/payments/**").hasRole("CUSTOMER")

                                    // Product
                                    .requestMatchers(HttpMethod.GET,"/api/products","/api/products/**").hasAnyRole("ADMIN","MANAGER","EMPLOYEE","CUSTOMER")
                                    .requestMatchers(HttpMethod.POST,"/api/products").hasAnyRole("ADMIN","MANAGER")
                                    .requestMatchers(HttpMethod.PUT,"/api/products/**").hasAnyRole("ADMIN","MANAGER")
                                    .requestMatchers(HttpMethod.DELETE,"/api/products/**").hasRole("ADMIN")

                                    // Categories
                                    .requestMatchers(HttpMethod.GET,"/api/categories","/api/categories/**").hasAnyRole("ADMIN","MANAGER","EMPLOYEE")
                                    .requestMatchers(HttpMethod.POST,"/api/categories").hasAnyRole("ADMIN","MANAGER")
                                    .requestMatchers(HttpMethod.PUT,"/api/categories/**").hasAnyRole("ADMIN","MANAGER")
                                    .requestMatchers(HttpMethod.DELETE,"/api/categories/**").hasRole("ADMIN")

                                    // Suppliers
                                    .requestMatchers(HttpMethod.GET,"/api/suppliers","/api/suppliers/**").hasAnyRole("ADMIN","MANAGER","EMPLOYEE")
                                    .requestMatchers(HttpMethod.POST,"/api/suppliers").hasAnyRole("ADMIN","MANAGER")
                                    .requestMatchers(HttpMethod.PUT,"/api/suppliers/**").hasAnyRole("ADMIN","MANAGER")
                                    .requestMatchers(HttpMethod.DELETE,"/api/suppliers/**").hasRole("ADMIN")

                                    //Customers
                                    .requestMatchers(HttpMethod.GET,"/api/customers","/api/customers/**").hasAnyRole("ADMIN","MANAGER","EMPLOYEE")
                                    .requestMatchers(HttpMethod.POST,"/api/customers").hasAnyRole("ADMIN","MANAGER")
                                    .requestMatchers(HttpMethod.PATCH,"/api/customers/**").hasAnyRole("ADMIN","MANAGER")
                                    .requestMatchers(HttpMethod.DELETE,"/api/customers/**").hasRole("ADMIN")

                                    // Employees
                                    .requestMatchers(HttpMethod.GET,"/api/employees","/api/employees/**").hasAnyRole("ADMIN","MANAGER")
                                    .requestMatchers(HttpMethod.POST,"/api/employees").hasAnyRole("ADMIN","MANAGER")
                                    .requestMatchers(HttpMethod.PATCH,"/api/employees/**").hasAnyRole("ADMIN","MANAGER")
                                    .requestMatchers(HttpMethod.DELETE,"/api/employees/**").hasRole("ADMIN")

                                    // Invoices
                                    .requestMatchers(HttpMethod.GET,"/api/invoices","/api/invoices/**").hasAnyRole("ADMIN","MANAGER","EMPLOYEE")
                                    .requestMatchers(HttpMethod.POST,"/api/invoices").hasAnyRole("ADMIN","MANAGER","EMPLOYEE")
                                    .requestMatchers(HttpMethod.PUT,"/api/invoices/**").hasAnyRole("ADMIN","MANAGER")
                                    .requestMatchers(HttpMethod.DELETE,"/api/invoices/**").hasRole("ADMIN")

                                    // Invoice Items
                                    .requestMatchers(HttpMethod.GET,"/api/invoice-items","/api/invoice-items/**").hasAnyRole("ADMIN","MANAGER","EMPLOYEE")
                                    .requestMatchers(HttpMethod.POST,"/api/invoice-items").hasAnyRole("ADMIN","MANAGER","EMPLOYEE")
                                    .requestMatchers(HttpMethod.PUT,"/api/invoice-items/**").hasAnyRole("ADMIN","MANAGER")
                                    .requestMatchers(HttpMethod.DELETE,"/api/invoice-items/**").hasRole("ADMIN")

                                    //  Payments
                                    .requestMatchers(HttpMethod.GET,"/api/payments","/api/payments/**").hasAnyRole("ADMIN","MANAGER","EMPLOYEE")
                                    .requestMatchers(HttpMethod.POST,"/api/payments").hasAnyRole("ADMIN","MANAGER","EMPLOYEE")
                                    .requestMatchers(HttpMethod.PUT,"/api/payments/**").hasAnyRole("ADMIN","MANAGER")
                                    .requestMatchers(HttpMethod.DELETE,"/api/payments/**").hasRole("ADMIN")

                                    //  Inventory Transactions
                                    .requestMatchers(HttpMethod.GET,"/api/inventory-transactions","/api/inventory-transactions/**").hasAnyRole("ADMIN","MANAGER","EMPLOYEE")
                                    .requestMatchers(HttpMethod.POST,"/api/inventory-transactions").hasAnyRole("ADMIN","MANAGER","EMPLOYEE")
                                    .requestMatchers(HttpMethod.PUT,"/api/inventory-transactions/**").hasAnyRole("ADMIN","MANAGER")
                                    .requestMatchers(HttpMethod.DELETE,"/api/inventory-transactions/**").hasRole("ADMIN")

                                    //  Inventory Transaction Items
                                    .requestMatchers(HttpMethod.GET,"/api/inventory-transaction-items","/api/inventory-transaction-items/**").hasAnyRole("ADMIN","MANAGER","EMPLOYEE")
                                    .requestMatchers(HttpMethod.POST,"/api/inventory-transaction-items").hasAnyRole("ADMIN","MANAGER","EMPLOYEE")
                                    .requestMatchers(HttpMethod.PUT,"/api/inventory-transaction-items/**").hasAnyRole("ADMIN","MANAGER")
                                    .requestMatchers(HttpMethod.DELETE,"/api/inventory-transaction-items/**").hasRole("ADMIN")

                                    // Roles
                                    .requestMatchers(HttpMethod.GET,"/api/roles","/api/roles/**").hasAnyRole("ADMIN","MANAGER")
                                    .requestMatchers(HttpMethod.POST,"/api/roles").hasRole("ADMIN")
                                    .requestMatchers(HttpMethod.PATCH,"/api/roles/**").hasRole("ADMIN")
                                    .requestMatchers(HttpMethod.DELETE,"/api/roles/**").hasRole("ADMIN")

                                    // Users
                                    .requestMatchers(HttpMethod.GET,"/api/users","/api/users/**").hasAnyRole("ADMIN","MANAGER")
                                    .requestMatchers(HttpMethod.POST,"/api/users").hasRole("ADMIN")
                                    .requestMatchers(HttpMethod.PUT,"/api/users/**").hasRole("ADMIN")
                                    .requestMatchers(HttpMethod.DELETE,"/api/users/**").hasRole("ADMIN")
                                    .anyRequest().authenticated())

                    .httpBasic(Customizer.withDefaults())
                    .csrf(customizer -> customizer.disable())
                    .build();
    }

    @Bean
    public AuthenticationProvider  authenticationProvider() {
        DaoAuthenticationProvider  provider = new DaoAuthenticationProvider(userDetailsService);
        provider.setPasswordEncoder (passwordEncoder());
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }
}
