package verificando.uy.services;

import org.springframework.beans.factory.annotation.Autowired;
import verificando.uy.dtos.DtHecho;
import verificando.uy.dtos.DtVerificacion;
import verificando.uy.model.Verificacion;
import org.springframework.stereotype.Service;
import verificando.uy.model.Hecho;
import verificando.uy.repositories.HechoRepository;

import java.util.Optional;
import java.time.LocalDateTime;
import java.util.List;

import java.util.stream.Collectors;

@Service
public class ReportesService {

    private HechoRepository hechoRepository;

    @Autowired
    public ReportesService(HechoRepository hechoRepository) {
        this.hechoRepository = hechoRepository;
    }
    
    public List<Hecho> obtenerHechosEntreFechas(LocalDateTime desde, LocalDateTime hasta) {
    	List<Hecho> hechos = this.hechoRepository.getHechos();
    	return hechos.stream()
                .filter(hecho -> hecho.getFechaCreacion() != null && 
                !hecho.getFechaCreacion().isBefore(desde) && 
                !hecho.getFechaCreacion().isAfter(hasta))
            	.collect(Collectors.toList());
    }
}
