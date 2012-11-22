package de.htwg.mgse.formular.model;

public class Input extends Element {

	private InputType type;
	private String defaultValue;

	public Input(String id) {
		this.id = id;
	}

	public InputType getType() {
		return type;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setType(InputType type) {
		this.type = type;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	
	public String toHtml() {
		return	"\t" + label + ":<br />\n" + 
				"\t<input name=\"" + id + "\" value=\"" + defaultValue + "\" type=\"" + type.toString() + "\" /><br />\n";
	}

}
