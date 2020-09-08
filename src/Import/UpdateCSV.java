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

public class UpdateCSV {

	public void appendAtFirst(String fileName, String str) throws IOException {

		String defaultPath = "N:\\AAPAYMENTS\\Daily Imports\\New Vision_ VSM\\";
		BufferedReader br=null;
	    BufferedWriter bw=null;
	    
		try {
			// Updates the serial numbers
			File file = new File(defaultPath, fileName + ".csv");
			File file2 = new File(defaultPath, fileName + "1.csv");
			 
			br = new BufferedReader(new InputStreamReader(new FileInputStream(file))) ;
			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file2)));
			String line = null;
			
			for (line = br.readLine(); line != null; line = br.readLine()) {
				bw.write(str + line + "\n");
			}	
			
			// Close files
			 if(br!=null)
		            br.close();
		        if(bw!=null)
		            bw.close();
		        
			// Delete old file.
			if(file.delete()) {
				file2.renameTo(new File(defaultPath + fileName + ".csv"));
			}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.out.println("Chars added at first operation completed!");
		}
	}

	public void filterReport(String fileName) throws IOException {
		
		try {
			if(!Desktop.isDesktopSupported()) {
				System.err.println("Not Supported!");
				return;
			}
			
			Desktop desktop = Desktop.getDesktop();
			
			File file = new File("N:\\AAPAYMENTS\\Daily Imports\\New Vision_ VSM\\TMS Import Script 1.0 ("+ fileName +").xlsm");
			if(file.exists())
				desktop.open(file);
			
			Thread.sleep(5000);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			
			System.out.println("Filter report operation completed!");
		}
		
		
		
	}

}
