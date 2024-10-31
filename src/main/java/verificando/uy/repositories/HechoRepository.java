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
    public List<Hecho> getHechos();


        @Query("SELECT h FROM Hecho h WHERE h.fechaCreacion BETWEEN :desde AND :hasta")
        List<Hecho> findHechosBetweenDates(@Param("desde") LocalDateTime desde, @Param("hasta") LocalDateTime hasta);


}
