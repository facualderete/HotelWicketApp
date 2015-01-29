package ar.edu.itba.it.paw.web.user;

import ar.edu.itba.it.paw.domain.UserRepo;
import ar.edu.itba.it.paw.web.HotelWicketSession;
import ar.edu.itba.it.paw.web.base.BasePage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

public class LoginPage extends BasePage {

    private static final long serialVersionUID = 1L;

    @SpringBean
    private UserRepo users;

    public LoginPage() {

        final TextField<String> emailField = new TextField<String>("email");
        final PasswordTextField passwordField = new PasswordTextField("password");

        if (((HotelWicketSession) getSession()).isSignedIn()) {
            HotelWicketSession session = HotelWicketSession.get();
            setResponsePage(new ProfilePage(new PageParameters().set("username", users.getByEmail(session.getUserEmail()).getEmail())));
        }

        add(new FeedbackPanel("errorPanel"));

        Form<?> form = new Form<Void>("loginForm") {

            @Override
            protected void onSubmit() {
                final String emailValue = emailField.getModelObject();
                final String passwordValue = passwordField.getModelObject();
//                PageParameters pageParameters = new PageParameters();
//                pageParameters.add("username", usernameValue);
//                setResponsePage(SuccessPage.class, pageParameters);

            }
        };

        form.add(emailField);
        form.add(passwordField);
        add(form);
    }

}
