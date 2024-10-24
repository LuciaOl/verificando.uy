package verificando.uy.services;

import org.springframework.beans.factory.annotation.Autowired;
import verificando.uy.repositories.NotificacionRepository;
import verificando.uy.model.Citizen;
import verificando.uy.model.Hecho;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import verificando.uy.model.Notificacion;

import java.util.Date;

@Service
public class NotificationService {

    @Value("${expo.notification.url}")
    private String expoNotificationUrl;
    @Value("${app.firebase.server-key}")
    private String serverKey;

    private final NotificacionRepository notificacionRepository;

    @Autowired
    public NotificationService(NotificacionRepository notificacionRepository) {
        this.notificacionRepository = notificacionRepository;
    }

    // Método para enviar notificación push a un ciudadano
    public void enviarNotificacionPush(Citizen suscriptor, Hecho hechoVerificado) {
        String titulo = "Nuevo Hecho Verificado";
        String mensaje = "Se ha verificado un nuevo hecho en la categoría: " + hechoVerificado.getCategory() + " - " + hechoVerificado.getDescription();
        String token = suscriptor.getTockenDispositivo();
        RestTemplate restTemplate = new RestTemplate();

        JSONObject bodyJson = new JSONObject();
        bodyJson.put("to", token);
        bodyJson.put("title", titulo);
        bodyJson.put("body", mensaje);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = new HttpEntity<>(bodyJson.toString(), headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(expoNotificationUrl, request, String.class);
            String estadoMensaje;

            if (response.getStatusCode() == HttpStatus.OK) {
                estadoMensaje = "Notificación enviada exitosamente.";
            } else {
                estadoMensaje = "Error al enviar la notificación. Estado: " + response.getStatusCode();
            }

            // Guardar la notificación en la base de datos
            Notificacion notificacion = new Notificacion();
            notificacion.setCitizen(suscriptor);
            notificacion.setMensaje(mensaje);
            notificacion.setFecha(new Date());
            notificacionRepository.save(notificacion);

            System.out.println(estadoMensaje + " a: " + token);
        } catch (Exception e) {
            System.err.println("Error al enviar la notificación: " + e.getMessage());
            // Guardar la notificación con un mensaje de error en la base de datos
            Notificacion notificacion = new Notificacion();
            notificacion.setCitizen(suscriptor);
            notificacion.setMensaje("Error al enviar: " + e.getMessage());
            notificacion.setFecha(new Date());
            notificacionRepository.save(notificacion);
        }
    }


}
