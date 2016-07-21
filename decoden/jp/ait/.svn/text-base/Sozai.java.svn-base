package jp.ait;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.*;
import java.io.DataInputStream;
import java.io.File;
import java.net.*;
import java.io.*;




import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.imageio.ImageReader;
import com.sun.image.codec.jpeg.*;


//import org.apache.commons.net.ftp.*;


/**
 * サムネイルクラス
 * 
 */
class Thumbnail {
	
	final static int IMG_WIDTH_MAX = 64;
	final static int IMG_HEIGHT_MAX = 60;
	
	public int mOrder;
	private String mTitle;
	private String mPath;
	private Image mImg;	
	private BufferedImage mOrgImg;	
	private int mWidth, mHeight;
	private JPanel mPanel;
	
	/*
	 * サムネイルは　起動時に作る必要がある
	 * ダイアログを表示時に作るとかなり遅い印象になる
	 */
	Thumbnail(JPanel panel, int order, String title, String path) {
		mOrder = order;
		mTitle = title;
		mPath = path;
		mPanel = panel;
		

		if (path == null)
			return;
		
		mOrgImg = null;
		int width, height;
					
		mOrgImg = Sozai.getImage(path);		
		
		if (mOrgImg.getHeight() < mOrgImg.getWidth()) {
			width = IMG_WIDTH_MAX;
			height = (int)(((double)mOrgImg.getHeight() / mOrgImg.getWidth()) * IMG_WIDTH_MAX);
		} else {
			height = IMG_HEIGHT_MAX;
			width = (int)(((double)mOrgImg.getWidth() / mOrgImg.getWidth()) * IMG_HEIGHT_MAX);
		}


		// 2007/12/31 createImage が大量のメモリを消費する為BufferedImageに変更
/**		
		ImageFilter fl;
		fl = new ReplicateScaleFilter(width, height);
//		fl = new AreaAveragingScaleFilter(width , height);			// こっちの方が鮮明（だが少し遅い）

		FilteredImageSource fis = new FilteredImageSource(mOrgImg.getSource() , fl);
		mImg = panel.createImage(fis);
**/
		
	    mImg =  Sozai.getIconImage(mOrgImg, width, height);		
		mWidth = width;
		mHeight = height;
		
		// 2007/12/30 メモリの為起動時には画像は溜めない
		mOrgImg = null;

	}
	
	public Image getImage() {
		return mImg;
	}
	
	public BufferedImage getOriginalImage() {
		if (mOrgImg == null) {
			mOrgImg = Sozai.getImage(mPath);					
//System.out.println("Thumbnail set mOrgImg"+mPath);
		}
		return mOrgImg;
	}
	public boolean checkOriginalImage() {
		if (mOrgImg == null) {
			return false;
		}
		return true;
		
	}
	
	public void freeOriginalImage() {
		mOrgImg = null;
	}
	
	public int getWidth() {
		return mWidth;
	}
	
	public int getHeight() {
		return mHeight;
	}
	
	public String getPath() {
		return mPath;
	}
}

/**
 * タブに対応したクラス
 * 
 */
class TabImage {
	private String mTitle;
	private String mPath;
	private ArrayList mImgList = new ArrayList();	
	
	public void setTitle(String str1) {
		mTitle = str1;
	}
	
	public String getTitle() {
		return mTitle;
	}
/*	
	public String getPath() {
		return mPath;
	}
*/	
	public void addList(Thumbnail obj) {
		mImgList.add(obj);
	}
	public void addList(int index, Thumbnail obj) {
		mImgList.add(index, obj);
	}
	
	public Thumbnail get(int index) {
		return (Thumbnail)mImgList.get(index);
	}
	
	public int getNum() {
		return mImgList.size();
	}
}


/**
 * 素材選択ダイアログ
 * 
 * @author kagi
 */
class Sozai extends JDialog   implements  ActionListener, ComponentListener  {
	// ダイアログの初期サイズ
	final static int FRAME_WIDTH = 860;
	final static int FRAME_HEIGHT = 726;
	
	
	final static Color FRAME_COLOR = new Color(48, 192, 0);	
	static JDialog DIALOG;			
	
	private SelectPanel select_panel;
	private JButton ok_btn;
	private ViewPanel view_panel;
	
	private int init_view_width, init_view_height;
	
	static ImageReader GifReader = null;
	
	Sozai(Frame frame) {
		super(frame,  "飾り画像の選択", true);			
		addComponentListener(this);
		
		DIALOG = this;
		init_view_width = init_view_height = 0;
		
		Font font = new Font("Default", Font.PLAIN, 11);
		
		ImagePanel back_panel = new ImagePanel("img/sozai/add.gif");
		back_panel.setPreferredSize(new Dimension(700, 896)); 	// これが無いと表示しない
		back_panel.setBackground(new Color(255, 160, 161));
		back_panel.setLayout(null);
		add(back_panel);
			

		font = new Font("Default", Font.PLAIN, 12);
		
		int offsetX = 4;
		int offsetY = -230;
		/**
		 * 選択パネル
		 */
		select_panel = new SelectPanel(font, 1);
		select_panel.setBounds(22 + offsetX, 364 + offsetY, 653, 246);
		back_panel.add(select_panel);
		
		/**
		 * 画像表示パネル
		 */
		view_panel = new ViewPanel(font, true);
		view_panel.setBounds(25 + offsetX, 610 + offsetY, 290, 240);
		back_panel.add(view_panel);
		select_panel.setViewPanel(view_panel);
		
		/**
		 * 取置きパネル
		 */
		Torioki torioki_panel = new Torioki(view_panel, select_panel);
		torioki_panel.setBounds(319 + offsetX, 614 + offsetY, 350, 235);
		back_panel.add(torioki_panel);
		
		select_panel.setToriokiPanel(torioki_panel);
		
		/**
		 * 「確定」ボタンの作成
		 */ 
		ok_btn = new JButton(Utility.ImageIcon(this, "img/ok.gif"));
		ok_btn.setRolloverIcon(Utility.ImageIcon(this, "img/ok_rollover.gif"));
		ok_btn.setPressedIcon(Utility.ImageIcon(this, "img/ok_press.gif"));
		ok_btn.setDisabledIcon(Utility.ImageIcon(this, "img/ok_disable.gif"));
		ok_btn.setBorder(null);
		ok_btn.addActionListener(this);
		ok_btn.setBounds(265+80, 900 -250, 170, 34);
		back_panel.add(ok_btn);

		/**
		 * ダイアログのサイズ決定
		 */
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
			
//		setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == ok_btn) {
			setVisible(false);
			return;
		}	
	}
	
	public void setSozaiData(int type, int order, String path, String title) {
		select_panel.addSozaiData(type, order, path, title);
	}
	
	
	/**
	 * URLで指定したサーバー上の画像を読み取る
	 * 
	 * @param path  サーバー上の画像URL
	 * @return BufferedImage
	 */
	static BufferedImage getImage(String path) {
		BufferedImage img = null;
		int len;
		long before, after;
		    
 		try{
 
        	URLConnection con = null;
			try {
				URL url = new URL(path);
				con = url.openConnection();
			    String userPassword =  "usr" + ":" + "EW8inC45";

			    // Base64 encode the authentication string
			    String encoding = new sun.misc.BASE64Encoder().encode (userPassword.getBytes());
			    
			    con.setRequestProperty ("Authorization", "Basic " + encoding);

			    // readHttpURL
			    con.setAllowUserInteraction (true);
			    con.connect();
			    before = System.currentTimeMillis();
			    DataInputStream in = new DataInputStream(con.getInputStream());
			    after = System.currentTimeMillis();
			 
			    before = System.currentTimeMillis();
			    
			    // 2007/12/31  ImageID.read にメモリリークがあるため別の方法で読む
//			    img = ImageIO.read(in);

			    ImageIO.setUseCache(false);		    
			    if (GifReader == null) {
			    	GifReader = (ImageReader)ImageIO.getImageReadersByFormatName("gif").next(); 
			    }
			    GifReader.setInput(ImageIO.createImageInputStream(in)); 

			    BufferedImage img2;
			    img = GifReader.read(0); 
//System.out.println("getImage width="+img.getWidth()+" height="+img.getHeight());
			} catch(java.io.IOException ee) {
				JOptionPane.showMessageDialog(edenpo.Main_Frame, Message.READ_IMG_ERROR, "Error", JOptionPane.ERROR_MESSAGE);
			}
        	
        } catch(Exception ex) {
			JOptionPane.showMessageDialog(edenpo.Main_Frame, Message.READ_IMG_ERROR, "Error", JOptionPane.ERROR_MESSAGE);
       }
		
        return img;
	}
	
	// 2007/12/31 createImage が大量のメモリを消費する為BufferedImageに変更
	static BufferedImage getIconImage(BufferedImage org_img, int width, int height) {
		
		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics g = img.getGraphics();
		g.drawImage(org_img, 0, 0, width, height, null);				
		g.dispose();	
		img.flush();
		
		return img;
	}
	
	public void componentHidden(ComponentEvent e) {}
	public void componentMoved(ComponentEvent e) {}
	public void componentResized(ComponentEvent e) {
		if (isShowing() && (getSize().width <FRAME_WIDTH || getSize().height <FRAME_HEIGHT )) {
			setSize(FRAME_WIDTH, FRAME_HEIGHT);

			view_panel.getScrollPane().setSize(init_view_width, init_view_height);
			view_panel.getImagePanel().resizeComponent();
			view_panel.getImagePanel().repaint();		
		}
	}
	public void componentShown(ComponentEvent e) {
		/**
		 * この処理がないと、前回のrolloverが残ってしまう
		 */
//		view_panel.getCloseButton().setEnabled(false);
//		view_panel.getCloseButton().setEnabled(true);
		

		if (init_view_width == 0) {
			init_view_width = view_panel.getScrollPane().getWidth();
			init_view_height = view_panel.getScrollPane().getHeight();
			
			select_panel.setSelectPanelSize(select_panel.getWidth(), select_panel.getHeight());			
			
		}
		
	}

}
	

