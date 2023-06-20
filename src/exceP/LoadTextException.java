package exceP;

@SuppressWarnings("serial")
public class LoadTextException extends Exception {

	public LoadTextException() {
		super("파일 불러온게 이상해;; 파일 안에는 숫자만 있어야 합니다");
	}

}
