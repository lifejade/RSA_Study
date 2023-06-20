package mainP;
import java.util.ArrayList;
import java.util.LinkedList;

import guiP.RSAGUI;
import java.io.*;
import exceP.*;

public class Manager {
	/*대략적인 흐름은 이럼
	키만들기
	 암호화:
	->암호화(RSA)
	-->저장
	복호화:
	->불러오기
	-->복호화(RSA)
	RSA:
	c^x mod y;
	 
	암호화시에는 x=e
	복호화시에는 x=d 
	무조건 y=n
	 */
	//쓰래드 1개당 할당하는 문자열수 최대치
	public final int maxstrlen;
	//나눠진 문자열을 저장하기위한 임시저장소
	public static String[] retext_divided;
	
	public static Manager man;
	public static boolean wellco;
	public static boolean wellinco;
	File savefile;
	
	public static Manager getMan(){
		if(man==null){
			man=new Manager();
		}
		return man;
	}
	public void existfile()
	{
		savefile=new File("save");
		if(savefile.exists()==false)
			savefile.mkdir();
		System.out.println("폴더생성");
	}
	private Manager() {
	//테스트
	//	System.out.println("dddd   "+RSA(21,77,23));
	//	System.out.println("dddd   "+RSA(37,77,23));
	//	System.out.println("dddd   "+RSA(38,77,23));
	//	System.out.println("dddd   "+RSA(36,77,23));
	//	System.out.println("dddd   "+RSA(29,77,23));
	//	System.out.println("dddd   "+RSA(9,77,23));
		//매니저 인스턴스는 getMan으로밖에 못만드며 1개만 존재할수있음
		maxstrlen=5000;
		retext_divided=null;
		//아래 static 변수 2개는 코딩과 인코딩 도중 오류(치명적이지 않음)가 발생시 자동으로 창이 닫치는걸 방지
		wellco=true;
		wellinco=true;
	}
	//String을 정수로 바꿔줌, 이때 문자가 섞여있는경우 예외 발생
	private int strtoint(String s) throws Exception{
		char[] ss=s.toCharArray();
		if(ss.length>8)
			throw new NumException(NumException.st(1));
		int re=0;
		for(int x=0;x<ss.length;x++){
			if(ss[x]-'0'<0||ss[x]-'0'>10){
				throw new NumException(NumException.st(2));
			}
			
			re+=Math.pow(10,(double)(ss.length-x-1))*(int)(ss[x]-'0');
		}
		return re;
	}
	//key를 만들어주는 함수이며 전체적인 흐름과 예외처리 담당
	public int[] creatkey(String x,String y){
		int a,b;
		try{
		a=strtoint(x); b=strtoint(y);
		checkprim(a,b);
		return creatkey(a,b);
		}
		
		catch(Exception e){
			RSAGUI.BUGshow(e);
			return null;
		}
	}
	
	//받아드린 숫자가 소수인지 아닌지 판단 아닐시 예외 발생
	private void checkprim(int p1,int p2) throws Exception{
		if(isPri(p1)&&isPri(p2)){
			//System.out.println("이거들 소수 맞음ㅇㅇ");
		}
		else{
			//System.out.println("이거들 소수 아님ㅜㅜ");
			throw new PriExeption();
		}
	}
	//key를 만드는 본격적인 함수
	private int[] creatkey(int prim1,int prim2) throws Exception {
		int n; int pi; int e; int d;
		LinkedList<Integer> list=new LinkedList<Integer>();
		
		n=prim1*prim2;
		pi=(prim1-1)*(prim2-1);
		
		//이때 list의 길이가 0이면 버그가 생김 증명 필요
		for(int a=2;a<pi;a++){
		if(gdc(a,pi)==1){
			list.add(a);
			
		}
		}
		
		if(list.size()<1){
			throw new NumException(NumException.st(0));
		}
		
		e=list.get((int)(Math.random()*list.size()));
		list.clear();
		for(int a=2;a<pi;a++){
			if(ml(e,a,pi)==1){
				list.add(a);
				//System.out.println(a);
			}
		}
		d=list.get((int)(Math.random()*list.size()));
		//System.out.println("이거들 소수 맞음ㅇs3ddㅇ");
		//System.out.println(n+" "+pi+" "+e+" "+d);
		return new int[]{n,e,d};
	}
	
