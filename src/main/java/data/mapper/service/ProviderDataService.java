package data.mapper.service;

import data.mapper.dto.DataMapper;
import data.mapper.repository.ProviderDataRepo;
import data.mapper.repository.ProviderSpecificationRepo;

import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.JsonObject;

@RequestScoped
public class ProviderDataService {


    @Inject
    ProviderDataRepo dataRepository;
    
    @Inject
    ProviderSpecificationRepo providerSpecificationRepo;

    //save data to repo
    public DataMapper save(JsonObject providerData) {
        return dataRepository.save(providerData);
    }

    //get filtered dat from repo
    public Map<String, Object> getFilteredData(Integer providerId, final String name, final String age, final String timestamp)  {
        return dataRepository.getFilteredData(providerId, name, age, timestamp);
    }

    public Map<String, Object> saveProviderSpecification(Map<String, Object> provider) {
        return providerSpecificationRepo.addProvider(provider);
    }


}
