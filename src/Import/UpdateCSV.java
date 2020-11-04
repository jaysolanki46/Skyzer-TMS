package Import;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.time.LocalDate;

import Init.TMSLogger;

public class UpdateCSV {
	
	TMSLogger logger;
	
	public UpdateCSV() throws SecurityException, IOException {
		logger = new TMSLogger();
	}
	
	public void appendAtFirst(String path, String fileName, String str) throws IOException {
		
		logger.info(this.getClass().getName(), "Appending word at first...!");
		BufferedReader br=null;
	    BufferedWriter bw=null;
	    
		try {

			/* Open files */
			File file = new File(path, fileName + ".csv");
			File file2 = new File(path, fileName + "1.csv");

			br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file2)));
			String line = null;

			for (line = br.readLine(); line != null; line = br.readLine()) {
				bw.write(str + line + "\n");
			}
			
			/* Close files */
			if (br != null)
				br.close();
			if (bw != null)
				bw.close();

			/* Delete old files */
			if (file.delete()) {
				file2.renameTo(new File(path + fileName + ".csv"));
			}

			logger.info(this.getClass().getName(), "Appending word at first completed...!");

		} catch (Exception e) {
			logger.error(this.getClass().getName(), e.getMessage());
		}
	}

	public void filterReport(String path, String fileName) throws IOException {
		
		try {
			logger.info(this.getClass().getName(), "Filtering product names...!");
			if(!Desktop.isDesktopSupported()) {
				logger.info(this.getClass().getName(), "Desktop not supported...!");
				return;
			}
			
			Desktop desktop = Desktop.getDesktop();
			
			File file = new File(path + "TMS Import Script 1.0 ("+ fileName +").xlsm");
			
			/* TMS Import Script 1.0 */
			if(file.exists())
				desktop.open(file);
			Thread.sleep(10000);
			
			/* Checks whether script is still open */
			
			
			logger.info(this.getClass().getName(), "Filtering product names completed...!");
			
		} catch (Exception e) {
			logger.error(this.getClass().getName(), e.getMessage());
		}
	}
	
	public String getNewFileName(String fileName) {
		
		logger.info(this.getClass().getName(), "Updating file name...");
		String fileNameStr = "";
		LocalDate currentMonthLocalDate = LocalDate.now();
		int currentDay = currentMonthLocalDate.getDayOfMonth();
		int currentMonth = currentMonthLocalDate.getMonthValue();
		int currentYear = currentMonthLocalDate.getYear();
		String day = "";
		String month = "";
		String currentDateStr = "";
		
		if(String.valueOf(currentDay).length() < 2) {
			day = "0" + currentDay;
		} else {
			day = String.valueOf(currentDay);
		}
		
		if(String.valueOf(currentMonth).length() < 2) {
			month = "0" + currentMonth;
		} else {
			month = String.valueOf(currentMonth);
		}
		
		currentDateStr = day + month + currentYear;
		
		LocalDate lastMonthLocalDate = currentMonthLocalDate.minusMonths(1);
		int lastDay = lastMonthLocalDate.getDayOfMonth();
		int lastMonth = lastMonthLocalDate.getMonthValue();
		int lastYear = lastMonthLocalDate.getYear();
		String lastDateStr = "";
		
		if(String.valueOf(lastDay).length() < 2) {
			day = "0" + lastDay;
		} else {
			day = String.valueOf(lastDay);
		}
		
		if(String.valueOf(lastMonth).length() < 2) {
			month = "0" + lastMonth;
		} else {
			month = String.valueOf(lastMonth);
		}
		
		lastDateStr = day + month + lastYear;
		
		fileNameStr = fileName + " " + lastDateStr + " to " + currentDateStr;
		
		logger.info(this.getClass().getName(), "Updating file name completed...!");
		
		return fileNameStr;
	}

	public void moveFiles(String sourcePath, String fileName, String destinationPath) {
		
		try {
			
			logger.info(this.getClass().getName(), "Moving file to destination path...!");
			
			File file = new File(sourcePath, fileName + ".csv");
			file.renameTo(new File(sourcePath + getNewFileName(fileName) + ".csv"));
			
			/* Remove if already exist */
			File tempFile = new File(destinationPath, getNewFileName(fileName) + ".csv");
			
			if(tempFile.exists()) {
				tempFile.delete();
				logger.info(this.getClass().getName(), "Found duplicate file...");
			}

			/* Move file source to destination */
			File newFile = new File(sourcePath, getNewFileName(fileName) + ".csv");
			newFile.renameTo(new File(destinationPath + getNewFileName(fileName) + ".csv"));
			
			logger.info(this.getClass().getName(), "Moving file to destination path completed...!");
		} catch (Exception e) {
			logger.error(this.getClass().getName(), e.getMessage());
		}
		
	}

	public void taskCompletionLogger() {
		logger.info(this.getClass().getName(), "Import operation completed....!");
	}
}
