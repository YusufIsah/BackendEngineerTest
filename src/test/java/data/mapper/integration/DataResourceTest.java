package data.mapper.integration;
import data.mapper.dto.DataMapper;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.wildfly.common.Assert;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static javax.ws.rs.core.Response.Status.*;


@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DataResourceTest {

    @Test
    @Order(1)
    public void testProviderSpec() {
        Map<String, Object> map1 = new HashMap<>();
        List<String> spects = new ArrayList<>();
        spects.add("name");
        spects.add("age");
        spects.add("timestamp");
        map1.put("providerId", 123456);
        map1.put("fields", spects);

        String providerId =  given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(map1)
                .when()
                .post("/data/provider")
                .then()
                .statusCode(CREATED.getStatusCode())
                .extract().body().jsonPath().getString("providerId");
        Assert.assertNotNull(providerId);
    }

    @Test
    @Order(2)
    public void testSave() {
        Map<String, Object> map1 = new HashMap<>();
        Map<String, Object> map2 = new HashMap<>();
        map2.put("name","Sam");
        map2.put("age",20);
        map2.put("timestamp",123456789);
        DataMapper dataMapper = new DataMapper();
        dataMapper.setProviderId(123456);
        dataMapper.getData().add(map2);

      String providerId = given()
                .contentType(MediaType.APPLICATION_JSON)
               .body(dataMapper)
                .when()
                .post("/data")
                .then()
                .statusCode(CREATED.getStatusCode())
                .extract().body().jsonPath().getString("providerId");
        Assert.assertNotNull(providerId);
    }

    @Test
    @Order(3)
    public void testFilterData(){
        given().pathParam("providerId", 123456)
                .queryParam("name", "eqc:sam")
                .queryParam("age", "eq:20")
                .queryParam("timestamp", "eq:123456789")
                .when().get("/data/filter/{providerId}/")
                .then()
                .statusCode(OK.getStatusCode()).log().all();
    }




}
