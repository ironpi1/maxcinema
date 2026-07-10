package com.cine.cines.DTO;

import com.cine.cines.model.Region;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class RegionDTO {
    private Integer region_id;
    private String nombre;
}