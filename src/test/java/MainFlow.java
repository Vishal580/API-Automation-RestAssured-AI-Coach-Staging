import io.qameta.allure.Feature;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;

public class MainFlow {
    public String authorizationToken;
    public String baseURI="https://staging.bes-learning.com/ai-coach/api/v1";

    public String userEmail ="vishal@mailinator.com";
    public String userPassword = "Vishal@12";

    public String languageId = "22";
    public String language = "English";
    public String content = "Marketing manager";

    public static String repoId;
    public static String personaId;
    public static String URL;

//    public static String platform1 = "android";
//    public static String a_version = "1.0.1.8";
//
//    public static String platform2 = "ios";
//    public static String i_version = "1.0.1.8";

//    @Test
//    @Feature("Positive Scenario")
//    public void CreateAccount_POST(){
//        String body = "{\"email\": \"" + userEmail + "\"}";
//
//        System.out.println(body);
//
//        Response response = given()
//                .contentType("application/json")
//                .body(body)
//                .post(baseURI+"/auth/register");
//
//        System.out.println(response.getBody().asString());
//        Assert.assertEquals(response.getStatusCode(),200);
//    }

    @Test(priority = 1)
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
        //System.out.println(authorizationToken);
        //System.out.println(response.getBody().asString());
        response.prettyPrint();
        Assert.assertEquals(response.getStatusCode(), 200);
    }

