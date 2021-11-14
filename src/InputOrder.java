import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class InputOrder {

	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	Queue<Order> readInput() throws IOException {
		Queue<Order> orderQueue = new LinkedList<Order>();
		JSONParser parser = new JSONParser();
		try {
			//reading txt file as JSON Array
			JSONArray jsonArray = (JSONArray) parser.parse(new FileReader("input.txt"));
			LOGGER.log(Level.INFO, String.valueOf(jsonArray));
			// System.out.println(jsonArray);
			int i = 0;
			while (jsonArray.size() > i) {
				List<Meal> meals = new ArrayList<Meal>();
				//converting JSON array to JSONobject
				JSONObject jsonObject = (JSONObject) jsonArray.get(i);
				int orderId = Integer.parseInt(String.valueOf(jsonObject.get("orderId")));
				JSONArray meal = (JSONArray) jsonObject.get("meals");
				Double distance = Double.parseDouble(String.valueOf(jsonObject.get("distance")));
				for (int k = 0; k < meal.size(); k++) {
					String temp = (String) meal.get(k);
					if ((Meal.A).toString().equals(temp))
						meals.add(Meal.A);
					else
						meals.add(Meal.M);
				}
				i++;
				Order order = new Order(orderId, meals, distance);
				//updating the order queue with details
				orderQueue.add(order);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		LOGGER.log(Level.INFO, "Exiting readInput function");
		return orderQueue;
	}
}