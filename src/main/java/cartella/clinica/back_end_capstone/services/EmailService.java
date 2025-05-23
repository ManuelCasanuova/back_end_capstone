package cartella.clinica.back_end_capstone.services;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendCredenzialiPaziente(String to, String username, String password) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(to);
            helper.setSubject("Benvenuto in CareFlow");

            String htmlContent = """
            <html>
              <body style="font-family: Arial, sans-serif; line-height: 1.6; color: #333;">
                <div style="text-align: center; margin-bottom: 20px;">
                   <img src="cid:logoCareFlow" alt="Logo" style="width: 160px;" />
                </div>
                <p>Gentile Paziente,</p>
                <p>Le comunichiamo che è stato registrato nel nostro sistema.</p>
                <p><strong>Le sue credenziali sono:</strong></p>
                <ul>
                  <li><strong>Username:</strong> %s</li>
                  <li><strong>Password temporanea:</strong> %s</li>
                </ul>
                <p>Le ricordiamo che dovrà <strong>modificare la password al primo accesso</strong>.</p>
                <p>Cordiali saluti,<br/><em>Il team dello Studio Medico</em></p>
              </body>
            </html>
        """.formatted(username, password);

            helper.setText(htmlContent, true);
            helper.addInline("logoCareFlow", new ClassPathResource("static/Logo.png"));
            mailSender.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException("Errore nell'invio dell'email", e);
        }
    }

}

