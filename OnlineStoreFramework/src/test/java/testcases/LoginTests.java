package testcases;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.lessThanOrEqualTo;

import java.util.List;

import org.json.JSONObject;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import com.google.gson.JsonObject;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import payloads.Payload;
import pojo.Login;
import routes.Routes;

public class LoginTests extends BaseClass{

	@Test
	public void testInvalidUserLogin() {
		Login newLogin = Payload.loginPayload();
		
		given()
			.contentType(ContentType.JSON)
			.body(newLogin)
		
		.when()
			.post(Routes.AUTH_LOGIN)
		.then()
			.statusCode(401);
	}
	
	@Test
	public void testVerifiedUser() {
		String username = configReader.getProperty("username");
		String password = configReader.getProperty("password");
		
		Login newLogin = new Login(username,password);
		
		given()
		.contentType(ContentType.JSON)
		.body(newLogin)
	
	.when()
		.post(Routes.AUTH_LOGIN)
	.then()
		.statusCode(200)
		.body("token", notNullValue());
	}
}
