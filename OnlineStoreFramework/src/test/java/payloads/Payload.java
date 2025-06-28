package payloads;

import java.util.Random;

import com.github.javafaker.Faker;

import pojo.Product;

public class Payload {
	
	private static Faker faker = new Faker();
	private static String categories[] = {"electronics","furniture","books","clothing","beauty","jewellary"};
	
	private static Random random = new Random();
	
	// Product
	public static Product productPayload() {
		String name = faker.commerce().productName();
		double price = Double.parseDouble(faker.commerce().price());
		String description = faker.lorem().sentence();
		String imageUrl = "https://i.pravatar.cc/100";
		String category = categories[random.nextInt(categories.length)];
		
		return new Product(name,price,description,imageUrl,category);
	}

}
