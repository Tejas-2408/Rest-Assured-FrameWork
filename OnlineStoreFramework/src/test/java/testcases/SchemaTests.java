package testcases;

import routes.Routes;
import utils.ConfigReader;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import payloads.Payload;

import org.testng.ITestContext;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import java.util.List;

public class SchemaTests extends BaseClass{
	
	// Validate product schemas
	@Test
	public void testProductSchema() {

		int productId = configReader.getIntProperty("productId");
		
		given()
			.pathParam("id",productId)
		.when()
			.get(Routes.GET_PRODUCT_BY_ID)
		.then()
			.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("productSchema.json"));
	}
	
	// User Schema validation
	@Test
	public void testUserSchema() {
		int userId = configReader.getIntProperty("userId");
		given()
			.pathParam("id", userId)
		.when()
			.get(Routes.GET_USER_BY_ID)
		
		.then()
		.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("userSchema.json"));
	}
	
	@Test
	public void testCartSchema() {
		int cartId = configReader.getIntProperty("cartId");
		
		given()
			.pathParam("id", cartId)
		.when()
			.get(Routes.GET_CART_BY_ID)
		.then()
		.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("cartSchema.json"));
	}

}
