package jp.ait;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import java.net.*;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import java.awt.image.*;

import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.pdf.PdfWriter;
import com.sun.image.codec.jpeg.*;


/**
 * メインウィンドウ上部のパネル
 * 
 * @author kagi
 */
class TopPanel   extends JPanel implements ActionListener, ComponentListener  {
	
	private GPanel mGPanel;
	private LayerPanel mLayer;
	static TextInputDialog	mTextInputDlg;
	static TextInputDialog	mTextInputDlg2;		// 差出人入力
	private BasicSozai mBSozaiDlg;
	private Sozai mSozaiDlg;
	private UserSozai mUserSozaiDlg;
	private Preview mPreviewDlg;
	private JFrame mFrame;
	
	private JButton preview_btn;
	private JButton open_btn;
	private JButton save_btn;
	private JButton order_btn;
	private JButton help_btn;
	private JButton size_btn;
	
	private JButton btn[] = new JButton[10];
	
	TopPanel(JFrame frame, GPanel gpanel, LayerPanel layer) {
		setBackground(edenpo.BACK_COLOR);

		mFrame = frame;

		mGPanel = gpanel;
		mLayer = layer;
		
//		Insets insets = new Insets(1, 4, 1, 4);
		Insets insets = new Insets(0, 1, 0, 1);
		
		setLayout(new BorderLayout());
				
		setBackground(Color.white);
      	setBorder(BorderFactory.createLineBorder(Color.white));
		
		TopPanelImage panel = new TopPanelImage();
		panel.setBackground(edenpo.BACK_COLOR);
		panel.setLayout(null);
		

		int y = 55;
		
		/**
		 * 用紙画像の選択 ボタン
		 */
		btn[0] = new JButton(Utility.ImageIcon(this, "img/TopMenu/basic.gif"));
		btn[0].setDisabledIcon(Utility.ImageIcon(this, "img/TopMenu/basic_disable.gif"));
		btn[0].setRolloverIcon(Utility.ImageIcon(this, "img/TopMenu/basic_rollover.gif"));
		btn[0].setPressedIcon(Utility.ImageIcon(this, "img/TopMenu/basic_press.gif"));
		btn[0].setBounds(54, y, 95, 37);
		btn[0].setBorder(null);
		btn[0].addActionListener(this);
		panel.add(btn[0]);

		
        /**
         * 飾り画像の選択 ボタン
         */
		btn[1] = new JButton(Utility.ImageIcon(this, "img/TopMenu/add.gif"));
		btn[1].setDisabledIcon(Utility.ImageIcon(this, "img/TopMenu/add_disable.gif"));
		btn[1].setRolloverIcon(Utility.ImageIcon(this, "img/TopMenu/add_rollover.gif"));
		btn[1].setPressedIcon(Utility.ImageIcon(this, "img/TopMenu/add_press.gif"));
		btn[1].setBounds(152, y, 99, 37);
		btn[1].setBorder(null);
		btn[1].addActionListener(this);
		panel.add(btn[1]);

	    /**
	     * パソコン画像の選択 ボタン
	     */
		btn[2] = new JButton(Utility.ImageIcon(this, "img/TopMenu/user.gif"));
		btn[2].setDisabledIcon(Utility.ImageIcon(this, "img/TopMenu/user_disable.gif"));
		btn[2].setRolloverIcon(Utility.ImageIcon(this, "img/TopMenu/user_rollover.gif"));
		btn[2].setPressedIcon(Utility.ImageIcon(this, "img/TopMenu/user_press.gif"));
		btn[2].setBounds(254, y, 111, 37);
		btn[2].setBorder(null);
		btn[2].addActionListener(this);
		panel.add(btn[2]);

		/**
		 * メッセージ入力 ボタン
		 */
		btn[3] = new JButton(Utility.ImageIcon(this, "img/TopMenu/message.gif"));
		btn[3].setDisabledIcon(Utility.ImageIcon(this, "img/TopMenu/message_disable.gif"));
		btn[3].setRolloverIcon(Utility.ImageIcon(this, "img/TopMenu/message_rollover.gif"));
		btn[3].setPressedIcon(Utility.ImageIcon(this, "img/TopMenu/message_press.gif"));
		btn[3].setBounds(366, y, 92, 37);
		btn[3].setBorder(null);
		btn[3].addActionListener(this);
		panel.add(btn[3]);
		
		/**
		 * 差出人入力 ボタン
		 */
		btn[4] = new JButton(Utility.ImageIcon(this, "img/TopMenu/sashidashi.gif"));
		btn[4].setDisabledIcon(Utility.ImageIcon(this, "img/TopMenu/sashidashi_disable.gif"));
		btn[4].setRolloverIcon(Utility.ImageIcon(this, "img/TopMenu/sashidashi_rollover.gif"));
		btn[4].setPressedIcon(Utility.ImageIcon(this, "img/TopMenu/sashidashi_press.gif"));
		btn[4].setBounds(462, y, 71, 37);
		btn[4].setBorder(null);
		btn[4].addActionListener(this);
		panel.add(btn[4]);

		
		JLabel separator = new JLabel("       ");
		panel.add(separator);
		
		/**
		 * プレビュー ボタン
		 */
		preview_btn = new JButton(Utility.ImageIcon(this, "img/TopMenu/preview.gif"));
		preview_btn.setDisabledIcon(Utility.ImageIcon(this, "img/TopMenu/preview_disable.gif"));
		preview_btn.setRolloverIcon(Utility.ImageIcon(this, "img/TopMenu/preview_rollover.gif"));
		preview_btn.setPressedIcon(Utility.ImageIcon(this, "img/TopMenu/preview_press.gif"));
		preview_btn.setBounds(603, 54, 75, 24);
		preview_btn.setBorder(null);
		preview_btn.addActionListener(this);
		preview_btn.setEnabled(false);
		panel.add(preview_btn);
		
		separator = new JLabel("       ");
		panel.add(separator);
		
		
		/**
		 * 保存 ボタン
		 */
		save_btn = new JButton(Utility.ImageIcon(this, "img/TopMenu/save.gif"));
		save_btn.setDisabledIcon(Utility.ImageIcon(this, "img/TopMenu/save_disable.gif"));
		save_btn.setRolloverIcon(Utility.ImageIcon(this, "img/TopMenu/save_rollover.gif"));
		save_btn.setPressedIcon(Utility.ImageIcon(this, "img/TopMenu/save_press.gif"));
		save_btn.setBounds(745, 54, 43, 24);
		save_btn.setBorder(null);
		save_btn.addActionListener(this);
		save_btn.setEnabled(false);
		panel.add(save_btn);
		
		/**
		 * 注文 ボタン
		 */
		order_btn = new JButton(Utility.ImageIcon(this, "img/TopMenu/order.gif"));
		order_btn.setDisabledIcon(Utility.ImageIcon(this, "img/TopMenu/order_disable.gif"));
		order_btn.setRolloverIcon(Utility.ImageIcon(this, "img/TopMenu/order_rollover.gif"));
		order_btn.setPressedIcon(Utility.ImageIcon(this, "img/TopMenu/order_press.gif"));
		order_btn.setBounds(796, 44, 48, 48);
		order_btn.setBorder(null);
		order_btn.addActionListener(this);
		order_btn.setEnabled(false);
		panel.add(order_btn);
		
		/**
		 * 開く ボタン
		 */
		open_btn = new JButton(Utility.ImageIcon(this, "img/TopMenu/open.gif"));
		open_btn.setDisabledIcon(Utility.ImageIcon(this, "img/TopMenu/open_disable.gif"));
		open_btn.setRolloverIcon(Utility.ImageIcon(this, "img/TopMenu/open_rollover.gif"));
		open_btn.setPressedIcon(Utility.ImageIcon(this, "img/TopMenu/open_press.gif"));
		open_btn.setBounds(846, 42, 81, 47);
		open_btn.setBorder(null);
		open_btn.addActionListener(this);
		panel.add(open_btn);
		
		/**
		 * ヘルプ ボタン
		 */
		help_btn = new JButton(Utility.ImageIcon(this, "img/TopMenu/help.gif"));
		help_btn.setDisabledIcon(Utility.ImageIcon(this, "img/TopMenu/help_disable.gif"));
		help_btn.setRolloverIcon(Utility.ImageIcon(this, "img/TopMenu/help_rollover.gif"));
		help_btn.setPressedIcon(Utility.ImageIcon(this, "img/TopMenu/help_press.gif"));
		help_btn.setBounds(790, 15, 139, 19);
		help_btn.setBorder(null);
		help_btn.addActionListener(this);
		panel.add(help_btn);
		
	    /**
		 * サイズボタンを作成
		 */
	    size_btn = new JButton(Utility.ImageIcon(this, "img/size.gif"));
	    size_btn.setRolloverIcon(Utility.ImageIcon(this, "img/size_rollover.gif"));
	    size_btn.setPressedIcon(Utility.ImageIcon(this, "img/size_press.gif"));
	    size_btn.setBounds(668, 18, 121, 18);
	    size_btn.setBorder(null);
	    size_btn.addActionListener(this);
	    panel.add(size_btn);
	    
		
		add(panel, BorderLayout.CENTER);
		
 		setPreferredSize(new Dimension(940, 115));
		
		FontInfo font_info = new FontInfo();
		FontInfo font_info2 = new FontInfo();
		mTextInputDlg = new TextInputDialog(frame, font_info, "メッセージ入力", 0);
		mTextInputDlg2 = new TextInputDialog(frame, font_info2, "差出人入力",1);
		
		mBSozaiDlg = new BasicSozai(frame);
		mSozaiDlg = new Sozai(frame);
		mUserSozaiDlg = new UserSozai(frame);
		mPreviewDlg = new Preview(frame);
		mPreviewDlg.setInitSize();
		
	    addComponentListener(this);

		
		// 素材情報のxmlファイルを読む
		readXML(edenpo.TemplateURL);
	}
	
