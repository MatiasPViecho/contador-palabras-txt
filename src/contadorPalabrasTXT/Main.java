package contadorPalabrasTXT;

public class Main {
	public static void main(String [] args) {
		String path = "";
		Reader reader = new ReaderYearly(path);
		reader.readFile();
	}
}
