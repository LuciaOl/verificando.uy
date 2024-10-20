package verificando.uy.services;

import verificando.uy.model.Citizen;
import org.springframework.stereotype.Service;
import verificando.uy.repositories.CitizenRepository;

import java.util.List;
@Service
public class CitizenService {

    private CitizenRepository citizenRepository;

    public CitizenService(CitizenRepository citizenRepository) {
        this.citizenRepository = citizenRepository;
    }

    public List<Citizen> obtenerSuscriptores(String factID) {
        return citizenRepository.findSuscriptores(factID);
    }
}
