package verificando.uy.dtos;

import java.util.List;

public class HechosVerificadosResponseDTO {
    private int cantidadHechos;
    private List<CategoriaHechosDTO> categorias;

    // Getters y setters
    public int getCantidadHechos() {
        return cantidadHechos;
    }

    public void setCantidadHechos(int cantidadHechos) {
        this.cantidadHechos = cantidadHechos;
    }

    public List<CategoriaHechosDTO> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<CategoriaHechosDTO> categorias) {
        this.categorias = categorias;
    }
}
