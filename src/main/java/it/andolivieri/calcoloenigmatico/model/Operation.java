package it.andolivieri.calcoloenigmatico.model;

public enum Operation{
	SUM('+'),
	SUBTRACT('-'),
	MOLTIPLY('*'),
	DIVIDE(':');

	private char value;
	
	private Operation(char value){
		this.value = value;
	}
	
	public static Operation fromString(String op) throws BadInputException{
		if(op.equals("+")){
			return Operation.SUM;
		}else if(op.equals("-")){
			return Operation.SUBTRACT;
		}else if(op.equals("*")){
			return Operation.MOLTIPLY;
		}else if(op.equals("/") || op.equals("\\")){
			return Operation.DIVIDE;
		}else{
			throw new BadInputException("Operatore sconosciuto: " + op);
		}
	}
	
	public String toString(){
		return value+"";
	}
}