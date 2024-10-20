package services;

import model.Hecho;
import model.Verificacion;
import org.springframework.stereotype.Service;
import repositories.VerificacionRepository;

import java.time.LocalDateTime;
@Service
public class VerificacionService {

    public VerificacionRepository verificacionRepository;

    public VerificacionService(VerificacionRepository verificacionRepository) {
        this.verificacionRepository = verificacionRepository;
    }

    public Verificacion crearVerifiacion(Hecho hecho, boolean esVerdadero, String justification) {
        Verificacion verificacion = new Verificacion(hecho, esVerdadero, justification);
        verificacionRepository.save(verificacion);
        return verificacion;
    }
}
