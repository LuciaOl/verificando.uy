package verificando.uy.dtos;

import java.util.List;

public class HechosVerificadosResponseDTO {
    private int cantidadHechos;
    private List<CategoryHechosDTO> categorias;

    // Getters y setters
    public int getCantidadHechos() {
        return cantidadHechos;
    }

    public void setCantidadHechos(int cantidadHechos) {
        this.cantidadHechos = cantidadHechos;
    }

    public List<CategoryHechosDTO> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<CategoryHechosDTO> categorias) {
        this.categorias = categorias;
    }
}
