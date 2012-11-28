package de.htwg.mgse.form.model;

import java.util.LinkedList;
import java.util.List;

public class Form {

	private String id;

	private List<Element> elements = new LinkedList<Element>();

	public Form(String id) {
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
	
	public String toHtml() {
		String html = "<form name=\"" + id + "\">\n";
		for (Element e : elements) {
			html += e.toHtml();
		}
		return html + "</form>\n";
	}

}
