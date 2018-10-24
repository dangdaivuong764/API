/**
 * 
 */
package com.qa.testcases;

import org.testng.Assert;
import org.testng.annotations.Test;
import static com.jayway.restassured.RestAssured.*;

import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import com.qa.utils.ReadProperties;

/**
 * @author nhan.nguyen
 *http://jsonviewer.stack.hu/
 *https://jsonpath.curiousconcept.com/
 */
public class WeatherGetRequests {

	public static String apiKey = ReadProperties.getConfig("appid");
	public static String apiKey1 = ReadProperties.getConfig("appid1");

	/*
	 * Simple get request for getting weahter by city name
	 * https://samples.openweathermap.org/data/2.5/weather? q=London,uk& appid=
	 */
	@Test(enabled = true)
	public void Test_Simple_Get_Request() {
		Response resp = when().get("https://samples.openweathermap.org/data/2.5/weather?q=London,uk&appid=" + apiKey1);

		System.out.println(resp.getStatusCode());
		Assert.assertEquals(resp.getStatusCode(), 200);
	}

	@Test(enabled = false)
	public void Test_Simple_Get_Request_Fail() {
		Response resp1 = when().get("https://samples.openweathermap.org/data/2.5/weather?q=London,uk&appid=" + apiKey1);

		System.out.println(resp1.getStatusCode());
		Assert.assertEquals(resp1.getStatusCode(), 401);
	}

	/*
	 * How to use parameters with rest assured
	 * q=London,uk&appid=b6907d289e10d714a6e88b30761fae22
	 */
	@Test(enabled = true)
	public void Test_Simple_Get_Request_Parameters() {

		Response resp = given().param("q", "London").param("appid", apiKey).when()
				.get("https://samples.openweathermap.org/data/2.5/weather");

		System.out.println(resp.getStatusCode());
		Assert.assertEquals(resp.getStatusCode(), 200);
		if (resp.getStatusCode() == 200) {
			System.out.println("API is working fine!");
		} else {
			System.out.println("API is not woking fine!");
		}
	}

	/*
	 * How to use parameters with rest assured
	 */
	@Test(enabled = true)
	public void Test_Simple_Get_Request_Parameters_Other_Assert() {

		given().
		param("q", "London").
		param("appid", apiKey)
		.when()
		.get("https://samples.openweathermap.org/data/2.5/weather").
		then().
		assertThat().
		statusCode(200);

	}
	/*
	 * How to use parameters with rest assured
	 */
	@Test(enabled = true)
	public void Test_Simple_Get_Request_Parameters_Get_String() {

		Response resp = given().
		param("q", "London").
		param("appid", apiKey)
		.when()
		.get("https://samples.openweathermap.org/data/2.5/weather");
		Assert.assertEquals(resp.getStatusCode(), 200);
		System.out.println(resp.asString());
	}
	/*
	 * How to use parameters with rest assured with the id from country: AU
	 */
	@Test(enabled = true)
	public void Test_Simple_Get_Request_With_ID(){
		Response resp = given().
				param("id", "2172797").
				param("appid", apiKey)
				.when()
				.get("https://samples.openweathermap.org/data/2.5/weather");
		Assert.assertEquals(resp.getStatusCode(), 200);
		System.out.println(resp.asString());
	}
	/*
	 * How to use parameters with rest assured with the id from country: AU
	 */
	@Test(enabled = false)
	public void Test_Simple_Get_Request_With_Lat_Lon(){
		Response resp = given().
				param("lat", "35").
				param("lon", "139").
				param("appid", apiKey)
				.when()
				.get("https://samples.openweathermap.org/data/2.5/weather");
		Assert.assertEquals(resp.getStatusCode(), 200);
		System.out.println(resp.asString());
	}
	
	/*
	 * How to use parameters with rest assured with the id and check the description
	 */
	@Test(enabled = false)
	public void Test_Simple_Get_Request_With_ID_CheckContent(){
		String strWeatherReport = given().
				parameter("id", "2172797").
				parameter("appid", apiKey).
				when().
				get("https://samples.openweathermap.org/data/2.5/weather").
				then().
				contentType(ContentType.JSON).
				extract().
				path("weather[0].description");
		
		System.out.println("[WeatherReport: "+ strWeatherReport +"]");
	}
	
	@Test(enabled = false)
	public void Test_Simple_Get_Request_With_ID_CheckContentDescription(){
		
		Response resp = given().
				param("id", "2172797").
				param("appid", apiKey)
				.when()
				.get("https://samples.openweathermap.org/data/2.5/weather");
		String actualWeatherReport = resp.
				then().
				contentType(ContentType.JSON).
				extract().
				path("weather[0].description");
		String expectedWeatherReport= "scattered clouds";
		if(actualWeatherReport.equalsIgnoreCase(expectedWeatherReport)){
			System.out.println("[Testcase pass!]");
		}else{
			System.out.println("[Testcase fail!]");
		}
		
	}
	
	@Test(enabled = false)
	public void Test_Simple_Get_Request_With_CityID(){
		
		Response resp = given().
				parameter("id", "2172797").
				parameter("appid", apiKey)
				.when()
				.get("https://samples.openweathermap.org/data/2.5/weather");
		
		
		String reportByID = resp.
				then().
				contentType(ContentType.JSON).
				extract().
				path("weather[0].description");
		
		System.out.println("[WeatherReport description by ID: "+ reportByID +"]");
		
		String lon = String.valueOf(resp.
				then().
				contentType(ContentType.JSON).
				extract().
				path("coord.lon"));
		
		System.out.println("[longitude is: "+ lon +"]");
		
		String lat = String.valueOf(resp.
				then().
				contentType(ContentType.JSON).
				extract().
				path("coord.lat"));
		
		System.out.println("[latitude is: "+ lat +"]");
	}
}
