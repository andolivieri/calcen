package it.andolivieri.calcoloenigmatico.model;

import java.io.BufferedReader;
import java.io.IOException;
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
		// TODO Auto-generated method stub
		if(args.length!=1) {
			throw new IllegalArgumentException("Specificare il path!");
		}
		Path input = Paths.get(args[0]);

		CalcoloEnigmatico c = new CalcoloEnigmatico();
		Problem p = c.leggiDaFile(input, 10);
		
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

	public Problem leggiDaFile(Path path, int base) throws IOException, BadInputException{

		Charset charset = Charset.forName("US-ASCII");
		BufferedReader reader =  Files.newBufferedReader(path, charset);

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