class  SelectPanel extends JPanel  implements ActionListener, ComponentListener {
	// アイコンBoxの基本サイズ
	
	final static int SELECT_BOX_WIDTH = 76;
	final static int SELECT_BOX_WIDTH_MIN = 71;
	final static int SELECT_BOX_WIDTH_MAX = 85;
	final static int SELECT_BOX_HEIGHT = 68;
	final static int SELECT_BOX_HEIGHT_MAX = 100;
	final static int SELECT_TAB_HEIGHT = 25;
	
	// 選択リストの下の文字を表示する欄の高さ
	final static int SELECT_TEXT_HEIGHT = 34;
	
	private JPanel select_panel2, select_panel2_1, select_panel3, select_panel3_1;
	// お客様素材のみで使用
	private TabImage [] mTabImg = new TabImage[10];
	private TabButton[] mTabs = new TabButton[20];
	private int mTabNum;
	private int mCurrentTab;
	
	
	// 基本素材のみで仕様
	private ArrayList mThumbnail_list;
	
	private LinkButton[] mLinkButton = new LinkButton[30];
	private LinkButton mPrevButton, mNextButton;
	private int mLinkButtonNum;
	private int mCurrentLinkNo = 0;
	private int mType;			// 0:基本素材     1:素材選択
	
	
	private BoxPanel[] mBoxes;
	private File[] mFileList;
	private int mBoxNum;
	private JButton torioki_btn;
	private BoxPanel mCurrentBox;
	private int	mSelectBoxPage;
	
	private ViewPanel mViewPanel = null;
	private Torioki mToriokiPanel;
	
	private JButton mBasiceSozaiOKBtn = null;
	
	SelectPanel(Font font, int type) {
		setBackground(Color.white);
		setLayout(null);
		
		mType = type;
		mTabNum = 0;
		mCurrentTab = 0;
		mSelectBoxPage = 0;
		if (mType == 1) {
			
			mTabNum = 4;
			mTabImg[0] = new TabImage();
			mTabImg[0].setTitle("背景");
			mTabImg[1] = new TabImage();
			mTabImg[1].setTitle("フレーム");
			mTabImg[2] = new TabImage();
			mTabImg[2].setTitle("イラスト");
			mTabImg[3] = new TabImage();
			mTabImg[3].setTitle("飾り文字");
			
			// タブボタンを作成
			for (int i = 0; i < mTabNum; i++) {
				mTabs[i] = new TabButton(mTabImg[i].getTitle());
				mTabs[i].addActionListener(this);
				add(mTabs[i]);
				mTabs[i].setBounds(16+(100*i), 6, 95, 25);
			}
			if (mTabNum > 0) {
				mTabs[mCurrentTab].setSelect(true);
			}
			ImagePanel back_panel = new ImagePanel("img/sozai/tab_back.gif");
			back_panel.setBounds(8, 0, 637, 32);
			add(back_panel);
			
		} else {
			/**
			 * 基本素材ではタブは使わないが
			 * Thumbnailクラスを使うため　ダミーで１つタブ用のクラスを作成している
			 */
			
	 		mCurrentTab = 0;
			mTabImg = new TabImage[1];
			mTabImg[0] = new TabImage();
			mTabImg[0].setTitle(null);
		
			mTabNum = 1;

		}
		
//		select_panel.setBorder(BorderFactory.createLineBorder(Color.black, 1));
//		add(select_panel, BorderLayout.CENTER);
		addComponentListener(this);
		
	
		select_panel2 = new JPanel();
		select_panel2.setLayout(null);
		select_panel2.setBackground(Color.white);
		select_panel2.setBorder(new LineBorder(edenpo.FRAME_COLOR,  2, true ));
		add(select_panel2);
		
		// この中にイメージBoxが出来る
		select_panel2_1	= new JPanel();
		select_panel2_1.setBackground(Color.white);
		select_panel2.add(select_panel2_1);
	
		// この中に文字選択が入る
		select_panel3 = new JPanel();
		select_panel3.setBorder(new LineBorder(edenpo.FRAME_COLOR,  2, true ));
		select_panel3.setBackground(edenpo.BACK_COLOR2);
		add(select_panel3);
	
		if (mType == 1) {
			// この中に[取り置きボックスへ入れる]が入る
			select_panel3_1 = new JPanel();
			select_panel3_1.setLayout(null);
			select_panel3_1.setPreferredSize(new Dimension(240, 24)); 	
			select_panel3_1.setBorder(new LineBorder(edenpo.FRAME_COLOR,  2, true ));
			select_panel3_1.setBackground(edenpo.BACK_COLOR2);
			add(select_panel3_1);

//			torioki_btn = new JButton("取り置きボックスへ入れる");
			torioki_btn = new JButton(Utility.ImageIcon(this, "img/sozai/torioki_set.gif"));
			torioki_btn.setDisabledIcon(Utility.ImageIcon(this, "img/sozai/torioki_set_disable.gif"));
			torioki_btn.setRolloverIcon(Utility.ImageIcon(this, "img/sozai/torioki_set_rollover.gif"));
			torioki_btn.setPressedIcon(Utility.ImageIcon(this, "img/sozai/torioki_set_press.gif"));
			torioki_btn.addActionListener(this);
			torioki_btn.setBounds(11, 6, 218 ,23);
			torioki_btn.setFont(font);
			select_panel3_1.add(torioki_btn);
			torioki_btn.setEnabled(false);
		}
		
		mCurrentBox = null;
	}
	
	public void setViewPanel(ViewPanel panel) {
		mViewPanel = panel;
		
	}
	
	public void setToriokiPanel(Torioki panel) {
		mToriokiPanel = panel;		
	}
	
	public void setBasicSozaiOKButton(JButton btn) {
		mBasiceSozaiOKBtn = btn;
	}
	
	/**
	 * 選択したBoxの状態を設定
	 * 
	 * @param box
	 */
	public void setCurrentBox(BoxPanel box) {
		if (mCurrentBox != null) {
			mCurrentBox.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
			mCurrentBox.repaint();			
		}
		mCurrentBox = box;
		mCurrentBox.setBorder(BorderFactory.createLineBorder(Color.blue, 2));
		mCurrentBox.repaint();		
		mSelectBoxPage = mCurrentLinkNo;
		
		if (mType == 1) {
			torioki_btn.setEnabled(true);			
		} else {
			if (mBasiceSozaiOKBtn != null) {
				mBasiceSozaiOKBtn.setEnabled(true);
			}
		}
	}
	public BoxPanel getCurrentBox() {
		return mCurrentBox;
	}
	
