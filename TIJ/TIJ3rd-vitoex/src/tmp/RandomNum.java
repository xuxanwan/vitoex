package tmp;

import java.util.Random;

public class RandomNum {

	/**
	 * @param args
	 */
	private static char[] removeChar(char []array,char ch){
		char []temp=new char[array.length-1];
		int charIndex = 0;
		for(int i=0;i<array.length;i++){
			if(array[i]==ch){
				charIndex=i;
			}
		}
		for(int i=0;i<charIndex;i++){
			temp[i]=array[i];
		}
		for(int i=charIndex;i<array.length-1;i++){
			temp[i]=array[i+1];
		}
			
		//array=temp;
		
		return temp;
	}
	
	/**
	 * Use String to perform the action
	 */
	private static char[] removeChar1(char []array,char ch){
		String str=String.copyValueOf(array);
		int charIndex = str.indexOf(ch);
		String str1=str.substring(0, charIndex);
		String str2=str.substring(charIndex+1);
		str=str1+str2;
		
		array=str.toCharArray();
		return array;
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Random rand = new Random();
		int number = rand.nextInt(10);
		int nums[] = new int[10];
		for(int i=0;i<10;i++){
			nums[i]=rand.nextInt(10);
			//System.out.print(nums[i] + ", ");
		}
		
		char alphabet[] = {'A','a','B','b','C','c','D','d','E','e','F','f','G','g',
				'H','h','I','i','J','j','K','k','L','l','M','m','N','n',
				'O','o','P','p','Q','q','R','r','S','s','T','t',
				'U','u','V','v','W','w','X','x','Y','y','Z','z'};
		char numbers[] = {'0','1','2','3','4','5','6','7','8','9'};
		int mapLength = alphabet.length + numbers.length;
		char codeMapTemp[] = new char[mapLength];
		
		for(int i=0;i<alphabet.length;i++){
			codeMapTemp[i] = alphabet[i];
		}
		int j=0;
		for(int i=alphabet.length;i<mapLength;i++){
//			for(int j=0;j<numbers.length;j++){
//				codeMapTemp[i] = numbers[j];
//			}		
			
			codeMapTemp[i]=numbers[j++];
		}
		
		char codeMap[]=new char[mapLength];
		char currentChar=' ';
//		int mapTempLength = mapLength;
		for(int i=0;i<mapLength;i++){
			currentChar=codeMapTemp[rand.nextInt(codeMapTemp.length)];
			codeMap[i]=currentChar;
			if(codeMapTemp.length != 1){
				codeMapTemp=removeChar(codeMapTemp,currentChar);
			}			
			
		}
		
		int codeLength = 16;
		char code[] = new char[codeLength];
		
		for(int i=0;i<codeLength;i++){
			code[i]=codeMap[rand.nextInt(mapLength)];
		}
		
		for(int i=0;i<codeLength;i++){			
			System.out.print(code[i] + " ");
		}
		System.out.println();
		
		String str = String.copyValueOf(code);				
		System.out.println(str);
	}

}
