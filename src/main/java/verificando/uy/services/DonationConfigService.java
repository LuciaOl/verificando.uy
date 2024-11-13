package verificando.uy.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import verificando.uy.model.DonationConfig;
import verificando.uy.repositories.DonationConfigRepository;

@Service
public class DonationConfigService {

    @Autowired
    private DonationConfigRepository repository;

    public DonationConfig getConfig() {
        return repository.findById(1L).orElseGet(() -> {
            DonationConfig defaultConfig = new DonationConfig();
            defaultConfig.setEmail("donaciones@ejemplo.com");
            defaultConfig.setBankAccount("123-456-789");
            defaultConfig.setMessage("Gracias por tu apoyo.");
            return repository.save(defaultConfig);
        });
    }

    public void updateConfig(DonationConfig config) {
        repository.save(config);
    }
}
