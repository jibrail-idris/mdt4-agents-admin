package com.mt4agents.web.views.excel;

import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.servlet.view.AbstractView;

/**
 * <p>
 * Taken from 
 * https://jira.springsource.org/secure/attachment/19470/AbstractPOIExcelView.java
 * </p>
 * 
 * @author Jibrail Idris
 * 
 */
public abstract class AbstractExcelXView extends AbstractView {

	/** The content type for an Excel response */
	/** The content type for an Excel response */
	private static final String CONTENT_TYPE_XLSX = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	private static final String CONTENT_TYPE_XLS = "application/vnd.ms-excel";

	@Override
	protected boolean generatesDownloadContent() {
		return true;
	}

	/**
	 * Subclasses must implement this method to create an Excel Workbook.
	 * HSSFWorkbook and XSSFWorkbook are both possible formats.
	 */
	protected abstract Workbook createWorkbook();

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Workbook workbook = createWorkbook();

		if (workbook instanceof XSSFWorkbook) {
			setContentType(CONTENT_TYPE_XLSX);
		} else {
			setContentType(CONTENT_TYPE_XLS);
		}

		buildExcelDocument(model, workbook, request, response);

		// Set the content type.
		response.setContentType(getContentType());

		// Should we set the content length here?
		// response.setContentLength(workbook.getBytes().length);

		// Flush byte array to servlet output stream.
		ServletOutputStream out = response.getOutputStream();
		workbook.write(out);
		out.flush();
	}

	/**
	 * Subclasses must implement this method to create an Excel XSSFWorkbook
	 * document, given the model.
	 * 
	 * @param model
	 *            the model Map
	 * @param workbook
	 *            the Excel workbook to complete
	 * @param request
	 *            in case we need locale etc. Shouldn't look at attributes.
	 * @param response
	 *            in case we need to set cookies. Shouldn't write to it.
	 */
	protected abstract void buildExcelDocument(Map<String, Object> model,
			Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception;

	/**
	 * Convenient method to obtain the cell in the given sheet, row and column.
	 * <p>
	 * Creates the row and the cell if they still doesn't already exist. Thus,
	 * the column can be passed as an int, the method making the needed
	 * downcasts.
	 * 
	 * @param sheet
	 *            a sheet object. The first sheet is usually obtained by
	 *            workbook.getSheetAt(0)
	 * @param row
	 *            the row number
	 * @param col
	 *            the column number
	 * @return the HSSFCell
	 */
	protected Cell getCell(Sheet sheet, int row, int col) {
		Row sheetRow = sheet.getRow(row);
		if (sheetRow == null) {
			sheetRow = sheet.createRow(row);
		}
		Cell cell = sheetRow.getCell(col);
		if (cell == null) {
			cell = sheetRow.createCell(col);
		}
		return cell;
	}

	/**
	 * Convenient method to set a String as text content in a cell.
	 * 
	 * @param cell
	 *            the cell in which the text must be put
	 * @param text
	 *            the text to put in the cell
	 */
	protected void setText(Cell cell, String text) {
		cell.setCellType(Cell.CELL_TYPE_STRING);
		cell.setCellValue(text);
	}

}
