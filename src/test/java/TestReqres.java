import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.annotations.Test;

public class TestReqres {
    @Test
    public void testGetUserList(){
        RestAssured
                .given().when()
                .get("https://reqres.in/api/users?page=2")
                .then().log().all()
                .assertThat().statusCode(200)
                .assertThat().body("per_page", Matchers.equalTo(6))
                .assertThat().body("page", Matchers.equalTo(2));
    }

    @Test
    public void testPostCreateNewUser() throws JSONException {
        String valueName = "Erni Gee";
        String valueJob = "Quality Assurance Engineer Automation";

  JSONObject bodyObj = new JSONObject();

        bodyObj.put("name", valueName);
        bodyObj.put("job", valueJob);

        RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .body(bodyObj.toString())
                .when()
                .post("https://reqres.in/api/users")
                .then()
                .log().all()
                .assertThat().statusCode(201)
                .assertThat()
                .body("Name", Matchers.equalTo(valueName))
                .body("job", Matchers.equalTo(valueJob));

    }


    }
