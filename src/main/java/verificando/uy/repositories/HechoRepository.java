package verificando.uy.repositories;

import verificando.uy.model.Hecho;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;


public interface HechoRepository extends JpaRepository<Hecho, Long>  {
    public Optional<Hecho> findById(Long id);

    
    ///REPORTES
    @Query("SELECT h.categoria, COUNT(h) FROM Hecho h WHERE h.fechaCreacion BETWEEN :desde AND :hasta GROUP BY h.categoria ORDER BY COUNT(h) DESC")
    List<Object[]> getTopCategoriasConHechos(@Param("desde") LocalDateTime desde, @Param("hasta") LocalDateTime hasta);
    
    @Query("SELECT h.id, h.titulo FROM Hecho h WHERE h.categoria = :categoria AND h.fechaCreacion BETWEEN :desde AND :hasta")
    List<Object[]> getHechosPorCategoria(@Param("categoria") String categoria, @Param("desde") LocalDateTime desde, @Param("hasta") LocalDateTime hasta);
    
    
    
    
    @Query("SELECT h.categoria, COUNT(h), h.id, h.titulo " +
    	       "FROM Hecho h WHERE h.fechaCreacion BETWEEN :desde AND :hasta " +
    	       "GROUP BY h.categoria " +
    	       "ORDER BY COUNT(h) DESC")
    	List<Object[]> getCategoriasConHechosYCantidad(@Param("desde") LocalDateTime desde, @Param("hasta") LocalDateTime hasta);
    ///REPORTES


}
