package it.unibs.ing.myutility;

public class CheckValues {
	
	public static boolean existsElement(Object obj, String message){
		if(obj!=null) {
			System.out.println(message);
			return true;
		}
		return false;
	}
	
	public static boolean isNegative (int value, String message){
		if( value<0) {
			System.out.println(message);
			return true;
		}
		return false;
	}
	
	public static boolean isOverLimit (int value, int limit, String message){
		if(value>limit) {
			System.out.println(message);
			return true;
		}
		return false;
	}
	
	public static boolean empty(String message, boolean value){
		if(value) {
			System.out.println(message);
		}
		
		return value;
	}
	
	public static boolean noElement(Object obj, String message){
		if(obj==null) {
			System.out.println(message);
			return true;
		}
		return false;
	}

}
