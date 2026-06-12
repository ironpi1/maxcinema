package com.maxcinema.maxcinema.service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maxcinema.maxcinema.DTO.TipoSalaDto;
import com.maxcinema.maxcinema.model.TipoSala;
import com.maxcinema.maxcinema.repository.TipoSalaRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class TipoSalaService {
    @Autowired
    private TipoSalaRepository tipoSalaRepository;

    private TipoSalaDto convertirADTO(TipoSala tipoSala) {
        TipoSalaDto dto = new TipoSalaDto();
        dto.setId(tipoSala.getId());
        dto.setNombre(tipoSala.getNombre());

        if (tipoSala.getTiposSalas() != null && !tipoSala.getTiposSalas().isEmpty()) {
            dto.setNombreTiposSalas(tipoSala.getTiposSalas().get(0).getNombre());
        }

        return dto;
    }

    public List<TipoSalaDto> listarTipoSala() {
        return tipoSalaRepository.findAll().stream()
                .map(this::convertirADTO)
                .toList();
    }

    public TipoSalaDto guardarTipoSala(TipoSala tipoSala) {
        TipoSala guardado = tipoSalaRepository.save(tipoSala);
        return convertirADTO(guardado);
    }

    public String eliminarTipoSala(Integer id) {
        try {
            TipoSala tipoSala = tipoSalaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("imposible eliminar con id, el id " + id + " no existe"));
            tipoSalaRepository.delete(tipoSala);
            return "el tipo de sala '" + tipoSala.getNombre() + "' ha sido eliminado exitosamente";
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

    public TipoSalaDto actualizarTipoSala(Integer id, TipoSala tipoSala) {
        TipoSala tipoSala2 = tipoSalaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("imposible de encontrar el id, el id " + id + " no existe"));
        if (tipoSala.getNombre() != null) {
            tipoSala2.setNombre(tipoSala.getNombre());
        }
        return convertirADTO(tipoSalaRepository.save(tipoSala2));
    }

    public List<TipoSalaDto> buscarPortipoSalaPorNombre(String nombre) {
        return tipoSalaRepository.findByNombre(nombre).stream()
                .map(this::convertirADTO)
                .toList();
    }
    public TipoSalaDto buscarTipoSalaPorId(Integer id) {
        TipoSala tipoSala = tipoSalaRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("imposible encontrar el tipo de sala con id " + id));
        return convertirADTO(tipoSala);
    }
}