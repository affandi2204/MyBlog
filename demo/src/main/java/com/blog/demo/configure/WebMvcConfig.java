package com.blog.demo.configure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * WebMvcConfig
 */
@Configuration
public class WebMvcConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic()
            .and()
            .authorizeRequests()
            .antMatchers(HttpMethod.GET, "/**").permitAll()
            .antMatchers(HttpMethod.POST, "/**").permitAll()
            .antMatchers(HttpMethod.PUT, "/**").permitAll()
            .antMatchers(HttpMethod.PATCH, "/**").permitAll()
            .antMatchers(HttpMethod.DELETE, "/**").permitAll()
            .and()
            .csrf().disable();

    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
            .withUser("CobaDEV")
            .password("{noop}CobaDEV")
            .roles("USER");
    }
}