import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.Randomgenerator;
import static io.restassured.RestAssured.*;

public class CreateUser {

    Randomgenerator email = new Randomgenerator();
    public String baseURI="https://staging.bes-learning.com/ai-coach/api/v1";

    public String signinEmail ="Thor"+email.RandomNumberString()+"@mailinator.com";

    @Test
    @Feature("Positive Scenario")
    public void CreateAccount_POST(){
        String body = "{\"email\": \"" + signinEmail + "\"}";

        System.out.println(body);

        Response response = given()
                .contentType("application/json")
                .body(body)
                .post(baseURI+"/auth/register");

        System.out.println(response.getBody().asString());
        Assert.assertEquals(response.getStatusCode(),200);

/*        String password = response.jsonPath().getString("password");

        System.out.println("Password: " + password);*/
    }
}
