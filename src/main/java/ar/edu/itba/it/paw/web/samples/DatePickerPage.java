package ar.edu.itba.it.paw.web.samples;

import ar.edu.itba.it.paw.web.base.BasePage;
import org.apache.wicket.Session;
import org.apache.wicket.datetime.StyleDateConverter;
import org.apache.wicket.datetime.markup.html.form.DateTextField;
import org.apache.wicket.extensions.yui.calendar.DatePicker;
import org.apache.wicket.extensions.yui.calendar.TimeField;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.extensions.yui.calendar.DateTimeField;

import java.util.*;

public class DatePickerPage extends BasePage {
    private final class LocaleChoiceRenderer extends ChoiceRenderer<Locale>
    {
        /**
         * Constructor.
         */
        public LocaleChoiceRenderer()
        {
        }

        /**
         * @see org.apache.wicket.markup.html.form.IChoiceRenderer#getDisplayValue(Object)
         */
        @Override
        public Object getDisplayValue(Locale locale)
        {
            String enName = locale.getDisplayName(LOCALE_EN);
            String localizedName = locale.getDisplayName(selectedLocale);
            return localizedName + (!enName.equals(localizedName) ? (" (" + enName + ")") : "");
        }
    }

    /**
     * Dropdown with Locales.
     */
    private final class LocaleDropDownChoice extends DropDownChoice<Locale>
    {
        /**
         * Construct.
         *
         * @param id
         *            component id
         */
        public LocaleDropDownChoice(String id)
        {
            super(id);
            // sort locales on strings of selected locale
            setChoices(new AbstractReadOnlyModel<List<? extends Locale>>()
            {
                @Override
                public List<Locale> getObject()
                {
                    List<Locale> locales = new ArrayList<Locale>(LOCALES);
                    Collections.sort(locales, new Comparator<Locale>() {
                        public int compare(Locale o1, Locale o2) {
                            return o1.getDisplayName(selectedLocale).compareTo(
                                    o2.getDisplayName(selectedLocale));
                        }
                    });
                    return locales;
                }
            });
            setChoiceRenderer(new LocaleChoiceRenderer());
            setDefaultModel(new PropertyModel<Locale>(DatePickerPage.this, "selectedLocale"));
        }

        /**
         * @see org.apache.wicket.markup.html.form.DropDownChoice#onSelectionChanged(java.lang.Object)
         */
        @Override
        public void onSelectionChanged(Locale newSelection)
        {
        }

        /**
         * @see org.apache.wicket.markup.html.form.DropDownChoice#wantOnSelectionChangedNotifications()
         */
        @Override
        protected boolean wantOnSelectionChangedNotifications()
        {
            return true;
        }
    }

    private static final Locale LOCALE_EN = new Locale("en");

    private static final List<Locale> LOCALES;
    static
    {
        LOCALES = Arrays.asList(Locale.getAvailableLocales());
    }

    /** the backing object for DateTextField demo */
    private final Date date = new Date();

    /** the backing object for DateTimeField demo */
    private final Date date2 = new Date();

    /** the backing object for TimeField demo */
    private final Date time = new Date();

    private Locale selectedLocale = LOCALE_EN;

    /**
     * Constructor
     */
    public DatePickerPage()
    {
        selectedLocale = Session.get().getLocale();
        Form<?> localeForm = new Form<Void>("localeForm");
        localeForm.add(new LocaleDropDownChoice("localeSelect"));
        localeForm.add(new Link("localeUSLink")
        {
            @Override
            public void onClick()
            {
                selectedLocale = LOCALE_EN;
            }
        });
        add(localeForm);
        DateTextField dateTextField = new DateTextField("dateTextField", new PropertyModel<Date>(
                this, "date"), new StyleDateConverter("S-", true))
        {
            @Override
            public Locale getLocale()
            {
                return selectedLocale;
            }
        };
        Form<?> form = new Form<Void>("form")
        {
            @Override
            protected void onSubmit()
            {
                info("set date to " + date);
            }
        };
        add(form);
        form.add(dateTextField);

        DatePicker datePicker = new DatePicker()
        {
            @Override
            protected String getAdditionalJavaScript()
            {
                return "${calendar}.cfg.setProperty(\"navigator\",true,false); ${calendar}.render();";
            }
        };
        datePicker.setShowOnFieldClick(true);
        datePicker.setAutoHide(true);
        dateTextField.add(datePicker);
        add(new FeedbackPanel("feedback"));

        Form<?> form2 = new Form<Void>("form2")
        {
            @Override
            protected void onSubmit()
            {
                info("set date2 to " + date2);
            }
        };
        add(form2);
        form2.add(new DateTimeField("dateTimeField", new PropertyModel<Date>(this, "date2")));


        Form<?> form3 = new Form<Void>("form3")
        {
            @Override
            protected void onSubmit()
            {
                info("set time to " + time);
            }
        };
        add(form3);
        form3.add(new TimeField("timeField", new PropertyModel<Date>(this, "time")));
    }

    /**
     * @return the selected locale
     */
    public final Locale getSelectedLocale()
    {
        return selectedLocale;
    }

    /**
     * @param selectedLocale
     */
    public final void setSelectedLocale(Locale selectedLocale)
    {
        this.selectedLocale = selectedLocale;
    }
}
