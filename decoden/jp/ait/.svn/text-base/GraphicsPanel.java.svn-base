package jp.ait;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import java.util.*;

import java.nio.channels.*;
import com.sun.image.codec.jpeg.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;



class GraphicsPanel extends JPanel implements ActionListener, ComponentListener  {
   
	/**
	 * B5 182mm×257mm   A5 148×210
	 */
	/*
	 	B5を数値で表記すると
		B5＝18.2×25.7cm＝7.16535×10.11811インチ
		になります。（1インチ＝2.54cmで換算）

		「解像度」というのはビットマップグラフィック（ラスタグラフィック）のような、ドットに分解したデータの場合は必須の要件で、
		単位として「dpi」という単位を使います。
		「dpi」とは「dot/inch」＝「ドット・パー・インチ」の略で、
		意味としては「1インチをいくつで割るか」あるいは「1インチにいくつのドットを割り当てるか」ということを数値として表すものです。
		ですので例えば1インチ角（2.54cm角）の正方形があったとして、
		解像度をでイスプレイ表示用の72dpiにした場合は72×72ピクセル＝総画素数5184ピクセルになりますが、
		インクジェットプリンタなどで印刷する場合だと300dpi程度必要ですから、300×300ピクセル＝総画素数90000ピクセルになります。

		ディスプレイ表示なら72dpiなら、
		B5なら7.16535×10.11811インチに72をかけます。
		＝約516×約729ピクセルになりますね。（小数点以下四捨五入で計算）
		

		これがインクジェットプリンタ用だとすると300dpiとして、
		B5なら7.16535×10.11811インチに300をかけます。
		＝約2150×約3035ピクセルになりますね。（小数点以下四捨五入で計算）
	 */
	
	
 	/**
	 * 縦書き　ドキュメントサイズの初期値
	 */
//    final static int DEFAULT_VDOC_WIDTH = 420;
//    final static int DEFAULT_VDOC_HEIGHT = 595;
    static int DEFAULT_VDOC_WIDTH;
    static int DEFAULT_VDOC_HEIGHT;
  	/**
	 * 横書き　ドキュメントサイズの初期値
	 */
    static int DEFAULT_HDOC_WIDTH;
    static int DEFAULT_HDOC_HEIGHT;
    
    
    /**
     * 余白は 1.5cm
     */
    static int YOHAKU ;
	
	/**
	 * タイトル
	 */
    private JLabel mTitle;
    
    /**
     * 倍率のラベル
     */
    private JLabel mScaleLabel;
    
    /**
     * 拡大/縮小ボタン
     */
    private JButton mZoomInBtn, mZoomOutBtn;
    
    /**
     * グリッドボタン
     */
    private JButton mGridBtn;
      
    /**
     * 画像描画パネル
     */
    private GPanel mGPanel;
    
    /**
     * 倍率
     */
    private int mScale;
   
    /**
     * グリッド番号
     */
    private int mGrid;
    
	private Color mBackColor = new Color(167,230,167);
	
	protected int gwidth, gheight;
	
	private JScrollPane mScroll;
  
	public static int DPI ;
	
	
	/**
	 * 差出人の入力枠サイズ  (幅：9cm, 高さ：3.5cm)
	 */
    static int SASHIDASHI_WIDTH;
    static int SASHIDASHI_HEIGHT;


    GraphicsPanel() {
    	mScale = 100;	// 倍率の初期値は　100 %
    	mGrid = 0;		// グリッド無し
    	
     	/**
    	 * 画面の解像度を得で　用紙サイズと余白を計算する
    	 * A5で計算 (148x210)
    	 */
    	DPI = getToolkit().getScreenResolution();
    	DEFAULT_VDOC_WIDTH = (int)(14.8 / 2.54  * DPI);
    	DEFAULT_VDOC_HEIGHT = (int)(21.0 / 2.54 * DPI);
    	DEFAULT_HDOC_WIDTH = DEFAULT_VDOC_HEIGHT;
    	DEFAULT_HDOC_HEIGHT = DEFAULT_VDOC_WIDTH;
    	YOHAKU = (int)(1.5 / 2.54 * DPI);
	
    	SASHIDASHI_WIDTH = (int)(9.0 / 2.54 * DPI);
    	SASHIDASHI_HEIGHT = (int)(3.5 / 2.54 * DPI);
    	
  //  	setBorder(new BevelBorder(BevelBorder.RAISED));
    	setBackground(edenpo.BACK_COLOR);
		setBorder(new EtchedBorder(EtchedBorder.RAISED));
		setLayout(new BorderLayout());
  	
    	JPanel title_btn = new JPanel();	
    	title_btn.setLayout(new BorderLayout());
    	title_btn.setBackground(edenpo.BACK_COLOR);
    	
    	Insets insets = new Insets(0, 1, 0, 1);
 

    	/**
    	 * タイトルを作成
    	 */
    	mTitle = new JLabel("作成中のメニュー : 名称未設定");
    	mTitle.setHorizontalAlignment(SwingConstants.CENTER);
    	mTitle.setForeground(Color.white);
    	mTitle.setFont(edenpo.Menu_Font);
 		title_btn.add(mTitle, BorderLayout.NORTH);
    	title_btn.setBackground(edenpo.BACK_COLOR);
 
    	/**
    	 * 倍率と　「拡大」「縮小」「グリッド」ボタンの作成
    	 */
       	JPanel menu_panel = new JPanel();	
       	menu_panel.setBorder(BorderFactory.createLineBorder(Color.black));
       	menu_panel.setBackground(edenpo.BACK_COLOR2);
       	menu_panel.setLayout(null);
       	menu_panel.setPreferredSize(new Dimension(500, 32)); 	// これが無いと表示しない
      	
      	mScaleLabel = new JLabel("現在表示倍率:100%");
       	mScaleLabel.setFont(edenpo.Menu_Font);
       	mScaleLabel.setBounds(30, 2, 200, 28);
       	menu_panel.add(mScaleLabel);
       	
        mZoomInBtn = new JButton("拡大");
        mZoomInBtn.setFont(edenpo.Menu_Font);
        mZoomInBtn.setBounds(30, 6, 40, 20);
        mZoomInBtn.addActionListener(this);
        mZoomInBtn.setMargin(insets);
        menu_panel.add(mZoomInBtn);
       	
       	mZoomOutBtn = new JButton("縮小");
       	mZoomOutBtn.setFont(edenpo.Menu_Font);
       	mZoomOutBtn.setBounds(30, 6, 40, 20);
     	mZoomOutBtn.addActionListener(this);
      	mZoomOutBtn.setMargin(insets);
      	menu_panel.add(mZoomOutBtn);
       	
      	mGridBtn = new JButton("グリッド線の切り替え");
      	mGridBtn.setBounds(30, 6, 120, 20);
      	mGridBtn.setFont(edenpo.Menu_Font);
       	menu_panel.add(mGridBtn);
       	
       	mGridBtn.setMargin(insets);
       	mGridBtn.addActionListener(this);
       	title_btn.add(menu_panel, BorderLayout.SOUTH);
 //      	title_btn.add(grid_btn, BorderLayout.EAST);
       	
    	add(title_btn, BorderLayout.NORTH);

   	
    	/**
    	 * 画像表示領域を作成
    	 */
     	mGPanel = new GPanel();	
       	mScroll = new JScrollPane(mGPanel);
       	mScroll.setPreferredSize(new Dimension(400, 100));
		mGPanel.setScale((float)(mScale / 100.0));
	
    	add(mScroll, BorderLayout.CENTER);
    	
	    addComponentListener(this);
 		
    	
   	}
    
    /**
     * ボタンのコールバック
     */
    public void actionPerformed(ActionEvent e) {
    	if (e.getActionCommand() == "拡大") {
    		mScale += 50;
    		if (mScale > 200) {
    			mScale = 200;
    		}
    		mScaleLabel.setText("現在表示倍率:"+mScale+"%");
    		
    		if (mScale > 100) {
    			if (edenpo.DocDialog.getDocMuki() == 0) {
    				mGPanel.setPreferredSize(new Dimension((int)(DEFAULT_VDOC_WIDTH * (mScale / 100.0)), (int)(DEFAULT_VDOC_HEIGHT * (mScale / 100.0))));   
    			} else {
    				mGPanel.setPreferredSize(new Dimension((int)(DEFAULT_HDOC_WIDTH * (mScale / 100.0)), (int)(DEFAULT_HDOC_HEIGHT * (mScale / 100.0))));   
    			}
    		} else {
    			mGPanel.setPreferredSize(new Dimension((int)(gwidth * (mScale / 100.0)), (int)(gheight * (mScale / 100.0))));
    		}
    		mGPanel.revalidate();  	
    		mScroll.doLayout();
    		mScroll.validate();
    		
       		mGPanel.setScale((float)(mScale / 100.0));
       		mGPanel.recreate();
     			
    	} else if (e.getActionCommand() == "縮小") {
    		mScale -= 50;
    		if (mScale < 50) {
    			mScale = 50;
    		}
    		mScaleLabel.setText("現在表示倍率:"+mScale+"%");
    		
    		if (mScale > 100) {
    			if (edenpo.DocDialog.getDocMuki() == 0) {
    				mGPanel.setPreferredSize(new Dimension((int)(DEFAULT_VDOC_WIDTH * (mScale / 100.0)), (int)(DEFAULT_VDOC_HEIGHT * (mScale / 100.0))));  
    			} else {
    				mGPanel.setPreferredSize(new Dimension((int)(DEFAULT_HDOC_WIDTH * (mScale / 100.0)), (int)(DEFAULT_HDOC_HEIGHT * (mScale / 100.0))));  
    			}
    		} else {
    			mGPanel.setPreferredSize(new Dimension((int)(gwidth * (mScale / 100.0)), (int)(gheight * (mScale / 100.0))));
    		}
    		mGPanel.revalidate();  	
    		mScroll.doLayout();
    		mScroll.validate();

    		mGPanel.setScale((float)(mScale / 100.0));
       		mGPanel.recreate();
   			
    	} else if (e.getActionCommand() == "グリッド線の切り替え") {
    		mGrid++;
    		
    		if (mGrid > 6)
    			mGrid = 0;
    		
    		mGPanel.setGrid(mGrid);
    	}
    }   
	
