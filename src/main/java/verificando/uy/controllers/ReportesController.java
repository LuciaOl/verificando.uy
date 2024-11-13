package verificando.uy.controllers;

import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;
import verificando.uy.services.ReportesService;

import verificando.uy.dtos.HechosVerificadosResponseDTO;
import verificando.uy.dtos.CategoriaHechosDTO;

import org.springframework.format.annotation.DateTimeFormat;

@RestController
@RequestMapping("/reportes")
public class ReportesController {

    private final ReportesService reportesService;

    public ReportesController(ReportesService reportesService) {
        this.reportesService = reportesService;
    }

    @GetMapping("/getHechosVerificadosEntreFechas")
    public HechosVerificadosResponseDTO getHechosVerificadosEntreFechas(
            @RequestParam("desde") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime desde,
            @RequestParam("hasta") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime hasta) {

        return reportesService.getHechosVerificadosEntreFechas(desde, hasta);
    }

    @GetMapping("/getTopCategoriasDeHechos")
    public List<CategoriaHechosDTO> getTopCategoriasDeHechos(
            @RequestParam("desde") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime desde,
            @RequestParam("hasta") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime hasta) {

        return reportesService.getTopCategoriasDeHechos(desde, hasta);
    }
}
