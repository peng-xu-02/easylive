package com.darling.easylive.Config;

import com.darling.easylive.Filter.JwtFilter;
import com.darling.easylive.Pojo.loginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig  {
    @Autowired
    private UserDetailsService userDetailsService;
    /* PasswordEncoder是Secturity默认的密码加密方式，我们重写它，就会使用我们重写的加密方式*/
    @Autowired
    private JwtFilter jwtFilter;
    @Bean
    public PasswordEncoder passwordEncoder(){
        return  new BCryptPasswordEncoder();
    }
    /*认证方法，实现我们自己的一个认证方法*/
    @Bean
    public AuthenticationProvider authenticationProvider(){
        return new AuthenticationProvider() {
            @Override
            public Authentication authenticate(Authentication authentication) throws AuthenticationException {
                if(authentication==null){
                    throw new BadCredentialsException("认证失败");
                }
                String username=authentication.getPrincipal().toString();
                String password=authentication.getCredentials().toString();
                loginUser loginUser=(loginUser) userDetailsService.loadUserByUsername(username);
                if(loginUser==null){
                    throw new BadCredentialsException("用户不存在");
                }
                if(!passwordEncoder().matches(password,loginUser.getPassword())){
                    throw new BadCredentialsException("用户名或者密码错误，认证失败");
                }
                return new UsernamePasswordAuthenticationToken(loginUser,null,null);
            }

            @Override
            public boolean supports(Class<?> authentication) {
                return authentication.equals(UsernamePasswordAuthenticationToken.class);
            }
        };

    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                // 基于 token，不需要 csrf
                .csrf().disable()
                // 基于 token，不需要 session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                // 下面开始设置权限
                .authorizeRequests(authorize -> authorize
                        // 请求放开接口
                        .antMatchers("/druid/**","/favicon.ico",
                                "/user/account/register",
                                "/user/account/login",
                                "/admin/account/login",
                                "/category/getall",
                                "/video/random/visitor",
                                "/video/cumulative/visitor",
                                "/video/getone",
                                "/ws/danmu/**",
                                "/danmu-list/**",
                                "/msg/chat/outline",
                                "/video/play/visitor",
                                "/favorite/get-all/visitor",
                                "/search/**",
                                "/comment/get",
                                "/comment/reply/get-more",
                                "/comment/get-up-like",
                                "/user/info/get-one",
                                "/video/user-works-count",
                                "/video/user-works",
                                "/video/user-love",
                                "/video/user-collect").permitAll()
                        // 允许HTTP OPTIONS请求
                        .antMatchers(HttpMethod.OPTIONS).permitAll()
                        // 其他地址的访问均需验证权限
                        .anyRequest().authenticated()
                )
                // 添加 JWT 过滤器，JWT 过滤器在用户名密码认证过滤器之前
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }


}
