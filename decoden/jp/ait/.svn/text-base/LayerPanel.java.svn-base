package jp.ait;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;


/**
 * レイヤー
 * 
 * @author kagi
 */
class LayerPanel extends JPanel implements ActionListener,  MouseListener, ComponentListener, KeyListener   {

	
	/**
	 * レイヤーのセルのサイズ
	 */
	final static int LAYER_WIDTH = 72;
	final static int LAYER_HEIGHT = 70;
	
	/**
	 * セルに入る画像の大きさ
	 */
	final static int IMG_WIDTH = 40;
	final static int IMG_HEIGHT = 40;
	
	final static String TOP_IMG = "img/list_top.gif";
	final static String BOTTOM_IMG = "img/list_bottom.gif";
	
	final static String GROUP_IMG = "img/list_group.gif";
	final static String TEXT_IMG = "img/list_text.gif";
	final static String SASHIDASHI_IMG = "img/list_sashidashi.gif";
	
	final static Color FrameColor = new Color(255, 183, 184);
	
	private JList mList;
	private DefaultListModel mListModel;
	private JScrollPane mScrollPane;
	private JPanel mPanel;
	private JButton top_btn, bottom_btn;
	
	private int mListClickNo = -1;
	
	// 基本素材が存在するかどうか
	private boolean mBaseSozai;
	
	// キーリピートを無視するフラグ
	private boolean mKeyPress = false;
	
