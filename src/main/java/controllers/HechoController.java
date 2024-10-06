package controllers;

import model.Hecho;
import org.springframework.web.bind.annotation.*;
import services.HechoService;

import java.util.Optional;

@RestController
@RequestMapping("/hechos")
public class HechoController {

    private final HechoService hechoService;

    public HechoController(HechoService hechoService) {
        this.hechoService = hechoService;
    }

    @PostMapping("/crear")
    public Hecho crearHecho(@RequestBody Hecho hecho) {
        return hechoService.crearHecho(hecho);
    }

    @GetMapping("/{factID}")
    public Hecho obtenerHecho(@PathVariable String factID) {
        Optional<Hecho> hecho = hechoService.obtenerHecho(factID);
        return hecho.orElse(null); // Retorna null si no se encuentra el hecho
    }

    @PutMapping("/{factID}")
    public Hecho actualizarHecho(@PathVariable String factID, @RequestBody Hecho hecho) {
        Optional<Hecho> hechoActualizado = hechoService.actualizarHecho(factID, hecho);
        return hechoActualizado.orElse(null); // Retorna null si no se encuentra el hecho
    }
}
