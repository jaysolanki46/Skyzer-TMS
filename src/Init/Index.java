package Init;

import java.io.IOException;

import Import.UpdateCSV;

public class Index {

	public static void main(String[] args) throws IOException {

		String sourcePath = "N:\\AAPAYMENTS\\Daily Imports\\New Vision_ VSM\\";
	    UpdateCSV csv = new UpdateCSV();
	    
	    /* Vision */
	    csv.appendAtFirst(sourcePath, "Vision", "XXX");
		csv.filterReport(sourcePath, "Vision");
		
		/* VSM */
		csv.appendAtFirst(sourcePath, "VSM", "XXX");
		csv.filterReport(sourcePath, "VSM");
	    
		
		/* Code for selenium automation */
		
		/* Move files to specific location */
		String visionPath = "N:\\AAPAYMENTS\\Daily Imports\\New Vision_ VSM\\Vision\\";
		String vsmPath = "N:\\AAPAYMENTS\\Daily Imports\\New Vision_ VSM\\VSM\\";
		
		csv.moveFiles(sourcePath, "Vision", visionPath);
		csv.moveFiles(sourcePath, "VSM", vsmPath);
		
		csv.taskCompletionLogger();
	}
}
