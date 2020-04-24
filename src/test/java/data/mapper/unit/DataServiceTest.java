package data.mapper.unit;

import data.mapper.dto.DataMapper;
import data.mapper.entity.Record;
import data.mapper.repository.DataRepository;
import data.mapper.service.DataService;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class DataServiceTest {

    @Mock
    DataRepository dataRepository;
    @InjectMocks
    DataService dataService;

    @BeforeEach
    void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSave(){
        Record record = new Record();
        record.setName("Sam");
        record.setAge(20);
        record.setTimestamp(123456789L);
        DataMapper dataMapper = new DataMapper();
        dataMapper.setProviderId(123456L);
        dataMapper.getData().add(record);
       given(dataRepository.save(dataMapper)).willReturn(dataMapper);
        DataMapper expected = dataService.save(dataMapper);
        Assertions.assertEquals(expected.getProviderId(), dataMapper.getProviderId());
        verify(dataRepository).save(any(DataMapper.class));
    }

    @Test
    public void  testFilterData(){
        Record record = new Record();
        record.setName("Sam");
        record.setAge(20);
        record.setTimestamp(123456789L);
        when(dataRepository.getFilteredData(anyLong(), anyString(), anyString(), anyString())).thenReturn(record);
        Record expected = dataService.getFilteredData(23235L, "samy", "johnny", "raf");
        Assertions.assertEquals(expected.getName(), record.getName());
    }
}
