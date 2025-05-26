package cartella.clinica.back_end_capstone.services;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

    public void sendMailChiusuraStudio(String to, String nomeStudio, String dataChiusura, String nomeMedico, String cognomeMedico) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, false, "UTF-8");

            helper.setTo(to);
            helper.setSubject("Chiusura straordinaria dello studio medico");

            String text = String.format(
                    "Gentile Paziente,\n\nLa informiamo che lo studio %s sarà chiuso in data %s.\n" +
                            "Tutti gli appuntamenti per questo giorno sono stati cancellati.\n\n" +
                            "Ci scusiamo per il disagio.\n\n" +
                            "Cordiali saluti,\n%s %s",
                    nomeStudio, dataChiusura, nomeMedico, cognomeMedico
            );

            helper.setText(text, false);
            mailSender.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException("Errore nell'invio dell'email di chiusura studio", e);
        }
    }

    public void sendMailConfermaAppuntamento(String to, String nomePaziente, LocalDateTime dataOra, String nomeMedico, String cognomeMedico) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, false, "UTF-8");

            helper.setTo(to);
            helper.setSubject("Conferma prenotazione appuntamento");

            String testo = String.format(
                    "Gentile %s,\n\n" +
                            "La informiamo che il suo appuntamento è stato confermato per il giorno %s alle ore %s.\n\n" +
                            "Cordiali saluti,\n%s %s",
                    nomePaziente,
                    dataOra.toLocalDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                    dataOra.toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm")),
                    nomeMedico,
                    cognomeMedico
            );

            helper.setText(testo, false);
            mailSender.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException("Errore nell'invio della mail di conferma appuntamento", e);
        }
    }

}

