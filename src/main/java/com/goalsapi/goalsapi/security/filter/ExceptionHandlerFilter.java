package com.goalsapi.goalsapi.security.filter;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.goalsapi.goalsapi.exception.EntityNotFoundException;

// add logging
public class ExceptionHandlerFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (EntityNotFoundException e) {
            setResponse(response, HttpServletResponse.SC_NOT_FOUND, "Username does not exist");
        } catch (JWTVerificationException e) {
            setResponse(response, HttpServletResponse.SC_FORBIDDEN, "JWT not valid");
        } catch (RuntimeException e) {
            setResponse(response, HttpServletResponse.SC_BAD_REQUEST, "Bad Request");
        }
    }

    static HttpServletResponse setResponse(HttpServletResponse response, int status, String message)
            throws IOException {
        try {
            response.setStatus(status);
            response.getWriter().write(message);
            response.getWriter().flush();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            return response;
        } catch (IOException e) {
            throw e;
        }
    }

}