	/**
	 * 選択Boxの状態を初期化する
	 *
	 */
	public void clearSelectBox() {
		if (mCurrentBox != null) {
			/**
			 * 選択ボックスの選択状態をクリアにする
			 */
			mCurrentBox.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
			mCurrentBox.repaint();	
			mCurrentBox = null;
			mSelectBoxPage = 0;
			
			
			/**
			 * 取り置きボックスが選択状態になってなければビューをクリア
			 */
			if (mToriokiPanel.getCurrentBox() == null) {
				mViewPanel.init();
			}
			
			if (mType == 1) {
				/**
				 * [取り置きボックスへ入れる]を使用不可にする
				 */
				torioki_btn.setEnabled(false);
			}
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		/**
		 * 取り置きボタン
		 */
		if (e.getSource() == torioki_btn) {
			if (mToriokiPanel.getBoxNum() >= 10) {
				JOptionPane.showMessageDialog(this, "ボックスが一杯です。「削除」ボタンでボックスを減らすか「決定」ボタンで素材を配置してください");
			} else {
				if (mCurrentBox != null) {
					mToriokiPanel.addImage(mCurrentBox);
					mToriokiPanel.repaint();
					
					/**
					 * 選択Boxの選択状態を初期化
					 */
					clearSelectBox();
				}
				torioki_btn.setEnabled(false);
			}
			return;
		}
		
		if (mType == 1) {
			/**
			 * タブボタンのイベント
			 */
			for (int i = 0; i < mTabNum; i++) {
				if (e.getSource() == mTabs[i]) {
					if (i != mCurrentTab) {
						mTabs[mCurrentTab].setSelect(false);
						mTabs[mCurrentTab].repaint();
						
						mCurrentTab = i;
						selectTab(i);
					}
					
					if (mCurrentBox != null) {
						/**
						 * 選択Boxの選択状態を初期化
						 */
						clearSelectBox();
						
						torioki_btn.setEnabled(false);
					}
					return;
				}
			}
			
		}
		
		/*
		 * 前へ戻る　ボタンイベント
		 */
		if (e.getSource() == mPrevButton) {
			/**
			 * 選択Boxの選択状態を初期化
			 */
			clearSelectBox();
			
			setLinkButton(mCurrentLinkNo-1);
			return;
		}
		
		/*
		 * 次へ　ボタンイベント
		 */
		if (e.getSource() == mNextButton) {
			/**
			 * 選択Boxの選択状態を初期化
			 */
			clearSelectBox();
			
			setLinkButton(mCurrentLinkNo+1);
			return;
		}
		/**
		 * リンク文字列のクリックイベント
		 */
		for (int i = 0; i < mLinkButton.length; i++) {
			if (mLinkButton[i] != null) {
				if (e.getSource() == mLinkButton[i]) {
					/**
					 * 選択Boxの選択状態を初期化
					 */
					clearSelectBox();
					
					setLinkButton(i);
					return;
				}				
			}			
		}
		
	}
	
	
	public void componentHidden(ComponentEvent e) {}
	public void componentMoved(ComponentEvent e) {}
	public void componentResized(ComponentEvent e) {

		setSelectPanelSize(getWidth(), getHeight());
	}
	public void componentShown(ComponentEvent e) {
	}
	
	public void setSelectPanelSize(int width, int height) {
		int x = 8;
		int y = 31;
				
		select_panel2.setBounds(x, y, width-16, height-30-31);
		select_panel2_1.setBounds(6, 6, width-16-10, height-30-25-16);
		if (mType == 1) {
			select_panel3.setBounds(8, height-32, width-16-230, 32);
			select_panel3_1.setBounds(width-230-16, height-34, 230+8, 34);
		} else {
			select_panel3.setBounds(8, height-32, width-16, 32);		
		}
		select_panel2_1.revalidate();
		
		changeSelectBox();
		if (mType == 1) {
			selectTab(mCurrentTab);
		} else {
			selectTab(0);
		}
		
		if (mViewPanel != null) {
			mViewPanel.getImagePanel().resizeComponent();
		}
	}
	
	/**
	 * 選択領域から　Boxが縦横で何個入るか計算し、Boxオブジェクトを作成する
	 *
	 */
	public void changeSelectBox() {
		int frame_width = select_panel2_1.getWidth();
		int frame_height = select_panel2_1.getHeight();
		
		int col ;
		int row;
		
//		col = (frame_width - 10)  / SELECT_BOX_WIDTH; 
		col = (frame_width - 0)  / SELECT_BOX_WIDTH; 
		row = (int)((frame_height - SELECT_TAB_HEIGHT - SELECT_TEXT_HEIGHT) / (double)SELECT_BOX_HEIGHT + 0.5);

		if (col < 0 || row < 0)
			return;
		
		// Boxをクリア
		select_panel2_1.removeAll();
/*
		for (int i = 0; i < mBoxNum; i++) {
			mBoxes[mBoxNum].clear();
			mBoxes[i].repaint();
			mBoxes[mBoxNum] = null;
		}
*/		
		GridLayout grid_layout = new GridLayout(row, col);
		grid_layout.setHgap(4);
		grid_layout.setVgap(4);
		select_panel2_1.setLayout(grid_layout);
//System.out.println("row="+row+"  col="+col);

		mBoxes = new BoxPanel[row*col];
		mBoxNum = 0;
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				mBoxes[mBoxNum] = new BoxPanel(mViewPanel, this, mBoxNum);
				mBoxes[mBoxNum].setBorder(new LineBorder(Color.LIGHT_GRAY, 1,true ));
				mBoxes[mBoxNum].setBackground(new Color(207, 238, 254));
				select_panel2_1.add(mBoxes[mBoxNum]);
				if (mCurrentBox != null) {
					if (mCurrentBox.mNo == mBoxNum) {
						mCurrentBox = null;
						setCurrentBox(mBoxes[mBoxNum]);
					}
				}
				mBoxNum++;
				
			}
		}

