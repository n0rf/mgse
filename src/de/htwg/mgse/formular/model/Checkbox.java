package de.htwg.mgse.formular.model;

public class Checkbox extends Element {

	private boolean checked;
	
	public Checkbox(String id) {
		this.id = id;
	}
	
	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public String toHtml() {
		String checkedString = checked ? "checked=\"checked\" " : "";
		return "\t<input type=\"checkbox\" name=\"" + id + "\" " + checkedString + "/> " + label + "<br />\n";
	}

}
