package client_to_movies_v3;

public class IdGenerator {
	public static String idGenerator(Product product) {
		if (product instanceof Movie) {
			return "MOV" + System.nanoTime();
		}
		else if (product instanceof Game) {
			return "GAM" + System.nanoTime();
		}
		else if (product instanceof Book) {
			return "BOO" + System.nanoTime();
		}
		return null;
	}
}
