package contadorPalabrasTXT;
import java.util.Map;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
public class ReaderYearly extends Reader{
	private Map<Integer, Map<String, Integer>> wordsByYear;
	public ReaderYearly(String path) {
		super(path);
		this.wordsByYear = new HashMap<>();
		// TODO Auto-generated constructor stub
	}
	@Override
	public void readFile() {
		if(this.isRead()) {
			this.output();
		}else {
			this.tryReadFile();
		}
	}
	private void tryReadFile() {
		try(BufferedReader reader = new BufferedReader(new FileReader(this.getPath()))) {
			String line;
			while(((line = reader.readLine()) != null)) {
				String datePart = line.split(" ", 2)[0];
				String[] dateParts = datePart.split("/");
				if(dateParts.length < 3) continue;
				int year;
				try {
					year = Integer.parseInt(dateParts[2]);
					if(year < 100) {
						year += 2000; // handle dates "21" instead of 2021
					}
				} catch(NumberFormatException e) {
					continue;
				}
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
						
						// Step 4: Get or create the map for that year
						Map<String, Integer> yearMap = wordsByYear.computeIfAbsent(year, y -> new HashMap<>());
						
						for(String word: words) {
							if(word.isEmpty()) {
								continue;
							};
							yearMap.put(word, yearMap.getOrDefault(word, 0) + 1);
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

	@Override
	protected void output() {
		for (Map.Entry<Integer, Map<String, Integer>> entry : wordsByYear.entrySet()) {
			System.out.println("output: "+ entry.getKey());
		    int year = entry.getKey();
		    Map<String, Integer> freqMap = entry.getValue();

		    System.out.println("\nTop palabras en " + year + ":");
		    freqMap.entrySet().stream()
		        .sorted((a, b) -> b.getValue().compareTo(a.getValue()))
		        .limit(10)
		        .forEach(e -> System.out.println(e.getKey() + ": " + e.getValue()));
		}
	}
}
