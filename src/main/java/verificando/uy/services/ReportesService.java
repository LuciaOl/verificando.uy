package verificando.uy.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import verificando.uy.repositories.HechoRepository;
import verificando.uy.dtos.HechosVerificadosResponseDTO;
import verificando.uy.dtos.CategoryHechosDTO;
import verificando.uy.dtos.CategoryHechosDTO.HechoDTO;
import verificando.uy.model.Hecho;
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
        List<Object[]> categorysData = hechoRepository.getCategorysConHechosYCantidad(desde, hasta);

        Map<String, CategoryHechosDTO> categorysMap = new HashMap<>();

        for (Object[] obj : categorysData) {
            String category = (String) obj[0];
            Long idHecho = (Long) obj[2];
            String tituloHecho = (String) obj[3];
            int cantidadHechos = ((Long) obj[1]).intValue();

            CategoryHechosDTO categoryDTO = categorysMap.get(category);
            if (categoryDTO == null) {
                categoryDTO = new CategoryHechosDTO();
                categoryDTO.setNombreCategoria(category);
                categoryDTO.setCantidadHechos(cantidadHechos);
                categoryDTO.setHechos(new ArrayList<>());
                categorysMap.put(category, categoryDTO);
            }

            HechoDTO hechoDTO = new HechoDTO();
            hechoDTO.setId(idHecho);
            hechoDTO.setTitulo(tituloHecho);
            categoryDTO.getHechos().add(hechoDTO);
        }

        List<CategoryHechosDTO> categorys = new ArrayList<>(categorysMap.values());
        categorys.sort(Comparator.comparingInt(CategoryHechosDTO::getCantidadHechos).reversed());

        return categorys;
    }

    public HechosVerificadosResponseDTO getHechosVerificadosEntreFechas(LocalDateTime desde, LocalDateTime hasta) {
        List<Object[]> topcategorys = hechoRepository.getTopCategorysDeHechos(desde, hasta);

        int cantidadHechos = 0;
        List<CategoryHechosDTO> categorys = new ArrayList<>();

        for (Object[] obj : topcategorys) {
            String category = (String) obj[0];
            int cantidadcategory = ((Long) obj[1]).intValue();

            cantidadHechos += cantidadcategory;

            // Update this part to retrieve List<Hecho> instead of List<Object>
            List<Hecho> hechosDecategory = hechoRepository.getHechosPorCategory(category, desde, hasta);

            List<HechoDTO> hechosDTO = new ArrayList<>();
            for (int i = 0; i < Math.min(3, hechosDecategory.size()); i++) {
                Hecho hecho = hechosDecategory.get(i);
                HechoDTO hechoDTO = new HechoDTO();
                hechoDTO.setId(hecho.getId());
                hechoDTO.setTitulo(hecho.getDescription());// TODO: juan lo cambia
                hechosDTO.add(hechoDTO);
            }

            CategoryHechosDTO categoryDTO = new CategoryHechosDTO();
            categoryDTO.setNombreCategoria(category);
            categoryDTO.setHechos(hechosDTO);

            categorys.add(categoryDTO);
        }

        // Limitar la cantidad de categorías a 3
        categorys = categorys.stream()
                .limit(3)
                .collect(Collectors.toList());

        HechosVerificadosResponseDTO response = new HechosVerificadosResponseDTO();
        response.setCantidadHechos(cantidadHechos);
        response.setCategorias(categorys);

        return response;
    }
}
