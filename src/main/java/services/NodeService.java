package services;

import model.ColectiveNode;
import model.ExpertsNode;
import org.junit.platform.engine.support.hierarchical.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class NodeService {
    private List<Node> nodos = new ArrayList<Node>();

    public Node crearNodo(Node nodo) {
        nodos.add(nodo);
        return nodo;
    }

    public Optional<Node> obtenerNodo(String id){
        return nodos.stream().filter(nodo -> {
            if(nodo instanceof ColectiveNode){
                return ((ColectiveNode) nodo).getCollectiveId().equals(id);
            }
            else if(nodo instanceof ExpertsNode){
                return ((ExpertsNode) nodo).getExpertId().equals(id);
            }
            return false;
        }).findFirst();
    }

    public Optional<List<Node>> obtenerTodosLosNodos(){
        return Optional.of(nodos.stream().toList());
    }
}
