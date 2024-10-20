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

    public HechoController(HechoService hechoService) {
        this.hechoService = hechoService;
    }

    @PostMapping("/crear")
    public Hecho crearHecho(@RequestBody DtHecho hecho) {
        return hechoService.crearHecho(hecho.getDescription(), hecho.getCategory());
    }

    @GetMapping("/{factID}")
    public Hecho obtenerHecho(@PathVariable String factID) {
        Optional<Hecho> hecho = hechoService.obtenerHecho(factID);
        return hecho.orElse(null); // Retorna null si no se encuentra el hecho
    }

    // este NO verifica el hecho, SOLO actualiza info que ya este
    @PutMapping("/{factID}")
    public Hecho actualizarHecho(@PathVariable String factID, @RequestBody DtHecho hecho) {
        Optional<Hecho> hechoActualizado = hechoService.actualizarHecho(factID, hecho);
        return hechoActualizado.orElse(null); // Retorna null si no se encuentra el hecho
    }

    @PutMapping("/{factID}/verificar")
    public Hecho verificarHecho(@PathVariable String factID, @RequestBody DtVerificacion verificacion) {
        Optional<Hecho> hechoVerificado = hechoService.verificarHecho(factID, verificacion);
        List<Citizen> suscriptores = citizenService.obtenerSuscriptores(factID);
        // Notificar a los suscriptores
        suscriptores.forEach(suscriptor -> {
            notificationService.enviarNotificacionPush(suscriptor, hechoVerificado.get());
        });
        return hechoVerificado.orElse(null); // Retorna null si no se encuentra el hecho
    }
}
