package de.htwg.mgse.form.generator.html;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

import de.htwg.mgse.form.generator.IGenerator;
import de.htwg.mgse.form.model.Button;
import de.htwg.mgse.form.model.Checkbox;
import de.htwg.mgse.form.model.Element;
import de.htwg.mgse.form.model.Form;
import de.htwg.mgse.form.model.Input;

public class HtmlGenerator implements IGenerator {

	@Override
	public boolean generate(Form form, String outputPath) {
		String html = "<html><head><title>" + form.getName() + "</title>\n";
		html += "<style type=\"text/css\">\n";
		html +=	"body { font-family: sans-serif; }\n";
		html +=	".element { padding: 10px; font-size: 10pt; }\n";
		html +=	".label { display: block; }\n";
		html +=	".content { display: block; padding: 10px; }\n";
		html += "</style></head><body>\n";
		html += "<form name=\"" + form.getId() + "\"><fieldset>\n";
		html += "<legend>" + form.getName() + "</legend>\n";
		for (Element e : form.getElements()) {
			if (e instanceof Button){
				html += generateButton((Button)e);
			} else if (e instanceof Checkbox) {
				html += generateCheckbox((Checkbox)e);
			} else if (e instanceof Input) {
				html += generateInput((Input)e);
			} else {
				return false;
			}
		}
		html += "</fieldset></form></body></html>\n";
		try {
			saveToFile(html, outputPath + "/form.html");
		} catch (FileNotFoundException e) {
			return false;
		}
		return true;
	}
	
	private void saveToFile(String text, String fileName) throws FileNotFoundException {
		PrintWriter out = new PrintWriter(fileName);
		out.println(text);
		out.close();
	}
	
	private String generateButton(Button button) {
		String html = "<div class=\"element\">\n\t";
		switch (button.getType()) {
			case SUBMIT:
				html += "<input type=\"submit\" name=\"" + button.getId() + "\" value=\"" + button.getLabel() + "\" />";
				break;
			case RESET:
				html += "<input type=\"reset\" name=\"" + button.getId() + "\" value=\"" + button.getLabel() + "\" />";
				break;
			default:
				html += "<input type=\"button\" name=\"" + button.getId() + "\" value=\"" + button.getLabel() + "\" />";
		}
		return html + "\n</div>\n";
	}
	
	private String generateCheckbox(Checkbox checkbox) {
		String html = "<div class=\"element\">\n";
		html += "\t<span class=\"checkbox\">\n";
		String checkedString = checkbox.isChecked() ? "checked=\"checked\" " : "";
		html += "\t\t<input type=\"checkbox\" name=\"" + checkbox.getId() + "\" " + checkedString + "/>\n";
		html += "\t</span>\n";
		html += "\t<span class=\"checkboxlabel\">" + checkbox.getLabel() + "</span>\n";
		return html + "</div>\n";
	}
	
	private String generateInput(Input input) {
		String html = "<div class=\"element\">\n";
		html += "\t<span class=\"label\">" + input.getLabel() + ":</span>\n";
		html += "\t<span class=\"content\">\n\t\t";
		String intCheck = "onkeypress=\"return (function(e){var c = (e.which) ? e.which : event.keyCode; return !(c != 0 && c != 47 && c != 37 && c != 39 && c > 31 && (c < 48 || c > 57)); })(event)\"";
		switch (input.getType()) {
			case PASSWORD:
				html += "<input name=\"" + input.getId() + "\" value=\"" + input.getDefaultValue() + "\" type=\"password\" />";
				break;
			case INT:
				html += "<input name=\"" + input.getId() + "\" value=\"" + input.getDefaultValue() + "\" type=\"text\" " + intCheck + " />";
				break;
			default:
				html += "<input name=\"" + input.getId() + "\" value=\"" + input.getDefaultValue() + "\" type=\"text\" />";
		}
		html += "\n\t</span>\n";
		return html + "</div>\n";
	}
}
