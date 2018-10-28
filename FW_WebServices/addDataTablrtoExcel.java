package supportExam.until;

import java.util.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class addDataTablrtoExcel {
	public static void main(String[] args) throws Exception {
		WebDriver driver = null;
		String reference_path="src/test/references/";
		System.setProperty("webdriver.chrome.driver", reference_path + "chromedriver.exe");
        driver = new ChromeDriver();
		driver.get("http://automationpractice.com/index.php");
		driver.manage().window().maximize();
		System.out.println(driver.getTitle() + " - WebPage has been launched");
		
//		String curentLink = driver.getCurrentUrl();
//		writeToExcelIndexCol("getAllLink",1, 1, curentLink);
//		
//		List<WebElement> links = driver.findElements(By.tagName("a"));
//		System.out.println("Total links are " + links.size());
//		for (int i = 0; i < links.size(); i++) {
//			WebElement ele = links.get(i);
//			String url = ele.getAttribute("href");
//			writeToExcelIndexCol("getAllLink",i, 2, url);
//			int result = getResponse(url);
//			String lastResult = String.valueOf(result);
//			writeToExcelIndexCol("getAllLink",i, 3, lastResult);
//		}
//		driver.close();
		JavascriptExecutor js = ((JavascriptExecutor) driver);
		js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
		
		WebElement element = driver.findElement(By.xpath("//*[@id='editorial_block_center']/h1"));
	
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].setAttribute(arguments[1], arguments[2]);", element,"style", 
				"border-style: solid; border-width:3px; border-color: red;");
		
	}
	
	public static int getResponse(String urlString) throws MalformedURLException, IOException {
		int reponseCode = 0;
		try {
			URL url = new URL(urlString);
			HttpURLConnection huc = (HttpURLConnection) url.openConnection();

			huc.setRequestMethod("GET");
			huc.connect();
			reponseCode = huc.getResponseCode();
		} catch (Exception e) {
			e.getMessage();
		}
		return reponseCode;
	}
	
	public static void writeToExcelIndexCol (String SheetName, int row, int col, String data) throws IOException{
		FileInputStream fis;
		File file = new File("E://Workspace2//auto_sharing_su3_vuongdd//src//test//resources//DataIn//ElementsTravel - Copy.xlsx");
		fis = new FileInputStream(file);
		@SuppressWarnings("resource")
		XSSFWorkbook wkb = new XSSFWorkbook(fis);
		XSSFSheet sheet;
		
		if (wkb.getSheet(SheetName) == null) {
			sheet = wkb.createSheet(SheetName);
		} else {
			sheet = wkb.getSheet(SheetName);
		}
		XSSFRow rows = null;
		if (sheet.getRow(row) == null) {
			rows = sheet.createRow(row);
		} else {
			rows = sheet.getRow(row);
		}
		Cell cell = null;
		cell = rows.getCell(col);
		if (cell == null) {
			cell = rows.createCell(col);
		}
		cell.setCellValue(data);
		fis.close();
		FileOutputStream outFile = new FileOutputStream(new File("E://Workspace2//auto_sharing_su3_vuongdd//src//test//resources//DataIn//ElementsTravel - Copy.xlsx"));
		wkb.write(outFile);
		outFile.close();
	}
	
	@SuppressWarnings("null")
	public void getAndInputAllToExcell() throws IOException{
		WebDriver wd = null;
		wd.get("http://www.w3schools.com/html/html_tables.asp");
		
		List<WebElement> intRow = wd.findElements(By.xpath("//*[@id='main']/table/tbody/tr"));
		int countRow = intRow.size();
		List<WebElement> intCol = wd.findElements(By.xpath("//*[@id='main']/table/tbody/tr[1]/th"));
		int countCol = intCol.size();
		
		// ghi fiel Excel
		File file = new File("E://Workspace2//auto_sharing_su3_vuongdd//src//test//resources//DataIn//ElementsTravel - Copy.xlsx");
		FileOutputStream outPut = new FileOutputStream(file);
		@SuppressWarnings("resource")
		XSSFWorkbook wkb = new XSSFWorkbook();
		XSSFSheet sheet = wkb.createSheet("abc");
		
		for (int i = 1; i < countRow; i++) {
			XSSFRow exCellRow = sheet.createRow(i);
			for (int j = 1; j < countCol; j++) {
				if (i == 1) {
					//  Đoạn này là lấy Title từng cột
					// Row = 1 thì lặp theo Col là j để lấy content của thẻ <th>
					WebElement val = wd.findElement(By.xpath("//*[@id='main']/table/tbody/tr[" + i +"]/th[" + j+ "]" ));
					String value = val.getText();
					XSSFCell valueCell = exCellRow.createCell(j);
					valueCell.setCellValue(value);
				}else{
					//trường hợp này i lớn hơn 1
					WebElement val = wd.findElement(By.xpath("//*[@id='main']/table/tbody/tr[" + i +"]/td[" + j+ "]" ));
					String value = val.getText();
					XSSFCell valueCell = exCellRow.createCell(j);
					valueCell.setCellValue(value);
				}
			}
			System.out.println();
		}
		outPut.flush();
		wkb.write(outPut);
		outPut.close();
		
	}
	@SuppressWarnings("deprecation")
	public static void readAndWritedataTableToExcel() throws IOException{
		//driver.get("http://www.w3schools.com/html/html_tables.asp");
		WebDriver driver = null;
		@SuppressWarnings("null")
		List<WebElement> irows = driver.findElements(By.xpath("//*[@id='customers']/tbody/tr"));
		int iRowsCount = irows.size();
		List<WebElement> icols = driver.findElements(By.xpath("//*[@id='customers']/tbody/tr[1]/th"));
		int iColsCount = icols.size();
		System.out.println("Selected web table has " + iRowsCount + " Rows and " + iColsCount + " Columns");
		System.out.println();

		FileOutputStream fos = new FileOutputStream("E://Workspace2//auto_sharing_su3_vuongdd//src//test//resources//DataIn//ElementsTravel - Copy.xlsx");

		XSSFWorkbook wkb = new XSSFWorkbook();
		XSSFSheet sheet1 = wkb.createSheet("DataStorage2");

		for (int i = 1; i <= iRowsCount; i++) {
			 XSSFRow excelRow = sheet1.createRow(i);
			for (int j = 1; j <= iColsCount; j++) {
				if (i == 1) {
					// có thể dùng By.xpath("//*[@id='customers']/tbody/tr[1]/th[" + j + "]")
					WebElement val = driver
							.findElement(By.xpath("//*[@id='customers']/tbody/tr[" + i + "]/th[" + j + "]"));
					String a = val.getText();
					System.out.print(a);

					XSSFCell excelCell = excelRow.createCell(j);
					excelCell.setCellType(XSSFCell.CELL_TYPE_STRING);
					excelCell.setCellValue(a);

					// wkb.write(fos);
				} else {
					WebElement val = driver
							.findElement(By.xpath("//*[@id='customers']/tbody/tr[" + i + "]/td[" + j + "]"));
					String a = val.getText();
					System.out.print(a);

					XSSFCell excelCell = excelRow.createCell(j);
					excelCell.setCellType(XSSFCell.CELL_TYPE_STRING);
					excelCell.setCellValue(a);

					// wkb.write(fos);
				}
			}
			System.out.println();
		}
		fos.flush();
		wkb.write(fos);
		fos.close();
		driver.close();
	} 
	
	public static void readAllFileExcel(String fileName, String sheetName) throws IOException{
		//openFile(fileName, sheetName);
		XSSFSheet sheet = null;
		XSSFRow row;
		//XSSFCell cell;
		@SuppressWarnings("null")
		Iterator<Row> rows = sheet.rowIterator();
		while (rows.hasNext()) {
			row = (XSSFRow) rows.next();
			Iterator<Cell> cells = row.cellIterator();
			while (cells.hasNext()) {
				Cell cell = (Cell) cells.next();
				cell.getStringCellValue().toString();
				System.out.print(cell.toString() + " ||  ");
			}
			 System.out.println();
			 
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}