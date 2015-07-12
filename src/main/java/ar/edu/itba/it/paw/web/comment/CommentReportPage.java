package ar.edu.itba.it.paw.web.comment;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RadioChoice;
import org.apache.wicket.markup.html.panel.ComponentFeedbackPanel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.joda.time.DateTime;

import ar.edu.itba.it.paw.domain.Comment;
import ar.edu.itba.it.paw.domain.CommentRepo;
import ar.edu.itba.it.paw.web.base.BasePage;

import com.googlecode.wickedcharts.highcharts.options.Axis;
import com.googlecode.wickedcharts.highcharts.options.ChartOptions;
import com.googlecode.wickedcharts.highcharts.options.CssStyle;
import com.googlecode.wickedcharts.highcharts.options.DataLabels;
import com.googlecode.wickedcharts.highcharts.options.HorizontalAlignment;
import com.googlecode.wickedcharts.highcharts.options.Labels;
import com.googlecode.wickedcharts.highcharts.options.Legend;
import com.googlecode.wickedcharts.highcharts.options.Options;
import com.googlecode.wickedcharts.highcharts.options.SeriesType;
import com.googlecode.wickedcharts.highcharts.options.Title;
import com.googlecode.wickedcharts.highcharts.options.Tooltip;
import com.googlecode.wickedcharts.highcharts.options.series.SimpleSeries;
import com.googlecode.wickedcharts.wicket15.highcharts.Chart;

public class CommentReportPage extends BasePage {

	private static final List<String> CHOICES = Arrays.asList(new String[] {
			"Day", "Month", "Year" });
	// private Date fromDate;
	// private Date toDate;
	private String groupBy = "Day";
	private final Options options = new Options();
	// private IModel<List<Comment>> modelComments;
	private Chart chart = new Chart("chart", options);

	@SpringBean
	private CommentRepo comments;

	public CommentReportPage() {
		final RadioChoice<String> radioChoice = new RadioChoice<String>(
				"radioChoice", new PropertyModel<String>(this, "groupBy"),
				CHOICES);
		final DatePickerPanel fromDatePanel = new DatePickerPanel("fromDate");
		final DatePickerPanel toDatePanel = new DatePickerPanel("toDate");

		final Button confirmButton = new Button("confirm") {
			@Override
			public void onSubmit() {
				final String formatFromDate = fromDatePanel.getDateFormatted();
				final String formatToDate = toDatePanel.getDateFormatted();
				Map<String, Integer> data = groupData(formatFromDate,
						formatToDate);
				options.clearSeries();
				options.setChartOptions(new ChartOptions().setType(
						SeriesType.COLUMN).setMargin(
						Arrays.asList(new Integer[] { 50, 50, 100, 80 })));
				options.setTitle(new Title("Report"));
				options.setxAxis(new Axis().setCategories(
						new ArrayList<String>(data.keySet()))
						.setLabels(
								new Labels()
										.setAlign(HorizontalAlignment.RIGHT)
										.setRotation(-45)
										.setStyle(
												new CssStyle().setProperty(
														"fontFamily",
														"Verdana, sans-serif")
														.setProperty(
																"fontSize",
																"13px"))));
				options.setyAxis(new Axis().setTitle(new Title(
						"Amount of comments")));
				options.setTooltip(new Tooltip()
						.setPointFormat("Comments between " + formatFromDate
								+ " and " + formatToDate
								+ ": <b>{point.y:.1f}</b>"));
				options.addSeries(new SimpleSeries()
						.setDataLabels(
								new DataLabels(true)
										.setAlign(HorizontalAlignment.RIGHT)
										.setColor(Color.WHITE)
										.setRotation(-90)
										.setX(4)
										.setY(10)
										.setStyle(
												new CssStyle()
														.setProperty(
																"fontSize",
																"13px")
														.setProperty(
																"fontFamily",
																"Verdana, sans-serif")
														.setProperty(
																"textShadow",
																"0 0 3px black")))
						.setData(new ArrayList<Number>(data.values())));
				options.setLegend(new Legend(false));
				Chart chartNew = new Chart("chart", options);
				chart = (Chart) chart.replaceWith(chartNew);
			};
		};

		final Form<Void> form = new Form<Void>("form");

		form.add(fromDatePanel);
		form.add(toDatePanel);
		form.add(new ComponentFeedbackPanel("fromDate_error", fromDatePanel));
		form.add(new ComponentFeedbackPanel("toDate_error", toDatePanel));
		form.add(radioChoice);
		form.add(confirmButton);
		add(form);
		add(chart);

	}

	private Map<String, Integer> groupData(final String fromDate,
			final String toDate) {
		final List<Comment> commentsList = comments.getWithinRange(fromDate,
				toDate);
		final Map<String, Integer> data = new TreeMap<String, Integer>();
		for (final Comment comment : commentsList) {
			final String key = getKey(comment.getCommentDate());
			if(!data.containsKey(key)) {
				data.put(key, 0);
			}
			int count = data.get(key) + 1;
			data.put(key, count);
		}
		return data;
	}
	
	private String getKey(final DateTime date) {
		String key = "";
		if (groupBy.equals("Day")) {
			key+= date.getYear() + "/" + date.getMonthOfYear() + "/" + date.getDayOfMonth();
		} else if (groupBy.equals("Month")) {
			key+= date.getYear() + "/" + date.getMonthOfYear();
		} else if (groupBy.equals("Year")) {
			key+= date.getYear();
		}
		return key;
	}

}
