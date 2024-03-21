
public class App {
	private String data="7dcba47b33fec020153d0b7d4c9628815ace695fb7adc4b6dd69f959f9dbf8ba";

	public static void main(String[] args) {
		 String data="7dcba47b33fec020153d0b7d4c9628815ace695fb7adc4b6dd69f959f9dbf8ba";
		Transaction trans=new Transaction("001", "002", 100);
		Block block=new Block(trans, null);
		String jsonString=block.getJsonObject();
		System.out.println(jsonString);
		String hashed=block.calculateSHA256Hash(jsonString);
		block.setHash(hashed);
		
		
		
		Transaction trans1=new Transaction("003", "004", 200);
		Block block1=new Block(trans, hashed);
		String jsonString1=block1.getJsonObject();
		String hashes1=block1.calculateSHA256Hash(jsonString1);
		block1.setHash(hashes1);
		
		
		
	}
}