	public void componentHidden(ComponentEvent e) {}
	public void componentMoved(ComponentEvent e) {}
	public void componentResized(ComponentEvent e) {
		
		// リサイズしたときボタンの位置を変える
		int center_x = e.getComponent().getWidth() / 2;
		
		mScaleLabel.setLocation(new Point(center_x - 95, mScaleLabel.getY()));
		mZoomInBtn.setLocation(new Point(center_x + 10, mZoomInBtn.getY()));
		mZoomOutBtn.setLocation(new Point(center_x + 55, mZoomOutBtn.getY()));
		mGridBtn.setLocation(new Point(e.getComponent().getWidth()-130, mGridBtn.getY()));
	}
	public void componentShown(ComponentEvent e) {}
 
    /**
     *  画像表示領域のパネルを帰す
     */
    public GPanel getGPanel() {
    	return mGPanel;
    }
    
    /**
     *　ディスプレイリストを全部描画して、画面を再構築する
     */
   	public void redraw() {
    	mGPanel.redraw();
   	}
     	
   	/**
   	 * ドキュメントのタイトルを設定する
   	 * 
   	 * @param title  タイトル文字列
   	 */
    public void setTitle(String title) {
       	mTitle.setText("作成中のメニュー : "+ title);
    	mTitle.repaint();
    }

    /**
     * 現在の倍率を得る
     * 
     * @return 倍率
     */
    public int getScale() {
    	return mScale;
    }  	
    
    /**
     * 画像表示領域のサイズは起動してから決まるため
     * この関数で　そのサイズを保持している
     *  (この関数は edenpoのサイズ設定の後呼ばれる)
     */
    public void setInitSize() {
    	gwidth = mGPanel.getWidth();
    	gheight = mGPanel.getHeight();
    	mGPanel.setStartSize(gwidth, gheight);  	
    }
    
    
    public void init() {
    	mScale = 100;	// 倍率の初期値は　100 %
    	mGrid = 0;		// グリッド無し    	
    }
}    

/**
 * 画像表示パネル
 * 　画像表示とマウスオペレーションを行う
 * 
 * @author kagi
 */
class GPanel extends JPanel  implements ComponentListener {

    
	/**
	 * 表示用のリスト
	 */
	static public  LinkedList display_list = new LinkedList();
	
	/**
	 * 選択用のリスト
	 */
	private   LinkedList select_list = new LinkedList();
	
	/**
	 * ダブルバッファで使用する　バックイメージ
	 */
	private Image mImgBack = null;
	private BufferedImage mBaseImage = null;
    
	/**
	 * 倍率
	 */
	private float mScale;
	
    private int start_x, start_y, drag_x, drag_y, end_x, end_y;
    private int mGrid;
    
   
 	/**
	 * ドキュメントのサイズ(余白) と向き
	 */
    private int mDocSize, mDocMuki;
    
 	/**
	 * オリジナルのドキュメントのサイズ
	 */
    public int mOrgDocX, mOrgDocY, mOrgDocWidth, mOrgDocHeight;
    
    private double mdDragX, mdDragY, mdDragWidth, mdDragHeight;
    
  	/**
	 * Scaleによって変動するドキュメントのサイズ(余白は含まない)
	 */
    public double mDocX, mDocY, mDocWidth, mDocHeight;
  
    /**
     * 他のウィンドウで変更されるので一時的にデータをセーブする変数
     */
    private double mDocX_save, mDocY_save, mDocWidth_save, mDocHeight_save;
	private float mScale_save;
	private boolean mDocSave;
  
	/**
	 * 選択されたオブジェクトの枠に着く■の初期サイズ
	 */
    final private int SELECT_BOX = 6;
    
 	/**
	 * 選択された状態   0:選択   1:移動  2:リサイズ    -1:選択線を表示しない
	 */
    private int mSelectBoxNo;  
    
    /**
     * mSelectBoxNo を設定したときの選択オブジェクト
     */
    private Group mSelectObj;

 	/**
	 * ドラック前のオブジェクトの位置、サイズ
	 */
    private double mOrgX, mOrgY, mOrgWidth, mOrgHeight;

    /**
     * 起動時のパネルのサイズ
     */
    private int mStartWidth, mStartHeight;
    
    private int mTextInput;	// 文字入力中　フラグ
    private FontInfo mFontInfo;	// 文字入力中のフォント情報
    private String mTextInputStr;	// 文字入力中の文字列
    private boolean mTextDClick;	// ダブルクリックで文字編集
    private String mFilePath;
    
    // test用
    private FileChannel mFileChanel;
    
    GPanel() {
    	
    	setLayout(null);
       	setBackground(Color.WHITE);
       	
       	setBorder(BorderFactory.createLineBorder(Color.black));
       	
        	setPreferredSize(new Dimension(mStartWidth, mStartHeight));
           	 
    	revalidate();  	// Panelを作り直す
  	
       	addMouseListener(new MouseInput());
       	addMouseMotionListener(new MouseInput());
        addComponentListener(this);
 	
       	mGrid = 0;
    	mSelectBoxNo = -1;
    	mSelectObj = null;
    	
    	mTextInput = 0;
    	mTextInputStr = null;
    	mTextDClick = false;
    	
    	mDocSize = -1;
    	mDocMuki = -1;
    	mDocSave = false;
     }
   
    /**
     * 
     */
    public void setStartSize(int width, int height) {
      	mStartWidth = width;
       	mStartHeight = height;

    }
    /**
     * ドキュメントの縦横と余白のサイズを設定
     * @param size
     * @param muki
     * @return -1　確認ダイアログでキャンセルした
     *           0  同じ値で　決定した
     *           1  値を変えて　決定した
     */
    public int setDocType(int size, int muki) {
//System.out.println("setDocType in_size="+size+" in_muki="+muki+" mDocSize="+mDocSize+" mDocMuki="+mDocMuki);
    	if (mDocSize == size && mDocMuki == muki) {
    		return 0;
    	}
    	
    	if (getListSize() > 0) {
    		int value = JOptionPane.showConfirmDialog(edenpo.DocDialog, 
				"現在までのデザイン作成データが失われますが、よろしいですか？", "確認", JOptionPane.YES_NO_OPTION);
    		if (value != JOptionPane.YES_NO_OPTION) {
    			return -1;
    		}
    	}
    	clear();
    	
		mDocSize = size;
    	mDocMuki = muki;
    	
    	if (muki == 0) {
    		mOrgDocWidth = GraphicsPanel.DEFAULT_VDOC_WIDTH;
    		mOrgDocHeight = GraphicsPanel.DEFAULT_VDOC_HEIGHT;
    	} else {
    		mOrgDocWidth = GraphicsPanel.DEFAULT_HDOC_WIDTH;
    		mOrgDocHeight = GraphicsPanel.DEFAULT_HDOC_HEIGHT;
    	}
    	
		
		if (getWidth() < mOrgDocWidth || getHeight() < mOrgDocHeight) {
			setPreferredSize(new Dimension(mOrgDocWidth, mOrgDocHeight));			
 		} else {
			setPreferredSize(new Dimension(mStartWidth, mStartHeight));
		}
		
 	   	redraw();  
 	   	repaint();
 	   	
 	   	return 1;
    }
    
    public int getDocWidth() {
    	return mOrgDocWidth;
    }
    public int getDocHeight() {
    	return mOrgDocHeight;
    }
    
    /**
     * ドキュメントの情報を一時的に保存
     */
    public void pushDocInfo() {
    	mDocX_save = mDocX;
    	mDocY_save = mDocY;
    	mDocWidth_save = mDocWidth;
    	mDocHeight_save = mDocHeight;
    	mScale_save = mScale;
    	mDocSave = true;
    	
//JOptionPane.showMessageDialog(edenpo.Main_Frame, "pushDocInfo  mScale_save="+mScale_save+" mScale="+mScale);
//JOptionPane.showMessageDialog(edenpo.Main_Frame, "mDocX="+mDocX+" mDocY="+mDocY+" mDocWidth="+mDocWidth+" mDocHeight="+mDocHeight+"  mScale="+mScale);
   	
//System.out.println("pushDocInfo   mDocX="+mDocX+" mDocY="+mDocY+" mDocWidth="+mDocWidth+" mDocHeight="+mDocHeight+"  mScale="+mScale);
    }
    
