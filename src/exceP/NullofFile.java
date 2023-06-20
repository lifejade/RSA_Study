package exceP;

@SuppressWarnings("serial")
public class NullofFile extends Exception {

	public NullofFile() {
		super("저장된 파일이 없습니다. 일단 저장 먼저 하세요");
		System.out.println("dd");
	}

}