	//RSA알고리즘, 암호화와 복호화 둘다쓰인다.
	public int RSA(int a,int n,int x){
		if(x==1){
			return a%n;
		}
		return (a*RSA(a,n,x-1))%n;
	}
	//쓰래드를 이용하기 위해서 문장을 k등분함, k값은 글자수에 따라 달라짐
	//(maxstrlen)자(한국어 기준 약 100kb, 영문만이면 10kb)당 1쓰래드
	private String[] dividestr(String text,int maxthread){
		System.out.println("암호화전 갯수"+text.length());
		char[] text_c=text.toCharArray();
		String[] arrstr=new String[maxthread];
		int dividedlen = maxstrlen;
		String temp="";
		for(int a=0;a<maxthread;a++){
			for(int b=a*dividedlen;b<(a+1)*dividedlen;b++){
				
				if(b>=text.length())
					break;
//				System.out.println("ggee");
				temp=temp.concat(String.valueOf(text_c[b]));
			}
			arrstr[a]=temp;
			temp="";
		}
//		System.out.println(arrstr[0].length()+"dawer");
		return arrstr;
	}
	//위에것의 복호화버전
	private String[] dividestr(String text, int maxthread,int n){
		char[] text_c=text.toCharArray();
		int chunk=llen(n)*getlen(n);
		System.out.println("프린트후 갯수"+text.length()+"        "+chunk);
		String[] arrstr=new String[maxthread];
		int dividedlen = maxstrlen*chunk;
		System.out.println(dividedlen+"    "+maxthread);
		String temp="";
		for(int a=0;a<maxthread;a++){
			for(int b=a*dividedlen;b<(a+1)*dividedlen;b++){
				
				if(b>=text.length())
					break;
//				System.out.println("ggee");
				temp=temp.concat(String.valueOf(text_c[b]));
			}
			arrstr[a]=temp;
			temp="";
		}
		return arrstr;
	}
	private int howmanyThread_code(int text_length){
		return (int)(text_length/maxstrlen)+1;
	}
	private int howmanyThread_incode(int text_length,int n){
		int onecharperint=getlen(n)*llen(n);
		//Todo:제대로된 암호화를 거쳤다면 나머지가 나올수 없으나 나올경우 대비할것
		int temp=text_length/onecharperint;
		return (int)(temp/maxstrlen)+1;
	}
	//암호화의 전체적인 흐름과 얘외처리 담당
	public void startCode(String es,String ns,String text){
		try{
		int maxthread=howmanyThread_code(text.length());
		System.out.println("쓰래드 갯수는"+maxthread);
		retext_divided=new String[maxthread];
		int e=strtoint(es);
		int n=strtoint(ns);
		String[] text_arr=dividestr(text,maxthread);
		Code cd[]=new Code[maxthread];
		
		long time=System.currentTimeMillis();
		for(int a=0;a<maxthread;a++){
			//System.out.println("맡으신 문장"+text_arr[a]);
			cd[a]=new Code(e,n,text_arr[a],a);
		}
		for(int a=0;a<maxthread;a++){
			cd[a].start();
		}
		for(int a=0;a<maxthread;a++){
			cd[a].join();
		}
		System.out.println(System.currentTimeMillis()-time+"밀리 초 걸렸습니다");
		String result="";
		for(String a : retext_divided){
			result=result.concat(a);
		}
		RSAGUI.getmaingui().settextr(result);
		}
		catch(Exception E){
			wellco=false;
			System.out.println(E.getMessage());
			RSAGUI.BUGshow(E);
		}
	}
	//복호화의 전체적인 흐름과 예외처리 담당
	public void startinCode(String ds,String ns,String text){
		try{
			int d=strtoint(ds);
			int n=strtoint(ns);
			int maxthread=howmanyThread_incode(text.length(),n);
			System.out.println("쓰래드 갯수는"+maxthread);
			retext_divided=new String[maxthread];
			String[] text_arr=dividestr(text,maxthread,n);
			inCode icd[]=new inCode[maxthread];
			long time=System.currentTimeMillis();
			for(int a=0;a<maxthread;a++){
				//System.out.println("맡으신 문장"+text_arr[a]);
				icd[a]=new inCode(d,n,text_arr[a],a);
			}
			for(int a=0;a<maxthread;a++){
				icd[a].start();
			}
			for(int a=0;a<maxthread;a++){
				icd[a].join();
			}
			System.out.println(System.currentTimeMillis()-time+"밀리 초 걸렸습니다");
			String result="";
			for(String a : retext_divided){
				result=result.concat(a);
			}
			RSAGUI.getmaingui().settextr(result);
		}
		catch(Exception E){
			wellinco=false;
			RSAGUI.BUGshow(E);
		}
	}
	//IO
	public void savetext(String savetx,String title){
		try{
		if(title.length()==0)
			throw new NulltitleException();
		existfile();
		BufferedWriter out = new BufferedWriter(new FileWriter(savefile+
				File.separator+title+".txt"));
		out.write(savetx);
		out.close();
		}
		catch(IOException E){
			RSAGUI.BUGshow(E,"정보처리(저장) 오류입니다");
			return;
		}
		catch(Exception E){
			wellinco=false;
			RSAGUI.BUGshow(E);
		}
	}
	public String loadcode(String title)
	{
		long start_milisec=System.currentTimeMillis();
		String rd=null;
		try{
		existfile();
		//System.out.println(title);
		BufferedReader in=new BufferedReader(new FileReader(savefile+
				File.separator+title));
		String temp=null;
		rd=in.readLine();
		while(true){
			temp=in.readLine();
			if(temp==null){
				break;
			}
			rd+=("\n"+temp);
		}
		//System.out.println(rd);
		in.close();
		if(rd.length()==0){
			throw new LoadTextException();
		}
		}
		catch(IOException E){
			RSAGUI.BUGshow(E,"정보처리(불러오기) 오류입니다");
			return null;
		}
		catch(Exception E){
			wellinco=false;
			RSAGUI.BUGshow(E);
		}
		System.out.println(start_milisec-System.currentTimeMillis());
		return rd;
	}
	
