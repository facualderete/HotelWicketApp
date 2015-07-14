package ar.edu.itba.it.paw.web.hotel;

import ar.edu.itba.it.paw.common.ImageResourceReference;
import ar.edu.itba.it.paw.domain.Hotel;
import ar.edu.itba.it.paw.domain.HotelRepo;
import ar.edu.itba.it.paw.domain.Picture;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.LinkedList;
import java.util.List;

public class HotelPhotoSliderPanel extends Panel{

    @SpringBean
    HotelRepo hotelRepo;

    public HotelPhotoSliderPanel(String id, final IModel<Hotel> hotelModel) {
        super(id, hotelModel);

        final IModel<List<Picture>> picturesListModel = new LoadableDetachableModel<List<Picture>>() {
            @Override
            protected List<Picture> load() {
                return new LinkedList<Picture>(hotelModel.getObject().getPictures());
            }
        };

        ListView<Picture> photoSlider = new ListView<Picture>("photoSlider", picturesListModel) {
            @Override
            protected void populateItem(final ListItem<Picture> item) {

                Integer index = item.getIndex();

                Image image = new Image("photo", new ImageResourceReference(item.getModelObject().getPicture(), index.toString()));


                Link setMainLink = new Link("setMainLink") {
                    public void onClick() {
                        hotelModel.getObject().getMainPic().setMain(false);
                        hotelModel.getObject().getPicture(item.getModelObject()).setMain(true);
                        setResponsePage(getPage());
                    }
                };

                Link deleteLink = new Link("deleteLink") {
                    public void onClick() {
                        boolean isMain = item.getModelObject().getMain();
                        hotelModel.getObject().removePicture(item.getModelObject());
                        if (isMain && hotelModel.getObject().getPictures().size() > 0) {
                            hotelModel.getObject().getPictures().get(0).setMain(true);
                        }
                        setResponsePage(getPage());
                    }
                };

                item.add(image);
                item.add(setMainLink);
                item.add(deleteLink);
            }
        };

        add(photoSlider);
    }
}
