package payloads;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import com.github.javafaker.Faker;

import pojo.Address;
import pojo.Cart;
import pojo.Geolocation;
import pojo.Name;
import pojo.Product;
import pojo.ProductCart;
import pojo.User;

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
	
	// User
	public static User userPayload()
	{
		//name
		String firstname=faker.name().firstName();
		String lastname=faker.name().lastName();
		
		Name name=new Name(firstname,lastname);
		
		//location
		String lat=faker.address().latitude();
		String lng=faker.address().longitude();
		
		Geolocation location=new Geolocation(lat,lng);
		
		
		//Address
		
		String city=faker.address().city();
		String street=faker.address().streetName();
		int number=random.nextInt(100);
		String zipcode=faker.address().zipCode();
		Address address=new Address(city,street,number,zipcode,location);
		
		
		//User
		String email=faker.internet().emailAddress();
		String username=faker.name().username();
		String password=faker.internet().password();
		String phonenumber=faker.phoneNumber().cellPhone();
		
		User user=new User(email,username,password,name,address,phonenumber);
		
		return user;
		
	}
	
	// Cart
	public static Cart cartPayload(int userId) {
		List<ProductCart> products = new ArrayList<>();
		
		// Adding one random product to the cart
		int productId = random.nextInt(100);
		int quantity = random.nextInt(10)+1;
		products.add(new ProductCart(productId,quantity));
		
		return new Cart(userId,new Date(),products);
		
	}

}
