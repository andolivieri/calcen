package it.andolivieri.calcoloenigmatico.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Problem{

	public int base = 10;
	public ArrayList<Expression> expressions;

	public Problem(){
		this.expressions = new ArrayList<Expression>();
	}
	
	public boolean verifySolution(HashMap<Character, Integer> solution){
		for (Expression operazione : expressions) {
			if(!operazione.evaluate(solution, base))
				return false;
		}
		return true;
	}
	
	public int numeroSimboli(){
		String chrs = "";
		int i;
		for(Expression o : expressions){
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
	
	public static Problem generate(){
		
		Problem p = new Problem();
		
		Map<Character, Integer> char2int = new HashMap<Character, Integer>();
				
		
		return null;
	}
	
	private String randomExpr(){
		Random r = new Random();
		Operation o;
		switch(r.nextInt(3)){
			case 0:
				o = Operation.SUM;
			default:
				break;
		}
		return null;
	}
	

}