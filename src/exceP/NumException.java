package exceP;
@SuppressWarnings("serial")
public class NumException extends Exception {
	
	public NumException(String str) {
		super(str);
		
		
	}
	public static String st(int a){
		if(a==0){
			return "입력값이 너무 작습니다,다른수로 해보세요";
		}
		else if(a==1){
			return "입력값이 너무 큽니다,다른수로 해보세요";
		}
		else{
			return "입력값이 이상합니다,숫자만 입력해 주세요";
		}
	}
}