		select_panel2_1.repaint();

	}
	/**
	 * 指定したディレクトリから画像ファイルを得てサムネイルのサイズでImageを作成する
	 *   追加素材の場合は　フォルダ名がタグのタイトルし、タブ毎にImageのリストを作成
	 *   
	 * @param dir　　ディレクトリ
	 * @param type　 0:基本素材    1:追加素材
	 */
	public void addSozaiData(int type, int order, String path, String title) {
		
		Thumbnail thum = new Thumbnail(this, order, title, path);
		
		if (mType == 0) {
			addList(0, thum);
		} else {
			addList(type-1, thum);

		}
	}
	
	/**
	 * 現在は　基本素材だけ対応
	 * Thumbnail に保持している画像データを開放させる
	 *   
	 * @param dir　　ディレクトリ
	 * @param type　 0:基本素材    1:追加素材
	 */
	public void deleteSozaiData() {
		
		for (int i = 0; i < mTabImg[0].getNum(); i++) {
			Thumbnail thum =   mTabImg[0].get(i);
			thum.freeOriginalImage();
		}
	}
	
	private void addList(int index, Thumbnail new_thum) {
		if (index >= mTabNum)
			return;
		
		for (int i = 0; i < mTabImg[index].getNum(); i++) {
			Thumbnail thum =   mTabImg[index].get(i);
			/**
			 * orderの順序で挿入
			 * 同じ orderの番号があったらそのobjectの前に挿入
			 */
			if (new_thum.mOrder <= thum.mOrder) {
				mTabImg[index].addList(i, new_thum);
				return;
			}
		}
		mTabImg[index].addList(new_thum);		
	}
	
	/**
	 * タブ選択により、画像リストと　リンク文字の状態を変える
	 * 
	 * @param tab_index
	 */
	public void selectTab(int tab_index) {
		if (mBoxNum == 0 || mTabImg == null)
			return;
		
		// 追加素材かどうか
		if (mType == 1) {
			mTabs[tab_index].setSelect(true);
			mTabs[tab_index].repaint();
		}
		
		/**
		 * 画像リストを初期化
		 */
		for (int i = 0; i < mBoxNum; i++) {
			mBoxes[i].clear();
			mBoxes[i].repaint();
		}
		
		
		/**
		 * リンク文字列を全て削除
		 */
		select_panel3.removeAll();
		
		
		/**
		 * リンク文字列を作成
		 */
		mLinkButtonNum = (mTabImg[tab_index].getNum() -1)/ mBoxNum + 1;
	    mPrevButton = new LinkButton("前の一覧へ", false, false);
	    mPrevButton.addActionListener(this);
	    select_panel3.add(mPrevButton);
	    for (int i = 0; i < mLinkButtonNum; i++) {
	    	mLinkButton[i] = new LinkButton(""+(i+1), false, true);
	    	mLinkButton[i].addActionListener(this);
		    select_panel3.add(mLinkButton[i]);			
		}
	    mNextButton = new LinkButton("次の一覧へ", false, false);
	    mNextButton.addActionListener(this);
	    select_panel3.add(mNextButton);
	    
	    /**
	     * 起動時は一番初めが表示されているの
	     * リンクボタンの状態を変えて、Boxを再配置する
	     */
	    setLinkButton(0);
	    
	    // 全部表示しているときは　画面遷移文字は選択不可にする
	    if (mTabImg[tab_index].getNum() <= mBoxNum) {
	    	mNextButton.setEnabled(false);
	    }
	    select_panel3.revalidate();
	    select_panel3.repaint();
	}

	
	/**
	 * 拡張子が jpeg(jpg) かgifのときだけ　trueを返す
	 * 
	 * @param fileName
	 * @return
	 */
	public boolean checkSuffix(String fileName) {
	    if (fileName == null)
	        return false;
	    
	    int point = fileName.lastIndexOf(".");
	    if (point != -1) {
	    	String ext = fileName.substring(point + 1);
	    	if (ext.equalsIgnoreCase("jpg") || ext.equalsIgnoreCase("jpeg") || ext.equalsIgnoreCase("gif"))
	    		return true;
	    	else
	    		return false;
	    }
	    
	    return false;
	}

	
	/**
	 * 指定したリンクボタンのインデックスによって
	 * Boxの画像を変更し、　リンク文字列の使用可/不可を変えている
	 * @param index
	 */
	private void  setLinkButton(int index) {
		int box_start = index * mBoxNum;

		for (int i = 0; i < mBoxNum ; i++){
			mBoxes[i].clear();
			mBoxes[i].repaint();
		}
		
		int no = 0;
		for (int i = box_start; i < mTabImg[mCurrentTab].getNum() ; i++){
			if (i >= (box_start+mBoxNum))
				break;
			
			Thumbnail obj = mTabImg[mCurrentTab].get(i);
            mBoxes[no].setIcon(obj, obj.getPath(), obj.getImage(), obj.getWidth(), obj.getHeight()) ;
            mBoxes[no++].repaint();
		}
		
		mLinkButton[mCurrentLinkNo].setEnabled(true);
		if (mCurrentLinkNo == 0) {
	   		mPrevButton.setEnabled(true);			
		}
		if (mCurrentLinkNo == mLinkButtonNum-1) {
	   		mNextButton.setEnabled(true);			
		}
		
		mLinkButton[index].setEnabled(false);
		if (index == 0) {
	   		mPrevButton.setEnabled(false);			
		}
		if (index == mLinkButtonNum-1) {
	   		mNextButton.setEnabled(false);			
		}
		mCurrentLinkNo = index;
		
	}
	
}

class TabButton extends JButton {
	
	final static String SELECT_IMG = "img/sozai/tab_base_select.gif";
	final static String NORMAL_IMG = "img/sozai/tab_base.gif";

	private boolean mSelected;
	private String mTitle;
	private Font mFont;
	private FontMetrics mFm;
	private int str_width, str_height;
	

	TabButton(String str) {
		mTitle = str;
		mSelected = false;
		mFont = new Font("Default", Font.BOLD, 12);
		setCursor(new Cursor(Cursor.HAND_CURSOR));
		setBorder(null);
		mFm = getFontMetrics( mFont );
		  
		str_width= mFm.stringWidth( str );
		str_height = mFm.getHeight();
	}
	
	public void setSelect(boolean select) {
		mSelected = select;
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g); 

		ImageIcon icon;
		Image img;
		String img_path;
		Color color;
		if (mSelected) {
			img_path = SELECT_IMG;
			color = Color.white;
			
		} else {
			img_path = NORMAL_IMG;
			color = edenpo.FRAME_COLOR;
		}
		icon = Utility.ImageIcon(this, img_path);
		
		g.drawImage(icon.getImage(), 0, 0, this);
		g.setFont(mFont);
		g.setColor(color);
		g.drawString(mTitle, icon.getIconWidth()/2-str_width/2, icon.getIconHeight()-12);
	}
}

/**
 * 取り置きパネルの作成
 * 
 * @author kagi
 */
class  Torioki extends JPanel  implements ActionListener {
	static final String IMG = "img/sozai/torioki.gif";
	
	private JButton ok_btn, del_btn;
	private ToriokiBox[] boxes = new ToriokiBox[10];
	private int box_num;
	private int current_index;
	private ViewPanel view_panel;
	private SelectPanel mSelectPanel;
	private ToriokiBox mCurrentBox;
	
	ImageIcon icon;
	  
	Torioki(ViewPanel panel, SelectPanel spanel) {
		view_panel = panel;
		mSelectPanel = spanel;
		
		setLayout(null);
			
		Font font = new Font("Default", Font.PLAIN, 12);
		icon = Utility.ImageIcon(this, IMG);	
		
		/**
		 * 決定ボタンの作成
		 */
//		ok_btn = new JButton("決　定");
		ok_btn = new JButton(Utility.ImageIcon(this, "img/sozai/torioki_ok.gif"));
		ok_btn.setDisabledIcon(Utility.ImageIcon(this, "img/sozai/torioki_ok_disable.gif"));
		ok_btn.setRolloverIcon(Utility.ImageIcon(this, "img/sozai/torioki_ok_rollover.gif"));
		ok_btn.setPressedIcon(Utility.ImageIcon(this, "img/sozai/torioki_ok_press.gif"));
		ok_btn.setBounds(255, 178, 85, 22);
		ok_btn.setFocusable(false);
		ok_btn.setBorder(null);
		ok_btn.setFont(font);
		ok_btn.addActionListener(this);
		ok_btn.setEnabled(false);
		add(ok_btn);
		   
		/**
		 * 削除ボタンの作成
		 */
//		del_btn = new JButton("削　除");
		del_btn = new JButton(Utility.ImageIcon(this, "img/sozai/torioki_del.gif"));
		del_btn.setDisabledIcon(Utility.ImageIcon(this, "img/sozai/torioki_del_disable.gif"));
		del_btn.setRolloverIcon(Utility.ImageIcon(this, "img/sozai/torioki_del_rollover.gif"));
		del_btn.setPressedIcon(Utility.ImageIcon(this, "img/sozai/torioki_del_press.gif"));
//		del_btn.setBounds(259, 228, 74, 20);
		del_btn.setBounds(255, 207, 85, 22);
		del_btn.setBorder(null);
		del_btn.setFocusable(false);
		del_btn.setFont(font);
		del_btn.addActionListener(this);
		del_btn.setEnabled(false);
		add(del_btn);
		   
		box_num = 0;
		mCurrentBox = null;
	}
	public void addImage(BoxPanel box) {

		boxes[box_num] = new ToriokiBox(this, view_panel);
		boxes[box_num].setIcon(box);
		
		add(boxes[box_num]);
		
		setCurrentBox(boxes[box_num]);
		
		box_num++;
		reLayout();
		
	
	}
	
	public int getBoxNum() {
		return box_num;		
	}
	
	/**
	 * 現在を取り置きBoxの枠の色を変える
	 *  
	 * @param box
	 */
	public void setCurrentBox(ToriokiBox box) {
		if (mCurrentBox != null) {
			mCurrentBox.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
			mCurrentBox.repaint();			
		}
		mCurrentBox = box;
		mCurrentBox.setBorder(BorderFactory.createLineBorder(Color.blue, 2));
		mCurrentBox.repaint();			
	}
	
	public ToriokiBox getCurrentBox() {
		return mCurrentBox;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g); 