	//아래 함수,클래스들은 보조용
	
	//두수의 반환 최대공약수
	private int gdc(int a, int b){
		if(b==0){
			return a;
		}
		return gdc(b,a%b);
	}
	//이 수가 소수인지 아닌지 판별
	private boolean isPri(int a){
		if(a==2){
			return true;
		}
		double x=Math.pow(a,0.5);
		for(int i=2;i<=x;i++){
			if(a%i==0){
				return false;
			}
		}
		return true;
	}
	//a*b%c를 구함-오버플로우 방지
	private int ml(int a,int b,int c){
		if((a%c)==0||(b%c)==0){
			return 0;
		}
		int re=a;
		for(int x=1;x<b;x++){
			re=(re+a)%c;
		}
		return re;
	}
	
	/*암호화 시키면 n미만,0이상의 정수만 나오므로
	char형의 길이인 2^16의 수를 표현하기 위해
	 몇자리의 수가 필요한지 구해주는 함수
	 */
	private int getlen(int n){
		//System.out.println(Character.BYTES);
		double x=Math.log10(Math.pow(2,Character.SIZE))/Math.log10(n);
		if(x==(int)x){
			return (int)x;
		}
		return (int)x+1;
	}
	//n이 몇자리 수인지 반환
	private int llen(int n){
		return (int)Math.log10(n)+1;
	}
	
	//GUI불러오기 부분의 ComboBox지원
	public String[] SupGui()
	{
		ArrayList<String> only=new ArrayList<String>();
		File savef=new File("save");
		if(savef.exists()==false)
		{
			savef.mkdir();
		}
		File[] list=savef.listFiles();
		for(int a=0;a<list.length;a++)
		{
			if(list[a].getName().contains(".txt"))
			{
				only.add(list[a].getName());
			}
		}
		String[] hi=new String[only.size()];
		for(int a=0; a<only.size();a++)
		{
			hi[a]=only.get(a);
		}
		
		return hi;
	}
	//암호화 담당
	class Code extends Thread {
		int e;int n;
		int[] tx;
		
