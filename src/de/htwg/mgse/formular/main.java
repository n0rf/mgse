package de.htwg.mgse.formular;

import static de.htwg.mgse.formular.dsl.FormBuilder.*;
import static de.htwg.mgse.formular.model.InputType.*;
import static de.htwg.mgse.formular.model.ButtonType.*;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

import de.htwg.mgse.formular.model.Formular;

public class Main {
	
	public static void main(String[] args) {
		Formular f = form("address")
						.input("fname").label("First Name").type(TEXT).defaultValue("Michael")
						.input("lname").label("Last Name").type(TEXT).defaultValue("Muster")
						.input("street").label("Street").type(TEXT).defaultValue("Musterweg 23")
						.input("zip").label("ZIP Code").type(UINT).defaultValue("12345")
						.input("city").label("City").type(TEXT).defaultValue("Musterhausen")
						.checkbox("primary").label("Primary address").checked(true)
						.button("reset").label("Reset").type(RESET)
						.button("submit").label("Send").type(SUBMIT)
						.button("clickme").label("Click me!").type(SIMPLE)
					 .generate();
		
		String html = f.toHtml();
		SaveToFile(html, "formular.html");
	}
	
	public static void SaveToFile(String text, String fileName) {
		try {
			PrintWriter out = new PrintWriter(fileName);
			out.println(text);
			out.close();
		} catch (FileNotFoundException e) {}
	}

}
