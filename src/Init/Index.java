package Init;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import Import.DateRange;
import Import.Email;
import Import.TMSImport;
import Import.UpdateCSV;

public class Index {

	static String sourcePath = "\\N:\\AAPAYMENTS\\Daily Imports\\New Vision_ VSM\\";
	static String visionPath = "\\N:\\AAPAYMENTS\\Daily Imports\\New Vision_ VSM\\Vision\\";
	static String vsmPath = "\\N:\\AAPAYMENTS\\Daily Imports\\New Vision_ VSM\\VSM\\";
	static Email email;
	static Timer t;
	static int k = 0;
	
	public static void main(String[] args) throws IOException, InterruptedException {

		
		TimerTask timertask = new TimerTask() {
			
			@Override
			public void run() {
				
				Calendar now = Calendar.getInstance();
				int hour = now.get(Calendar.HOUR_OF_DAY);
				int minute = now.get(Calendar.MINUTE);
				int second = now.get(Calendar.SECOND);
				
				if((hour == 13 || hour == 16) && (minute == 4 && second == 0)) {
					try {
						process();
					} catch (SecurityException | IOException | InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		
		t = new Timer();
		t.schedule(timertask, 1000, 1000);
	}
	
	private static void success() throws SecurityException, IOException, InterruptedException {
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
		email.success();
	}
	
	private static void fail() throws UnsupportedEncodingException {
		
		File isVisionImported = new File(visionPath, "Vision " + new DateRange().getDateRangeStr() + ".csv");
		File isVSMImported = new File(vsmPath, "VSM " + new DateRange().getDateRangeStr() + ".csv");
		SimpleDateFormat formate = new SimpleDateFormat("a");
		String amPm = formate.format(new Date());
		 
		if(!isVisionImported.exists() && !isVSMImported.exists()) {
			
			if(amPm.equals("am"))
				email.failed("Next TMS import scheduled at 6:00 PM." + "Path:" + isVisionImported.getPath().toString());
			else
				email.failed("Next TMS import scheduled at 10:00 AM." + "Path:" + isVisionImported.getPath().toString());
		} else {
			email.secondUpdateEmail();
		}
	}
	
	private static void process() throws SecurityException, IOException, InterruptedException {
		
		File isVisionAvailable = new File(sourcePath, "Vision.csv");
		File isVSMAvailable = new File(sourcePath, "VSM.csv");
		email = new Email();
		
		if(isVisionAvailable.exists() && isVSMAvailable.exists()) {
			success();
		} else {
			fail();
		}
	}
	
}
