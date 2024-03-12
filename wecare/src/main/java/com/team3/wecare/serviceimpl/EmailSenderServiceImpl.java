package com.team3.wecare.serviceimpl;

import java.io.StringWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.team3.wecare.entities.Admin;
import com.team3.wecare.entities.Complaint;
import com.team3.wecare.entities.EmailOtp;
import com.team3.wecare.entities.Jurisdiction;
import com.team3.wecare.entities.Officer;
import com.team3.wecare.entities.User;
import com.team3.wecare.exception.WeCareException;
import com.team3.wecare.repositories.EmailOtpRepository;
import com.team3.wecare.service.EmailSenderService;

import freemarker.template.Configuration;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailSenderServiceImpl implements EmailSenderService {
	private static final String COMPLAINT = "complaint";
	private static final Logger log = LoggerFactory.getLogger(EmailSenderServiceImpl.class);
	@Value("${OTPTIME}")
	private int otpTime;
	@Value("${EMAIL_BODY}")
	private String emailBody;
	@Value("${SECURITY_MESSAGE}")
	private String securityMessage;
	@Value("${REGARDS}")
	private String regards;
	@Value("${EMAIL_SUBJECT}")
	private String emailSubject;
	@Value("${spring.mail.username}")
	public String senderAddress;

	@Autowired
	private JavaMailSender mailSender;
	@Autowired
	private EmailOtpRepository emailOtpRepository;
	@Autowired
	private Configuration configuration;

	Random random = new Random();

	@Override
	public String getotp(String email) {
		log.debug("Get OTP for email: {}", email);
		EmailOtp emailOtp = new EmailOtp();
		boolean isMailSent = false;
		emailOtp = null;
		String otp = generateOtp();
		String body = emailBody + String.valueOf(otp) + "\n" + securityMessage + "\n" + "\n" + regards;
		isMailSent = sendSimpleEmail(email, emailSubject, body);
		log.info("Mail sent status : {}", isMailSent);

		if (isMailSent) {
			emailOtp = saveOtpToDb(email, otp);
		}

		return String.valueOf(emailOtp.getOtp());
	}

	public String generateOtp() {
		int min = 100000;
		int max = 999999;
		int randomNumber = random.nextInt(max - min + 1) + min;

		return String.valueOf(randomNumber);
	}

	public Boolean sendSimpleEmail(String toEmail, String subject, String body) {
		log.debug("Sending Email with otp");
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(senderAddress);
		message.setTo(toEmail);
		message.setText(body);
		message.setSubject(subject);
		mailSender.send(message);
		return true;

	}

	public EmailOtp saveOtpToDb(String email, String otp) {
		log.debug("{} is the OTP for Email {}", otp, email);
		EmailOtp emailOtp = new EmailOtp();
		emailOtp.setEmail(email);
		emailOtp.setOtp(otp);
		emailOtp.setExpirationDate(new Date(emailOtp.getCreationDate().getTime() + otpTime * 1000L));
		return emailOtpRepository.save(emailOtp);
	}

	@Override
	public Boolean validate(String email, String otp) throws WeCareException {
		log.info("Validating OTP: {} for the Email: {}", otp, email);
		EmailOtp emailOtp = new EmailOtp();
		emailOtp = getEmailOtp(email, otp);

		if (emailOtp != null) {

			return true;
		} else {
			log.error("Otp is expired");
			throw new WeCareException("Otp is expired");
		}
	}

	public EmailOtp getEmailOtp(String email, String otp) {
		Date date = new Date();
		EmailOtp emailOtp = new EmailOtp();
		emailOtp = emailOtpRepository.findByEmailAndOtpAndExpirationDateAfter(email, otp, date);

		return emailOtp;
	}

	@Override
	public String getEmailContent(User user, Officer officer, Complaint complaint) throws Exception {
		StringWriter stringWriter = new StringWriter();
		Map<String, Object> model = new HashMap<>();
		model.put("user", user);
		model.put("officer", officer);
		model.put(COMPLAINT, complaint);
		configuration.getTemplate("complaintEmail.ftlh").process(model, stringWriter);
		log.debug("Configuration of template : Complaint");


		return stringWriter.getBuffer().toString();
	}
	@Override
	public String getEmaiOfficerContent( Officer officer,Jurisdiction jury) throws Exception {
		StringWriter stringWriter = new StringWriter();
		Map<String, Object> model = new HashMap<>();
		model.put("officer", officer);
		model.put("jury", jury );
		configuration.getTemplate("officerEmail.ftlh").process(model, stringWriter);

		return stringWriter.getBuffer().toString();
	}

	@Override
	public Boolean sendEmailToUser(User user, Officer officer, Complaint complaint) throws Exception {
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
		helper.setSubject("WeCare - Complaints");
		helper.setTo(user.getEmail());
		helper.setCc(officer.getEmail());
		String emailContent = getEmailContent(user, officer, complaint);
		helper.setText(emailContent, true);
		mailSender.send(mimeMessage);
		
		return true;
	}

	@Override
	public Boolean sendEmailToOfficer(Officer officer, Jurisdiction jury) throws Exception {
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
		helper.setSubject("WeCare - Credentials");
		helper.setTo(officer.getEmail());
		String emailContent = getEmaiOfficerContent(officer,jury);
		helper.setText(emailContent, true);
		mailSender.send(mimeMessage);
		
		return true;
	}

  @Override
	public String getResponseContent(User user, Complaint complaint) throws Exception {
		StringWriter stringWriter = new StringWriter();
		Map<String, Object> model = new HashMap<>();
		model.put("user", user);
		model.put(COMPLAINT, complaint);
		configuration.getTemplate("responseEmail.ftlh").process(model, stringWriter);
		log.debug("Configuration of template : User");


		return stringWriter.getBuffer().toString();
	}

	@Override
	public Boolean sendResponseToUser(User user, Complaint complaint) throws Exception {
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
		helper.setSubject("WeCare - Officer Response");
		helper.setTo(complaint.getUser().getEmail());
		String emailContent = getResponseContent(user, complaint);
		helper.setText(emailContent, true);
		mailSender.send(mimeMessage);

		return true;
	}
	
	@Override
	public String getStatusContent(User user, Complaint complaint) throws Exception {
		StringWriter stringWriter = new StringWriter();
		Map<String, Object> model = new HashMap<>();
		model.put("user", user);
		model.put(COMPLAINT, complaint);
		configuration.getTemplate("statusEmail.ftlh").process(model, stringWriter);
		log.debug("Configuration of template : Status");


		return stringWriter.getBuffer().toString();
	}

	@Override
	public Boolean sendStatusToUser(User user, Complaint complaint) throws Exception {
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
		helper.setSubject("WeCare - Status");
		helper.setTo(complaint.getUser().getEmail());
		String emailContent = getStatusContent(user, complaint);
		helper.setText(emailContent, true);
		mailSender.send(mimeMessage);

		return true;
	}

	@Override
	public Boolean sendEmailToAdmin(Admin admin) throws Exception {
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
		helper.setSubject("WeCare - Credentials");
		helper.setTo(admin.getEmail());
		String emailContent = getEmailContentOfAdmin(admin);
		helper.setText(emailContent, true);
		mailSender.send(mimeMessage);
		
		return true;
	}

	@Override
	public String getEmailContentOfAdmin(Admin admin) throws Exception {
		StringWriter stringWriter = new StringWriter();
		Map<String, Object> model = new HashMap<>();
		model.put("admin", admin);
		configuration.getTemplate("adminEmail.ftlh").process(model, stringWriter);
		log.debug("Configuration of template : Admin");

		return stringWriter.getBuffer().toString();
	}
}