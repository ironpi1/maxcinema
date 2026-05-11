package com.maxcinema.maxcinema.service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maxcinema.maxcinema.DTO.RegionDto;
import com.maxcinema.maxcinema.model.Region;
import com.maxcinema.maxcinema.repository.RegionRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class RegionService {
    @Autowired
    private RegionRepository regionRepository;

    private RegionDto convertirADTO(Region region) {

        RegionDto dto = new RegionDto();
        dto.setRegion_id(region.getId());
        dto.setNombre(region.getNombre());
        return dto;
    }
    
    public List<RegionDto> ListarRegion() {
    return regionRepository.findAll().stream()
            .map(this::convertirADTO)
            .toList();
    }

        public RegionDto guardaRegion(Region region) {
        Region guardado = regionRepository.save(region);
        return convertirADTO(guardado);
    }

    public RegionDto actualizarRegion(Integer id, Region region) {

        Region region2 = regionRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("imposible de encontrar con id, el id " + id + " no existe"));
        if (region.getNombre() != null) {
        region2.setNombre(region.getNombre());
        }
        return convertirADTO(regionRepository.save(region2));
    }
    public String eliminarRegion(Integer id) {
    try {
        Region region = regionRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("imposible de eliminar con id, el " + id + " no existe"));
        regionRepository.delete(region);
        return "la region " + region.getNombre() + " ha sido eliminado exitosamente";
    } catch (RuntimeException e) {
        return e.getMessage();
    }
    }
    public RegionDto buscarRegionPorId(Integer id) {
        Region region = regionRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("imposible encontrar la region con id " + id));
        return convertirADTO(region);
    }
}