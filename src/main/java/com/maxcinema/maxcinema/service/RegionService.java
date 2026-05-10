package com.maxcinema.maxcinema.service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.maxcinema.maxcinema.model.Region;
import com.maxcinema.maxcinema.repository.RegionRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class RegionService {
    @Autowired
    private RegionRepository regionRepository;
    
    public List<Region> ListarRegion(){
        return regionRepository.findAll();
    }

    public Region guardaRegion(Region region){
        return regionRepository.save(region);
    }

    public String eliminarRegion( Integer id){
        try {
            Region region = regionRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("imposible de eliminar con id, el " + id + "no existe"));
            regionRepository.delete(region);
            return "la region " + region.getNombre() + "ha sido eliminado exitosamente";      
        } catch (RuntimeException e) {
            return e.getMessage();
        }
        
    }

    public Region actualizarRegion(Integer id, Region region){
        Region region2 = regionRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("imposible de encontrar con id, el id " + id + "no existe"));
        if(region.getNombre() != null){
            region2.setNombre(region.getNombre());
        }
        return regionRepository.save(region2);
    }
}