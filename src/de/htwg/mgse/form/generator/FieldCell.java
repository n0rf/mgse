package de.htwg.mgse.form.generator;

import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfAnnotation;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfFormField;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPCellEvent;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class FieldCell implements PdfPCellEvent{
	
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