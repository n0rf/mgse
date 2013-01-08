package de.htwg.mgse.form;

import static de.htwg.mgse.form.dsl.FormBuilder.*;
import static de.htwg.mgse.form.model.ButtonType.*;

import java.util.ArrayList;
import java.util.List;

import de.htwg.mgse.form.generator.HtmlGenerator;
import de.htwg.mgse.form.generator.IGenerator;
import de.htwg.mgse.form.generator.PdfGenerator;
import de.htwg.mgse.form.generator.SwingGenerator;
import de.htwg.mgse.form.model.Form;

public class Main {
	
	public static void main(String[] args) {
		Form addressForm = form("address", "Address")
						.input("fname").label("First Name").setTypeToText().defaultValue("Michael")
						.input("lname").label("Last Name").setTypeToText().defaultValue("Muster")
						.input("street").label("Street").setTypeToText().defaultValue("Musterweg 23")
						.input("zip").label("ZIP Code").setTypeToInt().defaultValue(12345)
						.input("city").label("City").setTypeToText().defaultValue("Musterhausen")
						.input("pass").label("Optional Password").setTypeToPassword().defaultValue("")
						.checkbox("primary").label("Primary address").checked(true)
						.button("reset").label("Reset").type(RESET)
						.button("submit").label("Send").type(SUBMIT)
						.button("clickme").label("Click me!").type(SIMPLE)
					 .generate();
		
		List<IGenerator> generators = new ArrayList<IGenerator>();
		generators.add(new HtmlGenerator());
		generators.add(new SwingGenerator());
		generators.add(new PdfGenerator());
		
		for (IGenerator generator : generators) {
			generator.generate(addressForm, "./generated");
		}
	}
}