	LayerPanel() {
	    setBackground(edenpo.BACK_COLOR);
	    setLayout(new BorderLayout());
	   
	    /**
	     * JListを操作するための ListModel
	     */
	    mListModel = new DefaultListModel();

	    /**
	     * List部作成
	     */
	    mList = new JList(mListModel);
	    mList.setFixedCellWidth(70);
/*
	    EmptyBorder emptyBorder     = new EmptyBorder(4,4,4,4);
	    LineBorder lineBorder2 = new LineBorder(edenpo.BACK_COLOR, 4);
	    LineBorder lineBorder = new LineBorder(FRAME_COLOR, 1);
	    CompoundBorder  compoundBorder  = new CompoundBorder(lineBorder, lineBorder2);
*/
	    mList.setBorder(new BorderEx(FrameColor, 4));
//	    mList.setBorder(new BorderEx(Color.RED, 4));
	    //	    mList.addListSelectionListener(new LayerListListener());
	    setBackground(new Color(255, 255, 204));
	    mList.setValueIsAdjusting(true);
//	    mList.setDragEnabled(true);
//	    mList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	    mList.addMouseListener(this);
	    mList.setCellRenderer(new MyCellRenderer());
	    mList.setBorder(null);
	    KeyListener [] kls = mList.getKeyListeners();
	    for (int i = 0; i < kls.length; i++) {
	    	mList.removeKeyListener(kls[i]);
	    }
	    mList.addKeyListener(this);
	    
	    /**
	     * スクロールバーは付かないが　ボタンでスクロールさせる為 JList::ensureIndexIsVisibleを使っている
	     * 　ensureIndexIsVisible　を効かせる為に JScrollPaneを作成している
	     */
	    mPanel = new JPanel();
	    mPanel.setBackground(FrameColor);
//	    mPanel.setBackground(Color.CYAN);   TEST
//	    mPanel.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
	    mPanel.setBorder(new BorderEx(FrameColor, 4));
	    mScrollPane = new JScrollPane(mList, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		mScrollPane.setPreferredSize(new Dimension(75, 770));
	    mScrollPane.setBorder(null);
//	    mList.setBorder(new BorderEx(Color.BLUE, 2));   TEST
	    mPanel.add(mScrollPane);
	    
	    
	    mPanel.addComponentListener(this);
	   
	    /**
	     * 上へボタン
	     */
	    top_btn = new JButton(Utility.ImageIcon(this, "img/list_top.gif"));
	    top_btn.setDisabledIcon(Utility.ImageIcon(this, "img/list_top_disable.gif"));
	    top_btn.setRolloverIcon(Utility.ImageIcon(this, "img/list_top_rollover.gif"));
	    top_btn.setPressedIcon(Utility.ImageIcon(this, "img/list_top_press.gif"));
	    top_btn.setBorder(null);
	    top_btn.addActionListener(this);
	    top_btn.setEnabled(false);
    
	    add(top_btn, BorderLayout.NORTH);
	    add(mPanel, BorderLayout.CENTER);
	    
	    /**
	     * 下へボタン
	     */
    	bottom_btn = new JButton(Utility.ImageIcon(this, BOTTOM_IMG));
	    bottom_btn.setDisabledIcon(Utility.ImageIcon(this, "img/list_bottom_disable.gif"));
	    bottom_btn.setRolloverIcon(Utility.ImageIcon(this, "img/list_bottom_rollover.gif"));
	    bottom_btn.setPressedIcon(Utility.ImageIcon(this, "img/list_bottom_press.gif"));
	    bottom_btn.setBorder(null);
	    bottom_btn.setEnabled(false);
	    bottom_btn.addActionListener(this);
	    add(bottom_btn, BorderLayout.SOUTH);
	    
	    mBaseSozai = false;
		setPreferredSize(new Dimension(85, 200));
		
		
	}
	
	/**
	 * コマンド分析
	 */
	public void actionPerformed(ActionEvent e) {
		/**
		 * 上へボタン
		 */
		if (e.getSource() == top_btn) {
//System.out.println("top_btn  getFirstVisibleIndex="+mList.getFirstVisibleIndex());
			// 最初の可視セルを得る
			int pos = mList.getFirstVisibleIndex() -1;
			if (pos >= 0) {
				mList.ensureIndexIsVisible(pos);
			}
			setScrollEnabled();
//			bottom_btn.setEnabled(true);		
			return;
		}
		/**
		 * 下へボタン
		 */
		if (e.getSource() == bottom_btn) {
//System.out.println("bottom_btn  getFirstVisibleIndex="+mList.getLastVisibleIndex());
			// 最後の可視セルを得る
			int pos = mList.getLastVisibleIndex() + 1;
			mList.ensureIndexIsVisible(pos);
			
			setScrollEnabled();
//			top_btn.setEnabled(true);		
			return;
		}
	}
	
	/**
	 * 現在のスクロールの位置によって
	 * 上下のボタンの使用可/不可　を設定している
	 */
	public void setScrollEnabled() {
		mScrollPane.revalidate();
//System.out.println("getFirstVisibleIndex="+mList.getFirstVisibleIndex()+"  getLastVisibleIndex="+mList.getLastVisibleIndex()+"  num="+ mListModel.getSize()+"  getVisibleRowCount()="+mList.getVisibleRowCount());

		if (mList.getFirstVisibleIndex() > 0) {
			top_btn.setEnabled(true);		
		} else {
			top_btn.setEnabled(false);					
		}
		
		if (mList.getLastVisibleIndex() <= mListModel.getSize()-2) {
			bottom_btn.setEnabled(true);		
		} else {
			bottom_btn.setEnabled(false);					
		}
	}
	public void componentHidden(ComponentEvent e) {}
	public void componentMoved(ComponentEvent e) {}
	public void componentResized(ComponentEvent e) {
//System.out.println("width="+mPanel.getWidth()+"  height="+mPanel.getHeight());

		mScrollPane.setPreferredSize(new Dimension(mPanel.getWidth()-10, mPanel.getHeight()-15));
		mScrollPane.revalidate();
		
	}
	public void componentShown(ComponentEvent e) {
	}

	public void mouseClicked(MouseEvent e) {
//System.out.println("mouseClicked");
	}
	
	public void mouseEntered(MouseEvent e) {
//System.out.println("mouseEntered");
	}
	
	public void mouseExited(MouseEvent e) { 
	}
	public void mousePressed(MouseEvent e) { 
		int index = mList.locationToIndex(e.getPoint());
		Rectangle rect = mList.getCellBounds(index,index);
		
		boolean bIn;
		if(rect.contains(e.getPoint())) {
			MyListAction action = (MyListAction)mListModel.get(index);
			if (action.mBase) {
				// 基本素材では　選択処理は行わない
				mListClickNo = -1;
				edenpo.Graphics_Panel.clearSelect();
				edenpo.Graphics_Panel.repaint();
				return;
			}
			
			mListClickNo = index;
			int[] indices = mList.getSelectedIndices()	;
/*			
			int [] indices = new int[1];
			indices[0] = mListModel.getSize() - index -1;
			if (mBaseSozai) {
				indices[0]--;
			}
*/
			for (int i = 0; i < indices.length; i++) {
				indices[i] = mListModel.getSize() - indices[i] -1;
				if (mBaseSozai) {
					indices[i]--;
				}				
			}
			edenpo.Graphics_Panel.setSelect(indices);
    		edenpo.Graphics_Panel.repaint();
		} else {
			mListClickNo = -1;
		}
//System.out.println("mousePressed  mListClickNo="+mListClickNo);

	}
	public void mouseReleased(MouseEvent e) { 
		int index = mList.locationToIndex(e.getPoint());

		Rectangle rect = mList.getCellBounds(index,index);
		if(rect.contains(e.getPoint()) && mListClickNo >= 0) {
			MyListAction action = (MyListAction)mListModel.get(index);
			if (action.mBase) {
				// 基本素材では　選択処理は行わない
				mListClickNo = -1;
				return;
			}
			
      	  	if (mListClickNo != index) {
	     	  	Object obj = mListModel.getElementAt(mListClickNo);
	     	  	
	     	  	// 選択を全てクリア
	     	  	mList.clearSelection();
	     	  	
   	  			if (mListClickNo < index) {
     	  			mListModel.remove(mListClickNo);
     	  			mListModel.add(index, obj);
     	      		mList.addSelectionInterval(index, index);
     	  		} else {
   	  				mListModel.remove(mListClickNo);
   	  				mListModel.add(index, obj);
    	      		mList.addSelectionInterval(index, index);
     	  		}
    			mListClickNo = mListModel.getSize() - mListClickNo -1;
    			index = mListModel.getSize() - index -1;
         	  	
         	  
    			if (mBaseSozai) {
    				mListClickNo--;
    				index--;
    			}
    			OpeLayer ope = new OpeLayer(mListClickNo, index);
    			edenpo.Stack.push(ope);
       	 	
//    			edenpo.Graphics_Panel.exchange(mListClickNo, index);
        		edenpo.Graphics_Panel.redraw();
        		edenpo.Graphics_Panel.repaint();
     	  	}
//System.out.println("currentIndex="+mListClickNo+"  newIndex="+index);
     		mListClickNo = -1;
			
		}
		
//		edenpo.Graphics_Panel.setFocusable(true);
	}
	
	/**
	 *  Ctrl C が JListに横取りされて　コピーのショートカット機能が効かない為
	 *  ここで　そのハンドリングを行っている   2007/10/27
	 */
    public void keyPressed(KeyEvent e) {
 
    	/**
    	 * キーコードを得る
    	 */
        String KeyCode = e.getKeyText(e.getKeyCode());
        
        /**
         * 修飾キー情報を得る
         */
        String Modifiers = KeyEvent.getKeyModifiersText(e.getModifiers());
        
        /**
         *  Ctrl+C ならコピーを実行する
         */
        if (KeyCode.equalsIgnoreCase("C") && Modifiers.equalsIgnoreCase("Ctrl")) {
        	if (! mKeyPress) {
        		edenpo.Tool_Panel.execCopy();
        		
        		/**
        		 * キーリピートを無視するためのフラグ
        		 */
         		mKeyPress = true;
        	}
        }
    }
    public void keyReleased(KeyEvent e) {
    	mKeyPress = false;
//        System.out.println("Release: " + e.getKeyText(e.getKeyCode()));
    }
    public void keyTyped(KeyEvent e) {
//       System.out.println("Type: " + e.getKeyChar());
    }
  	
 	/**
	 * レイヤーとディスプレイリストの最後にオブジェクトを追加する。
	 * @param obj	追加するオブジェクト
	 */
	public void addObject(Group obj) {
		addObject(0, obj);
	}
	
	public void addObject(int index, Group obj) {
		// サムネイルイメージ作成
   		BufferedImage bimg = null;
   		
		switch(obj.getType()) {
		case 0:		// グループ
      		 try{
      			 ImageIcon icon = Utility.ImageIcon(this, GROUP_IMG);
        			
     			 
      			 bimg = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(),BufferedImage.TYPE_3BYTE_BGR);  // BufferedImageオブジェクトを作成
      			 Graphics g = bimg.createGraphics();  // Graphicsの取得
      			 g.drawImage(icon.getImage(), 0, 0, null);  // BufferedImageに描画させる
    	   
      			 g.dispose();
      			 bimg.flush();
      			 icon = null;
     			
    		 }
       		catch(Exception ex){}
 			break;
		case 1:		// イメージ
			{
				// ImageObjectからimgは使いまわしたく無いため
				BufferedImage tmp_img = obj.getImage();
			
				int width, height;
	        	if (tmp_img.getHeight() < tmp_img.getWidth()) {
	        		width = IMG_WIDTH;
	        		height = (int)(((double)tmp_img.getHeight() / tmp_img.getWidth()) * IMG_HEIGHT);
	        	} else {
	        		height = IMG_HEIGHT;
	        		width = (int)(((double)tmp_img.getWidth() / tmp_img.getWidth()) * IMG_WIDTH);
	        	}
				bimg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
				Graphics g = bimg.createGraphics();  // Graphicsの取得
				g.drawImage(tmp_img, 0, 0, width, height, null);  // BufferedImageに描画させる
				tmp_img = null;
				g.dispose();
				bimg.flush();
			}
			break;
		case 2:		// テキスト
		case 3:		// 差出人
			try{
				ImageIcon icon;
				if (obj.getType() == 2)
					icon = Utility.ImageIcon(this, TEXT_IMG);
				else {
					icon = Utility.ImageIcon(this, SASHIDASHI_IMG);
				}
      			 
    			bimg = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(),BufferedImage.TYPE_3BYTE_BGR);  // BufferedImageオブジェクトを作成
    	        Graphics g = bimg.createGraphics();  // Graphicsの取得
    	        g.drawImage(icon.getImage(), 0, 0, null);  // BufferedImageに描画させる
       			g.dispose();
       			bimg.flush();
    			icon = null;
   		 	}
     		 catch(Exception ex){}
			break;
		}

