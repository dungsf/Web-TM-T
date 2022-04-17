package com.rikkeisoft.canifashop.services;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

public interface EmailService {

	public void sendResetPassword(String toEmail, String name, String code) throws MessagingException;

	public void sendOrderStatus(String toEmail, String name, String code, Integer status) throws MessagingException;

	public void sendEmail(String toEmail, String code, String createDate, String name, String address, String phone,
			Integer orderStatus) throws MessagingException;
}

@Service
@RequiredArgsConstructor
class EmailServiceImpl implements EmailService {

	private final JavaMailSender javaMailSender;

	@Override
	public void sendResetPassword(String toEmail, String name, String code) throws MessagingException {
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
		helper.setTo(toEmail);
		message.setContent("<!DOCTYPE html>\n" + "<html lang=\"en\">\n" + "\n" + "<head>\n"
				+ "    <meta charset=\"UTF-8\">\n" + "</head>\n" + "<style>\n" + "    pre{\n"
				+ "        font-size: 14px;\n" + "        font-weight: 400;\n" + "    }\n" + "</style>"
				+ "<body style=\"font-family: Arial, Helvetica, sans-serif;\">\n"
				+ "    <div style=\"width: 50%;margin: auto; height: auto; border: 1px solid black; padding: 20px;\">\n"
				+ "        <div><img src=\"https://img.upanh.tv/2022/04/07/logo.png\" style=\"width:15%;\"></div>\n"
				+ "        <div style=\"margin-top: 20px;\">\n" + "            <h3>Xin chào + name,</h2>\n"
				+ "            <h3>Xin chào <span style=\"font-size: 25px;\">" + name + "</span></h3>\n"
				+ "            <pre>Mật khẩu của bạn đã được cấp lại!\n" + "            </pre>\n"
				+ "            <h3>Mật khẩu: " + code + "</h3>\n"
				+ "            <pre>Vui lòng không cung cấp mật khẩu cho bất kì ai để tránh bị xâm nhập!</pre>\n"
				+ "            </pre>\n" + "        </div>\n" + "    </div>\n" + "</body>" + "\n" + "</html>",
				"text/html; charset=UTF-8");
		helper.setSubject("Canifa Shop");
		javaMailSender.send(message);
	}

	@Override
	public void sendOrderStatus(String toEmail, String name, String code, Integer status) throws MessagingException {
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
		helper.setTo(toEmail);
		message.setContent("<!DOCTYPE html>\n" + "<html lang=\"en\">\n" + "\n" + "<head>\n"
				+ "    <meta charset=\"UTF-8\">\n" + "</head>\n" + "<style>\n" + "    pre{\n"
				+ "        font-size: 19px;\n" + "        font-weight: 400;\n" + "    }\n" + "</style>\n"
				+ "<body style=\"font-family: Arial, Helvetica, sans-serif;\">\n"
				+ "    <div style=\"width: 50%;margin: auto; height: auto; border: 1px solid black; padding: 20px;\">\n"
				+ "        <div><img src=\"https://img.upanh.tv/2022/04/07/logo.png\" style=\"width:15%;\"></div>\n"
				+ "        <div style=\"margin-top: 20px;\">\n"
				+ "            <h3>Xin chào <span style=\"font-size: 25px;\">" + name + "</span></h3>\n"
				+ "            <pre>Đơn hàng <span style=\"font-weight: 500; font-size: 15px; color: dimgray;\">" + code
				+ "</span> đã được <span style=\"font-weight: 600; font-size: 20px; color: cadetblue;\">"
				+ convertStatusToString(status) + "</span>\n" + "            </pre>\n"
				+ "            <pre>Vui lòng check email để cập nhật đơn hàng thường xuyên nhé!</pre>\n"
				+ "        </div>\n" + "    </div>\n" + "</body>" + "\n" + "</html>", "text/html; charset=UTF-8");
		helper.setSubject("Canifa Shop");
		javaMailSender.send(message);
	}

	@Override
	public void sendEmail(String toEmail, String code, String createDate, String name, String address, String phone,
			Integer orderStatus) throws MessagingException {
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
		helper.setTo(toEmail);
		message.setContent("<!DOCTYPE html>\n" + "<html lang=\"en\">\n" + "\n" + "<head>\n"
				+ "    <meta charset=\"UTF-8\">\n" + "</head>\n" + "<style>\n" + "    pre{\n"
				+ "        font-size: 14px;\n" + "        font-weight: 400;\n" + "    }\n" + "</style>"
				+ "<body style=\"font-family: Arial, Helvetica, sans-serif;\">\n"
				+ "    <div style=\"width: 50%;margin: auto; height: 500px; border: 1px solid black; padding: 20px;\">\n"
				+ "        <div><img src=\"https://img.upanh.tv/2022/04/07/logo.png\" style=\"width:15%;\"></div>\n"
				+ "        <div style=\"margin-top: 20px;\">\n" + "            <h3>Xin chào + name</h2>\n"
				+ "            <h3>Xin chào <span style=\"font-size: 25px;\">" + name + "</span></h3>\n"
				+ "            <pre>Chúng tôi vui mừng thông báo cho bạn biết rằng chúng tôi đã nhận được đơn đặt hàng của bạn\n"
				+ "            </pre>\n" + "            <h3>Thông tin đơn hàng</h3>\n" + "            <pre>Mã đơn hàng:"
				+ code + "</pre>\n" + "            <pre>Ngày đặt:" + createDate + "</pre>\n"
				+ "            <pre>Họ tên người nhận:" + name + "</pre>\n" + "            <pre>Địa chỉ: " + address
				+ "</pre>\n" + "            <pre>Số điện thoại: " + phone + "</pre>\n" + "            <pre>Trạng thái: "
				+ orderStatus + "</pre>\n" + "            <pre>Trạng thái: " + convertStatusToString(orderStatus)
				+ "</pre>\n" + "            <pre>Đơn hàng của bạn sẽ được chuyển đến trong vài ngày nữa\n"
				+ "Vui lòng chú ý điện thoại và gmail nhé!\n" + "\n"
				+ "                                                Canifa shop chân thành cảm ơn quý khách!\n"
				+ "            </pre>\n" + "        </div>\n" + "    </div>\n" + "</body>" + "\n" + "</html>",
				"text/html; charset=UTF-8");
		helper.setSubject("Canifa Shop");
		javaMailSender.send(message);
	}

	public String convertStatusToString(Integer status) {
		switch (status) {
		case 0:
			return "Chờ xác nhận";
		case 1:
			return "Đang vận chuyển";
		case 2:
			return "Giao hàng thành công";
		case 3:
			return "Đã hủy";
		default:
			return null;
		}
	}

}