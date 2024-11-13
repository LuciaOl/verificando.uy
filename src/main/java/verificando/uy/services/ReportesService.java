package verificando.uy.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import verificando.uy.repositories.HechoRepository;
import verificando.uy.dtos.HechosVerificadosResponseDTO;
import verificando.uy.dtos.CategoriaHechosDTO;
import verificando.uy.dtos.CategoriaHechosDTO.HechoDTO;  // Importa HechoDTO directamente

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Comparator;
import java.util.stream.Collectors;

@Service
public class ReportesService {

    private HechoRepository hechoRepository;

    @Autowired
    public ReportesService(HechoRepository hechoRepository) {
        this.hechoRepository = hechoRepository;
    }

    public List<CategoriaHechosDTO> getTopCategoriasDeHechos(LocalDateTime desde, LocalDateTime hasta) {
        // Obtenemos los resultados de la consulta del repository
        List<Object[]> categoriasData = hechoRepository.getCategoriasConHechosYCantidad(desde, hasta);

        // Mapa para agrupar los hechos por categoria
        Map<String, CategoriaHechosDTO> categoriasMap = new HashMap<>();

        // Procesamos los resultados de la consulta
        for (Object[] obj : categoriasData) {
            String categoria = (String) obj[0];  // Nombre de la categoria
            Long idHecho = (Long) obj[2];        // ID del hecho
            String tituloHecho = (String) obj[3]; // Titulo del hecho
            int cantidadHechos = ((Long) obj[1]).intValue();  // Cantidad de hechos por categoria

            // Si la categoria no existe en el mapa, la creamos
            CategoriaHechosDTO categoriaDTO = categoriasMap.get(categoria);
            if (categoriaDTO == null) {
                categoriaDTO = new CategoriaHechosDTO();
                categoriaDTO.setNombreCategoria(categoria);
                categoriaDTO.setCantidadHechos(cantidadHechos);
                categoriaDTO.setHechos(new ArrayList<>());
                categoriasMap.put(categoria, categoriaDTO);
            }

            // Anadimos el hecho a la categoria
            CategoriaHechosDTO.HechoDTO hechoDTO = new CategoriaHechosDTO.HechoDTO();  // Crear un HechoDTO desde la clase interna
            hechoDTO.setId(idHecho);
            hechoDTO.setTitulo(tituloHecho);
            categoriaDTO.getHechos().add(hechoDTO);
        }

        // Convertimos el mapa en una lista y la ordenamos por la cantidad de hechos
        List<CategoriaHechosDTO> categorias = new ArrayList<>(categoriasMap.values());
        categorias.sort(Comparator.comparingInt(CategoriaHechosDTO::getCantidadHechos).reversed());  // Ordenamos por cantidad de hechos

        return categorias;
    }

    public HechosVerificadosResponseDTO getHechosVerificadosEntreFechas(LocalDateTime desde, LocalDateTime hasta) {
        List<Object[]> topCategorias = hechoRepository.getTopCategoriasConHechos(desde, hasta);

        int cantidadHechos = 0;
        List<CategoriaHechosDTO> categorias = new ArrayList<>();

        // Procesamos la respuesta de la consulta y agrupamos los hechos por categoria
        for (Object[] obj : topCategorias) {
            String categoria = (String) obj[0];
            int cantidadCategoria = ((Long) obj[1]).intValue();  // Convertimos el conteo de hechos a int

            // Incrementamos el total de hechos
            cantidadHechos += cantidadCategoria;

            // Obtenemos los hechos de esta categoria (limitar a 3)
            List<Object[]> hechosDeCategoria = hechoRepository.getHechosPorCategoria(categoria, desde, hasta);

            // Limitar a 3 hechos
            List<CategoriaHechosDTO.HechoDTO> hechosDTO = new ArrayList<>();  // Usamos directamente HechoDTO
            for (int i = 0; i < Math.min(3, hechosDeCategoria.size()); i++) {
                Object[] hecho = hechosDeCategoria.get(i);
                CategoriaHechosDTO.HechoDTO hechoDTO = new CategoriaHechosDTO.HechoDTO();  // Crear un HechoDTO desde la clase interna
                hechoDTO.setId((Long) hecho[0]);
                hechoDTO.setTitulo((String) hecho[1]);
                hechosDTO.add(hechoDTO);
            }

            // Crear el DTO de categoria con los hechos obtenidos
            CategoriaHechosDTO categoriaDTO = new CategoriaHechosDTO();
            categoriaDTO.setNombreCategoria(categoria);
            categoriaDTO.setHechos(hechosDTO);

            categorias.add(categoriaDTO);
        }

        // Limitar la cantidad de categorias a 3
        categorias = categorias.stream()
                               .limit(3)  // Solo traemos las primeras 3 categorias
                               .collect(Collectors.toList());

        // Crear y devolver el DTO final
        HechosVerificadosResponseDTO response = new HechosVerificadosResponseDTO();
        response.setCantidadHechos(cantidadHechos);
        response.setCategorias(categorias);

        return response;
    }
}
