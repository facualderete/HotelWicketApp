package ar.edu.itba.it.paw.filters;

import ar.edu.itba.it.paw.helpers.SendMailHelper;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.mail.MessagingException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

@Component
public class ErrorFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (Exception e) {

            String server = request.getServerName();
            Integer port = request.getServerPort();
            try {
                StringWriter sw = new StringWriter();
                e.printStackTrace(new PrintWriter(sw));
                String exceptionAsString = sw.toString();

                SendMailHelper sendMailHelper = new SendMailHelper();
                sendMailHelper.sendErrorReport(server + port.toString(), exceptionAsString);
            } catch (MessagingException e1) {
                e1.printStackTrace();
            }
            request.getContextPath();

            request.getRequestDispatcher("/WEB-INF/jsp/internalError.jsp").forward(
                    request, response);
        }
    }
}

