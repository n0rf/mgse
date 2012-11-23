package de.htwg.mgse.formular;

import static de.htwg.mgse.formular.dsl.FormBuilder.*;
import static de.htwg.mgse.formular.model.ButtonType.*;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

import de.htwg.mgse.formular.model.Formular;

public class Main {
	
	public static void main(String[] args) {
		Formular f = form("address")
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
