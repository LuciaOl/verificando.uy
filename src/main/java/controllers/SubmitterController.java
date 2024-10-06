package controllers;

import model.Hecho;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/submitter")
public class SubmitterController extends UsuarioController {

    // Métodos específicos para los submitters

    @PostMapping("/enviar-hecho")
    public Hecho enviarHecho(@RequestBody Hecho hecho) {
        // Lógica para que un submitter envíe un hecho para verificación
        return hecho;
    }

    // Otros métodos específicos del submitter
}
