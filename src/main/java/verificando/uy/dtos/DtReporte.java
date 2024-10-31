package verificando.uy.dtos;

import verificando.uy.enums.Status;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter

public class DtReporte {
    private LocalDateTime fechaDesde;
    private LocalDateTime fechaHasta;
}
