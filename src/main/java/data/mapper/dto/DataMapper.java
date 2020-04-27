package data.mapper.dto;

import lombok.Data;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
public class DataMapper implements Serializable {
    private Integer providerId;
    private List<Map<String, Object>> data;
    public DataMapper(){
        this.data  = new ArrayList<>();
    }
}
