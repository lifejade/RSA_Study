package guiP;
import java.awt.*;
import javax.swing.*;

import mainP.Manager;

import java.awt.event.*;

@SuppressWarnings("serial")
class CtKey extends JFrame{

	protected CtKey(String str) {
		super(str);
		
		setBounds(100,200,330,250);
		setLayout(new BorderLayout());
		addWindowListener(new WindowAdapter(){
			public void windowClosed(WindowEvent e){
				RSAGUI.getmaingui().setVisible(true);
			}
		});
		
		JLabel lab1=new JLabel("소수 2개를 넣어주세요",JLabel.CENTER);
		JLabel lab2=new JLabel("결과(e/d,n) : ↓",JLabel.CENTER);
		JLabel lab3=new JLabel("공개키(e,n) : ");
		JLabel lab4=new JLabel("개인키(d,n) : ");
		
		//소수 2개를 받습니다
		JTextField pri1=new JTextField(8);
		JTextField pri2=new JTextField(8);
		JButton btn1=new JButton("확인");
		
		
		JLabel e_n=new JLabel();
		JLabel d_n=new JLabel();
		
		btn1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				btn1.setEnabled(false);
				int[] x=Manager.getMan().creatkey(pri1.getText(),pri2.getText());
				
				if(x==null){
					btn1.setEnabled(true);
					return;
				}
				e_n.setText(x[1]+" : "+x[0]);
				d_n.setText(x[2]+" : "+x[0]);
				btn1.setEnabled(true);
				}
		});
		
		JPanel pan1=new JPanel(new GridLayout(3,1,3,3));
		JPanel pan1_=new JPanel(new FlowLayout());
		pan1.add(lab1); pan1_.add(pri1);pan1_.add(pri2);;
		pan1.add(pan1_);pan1.add(btn1);
		add(pan1,BorderLayout.NORTH);
		
		JPanel pan2=new JPanel(new BorderLayout());
		JPanel pan2_=new JPanel(new GridLayout(2,2,3,3));
		pan2.add(lab2,BorderLayout.NORTH);
		pan2_.add(lab3); pan2_.add(e_n);; pan2_.add(lab4); pan2_.add(d_n);
		pan2.add(pan2_,BorderLayout.CENTER);
		add(pan2,BorderLayout.CENTER);
		
		setVisible(true);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	}

}