    /**
     * 一時的に保存していたドキュメントの情報を戻す
     */
   public void popDocInfo() {
    	mDocX  = mDocX_save;
    	mDocY  = mDocY_save;
    	mDocWidth  = mDocWidth_save;
    	mDocHeight  = mDocHeight_save;
    	mScale  = mScale_save;
    	mDocSave = false;
    	
//JOptionPane.showMessageDialog(edenpo.Main_Frame, "popDocInfo  mScale="+mScale);

//System.out.println("popDocInfo   mDocX="+mDocX+" mDocY="+mDocY+" mDocWidth="+mDocWidth+" mDocHeight="+mDocHeight+"  mScale="+mScale);
//		setScale(mScale);
		for (int i = 0; i < display_list.size(); i++) {
			Group obj = (Group)display_list.get(i);
			obj.setScale(mScale);
		}
		
		repaint();
   }
    
    /**
     * プレビューで使用
     * @return
     */
    public int getOrgDocWidth() { return mOrgDocWidth; }
    public int getOrgDocHeight() { return mOrgDocHeight; }
    
    
	public void componentHidden(ComponentEvent e) {}
	public void componentMoved(ComponentEvent e) {}
	public void componentShown(ComponentEvent e) {}
	public void componentResized(ComponentEvent e) {

		if (getWidth() < mOrgDocWidth || getHeight() < mOrgDocHeight) {
			setPreferredSize(new Dimension(mOrgDocWidth, mOrgDocHeight));			
 		} 

		mImgBack = null;
 	   	redraw();    
 	   	repaint();
	}
	
  
    /**
     * ディスプレイリストにオブジェクトを登録
     * @param obj
     */
    public void addList(Group obj){
    	display_list.add(obj);   
   
    	// 選択状態を全てクリア
		clearSelect();
		
		// 追加したオブジェクトだけを選択状態にする
		obj.mSelected = true;
    	select_list.add(obj);
    	mSelectBoxNo = 1;
       	mSelectObj = obj;
 	
    	
		resetToolandLayer(true);
		
		edenpo.Layer_Panel.addObject(obj);

    }
    
    /**
     * ディスプレイリストにオブジェクトを登録
     * @param obj
     */
    public void addList(int index, Group obj){
    	int index2 = getListSize() - index;
 
// System.out.println("num="+getListSize()+"  index="+index);
    	display_list.add(index, obj);   
    	
		clearSelect();
		
		obj.mSelected = true;
    	select_list.add(obj);
    	mSelectBoxNo = 1;
      	mSelectObj = obj;
 	
    	resetToolandLayer(true);
    	
    	edenpo.Layer_Panel.addObject(index2, obj);
    }
    
    /**
     * 指定したオブジェクトを選択状態にする
     * @param obj
     */
    public void setSelect( Group obj){
		obj.mSelected = true;
    	select_list.add(obj);
    	mSelectBoxNo = 1;
      	mSelectObj = obj;
      	resetToolandLayer(false);
    }
    
    /**
     * ディスプレイリストから指定したオブジェクトを削除
     * @param obj
     */
    public void removeList(Group obj){
    	int index = display_list.indexOf(obj);
    	
    	removeList(index);
    	
    }
    
    public void removeList(int index){
    	int index2 = getListSize() - index - 1;
		Group obj = (Group)display_list.get(index2);
 	
    	display_list.remove(index);
    	obj = null;
    	
    	edenpo.Layer_Panel.removeObject(index2);
    	
    	// 選択している物を削除するので
    	// 選択状態をクリアする
		clearSelect();
		

		resetToolandLayer(true);
    	
    }
   
    /**
     * ディスプレイリストのサイズを返す
     * @return  ディスプレイリストのサイズ
     */
    public int getListSize() {
    	return display_list.size();
    }
    
    /**
     * クリア
     */
    public void clear() {
    	
    	// 選択リストの初期化
		for (int i = 0; i < select_list.size(); i++) {
			Group obj = (Group)select_list.get(i);
			obj = null;
		}
		
		select_list.clear();
    	
    	// レイヤーの初期化
      	edenpo.Layer_Panel.clear();

       	// 操作スタックの初期化
		edenpo.Stack.clear();
		
		// ツールの初期化
		edenpo.Tool_Panel.setEnable(0);

    	// ディスプレイリストの初期化
		for (int i = 0; i < display_list.size(); i++) {
			Group obj = (Group)display_list.get(i);
			obj.clear();
			obj = null;
		}   	

    	display_list.clear();

      	mGrid = 0;
    	mSelectBoxNo = -1;
    	mSelectObj = null;
    	
    	mTextInput = 0;
    	mTextInputStr = null;
    	
    	mDocSize = -1;
    	mDocMuki = -1;
    	
    	// 起動時のサイズに戻す
       	setPreferredSize(new Dimension(mStartWidth, mStartHeight));
      	revalidate();  	// Panelを作り直す
      	
    	mImgBack = null;
    	mBaseImage = null;


    	System.gc();
    }

    
    /**
     * グループ化/解除などでレイヤー番号を振りなおす
     *
     */
    public void resetLayer() {
    	int layer = 0;
		for (int i = 0; i < display_list.size(); i++) {
			Group obj = (Group)display_list.get(i);
			obj.mLayer = layer++;
		}   	
    }
    
    public void printDisplayList() {
     	Group obj;
       	System.out.println("printDisplayList-->");
		for (int i = 0; i < display_list.size(); i++) {
			obj = (Group)display_list.get(i);
			System.out.print("obj.mLayer="+obj.mLayer+" ");
		}   	
       	System.out.println("");

    }
    
    /**
     * グリッドのタイプを設定する
     * @param grid
     */
    public void setGrid(int grid) {
	   mGrid = grid;
	   redraw();
	   repaint();
   }
    
    public void exchange(int start_no, int end_no) {	
    	if (start_no == end_no)
    		return;
    	
    	Group obj = (Group)display_list.get(start_no);
 
//System.out.println("exchange  start_no="+start_no+"  end_no="+end_no);
//printDisplayList();

    	if (start_no < end_no) {
    	   	display_list.add(end_no+1, obj);    	
//printDisplayList();
   		display_list.remove(obj);			
//printDisplayList();
     	} else {
    		display_list.remove(obj);			
    	   	display_list.add(end_no, obj);    	
    	}
    }
    
    public Group[]  getSelectedObject() {
    	Group[] objs = new Group[select_list.size()];
    	
    	for (int i = 0; i < select_list.size(); i++) {
    		objs[i] = (Group)select_list.get(i);
    	}
    	
    	/**
    	 *選択の順番は　Ctrlキーなどで追加するなどで
    	 * 順番どおりになってない場合があるので　ここで昇順に並ばせるg
    	 */
    	Arrays.sort(objs, new GroupComparator()); 
    	return objs;
    }
   
    public void setBaseImage(BufferedImage img) {
    	
    	if (getListSize() > 0) {
    		int value = JOptionPane.showConfirmDialog(this, 
				"現在までのデザイン作成データが失われますが、よろしいですか？", "確認", JOptionPane.YES_NO_OPTION);
    		if (value != JOptionPane.YES_NO_OPTION) {
    			return;
    		}
    		
    		clear();
/*
        	display_list.clear();
        	select_list.clear();
          	edenpo.Layer_Panel.clear();
          	
    		// ツールの初期化
 */   		edenpo.Tool_Panel.setEnable(0);

         	mSelectBoxNo = -1;
        	mSelectObj = null;
        	
        	mTextInput = 0;
        	mTextInputStr = null;
    	}
    	
    	mBaseImage = img;
    	edenpo.Layer_Panel.addBaseSozai(mBaseImage);
    	redraw();
    	repaint();
    }
   
   /**
    *　再構築後表示する
    */
   public void draw() {
	   redraw();
	   repaint();
   }
   
   public void update(Graphics g) {
    	paint(g);
    }
  
   /**
    * ディスプレイリストのオブジェクトを全て描画し、画面を再構築する
    *
    */
    public void redraw() {
   		
    	if (mImgBack == null) {
    		mImgBack = createImage(getWidth(), getHeight());   
    	}
    	if (mImgBack == null) {
    		return;
    	}
    	
    	Graphics offscreen = mImgBack.getGraphics();
		
    	//　クリア
    	offscreen.setColor(Color.white);
    	offscreen.fillRect(0, 0,getWidth(), getHeight());
		
		drawBackground(offscreen, getWidth(), getHeight());
		
		for (int i = 0; i < display_list.size(); i++) {
			Group obj = (Group)display_list.get(i);
			if (obj.mShow) {
				obj.draw(offscreen, this);
			}
		}
		
		drawGrid(offscreen);
		
		drawFrame(offscreen, getWidth(), getHeight(), true, true);
		
		offscreen.dispose();
		if (mImgBack != null)
			mImgBack.flush();
    }
    
