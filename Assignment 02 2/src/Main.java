import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

import com.google.gson.Gson;

public class Main {

	protected static Scanner scanner = new Scanner(System.in);
	public static String dest;
	protected static FileReader f;
	public static void main(String[] args) {
		Code[] code;
		while (true) {
			System.out.println("Enter an input file");
			dest = scanner.nextLine();
			try {
				f = new FileReader(dest);
				Gson gson = new Gson();
				code = gson.fromJson(f, Code[].class);
				break;
			} 
			catch (FileNotFoundException rr) {
				System.out.println("File not found, enter a new input file");
			}
		}
		Dictionary<String> input = new Dictionary<String>();
		for (Code co : code) {
			for (String c : co.codes) {
				input.add(c);
			}
		}
		while (true) {
			System.out.println("What would you like to do with your database of codes?");
			System.out.println("     1) Get frequency of a code");
			System.out.println("     2) Check if a code was guessed");
			System.out.println("     3) Remove a code");
			System.out.println("     4) Quit");
			switch(scanner.nextLine()) {
			case "1": //if the user inputs 1
				System.out.println("Enter a code to check its frequency");
				String type = scanner.nextLine();
				int fr = input.frequency(type);
				System.out.println(type + " was guessed by " + fr + " teammates");
				break;
			case "2": //if the user inputs 4
				System.out.println("Enter a code to check if it was guessed by a teammate");
				type = scanner.nextLine();
				boolean h = input.contains(type);
				if (h) System.out.println(type + " was guessed by a teammate");
				else System.out.println(type + " was not guessed by a teammate");
				break;
			case "4":
				System.out.println("Quitting, have a nice day!");
				return;
			case "3":
				System.out.println("Enter a code to remove");
				type = scanner.nextLine();
				while (input.contains(type) == true) {
					input.remove(type);
				}
				System.out.println(type + " was removed from the database");
			}
		}
			
	}
}
