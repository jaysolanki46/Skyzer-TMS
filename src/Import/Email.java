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
import java.io.UnsupportedEncodingException;
import java.util.Properties;

public class Email {
	
	TMSLogger logger;
	String to = "support@skyzer.co.nz";
    String from = "skyzertms@gmail.com";
    String logFile = System.getProperty("user.dir") + "\\logs\\Skyzer-TMS-Logs.log";
    String visionFile = "N:\\AAPAYMENTS\\Daily Imports\\New Vision_ VSM\\Vision\\Vision " + new DateRange().getDateRangeStr() + ".csv";
    String vsmFile = "N:\\AAPAYMENTS\\Daily Imports\\New Vision_ VSM\\VSM\\VSM " + new DateRange().getDateRangeStr() + ".csv";
    String bodyText = "";
    final String username = "skyzertms@gmail.com";
    final String password = "Skynet123";
    Properties props;
    Session session;

    public Email() throws SecurityException, IOException {
    	
    	logger = new TMSLogger();
    	props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "25");
        
        session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                   protected PasswordAuthentication getPasswordAuthentication() {
                      return new PasswordAuthentication(username, password);
                   }
                });
    }
    
    public void success() throws UnsupportedEncodingException {
    	
    	logger.info(this.getClass().getName(), "Confirmation email is on way...");

        try {
           Message message = new MimeMessage(session);

           message.setFrom(new InternetAddress(from, "TMS Automation"));
           message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
           message.setSubject("Daily Import (" + new DateRange().getDateRangeStr() + ")");
          
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
           messageBodyPart.setFileName("Skyzer TMS Process Logs " + new DateRange().getDateRangeStr() + ".log");
           multipart.addBodyPart(messageBodyPart);
           
           DataSource visionSource = new FileDataSource(visionFile);
           messageBodyPart = new MimeBodyPart();
           messageBodyPart.setDataHandler(new DataHandler(visionSource));
           messageBodyPart.setFileName("Vision " + new DateRange().getDateRangeStr() + ".csv");
           multipart.addBodyPart(messageBodyPart);

           DataSource vsmSource = new FileDataSource(vsmFile);
           messageBodyPart = new MimeBodyPart();
           messageBodyPart.setDataHandler(new DataHandler(vsmSource));
           messageBodyPart.setFileName("VSM " + new DateRange().getDateRangeStr() + ".csv");
           multipart.addBodyPart(messageBodyPart);

           message.setContent(multipart);
           Transport.send(message);
           logger.info(this.getClass().getName(), "Email sent...");
    
        } catch (MessagingException e) {
        	e.printStackTrace();
        	logger.error(this.getClass().getName(), e.getMessage());
        }
    }
	
    public void failed(String nextScheduledMsg) throws UnsupportedEncodingException {
    	
        try {
           Message message = new MimeMessage(session);

           message.setFrom(new InternetAddress(from, "TMS Automation"));
           message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("jay.solanki@skyzer.co.nz")); // Change here after success
           message.setSubject("Failed Daily Import (" + new DateRange().getDateRangeStr() + ")");
          
           BodyPart messageBodyPart = new MimeBodyPart();
           bodyText = "<center><h1 style=\"color:#0076BE\">Skyzer Daily Import</h1> <h1 style=\"color:#FF0000\">(Failed)</h1></center>" + 
           		"<p>Hi Team,</p>" + 
           		"<p>The system has failed the importing process for today for the Sybiz Vision.</p>" + 
           		"<p>Please export Vision and VSM report from the Sybiz Vision.</p>" +
           		"<p>"+ nextScheduledMsg +"</p>" +
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
          
           message.setContent(multipart);
           Transport.send(message);
    
        } catch (MessagingException e) {
        	logger.error(this.getClass().getName(), e.getMessage());
        }
    }
    
    public void secondUpdateEmail() throws UnsupportedEncodingException {
    	
        try {
           Message message = new MimeMessage(session);

           message.setFrom(new InternetAddress(from, "TMS Automation"));
           message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("jay.solanki@skyzer.co.nz"));
           message.setSubject("Double Check Daily Import (" + new DateRange().getDateRangeStr() + ")");
          
           BodyPart messageBodyPart = new MimeBodyPart();
           bodyText = "<center><h1 style=\"color:#0076BE\">Skyzer Daily Import</h1> <h1 style=\"color:#FF0000\">(Second check)</h1></center>" + 
           		"<p>Hi Jay,</p>" + 
           		"<p></p>" + 
           		"<p>All done for today.</p>" +
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
          
           message.setContent(multipart);
           Transport.send(message);
    
        } catch (MessagingException e) {
        	logger.error(this.getClass().getName(), e.getMessage());
        }
    }
    
}
