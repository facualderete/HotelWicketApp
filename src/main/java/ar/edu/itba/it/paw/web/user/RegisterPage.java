package ar.edu.itba.it.paw.web.user;

import ar.edu.itba.it.paw.common.CaptchaImage;
import ar.edu.itba.it.paw.common.CaptchaValidator;
import ar.edu.itba.it.paw.common.PictureHelper;
import ar.edu.itba.it.paw.domain.Picture;
import ar.edu.itba.it.paw.domain.User;
import ar.edu.itba.it.paw.domain.UserRepo;
import ar.edu.itba.it.paw.web.HotelWicketSession;
import ar.edu.itba.it.paw.web.base.BasePage;
import org.apache.wicket.markup.html.form.*;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.List;

public class RegisterPage extends BasePage {

    @SpringBean
    private UserRepo users;

    private transient String name;
    private transient String lastname;
    private transient String description;
    private transient String email;
    private transient String password;
    private transient String password2;
    private transient List<FileUpload> uploadingPicture;

    private String captchaInput;

    public RegisterPage() {

        add(new FeedbackPanel("feedbackPanel"));

        Form<RegisterPage> form = new Form<RegisterPage>(
                "registerForm",
                new CompoundPropertyModel<RegisterPage>(this)) {

            @Override
            protected void onSubmit() {
                if (users.getByEmail(email) != null) {
                    error(getString("email_already_used"));
                } else if (!password.equals(password2)) {
                    error(getString("password_nonmatch"));
                } else if (checkNotEmptyCaptcha()) {


                } else {
                    User newUser = new User(name, lastname, description, email, password);
                    if (!uploadingPicture.isEmpty()) {
                        Picture profilePicture = new Picture(PictureHelper.getImageBytes(uploadingPicture));
                        newUser.setPicture(profilePicture);
                    }
                    users.save(newUser);
                    HotelWicketSession session = HotelWicketSession.get();
                    session.signIn(email, password, users);
                    continueToOriginalDestination();
                    //TODO: hacer que vaya al perfil de usuario
                    setResponsePage(getApplication().getHomePage());
                }
            }
        };

        final CaptchaImage captchaImage = new CaptchaImage("kaptchaImage");
        captchaImage.setOutputMarkupId(true);
        TextField<String> captchaTF = new TextField<String>("captcha",
                new PropertyModel<String>(this, "captchaInput"));
        captchaTF.add(new CaptchaValidator());

        form.add(new TextField<String>("name").setRequired(true));
        form.add(new TextField<String>("lastname").setRequired(true));
        form.add(new TextArea<String>("description").setRequired(true));
        form.add(new TextField<String>("email").setRequired(true));
        form.add(new FileUploadField("uploadingPicture"));
        form.add(new PasswordTextField("password").setRequired(true));
        form.add(new PasswordTextField("password2").setRequired(true));
        form.add(new Button("register", new ResourceModel("register")));

        form.add(captchaImage);
        form.add(captchaTF.setRequired(true));

        add(form);
    }

    private boolean checkNotEmptyCaptcha() {
        if (captchaInput == null || captchaInput.isEmpty()) {
            error(getString("empty_captcha"));
            return false;
        }
        return true;
    }
}
