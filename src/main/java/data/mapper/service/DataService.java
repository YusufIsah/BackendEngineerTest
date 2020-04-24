package data.mapper.service;

import data.mapper.dto.DataMapper;
import data.mapper.entity.Record;
import data.mapper.repository.DataRepository;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@RequestScoped
public class DataService {


    @Inject
    DataRepository dataRepository;

    //save data to repo
    public DataMapper save(DataMapper dataMapper) {
        return dataRepository.save(dataMapper);
    }

    //get filtered dat from repo
    public Record getFilteredData(Long providerId, String name, String age, String timestamp) {
        return dataRepository.getFilteredData(providerId, name, age, timestamp);
    }


}
