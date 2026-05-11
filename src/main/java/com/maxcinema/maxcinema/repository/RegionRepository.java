package com.maxcinema.maxcinema.repository;
import com.maxcinema.maxcinema.model.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegionRepository extends JpaRepository <Region, Integer>{
    

}
