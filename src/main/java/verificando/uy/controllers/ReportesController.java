package verificando.uy.controllers;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reportes")
public class ReportesController {

    @GetMapping("/generar")
    public String generarReporte() {
        // Lógica para generar un reporte
        return "Reporte generado";
    }
}
