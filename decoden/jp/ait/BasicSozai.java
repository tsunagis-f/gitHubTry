package jp.ait;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;


/**
 * 基本素材の選択　ダイアログ
 * 
 * @author kagi
 */
class BasicSozai extends JDialog implements ActionListener, ComponentListener {	
	/**
	 * ダイアログの初期サイズ
	 */
	final static int FRAME_WIDTH = 700;
	final static int FRAME_HEIGHT = 714;
	
	/**
	 * アイコンBoxの基本サイズ
	 */ 
	final static int SELECT_BOX_WIDTH = 76;
	final static int SELECT_BOX_WIDTH_MIN = 71;
	final static int SELECT_BOX_WIDTH_MAX = 85;
	final static int SELECT_BOX_HEIGHT = 71;
	final static int SELECT_BOX_HEIGHT_MAX = 100;
	
	
	// 選択リストの下の文字を表示する欄の高さ
	final static int SELECT_TEXT_HEIGHT = 44;
	
	// イメージ画像表示欄の下と　倍率と拡大/縮小ボタン　の欄の高さ
	final static int VIEW_TEXT_HEIGHT = 30;
	
	private JButton close_btn, select_btn;
	
//	private JPanel panel, select_panel, view_panel;
	private JPanel panel;
	private SelectPanel select_panel;
	private ViewPanel view_panel = null;
	public static JScrollPane scrollpane;


	private BufferedImage mImg;
	
	private int init_view_width, init_view_height;
	
	BasicSozai(Frame frame) {
		super(frame,  "用紙画像の選択", true);			
		setBackground(Color.white);
		addComponentListener(this);
		
		ImagePanel back_panel = new ImagePanel("img/sozai/basic.gif");
		back_panel.setPreferredSize(new Dimension(692, 648)); 	// これが無いと表示しない
		back_panel.setBackground(new Color(255, 160, 161));
		add(back_panel);
		back_panel.setLayout(null);
		
		init_view_width = init_view_height = 0;
//System.out.println("BasicSozai init create");

		/**
		 * 全体のレイアウトだけ作成する
		 */ 
/*
		JPanel space = new JPanel();
		space.setBackground(Color.white);
//		space.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		space.setPreferredSize(new Dimension(FRAME_WIDTH, 6)); 	
		back_panel.add(space, BorderLayout.NORTH);
	    
		
		space = new JPanel();
		space.setBackground(Color.white);
//		space.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		space.setPreferredSize(new Dimension(30, 556)); 
		back_panel.add(space, BorderLayout.WEST);
		
		space = new JPanel();
		space.setBackground(Color.white);
//		space.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		space.setPreferredSize(new Dimension(30, 556)); 	
		back_panel.add(space, BorderLayout.EAST);
		
		JPanel bottom_panel = new JPanel();
		bottom_panel.setBackground(Color.white);
		bottom_panel.setPreferredSize(new Dimension(FRAME_WIDTH, 40)); 	
		back_panel.add(bottom_panel, BorderLayout.SOUTH);
*/	    
	    panel = new JPanel();
		panel.setBackground(Color.white);
//		panel.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		panel.addComponentListener(this);
		panel.setPreferredSize(new Dimension(412, 463)); 	
	    panel.setLayout(null);
	    panel.setBounds(30, 169, 412, 463);
	    back_panel.add(panel);

		/**
		 * 「このウィンドウを閉じる」ボタンの作成
		 */ 
/*		
		close_btn = new JButton(Utility.ImageIcon(this, "img/close.gif"));
		close_btn.setRolloverIcon(Utility.ImageIcon(this, "img/close_rollover.gif"));
		close_btn.setPressedIcon(Utility.ImageIcon(this, "img/close_press.gif"));
		close_btn.setBorder(null);
		close_btn.addActionListener(this);
		bottom_panel.add(close_btn);
*/
	    
		/**
		 * 「選択中の画像に決定」ボタンの作成
		 */ 
		select_btn = new JButton(Utility.ImageIcon(this, "img/ok.gif"));
		select_btn.setRolloverIcon(Utility.ImageIcon(this, "img/ok_rollover.gif"));
		select_btn.setPressedIcon(Utility.ImageIcon(this, "img/ok_press.gif"));
		select_btn.setDisabledIcon(Utility.ImageIcon(this, "img/ok_disable.gif"));
		select_btn.setBorder(null);
		select_btn.addActionListener(this);
		select_btn.setEnabled(false);
		select_btn.setBounds(265, 644, 170, 34);
		back_panel.add(select_btn);
//		bottom_panel.add(select_btn);
		
		
		Font font = new Font("Default", Font.PLAIN, 12);
		
		/**
		 * 選択パネル
		 */
		select_panel = new SelectPanel(font, 0);
		select_panel.setLayout(null);
		select_panel.setBounds(0, 0, 419, 229);
		select_panel.setLayout(null);
		panel.add(select_panel);
		select_panel.setBasicSozaiOKButton(select_btn);
				
		/**
		 * 画像表示パネル
		 */
		view_panel = new ViewPanel(font, false);
		view_panel.setLayout(null);
		view_panel.setBounds(0, 238, 409, 228);
		panel.add(view_panel);
		select_panel.setViewPanel(view_panel);
		
		
		/**
		 * ダイアログのサイズ決定
		 */
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
		
		/**
		 * リサイズ出来ないようにする
		 */
		setResizable(false);	
		
		/**
		 * スクリーンの中央に配置
		 */
		Rectangle screen = getGraphicsConfiguration().getBounds();
		setLocation(screen.x + screen.width/2  - FRAME_WIDTH/2, 
				screen.y + screen.height/2 - FRAME_HEIGHT/2);
//		setVisible(true);
	}
	
