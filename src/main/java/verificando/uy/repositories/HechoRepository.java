package verificando.uy.repositories;

import verificando.uy.model.Hecho;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.Optional;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;


public interface HechoRepository extends JpaRepository<Hecho, Long>  {
    public Optional<Hecho> findById(Long id);

    
    ///REPORTES
    ///HECHO DEBERIA TENER FECHA DE VERIFICACION
    @Query("SELECT h.category, COUNT(h) FROM Hecho h WHERE h.status = verificando.uy.enums.Status.VERIFICADO AND h.fechaCreacion BETWEEN :desde AND :hasta GROUP BY h.category ORDER BY COUNT(h) DESC")
    List<Object[]> getTopCategorysVerificadasPorFecha(@Param("desde") LocalDateTime desde, @Param("hasta") LocalDateTime hasta);
    
    @Query("SELECT h.category, COUNT(h) FROM Hecho h WHERE h.fechaCreacion BETWEEN :desde AND :hasta GROUP BY h.category ORDER BY COUNT(h) DESC")
    List<Object[]> getTopCategorysConMasHechos(@Param("desde") LocalDateTime desde, @Param("hasta") LocalDateTime hasta);
    
    @Query("SELECT h.id, h.description FROM Hecho h WHERE h.status = verificando.uy.enums.Status.VERIFICADO AND h.category = :category AND h.fechaCreacion BETWEEN :desde AND :hasta")
    List<Object[]> getHechosVerificadosPorCategory(@Param("category") String category, @Param("desde") LocalDateTime desde, @Param("hasta") LocalDateTime hasta);
    
    @Query("SELECT h.id, h.description FROM Hecho h WHERE h.category = :category AND h.fechaCreacion BETWEEN :desde AND :hasta")
    List<Object[]> getHechosPorCategory(@Param("category") String category, @Param("desde") LocalDateTime desde, @Param("hasta") LocalDateTime hasta);
    
    
    
//    @Query("SELECT h.category, COUNT(h), h.id, h.description " +
//    	       "FROM Hecho h WHERE h.fechaCreacion BETWEEN :desde AND :hasta " +
//    	       "GROUP BY h.category " +
//    	       "ORDER BY COUNT(h) DESC")
//    	List<Object[]> getCategorysConHechosYCantidad(@Param("desde") LocalDateTime desde, @Param("hasta") LocalDateTime hasta);
//    ///REPORTES


}
