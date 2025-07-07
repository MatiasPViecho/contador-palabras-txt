package contadorPalabrasTXT;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
public class Reader {
	private static final int MAX_AMOUNT = 1000;
	private String path;
	private int totalWords;
	private boolean read;
	private Map<String, Integer> wordFrecuency;
	public Reader(String path) {
		this.path = path;
		this.totalWords = 0;
		this.read = false;
		this.wordFrecuency = new HashMap();
	}
	public void setPath(String path) {
		if(!this.path.equals(path)) {
			this.path = path;
			this.setRead(false);
		}
	}
	public void readFile() {
		if(this.read) {
			this.output();
		}else {
			this.tryReadFile();
		}
	}
	protected String getPath() {
		return this.path;
	}//
	private void tryReadFile() {
		try(BufferedReader reader = new BufferedReader(new FileReader(this.getPath()))) {
			String line;
			while(((line = reader.readLine()) != null)) {
				int idx = line.indexOf(" - ");
				if(idx != -1) {
					int msgStart = line.indexOf(": ", idx + 3);
					if(msgStart != -1) {
						String message = line.substring(msgStart + 2).trim().toLowerCase();
						if (message.contains("multimedia omitido") || message.contains("mensaje omitido")) continue;
						// quita de puntuación con regex
						message.replaceAll("[^\\p{L}\\p{N}\\s]", "");
						// Separación de palabras
						String[] words = message.trim().split("\\s+");
						for(String word: words) {
							if(word.isEmpty()) continue;
							this.sumeOneTotalWords();
							wordFrecuency.put(word, wordFrecuency.getOrDefault(word, 0) + 1);
						}
						
					}
				}
			}
			this.setRead(true);
			this.readFile();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	protected void setRead(boolean value) {
		this.read = value;
	}
	public boolean isRead() {
		return this.read;
	}
	private void sumeOneTotalWords() {
		this.totalWords++;
	}
	protected void output() {
		System.out.println("Total de palabras: " + this.totalWords + "\nPalabras más frecuentes:\n");
		wordFrecuency.entrySet().stream().sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue())).limit(MAX_AMOUNT).forEach(entry -> System.out.println(entry.getKey() + ": " + entry.getValue()));
		System.out.println("Palabras menos frecuentes");
		wordFrecuency.entrySet().stream().sorted((e1, e2) -> e1.getValue().compareTo(e2.getValue())).limit(MAX_AMOUNT).forEach(entry -> System.out.println(entry.getKey() + ": " + entry.getValue()));
		//wordFrecuency.entrySet().stream().sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue())).limit(MAX_AMOUNT).forEach(entry -> System.out.println(entry.getValue()));
		//wordFrecuency.entrySet().stream().sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue())).limit(MAX_AMOUNT).forEach(entry -> System.out.println(entry.getKey()));
	}
}