		g.drawImage(icon.getImage(), 0, 0, this);
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == ok_btn) {
			if (mCurrentBox != null) {
//				BufferedImage img = view_panel.getImagePanel().getImage();
//				BufferedImage img = mCurrentBox.getOriginalImage();
				OpeAddImage ope = new OpeAddImage(mCurrentBox.getThumbnail());
				edenpo.Stack.push(ope);
				
				deleteBox(mCurrentBox);
			}
			return;
		}
		if (e.getSource() == del_btn) {
			if (mCurrentBox == null)
				return;
			
			deleteBox(mCurrentBox);
			
			return;
		}		
	}
	
	/**
	 * 指定した取り置きBoxを削除する
	 * 
	 * @param box
	 */
	private void deleteBox(ToriokiBox box) {
		int i, j, current = 0;
		for (i = 0; i < box_num; i++) {
			if (box == boxes[i]) {
				current = i;
				break;
			}
		}
		
		remove(boxes[i]);
		mCurrentBox = null;
		
		box = null;
		if (i != box_num-1) {
			for (j = current; j < box_num-1; j++) {
				boxes[j] = boxes[j+1];
			}
		}
		boxes[box_num-1] = null;
		
		box_num--;
		
		reLayout();
		
		// 選択パネルが選択状態にあればビューは初期化しない
		if (mSelectPanel.getCurrentBox() == null) {
			view_panel.init();
		}
		
	}
	
	/**
	 * 取り置きBoxを再配置する
	 */
	public void reLayout() {
		if (box_num == 0) {	
			ok_btn.setEnabled(false);
			del_btn.setEnabled(false);
		} else {
			ok_btn.setEnabled(true);
			del_btn.setEnabled(true);			
		}
		
		int x = 9;
		int y = 42;
		for (int i = 0; i < box_num; i++) {
			if (i == 5) {
				x = 10  -1;
				y = 129 - 22;
			}
			boxes[i].setBounds(x, y, 64, 62);
			x += 67;
		}
		repaint();
	}
}

class ToriokiBox extends JPanel implements MouseListener {
	
	private String mFilePath;
	
	private Image mImg;
	private BufferedImage mOrgImg;
	private int mImgWidth, mImgHeight;
	
	private final static int PANEL_WIDTH = 64;
	private final static int PANEL_HEIGHT = 62;
	private final static int IMG_MAX = 56;
	
	private ViewPanel mViewPanel;
	private Torioki mToriokiPanel;
	
	private Thumbnail mThumbnail;
	
	ToriokiBox(Torioki panel1, ViewPanel panel2) {
		mToriokiPanel = panel1;
		mViewPanel = panel2;
		
		setLayout(null);
		setBorder(BorderFactory.createLineBorder(Color.gray, 1));
		setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT)); 	
		addMouseListener(this);
	}
	
	void setIcon(BoxPanel box) {
		// 2007/12/30 移動速度向上
		mThumbnail = box.getThumbnail();
		
		mFilePath = mThumbnail.getPath();
		mImg = mThumbnail.getImage();	
		mImgWidth = mThumbnail.getWidth();
		mImgHeight = mThumbnail.getHeight();
		
	}
	
	public BufferedImage getOriginalImage() {
		return mThumbnail.getOriginalImage();
	}
	
	public void clear(){
		mImg = null;
	}
	
	public String getFilePath() {
		return mFilePath;
	}
	
	public Thumbnail getThumbnail() {
		return mThumbnail;
	}
	
    protected void paintComponent(Graphics g) {
     	
    	super.paintComponent(g);
    	

       if (mImg != null) {
    	   int width = getWidth();
    	   int height = getHeight();
    	   
    	   g.setColor(Color.white);
    	   g.fillRect(2, 2, width-4, height-4);
   	   
    	   g.drawImage(mImg, width/2 - mImgWidth/2, height/2 - mImgHeight/2, this);
        }
               
    }    
	public void mouseClicked(MouseEvent e) {

	}
	public void mouseEntered(MouseEvent e) {
	}
	public void mouseExited(MouseEvent e) { 
	}
	public void mousePressed(MouseEvent e) { 
		mToriokiPanel.setCurrentBox(this);
				
		mViewPanel.setImage(mThumbnail);
		mViewPanel.repaint();
		
	}
	public void mouseReleased(MouseEvent e) { }

}

class ViewPanel extends JPanel implements ActionListener, ComponentListener {

	// イメージ画像表示欄の下と　倍率と拡大/縮小ボタン　の欄の高さ
	final static int VIEW_TEXT_HEIGHT = 30;
	
	final static Color BORDER_COLOR = new Color(255, 160, 161);
	
	private JScrollPane scrollpane;
	private ImageViewPanel gpanel;
	private JPanel gpanel2;
	private JButton scale_btn1, scale_btn2;
	private JLabel scale_label;
	private JButton close_btn;
	private boolean mClose;
	private int mScale;
	
	ViewPanel(Font font, boolean close) {
		setLayout(null);
		setBackground(Color.white);
		addComponentListener(this);
		
		gpanel = new ImageViewPanel();
		scrollpane = new JScrollPane(gpanel);
//		scrollpane.setBorder(null);
		scrollpane.setBorder(new LineBorder(BORDER_COLOR, 2,true ));
		add(scrollpane);
		gpanel.setScrollPane(scrollpane);
		
		gpanel2 = new JPanel();
		gpanel2.setBorder(new LineBorder(BORDER_COLOR, 2,true ));
		gpanel2.setBackground(edenpo.BACK_COLOR2);
		gpanel2.setLayout(null);
		add(gpanel2, BorderLayout.CENTER);
		
		scale_label = new JLabel("現在の倍率:100%");
		scale_label.setFont(font);
		gpanel2.add(scale_label);
				
		scale_btn1 = new JButton("拡大");
		scale_btn1.setFont(font);
		scale_btn1.addActionListener(this);
		scale_btn1.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
		scale_btn1.setFocusPainted(false);
		scale_btn1.setEnabled(false);
		gpanel2.add(scale_btn1);
		scale_btn2 = new JButton("縮小");
		scale_btn2.setFont(font);
		scale_btn2.addActionListener(this);
		scale_btn2.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
		scale_btn2.setFocusPainted(false);
		scale_btn2.setEnabled(false);
		gpanel2.add(scale_btn2);
		mScale = 100;
		
		gpanel.setScaleButton(scale_btn1, scale_btn2);
		
		mClose = false;
/*
		if (close) {
			// 「このウィンドウを閉じる」ボタンの作成
			close_btn = new JButton(Utility.ImageIcon(this, "img/close.gif"));
			close_btn.setRolloverIcon(Utility.ImageIcon(this, "img/close_rollover.gif"));
			close_btn.setPressedIcon(Utility.ImageIcon(this, "img/close_press.gif"));
			close_btn.setBorder(null);
			close_btn.setBounds(0, 0, 129,21);
			close_btn.addActionListener(this);
			add(close_btn);
			mClose = true;
		}
*/
	}
	public JButton getCloseButton() {
		return close_btn;
	}
	
	public void init() {
		setImage(null);
		
		gpanel.clear();
		gpanel.resizeComponent();
		
	}
/*
	public void setImage(BufferedImage img) {
		mScale = 100;
		gpanel.setImage(img);
		
		if (img == null) {
			scale_btn1.setEnabled(false);
			scale_btn2.setEnabled(false);
	 		scale_label.setText("現在表示倍率:"+mScale+"%");	
			gpanel.setScale(mScale);
	 		scrollpane.revalidate();
		
		} else {
			setScaleLabel();
		}
		
	}
*/
	public void setImage(Thumbnail thum) {
		mScale = 100;
		
		if (thum == null) {
			scale_btn1.setEnabled(false);
			scale_btn2.setEnabled(false);
	 		scale_label.setText("現在表示倍率:"+mScale+"%");	
			gpanel.setScale(mScale);
	 		scrollpane.revalidate();
		
		} else {
			gpanel.setImage(thum);
			
			setScaleLabel();
		}
		
	}
	
	
	
	public ImageViewPanel getImagePanel() {
		return gpanel;
	}
	
	public JScrollPane getScrollPane() {
		return scrollpane;
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
		if (mClose) {
			Sozai.DIALOG.setVisible(false);
			return;
		}
	}