//    @Test(dependsOnMethods = {"LoginUser_POST"}, priority = 3)
//    @Feature("Positive Scenario")
//    public void ChangePassword_POST(){
//        String body="{\"oldPassword\":\"" + oldPassword + "\",\"newPassword\":\"" + newPassword + "\",\"confirmNewPassword\":\"" + confirmNewPassword + "\"}";
////        String authorizationToken= "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6OTksImp0aSI6IjA1Zzk4YmNlYTE5MzFiNzIyMTdnOTdkYmU4MTZhZWMwNzQ4ZDhiNDQiLCJpYXQiOjE3MTk4MzY5MTQsImV4cCI6MTc1MTM5NDUxNH0.0eWtDz4DUOilb-NQn_f7fcvOwffLxyNr47mq8t3YUIA";
//
//        System.out.println(body);
//        System.out.println(authorizationToken);
//        Response response=given()
//                .header("Authorization","Bearer "+ authorizationToken)
//                .contentType("application/json")
//                .body(body)
//                .post(baseURI+"/auth/change-password");
//
//        System.out.println(response.getBody().asString());
//        Assert.assertEquals(response.getStatusCode(), 200);
//    }

    @Test(dependsOnMethods = {"LoginUser_POST"}, priority = 2)
    @Feature("Positive Scenario")
    public void GetLanguageList_GET(){

        Response response=given()
                .header("Authorization", "Bearer "+authorizationToken)
                .get(baseURI+"/chat/languages");

        //System.out.println(response.getBody().asString());
        response.prettyPrint();
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(dependsOnMethods = {"LoginUser_POST"}, priority = 3)
    @Feature("Positive Scenario")
    public void LanguageUpdate_PUT(){
        String body="{\"languageId\": \"" + languageId + "\",\"language\": \"" + language + "\"}";

        System.out.println(body);
        Response response=given()
                .header("Authorization", "Bearer "+authorizationToken)
                .contentType("application/json")
                .body(body)
                .put(baseURI+"/auth/update-language");

        //System.out.println(response.getBody().asString());
        response.prettyPrint();
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(dependsOnMethods = {"LoginUser_POST"}, priority = 4)
    @Feature("Positive Scenario")
    public void Language_GET(){
        Response response=given()
                .header("Authorization", "Bearer "+authorizationToken)
                .get(baseURI+"/auth/language");

//        System.out.println(response.getBody().asString());
        response.prettyPrint();
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(dependsOnMethods = {"LoginUser_POST"}, priority = 5)
    @Feature("Positive Scenario")
    public void UserPhoneDetails_GET(){
        Response response=given()
                .header("Authorization", "Bearer "+authorizationToken)
                .get(baseURI+"/auth/phone");

        response.prettyPrint();
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(dependsOnMethods = {"LoginUser_POST"}, priority = 6)
    @Feature("Positive Scenario")
    public void GetRepositoryDetails_GET(){
        Response response=given()
                .header("Authorization", "Bearer "+authorizationToken)
                .contentType("application/json")
                .get(baseURI+"/repository");

        //System.out.println(response.getBody().asString());
        response.prettyPrint();
        Assert.assertEquals(response.getStatusCode(),200);
        repoId = response.jsonPath().get("data.id[0]").toString();
        System.out.println("---------------------repoId: "+repoId);
    }

    @Test(dependsOnMethods = {"LoginUser_POST", "GetRepositoryDetails_GET"}, priority = 7)
    @Feature("Positive Scenario")
    public void GetPersonaDetails_GET(){
        //String repositoryId = "132";

        Response response=given()
                .header("Authorization", "Bearer "+authorizationToken)
                .queryParam("repositoryId", repoId)
                .get(baseURI+"/repository/persona");

        personaId = response.jsonPath().get("data.id[0]").toString();
        System.out.println("------------------------------Persona ID: "+personaId);

        URL = response.jsonPath().get("data.image[0]").toString();
        System.out.println("------------------------------Persona Image: "+URL);
        response.prettyPrint();
        Assert.assertEquals(response.getStatusCode(), 200);
    }


    @Test(dependsOnMethods = {"LoginUser_POST", "GetRepositoryDetails_GET", "GetPersonaDetails_GET"}, priority = 8)
    @Feature("Positive Scenario")
    public void ChatWithPersona_POST(){
        String body = "{\"personaId\": \"" + personaId + "\", \"language\": \"" + language + "\", \"content\": [{\"role\": \"user\", \"content\": \"" + content + "\"}]}";
        System.out.println(body);

        Response response=given()
                .header("Authorization","Bearer "+authorizationToken)
                .contentType("application/json")
                .body(body)
                .post(baseURI+"/chat/persona");

        response.prettyPrint();
        Assert.assertEquals(response.getStatusCode(),200);
    }

    @Test(dependsOnMethods = {"LoginUser_POST"}, priority = 9)
    @Feature("Positive Scenario")
    public void GetFeedback_POST(){
        String body = "{\"personaId\": \"" + personaId + "\", \"language\": \"" + language + "\", \"lastAIMessage\": \"Last AI Message\", \"lastUserMessage\": \"Last User Message\"}";
        System.out.println(body);

        Response response=given()
                .header("Authorization", "Bearer "+authorizationToken)
                .contentType("application/json")
                .body(body)
                .post(baseURI+"/chat/feedback");

        response.prettyPrint();
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(dependsOnMethods = {"LoginUser_POST","GetRepositoryDetails_GET", "GetPersonaDetails_GET"}, priority = 10)
    @Feature("Positive Scenario")
    public void GetVideoDuration_GET(){
        Response response=given()
                .header("Authorization", "Bearer "+authorizationToken)
                .queryParam("url", URL)
                .queryParam("repositoryId", repoId)
                .get(baseURI+"/repository/persona/video/duration");

        response.prettyPrint();
        Assert.assertEquals(response.getStatusCode(),200);
    }

    @Test(priority = 11)
    @Feature("Positive Scenario")
    public void ForgotPassword_POST(){
        String body = "{\"email\": \"" + userEmail + "\"}";
        System.out.println(body);

        Response response=given()
                .contentType("application/json")
                .body(body)
                .post(baseURI+"/auth/forgot-password");

//        System.out.println(response.getBody().asString());
        response.prettyPrint();
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(dependsOnMethods = {"LoginUser_POST"}, priority = 12)
    @Feature("Positive Scenario")
    public void UserLogout_POST(){
        Response response=given()
                .header("Authorization", "Bearer "+authorizationToken)
                .contentType("application/json")
                .post(baseURI+"/auth/logout");

//        System.out.println(response.getBody().asString());
        response.prettyPrint();
        Assert.assertEquals(response.getStatusCode(),200);
    }

//    @Test(priority = 13)
//    @Feature("Positive Scenario")
//    public void AppVersion_POST(){
//        String body="{\"platform\": \"" + platform1 + "\",\"version\": \"" + a_version + "\"}";
//        System.out.println(body);
//
//        Response response=given()
//                .contentType("application/json")
//                .body(body)
//                .post(baseURI+"/app/check-update");
//
//        response.prettyPrint();
//        Assert.assertEquals(response.getStatusCode(), 200);
//    }
//
//    @Test(priority = 14)
//    @Feature("Positive Scenario")
//    public void IOSAppVersion_POST(){
//        String body="{\"platform\": \"" + platform2 + "\",\"version\": \"" + i_version + "\"}";
//        System.out.println(body);
//
//        Response response=given()
//                .contentType("application/json")
//                .body(body)
//                .post(baseURI+"/app/check-update");
//
//        response.prettyPrint();
//        Assert.assertEquals(response.getStatusCode(), 200);
//    }


//    @Test(dependsOnMethods = {"LoginUser_POST"})
//    @Feature("Positive Scenario")
//    public void DeleteUserAccount_DELETE(){
//        Response response=given()
//                .header("Authorization", "Bearer "+ authorizationToken)
//                .delete(baseURI+"/auth/delete-account");
//
//        System.out.println(response.getBody().asString());
//        Assert.assertEquals(response.getStatusCode(),200);
//    }
}