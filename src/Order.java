import java.util.ArrayList;
import java.util.List;

public class Order {
	int orderId;
	List<Meal> mealType = new ArrayList<Meal>();
	double distance;

	public Order(int orderId, List<Meal> mealType, double distance) {
		super();
		this.orderId = orderId;
		this.mealType = mealType;
		this.distance = distance;
	}
}