package jp.ait;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class ProgressDialog  extends JDialog implements WindowListener {
	// ダイアログの初期サイズ
	final static int FRAME_WIDTH = 400;
	final static int FRAME_HEIGHT = 62;
	
	private JProgressBar  mPBar;
	
	ProgressDialog() {
		super((Frame)null, "デコレーションカードを起動しています");
		setBackground(edenpo.BACK_COLOR2);
		
		mPBar = new JProgressBar(0 , 100);
		mPBar.setBackground(edenpo.BACK_COLOR2);
		mPBar.setForeground(edenpo.BACK_COLOR);
		
		mPBar.setPreferredSize(new Dimension(300, 20));
		mPBar.setStringPainted(true);

		getContentPane().setLayout(new FlowLayout());
		getContentPane().add(mPBar);

		setSize(FRAME_WIDTH, FRAME_HEIGHT);
	
		// スクリーンの中央に配置
		Rectangle screen = getGraphicsConfiguration().getBounds();
		setLocation(screen.x + screen.width/2  - FRAME_WIDTH/2, 
				screen.y + screen.height/2 - FRAME_HEIGHT/2);

		addWindowListener(this);

		setVisible(true);
	}
	
	public void setPos(int value) {
		mPBar.setValue(value);
	}
	
	
	public void windowActivated(WindowEvent e) {}
	public void windowClosed(WindowEvent e) {
		System.exit(0);
	}
	public void windowClosing(WindowEvent e) {
		System.exit(0);
	}
	public void windowDeactivated(WindowEvent e) {}
	public void windowDeiconified(WindowEvent e) {}
	public void windowIconified(WindowEvent e) {}
	public void windowOpened(WindowEvent e) {}	
	

}