	public void setScaleLabel() {
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
	
	public void componentHidden(ComponentEvent e) {}
	public void componentMoved(ComponentEvent e) {}
	public void componentResized(ComponentEvent e) {
		revalidate();
			
		int width = getWidth();
		int height = getHeight();
			
		int width2 = 0;
		int height2 = 0;
		
		if (mClose) {
			width2 = close_btn.getWidth();
			height2 = close_btn.getHeight();
			close_btn.setBounds(width - width2 - 5, height - height2 - 2, width2, height2);		
		}
		gpanel2.setBounds(5, height-height2-5-31, width-10, 31);
		scale_btn1.setBounds(width - 90, 6, 38, 21);
		scale_btn2.setBounds(width - 52, 6, 38, 21);
		scrollpane.setBounds(5, 5, width-10, height - height2 - 31 - 5);
			
		gpanel.setBounds(0, 0, scrollpane.getWidth(), scrollpane.getHeight());
		scrollpane.revalidate();  	// Panelを作り直す

		scale_label.setBounds(10, 6, 150, 21);
		scale_btn1.setBounds(width-4-85, 8, 35, 20);
		scale_btn2.setBounds(width-4-45, 8, 35, 20);
			
			
		return;
			
	}
	
	public void componentShown(ComponentEvent e) {
	}
	
}



class LinkButton extends JButton {
	private String mStr;
//	private BufferedImage mImg;
	
	LinkButton(String str, boolean current, boolean under) {		
		mStr = str;
		Font font = new Font("Default", Font.BOLD, 12);
		FontMetrics fm = getFontMetrics( font );
		  
		int width = fm.stringWidth( str );
		int height = fm.getHeight();

		BufferedImage img;
		
		img=  new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics g = img.getGraphics();
		g.setColor(edenpo.BACK_COLOR2);
		g.fillRect(0, 0, width, height);
   		g.setColor(Color.blue);
		g.setFont(font);
		g.drawString(str, 0, height-2);
//		g.drawRect(0, 0, width-1, height-1);
		if (under) {
			g.drawLine(0,height-1,width, height-1);
		}
		ImageIcon icon = new ImageIcon(img);
		setIcon(icon);
		g.dispose();
		img.flush();
		
		img =  new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		g = img.getGraphics();
		g.setFont(font);
		g.setColor(edenpo.BACK_COLOR2);
		g.fillRect(0, 0, width, height);
   		g.setColor(Color.red);
		g.drawString(str, 0, height-2);
		setRolloverIcon(new ImageIcon(img));
		g.dispose();
		img.flush();
		
		img =  new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		g = img.getGraphics();
		g.setFont(font);
		g.setColor(edenpo.BACK_COLOR2);
		g.fillRect(0, 0, width, height);
   		g.setColor(Color.gray);
		g.drawString(str, 0, height-2);
		
		setDisabledIcon(new ImageIcon(img));
		setPressedIcon(new ImageIcon(img));
		
		g.dispose();
		img.flush();
		
		setBorder(null);
		
		if (current) {
			setEnabled(false);
		}
	}
	
}

/**
 * 選択された画像ファイルを　表示するパネル
 *  お客様素材ではドラックで領域を選択できる
  */
class ImageViewPanel extends JPanel implements MouseListener, MouseMotionListener   {
	final private int SELECT_BOX = 6;
	   
	final private String DEFAULT_MESSAGE = "選択した素材を拡大表示します";
	
	private BufferedImage mImg;
	private double mX, mY, mWidth, mHeight;
	private double mScale, mPrevScale;
	
	private JScrollPane scroll_pane;
	private boolean mTrim;
	private double mTrimX, mTrimY, mTrimWidth, mTrimHeight, mClickSize; 
	private int mSelectNo;
	private int start_x, start_y;
	
	private JButton mScaleBtn1, mScaleBtn2;
	private Thumbnail mThumbnail;
	
	ImageViewPanel() {
		
    	setLayout(null);
       	setBackground(Color.white);
       	
//       	setBorder(BorderFactory.createLineBorder(Color.black));
       	
       	setPreferredSize(new Dimension(getWidth(), getHeight()));
    	revalidate();  	// Panelを作り直す
    	
    	mImg = null;
    	mScale = mPrevScale = 1.0f;
    	mX = mY = 0;
    	
    	mTrim = false;
    	mTrimX = mTrimY = mTrimWidth = mTrimHeight = 0;
    	
    	mThumbnail = null;
	}
	
	
	public void setScrollPane(JScrollPane panel) {
		scroll_pane = panel;
	}
	
	public void setScale(int scale) {
		mScale = scale / 100.0;        
        resizeComponent();	
        mPrevScale = mScale;
        
		repaint();
	}
	public void setScaleButton(JButton scale_btn1, JButton scale_btn2) {
		mScaleBtn1 = scale_btn1;
		mScaleBtn2 = scale_btn2;
	}
	public void setImage(String path) {
		
		if (path != null) {
			try{
				mImg = ImageIO.read(new File(path));
		  
			}
			catch(Exception ex) {
				JOptionPane.showMessageDialog(this, Message.READ_IMG_ERROR, "Error", JOptionPane.ERROR_MESSAGE);
				mImg = null;
				return;
			}
			if (mImg == null) {
				JOptionPane.showMessageDialog(this, Message.READ_IMG_ERROR, "Error", JOptionPane.ERROR_MESSAGE);
				return;
				
			}
		} else {
			mImg = null;
	     	setPreferredSize(new Dimension(scroll_pane.getWidth()-10, scroll_pane.getHeight()-10));
		}
       	mScale = mPrevScale = 1.0f;
    	mX = mY = 0.0;
    	
    	mTrim = false;
    	mTrimX = mTrimY = mTrimWidth = mTrimHeight = 0.0;
      
        resizeComponent();		
	}
	
	// ユーザー素材はここには来ない
	// 2007/12/30 移動速度向上
	public void setImage(Thumbnail thum) {
		mThumbnail = thum;
		if (thum == null) 
			return;

		if (mThumbnail.checkOriginalImage()) {
			mImg = mThumbnail.getOriginalImage();
		} else {
			mImg = Sozai.getImage(mThumbnail.getPath());		
		}
		mScale = mPrevScale = 1.0f;
    	mX = mY = 0.0;
    	
    	mTrim = false;
    	mTrimX = mTrimY = mTrimWidth = mTrimHeight = 0.0;
      
        resizeComponent();		
	}
	
	
	/**
	 * 画像データを返す
	 * 　トリミングが設定されている場合は、画像をトリミングして返す
	 * @return
	 */
	public BufferedImage getImage() {
		if (mTrim) {
			// 選択枠から　Imageの実際のサイズの枠に変換する
			double ratio = (double)mImg.getWidth() / (double)mWidth;
			
			ImageFilter fl = new CropImageFilter((int)((mTrimX-mX) * ratio) , (int)((mTrimY-mY) * ratio) , (int)(mTrimWidth * ratio) , (int)(mTrimHeight * ratio));
			FilteredImageSource fis = new FilteredImageSource(mImg.getSource() , fl);
			Image img = createImage(fis);		
			
		    //  ImageをBufferedImageに変換する処理
			mImg = new BufferedImage(img.getWidth(null),img.getHeight(null),BufferedImage.TYPE_INT_ARGB);  // BufferedImageオブジェクトを作成
	        Graphics g = mImg.createGraphics();  // Graphicsの取得
	        g.drawImage(img, 0, 0, null);  // BufferedImageに描画させる
	        
	        // 2007/12/31
	        g.dispose();
	        img.flush();
		}
		return mImg;
	}
	
	/**
	 * 単純に現在の画像を返す
	 * @return
	 */
	public BufferedImage getImage2() {
		return mImg;
	}

	public void clear(){
		mImg = null;
		
    	mScale = mPrevScale = 1.0f;
    	mX = mY = 0;
    	
    	mTrim = false;
    	mTrimX = mTrimY = mTrimWidth = mTrimHeight = 0;
    	
    	mThumbnail = null;
    	
//System.out.println("ImageViewPanel width="+scroll_pane.getWidth()+"  height="+scroll_pane.getHeight());
	}
	