	public void setEnabled(boolean sw) {
		if (sw) {
			preview_btn.setEnabled(true);
			save_btn.setEnabled(true);
			order_btn.setEnabled(true);	
		} else {
			preview_btn.setEnabled(false);
			save_btn.setEnabled(false);
			order_btn.setEnabled(false);	
		}
		
		if (edenpo.PostURL == null) {
			order_btn.setEnabled(false);				
		}
	}

	/**
	 * コマンド分析
	 */
	public void actionPerformed(ActionEvent e) {
		BufferedImage img;
		
		/**
		 * 用紙画像の選択
		 */
        if(e.getSource() == btn[0]){
        	mBSozaiDlg.setVisible(true);
        
        /**
         * 飾り画像の選択
         */
		} else if (e.getSource() == btn[1]){
	       	mSozaiDlg.setVisible(true);
	   
	    /**
	     * パソコン画像の選択
	     */
		} else if (e.getSource() == btn[2]){
			mUserSozaiDlg.setVisible(true);
		
		/**
		 * メッセージ入力
		 */
		} else if (e.getSource() == btn[3]){
			edenpo.Graphics_Panel.setTextInput(1);
			mTextInputDlg.text_area.setText("");
			edenpo.Graphics_Panel.setTextInputFont(mTextInputDlg.getInfo(), false);
			mTextInputDlg.setVisible(true);
		
		/**
		 * 差出人入力
		 */
		} else if (e.getSource() == btn[4]){
			edenpo.Graphics_Panel.setTextInput(2);
			mTextInputDlg2.text_area.setText("");
			edenpo.Graphics_Panel.setTextInputFont(mTextInputDlg2.getInfo(), false);
			mTextInputDlg2.setVisible(true);
		
		/**
		 * プレビュー
		 */
		} else if (e.getSource() == preview_btn){
			edenpo.Graphics_Panel.pushDocInfo();
			
			mPreviewDlg.setVisible(true);
			
		/**
		 * 開く
		 */
		} else if (e.getSource() == open_btn){
	    	if (edenpo.Graphics_Panel.getListSize() > 0) {
	    		int value = JOptionPane.showConfirmDialog(edenpo.Main_Frame, 
					"現在までのデザイン作成データが失われますが、よろしいですか？", "確認", JOptionPane.YES_NO_OPTION);
	    		if (value != JOptionPane.YES_NO_OPTION) {
	    			return;
	    		}
	    	}

			FileDialog fd = new FileDialog(mFrame , "ファイルの呼び出し",  FileDialog.LOAD);
			fd.setFile("*.edp");
			fd.setVisible(true);
			
            if(fd.getFile() != null) {
            	String path = fd.getDirectory() + fd.getFile();
            	
            	String fname = fd.getFile();
            	
            	// タイトルを設定
            	edenpo.Graphics_PanelM.setTitle(fname.substring(0, fname.length()-4));
            	
            	edenpo.Graphics_Panel.load(path);
            }
        /**
         * 保存
         */
		} else if (e.getSource() == save_btn){
			FileDialog fd = new FileDialog(mFrame , "保存",  FileDialog.SAVE);
//			fd.setFileFilter(new MyFileFilter());
			//fd.setFile("*.edp");
			fd.setVisible(true);
			
			boolean bNewName = false;
            if(fd.getFile() != null) {
            	String fname = fd.getFile();
            	if (fname.length() < 5) {
            		fname += ".edp";
            		fd.setFile(fname);
            		bNewName = true;
            	} else {
            		String fname2 = fname.substring(fname.length()-4);
            		if (! fname2.equalsIgnoreCase(".edp")) {
                   		fname += ".edp";                   	            			
                   		fd.setFile(fname);

                		bNewName = true;
           		}
            	}
            	
            	String path = fd.getDirectory() + fd.getFile();
            	
           		File file = new File(path);
           		if (bNewName && file.exists()) {
    	    		int value = JOptionPane.showConfirmDialog(edenpo.Main_Frame, 
    	    				path+"は既に存在します。\n上書きしますか？", "保存", JOptionPane.YES_NO_OPTION);
    	    		if (value != JOptionPane.YES_NO_OPTION) {
    	    			return;
    	    		}
           		
            	}
            	edenpo.Graphics_Panel.save(path);
            }
	
        /**
         * 注文
         */
		} else if (e.getSource() == order_btn){
			
			order_btn.setEnabled(false);
			
			// 現在のドキュメント描画情報をセーブ
			edenpo.Graphics_Panel.pushDocInfo();
			
			// PDF作成
			PDFCtrl.SendPDF(edenpo.DocDialog.getDocMuki());
			
			// 現在のドキュメント描画情報を戻す
			edenpo.Graphics_Panel.popDocInfo();
			
			order_btn.setEnabled(true);

		/**
		 * 用紙の向き選択
		 */
		} else if (e.getSource() == size_btn){
			edenpo.DocDialog.setVisible(true);
			return;
			
		/**
		 * ヘルプボタン
		 */
		} else if (e.getSource() == help_btn){
			PDFCtrl.showURL(edenpo.SERVER+"decoca_howto_use.pdf");
			return;
		}
	}
	public void componentHidden(ComponentEvent e) {}
	public void componentMoved(ComponentEvent e) {}
	public void componentResized(ComponentEvent e) {
		
		// リサイズしたときボタンの位置を変える
		open_btn.setLocation(new Point(e.getComponent().getWidth()-95, open_btn.getY()));
		help_btn.setLocation(new Point(e.getComponent().getWidth()-180, help_btn.getY()));
//		size_btn.setLocation(new Point(e.getComponent().getWidth()-180, size_btn.getY()));
	}
	public void componentShown(ComponentEvent e) {}
	
