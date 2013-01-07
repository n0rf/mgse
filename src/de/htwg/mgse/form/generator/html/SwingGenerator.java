package de.htwg.mgse.form.generator.html;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import de.htwg.mgse.form.generator.IGenerator;
import de.htwg.mgse.form.model.Button;
import de.htwg.mgse.form.model.ButtonType;
import de.htwg.mgse.form.model.Checkbox;
import de.htwg.mgse.form.model.Element;
import de.htwg.mgse.form.model.Form;
import de.htwg.mgse.form.model.Input;
import de.htwg.mgse.form.model.InputType;

public class SwingGenerator implements IGenerator {

	private StringBuffer sb = new StringBuffer();
	
	@Override
	public boolean generate(Form form, String outputPath) {
		setupImports();
		setupClassBegin(form.getId());

//		JFrame frame = new JFrame("Formular");
		// frame.setBounds(0, 0, 400, 800);
//		JPanel container = new JPanel();
//		container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
//		final Map<String, JTextField> textfields = new HashMap<String, JTextField>();
//		final Map<String, String> defaultValues = new HashMap<String, String>();
//		final Map<String, JCheckBox> checkboxes = new HashMap<String, JCheckBox>();
//		final Map<String, Boolean> defaultValuesCb = new HashMap<String, Boolean>();
		
		// key: field name; value: default
		Map<String, String> defaultValuesTf = new HashMap<String, String>();
		Map<String, Boolean> defaultValuesCb = new HashMap<String, Boolean>();
		
		// buttons used to reset
		List<String> resetButtons = new LinkedList<String>();

		// List<Element> elements = form.getElements();
		// for (int i = 0; i < elements.size(); ++i) {
		// if (elements.get(i) instanceof Button) {
		int lineNr = 1;
		int tfNr = 1;
		int btNr = 1;
		int cbNr = 1;
		int lbNr = 1;
		for (Element e : form.getElements()) {
			appendToSB(sb, "JPanel line" + lineNr + " = new JPanel();", 2);
//			JPanel line = new JPanel();
			if (e instanceof Input) {
				appendToSB(sb, "JLabel label" + lbNr + " = new JLabel(\"" + ((Input) e).getLabel() + "\");", 2);
				appendToSB(sb, "label" + lbNr + ".setPreferredSize(new Dimension(150, 25));", 2);
				if (((Input) e).getType() == InputType.PASSWORD) {
					appendToSB(sb, "final JTextField tf" + tfNr + " = new JPasswordField();", 2);
				} else {
					appendToSB(sb, "final JTextField tf" + tfNr + " = new JTextField(\"" + ((Input) e).getDefaultValue() + "\");", 2);
				}
				appendToSB(sb, "tf" + tfNr + ".setPreferredSize(new Dimension(200, 25));", 2);
				
//				JLabel label = new JLabel(((Input) e).getLabel());
//				label.setPreferredSize(new Dimension(150, 25));
//				JTextField tf = null;
//				if (((Input) e).getType() == InputType.PASSWORD) {
//					tf = new JPasswordField();
//				} else {
//					tf = new JTextField(((Input) e).getDefaultValue());
//				}
//				tf.setPreferredSize(new Dimension(200, 25));

				// add Textfield and defaultvalue to maps
//				textfields.put(((Input) e).getId(), tf);
				defaultValuesTf.put("tf" + tfNr, ((Input) e).getDefaultValue());

				appendToSB(sb, "line" + lineNr + ".add(label" + lbNr + ");", 2);
				appendToSB(sb, "line" + lineNr + ".add(tf" + tfNr + ");", 2);
				lbNr++;
				tfNr++;
//				line.add(label);
//				line.add(tf);
			} else if (e instanceof Checkbox) {
				appendToSB(sb, "final JCheckBox cb" + cbNr + " = new JCheckBox(\"" + ((Checkbox) e).getLabel() + "\");", 2);
				appendToSB(sb, "cb" + cbNr + ".setSelected(" + ((Checkbox) e).isChecked() + ");", 2);
				appendToSB(sb, "line" + lineNr + ".add(cb" + cbNr + ");", 2);
				
//				JCheckBox box = new JCheckBox(((Checkbox) e).getLabel());
//				box.setSelected(((Checkbox) e).isChecked());
//				checkboxes.put(((Checkbox) e).getId(), box);
				defaultValuesCb.put("cb" + cbNr, ((Checkbox) e).isChecked());
				cbNr++;
//				line.add(box);
			} else if (e instanceof Button) {
				appendToSB(sb, "final JButton button" + btNr + " = new JButton(\"" + ((Button) e).getLabel() + "\");", 2);
				appendToSB(sb, "button" + btNr + ".setPreferredSize(new Dimension(200, 25));", 2);
//				JButton button = new JButton(((Button) e).getLabel());
//				button.setPreferredSize(new Dimension(200, 25));
				appendToSB(sb, "line" + lineNr + ".add(button" + btNr + ");", 2);
				
				if (((Button) e).getType() == ButtonType.RESET) {
					resetButtons.add("button" + btNr);
				}
				btNr++;
				
//				line.add(button);
			}
			appendToSB(sb, "container.add(line" + lineNr + ");", 2);
			lineNr++;
//			container.add(line);
		}
		
		for (String button : resetButtons) {
			appendToSB(sb, button + ".addActionListener(new ActionListener() {", 2);
			appendToSB(sb, "@Override", 3);
			appendToSB(sb, "public void actionPerformed(ActionEvent arg0) {", 3);
			for (Iterator<String> iterator = defaultValuesTf.keySet().iterator(); iterator.hasNext();) {
				String tf = iterator.next();
				appendToSB(sb, tf + ".setText(\"" + defaultValuesTf.get(tf) + "\");", 4);
			}
			for (Iterator<String> iterator = defaultValuesCb.keySet().iterator(); iterator.hasNext();) {
				String cb = iterator.next();
				appendToSB(sb, cb + ".setSelected(" + defaultValuesCb.get(cb) + ");", 4);
			}
			appendToSB(sb, "}", 3);
			appendToSB(sb, "});", 2);
		}
		

		// loop for buttons to add action listener
//		for (Iterator<String> iterator = resetButtons.keySet().iterator(); iterator.hasNext();) {
//			String key = iterator.next();
//			resetButtons.get(key).addActionListener(new ActionListener() {
//
//				@Override
//				public void actionPerformed(ActionEvent arg0) {
//					for (Iterator<String> iterator = textfields.keySet().iterator(); iterator.hasNext();) {
//						String id = iterator.next();
//						if (defaultValues.containsKey(id)) {
//							textfields.get(id).setText(defaultValues.get(id));
//						}
//					}
//					for (Iterator<String> iterator = checkboxes.keySet().iterator(); iterator.hasNext();) {
//						String id = iterator.next();
//						if (defaultValuesCb.containsKey(id)) {
//							checkboxes.get(id).setSelected(defaultValuesCb.get(id));
//						}
//					}
//				}
//			});
//		}

//		JScrollPane scrollPane = new JScrollPane(container);
//		frame.add(scrollPane);
//
//		frame.pack();
//		frame.setVisible(true);
		
		setupClassEnd();
		try {
			saveToFile(sb.toString(), outputPath + "/" + form.getId() + ".java");
		} catch (FileNotFoundException e) {
			return false;
		}
		return true;
	}

