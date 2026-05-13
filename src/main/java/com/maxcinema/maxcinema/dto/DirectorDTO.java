package com.maxcinema.maxcinema.DTO;

import java.util.List;
import com.maxcinema.maxcinema.model.Directores;
import lombok.Data;
@Data
public class DirectorDTO {

    private Integer id;
    private String nombre;
    private String nacionalidad;
    private List<Directores> directores;

}
