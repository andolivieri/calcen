package it.andolivieri.calcoloenigmatico;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import it.andolivieri.calcoloenigmatico.generators.JSTPermutationGenerator;
import it.andolivieri.calcoloenigmatico.generators.KPermutationGenerator;

class BadInputException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BadInputException(String message){
		super(message);
	}
}

class Operazione{

	private static enum OperatoreAritmetico{
		SOMMA,
		SOTTRAZIONE,
		MOLTIPLICAZIONE,
		DIVISIONE;

		public static OperatoreAritmetico fromString(String op) throws BadInputException{
			if(op.equals("+")){
				return OperatoreAritmetico.SOMMA;
			}else if(op.equals("-")){
				return OperatoreAritmetico.SOTTRAZIONE;
			}else if(op.equals("*")){
				return OperatoreAritmetico.MOLTIPLICAZIONE;
			}else if(op.equals("/") || op.equals("\\")){
				return OperatoreAritmetico.DIVISIONE;
			}else{
				throw new BadInputException("Operatore sconosciuto: " + op);
			}
		}
	}

	String operand1;
	String operand2;
	String result;
	OperatoreAritmetico operator;

	final static Pattern opPattern = 
			Pattern.compile("^([a-jA-J]+)\\s*([\\+\\-\\*\\/])\\s*([a-jA-J]+)\\s*=\\s*([a-jA-J]+)\\s*$");

	public static Operazione fromString(String descr, int base) throws BadInputException{

		Matcher m = opPattern.matcher(descr);

		if(!m.matches() || m.groupCount() != 4){
			throw new BadInputException("Cannot parse string " + descr);
		}else{
			Operazione o = new Operazione();
			o.operand1 = m.group(1).toLowerCase();
			o.operand2 = m.group(3).toLowerCase();
			o.operator = OperatoreAritmetico.fromString(m.group(2));
			o.result = m.group(4).toLowerCase();
			return o;
		}

	}

	public boolean compute(HashMap<Character, Integer> solution, int base){

		int op1Value = calcolaValoreOperando(solution, operand1, base);
		int op2Value = calcolaValoreOperando(solution, operand2, base);
		int resutValue = calcolaValoreOperando(solution, result, base);

		switch(operator){
		case SOMMA:
			return op1Value + op2Value == resutValue;
		case SOTTRAZIONE:
			return op1Value - op2Value == resutValue;
		case MOLTIPLICAZIONE:
			return op1Value * op2Value == resutValue;
		case DIVISIONE:
			if(op2Value != 0)
				return op1Value / op2Value == resutValue;
			else
				return false;
		default:
			return false;
		}

	}
	
	public int numeroSimboli(){
		String chrs = "";
		int i;
		for(i=0; i< operand1.length(); i++)
			if(chrs.indexOf(operand1.charAt(i)) < 0){
					chrs += operand1.charAt(i);
			}
		for(i=0; i< operand2.length(); i++)
			if(chrs.indexOf(operand2.charAt(i)) < 0){
					chrs += operand2.charAt(i);
			}
		for(i=0; i< result.length(); i++)
			if(chrs.indexOf(result.charAt(i)) < 0){
					chrs += result.charAt(i);
			}
		return chrs.length();
	}

	public int calcolaValoreOperando(HashMap<Character, Integer> solution, String operand, int base){
		int res=0;
		Integer x;
		for(int i=0;i<operand.length();i++){
			x = solution.get(operand.charAt(i));
			res += Math.pow(base, operand.length()-1-i) * x.intValue();
		}
		return res;
	}

}


class Problema{

	public int base = 10;
	public ArrayList<Operazione> operazioni;

	public Problema(){
		this.operazioni = new ArrayList<Operazione>();
	}
	
	public boolean verifica(HashMap<Character, Integer> solution){
		for (Operazione operazione : operazioni) {
			if(!operazione.compute(solution, base))
				return false;
		}
		return true;
	}
	
	public int numeroSimboli(){
		String chrs = "";
		int i;
		for(Operazione o : operazioni){
			for(i=0; i< o.operand1.length(); i++)
				if(chrs.indexOf(o.operand1.charAt(i)) < 0){
						chrs += o.operand1.charAt(i);
				}
			for(i=0; i< o.operand2.length(); i++)
				if(chrs.indexOf(o.operand2.charAt(i)) < 0){
						chrs += o.operand2.charAt(i);
				}
			for(i=0; i< o.result.length(); i++)
				if(chrs.indexOf(o.result.charAt(i)) < 0){
						chrs += o.result.charAt(i);
				}
		}
		return chrs.length();
	}
	



}


class Solutore{
	
		
	
	public void risolvi2(Problema p){
		
		Set<Integer> cifre = new HashSet<Integer>(p.base);
		for(int i=0;i<p.base;i++){
			cifre.add(Integer.valueOf(i));
		}
		
		
		
	}
	
	public void risolvi(Problema p){
		
		Set<Integer> cifre = new HashSet<Integer>(p.base);
		for(int i=0;i<p.base;i++){
			cifre.add(Integer.valueOf(i));
		}
		
		KPermutationGenerator<Integer> gen = new KPermutationGenerator<Integer>(cifre, p.numeroSimboli());
		List<Integer> perm;
		HashMap<Character, Integer> solution;
		String characters = "abcdefghij";
		while(gen.hasNext()) {
			perm = gen.next();
			//JSTPermutationGenerator.print(perm);
			solution = new HashMap<>();
			for(int i=0; i<p.numeroSimboli(); i++){
				solution.put(Character.valueOf(characters.charAt(i)), perm.get(i).intValue());
			}
			if(p.verifica(solution)){
				System.out.println("Trovata: " + solution);
				return;
			}
			
			
		}
		

		System.err.println("Il problema non ammette soluzioni: " + gen.count);
	}
	
	
	
	
}

public class CalcoloEnigmatico {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		if(args.length!=1) throw new Exception("Specificare il path!");
		Path input = Paths.get(args[0]);
		Problema p = leggiDaFile(input, 10);
		
		new Solutore().risolvi(p);

	}

	public static Problema leggiDaFile(Path path, int base) throws IOException, BadInputException{

		Charset charset = Charset.forName("US-ASCII");
		BufferedReader reader =  Files.newBufferedReader(path, charset);

		Problema p = new Problema();
		Operazione o;
		
		String line = null;
		while ((line = reader.readLine()) != null) {
			o = Operazione.fromString(line, base);
			p.operazioni.add(o);
		}
		
		return p;

	}

}
