package org.vito;

import java.util.LinkedList;
import java.util.List;

import org.vito.dummy.PoppedWhenEmpty;

/**
 * From Clean Code listing 10-4,
 * mock of a stack manipulation on interger.
 * 
 * @author vito
 *
 */
public class Stack {
	private int topOfStack = 0; //also represents the size of the stack
	List<Integer> elements = new LinkedList<Integer>();
	
	public int size(){
		return topOfStack;
	}
	
	public void push(int element){
		topOfStack++;
		elements.add(element);
	}
	
	public int pop() throws PoppedWhenEmpty{
		if(topOfStack == 0){
			throw new PoppedWhenEmpty();
		}
		
		int element = elements.get(--topOfStack);
		elements.remove(topOfStack);
		return element;
	}
}
