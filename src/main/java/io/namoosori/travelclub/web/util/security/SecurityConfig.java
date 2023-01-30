package io.namoosori.travelclub.web.util.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;

import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public BCryptPasswordEncoder encodePassword() {
        return new BCryptPasswordEncoder();
    }
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/**");
        //resources 하위 정적자원들에 대해 보안구성에서 제외한다.
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //HttpSecurity - 특정 http 요청에 대해 웹 기반 보안을 구성할 수 있다.
        //기본적으로 모든 요청에 적용되니만 requestMatcher또는 유사한 방법을 사용하여 제한 할 수 있다.

        //authorizeRequests - RequestMatcher 구현(url 패턴)을 사용하여 HttpServletReqeust를 기반으로 접근을 제한 할 수 있다.
        //antMatchers - 제공된 ant pattern과 일치할때만 호출되도록 HttpSecurity를 호출할 수 있다.
        http.authorizeRequests()
                .antMatchers("/").permitAll()
//                .antMatchers("/club").hasAnyRole("ROLE_MEMBER")
//                .antMatchers("/member").hasAnyRole("ROLE_ADMIN")
//                .anyRequest().authenticated()
                .and().logout().permitAll();
        http.csrf().disable();
    }
}
