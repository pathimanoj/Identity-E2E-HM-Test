package car.processor;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import car.filereader.GenericFileReader;

public class CarInputProcessor {

	private  List<String> regNumbers = new ArrayList<>();
	private static final String REG_PATTERN = "[A-Za-z]{2}\\d{2}\\s*[A-Za-z]{3}";

	public CarInputProcessor(String fileName) throws FileNotFoundException, IOException {
		List<String> lines = GenericFileReader.readFile(fileName);

		for (String str : lines) {
			Pattern p = Pattern.compile(REG_PATTERN);
			Matcher m = p.matcher(str);
			while (m.find()) {
				String theGroup = m.group(0);
				regNumbers.add(theGroup.replaceAll("\\s", ""));
			}
		}

	}

	public  List<String> getAllRegistrationNumbers() {
		return regNumbers;
	}
	
	public static void main(String []args) throws FileNotFoundException, IOException {
		CarInputProcessor c= new CarInputProcessor(System.getProperty("user.dir")+ "/src/test/java/resources/car_input.txt");
		System.out.println(c.getAllRegistrationNumbers());
	}
}