	/**
	 * 親のスクロールペインのサイズが変更したときこのメソッドが呼ばれる
	 *
	 */
	public void resizeComponent() {
		if (mImg == null) {
	        // スクロールペイン中のパネルのサイズを変更する
	      	setPreferredSize(new Dimension(scroll_pane.getWidth()-10, scroll_pane.getHeight()-10));
			revalidate();  	// Panelを作り直す		
			return;
		}
		if (mScaleBtn1 != null && mScaleBtn2 != null) {
			mScaleBtn1.setEnabled(true);
			mScaleBtn2.setEnabled(true);
		}
		
        int width = scroll_pane.getWidth();
        int height = scroll_pane.getHeight();
//System.out.println("resizeComponent width="+width+"  height="+height);

        mWidth = mImg.getWidth() * mScale;
        mHeight = mImg.getHeight() * mScale;
        
        double dx, dy;
        dx = dy = 0;
        if (mTrim) {
         	dx = mTrimX - mX;
        	dy = mTrimY - mY;
        }
        double new_width = width;
        double new_height = height;
        
        mX = mY = 0;
        if (width > mWidth) {
        	mX = width/2.0 - mWidth/2.0;
        	new_width -= 8;
           	new_height -= 8;
        } else {
        	new_width = mWidth;
        }
        if (height > mHeight) {
        	mY = height/2.0 - mHeight/2.0;
        } else {
        	new_height = mHeight;
        }
        if (mTrim) {
        	mTrimX = mX + (dx * (mScale / mPrevScale));
        	mTrimY = mY + (dy * (mScale / mPrevScale));
        	mTrimWidth = (mTrimWidth * (mScale / mPrevScale));
        	mTrimHeight = (mTrimHeight * (mScale / mPrevScale));
        }
        
        // スクロールペイン中のパネルのサイズを変更する
      	setPreferredSize(new Dimension((int)new_width, (int)new_height));
		revalidate();  	// Panelを作り直す		
	}
	
	/**
	 * トリミングスタート
	 *　mTrimフラグをセットし、マウスイベントを取れるようにする
	 */
	public void startTrim() {
		if (mTrim) {
			mTrim = false;
			removeMouseListener(this);
			removeMouseMotionListener(this);
		} else {
			addMouseListener(this);
			addMouseMotionListener(this);
			mTrim = true;
//			if (mTrimWidth == 0) {
				mTrimX = mX;
				mTrimY = mY;
				mTrimWidth = mWidth;
				mTrimHeight = mHeight;
				mClickSize = SELECT_BOX * mScale;
//			}
		}
		repaint();
	}
	
    protected void paintComponent(Graphics g) {
     	
    	super.paintComponent(g);
    	
       if (mImg != null) {
 //System.out.println("ViewGraph mX="+mX+"  mY="+mY);
 
    	   GPanel.drawImageEx(g, mImg, mX, mY, mWidth, mHeight, this);
        } else {
        	/**
        	 * 画像が無いとき、コメントを表示
        	 */
    		Font font = new Font("Default", Font.PLAIN, 14);
    		g.setColor(Color.LIGHT_GRAY);
    		g.setFont(font);
    		FontMetrics fm = getFontMetrics( font );
 		    int width = fm.stringWidth( DEFAULT_MESSAGE );
    		//int height = fm.getHeight();
    		g.drawString(DEFAULT_MESSAGE, getWidth()/2 - width/2, getHeight()/2);
        }
       
       if (mTrim) {
       		g.setColor(Color.blue);
       		// 文字は上下左右にサイズ変更Boxが付く
       		GPanel.fillRectEx(g, mTrimX-mClickSize/2 , mTrimY-mClickSize/2 , mClickSize, mClickSize);
       		GPanel.fillRectEx(g, (mTrimX + mTrimWidth/2)-mClickSize/2 , mTrimY-mClickSize/2 , mClickSize, mClickSize);
       		GPanel.fillRectEx(g, mTrimX+mTrimWidth-mClickSize/2 , mTrimY-mClickSize/2 , mClickSize, mClickSize);
       		GPanel.fillRectEx(g, mTrimX-mClickSize/2 , mTrimY+mTrimHeight/2-mClickSize/2 , mClickSize, mClickSize);
       		GPanel.fillRectEx(g, mTrimX+mTrimWidth-mClickSize/2 , mTrimY+mTrimHeight/2-mClickSize/2 , mClickSize, mClickSize);
       		GPanel.fillRectEx(g, mTrimX-mClickSize/2 , mTrimY+mTrimHeight-mClickSize/2 , mClickSize, mClickSize);
       		GPanel.fillRectEx(g, (mTrimX + mTrimWidth/2)-mClickSize/2 , mTrimY+mTrimHeight-mClickSize/2 , mClickSize, mClickSize);
       		GPanel.fillRectEx(g, mTrimX+mTrimWidth-mClickSize/2 , mTrimY+mTrimHeight-mClickSize/2 , mClickSize, mClickSize);				
    	   
            Graphics2D g2 = (Graphics2D)g;
            g2.setStroke(new BasicStroke(1.0f));
            GPanel.drawRectEx(g, mTrimX, mTrimY, mTrimWidth, mTrimHeight);      		
       }              
    }  
    
    
	/**
	 * 選択場所(mSelectNo)により
	 *  選択領域を移動したり、サイズ変更する
	 *  
	 * @param x  x方向の移動量
	 * @param y　y方向の移動量
	 */
	public void moveSelect(int x, int y) {
		double dx, dy, dwidth, dheight;
		
		dx = mTrimX;
		dy = mTrimY;
		dwidth = mTrimWidth;
		dheight = mTrimHeight;
		
		switch(mSelectNo) {
		case 1:	 // 移動
			dx += x;
			dy += y;
			break;
		case 2: // 左上ドラック
			dx += x;
			dy += y;
			dwidth -= x;
			dheight -= y;
			break;
		case 3: // 右上ドラック
			dy += y;
			dwidth += x;
			dheight -= y;
			break;
		case 4: // 左下ドラック
			dx += x;
			dwidth -= x;
			dheight += y;
			break;
		case 5: // 右下ドラック
			dwidth += x;
			dheight += y;
			break;
		case 6: //　上中ドラック
			dy += y;
			dheight -= y;
			break;
		case 7: //　左中ドラック
			dx += x;
			dwidth -= x;
			break;
		case 8: // 右中ドラック
			dwidth += x;
			break;
		case 9: // 下中ドラック
			dheight += y;
			break;
		}
		
		/**
		 * トリミングの枠が画像からはみ出た時の処理
		 */
		if (dx > mX + mWidth) {
			return;
		}
		if (dy > mY + mHeight) {
			return;
		}
		if (dx + dwidth < mX ) {
			return;
		}
		if (dy + dheight < mY) {
			return;
		}
		mTrimX = dx;
		mTrimY = dy;
		mTrimWidth  = dwidth;
		mTrimHeight  = dheight;
		
		if (mTrimX  < mX) {
			mTrimX = mX;
		}
		if (mSelectNo == 1) {
			if (mTrimX+mTrimWidth > mX + mWidth) {
				mTrimX = mX + mWidth - mTrimWidth;				
			}
		
		} else {
			if (mTrimX+mTrimWidth > mX + mWidth) {
				mTrimWidth = mX + mWidth - mTrimX;				
			}
		}
		
		if (mTrimY < mY) {
			mTrimY = mY;
		}
		if (mSelectNo == 1) {
			if (mTrimY+mTrimHeight > mY + mHeight) {
				mTrimY = mY + mHeight - mTrimHeight;				
			}
		
		} else {
			if (mTrimY+mTrimHeight > mY + mHeight) {
				mTrimHeight = mY + mHeight - mTrimY;
			}
		}
		
		repaint();
	}

