package verificando.uy.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;
import verificando.uy.dtos.DtHecho;
import verificando.uy.dtos.DtVerificacion;
import verificando.uy.model.Checker;
import verificando.uy.model.Citizen;
import verificando.uy.model.Hecho;
import org.springframework.web.bind.annotation.*;
import verificando.uy.repositories.CheckerRepository;
import verificando.uy.repositories.HechoRepository;
import verificando.uy.repositories.SubmitterRepository;
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

    @Autowired
    private HechoRepository hechoRepository;

    @Autowired
    private CheckerRepository checkerRepository;

    @Autowired
    private SubmitterRepository submitterRepository;

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

    @GetMapping("/obtenerHechos")
    public List<Hecho> obtenerHechos() {
        return hechoRepository.findAll();
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

    // Endpoint para asignar un checker a un hecho
    @PostMapping("/{hechoId}/asignar-checker/{checkerId}")
    public ResponseEntity<String> asignarChecker(@PathVariable Long hechoId, @PathVariable Long checkerId) {

        // Buscar el hecho por ID
        Hecho hecho = hechoRepository.findById(hechoId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Hecho no encontrado"));

        // Verificar que el hecho aún no ha sido asignado a otro checker
        if (hecho.getAssignedChecker() != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Este hecho ya está asignado a un checker.");
        }

        // Buscar el checker por ID
        Checker checker = checkerRepository.findById(checkerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Checker no encontrado"));

        // Asignar el hecho al checker,
        checker.addFact(hecho);

        // Guardar solo el checker
        checkerRepository.save(checker);

        // Asignar el checker al hecho
        hecho.setAssignedChecker(checker);

        // Guardar solo el hecho
        hechoRepository.save(hecho);

        return ResponseEntity.ok("Checker asignado exitosamente al hecho.");
    }
}
