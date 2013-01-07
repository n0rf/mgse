package de.htwg.mgse.form.model;

public class Button extends Element {

	private ButtonType type;

	public Button(String id) {
		this.id = id;
	}

	public ButtonType getType() {
		return type;
	}

	public void setType(ButtonType type) {
		this.type = type;
	}
}
