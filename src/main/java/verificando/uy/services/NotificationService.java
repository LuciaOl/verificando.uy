package verificando.uy.services;

import verificando.uy.model.Citizen;
import verificando.uy.model.Hecho;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import verificando.uy.repositories.CitizenRepository;

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
            if(response.getStatusCode() == HttpStatus.OK){
                System.out.println("Notificación enviada exitosamente a: " + token);
            }
            else{
                System.out.println("Notificación no enviada a: " + token);
            }
        } catch (Exception e) {
            System.err.println("Error al enviar la notificación: " + e.getMessage());
        }
    }


}
