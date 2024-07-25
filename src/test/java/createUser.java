import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;

public class createUser {

    public String authorizationToken;
    public String baseURI="https://dev.bes-learning.com:3005/api/v1";

    public String userEmail ="Thor@mailinator.com";

    @Test
    @Feature("Positive Scenario")
    public void CreateAccount_POST(){
        String body = "{\"email\": \"" + userEmail + "\"}";

        System.out.println(body);

        Response response = given()
                .contentType("application/json")
                .body(body)
                .post(baseURI+"/auth/register");

        System.out.println(response.getBody().asString());
        Assert.assertEquals(response.getStatusCode(),200);
    }
}
