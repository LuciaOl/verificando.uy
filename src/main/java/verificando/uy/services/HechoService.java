package verificando.uy.services;

import verificando.uy.dtos.DtHecho;
import verificando.uy.dtos.DtVerificacion;
import verificando.uy.model.Verificacion;
import org.springframework.stereotype.Service;
import verificando.uy.model.Hecho;
import verificando.uy.repositories.HechoRepository;

import java.util.Optional;

import static verificando.uy.enums.Status.VERIFICADO;

@Service
public class HechoService {

    private HechoRepository hechoRepository;

    private VerificacionService verificacionService;

    public Hecho crearHecho(String description, String category) {
        Hecho hecho = new Hecho(description, category);
        hechoRepository.save(hecho); // Guardar el hecho en la "base de datos"
        return hecho;
    }

    public Optional<Hecho> obtenerHecho(String factID) {
        // Buscar un hecho por su ID
        return this.hechoRepository.findById(factID);
    }

    public Optional<Hecho> actualizarHecho(String factID, DtHecho hechoActualizado) {
        Hecho hecho = this.hechoRepository.findById(factID).orElse(null);
        if (hecho == null) {
            return Optional.empty(); // Hecho no encontrado
        } else {
            hecho.setDescription(hechoActualizado.getDescription());
            hecho.setCategory(hechoActualizado.getCategory());
            hecho.setStatus(hechoActualizado.getStatus());
            hecho.setRatings(hechoActualizado.getRatings());
            hecho.setJustifications(hechoActualizado.getJustifications());
            hechoRepository.save(hecho); // Actualizar el hecho en la "base de datos"
            return Optional.of(hecho);
        }
    }

    public Optional<Hecho> verificarHecho(String factID, DtVerificacion verificacion) {
        Hecho hecho = this.hechoRepository.findById(factID).orElse(null);
        if (hecho == null) {
            return Optional.empty(); // Hecho no encontrado
        } else {
            Verificacion verif = verificacionService.crearVerifiacion(hecho, verificacion.esVerdadero(), verificacion.getJustificacion());
            hecho.setStatus(VERIFICADO);
            hecho.addVerification(verif);
            hechoRepository.save(hecho);
            return Optional.of(hecho);
        }
    }
}
