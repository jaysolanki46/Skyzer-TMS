package Init;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class TMSLogger {

	static final Logger logger = Logger.getLogger("TMS logs");
	FileHandler fh;
	
	public TMSLogger() throws SecurityException, IOException {
		
		fh = new FileHandler(System.getProperty("user.dir") + "\\logs\\Skyzer-TMS-Logs.log");
		logger.addHandler(fh);
		
		SimpleFormatter formatter = new SimpleFormatter();  
		fh.setFormatter(formatter);  
		
	}
	
	public void info(String location, String msg) {
		 logger.info(location + " >>> " + msg + "\n");
	}
	
	public void error(String location, String msg) {
		logger.warning(location + " >>> " + msg + "\n");
	}
	
	
}
