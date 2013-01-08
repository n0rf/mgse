package de.htwg.mgse.form.generator;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
		String name = form.getId();
		name = name.substring(0, 1).toUpperCase() + name.substring(1);

		setupImports();
		setupClassBegin(name, form.getName());

		// key: field name; value: default
		Map<String, String> defaultValuesTf = new HashMap<String, String>();
		Map<String, Boolean> defaultValuesCb = new HashMap<String, Boolean>();

		// buttons used to reset
		List<String> resetButtons = new LinkedList<String>();

		int lineNr = 1;
		int tfNr = 1;
		int btNr = 1;
		int cbNr = 1;
		int lbNr = 1;
		for (Element e : form.getElements()) {
			appendToSB(sb, "JPanel line" + lineNr + " = new JPanel();", 2);

			if (e instanceof Input) {
				appendToSB(sb, "JLabel label" + lbNr + " = new JLabel(\"" + ((Input) e).getLabel() + "\");", 2);
				appendToSB(sb, "label" + lbNr + ".setPreferredSize(new Dimension(150, 25));", 2);
				if (((Input) e).getType() == InputType.PASSWORD) {
					appendToSB(sb, "final JTextField tf" + tfNr + " = new JPasswordField();", 2);
				} else {
					appendToSB(sb,
							"final JTextField tf" + tfNr + " = new JTextField(\"" + ((Input) e).getDefaultValue()
									+ "\");", 2);
				}
				appendToSB(sb, "tf" + tfNr + ".setPreferredSize(new Dimension(200, 25));", 2);

				// add Textfield and defaultvalue to maps
				defaultValuesTf.put("tf" + tfNr, ((Input) e).getDefaultValue());

				appendToSB(sb, "line" + lineNr + ".add(label" + lbNr + ");", 2);
				appendToSB(sb, "line" + lineNr + ".add(tf" + tfNr + ");", 2);
				lbNr++;
				tfNr++;
			} else if (e instanceof Checkbox) {
				appendToSB(sb,
						"final JCheckBox cb" + cbNr + " = new JCheckBox(\"" + ((Checkbox) e).getLabel() + "\");", 2);
				appendToSB(sb, "cb" + cbNr + ".setSelected(" + ((Checkbox) e).isChecked() + ");", 2);
				appendToSB(sb, "line" + lineNr + ".add(cb" + cbNr + ");", 2);

				defaultValuesCb.put("cb" + cbNr, ((Checkbox) e).isChecked());
				cbNr++;
			} else if (e instanceof Button) {
				appendToSB(sb, "final JButton button" + btNr + " = new JButton(\"" + ((Button) e).getLabel() + "\");",
						2);
				appendToSB(sb, "button" + btNr + ".setPreferredSize(new Dimension(200, 25));", 2);
				appendToSB(sb, "line" + lineNr + ".add(button" + btNr + ");", 2);

				if (((Button) e).getType() == ButtonType.RESET) {
					resetButtons.add("button" + btNr);
				}
				btNr++;
			}
			appendToSB(sb, "container.add(line" + lineNr + ");", 2);
			lineNr++;
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

		setupClassEnd();
		try {
			saveToFile(sb.toString(), outputPath + "/" + name + ".java");
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

	private void setupClassBegin(String name, String title) {
		appendToSB(sb, "public class " + name + " extends JFrame {", 0);
		appendToSB(sb, "private static final long serialVersionUID = 1L;", 1);
		appendToSB(sb, "public static void main(String[] args) {", 1);
		appendToSB(sb, "new " + name + "();", 2);
		appendToSB(sb, "}", 1);

		appendToSB(sb, "public " + name + "() {", 1);
		appendToSB(sb, "setTitle(\"" + title + "\");", 2);
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
