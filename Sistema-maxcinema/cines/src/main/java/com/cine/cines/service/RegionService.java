package com.cine.cines.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cine.cines.DTO.RegionDTO;
import com.cine.cines.model.Region;
import com.cine.cines.repository.RegionRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class RegionService {
    
    @Autowired
    private RegionRepository regionRepository;

    private RegionDTO convertirADTO(Region region) {

        RegionDTO dto = new RegionDTO();
        dto.setRegion_id(region.getId());
        dto.setNombre(region.getNombre());
        return dto;
    }
    
    public List<RegionDTO> listarRegion() {
    return regionRepository.findAll().stream()
            .map(this::convertirADTO)
            .toList();
    }

        public RegionDTO guardarRegion(Region region) {
        Region guardado = regionRepository.save(region);
        return convertirADTO(guardado);
    }

    public RegionDTO actualizarRegion(Integer id, Region region) {

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
    public RegionDTO buscarRegionPorId(Integer id) {
        Region region = regionRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("imposible encontrar la region con id " + id));
        return convertirADTO(region);
    }
}
