/**
 * 
 */
package com.qa.testcases;

import org.testng.annotations.Test;
import static com.jayway.restassured.RestAssured.*;
import static org.hamcrest.Matchers.lessThan;

import java.util.ArrayList;

import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import com.qa.classes.Info;
import com.qa.classes.Posts;
import com.qa.classes._Posts;

import com.qa.classes.advance.AdvInfo;
import com.qa.classes.advance.AdvPosts;

import net.minidev.json.JSONArray;
/**
 * @author nhan.nguyen
 *
 */
public class JsonServerRequest {
	
	//GET
	@Test(enabled = true)
	public void test_01(){
		getAllAPI("http://localhost:3000/posts");
	}
	
	public void getAllAPI(String urlApi){
		Response resp = given().
				when().
				get(urlApi);
		System.out.println("[Get response is : "+resp.asString()+"]");
	}
	
	//POST   /posts
	@Test(enabled = false)
	public void test_02(){
		Response resp = given().
				body("{\"id\":\"2\","
						+ "\"title\": \"My Books\","
						+ "\"author\": \"Nhan Nguyen\"}").
				when().
				contentType(ContentType.JSON).
				post("http://localhost:3000/posts");
		System.out.println("[Get response is : "+resp.asString()+"]");
	}
	
	public void postJsonApi(String url, String id, String title, String author, String description, String location){
		
		Posts posts = new Posts();
		posts.setId(id);
		posts.setTitle(title);
		posts.setAuthor(author);
		posts.setDescription(description);
		posts.setLocation(location);
		Response resp = given().
				when().
				contentType(ContentType.JSON).
				body(posts).
				post(url);
		System.out.println("[POST response is : "+resp.asString()+"]");
	}
	
	
	
	//POST   /posts with Object
		@Test(enabled = false)
		public void test_03(){
			Posts posts = new Posts();
			posts.setId("4");
			posts.setTitle("My book 4");
			posts.setAuthor("Nhan");
			posts.setImage("C:\\Users\\vuong.dd\\Desktop\\hinh.jpg");
			Response resp = given().
					when().
					contentType(ContentType.JSON).
					body(posts).
					post("http://localhost:3000/posts");
			System.out.println("[POST response is : "+resp.asString()+"]");
		}
		
		//GET   /posts/3
		@Test(enabled = false)
		public void test_04(){
//			int i=3;
//			Response resp = given().
//					when().
//					get("http://localhost:3000/posts/"+ i);
//			System.out.println("[Get response is : "+resp.asString()+"]");
				
		}
		
		
		//PUT   /posts/3
		@Test(enabled = false)
		public void test_05(){
			Posts posts = new Posts();
			posts.setId("2");
			posts.setAuthor("Ngoc");
			posts.setTitle("Updated Title name");
			Response resp = given().
				when().
				contentType(ContentType.JSON).
				body(posts).
				put("http://localhost:3000/posts/2");
			System.out.println("[PUT API response is : "+resp.asString()+"]");
		}	
		//PATCH   /posts/2
		@Test(enabled = false)
		public void test_06(){
			Response resp = given().
						when().
						contentType(ContentType.JSON).
						body("{\"title\": \"Updated by PUT Request: My Books\"}").
						patch("http://localhost:3000/posts/2");
			System.out.println("[PATCH API response is : "+resp.asString()+"]");
		}
		//DELETE   /posts/4
		@Test(enabled = false)
		public void test_07(){
					
			Response resp = given().
							when().
							delete("http://localhost:3000/posts/4");
			System.out.println("[DELETE API response is : "+resp.asString()+"]");
		}
		
		//Complex Posts
		//Post /posts
		@Test(enabled = false)
		public void test_08(){
			Info info = new Info();
			info.setEmail("test@test.com");
			info.setPhone("0909090909");
			info.setAddress("123 abc");
			
			_Posts posts = new _Posts();
			posts.setAuthor("Nhan");
			posts.setId("5");
			posts.setTitle("Nguyen");
			posts.setInfo(info);
			
			Response resp = given().
							when().
							contentType(ContentType.JSON).
							body(posts).
							post("http://localhost:3000/posts");
			System.out.println("[POSTS API response is : "+resp.asString()+"]");
		}
		@Test(enabled = false)
		public void test_09(){
			AdvInfo info1 = new AdvInfo();
			info1.setEmail("test1@test1.com");
			info1.setPhone("0101010101");
			info1.setAddress("11111 abccdddc");
			
			AdvInfo info2 = new AdvInfo();
			info2.setEmail("test2@test2.com");
			info2.setPhone("0202020202");
			info2.setAddress("22222 abccdddc");
			
			AdvPosts posts = new AdvPosts();
			posts.setAuthor("Nhan Nguyen");
			posts.setId("2");
			posts.setTitle("My Books");
			posts.setInfo(new AdvInfo[]{info1,info2});
			
			Response resp = given().
							when().
							contentType(ContentType.JSON).
							body(posts).
							post("http://localhost:3000/posts");
			System.out.println("[POSTS API response is : "+resp.asString()+"]");
		}

		//Get Request calculate Response time
		//GET /posts
		@Test(enabled=false)
		public void test_10(){
			Response resp = given().
					when().
					get("http://localhost:3000/posts");
			Long time = resp.then().extract().time();
			
	System.out.println("[Response time is : "+time+"]");
	
	given().
	when().
	get("http://localhost:3000/posts").
	then().and().time(lessThan(400L));
		}
}
