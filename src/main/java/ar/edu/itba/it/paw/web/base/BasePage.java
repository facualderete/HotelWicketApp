package ar.edu.itba.it.paw.web.base;

import ar.edu.itba.it.paw.common.PictureHelper;
import ar.edu.itba.it.paw.domain.User;
import ar.edu.itba.it.paw.domain.UserRepo;
import ar.edu.itba.it.paw.web.HotelWicketSession;
import ar.edu.itba.it.paw.web.WicketApplication;
import ar.edu.itba.it.paw.web.user.LoginPage;
import ar.edu.itba.it.paw.web.user.RegisterPage;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.spring.injection.annot.SpringBean;

@SuppressWarnings("serial")
public class BasePage extends WebPage {

    @SpringBean
    private UserRepo users;

    @SuppressWarnings("serial")
    public BasePage() {

        HotelWicketSession session = HotelWicketSession.get();

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
                setResponsePage(RegisterPage.class);
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

        Label userLabel = new Label("user", session.getUserEmail());

        add(home);
        add(login);
        add(logout);
        add(userLabel);
        add(register);

        Image profilePicture = new Image("profilePictureHeader", WicketApplication.DEFAULT_PROFILE_IMAGE);
        profilePicture.setVisible(false);
        add(profilePicture);

        if(session.isSignedIn()) {
            login.setVisible(false);
            register.setVisible(false);
            User user = users.getByEmail(session.getUserEmail());
            profilePicture.setImageResourceReference(PictureHelper.getProfilePicture(user, "1"));
            profilePicture.setVisible(true);
            add(profilePicture);
        }else{
            logout.setVisible(false);
            userLabel.setVisible(false);
        }
    }
}