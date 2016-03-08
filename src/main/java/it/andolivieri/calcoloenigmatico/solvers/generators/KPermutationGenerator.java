package it.andolivieri.calcoloenigmatico.solvers.generators;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class KPermutationGenerator<T> {

	private List<T> elements;
	public int total, count;
	private int N, K;
	private int[] comb;
	private Set<T> currentSubset;
	private JSTPermutationGenerator<T> gen;
	
	public static void main(String[] args){
		
		Set<Integer> s = new HashSet<Integer>();
		s.add(Integer.valueOf(0));
		s.add(Integer.valueOf(1));
		s.add(Integer.valueOf(2));
		s.add(Integer.valueOf(3));
		s.add(Integer.valueOf(4));
		
		KPermutationGenerator<Integer> g = new KPermutationGenerator<Integer>(s, 3);
		
		while(g.hasNext())
			System.out.println(g.next());
		System.out.println(g.count);
	}
	
	public KPermutationGenerator(Set<T> elems, int k){
		
		N = elems.size();
		K = k;
		elements = new ArrayList<T>(N);
		elements.addAll(elems);
		
		comb = new int[K];
		for(int i=0;i<k; i++)
			comb[i] = i;
		
		initGen();
		count = 0;
		total = (fact(N) / (fact(K) * fact(N-K))) * fact(K);
		
	}
	
	private void initGen(){
		currentSubset = new HashSet<T>();
		for(int i=0; i<K;i++)
			currentSubset.add(elements.get(comb[i]));
		gen = new JSTPermutationGenerator<T>(currentSubset);
	}
	
	public boolean hasNext(){
		return count < total;
	}
	
	public List<T> next(){
		
		List<T> next = null;
		
		if(gen.hasNext()){
			count ++;
			return gen.next();
		}else{
			if(!nextCombination()){
				return null;
			}else{
				initGen();
				return next();
			}
		}
	}
	
	private boolean nextCombination(){
		
		if(comb[K-1] < N - 1){
			comb[K-1] += 1;
			return true;
		}else{
			int j;
			for(j=K-2;j>=0;j--){
				if(comb[j] < N - K + j){
					break;
				}
			}
			if(j<0){
				return false;
			}else{
				comb[j] += 1;
			}
			
			while(j < K - 1){
				comb[j+1] = comb[j] + 1;
				j++;
			}
			
			return true;
			
		}
		
	}
	
	public static int fact(int n) {
		return n==0 ? 1 : n*fact(n-1);
	}
	
	
	
}
