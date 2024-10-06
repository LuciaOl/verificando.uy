package controllers;

import model.Hecho;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/citizen")
public class CitizenController extends UsuarioController {

    // Métodos específicos para los ciudadanos

    @PostMapping("/reportar-hecho")
    public Hecho reportarHecho(@RequestBody Hecho hecho) {
        // Lógica para que un ciudadano reporte un hecho
        return hecho;
    }

    // Otros métodos específicos del ciudadano
}
