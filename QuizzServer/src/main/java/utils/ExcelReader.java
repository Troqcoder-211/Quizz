package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public abstract class ExcelReader {

	public <T> List<T> readExcel(String excelFilePath) throws IOException {
		List<T> listData = new ArrayList<T>();
		InputStream inputStream = new FileInputStream(new File(excelFilePath));
		Workbook workbook = getWorkbook(inputStream, excelFilePath);
		Sheet sheet = workbook.getSheetAt(0);
		for (Row row : sheet) {
			if (row.getRowNum() == 0)
				continue; // Ignore header
			listData.add(getData(row));
		}
		workbook.close();
		inputStream.close();
		return listData;
	}

	public abstract <T> T getData(Row row);

	private Workbook getWorkbook(InputStream inputStream, String excelFilePath) throws IOException {
		Workbook workbook = null;
		if (excelFilePath.endsWith("xlsx")) {
			workbook = new XSSFWorkbook(inputStream);
		} else if (excelFilePath.endsWith("xls")) {
			workbook = new HSSFWorkbook(inputStream);
		} else {
			throw new IllegalArgumentException("The specified file is not Excel file");
		}
		return workbook;
	}

	public Object getCellValue(Cell cell) {
		CellType cellType = cell.getCellType();
		Object cellValue = null;
		switch (cellType) {
			case BOOLEAN:
				cellValue = cell.getBooleanCellValue();
				break;
			case NUMERIC:
				cellValue = cell.getNumericCellValue();
				break;
			case STRING:
				cellValue = cell.getStringCellValue();
				break;
			case FORMULA:
				// Workbook workbook = cell.getSheet().getWorkbook();
				// FormulaEvaluator evaluator =
				// workbook.getCreationHelper().createFormulaEvaluator();
				// cellValue = evaluator.evaluate(cell).getNumberValue();
				// break;
			case _NONE:
			case BLANK:
			case ERROR:
				break;
			default:
				break;
		}
		return cellValue;
	}

}
