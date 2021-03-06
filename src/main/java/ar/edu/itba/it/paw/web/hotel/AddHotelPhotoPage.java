package ar.edu.itba.it.paw.web.hotel;

import ar.edu.itba.it.paw.common.PictureHelper;
import ar.edu.itba.it.paw.domain.EntityModel;
import ar.edu.itba.it.paw.domain.Hotel;
import ar.edu.itba.it.paw.domain.HotelRepo;
import ar.edu.itba.it.paw.domain.Picture;
import ar.edu.itba.it.paw.web.base.SecuredPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.List;

public class AddHotelPhotoPage extends SecuredPage {

    @SpringBean
    HotelRepo hotelRepo;

    private List<FileUpload> uploadingPicture;
    private Boolean isMain;
    private final IModel<Hotel> hotelModel = new EntityModel<Hotel>(Hotel.class);

    public AddHotelPhotoPage(final PageParameters parameters) {

        hotelModel.setObject(hotelRepo.get(parameters.get("hotelId").toInteger()));
        final CheckBox isMain = new CheckBox("isMain", new PropertyModel<Boolean>(this, "isMain"));

        Form<AddHotelPhotoPage> form = new Form<AddHotelPhotoPage>(
                "addHotelPhotoForm",
                new CompoundPropertyModel<AddHotelPhotoPage>(this)) {

            @Override
            protected void onSubmit() {
                if (!uploadingPicture.isEmpty()) {
                    Picture newPicture = new Picture(PictureHelper.getImageBytes(uploadingPicture));
                    newPicture.setMain(isMain.getModelObject());
                    Picture mainPic = hotelModel.getObject().getMainPic();

                    if(mainPic == null){
                        newPicture.setMain(true);
                    } else if (isMain.getModelObject()) {
                        mainPic.setMain(false);
                        newPicture.setMain(true);
                    } else {
                        newPicture.setMain(false);
                    }

                    hotelModel.getObject().addPicture(newPicture);
                }
                hotelRepo.save(hotelModel.getObject());
                setResponsePage(new HotelDetailPage(new PageParameters().set("hotelId", hotelModel.getObject().getId())));
            }
        };

        Link<Void> goBackLink = new Link<Void>("goBackLink") {
            @Override
            public void onClick() {
                setResponsePage(new HotelDetailPage(new PageParameters().set("hotelId", hotelModel.getObject().getId())));
            }
        };

        add(goBackLink);
        add(new Label("hotelNameTitle", getString("title") + " " + getDecoratedHotelName(hotelModel.getObject())));
        form.add(new FileUploadField("uploadingPicture"));
        form.add(isMain);
        form.add(new Button("addPhoto", new ResourceModel("addPhoto")));
        add(form);
    }

    @Override
    protected void onDetach() {
        super.onDetach();
        hotelModel.detach();
    }
}
