import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.annotations.Test;
import io.restassured.specification.RequestSpecification;

import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class TestReqres {
    @Test
    public void testGetUserList() {
        given().when()
                .get("https://reqres.in/api/users?page=2")
                .then().log().all()
                .assertThat().statusCode(200)
                .assertThat().body("per_page", Matchers.equalTo(6))
                .assertThat().body("page", Matchers.equalTo(2));
    }

    @Test
    public void testPutReqres() {

        int userId = 2;
        String newName = "Daffa Fawwaz";

        String fname = given().when().get("https://reqres.in/api/users/" + userId).getBody().jsonPath().get("data.first_name");
        String lname = given().when().get("https://reqres.in/api/users/" + userId).getBody().jsonPath().get("data.last_name");
        String avatar = given().when().get("https://reqres.in/api/users/" + userId).getBody().jsonPath().get("data.avatar");
        String email = given().when().get("https://reqres.in/api/users/" + userId).getBody().jsonPath().get("data.email");

        HashMap<String, Object> bodyMap = new HashMap<>();
        bodyMap.put("id", userId);
        bodyMap.put("email", email);
        bodyMap.put("first_name", newName);
        bodyMap.put("last_name", lname);
        bodyMap.put("avatar", avatar);
        JSONObject jsonObject = new JSONObject(bodyMap);

        given()
                .header("Content-Type", "application/json")
                .body(jsonObject.toString())
                .put("https://reqres.in/api/users/" + userId)
                .then()
                .assertThat().statusCode(200)
                .assertThat().body("first_name", Matchers.equalTo(newName));
    }

    @Test
    public void testPostReqres() throws JSONException {
        RequestSpecification req = RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json");

        String name = "Fawweka";
        String job = "leader";
        JSONObject payload = new JSONObject();
        payload.put("name", name);
        payload.put("job", job);

        Response res = req.body(payload.toString()).when().post("https://reqres.in/api/users");
        System.out.println(res.getBody().asString());
    }

    @Test
    public void testDeleteReqres() {
        RequestSpecification req = RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json");

        Response res = req.when().delete("https://reqres.in/api/users/2");
        System.out.println(res.statusCode());
    }
}
