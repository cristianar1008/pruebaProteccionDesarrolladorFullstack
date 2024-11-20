package ejemplobd.crud.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    // Método para enviar el correo con la secuencia Fibonacci
    public void sendFibonacciEmail(String recipient, String fibonacciSequence) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(recipient);  // Dirección de correo destino
        message.setSubject("Secuencia de Fibonacci Generada - Cristian Arturo Parra Gonzalez");
        message.setText("Se ha generado una nueva secuencia de Fibonacci: " + fibonacciSequence);

        try {
            emailSender.send(message);
            System.out.println("Correo enviado exitosamente a " + recipient);
        } catch (Exception e) {
            System.err.println("Error al enviar correo: " + e.getMessage());
        }
    }
}