	/**
	 * 現在の位置によってマウスカーソルを変える
	 * 
	 * @param x
	 * @param y
	 */
    public void setCursor(int x, int y) {
		int no = HitTestSelectBox(x, y);
		if (no > 0) {
			switch(no) {
			case 1:
				setCursor(new Cursor(Cursor.HAND_CURSOR)); 
				return;
			case 2:  // 左上
				setCursor(new Cursor(Cursor.NW_RESIZE_CURSOR));
				return;
			case 3:  // 右上
				 setCursor(new Cursor(Cursor.NE_RESIZE_CURSOR));
				 return;
			case 4: // 左下
				setCursor(new Cursor(Cursor.NE_RESIZE_CURSOR));
				return;
			case 5: // 右下
				setCursor(new Cursor(Cursor.NW_RESIZE_CURSOR));
				return;
			case 6: // 中上
				setCursor(new Cursor(Cursor.S_RESIZE_CURSOR)); 
				return;
			case 7: // 左中
				setCursor(new Cursor(Cursor.W_RESIZE_CURSOR));
				return;
			case 8: // 右中
				setCursor(new Cursor(Cursor.W_RESIZE_CURSOR));
				return;
			case 9: // 中下
				setCursor(new Cursor(Cursor.S_RESIZE_CURSOR)); 
				return;
			}			
		}  		
		setCursor(new Cursor(Cursor.DEFAULT_CURSOR)); 
    }
    
 	/**
     * 選択時に端に表示される■の位置にあるかどうか
	 */
    public int HitTestSelectBox(int in_x, int in_y) {
   		int x, y, width, height;
   		x = (int)mTrimX;
   		y = (int)mTrimY;
   		width = (int)mTrimWidth;
   		height = (int)mTrimHeight;
    		
     	// 左上の選択Box
      	if (x-mClickSize/2 <= in_x && in_x <= (x+mClickSize/2) && y-mClickSize/2 <= in_y && in_y <= (y+mClickSize/2))
        	return 2;

 
      	// 右上の選択Box
      	if (x+width-mClickSize/2 <= in_x && in_x <= (x+width+mClickSize/2) && y-mClickSize/2 <= in_y && in_y <= (y+mClickSize/2))
        	return 3;


      	// 左下の選択Box
      	if (x-mClickSize/2 <= in_x && in_x <= (x+mClickSize/2) && y+height-mClickSize/2 <= in_y && in_y <= (y+height+mClickSize/2))
        	return 4;

      	// 右下の選択Box
      	if (x+width-mClickSize/2 <= in_x && in_x <= (x+width+mClickSize/2) && y+height-mClickSize/2 <= in_y && in_y <= (y+height+mClickSize/2))
        	return 5;
      	
      	 // 中上の選択Box
      	if (x+width/2-mClickSize/2 <= in_x && in_x <= (x+width/2+mClickSize/2) && y-mClickSize/2 <= y && in_y <= (y+mClickSize/2))
        	return 6;
      		
     	// 左中の選択Box
      	if (x-mClickSize/2 <= in_x && in_x <= (x) && y+height/2-mClickSize/2 <= in_y && in_y <= (y+height/2+mClickSize/2))
        	return 7;

      	// 右中の選択Box
      	if (x+width-mClickSize/2 <= in_x && in_x <= (x+width+mClickSize/2) && y+height/2-mClickSize/2 <= in_y && in_y <= (y+height/2+mClickSize/2))
        	return 8;
      		
     	// 中下の選択Box
      	if (x+width/2-mClickSize/2 <= in_x && in_x <= (x+width/2+mClickSize/2) && y+height-mClickSize/2 <= in_y && in_y <= (y+height+mClickSize/2))
        	return 9;

      	if (x <= in_x && y <= in_y && x+width >= in_x && y+height >= in_y)
      		return 1;
      	
      	return 0;
    }
   
	public void mouseClicked(MouseEvent e) {
	}
	public void mouseEntered(MouseEvent e) {
	}
	public void mouseExited(MouseEvent e) { 
	}
	public void mousePressed(MouseEvent e) {
		if (mTrim) {
			mSelectNo = HitTestSelectBox(e.getX(), e.getY());
			if (mSelectNo > 0) {
				start_x = e.getX();
				start_y = e.getY();
			}
		}
	}
	public void mouseReleased(MouseEvent e) { 
		if (mTrim && mSelectNo > 0) {
			if (mTrimWidth < 0) {
				mTrimX = mTrimX + mTrimWidth;
				mTrimWidth *= -1.0;
			}
			if (mTrimHeight < 0) {
				mTrimY = mTrimY + mTrimHeight;
				mTrimHeight *= -1.0;
			}
		}
	}
	public void mouseMoved(MouseEvent e) {
		if (mTrim) {
			setCursor(e.getX(), e.getY());
		}
	}

	 public void mouseDragged(MouseEvent e) {
		 if (mTrim && mSelectNo > 0) {
			 moveSelect(e.getX()-start_x, e.getY()-start_y);
			 repaint();
			 
			 start_x = e.getX();
			 start_y = e.getY();
		 }
	 }
}

/**
 * 画像ファイル選択パネルの中の個々の画像パネル
 * 
  */
class BoxPanel extends JPanel implements MouseListener {
	
	private String mFilePath;
	
	private Image mImg;
	private BufferedImage mOrgImg;
	private int mImgWidth, mImgHeight;
	
	private final static int IMG_WIDTH_MAX = 64;
	private final static int IMG_HEIGHT_MAX = 60;
	
	private ViewPanel mViewPanel;
	private SelectPanel mSelectPanel;
	
	private Thumbnail mThumbnail;
	public int mNo;
	
	BoxPanel(ViewPanel panel, SelectPanel panel2, int no) {
		mViewPanel = panel;
		mSelectPanel = panel2;
		mNo = no;
		
		addMouseListener(this);
		
		mImg = null;
	}
	
	void setIcon(String path) {
		mFilePath = path;
		BufferedImage bimg = null;
		
		bimg = Sozai.getImage(path);
		mOrgImg = bimg;
		 		
	
        if (bimg.getHeight() < bimg.getWidth()) {
        	mImgWidth = IMG_WIDTH_MAX;
        	mImgHeight = (int)(((double)bimg.getHeight() / bimg.getWidth()) * IMG_WIDTH_MAX);
        } else {
        	mImgHeight = IMG_HEIGHT_MAX;
        	mImgWidth = (int)(((double)bimg.getWidth() / bimg.getWidth()) * IMG_HEIGHT_MAX);
        }
  

		// 2007/12/31 createImage が大量のメモリを消費する為BufferedImageに変更
/**		
	    
        ImageFilter fl;
	 	fl = new ReplicateScaleFilter(mImgWidth, mImgHeight);
//		fl = new AreaAveragingScaleFilter(mImgWidth , mImgHeight);			// こっちの方が鮮明（だが少し遅い）

		FilteredImageSource fis = new FilteredImageSource(bimg.getSource() , fl);
		mImg = createImage(fis);
**/
		
	    mImg =  Sozai.getIconImage(bimg, mImgWidth, mImgHeight);	

////////////////////////
		
///////////////////////
	
	}
	
	// 2007/12/30 移動速度向上
	void setIcon(Thumbnail obj, String path, Image img, int width, int height){
		mImg = img;
		mImgWidth = width;
		mImgHeight = height;
		mFilePath = path;
		mThumbnail = obj;
	}

	public Thumbnail getThumbnail() {
		return mThumbnail;
	}
	
	public void clear(){
		mImg = null;
	}
	
	public String getPath() {
		return mFilePath;
	}
	
	public Image getIconImage() {
		return mImg;
	}
	
	// 2007/12/30 移動速度向上
	public BufferedImage getOriginalImage() {
		return mThumbnail.getOriginalImage();
	}
	
	public int getIconWidth() {
		return mImgWidth;
	}
	
	public int getIconHeight() {
		return mImgHeight;
	}
    protected void paintComponent(Graphics g) {
     	
    	super.paintComponent(g);
    	

       if (mImg != null) {
    		g.drawImage(mImg, getWidth()/2 - mImgWidth/2, getHeight()/2 - mImgHeight/2, this);
//     		g.drawImage(mImg, 0, 0, this);
        }
               
    }    
	public void mouseClicked(MouseEvent e) {

	}
	public void mouseEntered(MouseEvent e) {
	}
	public void mouseExited(MouseEvent e) { 
	}
	public void mousePressed(MouseEvent e) { 
		if (mImg == null)
			return;
		
		mSelectPanel.setCurrentBox(this);

//System.out.println("mViewPanel="+mViewPanel+"  mOrgImg="+mOrgImg);
		// 2007/12/30 移動速度向上
		mThumbnail.getOriginalImage();		// ボックスをクリックしたタイミングで画像をロード
		mViewPanel.setImage(mThumbnail);
		mViewPanel.repaint();
	}
	public void mouseReleased(MouseEvent e) { }

}

