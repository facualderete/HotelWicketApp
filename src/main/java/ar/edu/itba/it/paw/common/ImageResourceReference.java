package ar.edu.itba.it.paw.common;

import org.apache.wicket.request.resource.IResource;
import org.apache.wicket.request.resource.ResourceReference;

import java.util.Random;

@SuppressWarnings("serial")
public class ImageResourceReference extends ResourceReference {

    private byte[] img;

    public ImageResourceReference(byte[] img, String suffix) {
        super(ImageResourceReference.class, "Hotel_wicket_image_"
                + new Random().nextLong() + suffix);
        this.img = img;
    }

    @Override
    public IResource getResource() {
        return new ImageResource(img);
    }

}