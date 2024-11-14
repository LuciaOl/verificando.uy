package verificando.uy.dtos;

import java.util.List;

public class DtReporte {
    private int cantidadHechos;
    private List<DtSubReporte> categorias;

    // Getters y setters
    public int getCantidadHechos() {
        return cantidadHechos;
    }

    public void setCantidadHechos(int cantidadHechos) {
        this.cantidadHechos = cantidadHechos;
    }

    public List<DtSubReporte> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<DtSubReporte> categorias) {
        this.categorias = categorias;
    }
}
