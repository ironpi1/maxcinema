package com.maxcinema.maxcinema.service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.maxcinema.maxcinema.DTO.TiposSalasDto;
import com.maxcinema.maxcinema.model.TiposSalas;
import com.maxcinema.maxcinema.repository.TipoSalasRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class TiposSalasService {
    @Autowired
    private TipoSalasRepository tipoSalasRepository;

    private TiposSalasDto convertirADTO(TiposSalas tiposSalas) {
    TiposSalasDto dto = new TiposSalasDto();
    dto.setId(tiposSalas.getId());
    dto.setNombre(tiposSalas.getNombre());

    if (tiposSalas.getSala() != null) {
        dto.setNombreSala(tiposSalas.getSala().getNombre());
    }
    if (tiposSalas.getTipoSala() != null) {
        dto.setNombreTipoSala(tiposSalas.getTipoSala().getNombre());
    }

    return dto;
    }

    public List<TiposSalasDto> ListarTiposSalas() {
        return tipoSalasRepository.findAll().stream()
                .map(this::convertirADTO)
                .toList();
    }

    public TiposSalasDto guardarTiposSalas(TiposSalas tiposSalas) {
        TiposSalas guardado = tipoSalasRepository.save(tiposSalas);
        return convertirADTO(guardado);
    }

    public String eliminarTiposDeSalas(Integer id) {
        try {
            TiposSalas tiposSalas = tipoSalasRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("imposible eliminar con id, el id " + id + " no existe"));
            tipoSalasRepository.delete(tiposSalas);
            return "El tipo de sala '" + tiposSalas.getNombre() + "' ha sido eliminado exitosamente";
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

    public TiposSalasDto actualizar(Integer id, TiposSalas tiposSalas) {
        TiposSalas tiposSalas2 = tipoSalasRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("imposible encontrar con id, el id " + id + " no existe"));
        if (tiposSalas.getNombre() != null) {
            tiposSalas2.setNombre(tiposSalas.getNombre());
        }
        return convertirADTO(tipoSalasRepository.save(tiposSalas2));
    }

    public List<TiposSalasDto> buscarTiposSalas(String nombre) {
        return tipoSalasRepository.findByNombre(nombre).stream()
                .map(this::convertirADTO)
                .toList();
    }
    public TiposSalasDto buscarTiposSalasPorId(Integer id) {
        TiposSalas tiposSalas = tipoSalasRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("imposible encontrar el tipo de sala con id " + id));
        return convertirADTO(tiposSalas);
    }
}