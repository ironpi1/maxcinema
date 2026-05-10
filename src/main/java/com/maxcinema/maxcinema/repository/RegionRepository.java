package com.maxcinema.maxcinema.repository;
import com.maxcinema.maxcinema.model.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface RegionRepository extends JpaRepository <Region, Integer>{
    

}
