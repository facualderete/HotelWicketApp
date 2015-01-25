package ar.edu.itba.it.paw.web.user;

import ar.edu.itba.it.paw.domain.User;
import ar.edu.itba.it.paw.domain.UserRepo;
import ar.edu.itba.it.paw.web.HotelWicketSession;
import ar.edu.itba.it.paw.web.base.BasePage;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

public class RegisterPage extends BasePage {

    private static final long serialVersionUID = 1L;

    @SpringBean
    private UserRepo users;

    private transient String name;
    private transient String lastname;
    private transient String description;
    private transient String email;
    private transient String password;
    private transient String password2;

    public RegisterPage() {


        add(new FeedbackPanel("errorPanel"));
        Form<RegisterPage> form = new Form<RegisterPage>(
                "registrationForm",
                new CompoundPropertyModel<RegisterPage>(this)) {
            private static final long serialVersionUID = 1L;

            @Override
            protected void onSubmit() {
                if (password.equals(password2)) {
                    User newUser = new User(name, lastname, description, email, password);
                    users.save(newUser);
                    HotelWicketSession session = HotelWicketSession.get();
                    session.signIn(name, password);
                    continueToOriginalDestination();
                    setResponsePage(getApplication().getHomePage());
                } else {
                    error(getString("password_nonmatch"));
                }
            }
        };

        form.add(new TextField<String>("name").setRequired(true));
        form.add(new TextField<String>("lastname").setRequired(true));
        form.add(new TextField<String>("description").setRequired(true));
        form.add(new TextField<String>("email").setRequired(true));
        form.add(new PasswordTextField("password").setRequired(true));
        form.add(new PasswordTextField("password2").setRequired(true));
        form.add(new Button("register", new ResourceModel("register")));
        add(form);
    }
}
