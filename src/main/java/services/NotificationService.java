package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import repositories.CitizenRepository;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.FirebaseMessagingException;
import java.util.concurrent.TimeUnit;

@Service
public class NotificationService {

    @Value("${expo.notification.url}")
    private String expoNotificationUrl;
    @Value("${app.firebase.server-key}")
    private String serverKey;
    @Autowired
    private CitizenRepository citizenRepository;

    public NotificationService(CitizenRepository citizenRepository) {
        this.citizenRepository = citizenRepository;
    }


    // Método para enviar notificación push a un ciudadano
    public void enviarNotificacionPush(String token, String titulo, String mensaje) {
        Message message = Message.builder()
                .setToken(token)
                .putData("title", titulo)
                .putData("body", mensaje)
                .build();

        try {
            // Intentar enviar la notificación
            String response = FirebaseMessaging.getInstance().send(message);
            System.out.println("Notificación enviada exitosamente a: " + token + ", Response: " + response);
        } catch (FirebaseMessagingException e) {
            System.err.println("Error al enviar la notificación: " + e.getMessage());
            manejarFalloEnConexion(token, titulo, mensaje); // Llamar al método para manejar el fallo
        }
    }

    // Método para manejar fallos en la conexión con Firebase
    private void manejarFalloEnConexion(String token, String titulo, String mensaje) {
        // Aquí puedes implementar un método para almacenar o reintentar
        System.out.println("Falló el envío de la notificación, almacenando para envío posterior: " + mensaje);
        almacenarNotificacion(token, titulo, mensaje);
    }

    // Método para almacenar la notificación para su envío posterior
    private void almacenarNotificacion(String token, String titulo, String mensaje) {
        // Implementar lógica para almacenar la notificación (e.g., en base de datos)
        System.out.println("Notificación almacenada para envío posterior: " + titulo + " - " + mensaje);
    }

    // Método que se invoca cuando se verifica un nuevo hecho
    public void notificarNuevoHecho(String areaTematica, String token) {
        String titulo = "Nuevo Hecho Verificado";
        String mensaje = "Se ha verificado un nuevo hecho en el área temática: " + areaTematica;
        enviarNotificacionPush(token, titulo, mensaje);
    }
}
