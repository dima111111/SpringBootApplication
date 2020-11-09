package org.example;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "products", path = "products")
public interface ProductRepository extends PagingAndSortingRepository<productEntity, Long> {

    List<productEntity> findByName(@Param("name") String name);
    List<productEntity> findByBrand(@Param("brand") String brand);
}
