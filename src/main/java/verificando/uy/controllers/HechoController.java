package verificando.uy.controllers;

import verificando.uy.dtos.DtHecho;
import verificando.uy.dtos.DtVerificacion;
import verificando.uy.model.Citizen;
import verificando.uy.model.Hecho;
import org.springframework.web.bind.annotation.*;
import verificando.uy.services.CitizenService;
import verificando.uy.services.HechoService;
import verificando.uy.services.NotificationService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/hechos")
public class HechoController {

    private final HechoService hechoService;

    private CitizenService citizenService;

    private NotificationService notificationService;

    public HechoController(HechoService hechoService, CitizenService citizenService, NotificationService notificationService) {
        this.hechoService = hechoService;
        this.citizenService = citizenService;
        this.notificationService = notificationService;
    }

    @PostMapping("/crear")
    public Hecho crearHecho(@RequestBody DtHecho hecho) {
        return hechoService.crearHecho(hecho.getDescription(), hecho.getCategory());
    }

    @GetMapping("/{id}")
    public Hecho obtenerHecho(@PathVariable Long id) {
        Optional<Hecho> hecho = hechoService.obtenerHecho(id);
        return hecho.orElse(null); // Retorna null si no se encuentra el hecho
    }

    // este NO verifica el hecho, SOLO actualiza info que ya este
    @PutMapping("/{id}")
    public Hecho actualizarHecho(@PathVariable Long id, @RequestBody DtHecho hecho) {
        Optional<Hecho> hechoActualizado = hechoService.actualizarHecho(id, hecho);
        return hechoActualizado.orElse(null); // Retorna null si no se encuentra el hecho
    }

    @PutMapping("/{id}/verificar")
    public Hecho verificarHecho(@PathVariable Long id, @RequestBody DtVerificacion verificacion) {
        Optional<Hecho> hechoVerificado = hechoService.verificarHecho(id, verificacion);
        if (hechoVerificado.isEmpty()) {
            return null;
        }
        List<Citizen> suscriptores = citizenService.obtenerSuscriptores(hechoVerificado.get());
        // Notificar a los suscriptores
        suscriptores.forEach(suscriptor -> {
            notificationService.enviarNotificacionPush(suscriptor, hechoVerificado.get());
        });
        return hechoVerificado.orElse(null); // Retorna null si no se encuentra el hecho
    }
}
