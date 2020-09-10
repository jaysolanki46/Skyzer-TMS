package Init;

import java.io.IOException;

import Import.UpdateCSV;

public class Index {

	static String sourcePath = "N:\\AAPAYMENTS\\Daily Imports\\New Vision_ VSM\\";
	static String visionPath = "N:\\AAPAYMENTS\\Daily Imports\\New Vision_ VSM\\Vision\\";
	static String vsmPath = "N:\\AAPAYMENTS\\Daily Imports\\New Vision_ VSM\\VSM\\";
	
	public static void main(String[] args) throws IOException {

		UpdateCSV csv = new UpdateCSV();
	    
	    /* ----- Vision ----- */
	    csv.appendAtFirst(sourcePath, "Vision", "XXX");
		csv.filterReport(sourcePath, "Vision");
		
		/* Code for selenium automation */
		
		
		/* Move files to specific location */
		csv.moveFiles(sourcePath, "Vision", visionPath);
		
		
		 /* ----- VSM ----- */
		csv.appendAtFirst(sourcePath, "VSM", "XXX");
		csv.filterReport(sourcePath, "VSM");
		
		/* Code for selenium automation */
		
		
		/* Move files to specific location */
		csv.moveFiles(sourcePath, "VSM", vsmPath);
		
		csv.taskCompletionLogger();
	}
}
