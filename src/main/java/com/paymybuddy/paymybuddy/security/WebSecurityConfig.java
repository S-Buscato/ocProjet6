package com.paymybuddy.paymybuddy.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableScheduling
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.csrf()
                .disable()
                .exceptionHandling()
                .authenticationEntryPoint(new Http403ForbiddenEntryPoint() {
                })
                .and()
                .authorizeRequests()
                .antMatchers("/api/users/**")
                .permitAll()
                .antMatchers("/api/**")
                .permitAll()
                .anyRequest().permitAll();


//        httpSecurity
//                // we don't need CSRF because our token is invulnerable
//                .csrf()
//                .disable()
//
//                .exceptionHandling()
//                //.authenticationEntryPoint(unauthorizedHandler)
//                .and()
//
//                // don't create session
//                .sessionManagement()
//                //.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//
//                .authorizeRequests()
//
//                // allow anonymous resource requests
//                .antMatchers(HttpMethod.GET, "/", "/*.html", "/favicon.ico", "/**/*.html", "/**/*.css", "/**/*.js")
//                .permitAll()
//                .antMatchers(HttpMethod.OPTIONS, "/api/**")
//                .permitAll()
//                .antMatchers("/auth/**")
//                .permitAll()
//                .antMatchers("/ws/**")
//                .permitAll()
//                .antMatchers("/api/param/message/actifs/LOGIN")
//                .permitAll()
//                .antMatchers("/api/config/*")
//                .permitAll()
//                .antMatchers("/api/**")
//                .authenticated()
//                .anyRequest()
//                .permitAll();
//
//
//        // disable page caching
//        httpSecurity.headers().cacheControl();
    }
}
