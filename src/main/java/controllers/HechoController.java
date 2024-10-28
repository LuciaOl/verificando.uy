package controllers;

import model.Hecho;
import org.junit.platform.engine.support.hierarchical.Node;
import org.springframework.web.bind.annotation.*;
import services.HechoService;
import services.NodeService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/hechos")
public class HechoController {
    private Hecho hechoParaVerificar;
    private Node nodoSeleccionado;

    private final HechoService hechoService;
    private final NodeService nodeService;

    public HechoController(HechoService hechoService, NodeService nodeService) {
        this.hechoParaVerificar = null;
        this.hechoService = hechoService;
        this.nodeService = nodeService;
    }

    @PostMapping("/crear")
    public Hecho crearHecho(@RequestBody Hecho hecho) {
        return hechoService.crearHecho(hecho);
    }

    @GetMapping("/{factID}")
    public Hecho obtenerHecho(@PathVariable String factID) {
        Optional<Hecho> hecho = hechoService.obtenerHecho(factID);
        return hecho.orElse(null); // Retorna null si no se encuentra el hecho
    }

    @GetMapping("/enProceso")
    public Optional<List<Hecho>> obtenerHechosEnProceso(){
        Optional<List<Hecho>> hechos = hechoService.obtenerTodosLosHechos();

        return hechos.map(listaHechos ->
                listaHechos.stream()
                        .filter(hecho -> "Pendiente".equals(hecho.getStatus()))
                        .collect(Collectors.toList()));
    }

    @PutMapping("/verificar/{factID}")
    public Hecho seleccionarHechoParaVerificar(@PathVariable String factID){
        this.hechoParaVerificar = obtenerHecho(factID);

        return this.hechoParaVerificar;
    }


    @GetMapping("/obtenerNodos")
    public Optional<List<Node>> obtenerNodos(){
        return this.nodeService.obtenerTodosLosNodos();
    }

    @PutMapping("/nodos/{nodeID}")
    public Node seleccionarNodoParaVerificar(@PathVariable String nodeID){
        this.nodoSeleccionado = obtener
    }


    @PutMapping("/{factID}")
    public Hecho actualizarHecho(@PathVariable String factID, @RequestBody Hecho hecho) {
        Optional<Hecho> hechoActualizado = hechoService.actualizarHecho(factID, hecho);
        return hechoActualizado.orElse(null); // Retorna null si no se encuentra el hecho
    }
}
