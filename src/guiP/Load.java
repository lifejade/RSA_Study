package guiP;
import java.awt.*;
import javax.swing.*;

import mainP.Manager;

import java.awt.event.*;
import java.util.Vector;

@SuppressWarnings("serial")
class Load extends JFrame{
	String axaxs;
	protected Load(String stttt,JTextArea ori) {
		super(stttt);
		setBounds(200, 200, 350, 250);
		setLayout(new BorderLayout());
		
		JPanel pan0=new JPanel();
		pan0.setLayout(new GridLayout(0,1));
		
		JPanel pan1=new JPanel();
		pan1.setLayout(new GridLayout(1,2));
		
		
		JPanel pan2=new JPanel();
		pan2.setLayout(new FlowLayout());
		
		
		
		Vector<String> strv = new Vector<String>();
		
		String[] str = Manager.getMan().SupGui();
		for (int aa = 0; aa < str.length; aa++) {
			strv.add(str[aa]);
		}
		
		
		JLabel label1 = new JLabel("저장된 파일들", SwingConstants.CENTER);
		@SuppressWarnings({ "unchecked", "rawtypes" })
		JComboBox cmb1 = new JComboBox(strv);
		cmb1.setMaximumRowCount(5);
		axaxs=strv.firstElement();
		cmb1.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					axaxs = (String) e.getItem();
				} else {
					axaxs = (String) e.getItem();
				}
				
			}
		});

		JButton btn1 = new JButton("확인");
		btn1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				ori.setText(mainP.Manager.getMan().loadcode(axaxs));
				if(Manager.wellinco){
					dispose();
				}
				Manager.wellinco=true;
			}
		});
		JButton btn2 = new JButton("취소");
		btn2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		
		pan1.add(label1);
		pan1.add(cmb1);
		
		
		pan2.add(btn1);pan2.add(btn2);
		
		
		
		add(pan1,BorderLayout.CENTER);add(pan2,BorderLayout.SOUTH);
		setVisible(true);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	}
}
