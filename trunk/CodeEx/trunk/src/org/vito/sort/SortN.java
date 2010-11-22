package org.vito.sort;

/*
 * 网上流传的一道华为的面试题.
 * 有N个大小不等的自然数（1--N），请将它们由小到大排序。 (比如对[5,2,4,3,...],进行排序) 
 * 要求程序算法:时间复杂度为O(n)，空间复杂度为O(1)。
 */

public class SortN {
	/*
	 * 网贴算法如下.
	 */
	int count = 0;  //辅助变量，不是算法组成部分
	
	//可以证明这个算法每次交换必然将一个数字正确安排到位，而且最多只有N次交换。 
	//具体体现在count的值上，所以虽然是二重循环仍然是时间复杂度O(n)
	void sort(int arr[],int n){
		int temp;  //临时变量,空间复杂度 O(1)
		for(int i = 1; i <= n; i++){
			while(arr[i] != i){
				temp = arr[arr[i]];
				arr[arr[i]] = arr[i];
				arr[i] = temp;
				++count;
			}
		}
	}
	//===
	
	//改成下标索引从0开始
	void sortIndexing0(int arr[], int n){
		int temp;
		for(int i = 0; i < n; i++){
			while(arr[i] - 1 != i){
				temp = arr[arr[i] - 1];
				arr[arr[i] - 1] = arr[i];  //!
				arr[i] = temp;
				
				count = count + 1;
			}
		}
	}
	
	//==
	public static void main(String args[]){
		int[] array = 
			//{5,3,2,1,6,4};
			{1,2,3,4,5,6};
		
		SortN sort = new SortN();
		sort.sortIndexing0(array, array.length);
		for(int i = 0; i < array.length; i++){
			System.out.print(array[i] + " ");
		}
		System.out.println("Exchange counter: " + sort.count);
	}
	//===
	
}
