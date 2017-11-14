package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

public class JsonKit {
	//This is the main object that will be edited
	private JsonObject object;
	
	public JsonKit() {
		object = Json.createObjectBuilder().build();
	}

	public void buildProfile(Profile profile) {
		object = Json.createObjectBuilder()
				.add("firstName", profile.getFirstName())
				.add("lastName", profile.getLastName())
				.add("password", profile.getPassword())
				.add("balance", profile.getBalance())
		.build();
	}
	
	public Profile loadProfile(String fileName) {
		Profile profile = null;
		try {
	        JsonReader reader = Json.createReader(new FileReader("src/resources/" + fileName + ".json"));
	        object = reader.readObject();
	        reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String fname, lname, passwd;
		double balance;
		
		fname = object.getString("firstName");
		lname = object.getString("lastName");
		passwd = object.getString("password");
		balance = object.getJsonNumber("balance").doubleValue();
		
		profile = new Profile(fname, lname, passwd, balance);
		
		return profile;
	}
	
	public void writeProfile(String fileName) {
		try {
			File f = new File("src/resources/" + fileName + ".json");
			f.createNewFile();
			PrintWriter out = new PrintWriter(f);
			//Write the stringified json object to the file
			out.write(object.toString());
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void deleteProfile(String fileName) {
		File f = new File("src/resources/" + fileName + ".json");
		//Make sure this is real so no malicious file paths are entered
		if(!f.isDirectory()) {
			f.delete();
		}
		
	}

	public void buildAdmin (CsAdmin profile) {
		object = Json.createObjectBuilder()
				.add("login", profile.getLogin())
				.add("firstName", profile.getFirstName())
				.add("lastName", profile.getLastName())
				.add("password", profile.getPassword())
		.build();
	}
	
	public CsAdmin openAdmin(String fileName) {
		CsAdmin profile = null;
		try {
			//Begin reading the Json object from text
	        JsonReader reader = Json.createReader(new FileReader("src/resources/" + fileName + ".json"));
	        object = reader.readObject();
	        reader.close();
	        
		} catch (IOException e) {
			System.out.println("Could not find file: " + fileName + ".json");
			return profile;
		}
		
		String login, fname, lname, passwd;
		
		login = object.getString("login");
		fname = object.getString("firstName");
		lname = object.getString("lastName");
		passwd = object.getString("password");
		
		profile = new CsAdmin(login, passwd, fname, lname);
		
		return profile;
	}
	
	public void writeAdmin(String filepath) {
		try {
			PrintWriter out = new PrintWriter("src/resources/" + filepath + ".json");
			//Write the stringified json object to the file
			out.write(object.toString());
			out.close();
		} catch (FileNotFoundException e) {
			System.out.println(e.getCause());
		}
	}

	public void deleteAdmin(String filepath) {
		File f = new File(filepath);
		//Make sure this is real so no malicious file paths are entered
		if(!f.isDirectory()) {
			f.delete();
		}
		
	}	
	
	public String objectString() {
		return object.toString();
	}

	public static void main(String[] args) {
		JsonKit jkit = new JsonKit();
		CsAdmin admin = new CsAdmin("csadmin", "csci323", "Robyn", "Berg");
		
		jkit.buildAdmin(admin);
		jkit.writeAdmin(admin.getLogin());
	}
	
}
