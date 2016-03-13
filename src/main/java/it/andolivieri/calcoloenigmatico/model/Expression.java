package it.andolivieri.calcoloenigmatico.model;

import java.util.Comparator;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Expression{

	String operand1;
	String operand2;
	String result;
	Operation operator;
	SortedSet<Character> characterSet;

	final static Pattern opPattern = 
			Pattern.compile("^([a-zA-Z]+)\\s*([\\+\\-\\*\\/])\\s*([a-zA-Z]+)\\s*=\\s*([a-zA-Z]+)\\s*$");

	public static Expression fromString(String descr, int base) throws BadInputException{

		Matcher m = opPattern.matcher(descr);

		if(!m.matches() || m.groupCount() != 4){
			throw new BadInputException("Cannot parse string " + descr);
		}else{
			Expression o = new Expression();
			o.operand1 = m.group(1).toLowerCase();
			o.operand2 = m.group(3).toLowerCase();
			o.operator = Operation.fromString(m.group(2));
			o.result = m.group(4).toLowerCase();
			return o;
		}

	}

	public boolean evaluate(Map<Character, Integer> solution, int base){

		int op1Value = calcolaValoreOperando(solution, operand1, base);
		int op2Value = calcolaValoreOperando(solution, operand2, base);
		int resutValue = calcolaValoreOperando(solution, result, base);

		switch(operator){
		case SUM:
			return op1Value + op2Value == resutValue;
		case SUBTRACT:
			return op1Value - op2Value == resutValue;
		case MOLTIPLY:
			return op1Value * op2Value == resutValue;
		case DIVIDE:
			if(op2Value != 0)
				return op1Value / op2Value == resutValue;
			else
				return false;
		default:
			return false;
		}

	}

	public SortedSet<Character> placeholders(){
		if(characterSet == null){
			
			characterSet = new TreeSet<Character>(
					new Comparator<Character>() {
						public int compare(Character o1, Character o2) {
							return o1.compareTo(o2);
						}
					});
			int i;
			for(i=0; i< operand1.length(); i++)
				characterSet.add(Character.valueOf(operand1.charAt(i)));
			for(i=0; i< operand2.length(); i++)
				characterSet.add(Character.valueOf(operand2.charAt(i)));
			for(i=0; i< result.length(); i++)
				characterSet.add(Character.valueOf(result.charAt(i)));

		}
		return characterSet;
	}

	public int placeholdersCount(){
		return placeholders().size();
	}

	public int calcolaValoreOperando(Map<Character, Integer> solution, String operand, int base){
		int res=0;
		Integer x;
		for(int i=0;i<operand.length();i++){
			x = solution.get(operand.charAt(i));
			res += Math.pow(base, operand.length()-1-i) * x.intValue();
		}
		return res;
	}
	@Override
	public String toString(){
		return operand1 + operator.toString() + operand2 + "=" + result; 
	}
	
	@Override
	public boolean equals(Object o1){
		if(o1==null)
			return false;
		if(!(o1 instanceof Expression))
			return false;
		Expression e1 = (Expression)o1;
		return e1.operand1 == operand1 && 
				e1.operand2 == operand2 && 
				e1.result == result && 
				e1.operator == operator;
		
	}

}