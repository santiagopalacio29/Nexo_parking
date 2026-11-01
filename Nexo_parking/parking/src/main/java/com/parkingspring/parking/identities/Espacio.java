package com.parkingspring.parking.identities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Espacio {
    private Integer idEspacio;
    private Integer numeroEspacio;
    private Integer nivel;
    private Boolean estado;
    private String tipoEspacio;
}
