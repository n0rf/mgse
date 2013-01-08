package de.htwg.mgse.form.generator;

import java.io.FileOutputStream;
import java.io.IOException;

import de.htwg.mgse.form.model.Button;
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
import com.itextpdf.text.pdf.PdfBorderDictionary;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfFormField;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPCellEvent;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.PushbuttonField;
import com.itextpdf.text.pdf.TextField;

public class PdfGenerator implements IGenerator {

	class FieldCell implements PdfPCellEvent{
		
		PdfFormField formField;
		PdfWriter writer;
		int width;
		
		public FieldCell(PdfFormField formField, int width, 
			PdfWriter writer){
			this.formField = formField;
			this.width = width;
			this.writer = writer;
		}

		public void cellLayout(PdfPCell cell, Rectangle rect, PdfContentByte[] canvas) {
			try{
				// delete cell border
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
			}catch(Exception e) {
				System.out.println(e);
			}
		}
	}
	
	@Override
	public boolean generate(Form form, String outputPath) {
		
		Document document = new Document();
		PdfWriter writer = null;
		try {
			writer = PdfWriter.getInstance(document, 
				new FileOutputStream(outputPath + "/" + form.getId() + ".pdf"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		document.open();
		
		PdfPTable table = new PdfPTable(2);
		table.getDefaultCell().setPadding(5f); // Code 1
		table.setHorizontalAlignment(Element.ALIGN_LEFT);
		PdfPCell cell;
		
		for (de.htwg.mgse.form.model.Element e : form.getElements()) {
			if (e instanceof Button){
				generateButton((Button)e, writer, table);
			} else if (e instanceof Checkbox) {
				//html += generateCheckbox((Checkbox)e);
			} else if (e instanceof Input) {
				generateInput((Input)e, writer, table);
			} else {
				return false;
			}
		}

		// force upper case javascript
		writer.addJavaScript(
			"var nameField = this.getField('nameField');" +
			"nameField.setAction('Keystroke'," +
			"'forceUpperCase()');" +
			"" +
			"function forceUpperCase(){" +
			"if(!event.willCommit)event.change = " +
			"event.change.toUpperCase();" +
			"}");
		
		
		// Code 3, add empty row
		table.addCell("");
		table.addCell("");
		
		
		// Code 4, add age TextField
		table.addCell("Age");
		TextField ageComb = new TextField(writer, new Rectangle(0,
			 0, 30, 10), "ageField");
		ageComb.setBorderColor(BaseColor.BLACK);
		ageComb.setBorderWidth(1);
		ageComb.setBorderStyle(PdfBorderDictionary.STYLE_SOLID);
		ageComb.setText("12");
		ageComb.setAlignment(Element.ALIGN_RIGHT);
		ageComb.setMaxCharacterLength(2);
		ageComb.setOptions(TextField.COMB | 
			TextField.DO_NOT_SCROLL);
		cell = new PdfPCell();
		cell.setMinimumHeight(10);
		try {
			cell.setCellEvent(new FieldCell(ageComb.getTextField(), 
				30, writer));
		} catch (IOException e4) {
			// TODO Auto-generated catch block
			e4.printStackTrace();
		} catch (DocumentException e4) {
			// TODO Auto-generated catch block
			e4.printStackTrace();
		}
		table.addCell(cell);
		
		// validate age javascript
		writer.addJavaScript(
			"var ageField = this.getField('ageField');" +
			"ageField.setAction('Validate','checkAge()');" +
			"function checkAge(){" +
			"if(event.value < 12){" +
			"app.alert('Warning! Applicant\\'s age can not" +
			" be younger than 12.');" +
			"event.value = 12;" +
			"}}");		
		
		
		
		// add empty row
		table.addCell("");
		table.addCell("");
		
		
		// Code 5, add age TextField
		table.addCell("Comment");
		TextField comment = new TextField(writer, 
			new Rectangle(0, 0,200,	100), "commentField");
		comment.setBorderColor(BaseColor.BLACK);
		comment.setBorderWidth(1);
		comment.setBorderStyle(PdfBorderDictionary.STYLE_SOLID);
		comment.setText("");
		comment.setOptions(TextField.MULTILINE | 
			TextField.DO_NOT_SCROLL);
		cell = new PdfPCell();
		cell.setMinimumHeight(100);
		try {
			cell.setCellEvent(new FieldCell(comment.getTextField(), 
				200, writer));
		} catch (IOException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		} catch (DocumentException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
		table.addCell(cell);
		
		
		// check comment characters length javascript
		writer.addJavaScript(
			"var commentField = " +
			"this.getField('commentField');" +
			"commentField" +
			".setAction('Keystroke','checkLength()');" +
			"function checkLength(){" +
			"if(!event.willCommit && " +
			"event.value.length > 100){" +
			"app.alert('Warning! Comment can not " +
			"be more than 100 characters.');" +
			"event.change = '';" +
			"}}");			
		
		
		// add empty row
		table.addCell("");
		table.addCell("");
		
		
		// Code 6, add submit button	
		PushbuttonField submitBtn = new PushbuttonField(writer,
				new Rectangle(0, 0, 35, 15),"submitPOST");
		submitBtn.setBackgroundColor(BaseColor.GRAY);
		submitBtn.
			setBorderStyle(PdfBorderDictionary.STYLE_BEVELED);
		submitBtn.setText("POST");
		submitBtn.setOptions(PushbuttonField.
			VISIBLE_BUT_DOES_NOT_PRINT);
		PdfFormField submitField = null;
		try {
			submitField = submitBtn.getField();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (DocumentException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		submitField.setAction(PdfAction
		.createSubmitForm(
		"http://www.geek-tutorials.com/java/itext/submit.php",
		null, PdfAction.SUBMIT_HTML_FORMAT));
		
		cell = new PdfPCell();
		cell.setMinimumHeight(15);
		cell.setCellEvent(new FieldCell(submitField, 35, writer));
		table.addCell(cell);
		
		
		
		// Code 7, add reset button
		PushbuttonField resetBtn = new PushbuttonField(writer,
				new Rectangle(0, 0, 35, 15), "reset");
		resetBtn.setBackgroundColor(BaseColor.GRAY);
		resetBtn.setBorderStyle(
			PdfBorderDictionary.STYLE_BEVELED);
		resetBtn.setText("RESET");
		resetBtn
		.setOptions(
			PushbuttonField.VISIBLE_BUT_DOES_NOT_PRINT);
		PdfFormField resetField = null;
		try {
			resetField = resetBtn.getField();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (DocumentException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		resetField.setAction(PdfAction.createResetForm(null, 0));
		cell = new PdfPCell();
		cell.setMinimumHeight(15);
		cell.setCellEvent(new FieldCell(resetField, 35, writer));
		table.addCell(cell);		
				
		try {
			document.add(table);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		nameField.setText(input.getDefaultValue());
		nameField.setAlignment(Element.ALIGN_LEFT);
		//nameField.setOptions(TextField.REQUIRED);				
		PdfPCell cell = new PdfPCell();
		cell.setMinimumHeight(25);
		try {
			cell.setCellEvent(new FieldCell(nameField.getTextField(), 250, writer));
		} catch (Exception e) {
			e.printStackTrace();
		}
		table.addCell(cell);
	}
	
	private void generateButton(Button button, PdfWriter writer, PdfPTable table) {
		table.addCell("");
		PushbuttonField buttonField = new PushbuttonField(
				writer, new Rectangle(0, 0, 35, 25), button.getId());
		buttonField.setBackgroundColor(BaseColor.GRAY);
		buttonField.setBorderStyle(PdfBorderDictionary.STYLE_BEVELED);
		buttonField.setText(button.getLabel());
		buttonField.setOptions(PushbuttonField.VISIBLE_BUT_DOES_NOT_PRINT);
		PdfFormField resetField = null;
		try {
			resetField = buttonField.getField();
		} catch (Exception e) {
			e.printStackTrace();
		}
		resetField.setAction(PdfAction.createResetForm(null, 0));
		PdfPCell cell = new PdfPCell();
		cell.setMinimumHeight(25);
		cell.setCellEvent(new FieldCell(resetField, 35, writer));
		table.addCell(cell);
	}
}