    public void recreate() {
    	mImgBack = null;
    	draw();
     }
    
    /**
     * 倍率を設定
     * 　ディスプレイリストのオブジェクト全てに値を設定
     * 
     * @param scale 倍率
     */
    public void setScale(float scale) {
    	if (mScale == scale)
    		return;
    	
    	mScale = scale;
		for (int i = 0; i < display_list.size(); i++) {
			Group obj = (Group)display_list.get(i);
			obj.setScale(mScale);
		}
     }
    
    /**
     * 倍率を返す
     * 
     * @return ディスプレイリストのサイズ
     */
    public float getScale() {
    	return mScale;
    }
    
	/**
	 * 選択処理
	 * 　　
	 * 
	 * @param x
	 * @param y
	 * @param ctrl Ctrlキーが押されているかどうか
	 */
	public void selection(int x, int y, boolean ctrl) {
		
		/**
		 * はじめに
		 * 選択していて、端に表示しているクリックBoxの上にあるかどうかチェックする
		 */		
		for (int i = select_list.size()-1; i >= 0; i--) {
			Group obj = (Group)select_list.get(i);
			int no  = HitTestSelectBox(obj, x, y);
			if (no > 0) {
				// グループでは移動だけ
				if (obj.mType == 0) {
					no = 1;
				}
				mSelectBoxNo = no;
		      	mSelectObj = obj;
		      	
		      	mOrgX = obj.mX;
		      	mOrgY = obj.mY;
		      	mOrgWidth = obj.mWidth;
		      	mOrgHeight = obj.mHeight;
		      	
		      	mdDragX = (double)obj.mX;
		      	mdDragY = (double)obj.mY;
		      	mdDragWidth = (double)obj.mWidth;
		      	mdDragHeight = (double)obj.mHeight;
				return;
			}
		}
		if (!ctrl) {
			// 選択を全て初期化する
			clearSelect();
		}

		mSelectBoxNo = 0;
		for (int i = display_list.size()-1; i >= 0; i--) {		// select_list に設定するのは１つだけなので順番は関係ない
			Group obj = (Group)display_list.get(i);
//JOptionPane.showMessageDialog(this, "obj type="+obj.mType);
			if (obj.HitTest(x, y)) {
				if (! obj.mSelected) {
					select_list.add(obj);
				}
				obj.mSelected = true;
				resetToolandLayer(true);
				mSelectBoxNo = 1;		// objの内部のどこかをクリックした  
										// resetToolandLayer で clearSelectが呼ばれるため resetToolandLayerの後に設定
//System.out.println("select index="+i);
		      	mSelectObj = obj;
		      	
		      	mOrgX = obj.mX;
		      	mOrgY = obj.mY;
		      	
		      	
		      	mdDragX = (double)obj.mX;
		      	mdDragY = (double)obj.mY;
		      	mdDragWidth = (double)obj.mWidth;
		      	mdDragHeight = (double)obj.mHeight;
		      	
		      	mOrgWidth = obj.mWidth;
		      	mOrgHeight = obj.mHeight;
				return;
			}
		}  
 	}
	
	
	/**
	 * 選択オブジェクトを設定する
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 */
	public void selection() {
					
		
		int x1, y1, x2, y2;
		
		if (start_x <= end_x) {
			x1 = start_x;
			x2 = end_x;
		} else {
			x1 = end_x;
			x2 = start_x;			
		}
		
		if (start_y <= end_y) {
			y1 = start_y;
			y2 = end_y;
		} else {
			y1 = end_y;
			y2 = start_y;			
		}
		
		// 近い場合はクリック選択を有効にする
		if ((x2-x1) < 2 && (y2-y1) < 2) {
			resetToolandLayer(true);
			return;
		}
		
		// 選択を全て初期化する
		clearSelect();
		
		
		boolean bSelected = false;
		for (int i = 0; i < display_list.size(); i++) {
			Group obj = (Group)display_list.get(i);
			if (obj.HitTest(x1, y1, x2, y2)) {
				obj.mSelected = true;
				select_list.add(obj);	
				bSelected = true;
			}
		}
		resetToolandLayer(true);
		
	}

	/**
	 * 選択の状況によってツールボタンの使用可/不可を設定する
	 *
	 * @param layer  layerパネルの選択状態を変更するかどうかのフラグ
	 *                Layerパネルからのイベントでもこの関数に来るため
	 *                ここでlayerの変更をすると無限ループになる。
	 *                Layerパネルからの変更では　この値を falseにする
	 */
	public void resetToolandLayer(boolean layer)
	{
		int num = select_list.size();
		int [] indices = null;
		
		edenpo.Text_Panel.setEnabled(false);
		if (num == 0) {
			// 選択してない場合   1/6 ファイルを読みこんだあとUnGroupするとツールが全てdisableになる為display_listの比較も追加
			if (edenpo.Stack.size() == 0 && display_list.size() == 0) {   
				// Stackが空の時　　全て選択不可
				edenpo.Tool_Panel.setEnable(0);
			} else {
				// Undoだけ使用可
				edenpo.Tool_Panel.setEnable(6);
			}
			
			if (layer) 
				edenpo.Layer_Panel.setSelect(indices, false);
			
		} else if (num == 1) {
			Group obj = (Group)select_list.get(0);
			
			// 1つだけ選択の場合
			switch(obj.mType) {
			case 0:
				edenpo.Tool_Panel.setEnable(5);
				break;
			case 1: // 画像
				edenpo.Tool_Panel.setEnable(2);
				break;
			case 2:	// 文字
			case 3:	// 差出人
				edenpo.Tool_Panel.setEnable(3);
				
				edenpo.Text_Panel.setFontInfo((TextObject)obj);
				edenpo.Text_Panel.setEnabled(true);
				break;
			}
			if (obj.mLayer == 0 && obj.mLayer == getListSize() - 1) {
				edenpo.Tool_Panel.setEnable(9);				
			} else {
				if (obj.mLayer == 0) {	// 最後面
					edenpo.Tool_Panel.setEnable(8);				
				} 
				if (obj.mLayer == getListSize() - 1){  // 最前面
					edenpo.Tool_Panel.setEnable(7);				
				}
			}
//System.out.println("resetToolandLayer obj.mLayer="+obj.mLayer);

			if (layer)  {
				indices = new int[1];
				indices[0] = obj.mLayer;
				edenpo.Layer_Panel.setSelect(indices, true);
			}
		} else {
			// 複数選択の場合
			edenpo.Tool_Panel.setEnable(4);
			
			indices = new int[num];
			for (int i = 0; i < num; i++) {
				Group obj = (Group)select_list.get(i);
				indices[i] = obj.mLayer;
			}
			
			if (layer)  {
				edenpo.Layer_Panel.setSelect(indices, true);
			}
		}
		
		/**
		 * トップメニューのボタンの状態を追加
		 */
		if (display_list.size() > 0) {
			edenpo.Top_Panel.setEnabled(true);
		} else {
			edenpo.Top_Panel.setEnabled(false);
		}
	}

	/**
	 * 選択状態を設定する (Layerなどから呼ばれる)
	 * 
	 * @param index   リスト上のindex
	 * @param clear	   選択する前に全ての選択をクリアするかどうかのフラグ
	 * 
	 */
	public void setSelect(int [] indices) {
		Group obj;
		
		// 選択を全て初期化する
		clearSelect();
		for (int i = 0; i < indices.length; i++) {
			obj = (Group)display_list.get(indices[i]);
			select_list.add(obj);
		}
		
		resetToolandLayer(false);
	}
	
