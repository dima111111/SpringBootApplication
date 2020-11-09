package org.bmstu.repository;

import java.util.List;

import org.bmstu.model.productEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * This repository is an interface and allows you to perform various operations involving
 * productEntity objects. It receives these operations by inheriting the PagingAndSortingRepository
 * interface defined in the Spring Data Commons.
 * At runtime, Spring Data REST will create an implementation of this interface automatically.
 * It will then use the @RepositoryRestResource annotation, referring to Spring MVC to create
 * a restful "/products" exit point
 */
@RepositoryRestResource(collectionResourceRel = "products", path = "products")
public interface ProductRepository extends PagingAndSortingRepository<productEntity, Long> {

    /**
     * request method to get a list of productEntity objects by a given name
     * @param name string Product name
     * @return List<productEntity>
     */
    List<productEntity> findByName(@Param("name") String name);
    /**
     * request method to get a list of productEntity objects by a given brand
     * @param brand string Product brand
     * @return List<productEntity>
     */
    List<productEntity> findByBrand(@Param("brand") String brand);
    /**
     * request method to get a list of productEntity objects which quantity is less than a given parameter
     * @param quantity string Product brand
     * @return List<productEntity>
     */
    List<productEntity> findByQuantityLessThan(@Param("quantity") int quantity);
}
