package org.openmrs.module.isanteplusreports.report.renderer;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import org.openmrs.Cohort;
import org.openmrs.module.reporting.dataset.DataSet;
import org.openmrs.module.reporting.dataset.DataSetColumn;
import org.openmrs.module.reporting.dataset.DataSetRow;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.openmrs.module.reporting.report.ReportData;
import org.openmrs.module.reporting.report.ReportRequest;
import org.openmrs.module.reporting.report.renderer.RenderingException;
import org.openmrs.module.reporting.report.renderer.ReportDesignRenderer;

public class IsantePlusReportsOtherHtmlReportRenderer extends ReportDesignRenderer {
	
	/**
	 * @see ReportRenderer#getRenderedContentType(org.openmrs.module.reporting.report.ReportRequest)
	 */
	public String getRenderedContentType(ReportRequest request) {
		return "text/html";
	}
	
	/**
	 * @see ReportRenderer#getFilename(org.openmrs.module.reporting.report.ReportRequest)
	 */
	@Override
	public String getFilename(ReportRequest request) {
		return getFilenameBase(request) + ".html";
	}
	
	/**
	 * @see ReportRenderer#render(ReportData, String, OutputStream)
	 */
	public void render(ReportData results, String argument, OutputStream out) throws IOException, RenderingException {
		
		Writer w = new OutputStreamWriter(out, "UTF-8");
		
		w.write("<html>");
		w.write("<head>");
		w.write("<body>");
		SimpleDateFormat parseFormater = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.getDefault());
		SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
		for (String key : results.getDataSets().keySet()) {
			DataSet dataset = results.getDataSets().get(key);
			List<DataSetColumn> columns = dataset.getMetaData().getColumns();
			List<Parameter> parameter = dataset.getDefinition().getParameters();
			w.write("<h4>" + key + "</h4>");

			for(DataSetColumn col : columns){
				System.out.println("COL = "+col);
			}

			if (dataset != null) {
				w.write("<table id=\"simple-html-parameter-" + key + "\" class=\"display simple-html-parameter\"><tr>");
				for (Parameter param : parameter) {
					if (param != null) {

						System.out.println("Parameter = "+param.getName());
						w.write("<tr>");
						w.write("<td><b>" + param.getName() + " :</b></td>");
						try {
							if (param.getName().equals("startDate") || param.getName().equals("endDate")) {
								w.write("<td>"
								        + formater.format(parseFormater.parse(dataset.getContext()
								                .getParameterValue(param.getName()).toString())) + "</td>");
							} else {
								w.write("<td>" + dataset.getContext().getParameterValue(param.getName()) + "</td>");
							}
						}
						catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						w.write("</tr>");
					}
				}
				w.write("</table><br/>");
			}



			w.write("<table id=\"simple-html-dataset-" + key + "\" class=\"display simple-html-dataset" + key
			        + "\" border=\"1" + key + "\" style=\"min-width:100px\">");
			/*for (DataSetColumn column : columns) {
				w.write("<th>"+column.getName()+"</th>");
			}
			w.write("</tr>");
			*/
			for (DataSetRow row : dataset) {
				
				for (DataSetColumn column : columns) {
					w.write("<tr>");
					w.write("<th>" + column.getName() + "</th>");
					w.write("<td>");
					Object colValue = row.getColumnValue(column.getName());
					if (colValue != null) {
						if (colValue instanceof Cohort) {
							w.write(Integer.toString(((Cohort) colValue).size()));
						} else {
							w.write(colValue.toString());
						}
					}
					w.write("</td>");
					w.write("</tr>");
				}
				
			}
			w.write("</table>");
		}
		w.write("</body>");
		w.write("</head>");
		w.write("</html>");
		w.flush();
	}
}
