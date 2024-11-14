package verificando.uy.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import verificando.uy.repositories.HechoRepository;
import verificando.uy.dtos.HechosVerificadosResponseDTO;
import verificando.uy.dtos.CategoryHechosDTO;
import verificando.uy.dtos.CategoryHechosDTO.HechoDTO;  // Importa HechoDTO directamente

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

    public List<CategoryHechosDTO> getTopCategorysDeHechos(LocalDateTime desde, LocalDateTime hasta) {
        // Obtenemos los resultados de la consulta del repository
        List<Object[]> categorysData = hechoRepository.getCategorysConHechosYCantidad(desde, hasta);

        // Mapa para agrupar los hechos por category
        Map<String, CategoryHechosDTO> categorysMap = new HashMap<>();

        // Procesamos los resultados de la consulta
        for (Object[] obj : categorysData) {
            String category = (String) obj[0];  // Nombre de la category
            Long idHecho = (Long) obj[2];        // ID del hecho
            String tituloHecho = (String) obj[3]; // Titulo del hecho
            int cantidadHechos = ((Long) obj[1]).intValue();  // Cantidad de hechos por category

            // Si la category no existe en el mapa, la creamos
            CategoryHechosDTO categoryDTO = categorysMap.get(category);
            if (categoryDTO == null) {
                categoryDTO = new CategoryHechosDTO();
                categoryDTO.setNombreCategoria(category);
                categoryDTO.setCantidadHechos(cantidadHechos);
                categoryDTO.setHechos(new ArrayList<>());
                categorysMap.put(category, categoryDTO);
            }

            // Anadimos el hecho a la category
            CategoryHechosDTO.HechoDTO hechoDTO = new CategoryHechosDTO.HechoDTO();  // Crear un HechoDTO desde la clase interna
            hechoDTO.setId(idHecho);
            hechoDTO.setTitulo(tituloHecho);
            categoryDTO.getHechos().add(hechoDTO);
        }

        // Convertimos el mapa en una lista y la ordenamos por la cantidad de hechos
        List<CategoryHechosDTO> categorys = new ArrayList<>(categorysMap.values());
        categorys.sort(Comparator.comparingInt(CategoryHechosDTO::getCantidadHechos).reversed());  // Ordenamos por cantidad de hechos

        return categorys;
    }

    public HechosVerificadosResponseDTO getHechosVerificadosEntreFechas(LocalDateTime desde, LocalDateTime hasta) {
        List<Object[]> topcategorys = hechoRepository.getTopCategorysDeHechos(desde, hasta);

        int cantidadHechos = 0;
        List<CategoryHechosDTO> categorys = new ArrayList<>();

        // Procesamos la respuesta de la consulta y agrupamos los hechos por category
        for (Object[] obj : topcategorys) {
            String category = (String) obj[0];
            int cantidadcategory = ((Long) obj[1]).intValue();  // Convertimos el conteo de hechos a int

            // Incrementamos el total de hechos
            cantidadHechos += cantidadcategory;

            // Obtenemos los hechos de esta category (limitar a 3)
            List<Object[]> hechosDecategory = hechoRepository.getHechosPorCategory(category, desde, hasta);

            // Limitar a 3 hechos
            List<CategoryHechosDTO.HechoDTO> hechosDTO = new ArrayList<>();  // Usamos directamente HechoDTO
            for (int i = 0; i < Math.min(3, hechosDecategory.size()); i++) {
                Object[] hecho = hechosDecategory.get(i);
                CategoryHechosDTO.HechoDTO hechoDTO = new CategoryHechosDTO.HechoDTO();  // Crear un HechoDTO desde la clase interna
                hechoDTO.setId((Long) hecho[0]);
                hechoDTO.setTitulo((String) hecho[1]);
                hechosDTO.add(hechoDTO);
            }

            // Crear el DTO de category con los hechos obtenidos
            CategoryHechosDTO categoryDTO = new CategoryHechosDTO();
            categoryDTO.setNombreCategoria(category);
            categoryDTO.setHechos(hechosDTO);

            categorys.add(categoryDTO);
        }

        // Limitar la cantidad de categorys a 3
        categorys = categorys.stream()
                               .limit(3)  // Solo traemos las primeras 3 categorys
                               .collect(Collectors.toList());

        // Crear y devolver el DTO final
        HechosVerificadosResponseDTO response = new HechosVerificadosResponseDTO();
        response.setCantidadHechos(cantidadHechos);
        response.setCategorias(categorys);

        return response;
    }
}
