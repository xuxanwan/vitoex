package tmp;

class Exception1 extends Exception{}

public class Test {
	void test() throws Exception1{		
		System.out.println(5/0);
		
		Exception1 e1 = new Exception1();
		e1.initCause(new NullPointerException());
		throw e1;
		
	}
	
	public static void main(String args[]){
		int ee = 1<<0 /* 1 */, es = 1 << 1 /* 2 */;
		System.out.println(es);
		Integer i = new Integer(-1);
		Long l = new Long(-1);
		String str = null, str1 = null;
		//str = Long.toBinaryString(l);
		str = Integer.toBinaryString(1504937931);
		str1 = Integer.toBinaryString(-1504937931);
		System.out.println(str);
		System.out.println(str1);
		
		try{
			new Test().test();	
		}catch(Exception1 e){
			throw new RuntimeException(e);
		}
		
		
		
		double expo = 2147483647+1;  // 2^31-1
		double to = Math.sqrt(expo);
		System.out.println(to);
		
		System.out.println(" "+Math.round(0.3));
		System.out.println(" "+Math.round(0.4));
		System.out.println(" "+Math.round(0.5));
		System.out.println(" "+Math.round(0.6));
		
		System.out.println(2*((Math.pow(2, 10)-1) + (Math.pow(2,10)-1))*Math.pow(2, 52));
	}
}
