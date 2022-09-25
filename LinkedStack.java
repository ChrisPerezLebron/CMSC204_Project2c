/**
 * Ignore this class. It was my initial implementation of the stack 
 * until I realized it had to be an array based implementation. It 
 * was then used for console based testing along the way
 * @author Christopher Perez Lebron
 *
 */
public class LinkedStack {
	public static void main(String[] args) {
		//String str = Character.toString('a');
		
		String str = "123";
		String str2 = str.substring(2, 3);
		String str3 = Character.toString(str.charAt(0));
		System.out.println(str2);
		System.out.println(str);
		String str4 = "" +'a';
		
		if(Character.isDigit(str2.charAt(0))) {
			System.out.println("true");
		}
		
		System.out.println(str4);
		
		System.out.println(str.charAt(str.length() - 1));
	}
}