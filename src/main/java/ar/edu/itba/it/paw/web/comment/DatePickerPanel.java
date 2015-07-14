package ar.edu.itba.it.paw.web.comment;

import org.apache.wicket.datetime.DateConverter;
import org.apache.wicket.datetime.PatternDateConverter;
import org.apache.wicket.datetime.markup.html.form.DateTextField;
import org.apache.wicket.extensions.yui.calendar.DatePicker;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.PropertyModel;

import java.util.Date;
import java.util.Locale;

public class DatePickerPanel extends Panel {

    private static final Locale LOCALE_ES = new Locale("es");
    private Locale selectedLocale = LOCALE_ES;
    DateConverter dateConverter = new PatternDateConverter("dd/MM/yyyy", false);
    private Date date;

    public DatePickerPanel(String id) {
        super(id);

        DateTextField fromDateTextField = new DateTextField("date",
                new PropertyModel<Date>(this, "date"), dateConverter) {
            @Override
            public Locale getLocale()
            {
                return selectedLocale;
            }
        };
        add(fromDateTextField);

        DatePicker fromDatePicker = new DatePicker()
        {
            @Override
            protected String getAdditionalJavaScript()
            {
                return "${calendar}.cfg.setProperty(\"navigator\",true,false); ${calendar}.render();";
            }
        };
        

        fromDatePicker.setShowOnFieldClick(true);
        fromDatePicker.setAutoHide(true);
        fromDateTextField.add(fromDatePicker);
    }

    public Date getDate() {
        return this.date;
    }
    
    public String getDateFormatted() {
    	return dateConverter.convertToString(date, selectedLocale);
    }
}
