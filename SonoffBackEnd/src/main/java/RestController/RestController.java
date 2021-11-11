package RestController;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

@org.springframework.web.bind.annotation.RestController
public class RestController {

	OkHttpClient client = new OkHttpClient();
	
	String host = "http://localhost:8081/";

	@RequestMapping(value = "changeStatusOFF", method = RequestMethod.GET)
	public ResponseEntity<Boolean> changeStatusOFF() throws Exception {
		Request request = new Request.Builder().url(host+"changeStatusOFF")
				.get()
				.build();
		Response response = client.newCall(request).execute();
		if (response.isSuccessful()) {
			return new ResponseEntity<>(HttpStatus.OK);
		}
		System.out.println("Something went wrong in changing status to OFF");
		throw new Exception();

	}

	@RequestMapping(value = "changeStatusON", method = RequestMethod.GET)
	public ResponseEntity<Boolean> changeStatusON() throws Exception {
		Request request = new Request.Builder().url(host+"changeStatusON")
				.get()
				.build();
		Response response = client.newCall(request).execute();
		if (response.isSuccessful()) {
			return new ResponseEntity<>(HttpStatus.OK);
		}
		System.out.println("Something went wrong in changing status to ON");
		throw new Exception();

	}

	  @RequestMapping(value="getStatus", method = RequestMethod.GET) 
	  public String getStatus() throws Exception{
		   Request request = new Request.Builder().url(host+"getStatus")
					.get()
				   .build();
		   Response response = client.newCall(request).execute();
		   
		   if(response.body().string()==null) {
			   System.out.println("Something went wrong while getting status");
			   throw new Exception();
		   }
		   
		   return response.body().string();
	  }
	  
	  @SuppressWarnings("unchecked")
	@RequestMapping(value="saveToken", method = RequestMethod.POST) 
	  public ResponseEntity<String> saveToken(@RequestBody String token) throws Exception{		  
		  JSONParser parser = new JSONParser();
	      JSONArray array = new JSONArray();
	        
	      String tokenValue = token.split("=")[1];
	      List<String> tokens = new ArrayList<>();
			
	      try {
	    	  Reader reader = new FileReader("./tokens.json");
	    	  JSONObject jsonObject = (JSONObject) parser.parse(reader);
	    	  tokens = (List<String>) jsonObject.get("token");
	    	  
	    	  for(String tok: tokens) {
	    		  array.add(tok);
	    		  }
	    	  array.add(tokenValue);
	    	  
	    	  jsonObject = new JSONObject();
	    	  jsonObject.put("token",array);
	    	  
	    	  
	    	  FileWriter fw = new FileWriter("./tokens.json", false);
	    	  fw.write(jsonObject.toJSONString());
	    	  fw.close();
	    	  return new ResponseEntity<String>(HttpStatus.OK);
	    	  } catch (IOException | ParseException e) {
	    		  System.out.println("Something went wrong while saving token\n" + e.getMessage());
	    		  throw e;
			}
	  }
	
}