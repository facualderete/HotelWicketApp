package ar.edu.itba.it.paw.web.base;

import ar.edu.itba.it.paw.domain.UserRepo;
import ar.edu.itba.it.paw.web.HotelWicketSession;
import ar.edu.itba.it.paw.web.user.LoginPage;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.spring.injection.annot.SpringBean;

public class BasePage extends WebPage{

    private static final long serialVersionUID = 1L;
    @SpringBean
    private UserRepo users;

    @SuppressWarnings("serial")
    public BasePage() {

        HotelWicketSession session = (HotelWicketSession) getSession();

        Link<Void> home = new Link<Void>("home") {
            @Override
            public void onClick() {
                setResponsePage(getApplication().getHomePage());
            }

        };

        Link<Void> register = new Link<Void>("register") {
            private static final long serialVersionUID = 1L;

            @Override
            public void onClick() {
                //setResponsePage(RegisterPage.class);      //TODO: hacer la página de registro!!
            }
        };

        Link<Void> login = new Link<Void>("login") {
            private static final long serialVersionUID = 1L;

            @Override
            public void onClick() {
                setResponsePage(LoginPage.class);
            }
        };

        Link<Void> logout = new Link<Void>("logout") {
            private static final long serialVersionUID = 1L;

            @Override
            public void onClick() {
                ((HotelWicketSession) getSession()).signOut();
                setResponsePage(getApplication().getHomePage());
            }
        };

        Label user = new Label("user", session.getUserEmail());

        add(home);
        add(login);
        add(logout);
        add(user);
        add(register);

        if(session.isSignedIn()) {
            login.setVisible(false);
            register.setVisible(false);
        }else{
            logout.setVisible(false);
            user.setVisible(false);

        }
    }
}