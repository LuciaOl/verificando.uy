package verificando.uy.controllers;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;
import verificando.uy.model.Hecho;
import verificando.uy.services.ReportesService;

@RestController
@RequestMapping("/reportes")
public class ReportesController {

    private final ReportesService reportesService;
	
    public ReportesController(ReportesService reportesService) {
        this.reportesService = reportesService;
    }
	
	@GetMapping("/between")
    public List<Hecho> getHechosEntreFechas(
            @RequestParam("desde") LocalDateTime desde,
            @RequestParam("hasta") LocalDateTime hasta) {
        return reportesService.obtenerHechosEntreFechas(desde, hasta);
    }
}
