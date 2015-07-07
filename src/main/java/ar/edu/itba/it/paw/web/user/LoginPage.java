package ar.edu.itba.it.paw.web.user;

import ar.edu.itba.it.paw.common.CookieService;
import ar.edu.itba.it.paw.domain.User;
import ar.edu.itba.it.paw.domain.UserRepo;
import ar.edu.itba.it.paw.web.HotelWicketSession;
import ar.edu.itba.it.paw.web.SessionProvider;
import ar.edu.itba.it.paw.web.WicketApplication;
import ar.edu.itba.it.paw.web.base.BasePage;
import ar.edu.itba.it.paw.web.feedback.CustomFeedbackPanel;
import org.apache.wicket.markup.html.form.*;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

public class LoginPage extends BasePage {

    @SpringBean
    private UserRepo users;

    private String email;
    private String password;
    private boolean rememberMe;

    public LoginPage() {

        add(new CustomFeedbackPanel("feedbackPanel"));

        if (((HotelWicketSession) getSession()).isSignedIn()) {
            HotelWicketSession session = HotelWicketSession.get();
            setResponsePage(new ProfilePage(new PageParameters().set("username", users.getByEmail(session.getUserEmail()).getEmail())));
        }

        Form<LoginPage> form = new Form<LoginPage>(
                "loginForm",
                new CompoundPropertyModel<LoginPage>(this)) {

            @Override
            protected void onSubmit() {
                User user = users.getByEmail(email);
                if (user != null && user.getPassword().equals(password)) {
                    HotelWicketSession session = HotelWicketSession.get();
                    session.signIn(user.getEmail(), user.getPassword(), users);
                    if (rememberMe) {
                        String token = String.valueOf(System.currentTimeMillis());
                        user.setToken(token);
                        CookieService cookieService = ((WicketApplication) WicketApplication.get()).getCookieService();
                        cookieService.saveCookie(getResponse(), SessionProvider.REMEMBERME_USER, user.getEmail(), 30);
                        cookieService.saveCookie(getResponse(), SessionProvider.REMEMBERME_TOKEN, token, 30);
                    }
                    setResponsePage(new ProfilePage(new PageParameters().set("userEmail", user.getEmail())));
                } else {
                    error(getString("invalidCredentials"));
                }
            }
        };

        form.add(new CheckBox("rememberMe", new PropertyModel<Boolean>(this,
                "rememberMe")));
        form.add(new TextField<String>("email").setRequired(true));
        form.add(new PasswordTextField("password").setRequired(true));
        form.add(new Button("login", new ResourceModel("login")));
        add(form);
    }

}
