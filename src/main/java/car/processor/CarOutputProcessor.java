package car.processor;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import car.domain.Car;
import car.filereader.GenericFileReader;

public class CarOutputProcessor {

	private  Map<String, Car> outputMap = new HashMap<>();

	public CarOutputProcessor(String fileName) throws FileNotFoundException, IOException {
		List<String> lines = GenericFileReader.readFile(fileName);
		convertLinesToMap(lines);
	}

	private void convertLinesToMap(List<String> lines) {

		for (int i = 1; i < lines.size(); i++) {
			String[] columns = lines.get(i).split(",");
			if (columns != null && columns.length >= 5) {
				Car car = new Car();
				car.setRegistration(columns[0]);
				car.setMake(columns[1]);
				car.setModel(columns[2]);
				car.setColor(columns[3]);
				car.setYear(columns[4]);
				outputMap.put(columns[0].toLowerCase(), car);
			}

		}

	}

	public boolean isCarByRegistrationNumberFound(String regNo) {
		return outputMap.containsKey(regNo.toLowerCase());
	}

	public Car getCarByRegistrationNumberFound(String regNo) {
		if (regNo != null && isCarByRegistrationNumberFound(regNo)) {
			return outputMap.get(regNo.toLowerCase());
		}

		return null;

	}

	public boolean compareWithGivenCar(Car car) {

		Car carResult = getCarByRegistrationNumberFound(car.getRegistration());
		if (carResult == null)
			return false;

		return carResult.equals(car);

	}
}
