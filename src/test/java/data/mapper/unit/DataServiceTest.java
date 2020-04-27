 package data.mapper.unit;

 import data.mapper.dto.DataMapper;
 import data.mapper.repository.ProviderDataRepo;
 import data.mapper.service.ProviderDataService;
 import org.junit.jupiter.api.Assertions;
 import org.junit.jupiter.api.BeforeEach;
 import org.junit.jupiter.api.Test;
 import org.mockito.InjectMocks;
 import org.mockito.Mock;
 import org.mockito.MockitoAnnotations;
 import javax.json.Json;
 import javax.json.JsonObject;
 import java.util.HashMap;
 import java.util.Map;
 import static org.mockito.ArgumentMatchers.*;
 import static org.mockito.BDDMockito.given;
 import static org.mockito.Mockito.verify;
 import static org.mockito.Mockito.when;


 public class DataServiceTest {

     @Mock
     ProviderDataRepo dataRepository;
     @InjectMocks
     ProviderDataService dataService;

     @BeforeEach
     void setup(){
         MockitoAnnotations.initMocks(this);
     }

     @Test
     public void testSave(){
         Map<String, Object> record = new HashMap<>();
         record.put("name","Sam");
         record.put("age",20);
         record.put("timestamp",123456789L);
         DataMapper dataMapper = new DataMapper();
         dataMapper.setProviderId(123456);
         dataMapper.getData().add(record);
         JsonObject jsonObject = Json.createObjectBuilder().build();
        given(dataRepository.save(jsonObject)).willReturn(dataMapper);
         DataMapper expected = dataService.save(jsonObject);
         Assertions.assertEquals(expected.getProviderId(), dataMapper.getProviderId());
         verify(dataRepository).save(any(JsonObject.class));
     }

     @Test
     public void  testFilterData(){
         Map<String, Object> record = new HashMap<>();
         record.put("name","Sam");
         record.put("age",20);
         record.put("timestamp",123456789L);
         when(dataRepository.getFilteredData(anyInt(), anyString(), anyString(), anyString())).thenReturn(record);
         var expected = dataService.getFilteredData(23235, "samy", "johnny", "raf");
         Assertions.assertEquals(expected.get("name"), record.get("name"));
     }
 }
