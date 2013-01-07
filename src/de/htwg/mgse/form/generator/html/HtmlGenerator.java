package de.htwg.mgse.form.generator.html;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

import de.htwg.mgse.form.generator.IGenerator;
import de.htwg.mgse.form.model.Button;
import de.htwg.mgse.form.model.Element;
import de.htwg.mgse.form.model.Form;

public class HtmlGenerator implements IGenerator {

	@Override
	public boolean generate(Form form, String outputPath) {
		String html = "<form name=\"" + form.getId() + "\">\n";
		for (Element e : form.getElements()) {
			//html += e.toHtml();
		}
		html += "</form>\n";
		try {
			saveToFile(html, outputPath + "form.html");
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
		switch (button.getType()) {
			case SUBMIT:
				return "\t<input type=\"submit\" name=\"" + button.getId() + "\" value=\"" + button.getLabel() + "\" /><br />\n";
			case RESET:
				return "\t<input type=\"reset\" name=\"" + button.getId() + "\" value=\"" + button.getLabel() + "\" /><br />\n";
			default:
				return "\t<input type=\"button\" name=\"" + button.getId() + "\" value=\"" + button.getLabel() + "\" /><br />\n";
		}
	}
}
