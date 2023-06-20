package guiP;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

import exceP.NullofFile;
import mainP.Manager;

import java.awt.event.*;
@SuppressWarnings("serial")
public class RSAGUI extends JFrame {
	JCheckBox clear_l;
	JCheckBox clear_r;
	static RSAGUI gui;
	static JTextArea text_l;
	static JTextArea text_r;
	private RSAGUI(String str) {
		super(str);
		setBounds(90,70,900,600);
		setLayout(new BorderLayout());
		
		JPanel funcpan1=new JPanel();		
		JPanel funcpan2=new JPanel();		
		JPanel f2_cbtn=new JPanel();
		
		JButton load_l=new JButton("↓불러오기↓");
		JButton btn_ctKey=new JButton("키 생성");
		JButton save_r=new JButton("↓저장↓");
		
		JLabel lab=new JLabel("무조건 ==> 방향으로 진행");
		lab.setFont(new Font("궁서",Font.PLAIN,25));
		JButton btn_convert=new JButton("CONVERT!");
		btn_convert.setPreferredSize(new Dimension(225,150));
		btn_convert.setFont(new Font("arial", Font.PLAIN,30));
		JButton btn_clear=new JButton("Clear!");
		btn_clear.setPreferredSize(new Dimension(225,150));
		btn_clear.setFont(new Font("arial", Font.PLAIN,45));
		
		Border clear_b=BorderFactory.createEtchedBorder();
		clear_l=new JCheckBox("왼쪽을 초기화",true);		
		clear_r=new JCheckBox("오른쪽을 초기화",true);
		clear_b=BorderFactory.createTitledBorder(clear_b,"어느쪽을 초기화?");
		JPanel clear_p=new JPanel();
		clear_p.setLayout(new GridLayout(0,1));
		clear_p.setBorder(clear_b);
		clear_p.add(clear_l);clear_p.add(clear_r);
		
		Border cvt_b=BorderFactory.createEtchedBorder();
		JRadioButton mode_c=new JRadioButton("암호화 모드",true);
		JRadioButton mode_inc=new JRadioButton("복호화 모드");
		cvt_b=BorderFactory.createTitledBorder(cvt_b, "모드는?");
		ButtonGroup grp=new ButtonGroup();
		grp.add(mode_c); grp.add(mode_inc);
		JPanel cvt_p=new JPanel(new GridLayout(0,1));
		cvt_p.setBorder(cvt_b);
		cvt_p.add(mode_c); cvt_p.add(mode_inc);
		
		text_l=new JTextArea(25,10);
		text_l.setColumns(text_l.getText().length());
		text_l.setWrapStyleWord(true);
		text_l.setLineWrap(true);
		JScrollPane scrl=new JScrollPane(text_l,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		text_r=new JTextArea(25,10);
		text_r.setColumns(text_r.getText().length());
		text_r.setWrapStyleWord(true);
		text_r.setLineWrap(true);
		JScrollPane scrr=new JScrollPane(text_r,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		text_l.setText("내용을 기입해 주세요L");
		text_r.setText("내용을 기입해 주세요R");
		
		
		funcpan1.setLayout(new GridLayout(1,3,10,10));
		funcpan1.setBorder(new EmptyBorder(4,4,1,4));
		funcpan2.setLayout(new GridLayout(1,3,2,2));
		f2_cbtn.setLayout(new FlowLayout());
		load_l.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				try{
				if(Manager.getMan().SupGui().length==0){
					throw new NullofFile();
				}
				else{
					new Load("저장된 정보를 불러옵니다",text_l);
				}
				}
				catch(NullofFile ed){
					BUGshow(ed);
					System.out.println("dd");
				}
				catch(Exception ed){
					BUGshow(ed);
				}
			}
		});
		save_r.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				new Save("정보를 저장합니다",text_r);
			}
		});
		btn_ctKey.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				new CtKey("키를 만듭니다");
			}
		});
		
		btn_clear.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				new Clear("주의!!");
			}
		});
		btn_convert.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(mode_c.isSelected()){
					new Con("암호화를 시작합니다",true);
				}
				else{
					new Con("복호화를 시작합니다",false);
				}
			}
		});
		
		
		funcpan1.add(load_l); funcpan1.add(btn_ctKey); funcpan1.add(save_r);  
		f2_cbtn.add(lab); f2_cbtn.add(cvt_p); f2_cbtn.add(btn_convert); f2_cbtn.add(clear_p); f2_cbtn.add(btn_clear);
		funcpan2.add(scrl); funcpan2.add(f2_cbtn); funcpan2.add(scrr);
		
		
		add(funcpan1,BorderLayout.NORTH);
		add(funcpan2,BorderLayout.CENTER);
		
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public static RSAGUI getmaingui(){
		if(gui==null){
			gui=new RSAGUI("Eni-RSA_made by SM");
		}
		return gui;
	}
	public static void BUGshow(Exception e){
		JFrame bb= new JFrame("오류!!!!!");
		bb.setBounds(300, 300, 300, 100);
		bb.setLayout(new FlowLayout());
		bb.add(new JLabel("원인 / 해결책",JLabel.CENTER));
		String str=e.getMessage();
		int si=0;
		
		//오류의 보고가 뭔지도 모를 상황일경우를 따로 분류해둠
		if(str==null){
			si=1;
			str="헤헷 뭐가 원인이고 해결책인지 저도 몰라영";
		}
		bb.add(new JLabel(str));
		
		bb.setVisible(true);
		if(si==0){
			bb.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		}
		else{
			bb.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		}
	}
	public static void BUGshow(Exception e,String str){
		JFrame bb= new JFrame("오류!!!!!");
		bb.setBounds(300, 300, 300, 100);
		bb.setLayout(new FlowLayout());
		bb.add(new JLabel("원인 / 해결책",JLabel.CENTER));
		bb.add(new JLabel(str));
		bb.setVisible(true);
		bb.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public void settextr(String str){
		text_r.setText(str);
	}

	private class Clear extends JFrame{
		public Clear(String title){
			super(title);
			
			setBounds(100,200,300,100);
			setLayout(new BorderLayout());
			
			JLabel lab=new JLabel("정말로 초기화 할거얌?",JLabel.CENTER);
			JPanel pan=new JPanel();
			pan.setLayout(new GridLayout(1,2,2,2));
			JButton btn1=new JButton("Yes! Yes! Yes!");
			JButton btn2=new JButton("아뇨");
			
			
			btn1.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					if(clear_l.isSelected()){
						text_l.setText("");
					}
					if(clear_r.isSelected()){
						text_r.setText("");
					}
					dispose();
				}
			});
			btn2.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					dispose();
				}
			});
			
			pan.add(btn1);pan.add(btn2);
			
			add(lab,BorderLayout.NORTH);add(pan,BorderLayout.CENTER);
			setVisible(true);
			this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		}
	}
	private class Con extends JFrame{
		public Con(String title,boolean mode){
			super(title);
			setBounds(333,360,300,200);
			setLayout(new GridLayout(2,1,1,1));
			
			JPanel kpan=new JPanel();
			kpan.setLayout(new BorderLayout());
			JPanel kpan_=new JPanel();
			kpan_.setLayout(new GridLayout(1,2,1,1));
			JPanel bpan=new JPanel();
			bpan.setLayout(new FlowLayout());
			
			JLabel kwLabel=new JLabel("키워드 (e/d,n)",SwingConstants.CENTER);
			JTextField e_d = new JTextField(10);
			JTextField n = new JTextField(10);
			JButton btn1 = new JButton("확인");
			btn1.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					if(mode){
					mainP.Manager.getMan().startCode(e_d.getText(), n.getText(), text_l.getText());
					}
					else{
						mainP.Manager.getMan().startinCode(e_d.getText(), n.getText(), text_l.getText());
					}
					dispose();
				}
			});
			JButton btn2 = new JButton("취소");
			btn2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
			
			kpan_.add(e_d);kpan_.add(n);
			kpan.add(kwLabel,BorderLayout.NORTH);kpan.add(kpan_,BorderLayout.CENTER);
			bpan.add(btn1);bpan.add(btn2);
			add(kpan);add(bpan);
			setVisible(true);
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		}
	}
}
