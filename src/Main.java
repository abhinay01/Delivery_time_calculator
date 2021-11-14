import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

class Main {
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	public static void main(String[] args) throws IOException {
		Queue<Order> orderQueue = new LinkedList<Order>();
		Restaurant hotel = new Restaurant();
		InputOrder input_order = new InputOrder();
		LOGGER.log(Level.INFO, "Application Started. Calling readInput function to read input");
		//reading input
		orderQueue = input_order.readInput();
		//processing order
		hotel.order_process(orderQueue);
		LOGGER.log(Level.INFO, "Process completed.Exiting main function");
	}
}