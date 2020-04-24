package data.mapper.integration;

import data.mapper.dto.DataMapper;
import data.mapper.entity.Record;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import org.wildfly.common.Assert;
import javax.ws.rs.core.MediaType;
import static io.restassured.RestAssured.given;
import static javax.ws.rs.core.Response.Status.*;


@QuarkusTest
public class DataResourceTest {

    @Test
    public void testSave() {
        Record record = new Record();
        record.setName("Sam");
        record.setAge(20);
        record.setTimestamp(123456789L);
        DataMapper dataMapper = new DataMapper();
        dataMapper.setProviderId(123456L);
        dataMapper.getData().add(record);
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
    public void testFilterData(){

        given().pathParams("providerId", 123456)
                .queryParam("name", "eqc:sam")
                .queryParam("age", "eq:20")
                .queryParam("timestamp", "eq:123456789")
                .when().get("/data/filter/{providerId}")
                .then()
                .statusCode(OK.getStatusCode());
    }




}
