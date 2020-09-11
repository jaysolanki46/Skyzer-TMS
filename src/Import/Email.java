package Import;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import Init.TMSLogger;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Properties;

public class Email {
	
	TMSLogger logger;

    public Email() throws SecurityException, IOException {
    	
    	logger = new TMSLogger();
    	logger.error(this.getClass().getName(), "Confirmation email is on way...");
    	String to = "jay.solanki@skyzer.co.nz";
        String from = "jaysolanki46@gmail.com";
        
        String logFile = System.getProperty("user.dir") + "\\logs\\Skyzer-TMS-Logs.log";
        String visionFile = "N:\\AAPAYMENTS\\Daily Imports\\New Vision_ VSM\\Vision\\Vision " + getDateRangeStr() + ".csv";
        String vsmFile = "N:\\AAPAYMENTS\\Daily Imports\\New Vision_ VSM\\VSM\\VSM " + getDateRangeStr() + ".csv";
        		
        String bodyText = "";

        final String username = "jaysolanki46@gmail.com";
        final String password = "Sisterbro46@";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "25");

        Session session = Session.getInstance(props,
           new javax.mail.Authenticator() {
              protected PasswordAuthentication getPasswordAuthentication() {
                 return new PasswordAuthentication(username, password);
              }
           });

        try {
           Message message = new MimeMessage(session);

           message.setFrom(new InternetAddress(from));
           message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
           message.setSubject("Daily Import (" + getDateRangeStr() + ")");
          
           BodyPart messageBodyPart = new MimeBodyPart();
           bodyText = "<center><h1 style=\"color:#0076BE\">Skyzer Daily Import</h1></center>" + 
           		"<p>Hi Team,</p>" + 
           		"<p>The system has done the importing process for today for new Vision.</p>" + 
           		"<p>Please click on following link to check import logs, and find attached file for process logs</p>" +
           		"<a href='http://10.63.192.11/SkyzerProd1/TMSBridge/ImportCheck/'>TMS Import Errors Checker</a>" +
           		"<p>Thank you.</p>" + 
           		"<span>Sincerely,</span><br/>" +
           		"<span style=\"color:#7d8185;font-weight:bold\">Skyzer Technologies</span><br/>" + 
           		"<span style=\"color:#0076BE;font-weight:bold\">E:</span><span style=\"color:#0076BE;font-weight:bold\"> support@skyzer.co.nz</span><br/>" + 
           		"<span style=\"color:#0076BE;font-weight:bold\">P:</span><span style=\"color:#7d8185;font-weight:bold\"> +64 9 259 0322</span><br/>" + 
           		"<span style=\"color:#0076BE;font-weight:bold\">A:</span><span style=\"color:#7d8185;font-weight:bold\"> 269 Mount smart Road, Onehunga, Auckland 1061</span><br/>" +
           		"<span style=\"color:#0076BE;font-weight:bold\">W: </span><a href='www.skyzer.co.nz' style='color:#0076BE;font-weight:bold'>www.skyzer.co.nz</a></span><br/>" + 
           		"<br/>" +
           		"<p style='color:#97999c'>[This is an automatically generated email, please do not reply]</p>";
           messageBodyPart.setContent(bodyText, "text/html");
           Multipart multipart = new MimeMultipart();
           multipart.addBodyPart(messageBodyPart);
          
           DataSource logFileSource = new FileDataSource(logFile);
           messageBodyPart = new MimeBodyPart();
           messageBodyPart.setDataHandler(new DataHandler(logFileSource));
           messageBodyPart.setFileName("Skyzer TMS Process Logs " + getDateRangeStr() + ".log");
           multipart.addBodyPart(messageBodyPart);
           
           DataSource visionSource = new FileDataSource(visionFile);
           messageBodyPart = new MimeBodyPart();
           messageBodyPart.setDataHandler(new DataHandler(visionSource));
           messageBodyPart.setFileName("Vision " + getDateRangeStr() + ".csv");
           multipart.addBodyPart(messageBodyPart);

           DataSource vsmSource = new FileDataSource(vsmFile);
           messageBodyPart = new MimeBodyPart();
           messageBodyPart.setDataHandler(new DataHandler(vsmSource));
           messageBodyPart.setFileName("VSM " + getDateRangeStr() + ".csv");
           multipart.addBodyPart(messageBodyPart);

           message.setContent(multipart);
           Transport.send(message);
           logger.error(this.getClass().getName(), "Email sent...");
    
        } catch (MessagingException e) {
        	logger.error(this.getClass().getName(), e.getMessage());
        }
    }	  
	
    public String getDateRangeStr() {
		
		String subject = "";
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
		}
		
		if(String.valueOf(lastMonth).length() < 2) {
			month = "0" + lastMonth;
		}
		
		lastDateStr = day + month + lastYear;
		
		subject = lastDateStr + " to " + currentDateStr;
		
		return subject;
	}

}
