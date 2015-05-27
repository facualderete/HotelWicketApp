package ar.edu.itba.it.paw.common;

import ar.edu.itba.it.paw.web.WicketApplication;
import org.apache.wicket.request.resource.DynamicImageResource;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.apache.wicket.util.io.IOUtils;
import org.apache.wicket.util.resource.ResourceStreamNotFoundException;

import java.io.IOException;
import java.io.InputStream;

public class ImageResource extends DynamicImageResource {
    private static final long serialVersionUID = 1L;
    private byte[] img;

    public ImageResource(byte[] img) {
        if (img == null) {
            try {
                InputStream is = ((PackageResourceReference) WicketApplication.DEFAULT_PROFILE_IMAGE)
                        .getResource().getResourceStream().getInputStream();
                this.img = IOUtils.toByteArray(is);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ResourceStreamNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            this.img = img;
        }
    }

    @Override
    protected byte[] getImageData(Attributes attributes) {
        return img;
    }

    @Override
    public boolean equals(Object that) {
        return that instanceof ImageResource;
    }
}