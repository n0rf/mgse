package de.htwg.mgse.form.generator;

import de.htwg.mgse.form.model.Form;

public interface IGenerator {
	public boolean generate(Form form, String outputPath);
}
