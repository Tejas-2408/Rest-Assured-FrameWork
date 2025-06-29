package testcases;

import pojo.Product;
import routes.Routes;
import utils.ConfigReader;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import payloads.Payload;

import org.testng.ITestContext;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import java.util.List;

public class ProductTests extends BaseClass {

	// Retrieve all products
	@Test
	public void testGetAllProducts() {

		given()

		.when().
			get(Routes.GET_ALL_PRODUCTS)
		.then()
		.statusCode(200)
		.body("size()", greaterThan(0));

	}
	
	// Test to retrieve single product by ID
	@Test
	public void testGetProductById() {

		int productId = configReader.getIntProperty("productId");
		
		given()
			.pathParam("id",productId)
		.when()
			.get(Routes.GET_PRODUCT_BY_ID)
		.then()
			.statusCode(200);
	}
	
	// Retrieve products with specified limit
	@Test
	public void testGetProductWithLimit() {
		given()
			.pathParam("limit", 3)
		.when()
			.get(Routes.GET_PRODUCTS_WITH_LIMIT)
		.then()
			.statusCode(200)
			.body("size()", equalTo(3));
	}

	// Retrieve products sorted in Descending order by id
	@Test
	public void testGetProductsDescSorted() {
		Response response = given()
			.pathParam("order", "desc")
		.when()
			.get(Routes.GET_PRODUCTS_SORTED)
		.then()
			.statusCode(200)
			.extract().response();
		
		List<Integer> productID = response.jsonPath().getList("id",Integer.class);
		assertThat(isSortedDescending(productID),is(true));
	}
	
	
	
	// Retrieve products sorted in Ascending order by id
		@Test
		public void testGetProductsAscSorted() {
			Response response = given()
				.pathParam("order", "asc")
			.when()
				.get(Routes.GET_PRODUCTS_SORTED)
			.then()
				.statusCode(200)
				.extract().response();
			
			List<Integer> productID = response.jsonPath().getList("id",Integer.class);
			assertThat(isSortedAscending(productID),is(true));
		}
		

	@Test
	public void testGetAllCategories() {
		given()

		.when()
			.get(Routes.GET_ALL_CATEGORIES)
		.then()
			.statusCode(200)
			.body("size()", greaterThan(0));
	}

	@Test
	public void testGetProductsByCategory() {
		given()
			.pathParam("category", "electronics")
		.when()
			.get(Routes.GET_PRODUCTS_BY_CATEGORY)
		.then()
			.statusCode(200)
			.body("size()", greaterThan(0))
			.body("category", everyItem(notNullValue()))
			.body("category", everyItem(equalTo("electronics")));		
	}

	@Test
	public void testCreateProduct() {
		
		Product newProduct = Payload.productPayload();
		
		int productId = given()
			.contentType(ContentType.JSON)
			.body(newProduct)
		.when()
			.post(Routes.CREATE_PRODUCT)
		.then()
			.statusCode(200)
			.body("id", notNullValue())
			.body("title",equalTo(newProduct.getTitle()))
			.body("category", equalTo(newProduct.getCategory()))
			.extract().jsonPath().getInt("id"); // Extracting id from response body
	}

	@Test
	public void testUpdateProduct() {
		
		int productId = configReader.getIntProperty("productId");
		Product updateProduct = Payload.productPayload();
		
		given()
			.pathParam("id", productId)
			.contentType(ContentType.JSON)
			.body(updateProduct)
		.when()
			.put(Routes.UPDATE_PRODUCT)
		.then()
			.statusCode(200)
			.body("title", equalTo(updateProduct.getTitle()));
	}

	@Test
	public void testDeleteProduct() {
		int productId = configReader.getIntProperty("productId");
		given()
			.pathParam("id", productId)
		.when()
			.delete(Routes.DELETE_PRODUCT)
		.then()
			.statusCode(200);
	}
}
