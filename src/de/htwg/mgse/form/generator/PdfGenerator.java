package de.htwg.mgse.form.generator;

import java.io.FileOutputStream;

import de.htwg.mgse.form.model.Button;
import de.htwg.mgse.form.model.ButtonType;
import de.htwg.mgse.form.model.Checkbox;
import de.htwg.mgse.form.model.Form;
import de.htwg.mgse.form.model.Input;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfAction;
import com.itextpdf.text.pdf.PdfAnnotation;
import com.itextpdf.text.pdf.PdfAppearance;
import com.itextpdf.text.pdf.PdfBorderDictionary;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfFormField;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPCellEvent;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.PushbuttonField;
import com.itextpdf.text.pdf.RadioCheckField;
import com.itextpdf.text.pdf.TextField;

public class PdfGenerator implements IGenerator {

	class FieldCell implements PdfPCellEvent{
		
		PdfFormField formField;
		PdfWriter writer;
		int width;
		
		public FieldCell(PdfFormField formField, int width, PdfWriter writer) {
			this.formField = formField;
			this.width = width;
			this.writer = writer;
		}

		public void cellLayout(PdfPCell cell, Rectangle rect, PdfContentByte[] canvas) {
			try{
				PdfContentByte cb = canvas[PdfPTable
					.LINECANVAS];
				cb.reset();
				
				formField.setWidget(
					new Rectangle(rect.getLeft(), 
						rect.getBottom(),
						rect.getLeft()+width, 
						rect.getTop()),
						PdfAnnotation
						.HIGHLIGHT_NONE);
				
				writer.addAnnotation(formField);
			} catch(Exception e) {
				System.out.println(e);
			}
		}
	}
	
	@Override
	public boolean generate(Form form, String outputPath) {
		
		Document document = new Document();
		PdfWriter writer = null;
		
		try {
			writer = PdfWriter.getInstance(document, new FileOutputStream(outputPath + "/" + form.getId() + ".pdf"));
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		document.open();
		PdfPTable table = new PdfPTable(2);
		table.getDefaultCell().setPadding(5f);
		table.setHorizontalAlignment(Element.ALIGN_LEFT);
		
		for (de.htwg.mgse.form.model.Element e : form.getElements()) {
			if (e instanceof Button){
				generateButton((Button)e, writer, table);
			} else if (e instanceof Checkbox) {
				generateCheckbox((Checkbox)e, writer, table);
			} else if (e instanceof Input) {
				generateInput((Input)e, writer, table);
			} else {
				return false;
			}
		}
	
		try {
			document.add(table);
		} catch (DocumentException e) {
			e.printStackTrace();
			return false;
		}
		
		document.close();
		return true;
	}

	private void generateInput(Input input, PdfWriter writer, PdfPTable table) {
		table.addCell(input.getLabel());
		Rectangle box = new Rectangle(0, 0, 250, 25);
		TextField nameField = new TextField(writer, box, input.getId());
		nameField.setBackgroundColor(BaseColor.WHITE);
		nameField.setBorderColor(BaseColor.BLACK);
		nameField.setBorderWidth(1);
		nameField.setBorderStyle(PdfBorderDictionary.STYLE_SOLID);
		nameField.setAlignment(Element.ALIGN_LEFT);		
		nameField.setDefaultText(input.getDefaultValue());
		nameField.setText(input.getDefaultValue());
		PdfPCell cell = new PdfPCell();
		cell.setMinimumHeight(25);
		try {
			cell.setCellEvent(new FieldCell(nameField.getTextField(), 250, writer));
		} catch (Exception e) {
			e.printStackTrace();
		}
		table.addCell(cell);
	}
	
	private void generateCheckbox(Checkbox checkbox, PdfWriter writer, PdfPTable table) {
		table.addCell(checkbox.getLabel());
		PdfContentByte canvas = writer.getDirectContent();
		PdfAppearance[] onOff = new PdfAppearance[2];
        onOff[0] = canvas.createAppearance(20, 20);
        onOff[0].rectangle(1, 1, 18, 18);
        onOff[0].stroke();
        onOff[1] = canvas.createAppearance(20, 20);
        onOff[1].setRGBColorFill(255, 128, 128);
        onOff[1].rectangle(1, 1, 18, 18);
        onOff[1].fillStroke();
        onOff[1].moveTo(1, 1);
        onOff[1].lineTo(19, 19);
        onOff[1].moveTo(1, 19);
        onOff[1].lineTo(19, 1);
        onOff[1].stroke();
        Rectangle rect = new Rectangle(0, 0, 25, 25);
        RadioCheckField checkField = new RadioCheckField(writer, rect, checkbox.getId(), "On");
        checkField.setChecked(checkbox.isChecked());
        checkField.setCheckType(RadioCheckField.TYPE_CROSS);
        PdfFormField formField = null;
		try {
			formField = checkField.getCheckField();
		} catch (Exception e) {
			e.printStackTrace();
		}
        formField.setAppearance(PdfAnnotation.APPEARANCE_NORMAL, "Off", onOff[0]);
        formField.setAppearance(PdfAnnotation.APPEARANCE_NORMAL, "On", onOff[1]);
        formField.setDefaultValueAsName(checkbox.isChecked() ? "On" : "Off");
        PdfPCell cell = new PdfPCell();
		cell.setMinimumHeight(25);
		try {
			cell.setCellEvent(new FieldCell(formField, 25, writer));
		} catch (Exception e) {
			e.printStackTrace();
		}
		table.addCell(cell);
	}
	
	private void generateButton(Button button, PdfWriter writer, PdfPTable table) {
		table.addCell("");
		PushbuttonField buttonField = new PushbuttonField(writer, new Rectangle(0, 0, 35, 25), button.getId());
		buttonField.setBackgroundColor(BaseColor.GRAY);
		buttonField.setBorderStyle(PdfBorderDictionary.STYLE_BEVELED);
		buttonField.setText(button.getLabel());
		buttonField.setOptions(PushbuttonField.VISIBLE_BUT_DOES_NOT_PRINT);
		PdfFormField formField = null;
		try {
			formField = buttonField.getField();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (button.getType() == ButtonType.RESET) formField.setAction(PdfAction.createResetForm(null, 0));
		PdfPCell cell = new PdfPCell();
		cell.setMinimumHeight(25);
		cell.setCellEvent(new FieldCell(formField, 50, writer));
		table.addCell(cell);
	}
}