		if (bimg == null)
			return;

 	  	
 	  	// 選択を全てクリア
 	  	mList.clearSelection();
 	  	
		mListModel.add(index, new MyListAction(null, setImage(bimg), false));
		bimg = null;
  		mList.addSelectionInterval(index, index);
		
		setScrollEnabled();
	}
	
	public void addBaseSozai(BufferedImage img) {
		if (img == null)
			return;
		
		// 全部削除する
		mListModel.clear();
		

		mBaseSozai = true;
		mListModel.add(0, new MyListAction(null, setImage(img), true));
  		mList.addSelectionInterval(0, 0);
		
		setScrollEnabled();
	}

 	/**
	 * レイヤーとディスプレイリストの指定したオブジェクトを削除する。
	 * @param index  	削除するリストの位置
	 */
	public void removeObject(int index) {
		mListModel.remove(index);

		setScrollEnabled();
	}

	
	/**
	 * リスト項目の移動
 	 * @param start_no	移動元
	 * @param end_no	移動先
	 */
	public void exchange(int start_no, int end_no) {
		if (start_no == end_no)
			return;
			
		start_no--;
		end_no--;
		
		Object obj = mListModel.getElementAt(start_no);
 	  	if (start_no < end_no) {
 	  		mListModel.removeElementAt(start_no);
 	  		mListModel.insertElementAt(obj, end_no);
 	  	} else {
 	  		mListModel.removeElementAt(start_no);
 	  		mListModel.insertElementAt(obj, end_no);
 	  	}		
	}

	/**
	 * BufferedImageからリストの表示するアイコンを作成する
	 * 
 	 * @param img		イメージ
	 */
	public ImageIcon setImage(BufferedImage img) {   		
   		Image rep = new BufferedImage(LAYER_WIDTH, LAYER_HEIGHT, BufferedImage.TYPE_INT_BGR);
		Graphics g = rep.getGraphics();
	    g.setColor(Color.white);
		g.fillRect(0,0,LAYER_WIDTH,LAYER_HEIGHT);
 		
        
        int img_width = 0;
        int img_height = 0;
        if (img != null) {
        	if (img.getHeight() < img.getWidth()) {
        		img_width = IMG_WIDTH;
        		img_height = (int)(((double)img.getHeight() / img.getWidth()) * IMG_HEIGHT);
        	} else {
        		img_height = IMG_HEIGHT;
        		img_width = (int)(((double)img.getWidth() / img.getWidth()) * IMG_WIDTH);
        	}
        }
 /*
        ImageFilter fl;
        
//	 	fl = new ReplicateScaleFilter(img_width, img_height);
		fl = new AreaAveragingScaleFilter(img_width , img_height);			// こっちの方が鮮明（だが少し遅い）

		FilteredImageSource fis = new FilteredImageSource(img.getSource() , fl);
		Image rep2 = createImage(fis);
*/
        BufferedImage img2 = new BufferedImage(img_width, img_height, BufferedImage.TYPE_INT_ARGB);
		Graphics g2 = img2.createGraphics();  // Graphicsの取得
		g2.drawImage(img, 0, 0, img_width, img_height, null);  // BufferedImageに描画させる
		g2.dispose();
		img2.flush();

		g.drawImage(img2, LAYER_WIDTH/2 - img_width/2, LAYER_HEIGHT/2 - img_height/2, this);
		img2 = null;
		
		g.dispose();
		rep.flush();
		
		ImageIcon icon = new ImageIcon(rep);	
		rep = null;

		return icon;		
	}
	
	
	/**
	 * pathから画像ファイルを読みからリストの表示するアイコンを作成する
	 * 
 	 * @param no		画像の種類
 	 * @param path		パス
	 */
	public ImageIcon setImage(int no, String path) {
   		BufferedImage bimg = null;
   		Image rep;
   		
		rep = new BufferedImage(70, 70, BufferedImage.TYPE_INT_BGR);
		Graphics g = rep.getGraphics();
	    g.setColor(Color.red);
		g.fillRect(0,0,70,70);
	    g.setColor(Color.black);
	    
        try{
        	bimg = ImageIO.read(new File(path));
        }
        catch(Exception ex){
        	return null;
        }
        
        int img_width = 0;
        int img_height = 0;
        if (bimg != null) {
        	if (bimg.getHeight() < bimg.getWidth()) {
        		img_width = 40;
        		img_height = (int)(((double)bimg.getHeight() / bimg.getWidth()) * 40);
        	} else {
        		img_height = 40;
        		img_width = (int)(((double)bimg.getWidth() / bimg.getWidth()) * 40);
        	}
        }
/*
        ImageFilter fl;
        
		fl = new ReplicateScaleFilter(img_width, img_height);
//		fl = new AreaAveragingScaleFilter(img_width , img_height);			// こっちの方が鮮明（だが少し遅い）

		FilteredImageSource fis = new FilteredImageSource(bimg.getSource() , fl);
		Image rep2 = createImage(fis);
*/
        BufferedImage img2 = new BufferedImage(img_width, img_height, BufferedImage.TYPE_INT_ARGB);
		Graphics g2 = img2.createGraphics();  // Graphicsの取得
		g2.drawImage(bimg, 0, 0, img_width, img_height, null);  // BufferedImageに描画させる
		g2.dispose();
		img2.flush();

		g.drawImage(img2, 35 - img_width/2, 35 - img_height/2, this);
		
		g.dispose();

		ImageIcon icon = new ImageIcon(rep);	
		rep = null;
		
		return 	icon;
	}
	
	/**
	 * 他のパネルからレイヤーの選択状態を設定する
	 * @param indices  GraphicsPanelのdisplay_listとは順番が逆になっている
	 * @param sw
	 */
	public void setSelect(int [] indices, boolean sw) {
		mList.clearSelection();
		
		if (sw) {
			for (int i = 0; i < indices.length; i++) {
				indices[i] = mListModel.size() - indices[i] -1;
				
				if (mBaseSozai) {
					// 基本素材が存在する場合は　indexがひとつ増える
					indices[i]--;
				}
			}
			mList.setSelectedIndices(indices);
//System.out.println("setSelect num="+indices.length);
		} else {
			mList.repaint();
		}
	}
	
	/**
	 * リストの内容を初期化する
	 */
	public void clear() {
		
		// 全部削除する
		mListModel.clear();
		
		mBaseSozai = false;
		
		mListClickNo = -1;
	}

}


