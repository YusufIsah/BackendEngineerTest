package data.mapper.repository;


import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;

/**
 * ProviderSpecification
 */
@ApplicationScoped
public class ProviderSpecificationRepo {

    Set<Map<String, Object>> providers;

    public  ProviderSpecificationRepo() {
        this.providers = new HashSet<>();
    }

    public Map<String, Object> addProvider(Map<String, Object> provider){
        //this check if old provider exist it then remove it and replace it with new one
        if (!providers.isEmpty()) {
            providers.stream().filter(p -> 
            p.containsValue(provider.get("providerId"))).findFirst().ifPresent(p -> p.clear());
        }
        providers.add(provider);
        return provider;
    }

    public Map<String, Object> findProviderById(Integer providerId) {
     return providers.stream().filter(provider -> provider.containsValue(providerId)).findFirst().get();

    }
}