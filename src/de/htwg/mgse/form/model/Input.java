package de.htwg.mgse.form.model;

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
}