class MyListAction extends AbstractAction {
	Icon mIcon;
	boolean mBase;
	
	MyListAction(String text, Icon icon, boolean base) { 
		super(text,icon);
		
		mIcon = icon;
		mBase = base;
	}
	public Icon getIcon() {
		return mIcon;
	}
	
	public void actionPerformed(ActionEvent e){}
}


class LayerListListener implements ListSelectionListener {
	int currentIndex = -1;
	boolean changeList = false;
	

	
	public void valueChanged(ListSelectionEvent e) {

		System.out.println("valueChanged=");

		if (changeList)
			return;
		

    	JList list = (JList)e.getSource();
		if (e.getValueIsAdjusting() == true) {		// マウスが押された
  	    	 if (currentIndex == -1) {
  	    		 currentIndex = list.getSelectedIndex();
  	    	 }
  	    	 
  	    	int [] indices = list.getSelectedIndices();
 // 	    	edenpo.Graphics_Panel.setSelect(indices);
//System.out.println("currentIndex="+currentIndex);

		} else {
			if (currentIndex != -1) {
	     	  	int newIndex = list.getSelectedIndex();

	     	  	if (currentIndex == newIndex)
	     	  		return;
	     	  	
	     	  	DefaultListModel model = (DefaultListModel)list.getModel();
	     	  	if (currentIndex != newIndex) {
   	     	  		Object obj = model.getElementAt(currentIndex);
     	  			changeList = true;
	     	  		if (currentIndex < newIndex) {
	     	  			model.remove(currentIndex);
	     	  			model.add(newIndex, obj);
	     	  		} else {
	     	  			model.remove(currentIndex);
	     	  			model.add(newIndex, obj);
	     	  		}
     	  			changeList = true;
	     	  	}
//System.out.println("currentIndex="+currentIndex+"  newIndex="+newIndex);
	     	  	
	     	  	currentIndex = model.getSize() - currentIndex -1;
	     	  	newIndex = model.getSize() - newIndex -1;
	     	  	
	     	  	
//	     	 	
//				edenpo.Graphics_Panel.exchange(currentIndex, newIndex);
//	    		edenpo.Graphics_Panel.redraw();
	    		edenpo.Graphics_Panel.repaint();
	     	  	currentIndex = -1;
			}
		}

	}

}


