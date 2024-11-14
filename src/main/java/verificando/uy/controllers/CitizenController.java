package verificando.uy.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import verificando.uy.controllers.UsuarioController;
import verificando.uy.model.Citizen;
import verificando.uy.model.Hecho;

import org.springframework.web.bind.annotation.*;
import verificando.uy.repositories.CitizenRepository;
import verificando.uy.repositories.HechoRepository;

import java.util.List;

@RestController
@RequestMapping("/citizen")
public class CitizenController extends UsuarioController {

    @Autowired
    private HechoRepository hechoRepository;

    @Autowired
    private CitizenRepository citizenRepository;

    @PostMapping("/reportar-hecho")
    public Hecho reportarHecho(@RequestBody Hecho hecho) {
        // Lógica para que un ciudadano reporte un hecho
        return hecho;
    }

    /// Suscribirse a hechos por categoría
    @PostMapping("/{citizenId}/suscribirse/{categoria}")
    public ResponseEntity<String> suscribirseACategoria(
            @PathVariable Long citizenId,
            @PathVariable String categoria) {

        Citizen citizen = citizenRepository.findById(citizenId).orElseThrow(() -> new RuntimeException("Ciudadano no encontrado"));
        List<Hecho> hechos = hechoRepository.findByCategory(categoria);

        for (Hecho hecho : hechos) {
            citizen.subscribeToHecho(hecho);
        }

        citizenRepository.save(citizen);
        return ResponseEntity.ok("Suscripción a hechos en la categoría " + categoria + " completada.");
    }

    // Cancelar suscripción a hechos por categoría
    @PostMapping("/{citizenId}/cancelar-suscripcion/{categoria}")
    public ResponseEntity<String> cancelarSuscripcionACategoria(
            @PathVariable Long citizenId,
            @PathVariable String categoria) {

        Citizen citizen = citizenRepository.findById(citizenId).orElseThrow(() -> new RuntimeException("Ciudadano no encontrado"));
        List<Hecho> hechos = hechoRepository.findByCategory(categoria);

        for (Hecho hecho : hechos) {
            citizen.unsubscribeFromHecho(hecho);
        }

        citizenRepository.save(citizen);
        return ResponseEntity.ok("Suscripción cancelada para la categoría " + categoria);
    }

    // Listar suscripciones
    @GetMapping("/{citizenId}/suscripciones")
    public List<Hecho> obtenerSuscripciones(@PathVariable Long citizenId) {
        Citizen citizen = citizenRepository.findById(citizenId).orElseThrow(() -> new RuntimeException("Ciudadano no encontrado"));
        return citizen.getSubscriptions();
    }

    // Otros métodos específicos del ciudadano
}
