package dev.markmuhari.webshop.backend.config;

import dev.markmuhari.webshop.backend.model.Product;
import dev.markmuhari.webshop.backend.model.ProductCategory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.metamodel.EntityType;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Configuration
@AllArgsConstructor
public class MyDataRestConfig implements RepositoryRestConfigurer {


    private final EntityManager entityManager;

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
        HttpMethod[] theUnsupportedActions ={HttpMethod.PUT, HttpMethod.DELETE, HttpMethod.POST};
        // Disable the HTTP methods for Product: PUT, DELETE, POST
        config.getExposureConfiguration()
                .forDomainType(Product.class)
                .withItemExposure((metadata, httpMethods) -> httpMethods.disable(theUnsupportedActions))
                .withCollectionExposure((metadata, httpMethods) -> httpMethods.disable(theUnsupportedActions));

        config.getExposureConfiguration()
                .forDomainType(ProductCategory.class)
                .withItemExposure((metadata, httpMethods) -> httpMethods.disable(theUnsupportedActions))
                .withCollectionExposure((metadata, httpMethods) -> httpMethods.disable(theUnsupportedActions));

        // call an internal helper method
        exposeIds(config);
    }

    private void exposeIds(RepositoryRestConfiguration config) {

        // - get a list of all entity classes from the Entity manager
        Set<EntityType<?>> entities = entityManager.getMetamodel().getEntities();

        // create an array of entity types
        List<Class<?>> entityClasses = new ArrayList<>();

        // get the entities types for the entities
        for (EntityType<?> entity : entities) {
            entityClasses.add(entity.getJavaType());
        }
        // expose the entity ids for the array of entity/domain types
        Class<?>[] domainType = entityClasses.toArray(new Class[0]);
        // add the entity classes to the config
        config.exposeIdsFor(domainType);
    }

}