class MyCellRenderer extends DefaultListCellRenderer {
	public Component getListCellRendererComponent(JList list, Object value, int index,
			boolean isSelected, boolean cellHasFocus) {
		
		MyListAction action = (MyListAction)value;
		

		// 背景色をセット
		if (isSelected) {
			setOpaque(true);			// これがないと選択時に背景色がセットされない
			setForeground(list.getSelectionForeground());
			setBackground(list.getSelectionBackground());
		} else {
			setOpaque(false);			// これがないと非選択時に背景色がリセットされない
		}
		
		if (action.mBase) {
			// 基本素材の場合の選択できない
			setOpaque(true);			
			isSelected = false;
		}
		
		ImageIcon icon = (ImageIcon)action.getValue(Action.SMALL_ICON);
		Image img = icon.getImage();
		Graphics g = img.getGraphics();
//System.out.println("DefaultListCellRenderer isSelected="+isSelected+" index="+index+"  cellHasFocus="+cellHasFocus);
		if (isSelected){
	   		g.setColor(Color.red);   
		} else {
	   		g.setColor(Color.white);    			
		}
   		g.drawRect(10, 10, 50, 48);			
	
		// 番号を描画
   		ListModel model = list.getModel();
		Font font = new Font("Default", Font.PLAIN, 10);
		g.setFont(font);
	    FontMetrics fm = g.getFontMetrics();
	    String number = Integer.toString(model.getSize()-index);
	    int fontDotSize = fm.stringWidth(number);
   		g.setColor(Color.white);    
   		g.fillRect(0, 60, 70, 10);
   		
   		g.setColor(Color.black);    		
   	    g.drawString(number, 35-fontDotSize/2, 68);
   	    
        Graphics2D g2 = (Graphics2D)g;
        g2.setStroke(new BasicStroke(1.0f));
        g.setColor(edenpo.BACK_COLOR);
  	    g.drawLine(0, LayerPanel.LAYER_HEIGHT-2, LayerPanel.LAYER_WIDTH, LayerPanel.LAYER_HEIGHT-2);
   	    

   		// Iconを描画
		setIcon(new ImageIcon(img));
		
		g.dispose();
		

		return this;
	}
}

class BorderEx extends AbstractBorder  {
	final static Color FRAME_COLOR = new Color(72, 171, 58);	
	
	private Color color;
	private int space;
	
	public BorderEx(Color color , int space) {
		this.color = color;
		this.space = space;
	}

	public Insets getBorderInsets(Component c) {
		return new Insets(space , space , space , space);
	}

	public void paintBorder(Component c , Graphics g , int x , int y , int w , int h) {
		g.setColor(color);
		g.drawRect(x , y , w - 1 , h - 1);
		g.drawRect(x+1 , y+1 , w - 2 , h - 2);
		g.drawRect(x+2 , y+2 , w - 3 , h - 3);
		g.drawRect(x+3 , y+3 , w - 4 , h - 4);
        Graphics2D g2 = (Graphics2D)g;
	    g2.setStroke(new BasicStroke(5.0f));
		g.drawLine(w-4, y, w-4, h-1);
		
	    g2.setStroke(new BasicStroke(1.0f));
		g.setColor(FRAME_COLOR);
		g.drawLine(x, y, x, h-1);
		g.drawLine(w-1, y, w-1, h-1);
		
	}
	
}
