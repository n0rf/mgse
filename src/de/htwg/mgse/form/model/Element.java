package de.htwg.mgse.form.model;

public abstract class Element {

	protected String id;
	protected String label;

	public String getId() {
		return id;
	}

	public String getLabel() {
		return label;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setLabel(String label) {
		this.label = label;
	}
}
