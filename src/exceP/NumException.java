package exceP;
@SuppressWarnings("serial")
public class NumException extends Exception {
	
	public NumException(String str) {
		super(str);
		
		
	}
	public static String st(int a){
		if(a==0){
			return "�Է°��� �ʹ� �۽��ϴ�,�ٸ����� �غ�����";
		}
		else if(a==1){
			return "�Է°��� �ʹ� Ů�ϴ�,�ٸ����� �غ�����";
		}
		else{
			return "�Է°��� �̻��մϴ�,���ڸ� �Է��� �ּ���";
		}
	}
}
