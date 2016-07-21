package jp.ait;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

class Preview extends JDialog implements ActionListener, ComponentListener, WindowListener   {
	
	// ダイアログの初期サイズ
	final static int FRAME_WIDTH = 669;
	final static int FRAME_HEIGHT = 720;
	
	private JPanel view_panel, bottom_panel;
	private PreviewView gpanel;
	private JScrollPane scrollpane;
	private JButton close_btn;
	private JLabel scale_label;
	private JButton scale_btn1, scale_btn2;
	
	private int mScale;
	private float mPrevScale;
	
	private int gwidth, gheight;
	
	private int mStartWidth, mStartHeight;

	Preview(Frame frame) {
		super(frame,  "プレビュー", true);	
		
		setLayout(new BorderLayout());
		getContentPane().setBackground(edenpo.FRAME_COLOR3);
		
		Font font = new Font("Default", Font.PLAIN, 12);
		Insets insets = new Insets(0, 2, 0, 2);
		
		JLabel label = new JLabel(new ImageIcon("img/preview.gif"));
		add(label, BorderLayout.NORTH);
		
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.setBackground(Color.white);
		panel.setBorder(BorderFactory.createLineBorder(Color.white, 4));
		
		view_panel = new JPanel();
		view_panel.setLayout(new BorderLayout());
		view_panel.setBackground(Color.white);
		view_panel.setBorder(new LineBorder(edenpo.FRAME_COLOR, 3,true ));
		panel.add(view_panel, BorderLayout.CENTER);
		
		gpanel = new PreviewView();
		scrollpane = new JScrollPane(gpanel);
		scrollpane.setBorder(new LineBorder(edenpo.FRAME_COLOR, 1 ));
//		gpanel.setScrollPane(scrollpane);
		view_panel.add(scrollpane, BorderLayout.CENTER);
		scrollpane.setPreferredSize(new Dimension(200, 200));

		JPanel view_panel2 = new JPanel();
		view_panel2.setBackground(edenpo.BACK_COLOR2);
		view_panel2.setBorder(new LineBorder(edenpo.FRAME_COLOR, 1,true ));
		scale_label = new JLabel("現在の表示倍率:100% ");
		scale_label.setFont(font);
		view_panel2.add(scale_label);
		
		scale_btn1 = new JButton("拡大");
		scale_btn1.setFont(font);
		scale_btn1.setFocusable(false);
		scale_btn1.addActionListener(this);
		scale_btn1.setMargin(insets);
		view_panel2.add(scale_btn1);
			
		scale_btn2 = new JButton("縮小");
		scale_btn2.setFont(font);
		scale_btn2.setFocusable(false);
		scale_btn2.addActionListener(this);
		scale_btn2.setMargin(insets);
		view_panel2.add(scale_btn2);
		
		view_panel2.setPreferredSize(new Dimension(2, 36)); 
		view_panel.add(view_panel2, BorderLayout.SOUTH);
		
		add(panel, BorderLayout.CENTER);
		
		bottom_panel = new JPanel();
		bottom_panel.setBackground(Color.white);
		add(bottom_panel, BorderLayout.SOUTH);
		
		addComponentListener(this);
		addWindowListener(this);
	
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
		
		
		gwidth = gpanel.getWidth();
		gheight = gpanel.getHeight();
		
		// スクリーンの中央に配置
		Rectangle screen = getGraphicsConfiguration().getBounds();
		setLocation(screen.x + screen.width/2  - FRAME_WIDTH/2, 
				screen.y + screen.height/2 - FRAME_HEIGHT/2);

	}
	
	public void setInitSize() {
		mStartWidth = gpanel.getWidth();
		mStartHeight = gpanel.getHeight();		
	}
	
	public void actionPerformed(ActionEvent e) {
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
			// ドキュメントの値を戻す
			edenpo.Graphics_Panel.popDocInfo();
			
			setVisible(false);
			return;
		}
	}

	
	public void componentHidden(ComponentEvent e) {}
	public void componentMoved(ComponentEvent e) {}
	public void componentResized(ComponentEvent e) {
		if (isShowing()) {
			setScaleLabel();
		}
	}
	public void componentShown(ComponentEvent e) {
		/**
		 * この処理がないと、前回のrolloverが残ってしまう
		 */
//		close_btn.setEnabled(false);
//		close_btn.setEnabled(true);
		
		mScale = 100;
		gwidth = gpanel.getWidth();
		gheight = gpanel.getHeight();
		scrollpane.doLayout();
		scrollpane.validate();
		
		edenpo.Graphics_Panel.setScale(1.0f);
		setScaleLabel();
	}
	
	public void setScaleLabel() {
		scale_btn1.setEnabled(true);
		scale_btn2.setEnabled(true);
  		if (mScale == 50) {
			scale_btn2.setEnabled(false);
   		} else if (mScale == 200) {
			scale_btn1.setEnabled(false); 			
    	}
			
 		scale_label.setText("現在表示倍率:"+mScale+"% ");	
 		
 		int width = (int)(edenpo.Graphics_Panel.getOrgDocWidth() * (mScale / 100.0)+16);
 		int height = (int)(edenpo.Graphics_Panel.getOrgDocHeight() * (mScale / 100.0)+16);
 		
		gpanel.setPreferredSize(new Dimension(width, height));   			
	
 		gpanel.revalidate();  	
		scrollpane.doLayout();
		scrollpane.validate();
		
 		edenpo.Graphics_Panel.setScale(mScale / 100.0f);
 		gpanel.recreate();
	}
	public void windowActivated(WindowEvent e) {}
	public void windowClosed(WindowEvent e) {}
	public void windowClosing(WindowEvent e) {
		// ドキュメントの値を
		edenpo.Graphics_Panel.popDocInfo();
	}
	public void windowDeactivated(WindowEvent e) {}
	public void windowDeiconified(WindowEvent e) {}
	public void windowIconified(WindowEvent e) {}
	public void windowOpened(WindowEvent e) {}	
	
}

class PreviewView extends JPanel implements ComponentListener  {
	private Image mImgBack;
	
	PreviewView() {
		addComponentListener(this);
	}
	
   protected void paintComponent(Graphics g) {
     	
    	super.paintComponent(g);
//    	redraw();
        if (mImgBack == null) {
       		
        	mImgBack = createImage(getWidth(), getHeight());   
        }	
        	
        Graphics offscreen = mImgBack.getGraphics();
        //　クリア
        offscreen.setColor(Color.white);
        offscreen.fillRect(0, 0,getWidth(), getHeight());
    		
    	edenpo.Graphics_Panel.drawBackground(offscreen, getWidth(), getHeight());
    		
    	for (int i = 0; i < edenpo.Graphics_Panel.display_list.size(); i++) {
    		Group obj = (Group)edenpo.Graphics_Panel.display_list.get(i);
    		obj.draw(offscreen, this);
    	}
    	
    	edenpo.Graphics_Panel.drawFrame(offscreen, getWidth(), getHeight(), false, true);
     	
        g.drawImage(mImgBack, 0, 0, this);      //バッファを画面に描画
        
        offscreen.dispose();
    }   
    
    public void recreate() {
    	mImgBack = null;
    	repaint();
    }
    
	public void componentHidden(ComponentEvent e) {}
	public void componentMoved(ComponentEvent e) {}
	public void componentShown(ComponentEvent e) {}
	public void componentResized(ComponentEvent e) {
		if (isShowing()) {
			recreate();
		}
	}

}


