import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class TestReqres2 {

    @Test
    public void testGetUserList1(){
        given().when()
                .get("https://reqres.in/api/users?page=2")
                .then().log().all()
                .assertThat().statusCode(200)
                .assertThat().body("per_page", Matchers.equalTo(6))
                .assertThat().body("page", Matchers.equalTo(2));
    }

    @Test
public void testPutReqres(){
        int userID = 4;
        String fname = given().when().get("https://reqres.in/api/users/"+userID).getBody().jsonPath("data.first_name");
        String lname = given().when().get("https://reqres.in/api/users/"+userID).getBody().jsonPath("data.last_name");
        String avatar = given().when().get("https://reqres.in/api/users"+userID).getBody().jsonPath("data.avatar");
        String email = given().when().get("https://reqres.in/api/users"+userID).getBody().jsonPath("data.email");

        HashMap<String,Object> bodyMap = new HashMap<>();
        bodyMap.put("id", userID);


}
