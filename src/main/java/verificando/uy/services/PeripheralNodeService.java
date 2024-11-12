package verificando.uy.services;

import verificando.uy.dtos.PeripheralNodeRequest;
import verificando.uy.model.PeripheralNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import verificando.uy.repositories.PeripheralNodeRepository;


import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Optional;

@Service
public class PeripheralNodeService {

    private final PeripheralNodeRepository peripheralNodeRepository;

    @Autowired
    public PeripheralNodeService(PeripheralNodeRepository peripheralNodeRepository) {
        this.peripheralNodeRepository = peripheralNodeRepository;
    }

    // Método para validar conectividad
    private boolean validarConectividad(String url) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            int code = connection.getResponseCode();
            return (code >= 200 && code < 300); // Conectividad exitosa si el código de respuesta es 2xx
        } catch (IOException e) {
            return false;
        }
    }

    // Método para crear un nodo periférico
    public PeripheralNode crearNodoPeriferico(PeripheralNodeRequest request) throws Exception {
        if (peripheralNodeRepository.findByUrl(request.getUrl()).isPresent()) {
            throw new Exception("El nodo periférico ya está registrado.");
        }

        // Validar conectividad
        if (!validarConectividad(request.getUrl())) {
            throw new Exception("Error de conectividad al nodo periférico.");
        }

        // Crear y guardar el nodo periférico
        PeripheralNode node = new PeripheralNode();
        node.setName(request.getName());
        node.setUrl(request.getUrl());
        node.setVerificationType(request.getVerificationType());
        node.setActive(true); // Queda activo si la validación es exitosa

        return peripheralNodeRepository.save(node);
    }
    // Método para obtener un nodo periférico por ID
    public PeripheralNode obtenerNodoPerifericoPorId(Long id) throws Exception {
        Optional<PeripheralNode> nodo = peripheralNodeRepository.findById(id);
        if (nodo.isPresent()) {
            return nodo.get();
        } else {
            throw new Exception("Nodo periférico no encontrado.");
        }
    }

    public List<PeripheralNode> obtenerNodos(){
        return peripheralNodeRepository.findAll();
    }
}
