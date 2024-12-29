package com.example.kanbansystem.Service;

import com.example.kanbansystem.service.EmailService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestEmailService {

    @Mock
    private JavaMailSender mailSender;

    @InjectMocks
    private EmailService emailService;

    @Test
    public void testSendEmail() {
        // Arrange
        String toEmail = "test@example.com";
        String subject = "Test Subject";
        String body = "This is a test email.";

        // Act
        emailService.sendEmail(toEmail, subject, body);

        // Assert
        verify(mailSender, times(1)).send(any(SimpleMailMessage.class));
    }

    @Test
    public void testSendEmail_ContentValidation() {
        // Arrange
        String toEmail = "test@example.com";
        String subject = "Test Subject";
        String body = "This is a test email.";

        // Capture the SimpleMailMessage sent to the mail sender
        doAnswer(invocation -> {
            SimpleMailMessage message = invocation.getArgument(0, SimpleMailMessage.class);

            // Assert the email content
            assertEquals("Kanbansystem1@gmail.com", message.getFrom());
            assertEquals(toEmail, message.getTo()[0]);
            assertEquals(subject, message.getSubject());
            assertEquals(body, message.getText());

            return null;
        }).when(mailSender).send(any(SimpleMailMessage.class));

        // Act
        emailService.sendEmail(toEmail, subject, body);

        // Verify the mailSender's send method was called
        verify(mailSender, times(1)).send(any(SimpleMailMessage.class));
    }
}
