package contadorPalabrasTXT;

public class Main {
	public static void main(String [] args) {
		String path = "PATH_HERE";
		Reader reader = new Reader(path);
		reader.readFile();
	}
}
