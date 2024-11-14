package verificando.uy.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import verificando.uy.dtos.PeripheralNodeRequest;
import verificando.uy.model.PeripheralNode;
import verificando.uy.repositories.PeripheralNodeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class PeripheralNodeService {
    private static final Logger log = LoggerFactory.getLogger(PeripheralNodeService.class);

    private final PeripheralNodeRepository peripheralNodeRepository;

    @Autowired
    public PeripheralNodeService(PeripheralNodeRepository peripheralNodeRepository) {
        this.peripheralNodeRepository = peripheralNodeRepository;
    }

    public PeripheralNode crearNodoPeriferico(PeripheralNodeRequest request) throws Exception {
        if (peripheralNodeRepository.findByUrl(request.getUrl()).isPresent()) {
            throw new Exception("El nodo periférico ya está registrado.");
        }

        PeripheralNode node = new PeripheralNode();
        node.setId(java.util.UUID.randomUUID().toString());
        node.setName(request.getName());
        node.setUrl(request.getUrl());
        node.setVerificationType(request.getVerificationType());
        node.setActive(true);

        return peripheralNodeRepository.save(node);
    }

    public PeripheralNode obtenerNodoPerifericoPorId(String id) throws Exception {
        return peripheralNodeRepository.findById(id)
                .orElseThrow(() -> new Exception("Nodo periférico no encontrado."));
    }

    public List<PeripheralNode> listarTodos() throws IOException {
        try {
            return peripheralNodeRepository.findAll();
        } catch (IOException e) {
            log.error("Error fetching peripheral nodes: ", e);
            throw e;
        }
    }

}
