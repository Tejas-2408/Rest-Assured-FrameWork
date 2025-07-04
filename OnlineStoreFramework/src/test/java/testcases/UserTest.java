package testcases;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import java.util.List;

import org.testng.ITestContext;
import org.testng.annotations.Test;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import payloads.Payload;
import pojo.User;
import routes.Routes;

public class UserTest extends BaseClass{
	
	// Fetch all users
	@Test
	public void testGetAllUsers() {
		given()
		
		.when()
			.get(Routes.GET_ALL_USERS)
		
		.then()
			.statusCode(200)
			.body("size()", greaterThan(0));
	}
	
	
	// Get specific user by id
	@Test
	public void testGetUserById() {
		int userId = configReader.getIntProperty("userId");
		given()
			.pathParam("id", userId)
		.when()
			.get(Routes.GET_USER_BY_ID)
		
		.then()
			.statusCode(200)
			.log().body();
	}
	
	
	// Fetch users with limits
	@Test
	public void testGetUserWithLimit() {
		
		given()
			.pathParam("limit", 2)
		.when()
			.get(Routes.GET_USERS_WITH_LIMIT)
		.then()
			.statusCode(200)
			.body("size()", equalTo(2));
	}
	
	// Fetch users in descending order
	@Test
	public void testGetUserDescendingOrder() {
		Response response = given()
			.pathParam("order", "desc")
		.when()
			.get(Routes.GET_USERS_SORTED)
		.then()
			.statusCode(200)
			.body("size()", greaterThan(0))
			.extract().response();
		
		List<Integer> userID = response.jsonPath().getList("id",Integer.class);
		assertThat(isSortedDescending(userID),is(true));
	}
	
	// Fetch user in ascending order
	@Test
	public void testGetUserAscendingOrder() {
		Response response = given()
				.pathParam("order", "asc")
		.when()
			.get(Routes.GET_USERS_SORTED)
		.then()
			.statusCode(200)
			.body("size()", greaterThan(0))
			.extract().response();
		
		List<Integer> userID = response.jsonPath().getList("id",Integer.class);
		assertThat(isSortedAscending(userID),is(true));
	}
	
	// Create a user
	@Test
	public void createUser() {
		
		User newUser = Payload.userPayload();
		
		int userId = given()
			.contentType(ContentType.JSON)
			.body(newUser)
		
		.when()
			.post(Routes.CREATE_USER)
		.then()
			.statusCode(200)
			.body("id", notNullValue())
			.extract().jsonPath().getInt("id");
		
		System.out.println("User id created is " + userId);
	}
	
	// Update user
	@Test
	public void updateUser() {
		int userId = configReader.getIntProperty("userId");
		User updatedUser = Payload.userPayload();
		
		given()
			.contentType(ContentType.JSON)
			.body(updatedUser)
			.pathParam("id", userId)
		.when()
			.put(Routes.UPDATE_USER)
		.then()
			.statusCode(200)
			.log().body()
			.body("username",equalTo(updatedUser.getUsername()));
	}
	
	// Delete User
	@Test
	public void deleteUser() {
		int userId = configReader.getIntProperty("userId");
		given()
			.pathParam("id", userId)
		.when()
			.delete(Routes.DELETE_USER)
		.then()
			.statusCode(200);
	}
	

}
