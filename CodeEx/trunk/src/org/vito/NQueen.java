package org.vito;

import java.util.ArrayList;
import java.util.List;

/**
 * N皇后问题的一个递归实现.
 * @author vito
 *
 */
public class NQueen {
	
	public static final int QUEEN_NUM = 4;
	
	public boolean conflict(List<Integer> state, int nextX){
		int nextY = state.size();
		for(int i = 0; i < state.size(); i++){
			int colDistance = Math.abs(state.get(i).intValue() - nextX);
			if( colDistance == 0 || colDistance == (nextY - i)){
				return true;
			}
		}
		return false;
	}
	
	public List<String> queens(List<Integer> state){
		List<String> solutions = new ArrayList<String>();
		
		for(int i = 0; i < QUEEN_NUM; i++){
			if(!conflict(state, i)){
				if(state.size() == QUEEN_NUM - 1){
					solutions.add(String.valueOf(i));
				}else{
					state.add(i);
					List<String> temp = queens(state);
					for(int j = 0; j < temp.size(); j++){
						solutions.add(String.valueOf(i) + temp.get(j));
					}
				}
			}
		}		
		
		return solutions;
	}

	public static void main(String[] args) {
		NQueen q4 = new NQueen();
		List<String> solutions = q4.queens(new ArrayList());
		for(int i = 0; i < solutions.size(); i++){
			System.out.println(solutions.get(i));
		}
	}

}
