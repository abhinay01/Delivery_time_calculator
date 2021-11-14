import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.Queue;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

class Restaurant {
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	static Properties prop = new Properties();
	static {
		try {
			prop.load(new FileInputStream("src/resource/config.properties"));
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	int MAX_SLOT = Integer.parseInt((prop.getProperty("MAX_SLOT")));
	int AVAILABLE_SLOT = MAX_SLOT;
	SortedMap<Double, Integer> ProcessingOrderMap = new TreeMap<Double, Integer>();

	public void order_process(Queue<Order> orderQueue) {
		LOGGER.log(Level.INFO, "Entering order_process function");
		for (Order order : orderQueue) {
			int slot_required = 0;
			int cooking_time = 0;
			double delivering_time = 0.0;
			double total_time_taken = 0.0;
			boolean is_appetiser_included = false;
			boolean is_main_course_included = false;
			double time_taken_by_previous_order = 0.0;
			int slot_to_be_freed = 0;
			//calculating required slots for order to be executed
			for (int i = 0; i < order.mealType.size(); i++) {
				if (order.mealType.get(i).equals(Meal.A)) {
					slot_required++;
					is_appetiser_included = true;
				} else {
					slot_required += 2;
					is_main_course_included = true;
				}
			}
			//when order is not accommodated in single try
			if (slot_required > MAX_SLOT) {
				System.out.println("Order " + order.orderId + " is denied because the restaurant cannot accommodate it.");
				continue;
			}
			//freeing slot by removing completed order for new order to process
			if (slot_required > AVAILABLE_SLOT) {
				for (@SuppressWarnings("rawtypes") Map.Entry mapElement : ProcessingOrderMap.entrySet()) {
					time_taken_by_previous_order = (double) mapElement.getKey();
					slot_to_be_freed = (int) mapElement.getValue();
					AVAILABLE_SLOT = AVAILABLE_SLOT + slot_to_be_freed;
					ProcessingOrderMap.remove(time_taken_by_previous_order);
					if (slot_required <= AVAILABLE_SLOT)
						break;
				}
			}
			//processing the order
			if (slot_required <= AVAILABLE_SLOT) {
				if (is_main_course_included == true) {
					cooking_time = cooking_time + Integer.parseInt((prop.getProperty("Main_course_meal_time")));
					is_appetiser_included = false;
					is_main_course_included = false;
				} else if (is_appetiser_included == true) {
					cooking_time = cooking_time + Integer.parseInt((prop.getProperty("Apetiser_meal_time")));
					is_appetiser_included = false;
					is_main_course_included = false;
				}
			}
			delivering_time = order.distance * Integer.parseInt((prop.getProperty("Time_taken_per_KM_in_minutes")));
			total_time_taken = delivering_time + cooking_time + time_taken_by_previous_order;
			ProcessingOrderMap.put(total_time_taken, slot_required);
			System.out.println("Order "+order.orderId+" will get delivered in "+total_time_taken+" minutes");
			AVAILABLE_SLOT = AVAILABLE_SLOT - slot_required;
			time_taken_by_previous_order = total_time_taken;
		}
		LOGGER.log(Level.INFO, "Exiting order_process function");
	}
}
