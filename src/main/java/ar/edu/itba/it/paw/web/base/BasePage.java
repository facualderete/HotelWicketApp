package ar.edu.itba.it.paw.web.base;

import ar.edu.itba.it.paw.common.PictureHelper;
import ar.edu.itba.it.paw.domain.*;
import ar.edu.itba.it.paw.web.HotelWicketSession;
import ar.edu.itba.it.paw.web.SessionProvider;
import ar.edu.itba.it.paw.web.WicketApplication;
import ar.edu.itba.it.paw.web.hotel.HotelDetailPage;
import ar.edu.itba.it.paw.web.hotel.HotelListPage;
import ar.edu.itba.it.paw.web.user.LoginPage;
import ar.edu.itba.it.paw.web.user.ProfilePage;
import ar.edu.itba.it.paw.web.user.RegisterPage;
import ar.edu.itba.it.paw.web.user.RequestNewPasswordPage;
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

    protected final String BREAKFAST_INCLUDED = getString("breakfast_included");
    protected final String BREAKFAST_NOT_INCLUDED = getString("breakfast_not_included");

    protected final boolean IS_ADMIN = HotelWicketSession.get().isAdmin(users);

    IModel<User> userModel = new EntityModel<User>(User.class);
    IModel<Hotel> hotelModel = new EntityModel<Hotel>(Hotel.class);
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
                User user = users.getByEmail(HotelWicketSession.get().getUserEmail());
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
                    setResponsePage(HotelListPage.class);
                }

            }
        };

        userProfileLink.add(new Label("userEmail", session.getUserEmail()));
        add(userProfileLink);

        Link recoverPasswordLink = new Link("recoverPasswordLink"){
            public void onClick() {
                setResponsePage(RequestNewPasswordPage.class);
            }
        };

        hotelModel.setObject(hotelRepo.getAnyOutstanding());

        Link<Void> outstandingLink = new Link<Void>("outstandingLink") {
            @Override
            public void onClick() {
                setResponsePage(new HotelDetailPage(new PageParameters().set("hotelId",hotelModel.getObject().getId())));
            }
        };

        Image outstandingHotelPicture = new Image("outstandingHotelPicture", PictureHelper.getHotelPicture(hotelModel.getObject(), "1"));
        Label outstandinglabel = new Label("outstandingHotelName", "");
        if(hotelModel.getObject() == null) {
            outstandingLink.setVisible(false);
        } else {
            outstandinglabel = new Label("outstandingHotelName", hotelModel.getObject().getName());
        }
        outstandingLink.add(outstandingHotelPicture);
        outstandingLink.add(outstandinglabel);



        //TODO: internacionalizar las etiquetas de estos botones!!

        add(home);
        add(login);
        add(logout);
        add(register);
        add(recoverPasswordLink);
        add(outstandingLink);

        Image profilePicture = new Image("profilePictureHeader", PictureHelper.getProfilePicture(userModel.getObject(), "1"));
        profilePicture.setVisible(false);
        add(profilePicture);

        if(session.isSignedIn()) {
            login.setVisible(false);
            register.setVisible(false);
            profilePicture.setVisible(true);
            add(profilePicture);
            recoverPasswordLink.setVisible(false);
        }else{
            logout.setVisible(false);
            userProfileLink.setVisible(false);
        }
    }

    public String getDecoratedHotelName(Hotel hotel) {
        int requiredComments = ((WicketApplication) WicketApplication.get()).getConfiguration().getRequiredCommentsDistinction();
        if (hotel.getComments().size() > requiredComments) {
            return "*" + hotel.getName() + "*";
        } else {
            return hotel.getName();
        }
    }

    @Override
    protected void onDetach() {
        super.onDetach();
        userModel.detach();
        hotelModel.detach();
    }
}