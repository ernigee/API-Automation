import com.github.fge.jsonschema.main.JsonSchema;
import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.annotations.Test;
import io.restassured.specification.RequestSpecification;

import java.io.File;
import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class TestReqres {


    @Test
    public void testGetUserList() {

        File jsonSchema = new File("src/test/resources/jsonSchema/getListUserSchema.json");

        given().when()
                .get("https://reqres.in/api/users?page=2")
                .then().log().all()
                .assertThat().statusCode(200)
                .assertThat().body("per_page", Matchers.equalTo(6))
                .assertThat().body("page", Matchers.equalTo(2))
                .assertThat().body(JsonSchemaValidator.matchesJsonSchema(jsonSchema));
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

    @Test
    public void testPatchReqres(){
        RestAssured.baseURI= "https://reqres.in/"; // mendefinisikan baseURI
        int userId = 3; //user ID yang akan diubah
        String newName = "Maya"; // isi nama baru
        String fname = given().when().get("api/users/"+userId) // Mengambil data atribut dari USER_ID_3 sebelum dilakukan perubahan
        .getBody().jsonPath().get("data.first_name");
        System.out.println("name before " + fname); //printout setelah di_RUN

        HashMap<String,String> bodyMap = new HashMap<>(); //membuat bodyRequest d/ HashMap dan mengganti "first_name" dengan "new_first_name"
        bodyMap.put("first_name", newName);
        JSONObject jsonObject = new JSONObject(bodyMap); //membuat JSONObject dari HashMap

        given().log().all()                       //log().all() digunakan untuk mencetak request ke console, sifatnya opsional
                .header("Content-Type", "application/json")                     //mendefinisikan header request
                .body(jsonObject.toString())                            // mengubah JSONObject ke String untuk body request
                .patch("api/users/"+userId)            // memanggil request patch
                .then().log().all()                       //log().all() digunakan untuk mencetak response ke console, sifatnya opsional
                .assertThat().statusCode(200)           // assertion terhadap status code 200 (OK)
                .assertThat().body("first_name", Matchers.equalTo(newName));   // assertion terhadap response body "first_name" = variable "new_name"

    }
}
