package com.blog_app.blog_app_apis.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtTokenHelper jwtTokenHelper;
    private static final String[] WHITELIST = {
            "/api/v1/auth/",
            "/v3/api-docs",
            "/v2/api-docs/",
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html"
    };

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String path = request.getRequestURI();

        for (String uri : WHITELIST) {
            if (path.startsWith(uri.replace("**",""))) {
                filterChain.doFilter(request, response);
                return;
            }
        }
        if (path.startsWith("/api/v1/auth/login")) {
            filterChain.doFilter(request, response);
            return;
        }
        // 1. Get token from request
        String requestToken= request.getHeader("Authorization");
        //token is of form Bearer 23456543fgj
        String username=null;
        String token=null;

        if(requestToken!=null && requestToken.startsWith("Bearer ")){

            token=requestToken.substring(7);
            try {
                username = this.jwtTokenHelper.getUsernameFromToken(token);
            }
            catch(IllegalArgumentException e){
                System.out.println("Unable to get Jwt token");
            }
            catch(ExpiredJwtException e){
                System.out.println("Jwt token expired.");
            } catch (MalformedJwtException e) {
                System.out.println("Jwt token is malformed.");
            }
        }else{
            System.out.println("Jwt token does not start with Bearer.");
        }
        //token validation
        if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null){
            UserDetails userDetails=this.userDetailsService.loadUserByUsername(username);
            if(this.jwtTokenHelper.validateToken(token,userDetails)){
                //all good, now do authentication
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }else {
                System.out.println("Invalid jwt token");
            }
        }else{
            System.out.println("Username is null or context is not valid.");
        }
        filterChain.doFilter(request,response);
    }
}
