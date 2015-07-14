package ar.edu.itba.it.paw.common;

import ar.edu.itba.it.paw.domain.Destination;
import ar.edu.itba.it.paw.domain.Hotel;
import ar.edu.itba.it.paw.domain.User;
import ar.edu.itba.it.paw.web.WicketApplication;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.request.resource.ResourceReference;

import java.util.List;

public class PictureHelper {

    public static byte[] getImageBytes(List<FileUpload> picture) {
        byte[] selectedPicture = null;
        if (picture != null) {
            selectedPicture = picture.get(0).getBytes();
        }
        return selectedPicture;
    }

    public static String getImageExtension(List<FileUpload> picture) {
        String picture_extension = "png";
        if (picture != null) {
            picture_extension = picture
                    .get(0)
                    .getClientFileName()
                    .substring(
                            picture.get(0).getClientFileName()
                                    .lastIndexOf(".")).substring(1);
        }
        return picture_extension;
    }

    public static ResourceReference getProfilePicture(User user, String suffix) {
        if (user != null && user.getPicture() != null) {
            return new ImageResourceReference(user.getPicture().getPicture(), suffix);
        } else {
            return WicketApplication.DEFAULT_PROFILE_IMAGE;
        }
    }

    public static ResourceReference getHotelPicture(Hotel hotel, String suffix) {
        if (hotel != null && hotel.getMainPic() != null) {
            return new ImageResourceReference(hotel.getMainPic().getPicture(), suffix);
        } else {
            return WicketApplication.DEFAULT_HOTEL_IMAGE;
        }
    }

    public static ResourceReference getDestinationPicture(Destination destination, String suffix) {
        if (destination.getPicture() != null) {
            return new ImageResourceReference(destination.getPicture().getPicture(), suffix);
        } else {
            return WicketApplication.DEFAULT_DESTINATION_IMAGE;
        }
    }
}