	/**
	 * 
	 * @param x  x方向の移動量
	 * @param y　y方向の移動量
	 */
	public void moveSelect(int x, int y) {
		if (mSelectObj == null || mSelectBoxNo == -1) 
			return;
		
		double dx = x / mScale;
		double dy = y / mScale;
		
		Group obj = mSelectObj;

//		System.out.println("moveSelect mSelectBoxNo ="+mSelectBoxNo+"  type="+obj.mType);
		
		double x2, y2, width2, height2;
		x2 = obj.mX;
		y2 = obj.mY;
		width2 = obj.mWidth;
		height2 = obj.mHeight;
		
		switch(mSelectBoxNo) {
		case 1:	 // 移動
			x2  += dx;
			y2  += dy;
			break;
		case 2: // 左上ドラック
			x2  += dx;
			y2  += dy;
			width2 -= dx;
			height2 -= dy;
			break;
		case 3: // 右上ドラック
			y2   += dy;			
			width2 += dx;
			height2 -= dy;
			break;
		case 4: // 左下ドラック
			x2 += dx;
			width2 -= dx;
			height2 += dy;
			break;
		case 5: // 右下ドラック
			width2 += dx;
			height2 += dy;
			break;
		case 6: //　上中ドラック
			y2  += dy;
			height2 -= dy;
			break;
		case 7: //　左中ドラック
			x2 += dx;
			width2 -= dx;
			break;
		case 8: // 右中ドラック
			width2 += dx;
			break;
		case 9: // 下中ドラック
			height2 += dy;
			break;
		}
		
		/**
		 * 選択領域を反転してドキュメント領域からはみ出る場合
		 */
		if (obj.mX*mScale > mDocWidth) {
			return;
		}		
		if (obj.mY*mScale > mDocHeight) {
			return;
		}
		if (obj.mX*mScale + obj.mWidth*mScale < 0) {
			return;
		}
		if (obj.mY*mScale + obj.mHeight*mScale < 0) {
			return;
		}

		
		obj.mX = x2;
		obj.mY = y2;
		obj.mWidth = width2;
		obj.mHeight = height2;
		
		
		/**
		 * サイズが無い特殊なケース   2007/10/27
		 */
/*
		if (obj.mWidth <= 0) {
			obj.mWidth = 0;
			return;
		}
		if (obj.mHeight <= 0) {
			obj.mHeight = 0;
			return;
		}
*/
		if (mOrgHeight == 0 || mOrgWidth == 0) {
			return;
		}

//System.out.println("1 moveSelect mX="+obj.mX+" mY="+obj.mY+" mWidth="+obj.mWidth+" mHeiht="+obj.mHeight);
		
	
		if (obj.mType == 1 && (mSelectBoxNo == 2 || mSelectBoxNo == 3 ||mSelectBoxNo == 4 || mSelectBoxNo == 5) ) {
//			if (Math.abs(obj.mWidth - mOrgWidth) < Math.abs(obj.mHeight - mOrgHeight)) {
			if (mOrgWidth < mOrgHeight) {
				obj.mWidth = obj.mHeight * mOrgWidth / mOrgHeight;
				if (mSelectBoxNo == 2 || mSelectBoxNo == 4) {
//				if (mSelectBoxNo == 2) {
					obj.mX = mOrgX + mOrgWidth - obj.mWidth;
				}
			} else {
				obj.mHeight = obj.mWidth * mOrgHeight / mOrgWidth;
				if (mSelectBoxNo == 2 || mSelectBoxNo == 3) {
//				if (mSelectBoxNo == 2) {
					obj.mY = mOrgY + mOrgHeight - obj.mHeight;
				}
			}
		}
		
		if (obj.mX*mScale+obj.mWidth*mScale > mDocWidth) {
			obj.mX = (mDocWidth - obj.mWidth*mScale) / mScale;				
		}
		if (obj.mX  < 0) {
			obj.mX = 0;
		}
		if (obj.mX*mScale+obj.mWidth*mScale >= mDocWidth) {
			obj.mWidth = (mDocWidth - obj.mX*mScale) / mScale;				
		}
		
		if (obj.mY*mScale+obj.mHeight*mScale >= mDocHeight) {
			obj.mY = (mDocHeight - obj.mHeight*mScale) / mScale;
		}
		if (obj.mY < 0) {
			obj.mY = 0;
		}
		if (obj.mY*mScale+obj.mHeight*mScale >= mDocHeight) {
			obj.mHeight = (mDocHeight - obj.mY*mScale) / mScale;				
		}
		
		
//System.out.println("2 moveSelect mX="+obj.mX+" mY="+obj.mY+" mWidth="+obj.mWidth+" mHeiht="+obj.mHeight);
	}
	
	public void clearSelect() {
		mSelectBoxNo = -1;
		mSelectObj = null;
		
		for (int i = display_list.size() -1; i >= 0; i--) {
			Group obj = (Group)display_list.get(i);
			obj.mSelected = false;
		}
		select_list.clear();
	}
    
	public void setTextInput(int no) {
		mTextInput = no;
		
		if (mTextInput == 0) 
			mTextInputStr = null;
		
		repaint();
	}
	
	public void setTextInputStr(String str) {
		mTextInputStr = str;
		repaint();
	}
	
	public void setTextInputFont(FontInfo font_info, boolean redraw) {
		mFontInfo = font_info;
		if (redraw) {
			redraw();
		}
		repaint();
	}
    
    public void drawTextInput(Graphics g) {
    	if (mTextInput == 0)
    		return;
    	
		int box_x = (int)mDocX;
		int box_y = (int)mDocY;
		int box_width = (int)mDocWidth;
		int box_height = (int)(100 * mScale);
		int click_box = (int)(SELECT_BOX * mScale);
 
    	if (mTextDClick) {
    		box_x = box_x + (int)mSelectObj.mX;
    		box_y = (int)mDocY + (int)mSelectObj.mY;
    		box_width = (int)mSelectObj.mWidth;
    		box_height = (int)mSelectObj.mHeight;
    	} else {    		
    		box_x = (int)mDocX+4;
     		box_width = (int)mDocWidth-8;
 
     		
    		if (mTextInput == 2) {
    			box_width = (int)(GraphicsPanel.SASHIDASHI_WIDTH * mScale);
    			box_height = (int)(GraphicsPanel.SASHIDASHI_HEIGHT * mScale);
    			box_x = (int)(mDocX + mDocWidth) - box_width - 4;
    			box_y = (int)(mDocY + mDocHeight) - box_height - 4;
    		} else {
    	   		box_y = (int)mDocY+4;			
    		}

    	}
    	

//System.out.println("drawTextInput   mDocXY="+mDocY+" mDocHeight="+mDocHeight+" box_y="+box_y);
		
    	g.setColor(Color.LIGHT_GRAY);
//System.out.println("drawTextInput   box_y="+box_y+" box_height="+box_height);
		g.drawRect(box_x, box_y, box_width, box_height);
		
    	g.setColor(Color.blue);
		// 文字は上下左右にサイズ変更Boxが付く
        g.fillRect(box_x-click_box/2 , box_y-click_box/2 , click_box, click_box);
        g.fillRect((box_x + box_width/2)-click_box/2 , box_y-click_box/2 , click_box, click_box);
        g.fillRect(box_x+box_width-click_box/2 , box_y-click_box/2 , click_box, click_box);
        g.fillRect(box_x-click_box/2 , box_y+box_height/2-click_box/2 , click_box, click_box);
        g.fillRect(box_x+box_width-click_box/2 , box_y+box_height/2-click_box/2 , click_box, click_box);
        g.fillRect(box_x-click_box/2 , box_y+box_height-click_box/2 , click_box, click_box);
        g.fillRect((box_x + box_width/2)-click_box/2 , box_y+box_height-click_box/2 , click_box, click_box);
        g.fillRect(box_x+box_width-click_box/2 , box_y+box_height-click_box/2 , click_box, click_box);				
   	
        if (mTextInputStr != null) {
			Font font = new Font(mFontInfo.mFont, mFontInfo.mStyle, (int)(mFontInfo.mSize * mScale));
			g.setColor(mFontInfo.mColor);
			g.setFont(font);
//        	g.drawString(mTextInputStr, box_x+5, box_y+mFontInfo.mSize);
			TextObject.drawStringExt(g, (Container)this, mTextInputStr, box_x, box_y, box_width, box_height, 
					font, mFontInfo.mTume, mFontInfo.mMuki, mFontInfo.mColor);
        }
    }
	
    protected void paintComponent(Graphics g) {
             	
    	super.paintComponent(g);
//    	redraw();
        if (mImgBack != null) {
        	g.drawImage(mImgBack, 0, 0, this);      //バッファを画面に描画
        }
        
        if (mSelectBoxNo == 0) {
        	if (start_x != end_x && start_y != end_y) {
        		// ドラック選択するときに表示する　四角
        		g.setColor(Color.blue);
        		
        		int x = start_x;
        		int y = start_y;
        		if (x > end_x)
        			x = end_x;
        		if (y > end_y)
        			y = end_y;
        		g.drawRect(x, y, Math.abs(start_x - end_x), Math.abs(start_y - end_y));
        	}
      	
        }
        
        /**
         * プレビューなどで影響を受けないようにする
         */
        if (! mDocSave) {
        	// 選択状態を描画
        	drawSelect(g);
      
        	// 文字入力中の情報を表示
        	drawTextInput(g);
        }
       
    }    
     
    /**
     * 基本素材があれば基本素材を描画
     * 
     * @param g
     * @param win_width
     * @param win_height
     * @param yohaku 余白線を描画するかどうか
     */
    public void drawBackground(Graphics g, int win_width, int win_height) {
    	double x1, y1, x2, y2;
    	double width, height;
    	
       	width = mOrgDocWidth * mScale;
       	height = mOrgDocHeight * mScale;
    	
    	// 画面の中心にドキュメント枠が来るように調整する
    	x1 = win_width / 2.0 - width / 2.0;
    	y1 = win_height / 2.0 - height / 2.0;
    	
     	mDocX = x1;
    	mDocY = y1;
    	mDocWidth = width;
    	mDocHeight = height;
    	
//    	System.out.println("drawBackground mDocX="+mDocX+"  mDocY="+mDocY);
//    	System.out.println("drawBackground mDocWidth="+mDocWidth+"  mDocHeight="+mDocHeight);
		/**
		 * 描画の最後にも呼ぶため冗長になるが
		 * 描画では　余白内の左上が原点となるため、　余白の計算だけ行っている
		 */
		drawFrame(g, win_width, win_height, false, false);
   
        if (mBaseImage != null) {
        	g.drawImage(mBaseImage, (int)(mDocX+0.49), (int)(mDocY+0.49), (int)(mDocWidth+0.49), (int)(mDocHeight+0.49), this);
        }
//System.out.println("mDocX="+mDocX+" mDocY="+mDocY+" mDocWidth="+mDocWidth+" mDocHeight="+mDocHeight);
   }
   
