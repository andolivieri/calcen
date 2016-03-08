package it.andolivieri.calcoloenigmatico.solvers;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import it.andolivieri.calcoloenigmatico.model.Problem;
import it.andolivieri.calcoloenigmatico.solvers.generators.KPermutationGenerator;

public class BFSolver extends Solver {

	protected Map<Character, Integer> findSolution(Problem p){

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
			solution = new HashMap<Character, Integer>();
			for(int i=0; i<p.numeroSimboli(); i++){
				solution.put(Character.valueOf(characters.charAt(i)), perm.get(i).intValue());
			}
			if(p.verifySolution(solution)){
				return solution;
			}

		}

		return null;

	}




}
