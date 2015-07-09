package ar.edu.itba.it.paw.web.base;

import ar.edu.itba.it.paw.common.PictureHelper;
import ar.edu.itba.it.paw.domain.*;
import ar.edu.itba.it.paw.web.HomePage;
import ar.edu.itba.it.paw.web.HotelWicketSession;
import ar.edu.itba.it.paw.web.SessionProvider;
import ar.edu.itba.it.paw.web.WicketApplication;
import ar.edu.itba.it.paw.web.user.LoginPage;
import ar.edu.itba.it.paw.web.user.ProfilePage;
import ar.edu.itba.it.paw.web.user.RegisterPage;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

@SuppressWarnings("serial")
public class BasePage extends WebPage {

    @SpringBean
    private UserRepo users;

    @SpringBean
    private HotelRepo hotelRepo;

    @SpringBean
    private CommentRepo commentRepo;

    IModel<User> userModel = new EntityModel<User>(User.class);
    private transient String searchText;
    private StringBuilder values = new StringBuilder();

    @SuppressWarnings("serial")
    public BasePage() {

        HotelWicketSession session = HotelWicketSession.get();
        if (session.isSignedIn()) {
            userModel.setObject(session.getUser());
        }

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
                User user = HotelWicketSession.get().getUser();
                user.resetToken();
                ((WicketApplication) WicketApplication.get()).getCookieService()
                        .removeCookieIfPresent(getRequest(), getResponse(),
                                SessionProvider.REMEMBERME_USER);
                ((WicketApplication) WicketApplication.get()).getCookieService()
                        .removeCookieIfPresent(getRequest(), getResponse(),
                                SessionProvider.REMEMBERME_TOKEN);
                ((HotelWicketSession) getSession()).signOut();
                setResponsePage(getApplication().getHomePage());
            }
        };

        Link userProfileLink = new Link("userProfileLink"){
            public void onClick() {
                if (userModel.getObject() != null) {
                    setResponsePage(new ProfilePage(new PageParameters().set("userEmail", userModel.getObject().getEmail())));
                } else {
                    setResponsePage(new HomePage(new PageParameters()));
                }

            }
        };

        userProfileLink.add(new Label("userEmail", session.getUserEmail()));
        add(userProfileLink);

        add(home);
        add(login);
        add(logout);
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
            userProfileLink.setVisible(false);
        }
    }

    @Override
    protected void onDetach() {
        super.onDetach();
        userModel.detach();
    }
}