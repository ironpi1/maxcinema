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
}