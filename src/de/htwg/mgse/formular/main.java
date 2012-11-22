package de.htwg.mgse.formular;

import static de.htwg.mgse.formular.dsl.FormBuilder.*;
import static de.htwg.mgse.formular.model.InputType.*;
import de.htwg.mgse.formular.model.Formular;

public class main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Formular f = form("MyForm").input("MyInputId").label("Label of input 1").type(TEXT).defaultValue("default")
					  .generate();
		
		
	}

}
