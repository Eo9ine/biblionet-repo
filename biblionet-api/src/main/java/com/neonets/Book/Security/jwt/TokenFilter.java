package com.neonets.Book.Security.jwt;

import com.neonets.Book.Security.CustomUserDetails;
import com.neonets.Book.Security.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Service
@AllArgsConstructor
public class TokenFilter extends OncePerRequestFilter {


    private TokenHelperService jwtUtil;
    private CustomUserDetailsService userDetailsService;



    private final Logger logger = LoggerFactory.getLogger(TokenFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        try
        {
            final String jwtToken = extractJwtFromRequest(request);
            if(jwtToken != null)
            {
                final String username = validateTokenAndGetUsernameFromToken(jwtToken);
                final UserDetails userDetails = loadUserDetails(username);
                setAuthenticationObject(userDetails,request);
            }
        }
        catch (Exception e)
        {
            logger.error("Cannot set user authentication");
            SecurityContextHolder.clearContext();
        }

        filterChain.doFilter(request, response);
    }

    private String extractJwtFromRequest(HttpServletRequest request)
    {
        final String BEARER_STRING = "Bearer ";
        String jwtBearer = request.getHeader("Authorization");
        logger.debug("authorization header: {}", jwtBearer);
        if(jwtBearer != null && jwtBearer.startsWith(BEARER_STRING))
        {
            return jwtBearer.substring(7);
        }

        logger.debug("Jwt token not found in request headers: {}", jwtBearer);
        return null;
    }

    private String validateTokenAndGetUsernameFromToken(String token)
    {
        if (jwtUtil.validateTokenExpiration(token))
        {
            try
            {
                String username = jwtUtil.getUsernameFromToken(token);
                logger.debug("username: {}", username);

                return username;
            }
            catch (Exception e)
            {
                logger.debug("Jwt validation error: {}", e.getMessage());
            }
        }
        return null;
    }

    private UserDetails loadUserDetails(String username)
    {
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null)
        {
            return userDetailsService.loadUserByUsername(username);
        }
        return null;
    }

    private void setAuthenticationObject(UserDetails userDetails,HttpServletRequest request)
    {
       try
       {
           UsernamePasswordAuthenticationToken authenticationObject = new UsernamePasswordAuthenticationToken(
                   userDetails,
                   null,
                   userDetails.getAuthorities()
           );

           authenticationObject.setDetails(
                   new WebAuthenticationDetailsSource().buildDetails(request)
           );

           SecurityContextHolder.getContext().setAuthentication(authenticationObject);
       }
       catch (Exception e) {
           logger.error("Something went wrong", e);
       }
    }
}
