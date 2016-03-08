package it.andolivieri.calcoloenigmatico.solvers.generators;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;


public class JSTPermutationGenerator<T>{

	int[] p, pi, dir;
	int currentMobileIxd;
	int count, total, N;
	boolean bounce = false;
	List<T> elements;

	public static void main(String[] args){

		int n = 4;
		
		Set<Integer> s = new HashSet<Integer>();
		s.add(Integer.valueOf(0));
		s.add(Integer.valueOf(1));
		s.add(Integer.valueOf(2));
		s.add(Integer.valueOf(3));
		s.add(Integer.valueOf(4));
		
		JSTPermutationGenerator<Integer> g = new JSTPermutationGenerator<Integer>(s);

		
		while(g.hasNext())
			System.out.println(g.next().toString());
		System.out.println(g.count);
		assert g.count == factorial(n);
	}


	public JSTPermutationGenerator(Set<T> elems){

		N = elems.size();
		p   = new int[N];
		pi   = new int[N];
		dir   = new int[N];
		elements = new ArrayList<T>(elems.size());
		elements.addAll(elems);
		
		for (int i = 0; i < N; i++) {
			dir[i] = -1;
			p[i]  = i;
			pi[i] = i;
		}
		currentMobileIxd = N - 1;
		count = 0;
		total = factorial(N);
	}

	public List<T> next(){

		if(count > 0){

			int gmi = findGreatestMobileIdx();
			
			if(gmi < 0)
				return null;
			
			if(bounce){
				for(int j=0; j<=N-1;j++){
					if(p[j] > p[gmi])
						dir[j] *= -1;
				}
			}
				
			// swap
			int next = gmi + dir[gmi];
			swap(p, gmi, gmi + dir[gmi]);
			swap(dir, gmi, gmi + dir[gmi]);
			// bounce
			if(!isMobile(next)){
				bounce = true;
			}else{
				bounce = false;
			}
			
		}
		
		count++;
		//debug();
		return permute();

	}
	
	private List<T> permute(){
		List<T> r = new ArrayList<T>(N);
		for(int i=0; i<p.length;i++){
			r.add(i,elements.get(p[i]));
		}
		return r;
	}

	private boolean isMobile(int idx){
		if(idx + dir[idx] < 0 || idx + dir[idx] >= N)
			return false;
		return p[idx] > p[idx + dir[idx]]; 
	}

	private static void swap(int[] x, int a, int b){
		int z = x[a];
		x[a] = x[b];
		x[b] = z;
	}

	private int findGreatestMobileIdx() {
		int maxValue = Integer.MIN_VALUE;
		int maxIdx = -1;
		for(int i=0; i<N; i++){
			if(i + dir[i] > -1 && i + dir[i] < N)
				if(p[i] > maxValue && p[i] > p[i + dir[i]]){
					maxValue = p[i];
					maxIdx = i;
				}
		}
		return maxIdx;
	}

	public boolean hasNext(){
		return count < total;
	}

	public static int factorial(int n) {
		return n==0 ? 1 : n*factorial(n-1);
	}
	
	private void debug(){
		System.out.print(count + ": ");
		System.out.print("[");
		for(int i=0; i<N; i++)
			System.out.print((dir[i] < 0 ? "<": " " )+ p[i] + (dir[i] > 0 ? ">" : " "));
		System.out.println("]");
	}





}