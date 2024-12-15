package com.darling.easylive.Filter;

import com.darling.easylive.Conponent;
import com.darling.easylive.Pojo.dto.userDto;
import com.darling.easylive.Pojo.loginUser;
import com.darling.easylive.Utils.JwtUtil;
import com.darling.easylive.Utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private RedisUtil redisUtil;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token=request.getHeader("Authentication");
        //如果没有token直接放行，后面还有过滤器链会进行拦截响应的
        if(StringUtils.isEmpty(token)){
            filterChain.doFilter(request,response);
            return;
        }
        //token=token.substring(7);
        /*
        * 这一步是去校验缓存中是否有token，其中就对token进行了校验。
        * */
        if(!jwtUtil.verifyToken(token)){
            logger.info("token出现问题");
             response.setHeader("message","token已过期");
             response.setStatus(403);
             return;
        }
        String userId=JwtUtil.getSubjectFromToken(token);
        String role=JwtUtil.getClaimFromToken(token,"role");
        String key=Conponent.USER_INFO+role+":"+userId;
        logger.info("key="+key);

        userDto user=redisUtil.getObject(key, userDto.class);
        //TODO 这里应该去查数据库看有没有的，这里就直接让用户重新去登录了
        if(user==null){
               logger.info("用户未登录");
               response.setStatus(403);
               response.setHeader("message","用户未登录");
               return;
        }
        /*
        * 把用户信息封装进securitycontest里面；
        * */
        loginUser loginUser=new loginUser(user);
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=
                new UsernamePasswordAuthenticationToken(loginUser,null,null);
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        filterChain.doFilter(request,response);


    }
}
