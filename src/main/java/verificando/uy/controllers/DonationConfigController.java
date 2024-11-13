package verificando.uy.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import verificando.uy.model.DonationConfig;
import verificando.uy.services.DonationConfigService;

@RestController
@RequestMapping("/donaciones")
public class DonationConfigController {

    @Autowired
    private DonationConfigService service;

    @GetMapping
    public ResponseEntity<DonationConfig> getConfig() {
        return ResponseEntity.ok(service.getConfig());
    }

    @PostMapping
    public ResponseEntity<String> updateConfig(@RequestBody DonationConfig config) {
        service.updateConfig(config);
        return ResponseEntity.ok("Configuración actualizada exitosamente.");
    }
}