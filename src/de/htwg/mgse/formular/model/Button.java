package de.htwg.mgse.formular.model;

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
	
	public String toHtml() {
		switch (type) {
		case SUBMIT:
			return "\t<input type=\"submit\" name=\"" + id + "\" value=\"" + label + "\" /><br />\n";
		case RESET:
			return "\t<input type=\"reset\" name=\"" + id + "\" value=\"" + label + "\" /><br />\n";
		default:
			return "\t<input type=\"button\" name=\"" + id + "\" value=\"" + label + "\" /><br />\n";
		}
	}
}
