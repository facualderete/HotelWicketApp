package ar.edu.itba.it.paw.web;

import ar.edu.itba.it.paw.domain.Hotel;
import ar.edu.itba.it.paw.domain.HotelRepo;
import ar.edu.itba.it.paw.web.base.BasePage;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormSubmitBehavior;
import org.apache.wicket.extensions.ajax.markup.html.autocomplete.AutoCompleteTextField;
import org.apache.wicket.markup.html.basic.MultiLineLabel;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.string.Strings;

import java.util.*;

public class AutoCompletePage extends BasePage {

    @SpringBean
    HotelRepo hotelRepo;

    private StringBuilder values = new StringBuilder();

    /**
     * Constructor
     */
    public AutoCompletePage()
    {
        Form<Void> form = new Form<Void>("form");

        final IModel<String> model = new IModel<String>()
        {
            private String value = null;

            @Override
            public String getObject()
            {
                return value;
            }

            @Override
            public void setObject(String object)
            {
                value = object;

                values.append("\n");
                values.append(value);
            }

            @Override
            public void detach()
            {
            }
        };

        final AutoCompleteTextField<String> field = new AutoCompleteTextField<String>("ac", model)
        {
            @Override
            protected Iterator<String> getChoices(String input)
            {
                if (Strings.isEmpty(input))
                {
                    List<String> emptyList = Collections.emptyList();
                    return emptyList.iterator();
                }

                List<String> choices = new ArrayList<String>(10);

                List<Hotel> hotels = hotelRepo.getAll();

                for (final Hotel h : hotels)
                {
                    final String country = h.getName();

                    if (country.toUpperCase().startsWith(input.toUpperCase()))
                    {
                        choices.add(country);
                        if (choices.size() == 10)
                        {
                            break;
                        }
                    }
                }

                return choices.iterator();
            }
        };
        form.add(field);

        final MultiLineLabel label = new MultiLineLabel("history", new PropertyModel<String>(this,
                "values"));
        label.setOutputMarkupId(true);
        form.add(label);

        field.add(new AjaxFormSubmitBehavior(form, "change")
        {
            @Override
            protected void onSubmit(AjaxRequestTarget target)
            {
                target.add(label);
            }

            @Override
            protected void onError(AjaxRequestTarget target)
            {
            }
        });
        add(form);
    }
}
