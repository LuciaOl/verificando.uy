package verificando.uy.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import verificando.uy.repositories.HechoRepository;

import verificando.uy.dtos.DtReporte;
import verificando.uy.dtos.DtSubReporte;
import verificando.uy.dtos.DtSubReporte.HechoDTO;  // Importa HechoDTO directamente

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

    public DtReporte getTopCategorysDeHechos(LocalDateTime desde, LocalDateTime hasta) {
    	 List<Object[]> topcategorys = hechoRepository.getTopCategorysConMasHechos(desde, hasta);

         int cantidadHechos = 0;
         List<DtSubReporte> categorys = new ArrayList<>();

         // Procesamos la respuesta de la consulta y agrupamos los hechos por category
         for (Object[] obj : topcategorys) {
             String category = (String) obj[0];
             int cantidadcategory = ((Long) obj[1]).intValue();  // Convertimos el conteo de hechos a int

             // Incrementamos el total de hechos
             cantidadHechos += cantidadcategory;

             // Obtenemos los hechos de esta category (limitar a 3)
             List<Object[]> hechosDecategory = hechoRepository.getHechosPorCategory(category, desde, hasta);

             // Limitar a 3 hechos
             List<DtSubReporte.HechoDTO> hechosDTO = new ArrayList<>();  // Usamos directamente HechoDTO
             for (int i = 0; i < Math.min(3, hechosDecategory.size()); i++) {
                 Object[] hecho = hechosDecategory.get(i);
                 DtSubReporte.HechoDTO hechoDTO = new DtSubReporte.HechoDTO();  // Crear un HechoDTO desde la clase interna
                 hechoDTO.setId((Long) hecho[0]);
                 hechoDTO.setTitulo((String) hecho[1]);
                 hechosDTO.add(hechoDTO);
             }

             // Crear el DTO de category con los hechos obtenidos
             DtSubReporte categoryDTO = new DtSubReporte();
             categoryDTO.setNombreCategoria(category);
             categoryDTO.setHechos(hechosDTO);
             categoryDTO.setCantidadHechos(cantidadcategory);

             categorys.add(categoryDTO);
         }

         // Limitar la cantidad de categorys a 3
         categorys = categorys.stream()
                                .limit(3)  // Solo traemos las primeras 3 categorys
                                .collect(Collectors.toList());

         // Crear y devolver el DTO final
         DtReporte response = new DtReporte();
         response.setCantidadHechos(cantidadHechos);
         response.setCategorias(categorys);

         return response;
    }

    public DtReporte getHechosVerificadosEntreFechas(LocalDateTime desde, LocalDateTime hasta) {
        List<Object[]> topcategorys = hechoRepository.getTopCategorysVerificadasPorFecha(desde, hasta);

        int cantidadHechos = 0;
        List<DtSubReporte> categorys = new ArrayList<>();

        // Procesamos la respuesta de la consulta y agrupamos los hechos por category
        for (Object[] obj : topcategorys) {
            String category = (String) obj[0];
            int cantidadcategory = ((Long) obj[1]).intValue();  // Convertimos el conteo de hechos a int

            // Incrementamos el total de hechos
            cantidadHechos += cantidadcategory;

            // Obtenemos los hechos de esta category (limitar a 3)
            List<Object[]> hechosDecategory = hechoRepository.getHechosVerificadosPorCategory(category, desde, hasta);

            // Limitar a 3 hechos
            List<DtSubReporte.HechoDTO> hechosDTO = new ArrayList<>();  // Usamos directamente HechoDTO
            for (int i = 0; i < Math.min(3, hechosDecategory.size()); i++) {
                Object[] hecho = hechosDecategory.get(i);
                DtSubReporte.HechoDTO hechoDTO = new DtSubReporte.HechoDTO();  // Crear un HechoDTO desde la clase interna
                hechoDTO.setId((Long) hecho[0]);
                hechoDTO.setTitulo((String) hecho[1]);
                hechosDTO.add(hechoDTO);
            }

            // Crear el DTO de category con los hechos obtenidos
            DtSubReporte categoryDTO = new DtSubReporte();
            categoryDTO.setNombreCategoria(category);
            categoryDTO.setHechos(hechosDTO);
            categoryDTO.setCantidadHechos(cantidadcategory);

            categorys.add(categoryDTO);
        }

        // Limitar la cantidad de categorys a 3
        categorys = categorys.stream()
                               .limit(3)  // Solo traemos las primeras 3 categorys
                               .collect(Collectors.toList());

        // Crear y devolver el DTO final
        DtReporte response = new DtReporte();
        response.setCantidadHechos(cantidadHechos);
        response.setCategorias(categorys);

        return response;
    }
}