	private void setupClassEnd() {
		appendToSB(sb, "JScrollPane scrollPane = new JScrollPane(container);", 2);
		appendToSB(sb, "add(scrollPane);", 2);
		appendToSB(sb, "pack();", 2);
		appendToSB(sb, "setVisible(true);", 2);
		appendToSB(sb, "setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);", 2);
		appendToSB(sb, "}", 1);
		appendToSB(sb, "}", 0);
	}

	private void setupClassBegin(String name) {
		appendToSB(sb, "public class " + name + " extends JFrame {", 0);
		appendToSB(sb, "public static void main(String[] args) {", 1);
		appendToSB(sb, "new " + name + "();", 2);
		appendToSB(sb, "}", 1);
		
		appendToSB(sb, "public " + name + "() {", 1);
		//appendToSB(sb, "public address() {", 1);
		//appendToSB(sb, "JFrame frame = new JFrame(\"" + name + " Formular\");", 2);
		appendToSB(sb, "setTitle(\"" + name + " Formular\");", 2);
		appendToSB(sb, "JPanel container = new JPanel();", 2);
		appendToSB(sb, "container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));", 2);
	}

	private void setupImports() {
		appendToSB(sb, "import java.awt.Dimension;", 0);
		appendToSB(sb, "import java.awt.event.ActionEvent;", 0);
		appendToSB(sb, "import java.awt.event.ActionListener;", 0);
		appendToSB(sb, "import javax.swing.BoxLayout;", 0);
		appendToSB(sb, "import javax.swing.JButton; ", 0);
		appendToSB(sb, "import javax.swing.JCheckBox;", 0);
		appendToSB(sb, "import javax.swing.JFrame;", 0);
		appendToSB(sb, "import javax.swing.JLabel;", 0);
		appendToSB(sb, "import javax.swing.JPanel;", 0);
		appendToSB(sb, "import javax.swing.JPasswordField;", 0);
		appendToSB(sb, "import javax.swing.JScrollPane;", 0);
		appendToSB(sb, "import javax.swing.JTextField;", 0);
		appendToSB(sb, "import javax.swing.WindowConstants;", 0);
		sb.append("\n\n");
	}
	
	private void appendToSB(StringBuffer sb, String text, int tabs) {
		for (int i = 0; i < tabs; ++i) {
			sb.append("\t");
		}
		sb.append(text);
		sb.append("\n");
	}

	private void saveToFile(String text, String fileName) throws FileNotFoundException {
		PrintWriter out = new PrintWriter(fileName);
		out.println(text);
		out.close();
	}
}
