package ar.edu.itba.it.paw.web;

import ar.edu.itba.it.paw.web.common.HibernateRequestCycleListener;
import ar.edu.itba.it.paw.web.user.LoginPage;
import org.apache.wicket.Page;
import org.apache.wicket.Session;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HotelWicketApplication extends WebApplication{

    //TODO: acá van los resource reference, que son imagenes básicamente...

    private final SessionFactory sessionFactory;

    @Autowired
    public HotelWicketApplication(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Class<? extends Page> getHomePage() {
        //TODO: cambiar esto por la que sea la pagina principal (5 mejores hoteles?)
        return LoginPage.class;
    }

    @Override
    protected void init() {
        super.init();
        getComponentInstantiationListeners().add(new SpringComponentInjector(this));
        getRequestCycleListeners().add(new HibernateRequestCycleListener(sessionFactory));
    }

    @Override
    public Session newSession(Request request, Response response) {
        return new HotelWicketSession(request);
    }
}
