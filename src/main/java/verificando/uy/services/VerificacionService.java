package verificando.uy.services;

import verificando.uy.model.Hecho;
import verificando.uy.model.Verificacion;
import org.springframework.stereotype.Service;
import verificando.uy.repositories.VerificacionRepository;

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
