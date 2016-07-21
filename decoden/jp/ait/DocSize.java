package jp.ait;


import java.awt.*;
import java.awt.event.*;

import javax.swing.*;


public class DocSize  extends JDialog implements ActionListener, WindowListener {
	private int mSize;
	private int mMuki;
	private JRadioButton radio1, radio2, radio3, radio4, radio5;
	private JButton ok_btn;
	
	DocSize(Frame frame) {
		super(frame,  "用紙の向きを選択", true);			// false でリサイズを無効にしている
//		super("向きと余白の選択");			// false でリサイズを無効にしている
		setBackground(Color.white);
		getContentPane().setBackground(new Color(255, 160, 161));
		
		ImagePanel panel = new ImagePanel("img/select_doc.gif");
		panel.setPreferredSize(new Dimension(700, 476)); 	// これが無いと表示しない
		panel.setBackground(new Color(255, 160, 161));
		add(panel);
				
		panel.setLayout(null);
/*
		radio1 = new JRadioButton("標準サイズ", true);
		radio1.setBounds(140, 160, 120, 18);
		radio1.setBackground(Color.white);
		radio1.setBorder(BorderFactory.createLineBorder(Color.white, 1));
		panel.add(radio1);
		radio1.addActionListener(this);	
		
		radio2 = new JRadioButton("余白3mm");
		radio2.setBounds(260, 160, 120, 18);
		radio2.setBackground(Color.white);
		panel.add(radio2);
		radio2.addActionListener(this);	
		
		radio3 = new JRadioButton("四辺フチなし");
		radio3.setBounds(380, 160, 120, 18);
		radio3.setBackground(Color.white);
		panel.add(radio3);
		radio3.addActionListener(this);	

		ButtonGroup group = new ButtonGroup();
		group.add(radio1);
		group.add(radio2);
		group.add(radio3);
*/
		radio4 = new JRadioButton("たて", true);
		radio4.setBounds(324, 384, 120, 18);
		radio4.setBackground(Color.white);
		radio4.setBorder(BorderFactory.createLineBorder(Color.white, 1));
		panel.add(radio4);
		radio4.addActionListener(this);	
		
		radio5 = new JRadioButton("よこ");
		radio5.setBounds(470, 384, 120, 18);
		radio5.setBackground(Color.white);
		radio5.setBorder(BorderFactory.createLineBorder(Color.white, 1));
		panel.add(radio5);
		radio5.addActionListener(this);	
		
		ButtonGroup group = new ButtonGroup();
		group.add(radio4);
		group.add(radio5);
		
//		pack();
//		setLocationRelativeTo(null);
		
		ok_btn = new JButton(Utility.ImageIcon(this, "img/ok.gif"));
		ok_btn.setBounds(268, 422, 170, 34);
		ok_btn.setRolloverIcon(Utility.ImageIcon(this, "img/ok_rollover.gif"));
		ok_btn.setPressedIcon(Utility.ImageIcon(this, "img/ok_press.gif"));
		panel.add(ok_btn);
		ok_btn.addActionListener(this);	
		
		int width = 704;
		int height = 492;
		
		setSize(width, height);
		
		// スクリーンの中央に配置
		Rectangle screen = getGraphicsConfiguration().getBounds();
		setLocation(screen.x + screen.width/2  - height/2, 
				screen.y + screen.height/2 - height/2);
		
		/**
		 * Frameについている終了ボタン処理はデフォルトの処理に任せないで自分で調整する
		 */
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(this);
		
		setResizable(false);	
	}	
	
	public int getDocSize() {
		return mSize;
	}
	public int getDocMuki() {
		return mMuki;
	}
	
	// 2007/12/31 ドキュメントの向きを設定
	public void setDocMuki(int muki) {
		mMuki = muki;
		if (mMuki == 0) {
			radio4.setSelected(true);
			radio5.setSelected(false);
		} else {
			radio4.setSelected(false);
			radio5.setSelected(true);
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == radio1) {
			mSize = 0;
			return;
		}
		if (e.getSource() == radio2) {
			mSize = 1;
			return;
		}
		if (e.getSource() == radio3) {
			mSize = 2;
			return;
		}
		if (e.getSource() == radio4) {
			mMuki = 0;
			return;
		}
		if (e.getSource() == radio5) {
			mMuki = 1;
			return;
		}
		if (e.getSource() == ok_btn) {
// 余白は１種類だけになった
			mSize = 1;

			if (edenpo.Graphics_Panel != null) {
				int ret = edenpo.Graphics_Panel.setDocType(mSize, mMuki);
			}
			
			setVisible(false);	
			return;
		}
	}
	
	public void windowActivated(WindowEvent e) {}
	public void windowClosed(WindowEvent e) {
	}
	public void windowClosing(WindowEvent e) {
		if (! edenpo.Boot) {
			int value = JOptionPane.showConfirmDialog(this, 
				"このアプリケーションを終了してもよろしいですか？", "終了確認", JOptionPane.YES_NO_OPTION);
			if (value == JOptionPane.YES_OPTION) {
				System.exit(0);
			}	
		} else {
			setVisible(false);	
		}
	}
	public void windowDeactivated(WindowEvent e) {}
	public void windowDeiconified(WindowEvent e) {}
	public void windowIconified(WindowEvent e) {}
	public void windowOpened(WindowEvent e) {}	
	
	
}