import io.qameta.allure.Feature;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;

public class DeleteUser {

    public String authorizationToken;
    public String baseURI="https://staging.bes-learning.com/ai-coach/api/v1";

    public String userEmail ="Thor@mailinator.com";
    public String userPassword = "Test@123";

    @Test
    @Feature("Positive Scenario")
    public void LoginUser_POST(){
        String body = "{\"email\":\"" + userEmail + "\",\"password\":\"" + userPassword + "\"}";

        System.out.println(body);
        Response response=given()
                .contentType("application/json")
                .body(body)
                .post(baseURI+"/auth/login");

        if (response.getStatusCode() == 200) {
            // Parse the response JSON string into a JsonObject
            JsonPath jsonPath = response.jsonPath();
            authorizationToken = jsonPath.get("data.auth.accessToken");
        }
        System.out.println(authorizationToken);
        System.out.println(response.getBody().asString());
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(dependsOnMethods = {"LoginUser_POST"})
    @Feature("Positive Scenario")
    public void DeleteUserAccount_DELETE(){
        Response response=given()
                .header("Authorization", "Bearer "+ authorizationToken)
                .delete(baseURI+"/auth/delete-account");

        System.out.println(response.getBody().asString());
        Assert.assertEquals(response.getStatusCode(),200);
    }
}

