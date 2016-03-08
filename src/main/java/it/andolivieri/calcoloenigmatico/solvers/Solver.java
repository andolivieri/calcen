package it.andolivieri.calcoloenigmatico.solvers;

import java.util.Map;

import it.andolivieri.calcoloenigmatico.model.Problem;

public abstract class Solver {

	public long lastExecTime = -1;
	public long steps = 0;
	
	public Map<Character, Integer> solve(Problem p){
		steps = 0;
		long startTs = System.currentTimeMillis();
		Map<Character, Integer>  sol = findSolution(p);
		lastExecTime = System.currentTimeMillis() - startTs;
		return sol;
		
	}
	
	protected abstract Map<Character, Integer> findSolution(Problem p);
	
}
