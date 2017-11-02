package model;

public class JsonTester {

	public static void main(String[] args) {
		JsonKit testKit = new JsonKit();
		
		//Create a new profile
		Profile testProfile = 
				new Profile("Bob", "George", "password", 0.0);
		
		//Put the profile in Json form
		System.out.println("Initializing profile...");
		testKit.buildProfile(testProfile);
		System.out.println("Printing json object...");
		System.out.println(testKit.objectString());
		
		//Write the file out
		System.out.println("Writing Json file...");
		testKit.writeProfile("test.txt");
		
		//Load the file in a new JsonKit class
		JsonKit loadTest = new JsonKit();
		System.out.println("Current contents of new JsonKit's class:");
		System.out.println(loadTest.objectString());
		System.out.println("Loading Json file...");
		loadTest.loadProfile("test.txt");
		System.out.println("Loaded contents...");
		System.out.println(testKit.objectString());
		
		System.out.println("Removing test profile...");
		loadTest.deleteProfile("test.txt");
	}

}
