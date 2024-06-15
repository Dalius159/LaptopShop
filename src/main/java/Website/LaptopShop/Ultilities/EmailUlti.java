package Website.LaptopShop.Ultilities;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class EmailUlti {

    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String toAddress, String title, String content) {
        // Kiểm tra email không được null hoặc rỗng
        Assert.notNull(toAddress, "To address must not be null");
        Assert.hasText(toAddress, "To address must not be empty");

        MimeMessage message = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(toAddress);
            helper.setSubject(title);
            helper.setText(content, true); // Tham số thứ hai là true để hỗ trợ định dạng HTML

            mailSender.send(message);
        } catch (MessagingException e) {
            // Xử lý ngoại lệ
            e.printStackTrace();
            // Hoặc ghi nhật ký lỗi: Logger.error("Failed to send email", e);
        }
    }
}
