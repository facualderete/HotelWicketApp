package ar.edu.itba.it.paw.web.hotel;

import ar.edu.itba.it.paw.common.PictureHelper;
import ar.edu.itba.it.paw.domain.Destination;
import ar.edu.itba.it.paw.domain.EntityModel;
import ar.edu.itba.it.paw.domain.HotelRepo;
import ar.edu.itba.it.paw.domain.Picture;
import ar.edu.itba.it.paw.web.base.SecuredPage;
import ar.edu.itba.it.paw.web.feedback.CustomFeedbackPanel;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.List;

public class EditDestinationPage extends SecuredPage {

    @SpringBean
    HotelRepo hotelRepo;

    private String details;
    private List<FileUpload> uploadingPicture;
    private Boolean removePicture = false;

    private final IModel<Destination> destinationModel = new EntityModel<Destination>(Destination.class);

    public EditDestinationPage(final PageParameters parameters) {

        destinationModel.setObject(hotelRepo.getDestination(parameters.get("destinationId").toInteger()));
        details = destinationModel.getObject().getDetails();

        add(new CustomFeedbackPanel("feedbackPanel"));

        add(new Label("title", getString("title") + " " + destinationModel.getObject().getDestination()));

        Form<EditDestinationPage> form = new Form<EditDestinationPage>(
                "editProfileForm",
                new CompoundPropertyModel<EditDestinationPage>(this)) {

            @Override
            protected void onSubmit() {
                destinationModel.getObject().setDetails(details);

                if (removePicture) {
                    destinationModel.getObject().setPicture(null);
                } else if (uploadingPicture != null && !uploadingPicture.isEmpty()) {
                    Picture destinationPicture = new Picture(PictureHelper.getImageBytes(uploadingPicture));
                    destinationModel.getObject().setPicture(destinationPicture);
                }

                setResponsePage(new DestinationPage(new PageParameters().set("destinationId", destinationModel.getObject().getId())));
            }
        };

        form.add(new TextArea<String>("details"));
        form.add(new FileUploadField("uploadingPicture"));
        form.add(new CheckBox("removePicture"));
        form.add(new Button("submit", new ResourceModel("submit")));
        add(form);
    }

    @Override
    protected void onDetach() {
        super.onDetach();
        destinationModel.detach();
    }
}
