package fr.eni.encheres.configuration.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {

        String username = request.getParameter("pseudo");
        System.out.println("Username : " + username);

        if (exception instanceof DisabledException) {
            request.getSession().setAttribute("pseudoInactif", username);
            response.sendRedirect("/reactiver-compte"); // ou rediriger vers une page JSP/Thymeleaf
        } else {
            response.sendRedirect("/login?error");
        }
    }
}

