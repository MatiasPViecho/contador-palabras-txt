package contadorPalabrasTXT;

/**
 * TODO: 
 * - Herencia mal hecha, cambiar por patrón strategy o rever
 * - Permitir seleccionar cantidad de palabras por consola
 * - Permitir seleccionar archivo de texto por consola
 * - Opción TXT default - TXT wsp (para utilizar o no los regex, -> enviar otra clase helper)
 * - Ejecución de archivo
 * - Interfaz gráfica?
 */
public class Main {
	public static void main(String [] args) {
		String path = "";
		Reader reader = new ReaderYearly(path);
		reader.readFile();
	}
}
