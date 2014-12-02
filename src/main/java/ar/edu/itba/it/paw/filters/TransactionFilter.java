package ar.edu.itba.it.paw.filters;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class TransactionFilter extends OncePerRequestFilter {

    private SessionFactory sessionFactory;

    @Autowired
    public TransactionFilter(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {

            // Starting a database transaction
            sessionFactory.getCurrentSession().beginTransaction();

            // Call the next filter (continue request processing)
            filterChain.doFilter(request, response);

            // Commit the database transaction
            sessionFactory.getCurrentSession().getTransaction().commit();

        } catch (Throwable ex) {
            // Rollback only
            try {
                if (sessionFactory.getCurrentSession().getTransaction().isActive())
                    sessionFactory.getCurrentSession().getTransaction().rollback();
            } catch (Throwable rbEx) {
                rbEx.printStackTrace();
            }
            // Let others handle it...
            throw new ServletException(ex);
        }
    }
}