		int len;int nel;
		String result;
		String text;
		int thisthreadnum;
		private Code(int e,int n,String text,int num) throws Exception{
			this.text=text;
			this.e=e;this.n=n;
			thisthreadnum=num;
			len=getlen(n);
			nel=llen(n);
			result="";
		}
		public void run(){
			try{
			//System.out.println(text);
			System.out.println("쓰래드"+thisthreadnum+"번  "+text.length());
			textToint(text);
			coding();
			retext_divided[thisthreadnum]=result;
			}
			catch(Exception E){
				wellinco=false;
				System.out.println(E.getMessage());
				RSAGUI.BUGshow(E);
			}
		}
		private void textToint(String text){
			tx=new int[text.length()];
			char[] ch=text.toCharArray();
			for(int a=0;a<text.length();a++){
				tx[a]=(int)ch[a]+(a+1);
			}
		}
		//n진법을 기반으로 암호화 시작
		private void coding(){
			LinkedList<Integer> re=new LinkedList<Integer>();
			//System.out.println("예상되는 길이는"+len+"입니다");
			for(int a=0;a<tx.length;a++){
				int[] kkk=new int[len];
				long x=Integer.toUnsignedLong(tx[a]);
				for(int b=0;b<len;b++){
					kkk[len-b-1]=(int)(x%n);
					x-=x%(n);
					x/=n;
				}
				if(x!=0){
					//System.out.println("어..이게 아닌데");
				}
				for(int b:kkk){
					//System.out.println(a+" ㅇㅇ  "+b);
					re.add(RSA(b,n,e));
					//System.out.println(RSA(b,n,e));
				}
			}
			for(int a=0;a<re.size();a++)
			{
				String st=new String("");
				int f=nel-((re.get(a).toString()).length());
				for(int dd=0;dd<f;dd++){
					st+="0";
					
				}
				
				st+=(String.valueOf(re.get(a)));
				//System.out.println("dfsa"+st);
				result=result.concat(st);
				
			}
		}
		
	}
	//복호화 담당
	class inCode extends Thread {
		int thisthreadnum;
		int d;int n;int len;int nel;
		String text;
		//re은 len진법으로 표기한 정수 
		//p는 임시적으로 추출한 정보 저장+그 문장을 복호화후 임시 저장하는 역활
		int re[];int[] p;
		String result;
		
		private inCode(int d,int n,String text,int num){
			this.d=d;this.n=n; this.text=text; thisthreadnum=num;
			len=getlen(n);result=new String("");
			nel=llen(n);
		}
		public void run(){
			try{
			toint();
			incoding();
			retext_divided[thisthreadnum]=result;
			}
			catch(Exception E){
				wellinco=false;
				RSAGUI.BUGshow(E);
			}
		}
		
		private void toint() throws Exception{
			char[] chtext=text.toCharArray();
			p=new int[chtext.length/nel];
			for(int e=0,w=0;e<chtext.length;e++)
			{
				if(chtext[e]<'0' || chtext[e]>'9')
					throw new LoadTextException();
				if((e+1)%nel==0)
				{
					long mul=0;
					for(int i=0;i<nel;i++)
					{
						mul=(mul*10)+(long)chtext[e+i-nel+1]-'0';
					}
					//System.out.println("gg  "+(int)mul);
					p[w]=(int)mul;
					w++;
				}
			}
			
		}

		private void incoding(){
			re=new int[p.length/len];
			for(int x=0;x<p.length;x++){
				p[x]=RSA(p[x],n,d);
			}
			for(int ee=0,ww=0;ee<p.length;ee++){
				if((ee+1)%len==0)
				{
					int mul=0;
					for(int i=0;i<len;i++)
					{
						mul=(mul*n)+p[ee+i-len+1];
					}
					//System.out.println("결론은 : "+(int)mul);
					re[ww]=mul;
					ww++;
				}
			}
			for(int x=0;x<re.length;x++){
				result+=(char)(re[x]-x-1);
			}
			//System.out.println(result);
		}
	}
	
}
