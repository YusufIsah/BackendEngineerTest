package data.mapper.repository;

import data.mapper.dto.DataMapper;
import util.QueryRules;
import util.StringUtil;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.*;
import java.util.*;

@ApplicationScoped
public class ProviderDataRepo {

    @Inject
    ProviderSpecificationRepo providerSpecificationRepo;

    private Set<DataMapper> providerDatas;


    public ProviderDataRepo() {
        this.providerDatas = new HashSet<>();
    }

    /**
     *
     * @param providerData
     * @return
     */
    public DataMapper save(JsonObject providerData) {
        //this check if old provider Data exist it then remove it and replace it with new one
        if (!providerDatas.isEmpty()) {
            DataMapper dataMapper = providerDatas.stream()
                    .filter(pro -> pro.getProviderId().equals(Integer.parseInt(providerData.get("providerId").toString()))).findFirst().get();
            providerDatas.remove(dataMapper);
        }
        var mapProviderData = extractDataBasedOnProviderSpec(providerData);
        providerDatas.add(mapProviderData);
        return mapProviderData;
    }

    private DataMapper extractDataBasedOnProviderSpec(JsonObject providerData) {
        DataMapper dataMapper  = new DataMapper();
        providerSpecificationRepo.findProviderById(providerData.getInt("providerId"))
        .forEach((key, value) -> {
          if (key.equals("providerId")) {
              dataMapper.setProviderId(providerData.getInt(key));
          }
          if (key.equals("fields")) { 
            providerData.getJsonArray("data")
            .forEach(data -> updateExtractedData(dataMapper, value, data));
            }
        });
        return  dataMapper;
    }

    private void updateExtractedData(DataMapper newdata, Object fields, Object data) {
        List<String> fieldList = getJsonArrayValueAsList(fields);
        Map<String, Object> mapData = new HashMap<>();
        if(data instanceof JsonObject){
        JsonObject jsonObject = (JsonObject) data;
        jsonObject.keySet().forEach(key -> {
            if (fieldList.contains(key)){
                Object value = jsonObject.get(key);
                mapData.put(key, value);
           }
        });
        newdata.getData().add(mapData);
    }
    }

    List<String> getJsonArrayValueAsList(Object value){
        List<String> fields = new ArrayList<>();
        if (value instanceof JsonArray) {
            JsonArray jsonArray = (JsonArray) value;
            jsonArray.forEach(v -> fields.add(v.toString().replaceAll("\"", "")));
        }
        return fields;
    }

    /**
     *
     * @param providerId
     * @param name
     * @param age
     * @param timestamp
     * @return
     */
    public Map<String, Object> getFilteredData(Integer providerId, String name, String age, String timestamp)  {
        return performFilter(providerId, name, age, timestamp);
    }

    /**
     *
     * @param providerId
     * @param name
     * @param age
     * @param timestamp
     * @return
     */
    private Map<String, Object> performFilter(Integer providerId, String name, String age, String timestamp) {
       Map<String, Object> filterResult = new HashMap<>();
        var regex = ":";
        String[] splitName = StringUtil.split(name, regex);
        String[] splitAge = StringUtil.split(age, regex);
        String[] splitTimestamp = StringUtil.split(timestamp, regex);

         providerDatas.stream()
                .filter(providerData -> providerData.getProviderId().equals(providerId)).findFirst().get().getData()
                 .forEach(objectMaps ->{
                     List<String> trackMatchedValue = new ArrayList<>();
                     objectMaps.forEach((key, value) -> {
                         filterFields(trackMatchedValue, key, value, splitName, splitAge, splitTimestamp);
                     });
                     if (trackMatchedValue.size() == objectMaps.size()){
                         filterResult.putAll(objectMaps);
                     }
                 });
        return filterResult;
    }

    /**
     *
     * @param trackMatchedValue
     * @param key
     * @param value
     * @param name
     * @param age
     * @param timestamp
     */
    private void filterFields( List<String> trackMatchedValue, String key, Object value, String[] name, String[] age, String[] timestamp) {

        if (name[0].toUpperCase().equals(QueryRules.EQC.name()) && value.toString().toLowerCase().contains(name[1].toLowerCase())) {
            trackMatchedValue.add("true");
        }
        if(!value.getClass().getName().equals("org.glassfish.json.JsonStringImpl")) {

            if (age[0].toUpperCase().equals(QueryRules.EQ.name()) && Integer.parseInt(value.toString()) == Integer.parseInt(age[1])) {
                trackMatchedValue.add("true");

            }else if (age[0].toUpperCase().equals(QueryRules.LT.name()) && (Integer.parseInt(age[1]) < Integer.parseInt(value.toString()))) {
                trackMatchedValue.add("true");

            }else if (age[0].toUpperCase().equals(QueryRules.GT.name()) && (Integer.parseInt(age[1]) > Integer.parseInt(value.toString()))) {
                trackMatchedValue.add("true");

            } else if (timestamp[0].toUpperCase().equals(QueryRules.EQ.name()) && (Long.parseLong(timestamp[1]) == Long.parseLong(value.toString()))) {
                trackMatchedValue.add("true");

        } else if (timestamp[0].toUpperCase().equals(QueryRules.LT.name()) && (Long.parseLong(timestamp[1]) < Long.parseLong(value.toString()))) {
                trackMatchedValue.add("true");
            ;
        } else if (timestamp[0].toUpperCase().equals(QueryRules.GT.name()) && (Long.parseLong(timestamp[1]) > Long.parseLong(value.toString()))) {
                trackMatchedValue.add("true");

        }
        }
    }

}
