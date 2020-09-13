package Init;

import java.io.IOException;

import Import.Email;
import Import.TMSImport;
import Import.UpdateCSV;

public class Index {

	static String sourcePath = "N:\\AAPAYMENTS\\Daily Imports\\New Vision_ VSM\\";
	static String visionPath = "N:\\AAPAYMENTS\\Daily Imports\\New Vision_ VSM\\Vision\\";
	static String vsmPath = "N:\\AAPAYMENTS\\Daily Imports\\New Vision_ VSM\\VSM\\";
	
	public static void main(String[] args) throws IOException, InterruptedException {

		UpdateCSV csv = new UpdateCSV();
	    
	    /* ----- Vision ----- */
	    csv.appendAtFirst(sourcePath, "Vision", "XXX");
		csv.filterReport(sourcePath, "Vision");
		
		/* Code for selenium automation */
		new TMSImport("Vision");
		Thread.sleep(3000);
		
		/* Move files to specific location */
		csv.moveFiles(sourcePath, "Vision", visionPath);
		
		 /* ----- VSM ----- */
		csv.appendAtFirst(sourcePath, "VSM", "XXX");
		csv.filterReport(sourcePath, "VSM");
		
		/* Code for selenium automation */
		new TMSImport("VSM");
		Thread.sleep(3000);
		
		/* ---- Move files to specific location ---- */
		csv.moveFiles(sourcePath, "VSM", vsmPath);
		
		/* ---- End process log ---- */
		csv.taskCompletionLogger(); 
		
		/* ---- Sending confirmation email to Skyzer ---- */
		new Email();
		
		// Test commit
	}
}
