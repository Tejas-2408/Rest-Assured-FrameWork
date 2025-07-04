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

import org.testng.ITestContext;
import org.testng.annotations.Test;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import payloads.Payload;
import pojo.Cart;
import routes.Routes;

public class CartTests extends BaseClass {
	
	@Test
	public void testGetAllCarts() {
		given()
		
		.when()
			.get(Routes.GET_ALL_CART_ITEMS)
		.then()
			.statusCode(200)
			.body("size()",greaterThan(0));
	}
	
	// get cart by id
	@Test
	public void testGetCartById() {
		int cartId = configReader.getIntProperty("cartId");
		
		given()
			.pathParam("id", cartId)
		.when()
			.get(Routes.GET_CART_BY_ID)
		.then()
			.statusCode(200)
			.body("id", equalTo(cartId));
	}
	
	// Cart within date range
	@Test
	public void testGetCartInDateRange() {
		String startDate = configReader.getProperty("startdate");
		String endDate = configReader.getProperty("enddate");
		
		Response response = given()
					.pathParam("startDate", startDate)
					.pathParam("endDate", endDate)
				.when()
					.get(Routes.GET_CARTS_IN_DATE_RANGE)
				.then()
				.statusCode(200)
				.body("size()", greaterThan(0))
				.extract().response();
		
		List<String> cartDates = response.jsonPath().getList("date");
		
		assertThat(validateCartDatesWithinRange(cartDates,startDate,endDate),is(true));
		
	}
	
	
	@Test
	public void testGetUserCart() {
		int userId = configReader.getIntProperty("userId");
		
		given()
			.pathParam("userId", userId)
		.when()
			.get(Routes.GET_CART_BY_USER)
		.then()
			.statusCode(200)
			.body("userId",everyItem(equalTo(userId)));
	}
	
	@Test
	public void testGetCartWithLimit() {
		given()
			.pathParam("limit", 3)
		.when()
			.get(Routes.GET_CARTS_WITH_LIMIT)
		.then()
			.statusCode(200)
			.body("size()", lessThanOrEqualTo(3));
	}
	
	@Test
	public void testGetCartsSortedDesc() {
		Response response = given()
				.pathParam("order", "desc")
			.when()
				.get(Routes.GET_CARTS_SORTED)
			.then()
				.statusCode(200)
				.body("size()",greaterThan(0))
				.extract().response();
		
		List<Integer> cartIds = response.jsonPath().getList("id",Integer.class);
		
		assertThat(isSortedDescending(cartIds), is(true));
	}
	
	@Test
	public void testGetCartsSortedAsc() {
		Response response = given()
				.pathParam("order", "asc")
			.when()
				.get(Routes.GET_CARTS_SORTED)
			.then()
				.statusCode(200)
				.body("size()",greaterThan(0))
				.extract().response();
		
		List<Integer> cartIds = response.jsonPath().getList("id",Integer.class);
		
		assertThat(isSortedAscending(cartIds), is(true));
	}
	
	@Test
	public void testCreateCart() {
		
		int userId = configReader.getIntProperty("userId");
		Cart newCart = Payload.cartPayload(userId);
		
		given()
			.contentType(ContentType.JSON)
			.body(newCart)
		.when()
			.post(Routes.CREATE_CART)
		.then()
			.statusCode(200)
			.body("id", notNullValue())
			.body("userId", notNullValue())
			.body("products.size()", greaterThan(0));
	}
	
	@Test
	public void testUpdateCart() {
		int userId = configReader.getIntProperty("userId");
		int cartId = configReader.getIntProperty("cartId");
		Cart updateCart = Payload.cartPayload(userId);
		
		given()
			.contentType(ContentType.JSON)
			.body(updateCart)
			.pathParam("id", cartId)
		.when()
			.put(Routes.UPDATE_CART)
		.then()
			.statusCode(200)
			.body("id", equalTo(cartId))
			.body("userId", notNullValue())
			.body("products.size()", equalTo(1));
	}
	
	@Test
	public void testDeleteCart() {
		int cartId = configReader.getIntProperty("cartId");
		
		given()
			.pathParam("id", cartId)
		.when()
			.delete(Routes.DELETE_CART)
		.then()
			.statusCode(200);
	}

}
