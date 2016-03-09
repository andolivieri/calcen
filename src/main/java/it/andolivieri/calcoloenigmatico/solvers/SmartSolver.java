package it.andolivieri.calcoloenigmatico.solvers;

import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import it.andolivieri.calcoloenigmatico.model.Expression;
import it.andolivieri.calcoloenigmatico.model.Problem;
import it.andolivieri.calcoloenigmatico.solvers.generators.KPermutationGenerator;

public class SmartSolver extends Solver{

	static final String characters = "abcdefghij";
	static final int base = 10;

	protected Map<Character, Integer> findSolution(Problem p){

		Map<Character, Integer> solution = new HashMap<Character, Integer>();

		Set<Integer> digits = new HashSet<Integer>(p.base);
		for(int i=0;i<p.base;i++){
			digits.add(Integer.valueOf(i));
		}

		Set<Expression> expressions = new HashSet<Expression>();

		for(Expression e : p.expressions){
			expressions.add(e);
		}

		if(!recurseFind(expressions, digits, solution)){
			return null;
		}

		return solution;

	}

	private boolean recurseFind(Set<Expression> expressions, Set<Integer> digits, Map<Character, Integer> solution){

		Expression curExp;
		KPermutationGenerator<Integer> permGen;
		List<Integer> curPerm;
		Map<Character, Integer> curSol = new HashMap<Character, Integer>(solution);
		boolean willItBlend = false;


		// Base step: no more expressions to evaluate => solution found
		if(expressions.isEmpty()){
			return true;
		}


		// Recursive step: solution is still partial OR more expression to compute

		// Pick the easiest expression given current solution
		// i.e. the one with least unsolved symbols
		// from given solution
		curExp = popEasyiestExpression(expressions, solution);


		// Solution is already complete, just proceed verification
		if(digits.size() == 0){
			if(curExp.evaluate(solution, base)){
				willItBlend = recurseFind(expressions, digits, curSol);
			}else{
				willItBlend = false;
			}
			expressions.add(curExp);
			return willItBlend;
		}

		// Find solution on current expression digits - solution digits
		Set<Character> diff = new HashSet<Character>(curExp.placeholders());
		diff.removeAll(solution.keySet());
		permGen = new KPermutationGenerator<Integer>(digits, diff.size());

		while(permGen.hasNext()) {
			steps++;
			curPerm = permGen.next();
			curSol.putAll(solution);
			int i = 0;
			for (Character c : diff) {
				curSol.put(c, curPerm.get(i).intValue());
				i++;
			}

			if(curExp.evaluate(curSol, base)){
				// Pop digits
				for (Character ch : curSol.keySet()) {
					digits.remove(curSol.get(ch));
				}

				willItBlend = recurseFind(expressions, digits, curSol);

				if(willItBlend){
					solution.clear();
					solution.putAll(curSol);
					break;
				}else{
					// push digits back in
					for (Character ch : curSol.keySet()) {
						digits.add(curSol.get(ch));
					}
				}

			}else{
				curSol.clear();
			}

		}

		expressions.add(curExp);

		return willItBlend;

	}

	public Expression popEasyiestExpression(Set<Expression> expressions, Map<Character, Integer> solution){

		int bestMatchValue = Integer.MAX_VALUE;
		Expression best = null;

		// Pick the expression with the greatest number
		// of placeholders contained in given solution
		Set<Character> solutionChrs = solution.keySet();
		Set<Character> difference;
		for (Expression e : expressions) {
			difference = new HashSet<Character>(e.placeholders());
			difference.removeAll(solutionChrs);
			if(difference.size() < bestMatchValue){
				best = e;
				bestMatchValue = difference.size();
			}

		}

		expressions.remove(best);
		return best;
	}
}