    /**
     * ドキュメント枠を描画
     * 
     * @param g
     * @param win_width
     * @param win_height
     * @param yohaku 余白線を描画するかどうか
     * @param frame 外枠を描画するかどうか
     */
    public void drawFrame(Graphics g, int win_width, int win_height, boolean yohaku, boolean frame) {
    	double x1, y1, x2, y2;
    	double width, height;
    	
       	width = mOrgDocWidth * mScale;
       	height = mOrgDocHeight * mScale;
    	
    	// 画面の中心にドキュメント枠が来るように調整する
    	x1 = win_width / 2.0 - width / 2.0;
    	y1 = win_height / 2.0 - height / 2.0;
    	x2 = x1 + width;
    	y2 = y1 + height;
  
//System.out.println("width="+width+"  win_width="+win_width);

   		Graphics2D g2 = (Graphics2D)g;
    	if (frame) {
    		// 外枠を描画する
    		g.setColor(Color.black);
    		g.drawRect((int)x1, (int)y1, (int)width, (int)height);
 
    		// 外枠の影を描画する
     		double line_width = 5 * mScale;
    		g.setColor(new Color(128, 128, 128));
    		g2.setStroke(new BasicStroke((float)line_width));
    		drawLineEx(g, x2+line_width/2.0, y1+line_width, x2+line_width/2.0, y1 + height);
    		drawLineEx(g, x1+line_width, y1+line_width/2.0+height, x1+line_width/2.0+width, y1+line_width/2.0+height);
    	}
    	mDocX = x1;
    	mDocY = y1;
    	mDocWidth = width;
    	mDocHeight = height;
    	
//System.out.println("1 drawFrame mDocWidth="+mDocWidth+"  mDocHeight="+mDocHeight);
   	
        // 余白の区切りを描画する
        g.setColor(Color.red);
        g2.setStroke(new BasicStroke(1.0f));
        double space = GraphicsPanel.YOHAKU * mScale;
     	if (mDocSize == 0) {
     		if (yohaku) {
     			drawLineEx(g, x1, y1+space, x1+width, y1+space);
     			drawLineEx(g, x1+space, y1, x1+space, y1+height);
     		}
           	mDocX = x1+space;
           	mDocY = y1+space;
           	if (mDocMuki == 0) {
        		if (yohaku) {
        			drawLineEx(g, x1+width-space, y1, x1+width-space, y1+height);
        			drawLineEx(g, x1, y1+height-space*2, x1+width, y1+height-space*2);
        		}
               	mDocWidth = width-space*2;
               	mDocHeight = height-space*3;
           	} else {
           		if (yohaku) {
           			drawLineEx(g, x1+width-space*2, y1, x1+width-space*2, y1+height);
           			drawLineEx(g, x1, y1+height-space, x1+width, y1+height-space);
           		}
           		
               	mDocWidth = width-space*3;
               	mDocHeight = height-space*2;
          		
           	}
            	
   		} else if (mDocSize == 1) {
       		if (yohaku) {
       			drawLineEx(g, x1, y1+space, x1+width, y1+space);
       			drawLineEx(g, x1+space, y1, x1+space, y1+height);
       			drawLineEx(g, x1+width-space, y1, x1+width-space, y1+height);
       			drawLineEx(g, x1, y1+height-space, x1+width, y1+height-space);  
       		}
       		
            mDocX = x1+space;
            mDocY = y1+space;
            mDocWidth = width-space*2;
           	mDocHeight = height-space*2;
        }
//System.out.println("2 drawFrame mDocWidth="+mDocWidth+"  mDocHeight="+mDocHeight);

//System.out.println("drawFrame mDocX="+mDocX+"  mDocY="+mDocY);
   }
    /**
     * グリッド線を描画
     * 
     * @param g
     */
    private void drawGrid(Graphics g) {
    	
    	if (mGrid == 0)
    		return;
    	
       	double x1, y1, x2, y2;
       	int div = 8;
       	
       	g.setColor(Color.LIGHT_GRAY);
	    Graphics2D g2 = (Graphics2D)g;
	    g2.setStroke(new BasicStroke(1.0f));
	    if (mGrid > 0) {
    		double div2 = div * mScale;
        	
    	    x1 = mDocX;
    	    x2 = mDocX + mDocWidth;
    	    y1 = mDocY + div2;
    	    while(y1 < mDocY+mDocHeight) {
    	    	drawLineEx(g, x1, y1, x2, y1);
       	    	y1 += div2;
    	    }
        	
    	    y1 = mDocY;
    	    y2 = mDocY + mDocHeight;
    	    x1 = mDocX + div2;
    	    while(x1 < mDocX+mDocWidth) {
    	    	drawLineEx(g, x1, y1, x1, y2);
    	    	x1 += div2;
    	    }
  		
  	    	g2.setStroke(new BasicStroke(2.0f));
  	        switch(mGrid) {
    	    case 2:
    	    	drawLineEx(g, mDocX, mDocY + mDocHeight/2, mDocX + mDocWidth, mDocY + mDocHeight/2);
    	    	drawLineEx(g, mDocX + mDocWidth/2, mDocY, mDocX + mDocWidth/2, mDocY + mDocHeight);
    	    	break;
    	    case 3:
    	    	drawLineEx(g, mDocX + mDocWidth/2, mDocY, mDocX + mDocWidth/2, mDocY + mDocHeight);
    	    	break;
    	    case 4:
    	    	drawLineEx(g, mDocX, mDocY + mDocHeight/2, mDocX + mDocWidth, mDocY + mDocHeight/2);
    	    	break;
    	    case 5:
    	    	drawLineEx(g, mDocX, mDocY + mDocHeight * 0.333, mDocX + mDocWidth, mDocY + mDocHeight * 0.333);
    	    	drawLineEx(g, mDocX, mDocY + mDocHeight * 0.666, mDocX + mDocWidth, mDocY + mDocHeight * 0.666);
    	    	break;
    	    case 6:
    	    	drawLineEx(g, mDocX + mDocWidth * 0.333, mDocY, mDocX + mDocWidth * 0.333, mDocY + mDocHeight);
    	    	drawLineEx(g, mDocX + mDocWidth * 0.666, mDocY, mDocX + mDocWidth * 0.666, mDocY + mDocHeight);
     	    	break;
  	        } 
    	}
    }
    
    static public void drawLineEx(Graphics g, double x1, double y1, double x2, double y2) {
    	g.drawLine((int)(x1+0.49), (int)(y1+0.49), (int)(x2+0.49), (int)(y2+0.49));
    }
    static public void drawImageEx(Graphics g, BufferedImage img, double x, double y, double width, double height, ImageObserver observer) {
		g.drawImage(img, (int)(x + 0.49), (int)(y + 0.49), (int)(width + 0.49), (int)(height + 0.49), observer);  	
    }
    
    static public void drawRectEx(Graphics g, double x, double y, double width, double height) {
		g.drawRect((int)(x + 0.49), (int)(y + 0.49), (int)(width + 0.49), (int)(height + 0.49));  	
   }
    
    static public void fillRectEx(Graphics g, double x, double y, double width, double height) {
		g.fillRect((int)(x + 0.49), (int)(y + 0.49), (int)(width + 0.49), (int)(height + 0.49));  	
   }
   
    /**
     * 選択状態を描画
     * 
     * @param g
     */
    private void drawSelect(Graphics g) {
		double click_box = (int)(SELECT_BOX * mScale);
		double box_x, box_y, box_width, box_height;
		
        		
		for (int i = 0; i < select_list.size(); i++) {
			Group obj = (Group)select_list.get(i);
			box_x = mDocX + obj.mX  * mScale;
			box_y = mDocY + obj.mY * mScale;
			box_width = obj.mWidth * mScale;
			box_height = obj.mHeight * mScale;
			
			if (obj.mType == 2 || obj.mType == 3) {
				// 文字で選択中のときは薄い枠を表示する
		    	g.setColor(Color.LIGHT_GRAY);
		    	drawRectEx(g, box_x, box_y, box_width, box_height);				
			}
			g.setColor(Color.blue);
			// ドラック中のときでは選択線も描画する
			if (mSelectBoxNo > 0 && mSelectObj == obj) {
				drawRectEx(g, box_x, box_y, box_width, box_height);
			} 
			
			// 文字は上下左右にサイズ変更Boxが付く
			if (obj.mType == 2 || obj.mType == 3) {
	            fillRectEx(g, box_x-click_box/2 , box_y-click_box/2 , click_box, click_box);
	            fillRectEx(g, (box_x + box_width/2)-click_box/2 , box_y-click_box/2 , click_box, click_box);
	            fillRectEx(g, box_x+box_width-click_box/2 , box_y-click_box/2 , click_box, click_box);
	            fillRectEx(g, box_x-click_box/2 , box_y+box_height/2-click_box/2 , click_box, click_box);
	            fillRectEx(g, box_x+box_width-click_box/2 , box_y+box_height/2-click_box/2 , click_box, click_box);
	            fillRectEx(g, box_x-click_box/2 , box_y+box_height-click_box/2 , click_box, click_box);
	            fillRectEx(g, (box_x + box_width/2)-click_box/2 , box_y+box_height-click_box/2 , click_box, click_box);
	            fillRectEx(g, box_x+box_width-click_box/2 , box_y+box_height-click_box/2 , click_box, click_box);				
			} else {
			// グループとImageでは四隅だけに Boxが付く
				fillRectEx(g, box_x-click_box/2 , box_y-click_box/2 , click_box, click_box);
				fillRectEx(g, box_x+box_width-click_box/2 , box_y-click_box/2 , click_box, click_box);
				fillRectEx(g, box_x-click_box/2 , box_y+box_height-click_box/2 , click_box, click_box);
				fillRectEx(g, box_x+box_width-click_box/2 , box_y+box_height-click_box/2 , click_box, click_box);				
			}
//System.out.println("x="+box_x+" y="+box_y+" width="+box_width+" heiht="+box_height);   	
		}
    }
 
