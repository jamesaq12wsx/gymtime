package com.jamesaq12wsx.gymtime.database;

import com.jamesaq12wsx.gymtime.model.Brand;
import com.jamesaq12wsx.gymtime.model.BrandAudit;
import com.jamesaq12wsx.gymtime.model.BrandWithCountry;

import java.util.List;

public interface BrandDao extends Dao<Brand, Integer> {

    List<BrandWithCountry> getAllWithCountry();

}
