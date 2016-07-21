package jp.ait;


import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

import javax.swing.*;
import javax.swing.border.*;

class UserSozai extends JDialog implements ActionListener, ComponentListener  {
	
	// ダイアログの初期サイズ
	final static int FRAME_WIDTH = 860;
	final static int FRAME_HEIGHT = 716;
	
	final static Color FRAME_COLOR = new Color(255, 160, 161);
	
	private JButton btn1, btn2;
	private JLabel select_label, scale_label;
	private JButton scale_btn1, scale_btn2;
	private JPanel panel;
	private JScrollPane scrollpane;
	private ImageViewPanel gpanel;
	private JButton end_btn, close_btn;
	
	private int mScale;
		
	UserSozai(Frame frame) {
		

		super(frame,  "パソコン画像の選択", true);	
		getContentPane().setBackground(Color.white);
		
		ImagePanel back_panel = new ImagePanel("img/sozai/user.gif");
		back_panel.setPreferredSize(new Dimension(692, 648)); 	// これが無いと表示しない
		back_panel.setBackground(FRAME_COLOR);
		back_panel.setLayout(null);
		add(back_panel);
						
	    JPanel panel0 = new JPanel();
	    panel0.setLayout(new BorderLayout());
	    panel0.setBorder(BorderFactory.createLineBorder(Color.white, 10));
	    panel0.setBackground(Color.white);
	    back_panel.add(panel0, BorderLayout.CENTER);
	    
		panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.setBackground(Color.white);
		panel.setBorder(BorderFactory.createLineBorder(edenpo.FRAME_COLOR, 2));
//		panel0.add(panel2, BorderLayout.CENTER);
		panel0.add(panel, BorderLayout.CENTER);
		
		
		int offsetX = 4;
		int offsetY = -187;
		
		gpanel = new ImageViewPanel();
		gpanel.setBounds(38, 39, 450, 413);
		scrollpane = new JScrollPane(gpanel);
//		scrollpane.setBorder(null);
		scrollpane.setBounds(30+offsetX, 350+offsetY, 450, 413);
		scrollpane.setBorder(new LineBorder(Color.LIGHT_GRAY, 2,true ));
		scrollpane.setBorder(BorderFactory.createLineBorder(edenpo.FRAME_COLOR, 2));
		gpanel.setScrollPane(scrollpane);
		
        Toolkit.getDefaultToolkit().setDynamicLayout(true);
	     System.setProperty("sun.awt.noerasebackground", "true");

		
		btn1 = new JButton(Utility.ImageIcon(this, "img/sozai/user_sozai_btn1.gif"));
		btn1.setRolloverIcon(Utility.ImageIcon(this, "img/sozai/user_sozai_btn1_rollover.gif"));
		btn1.setPressedIcon(Utility.ImageIcon(this, "img/sozai/user_sozai_btn1_press.gif"));
		btn1.setBorder(null);
		btn1.setBounds(488+offsetX, 371+offsetY, 170, 55);
		btn1.addActionListener(this);
		back_panel.add(btn1);
		
		btn2 = new JButton(Utility.ImageIcon(this, "img/sozai/user_sozai_btn2.gif"));
		btn2.setRolloverIcon(Utility.ImageIcon(this, "img/sozai/user_sozai_btn2_rollover.gif"));
		btn2.setPressedIcon(Utility.ImageIcon(this, "img/sozai/user_sozai_btn2_press.gif"));
		btn2.setDisabledIcon(Utility.ImageIcon(this, "img/sozai/user_sozai_btn2_disable.gif"));
		btn2.setBorder(null);
		btn2.setBounds(488+offsetX, 460+offsetY, 170, 55);
		btn2.addActionListener(this);
		btn2.setEnabled(false);
		back_panel.add(btn2);
		
		JPanel panel4 = new JPanel();
		panel4.setLayout(null);
		panel4.setPreferredSize(new Dimension(636, 28)); 	// これが無いと表示しない
		panel4.setBackground(edenpo.BACK_COLOR2);
		panel4.setBounds(30+offsetX, 762+offsetY, 635, 28);
		panel4.setBorder(BorderFactory.createLineBorder(edenpo.BACK_COLOR, 1));
		
		back_panel.add(scrollpane);
//		panel.add(back_panel, BorderLayout.EAST);
		back_panel.add(panel4, BorderLayout.SOUTH);
		
		Font font = new Font("Default", Font.PLAIN, 12);
		
		select_label = new JLabel("選択中のファイル名:");
		select_label.setBounds(2, 5, 350, 20);
		select_label.setFont(font);
		panel4.add(select_label);
		
		mScale = 100;
		scale_label = new JLabel("現在の表示倍率:100%");
		scale_label.setBounds(360, 5, 350, 20);
		scale_label.setFont(font);
		panel4.add(scale_label);
		
 //   	Insets insets = new Insets(0, 1, 0, 1);
		scale_btn1 = new JButton("拡大");
		scale_btn1.setBounds(500, 4, 58, 22);
		scale_btn1.setFont(font);
		scale_btn1.setFocusable(false);
//		scale_btn1.setMargin(insets);
		scale_btn1.addActionListener(this);
		panel4.add(scale_btn1);
			
		scale_btn2 = new JButton("縮小");
		scale_btn2.setBounds(565, 4, 58, 22);
		scale_btn2.setFont(font);
		scale_btn2.setFocusable(false);
//		scale_btn2.setMargin(insets);
		scale_btn2.addActionListener(this);
		panel4.add(scale_btn2);
		
		JPanel bottom_panel = new JPanel();
		bottom_panel.setBackground(FRAME_COLOR);
		back_panel.setBackground(new Color(255, 160, 161));
		bottom_panel.setLayout(null);
	    add(bottom_panel, BorderLayout.SOUTH);
	    bottom_panel.setPreferredSize(new Dimension(182, 40)); 	// これが無いと表示しない
	    
		
	    end_btn = new JButton(Utility.ImageIcon(this, "img/ok.gif"));
	    end_btn.setRolloverIcon(Utility.ImageIcon(this, "img/ok_rollover.gif"));
	    end_btn.setPressedIcon(Utility.ImageIcon(this, "img/ok_press.gif"));
	    end_btn.setDisabledIcon(Utility.ImageIcon(this, "img/ok_disable.gif"));
	    end_btn.setBorder(null);
	    end_btn.setBounds(350, 0, 170, 34);
	    end_btn.addActionListener(this);
	    //end_btn.setEnabled(false);
	    
	    bottom_panel.add(end_btn);
	    bottom_panel.addComponentListener(this);
	    addComponentListener(this);
	    
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
		
		/**
		 * リサイズ出来ないようにする
		 */
		setResizable(false);	
	
		// スクリーンの中央に配置
		Rectangle screen = getGraphicsConfiguration().getBounds();
		if (screen.height < FRAME_HEIGHT) {
			setLocation(screen.x + screen.width/2  - FRAME_WIDTH/2, 
					screen.height - FRAME_HEIGHT-30);
			
		} else {
			setLocation(screen.x + screen.width/2  - FRAME_WIDTH/2, 
				screen.y + screen.height/2 - FRAME_HEIGHT/2);
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btn1) {
			FileDialog fd = new FileDialog(this , "画像ファイルの読み込み");
			fd.setFile("*.jpg;*.jpeg;*.gif");
			fd.setVisible(true);
			
		    String path = fd.getDirectory() + fd.getFile();
		    
            if(fd.getFile() != null) {
            	gpanel.setImage(path);
            	gpanel.repaint();
            	select_label.setText("選択中のファイル名:"+fd.getFile());
            	select_label.repaint();
            	                   	
            	end_btn.setEnabled(true);
            	btn2.setEnabled(true);
            	            		
            	mScale = 100;
            	setScaleLabel();
            }
		    fd.dispose();
		}
		if (e.getSource() == btn2) {
			gpanel.startTrim();
			return;
		}
		
		if (e.getSource() == scale_btn1) {
	    	mScale += 25;
	    	if (mScale > 200) {
	    		mScale = 200;
	    	}
	    	setScaleLabel();
			return;
    	}
		if (e.getSource() == scale_btn2) {
    		mScale -= 25;
    		if (mScale < 50) {
    			mScale = 50;
    		}
    	   	setScaleLabel();
			return;
		}	
		if (e.getSource() == close_btn) {
			gpanel.clear();
			setVisible(false);
			return;
		}
		if (e.getSource() == end_btn) {
			BufferedImage img = gpanel.getImage();
			if (img != null) {
				OpeAddImage ope = new OpeAddImage(img);
				edenpo.Stack.push(ope);
			}
			gpanel.clear();
			setVisible(false);
			return;
		}
	}

	
	public void componentHidden(ComponentEvent e) {}
	public void componentMoved(ComponentEvent e) {}
	public void componentResized(ComponentEvent e) {
		if (isShowing() && (getSize().width <FRAME_WIDTH || getSize().height <FRAME_HEIGHT )) {
			setSize(FRAME_WIDTH, FRAME_HEIGHT);
		}
		
		// リサイズしたとき終了ボタンの位置を変える
//		scale_label.setLocation(new Point(e.getComponent().getWidth()-290, 6));
//		scale_btn1.setLocation(new Point(e.getComponent().getWidth()-160, 6));
//		scale_btn2.setLocation(new Point(e.getComponent().getWidth()-100, 6));
		
//		end_btn.setLocation(new Point(e.getComponent().getWidth()-150, 0));
		
//		gpanel.setBounds(0, 0, scrollpane.getWidth(), scrollpane.getHeight());
//		gpanel.setScale(mScale);
//		scrollpane.invalidate();
		
		
	    
	}
	public void componentShown(ComponentEvent e) {
		/**
		 * この処理がないと、前回のrolloverが残ってしまう
		 */
//		close_btn.setEnabled(false);
//		close_btn.setEnabled(true);
		
		gpanel.setImage((String)null);
		gpanel.repaint();
		select_label.setText("選択中のファイル名:");
		select_label.repaint();
	    //end_btn.setEnabled(false);
  	
		btn2.setEnabled(true);
	
		mScale = 100;
		setScaleLabel();
	}
	
	public void setScaleLabel() {
		if (gpanel.getImage2() == null) {
			scale_btn1.setEnabled(false);
			scale_btn2.setEnabled(false);
			select_label.setText("選択中のファイル名:");
			btn2.setEnabled(false);
		
			return;
		}
		scale_btn1.setEnabled(true);
		scale_btn2.setEnabled(true);
  		if (mScale == 50) {
			scale_btn2.setEnabled(false);
   		} else if (mScale == 200) {
			scale_btn1.setEnabled(false); 			
    	}
			
 		scale_label.setText("現在表示倍率:"+mScale+"%");	
 		
 		gpanel.setScale(mScale);
 		scrollpane.revalidate();
	}
	
	protected void processWindowEvent(WindowEvent e) {
		if (e.getID() == WindowEvent.WINDOW_CLOSING ) {
			// 2007/12/30 メモリ消費を少なくするため画像データを開放させる
			gpanel.clear();
			setVisible(false);
	    }
	}

}