    /**
     * 現在の位置に従ってマウスカーソルを変える
     * 
     * @param x
     * @param y
     */
    public void setCursor(int x, int y) {
		for (int i = 0; i < select_list.size(); i++) {
			Group obj = (Group)select_list.get(i);
			int no = HitTestSelectBox(obj, x, y);
			if (no > 0) {
				//　グループでは移動しかないので、カーソルは手だけ
				if (obj.mType == 0) {
					setCursor(new Cursor(Cursor.HAND_CURSOR)); 
					return;					
				}
				switch(no) {
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
			
		}  
		
		for (int i = 0; i < display_list.size(); i++) {
			Group obj = (Group)display_list.get(i);
			if (obj.HitTest(x, y)) {
				setCursor(new Cursor(Cursor.HAND_CURSOR)); 
				return;
			}
		}

		setCursor(new Cursor(Cursor.DEFAULT_CURSOR)); 

    }
    
 	/**
     * 選択時に端に表示される■の位置にあるかどうか
	 */
    public int HitTestSelectBox(Group obj, int in_x, int in_y) {
		if (!obj.mSelected) 
			return 0;

   		double x, y, width, height;
   		x = mDocX + obj.mX * mScale;
   		y = mDocY + obj.mY * mScale;
   		width = obj.mWidth * mScale;
   		height = obj.mHeight * mScale;
   		double click_box = SELECT_BOX * mScale;
   		
     	// 左上の選択Box
      	if (x-click_box/2 <= in_x && in_x <= (x+click_box/2) && y-click_box/2 <= in_y && in_y <= (y+click_box/2))
        	return 2;

 
      	// 右上の選択Box
      	if (x+width-click_box/2 <= in_x && in_x <= (x+width+click_box/2) && y-click_box/2 <= in_y && in_y <= (y+click_box/2))
        	return 3;


      	// 左下の選択Box
      	if (x-click_box/2 <= in_x && in_x <= (x+click_box/2) && y+height-click_box/2 <= in_y && in_y <= (y+height+click_box/2))
        	return 4;

      	// 右下の選択Box
      	if (x+width-click_box/2 <= in_x && in_x <= (x+width+click_box/2) && y+height-click_box/2 <= in_y && in_y <= (y+height+click_box/2))
        	return 5;
      	
      	if (obj.mType == 2 || obj.mType == 3) {
     	 	// 中上の選択Box
      		if (x+width/2-click_box/2 <= in_x && in_x <= (x+width/2+click_box/2) && y-click_box/2 <= y && in_y <= (y+click_box/2))
        		return 6;
      		
     		// 左中の選択Box
      		if (x-click_box/2 <= in_x && in_x <= (x) && y+height/2-click_box/2 <= in_y && in_y <= (y+height/2+click_box/2))
        		return 7;

      		// 右中の選択Box
      		if (x+width-click_box/2 <= in_x && in_x <= (x+width+click_box/2) && y+height/2-click_box/2 <= in_y && in_y <= (y+height/2+click_box/2))
        		return 8;
      		
     		// 中下の選択Box
      		if (x+width/2-click_box/2 <= in_x && in_x <= (x+width/2+click_box/2) && y+height-click_box/2 <= in_y && in_y <= (y+height+click_box/2))
        		return 9;
		}

      	return 0;
    }
    
    /**
     * 指定した位置がドキュメント内かどうかチェックする
     * @param x
     * @param y
     * @return
     */
    public boolean checkInDoc(int x, int y) {
    	if (x < mDocX || x > mDocX + mDocWidth || y < mDocY || y > mDocY + mDocHeight) {
    		setCursor(new Cursor(Cursor.DEFAULT_CURSOR)); 
   			return false;
    	}
    	return true;
    }
  
	
	class MouseInput extends MouseInputAdapter {
		
		public void mousePressed(MouseEvent e) {
			if (! checkInDoc(e.getX(), e.getY())) {
				/**
				 * クリックしたとき　ドキュメント領域から離れていたら選択を解除する  2007.10.31
				 */
				clearSelect();
				repaint();
				return;
			}
			
			boolean ctrl = false;
			// モディファイキーの状態を得る
			int modal_key = e.getModifiersEx();
			// Ctrlキーが押されているかどうか
			if ((modal_key & InputEvent.CTRL_DOWN_MASK) != 0){
				//System.out.println("Ctrlキーが押されている");
				ctrl = true;
			}

			
	 		selection(e.getX(), e.getY(), ctrl);
	 		
			// 文字がダブルクリックされた
			if (e.getClickCount() == 2 && mSelectObj != null && (mSelectObj.mType == 2 || mSelectObj.mType == 3)) {
				TextObject obj = (TextObject)mSelectObj;
//				TopPanel.mTextInputDlg = new TextInputDialog(edenpo.Main_Frame, obj.getFontInfo());

				if (mSelectObj.mType == 2) {
					TopPanel.mTextInputDlg.setInfo(obj);
					setTextInput(1);
					mTextDClick = true;
					mSelectObj.mShow = false;
					redraw();
					TopPanel.mTextInputDlg.setVisible(true);
				} else {
					TopPanel.mTextInputDlg2.setInfo(obj);
					setTextInput(2);
					mTextDClick = true;
					mSelectObj.mShow = false;
					redraw();
					TopPanel.mTextInputDlg2.setVisible(true);
					
				}
				mTextDClick = false;
				mSelectObj.mShow = true;
				redraw();
				
				return;
			}
			
	 		end_x = drag_x = start_x = e.getX();
	 		end_y = drag_y = start_y = e.getY();
			
			repaint();
		}
		public void mouseDragged(MouseEvent e) {
			int x = e.getX();
			int y = e.getY();
			
			
			if (! GPanel.this.checkInDoc(x, y) && mSelectBoxNo == -1)
				return;
			
			if (mSelectBoxNo == -1) 
				return;
			
			// ドキュメントウィンドウからマウスが出た場合はドキュメントウィンドウ内に留める
			end_x = x;
			end_y = y;
			if (x < mDocX) {
				x = (int)mDocX;
			}
			if (x > mDocX+mDocWidth) {
				x = (int)(mDocX+mDocWidth);				
			}
			if (y < mDocY) {
				y = (int)mDocY;
			}
			if (y > mDocY+mDocHeight) {
				y = (int)(mDocY+mDocHeight);				
			}

//System.out.println("Drag GPanel.this.mSelectBoxNo = "+GPanel.this.mSelectBoxNo);
			if (mSelectBoxNo > 0) {
				moveSelect(x-drag_x, y-drag_y);
//				GPanel.this.moveSelect(x, y);
				drag_x = x;
				drag_y = y;
			} 
			
			repaint();
		}

		public void mouseReleased(MouseEvent e) {
			int x = e.getX();
			int y = e.getY();

			if (! GPanel.this.checkInDoc(x, y) && mSelectBoxNo == -1)
				return;
			
			if (mSelectBoxNo == -1) 
				return;
			
			// ドキュメントウィンドウからマウスが出た場合はドキュメントウィンドウ内に留める
			
			if (x < mDocX) {
				x = (int)mDocX;
			}
			if (x > mDocX+mDocWidth) {
				x = (int)(mDocX+mDocWidth);				
			}
			if (y < mDocY) {
				y = (int)mDocY;
			}
			if (y > mDocY+mDocHeight) {
				y = (int)(mDocY+mDocHeight);				
			}
			end_x = x;
			end_y = y;

			if (mSelectBoxNo > 0 && !(start_x == end_x && start_y == end_y) ) {
				// 選択されているオブジェクトは１つしかない
//				Group obj = (Group)select_list.get(0);
				Group obj = mSelectObj;
				// 移動かリサイズが行われたはずなので　オペレーションを実行する
				if (mSelectBoxNo == 1) {
					//double dx = (obj.mX - mOrgX) / mScale;
					//double dy = (obj.mY - mOrgY) / mScale;
					double dx = (obj.mX - mOrgX) ;
					double dy = (obj.mY - mOrgY) ;
					OpeMove ope =  new OpeMove(obj, dx, dy);
					
					start_x = end_x;
					start_y = end_y;
					
					edenpo.Stack.push(ope);
				} else {
					int x0 =start_x;
					int y0 = start_y;
					if (x0 > end_x)
						x0 = end_x;
					if (y0 > end_y)
						y0 = end_y;
					if (obj.mWidth < 0) {
						obj.mX = obj.mX + obj.mWidth;
						obj.mWidth *= -1.0;
					}
					if (obj.mHeight < 0) {
						obj.mY = obj.mY + obj.mHeight;
						obj.mHeight *= -1.0;
					}
					// 2007/12/30 移動速度向上
					if (obj.mType == 1) {
						((ImageObject)obj).size(obj.mX, obj.mY, obj.mWidth, obj.mHeight);						
					}
					OpeSize ope =  new OpeSize(obj, mOrgX, mOrgY, mOrgWidth, mOrgHeight);
//					OpeSize ope =  new OpeSize(obj, (double)Math.abs(start_x - end_x), (double)Math.abs(start_y - end_y));
					edenpo.Stack.push(ope);

				}
				redraw();
			} else {
				selection();
			}
		   	mSelectBoxNo = -1;
		   	mSelectObj = null;
		   	
			repaint();
		}
		public void mouseMoved(MouseEvent e) {
			if (! checkInDoc(e.getX(), e.getY()))
				return;
			
			setCursor(e.getX(), e.getY());  						
		}
		public void mouseEntered(MouseEvent e) {
			if (! checkInDoc(e.getX(), e.getY()))
				return;

  			setCursor(new Cursor(Cursor.HAND_CURSOR));  			
		}	
		public void mouseClicked(MouseEvent e) {
		}	
		
	}
	
	public void load(String path) {
		char type;
		
		DataInputStream in = null;
		try {
			int pos;
			
			// 初期化する
			clear();
		
			FileInputStream fs = new FileInputStream(path);
			in = new DataInputStream(fs);
			mFileChanel = fs.getChannel();
			
			// システム名を得る
			byte[] version = new byte[32];
			in.read(version, 0, 32);
			String str = new String(version);
			String readVersion = str.substring(0, edenpo.VERSION.length());
			
			if (! edenpo.VERSION.equals(readVersion)) {
				JOptionPane.showMessageDialog(this, Message.READ_VER_ERROR, "Error", JOptionPane.ERROR_MESSAGE);
               	in.close();
                repaint();
                return;				
			}
			
//pos = (int)mFileChanel.position();

			// ドキュメントサイズを読み込む
//			setDocType(in.readInt(), in.readInt());
			// 2007/12/31 ドキュメントの向きを設定
			int size = in.readInt();
			int muki = in.readInt();
			edenpo.DocDialog.setDocMuki(muki);
			setDocType(size, muki);
		
//pos = (int)mFileChanel.position();
				
			// 背景画像があれば読み込む
			int back_size = in.readInt();
			if (back_size != 0) {
				setBaseImage(readImage(in, back_size));
				
				/**
				 * この位置でreadImageを呼ぶとファイルポインタの位置がファイルの最後になるためここで戻している
				 */
				mFileChanel.position(12+back_size);
			
			} else {
				mBaseImage = null;
			}
			
			try {
				while(true) {
					pos = (int)mFileChanel.position();
					
					type = in.readChar();
					pos = (int)mFileChanel.position();
					Group obj = null;
					switch(type) {
					case 'G':
						obj = new Group();
						break;
					case 'I':
						obj = new ImageObject();
						break;
					case 'T':
						obj = new TextObject();
						break;
					case 'S':
						obj = new SashidashiObject();
						break;
					}
					if (obj != null) {
						obj.load(in);
						
						// ディスプレイリストに登録
						addList(obj);
					}
				}
			} catch(EOFException e){
//		   		System.out.println("ファイルを読み終えた");
		   		
		   		//　倍率を設定しなおす
		   		setScale(1.0f);
		   		
		   		// レイヤー番号を振りなおす
		   		resetLayer();
		   		
				// 選択を全て初期化する
				clearSelect();
				
				// ツールの初期化
				edenpo.Tool_Panel.setEnable(0);

				
	    	}
				
		
	   	} catch(Exception  ex){
    	}
	   	
        finally
        {
            try
            {
                if( in != null )
                {
                	in.close();
                }
            }
            catch( IOException e )
            {
                // close()メソッドはIOExceptionがthrows指定されているので
                // 一応受け取ります。
                e.printStackTrace();
            }

       }
        
        clearSelect();
		int [] indices = null;
		edenpo.Layer_Panel.setSelect(indices, false);
		redraw();
        repaint();
        
	}
	
	public void save(String path) {
		mFilePath = path;
		
		
		DataOutputStream fo = null;
		try {
			FileOutputStream fs = new FileOutputStream(path);
			fo = new DataOutputStream(fs);
		
			byte [] version = new byte[32];
			byte [] src = null;

			src = edenpo.VERSION.getBytes();
			for (int i = 0; i < src.length; i++) {
				version[i] = src[i];
			}
			fo.write(version, 0, 32);
						
			// ドキュメントサイズを書き出し
			fo.writeInt(mDocSize);
			fo.writeInt(mDocMuki);
			
			
			// 背景画像があれば書き出す
			if (mBaseImage != null) {
				writeImage(fo, mBaseImage);
			} else {
				fo.writeInt(0);
			}
			
		   	Group obj;
			for (int i = 0; i < display_list.size(); i++) {
				obj = (Group)display_list.get(i);
				obj.save(fo);
			}  
			
			
			JOptionPane.showMessageDialog(this, Message.SAVE_OK, "ファイルの保存",JOptionPane.INFORMATION_MESSAGE);
			
    	} catch(Exception  ex){
    	}
    	
        finally
        {
            try
            {
                if( fo != null )
                {
                	fo.close();
                }
            }
            catch( IOException e )
            {
                // close()メソッドはIOExceptionがthrows指定されているので
                // 一応受け取ります。
                e.printStackTrace();
            }

       }
		
	}
	
	public BufferedImage readImage(DataInputStream in, int size) {
		BufferedImage img = null;
    	FileOutputStream fo = null;
    	String tmp_file = mFilePath + "_tmp";
		try {
	    	/**
	    	* Imageデータを一時ファイルにjpegとして保存
	    	 */
			
	    	fo = new FileOutputStream(tmp_file);
			///////////////////////////////////////////////////////
	    	
		
			BufferedInputStream input = new BufferedInputStream(in);
			BufferedOutputStream output = new BufferedOutputStream(fo);
			byte buf[] = new byte[size];
			int len;
			int pos;
			pos = (int)mFileChanel.position();
			
			len = input.read(buf, 0, size);
			
			/**
			 * ここでファイルポインタの位置を設定しないと
			 * ずれる場合がある (sizeが奇数の時など）
			 */
			mFileChanel.position(pos+len);
			
			output.write(buf, 0, len);
			output.flush();
			output.close();
								
			img = ImageIO.read(new File(tmp_file));
		} catch(Exception  ex){
	    	assert(false);
	    }
		finally
		{
			try
			{
				if( fo != null )
				{
					fo.close();
					
					fileDelete(tmp_file);
	            }
	        }
			catch( IOException e )
			{
				// close()メソッドはIOExceptionがthrows指定されているので
				// 一応受け取ります。
				e.printStackTrace();
			}
		}
	
		return img;
	}
	
	public void writeImage(DataOutputStream out, BufferedImage img) {
		FileOutputStream tmp_fo = null;
		FileInputStream  in = null;
    	String tmp_file = mFilePath + "_tmp";
		try {
	    	/**
	    	* Imageデータを一時ファイルにjpegとして保存
	    	 */
	    	tmp_fo = new FileOutputStream(tmp_file);
			
	    	JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder((OutputStream)tmp_fo);
	    	encoder.encode(img);
	    	tmp_fo.close();
				
	    	/**
	    	 * jpegをバイナリーファイルととして読み込む
	    	 */ 
	    	in = new FileInputStream(tmp_file);
	    	int size = in.available();
	    	
	    	/**
	    	 * 画像のサイズを書き出す
	    	 */
	       	out.writeInt(size);        				
	   		
	       	/**
	       	 * 画像データを書き出す
	       	 */
	       	BufferedInputStream input = new BufferedInputStream(in);
	    	BufferedOutputStream output = new BufferedOutputStream(out);
	    	byte buf[] = new byte[256];
	    	int len;
	    	while ((len = input.read(buf)) != -1) {
	    		output.write(buf, 0, len);
	    	}
	    	output.flush();
		} catch(Exception  ex){
	    	assert(false);
	    }
		
		finally
		{
			try
			{
				if( in != null )
				{
					in.close();
	            }
				if( tmp_fo != null )
				{
					tmp_fo.close();
					
					fileDelete(tmp_file);
	            }
	        }
			catch( IOException e )
			{
				// close()メソッドはIOExceptionがthrows指定されているので
				// 一応受け取ります。
				e.printStackTrace();
			}
		}
	}
	
	private void fileDelete(String fname) {
		File file = new File( fname );
		file.delete();
	}
	
 }