	/**
	 * サーバーからxmlファイルを読み取り素材の画像ファイルを取得する
	 * @param xml_path
	 */
	private void readXML(String xml_path) {
	
       	URLConnection con = null;
	    try {
			 long before, after;
			 
			URL url = new URL(xml_path);
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
			 
	        // ドキュメントビルダーファクトリを生成
	        DocumentBuilderFactory dbfactory = DocumentBuilderFactory.newInstance();
	        // ドキュメントビルダーを生成
	        DocumentBuilder builder = dbfactory.newDocumentBuilder();
	        // パースを実行してDocumentオブジェクトを取得
	        Document doc = builder.parse(in);

	        ProgressDialog pdlg = new ProgressDialog();
	        
	        Node node;
	        int type;
	        String stype, path, order, title;
	        // ルート要素を取得
	        Element root = doc.getDocumentElement();
	        NodeList list = root.getElementsByTagName("file");
	        // page要素の数だけループ
	        for (int i=0; i < list.getLength() ; i++) {
	        	
	        	// プログレスバーを更新
	        	pdlg.setPos((int)(((double)i / list.getLength() * 100)));
			 
	        	// files要素を取得
	        	Element element = (Element)list.item(i);
	          
	        	// typeの値を取得
	        	node = element.getElementsByTagName("type").item(0);
	        	stype = node.getFirstChild().getNodeValue();
	        	type = Integer.parseInt(stype);
          
	        	// pahtの値を取得
	        	node = element.getElementsByTagName("path").item(0);
	        	path = node.getFirstChild().getNodeValue();
		      
	        	// orderの値を取得
	        	node = element.getElementsByTagName("order").item(0);
	        	order = node.getFirstChild().getNodeValue();
		      
	        	// titleの値を取得
	        	node = element.getElementsByTagName("title").item(0);
	        	title = node.getFirstChild().getNodeValue();
		      
//System.out.println("type="+type+"  path="+path+"  order="+order+"  title="+title);
	        	if (i == list.getLength()-1) {
	               	pdlg.setPos(100);	                
	        	}
	        	if (type == 0) {		// 基本素材
					mBSozaiDlg.setSozaiData(Integer.parseInt(order), path,  title);
	        	} else {
					mSozaiDlg.setSozaiData(type, Integer.parseInt(order), path, title);		    	  
	        	}
	        }
	        
	        pdlg.setVisible(false);

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	}
	
}

/**
 * Topパネルの下に画像を描画するためのPanel
 * 
 * @author kagi
 */
class  TopPanelImage extends JPanel {
//	java.awt.Image mImg;
	  
