package com.toystore.service;

import java.util.Properties;
import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

public class EmailService {

    // (Hàm cũ của bạn để Reset Password)
    public static void sendResetPasswordEmail(String recipientEmail, String token) {
        final String fromEmail = "your-email@gmail.com"; // Email của bạn
        final String password = "your-app-password"; // Mật khẩu ứng dụng của bạn

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Authenticator auth = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        };

        Session session = Session.getInstance(props, auth);

        try {
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(fromEmail));
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));
            msg.setSubject("Yêu cầu đặt lại mật khẩu cho VT88Store");

            String resetLink = "http://localhost:8080/toystore/reset-password?token=" + token;
            String emailContent = "Chào bạn,<br><br>Chúng tôi nhận được yêu cầu đặt lại mật khẩu cho tài khoản của bạn. Vui lòng nhấp vào liên kết dưới đây để đặt lại mật khẩu:<br><br>"
                                + "<a href=\"" + resetLink + "\">Đặt lại mật khẩu</a><br><br>"
                                + "Nếu bạn không yêu cầu điều này, vui lòng bỏ qua email này.<br><br>Trân trọng,<br>Đội ngũ VT88Store";
            msg.setContent(emailContent, "text/html; charset=UTF-8");

            Transport.send(msg);
            System.out.println("Email sent successfully!");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    // ==========================================================
    // === HÀM MỚI ĐƯỢC THÊM VÀO (CHỈNH SỬA) ===
    // ==========================================================
    public static void sendVerificationEmail(String recipientEmail, String code) {
        // --- THAY THẾ THÔNG TIN CỦA BẠN VÀO ĐÂY ---
        final String fromEmail = "vt88.store@gmail.com";
        final String password = "reaiwmhaircydpyd";

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Authenticator auth = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        };

        Session session = Session.getInstance(props, auth);

        try {
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(fromEmail));
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));
            msg.setSubject("Xac thuc tai khoan VT88Store cua ban"); // Tiêu đề email

            // Nội dung email
            String emailContent = "Chao ban,<br><br>Cam on ban da dang ky tai khoan tai VT88Store. Ma xac thuc cua ban la:<br><br>"
                                + "<h2 style='color: #ff5722; text-align: center;'>" + code + "</h2><br>"
                                + "Vui long nhap ma nay vao trang xac thuc de hoan tat dang ky.<br><br>"
                                + "Tran trong,<br>Doi ngu VT88Store";
            msg.setContent(emailContent, "text/html; charset=UTF-8");

            Transport.send(msg);
            System.out.println("Verification Email sent successfully!");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
    /**
     * (HÀM MỚI) Gửi mã xác nhận ĐỔI MẬT KHẨU
     */
    public static void sendChangePasswordCodeEmail(String recipientEmail, String code) {
        // --- THAY THẾ THÔNG TIN CỦA BẠN VÀO ĐÂY ---
        final String fromEmail = "your-email@gmail.com"; // Email của bạn
        final String password = "your-app-password"; // Mật khẩu ứng dụng của bạn

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Authenticator auth = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        };

        Session session = Session.getInstance(props, auth);

        try {
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(fromEmail));
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));
            msg.setSubject("Ma xac nhan thay doi mat khau VT88Store"); // Tiêu đề email

            // Nội dung email
            String emailContent = "Chao ban,<br><br>Ban dang thuc hien thay doi mat khau tai VT88Store. Ma xac thuc cua ban la:<br><br>"
                                + "<h2 style='color: #ff5722; text-align: center;'>" + code + "</h2><br>"
                                + "Vui long nhap ma nay de hoan tat viec thay doi mat khau.<br><br>"
                                + "Neu ban khong yeu cau dieu nay, vui long bo qua email.<br><br>"
                                + "Tran trong,<br>Doi ngu VT88Store";
            msg.setContent(emailContent, "text/html; charset=UTF-8");

            Transport.send(msg);
            System.out.println("Change Password Code Email sent successfully!");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}