package ar.edu.itba.it.paw.web.hotel.form;

import ar.edu.itba.it.paw.domain.Destination;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.IModel;

import java.util.List;

public class DestinationDropDownChoice extends DropDownChoice<Destination> {

    public DestinationDropDownChoice(String id, IModel<Destination> model, IModel<List<Destination>> options) {
        super(id, model, options, new DestinationRenderer());
    }

    public static class DestinationRenderer implements IChoiceRenderer<Destination> {

        @Override
        public Object getDisplayValue(Destination object) {
            return  object.getDestination();
        }

        @Override
        public String getIdValue(Destination object, int index) {
            return new Integer(object.getId()).toString();
        }
    }
}