	TopPanelImage() {
	    
		setBackground(edenpo.BACK_COLOR);

	}
	
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g); 

	    ImageIcon icon = Utility.ImageIcon(this, "img/top_menu.gif");
	    java.awt.Image  img = icon.getImage();
	    g.drawImage(img, 0, 0, this);
	    img = null;
	}
	
}

/*
class FileFilterEx extends javax.swing.filechooser.FileFilter {
	private String extension , msg;
	public FileFilterEx(String extension , String msg) {
		this.extension = extension;
		this.msg = msg;
	}
	public boolean accept(java.io.File f) {
        if(f.isDirectory()) {
			return true;
		}
        if (extension == ".jpg") {
        	String [] ext = {".jpg", ".jpeg", ".JPG", ".JPEG"};
        	for (int i = 0; i < ext.length; i++) {
        		 if (f.getName().endsWith(ext[i])) {
        			 return true;
        		 }
        	}
        	return false;
        } else if (extension == ".gif") {
           	if (f.getName().endsWith(extension))
        		return true;
           	
           	if (f.getName().endsWith(".GIF"))
        		return true;
        	
           	return false;
        }
        
        return false;
	}
	public String getDescription() { return msg; }
}
*/

class MyFileFilter extends javax.swing.filechooser.FileFilter {

	public MyFileFilter() {
		super();
	}


	public boolean accept(File file) {
		if (file != null) {
			/* ディレクトリなら */
			/* true を返す */
			if (file.isDirectory()) {
				return true;
			}

			String extention = getExtentionFromFile(file);
			if (extention == null) {
			} else {
				if (extention.equals("xls")) {
					return true;
				}
			}
		}
		return false;
	}

	public String getDescription() {
		return "デコ電(eps)"; 
	}

	private String getExtentionFromFile(File file) {
		if(file == null) {
			return null;
		}

		String filename = file.getName();

		/* 最後のピリオド位置を取得 */
		int i = filename.lastIndexOf('.');
		if(i == -1) {
			return null;
		}

		if ((i>0) && (i< (filename.length()-1) ) ) {
			/* 最後のピリオドより後の文字列を小文字で返す */
			return filename.substring(i+1).toLowerCase();
		} else {
			return null;
		}
	}
}


