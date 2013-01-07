package de.htwg.mgse.form.model;

import java.util.LinkedList;
import java.util.List;

public class Form {

	private String id;
	private String name;

	private List<Element> elements = new LinkedList<Element>();

	public Form(String id, String name) {
		this.id = id;
		this.name = name;
	}

	public void addElement(Element e) {
		elements.add(e);
	}

	public List<Element> getElements() {
		return elements;
	}

	public String getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
}
