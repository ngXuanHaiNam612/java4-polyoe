package com.java4.asm2polyoe.service;

public interface EmailService {
    void sendSimpleEmail(String to, String subject, String text);
    void sendShareNotification(String to, String senderName, String videoTitle, String videoDescription, String videoUrl);
    void sendPasswordResetEmail(String to, String username, String newPassword); // Thêm phương thức này
}

