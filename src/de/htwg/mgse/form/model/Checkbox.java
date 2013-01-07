package de.htwg.mgse.form.model;

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
}
