package it.andolivieri.calcoloenigmatico.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import it.andolivieri.calcoloenigmatico.solvers.SmartSolver;
import it.andolivieri.calcoloenigmatico.solvers.Solver;

class BadInputException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BadInputException(String message){
		super(message);
	}
}

public class CalcoloEnigmatico {

	public static void main(String[] args) throws Exception {
		
		String path = args.length > 0 ? args[0] : null;

		CalcoloEnigmatico c = new CalcoloEnigmatico();
		Problem p = c.leggiDaFile(path, 10);
		
		Solver solver = new SmartSolver();
		
		Map<Character, Integer> result = solver.solve(p);
		
		if(result != null)
			System.out.print(result);
		else
			System.err.print("Nessuna soluzione per il problema");
		
		System.out.println(
				String.format(" [%s steps in %s msec]", solver.steps, solver.lastExecTime)
				);

	}

	public Problem leggiDaFile(String path, int base) throws IOException, BadInputException, URISyntaxException{

		ClassLoader classLoader = getClass().getClassLoader();
		
		
		Charset charset = Charset.forName("US-ASCII");
		
		Path filePath;
		
		if(path==null){
			filePath = Paths.get(classLoader.getResource("sample.txt").toURI());
		}else{
			filePath = Paths.get(path);
		}
		
		BufferedReader reader =  Files.newBufferedReader(filePath, charset);

		Problem p = new Problem();
		Expression o;

		String line = null;
		while ((line = reader.readLine()) != null) {
			o = Expression.fromString(line, base);
			p.expressions.add(o);
		}

		return p;

	}

}
