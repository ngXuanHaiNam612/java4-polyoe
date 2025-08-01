package com.java4.asm2polyoe.service.impl;

import com.java4.asm2polyoe.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Override
    public void sendSimpleEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("hieuvu0422@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }

    @Override
    public void sendShareNotification(String to, String senderName, String videoTitle, String videoDescription, String videoUrl) {
        String subject = "Video được chia sẻ từ " + senderName + " trên Online Entertainment";
        String text = String.format(
                "Chào bạn,\n\n" +
                        "Người dùng %s đã chia sẻ một video thú vị với bạn:\n\n" +
                        "Tiêu đề: %s\n" +
                        "Mô tả: %s\n" +
                        "Xem ngay tại: %s\n\n" +
                        "Chúc bạn có những giây phút giải trí vui vẻ!\n\n" +
                        "Trân trọng,\n" +
                        "Đội ngũ Online Entertainment",
                senderName, videoTitle, videoDescription, videoUrl
        );
        sendSimpleEmail(to, subject, text);
    }

    @Override
    public void sendPasswordResetEmail(String to, String username, String newPassword) {
        String subject = "Yêu cầu đặt lại mật khẩu của bạn trên Online Entertainment";
        String text = String.format(
                "Chào %s,\n\n" +
                        "Bạn đã yêu cầu đặt lại mật khẩu cho tài khoản của mình.\n" +
                        "Mật khẩu tạm thời mới của bạn là: %s\n\n" +
                        "Vui lòng đăng nhập bằng mật khẩu này và thay đổi mật khẩu của bạn ngay lập tức để đảm bảo an toàn.\n\n" +
                        "Nếu bạn không yêu cầu đặt lại mật khẩu này, vui lòng bỏ qua email này hoặc liên hệ với bộ phận hỗ trợ của chúng tôi.\n\n" +
                        "Trân trọng,\n" +
                        "Đội ngũ Online Entertainment",
                username, newPassword
        );
        sendSimpleEmail(to, subject, text);
    }
}

