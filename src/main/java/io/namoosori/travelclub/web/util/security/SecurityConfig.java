package io.namoosori.travelclub.web.util.security;

import io.namoosori.travelclub.web.aggregate.club.vo.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@RequiredArgsConstructor
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
//Controller에서 특정 페이지에 특정 권한이 있는 유저만 접근을 허용할 경우 @PreAuthorize 어노테이션을 사용하는데,
//해당 어노테이션에 대한 설정을 활성화시키는 어노테이션
public class SecurityConfig extends WebSecurityConfigurerAdapter { //2022.11 Deprecated되었으나 이해를 돕기 위해 기존대로 진행

    private final OAuth2UserLoginService oAuth2UserLoginService; //final 선언시 상속 불가.
//    @Override
//    public void configure(WebSecurity webSecurity) throws SpringSecurityException {
//        webSecurity.ignoring().antMatchers("/css/**", "/js/**", "/img/**", "/lib/**");
//    } // 시큐리티 요청에서 제외되는 경로들...
        //
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers("/lib/**", "/css/**", "/js/**", "/img/**");
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration corsConfiguration = new CorsConfiguration();

        corsConfiguration.addAllowedOriginPattern("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().disable() //소셜로그인 쓸 것이므로 세션 포기
                    .authorizeRequests() //url별 권한 권리
                    .antMatchers("/","/css/**","/images/**","/js/**").permitAll()
                    .antMatchers("/admin/**").hasRole(String.valueOf(Role.Admin))
                .and()
                    .cors().configurationSource(corsConfigurationSource())
                .and()
                    .logout()
                        .logoutSuccessUrl("/")
                .and()
                    .oauth2Login()
                        .loginPage("/login")
                        .defaultSuccessUrl("/")
                        .failureUrl("/login")
                        .userInfoEndpoint() //로그인 성공 후 가져올때의 설정들 아래로.
                            .userService(oAuth2UserLoginService);

        super.configure(httpSecurity);

    }



}
