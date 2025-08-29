package com.neonets.Book.Security.jwt;

import com.neonets.Book.Security.CustomUserDetails;
import com.neonets.Book.Security.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Service
public class TokenFilter extends OncePerRequestFilter {

    private TokenHelperService jwtUtil;
    private CustomUserDetails customUserDetails;
    private CustomUserDetailsService userDetailsService;

    private Logger logger = LoggerFactory.getLogger(TokenFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        final String authorizationHearder = request.getHeader("Authorization");
        logger.debug("authorization header: {}", authorizationHearder);
        String username = null;
        String jwt = null;
        UserDetails userDetails = null;

        if(authorizationHearder != null && authorizationHearder.startsWith("Bearer "))
        {
            jwt = authorizationHearder.substring(7);
            try
            {
                username = jwtUtil.getUsernameFromToken(jwt);
            }
            catch (Exception e)
            {
                filterChain.doFilter(request,response);
                logger.debug("Jwt validation error ", e);
            }
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null)
        {
            userDetails = this.userDetailsService.loadUserByUsername(username);
        }

        if (jwtUtil.validateTokenExpiration(jwt))
        {
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );

            authenticationToken.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request)
            );

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }

        filterChain.doFilter(request, response);
    }
}
