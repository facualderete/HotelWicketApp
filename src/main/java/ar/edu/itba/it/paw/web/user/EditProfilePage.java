package ar.edu.itba.it.paw.web.user;

import ar.edu.itba.it.paw.common.PictureHelper;
import ar.edu.itba.it.paw.domain.EntityModel;
import ar.edu.itba.it.paw.domain.Picture;
import ar.edu.itba.it.paw.domain.User;
import ar.edu.itba.it.paw.domain.UserRepo;
import ar.edu.itba.it.paw.web.HotelWicketSession;
import ar.edu.itba.it.paw.web.base.SecuredPage;
import ar.edu.itba.it.paw.web.feedback.CustomFeedbackPanel;
import org.apache.wicket.markup.html.form.*;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.List;

public class EditProfilePage  extends SecuredPage {

    @SpringBean
    private UserRepo users;

    private String name;
    private String lastname;
    private String description;
    private String password;
    private String password2;
    private List<FileUpload> uploadingPicture;
    private Boolean removePicture = false;

    private IModel<User> userModel = new EntityModel<User>(User.class);

    public EditProfilePage() {

        userModel.setObject(HotelWicketSession.get().getUser());
        name = userModel.getObject().getName();
        lastname = userModel.getObject().getLastname();
        description = userModel.getObject().getDescription();

        add(new CustomFeedbackPanel("feedbackPanel"));

        Form<EditProfilePage> form = new Form<EditProfilePage>(
                "editProfileForm",
                new CompoundPropertyModel<EditProfilePage>(this)) {

            @Override
            protected void onSubmit() {
                if (password != null && password2 != null && !password.equals(password2)) {
                    error(getString("password_nonmatch"));
                }
                else {
                    if (password != null && password2 != null) {
                        userModel.getObject().setPassword(password);
                    }
                    userModel.getObject().setName(name);
                    userModel.getObject().setLast(lastname);
                    userModel.getObject().setDescription(description);

                    if (removePicture) {
                        userModel.getObject().setPicture(null);
                    } else if (uploadingPicture != null && !uploadingPicture.isEmpty()) {
                        Picture profilePicture = new Picture(PictureHelper.getImageBytes(uploadingPicture));
                        userModel.getObject().setPicture(profilePicture);
                    }

                    setResponsePage(new ProfilePage(new PageParameters().set("userEmail", userModel.getObject().getEmail())));
                }
            }
        };

        form.add(new TextField<String>("name").setRequired(true));
        form.add(new TextField<String>("lastname").setRequired(true));
        form.add(new TextArea<String>("description").setRequired(true));
        form.add(new FileUploadField("uploadingPicture"));
        form.add(new PasswordTextField("password").setRequired(false));
        form.add(new PasswordTextField("password2").setRequired(false));
        form.add(new CheckBox("removePicture"));
        form.add(new Button("submit", new ResourceModel("submit")));
        add(form);
    }

    @Override
    protected void onDetach() {
        super.onDetach();
        userModel.detach();
    }
}
