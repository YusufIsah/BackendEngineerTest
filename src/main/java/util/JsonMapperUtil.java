package util;

import java.util.HashMap;
import java.util.Map;

import javax.json.JsonObject;

public class JsonMapperUtil {

    public static Map<String, Object> MapProvider(String providerId, String providerData,  JsonObject jsonObject) {
        Map<String, Object> provider = new HashMap<>();
        provider.put(providerId, jsonObject.getInt(providerId));
        provider.put(providerData, jsonObject.getJsonArray(providerData));
        return provider;
    }


}