	// 素材を読み込む
	public void setSozaiData(int order, String path,  String title) {
		select_panel.addSozaiData(0, order, path,  title);
	}
	
	// 基本素材の元画像を開放させる
	public void deleteSozaiData() {
		select_panel.deleteSozaiData();
	}
	
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == close_btn) {
			mImg = null;
			
			// 2007/12/30 メモリ消費を少なくするため画像データを開放させる
			deleteSozaiData();
			
			setVisible(false);
			return;
		}	
		
		if (e.getSource() == select_btn) {
			edenpo.Graphics_Panel.setBaseImage(view_panel.getImagePanel().getImage());
			
			// 2007/12/30 メモリ消費を少なくするため画像データを開放させる
			deleteSozaiData();
			
			setVisible(false);
			return;
		}	
	}
	
	public BufferedImage getImage() {
		return mImg;
	}
	
	public void componentHidden(ComponentEvent e) {}
	public void componentMoved(ComponentEvent e) {}
	public void componentResized(ComponentEvent e) {

		if (e.getComponent() != panel && isShowing() && (getSize().width <FRAME_WIDTH || getSize().height <FRAME_HEIGHT )) {
			setSize(FRAME_WIDTH, FRAME_HEIGHT);

//System.out.println("1 setSize width="+init_view_width+"  height="+init_view_height);
			view_panel.getScrollPane().setSize(init_view_width, init_view_height);
			view_panel.getImagePanel().resizeComponent();
			view_panel.getImagePanel().repaint();	
			return;
		}

		if (e.getComponent() == panel) {
			if (panel.getWidth() < 300 && panel.getHeight() < 300) {
				return;
			}
			if (init_view_width == 0) {
//System.out.println("1 view_panel = "+view_panel);
				if (view_panel == null)
					return;
				
				if (view_panel.getScrollPane().getWidth() > 0 && view_panel.getScrollPane().getHeight() > 0) {
					init_view_width = view_panel.getScrollPane().getWidth();
					init_view_height = view_panel.getScrollPane().getHeight();
				}
			}
			changeSelectBox();
			select_panel.setSelectPanelSize(panel.getWidth()-4, panel.getHeight()-4);
			
			if (view_panel.getScrollPane().getWidth() < init_view_width ||
					view_panel.getScrollPane().getHeight() < init_view_height) {
//System.out.println("2 setSize width="+init_view_width+"  height="+init_view_height);
				view_panel.getScrollPane().setSize(init_view_width, init_view_height);
			}
			view_panel.setBounds(0, select_panel.getHeight()+2, panel.getWidth()-4,  panel.getHeight()-select_panel.getHeight()-2);
			
			view_panel.getImagePanel().resizeComponent();
		}
	}
	public void componentShown(ComponentEvent e) {
//System.out.println("2 view_panel = "+view_panel+" init_view_width="+init_view_width+" init_view_height="+init_view_height);

		select_btn.setEnabled(false);
		if (view_panel.getImagePanel().getImage() != null) {
			select_btn.setEnabled(true);			
		}
		
		if (init_view_width == 0) {
			if (view_panel.getScrollPane().getWidth() > 0 && view_panel.getScrollPane().getHeight() > 0) {
				init_view_width = view_panel.getScrollPane().getWidth();
				init_view_height = view_panel.getScrollPane().getHeight();
				
//System.out.println("componentShown width="+init_view_width+"  height="+init_view_height);			
				changeSelectBox();
				select_panel.setSelectPanelSize(panel.getWidth()-4, panel.getHeight()-4);

				view_panel.getImagePanel().resizeComponent();
			}
		}
		
	}
	
	
	public void changeSelectBox() {
		select_panel.repaint();
		
		//int frame_width = getWidth();
		int frame_height = getHeight();
		int width = panel.getWidth()-4;
		int height = select_panel.getHeight();
		if (frame_height < 750) {
			height = 200;
		} else if (frame_height < 1040) {
			height = 300;
		} else {
			height = 400;
		}
		
//System.out.println("changeSelectBox select_panel="+select_panel);			
		select_panel.setSize(width, height+SELECT_TEXT_HEIGHT-2);

	}
	
	protected void processWindowEvent(WindowEvent e) {
		if (e.getID() == WindowEvent.WINDOW_CLOSING ) {
			// 2007/12/30 メモリ消費を少なくするため画像データを開放させる
			deleteSozaiData();
			
			setVisible(false);
	    }
	}
	

	
}
