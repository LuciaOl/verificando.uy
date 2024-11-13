package verificando.uy.dtos;

import java.util.List;

public class CategoriaHechosDTO {
    private String nombreCategoria;
    private int cantidadHechos;
    private List<HechoDTO> hechos;

    // Getters y setters
    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }

    public int getCantidadHechos() {
        return cantidadHechos;
    }

    public void setCantidadHechos(int cantidadHechos) {
        this.cantidadHechos = cantidadHechos;
    }

    public List<HechoDTO> getHechos() {
        return hechos;
    }

    public void setHechos(List<HechoDTO> hechos) {
        this.hechos = hechos;
    }

    // Clase interna para representar un hecho
    public static class HechoDTO {
        private Long id;
        private String titulo;

        // Getters y setters
        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getTitulo() {
            return titulo;
        }

        public void setTitulo(String titulo) {
            this.titulo = titulo;
        }
    }
}
