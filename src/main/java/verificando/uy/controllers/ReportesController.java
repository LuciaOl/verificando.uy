package verificando.uy.controllers;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;
import verificando.uy.model.Hecho;
import verificando.uy.services.ReportesService;
import org.springframework.format.annotation.DateTimeFormat;

@RestController
@RequestMapping("/reportes")
public class ReportesController {

    private final ReportesService reportesService;
	
    public ReportesController(ReportesService reportesService) {
        this.reportesService = reportesService;
    }
	

    @GetMapping("/between")
    public List<Hecho> getHechosEntreFechas(
            @RequestParam("desde") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime desde,
            @RequestParam("hasta") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime hasta) {
        return reportesService.obtenerHechosEntreFechas(desde, hasta);
    }

}
