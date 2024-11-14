package verificando.uy.repositories;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.classic.methods.HttpPut;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import verificando.uy.model.PeripheralNode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class PeripheralNodeRepository {

    @Value("${couchdb.url}")
    private String couchDbUrl;

    @Value("${couchdb.database}")
    private String database;

    @Value("${couchdb.username}")
    private String username;

    @Value("${couchdb.password}")
    private String password;

    private final ObjectMapper objectMapper = new ObjectMapper();

    // Guardar o actualizar un nodo
    public PeripheralNode save(PeripheralNode node) throws IOException {
        String url = couchDbUrl + "/" + database + "/" + node.getId();
        HttpPut request = new HttpPut(url);

        request.setHeader("Authorization", "Basic " +
                java.util.Base64.getEncoder().encodeToString((username + ":" + password).getBytes()));
        request.setHeader("Content-Type", "application/json");

        String json = objectMapper.writeValueAsString(node);
        request.setEntity(new StringEntity(json));

        try (CloseableHttpClient client = HttpClients.createDefault();
             CloseableHttpResponse response = client.execute(request)) {
            if (response.getCode() >= 200 && response.getCode() < 300) {
                return node;
            } else {
                throw new IOException("Error al guardar el nodo: " + response.getCode());
            }
        }
    }

    // Buscar un nodo por ID
    public Optional<PeripheralNode> findById(String id) throws IOException {
        String url = couchDbUrl + "/" + database + "/" + id;
        HttpGet request = new HttpGet(url);

        request.setHeader("Authorization", "Basic " +
                java.util.Base64.getEncoder().encodeToString((username + ":" + password).getBytes()));

        try (CloseableHttpClient client = HttpClients.createDefault();
             CloseableHttpResponse response = client.execute(request)) {
            if (response.getCode() == 200) {
                PeripheralNode node = objectMapper.readValue(response.getEntity().getContent(), PeripheralNode.class);
                return Optional.of(node);
            } else if (response.getCode() == 404) {
                return Optional.empty();
            } else {
                throw new IOException("Error al buscar el nodo: " + response.getCode());
            }
        }
    }

    // Buscar un nodo por URL
    public Optional<PeripheralNode> findByUrl(String url) throws IOException {
        String queryUrl = couchDbUrl + "/" + database + "/_find";
        HttpPost request = new HttpPost(queryUrl);

        request.setHeader("Authorization", "Basic " +
                java.util.Base64.getEncoder().encodeToString((username + ":" + password).getBytes()));
        request.setHeader("Content-Type", "application/json");

        String query = String.format("{\"selector\": {\"url\": \"%s\"}}", url);
        request.setEntity(new StringEntity(query));

        try (CloseableHttpClient client = HttpClients.createDefault();
             CloseableHttpResponse response = client.execute(request)) {
            if (response.getCode() == 200) {
                var responseBody = new String(response.getEntity().getContent().readAllBytes());
                var docs = objectMapper.readTree(responseBody).get("docs");

                if (docs.size() > 0) {
                    PeripheralNode node = objectMapper.treeToValue(docs.get(0), PeripheralNode.class);
                    return Optional.of(node);
                }
            }
            return Optional.empty();
        }
    }

    // Listar todos los nodos
    public List<PeripheralNode> findAll() throws IOException {
        String url = couchDbUrl + "/" + database + "/_all_docs?include_docs=true";
        HttpGet request = new HttpGet(url);

        request.setHeader("Authorization", "Basic " +
                java.util.Base64.getEncoder().encodeToString((username + ":" + password).getBytes()));

        try (CloseableHttpClient client = HttpClients.createDefault();
             CloseableHttpResponse response = client.execute(request)) {
            if (response.getCode() == 200) {
                var responseBody = new String(response.getEntity().getContent().readAllBytes());
                var rows = objectMapper.readTree(responseBody).get("rows");
                List<PeripheralNode> nodes = new ArrayList<>();

                for (var row : rows) {
                    var doc = row.get("doc");
                    nodes.add(objectMapper.treeToValue(doc, PeripheralNode.class));
                }
                return nodes;
            } else {
                throw new IOException("Error al obtener nodos: " + response.getCode());
            }
        }
    }
}
