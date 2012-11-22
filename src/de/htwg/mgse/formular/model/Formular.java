package de.htwg.mgse.formular.model;

import java.util.LinkedList;
import java.util.List;

public class Formular {

	private String id;

	private List<Element> elements = new LinkedList<Element>();

	public Formular(String id) {
		this.id = id;
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

}
