package guiP;
import java.awt.*;
import javax.swing.*;

import mainP.Manager;

import java.awt.event.*;

@SuppressWarnings("serial")
public class Save extends JFrame {
	protected Save(String str,JTextArea ori) {
		super(str);
		String resl=ori.getText();
		JTextField titleText;
		
		setBounds(333,360,300,200);
		setLayout(new GridLayout(3,1,3,3));
		
		JPanel pan1=new JPanel();
		pan1.setLayout(new BorderLayout());

		JPanel pan3=new JPanel();
		pan3.setLayout(new FlowLayout());
		
		JLabel titleLabel=new JLabel("저장하실 제목",SwingConstants.CENTER);
		titleText=new JTextField(10);
		
		JButton btn=new JButton("확인");
		btn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent E)
			{
				Manager.getMan().savetext(resl,titleText.getText());
				if(Manager.wellco){
					dispose();
				}
				Manager.wellco=true;
			}
		});
		
		JButton btn2=new JButton("취소");
		btn2.addActionListener(
		new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
						dispose();
				}
			}
		);
		
		pan1.add(titleLabel,BorderLayout.NORTH); pan1.add(titleText,BorderLayout.CENTER);
		
		pan3.add(btn); pan3.add(btn2);
		add(pan1); add(pan3);
		setVisible(true);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	}
}
