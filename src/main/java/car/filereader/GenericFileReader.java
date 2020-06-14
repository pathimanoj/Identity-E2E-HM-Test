package car.filereader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GenericFileReader {
	
	
	public static List<String> readFile(String fileName) throws FileNotFoundException, IOException{
		ArrayList<String> result = new ArrayList<>();
		 
		try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
		    while (br.ready()) {
		        result.add(br.readLine());
		    }
		}

		return result;
	}
}
