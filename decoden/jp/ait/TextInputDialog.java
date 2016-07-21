package jp.ait;


import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.Document;
import javax.swing.border.*;


class TextInputDialog extends JDialog implements ActionListener, DocumentListener, ComponentListener, MouseListener   {
	
	// ダイアログの初期サイズ
	final static int FRAME_WIDTH = 704;
	final static int FRAME_HEIGHT = 570;
	final static int FRAME_HEIGHT2 = FRAME_HEIGHT + 40;
	
	public JTextArea text_area;
	Document doc;
	
	private JComboBox combo1, combo2, combo3, combo4, combo5;
	private JPopupMenu pop = new JPopupMenu("Kitty");
	private JPanel color_btn;
	private int [][] colors = 
			{{0, 0, 0}, {102, 0, 102}, {0, 51, 204}, {0, 102, 0}, {204, 153, 0}, {102, 51, 0}, {153, 0, 0}, 
			{102, 102, 102}, {102, 51, 255}, {0, 153, 255}, {0, 153, 0}, {255, 204, 0}, {255, 102, 0}, {255, 0, 0}, 
			{204, 204, 204}, {153, 153, 255},{153, 204, 255}, {153, 204, 51}, {255, 255, 51}, {255, 204, 153},  {255, 153, 153}, 
			{255, 255, 255}, {204, 204, 255}, {204, 255, 255}, {204, 255, 204}, {255, 255, 153}, {255, 255, 204}, {255, 204, 204}};
	private JPanel [] color_sel_btn = new JPanel[28];
	
	private FontInfo font_info;
	
	private JButton cancel_btn, ok_btn;
	private int mType;
	
	// 修正オブジェクト
	TextObject mObj = null;
	
	String mInitStr;
	
	TextInputDialog(Frame frame, FontInfo info, String title, int type) {
		super(frame, title, true);
		
		mType = type;
		font_info = info;

		Font font = new Font("Default", Font.PLAIN, 12);
		
//		getContentPane().setLayout(null);
		getContentPane().setBackground(new Color(255, 160, 161));
		
		
/*
		ImagePanel back_panel = new ImagePanel("img/TextInput/background.gif");
		back_panel.setPreferredSize(new Dimension(690, 420));
		add(back_panel);
		back_panel.setLayout(null);
*/
		ImagePanel back_panel = null;
		if (type == 0) {
			back_panel = new ImagePanel("img/TextInput/background.gif");
		} else {
			back_panel = new ImagePanel("img/TextInput/background2.gif");
			
		}
		back_panel.setPreferredSize(new Dimension(695, 420)); 	// これが無いと表示しない
		back_panel.setBackground(new Color(255, 160, 161));
		back_panel.setLayout(null);
		add(back_panel);
				
		int offsetX = 6;
		int offsetY = 6;
		
		JPanel panel2 = new JPanel();
		panel2.setLayout(null);
		panel2.setBackground(edenpo.BACK_COLOR2);
		if (type == 0) {
			panel2.setBounds(28 + offsetX, 210 + offsetY, 388, 258);
		} else {
			panel2.setBounds(29+ offsetX, 210 + offsetY, 388, 258);
			
		}
		panel2.setBorder(BorderFactory.createLineBorder(edenpo.FRAME_COLOR, 2));
		back_panel.add(panel2);
	
		JPanel panel3 = new JPanel();
		panel3.setLayout(null);
		panel3.setBounds(3, 3, 382, 88);
		panel3.setBackground(Color.white);
		panel2.add(panel3);
		
		JLabel label;
/*
		label = new JLabel(Utility.ImageIcon(this, "img/TextInput/comment2.gif"));
		label.setBounds(10, 0, 150, 11);
		panel3.add(label);
*/		
		text_area = new JTextArea();
		JScrollPane scroll  = new JScrollPane(text_area);
		text_area.setBounds(10, 10, 364, 65);
		text_area.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
//		scroll.setPreferredSize(new Dimension(250, 250));
//		panel3.add(scroll);
		scroll.setBounds(10, 13, 360, 65);
		panel3.add(scroll);
		doc = text_area.getDocument();
		doc.addDocumentListener(this);
	
		JPanel panel4 = new JPanel();
		panel4.setBorder(BorderFactory.createLineBorder(edenpo.FRAME_COLOR, 2));
		panel4.setLayout(null);
		panel4.setBackground(edenpo.BACK_COLOR2);
		panel4.setBounds(0, 95, 388, 163);
		panel2.add(panel4);
	
		label = new JLabel(Utility.ImageIcon(this, "img/TextInput/menu_label.gif"));
		label.setBounds(10, 5, 66, 142);
		panel4.add(label);
		
		int x, y, width, height;
		x = 80;
		y = 10;
	   
  		String localfont[];
	    String localfont2[];
        // まず、使用可能なフォントを探す。
		localfont = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
		java.util.Arrays.sort(localfont);
		// "ＭＳ" と "HG"が着くものは前に移動する
		localfont2 = new String[localfont.length];
		int j = 0;
		int i = 0;
		int pos = 0;
		for (i = 0; i < localfont.length; i++) {
			if (localfont[i].startsWith("ＭＳ") ) {
				localfont2[pos++] = localfont[i];
			}
		}
		for (i = 0; i < localfont.length; i++) {
			if (localfont[i].startsWith("HG") ) {
				localfont2[pos++] = localfont[i];
			}
		}
		for (i = 0; i < localfont.length; i++) {
			boolean found = false;
			for (j = 0; j < localfont2.length; j++) {
				if (localfont[i] == localfont2[j]) {	
					found = true;
					break;
				}
			}
			if (! found) {
				localfont2[pos++] = localfont[i];
			}
		}
		
		combo1 = new JComboBox(localfont2);
		combo1.setFont(font);
		combo1.setBounds(x, y, 300, 20);
		panel4.add(combo1);
		
		y += 30;
		
		width = 86;
		height = 18;
		
		String sizes[] = {"6", "7", "8", "9", "10", "11", "12", "14", "16", "18", "20", "22", "24", "26", "28", "30",
				"32", "34", "36", "38", "40", "42", "44", "46", "48", "50", "55", "60", "65", "70", "72", "74",
				"76", "78", "80", "82", "84", "90", "94", "98", "100", "110", "120", "140", "160", "180", "200" };
		combo2 = new JComboBox(sizes);
		combo2.setFont(font);
		combo2.setBounds(x, y, width, height);
		panel4.add(combo2);
		y += 30;
	
		String styles[] = {"標準", "太字", "斜体", "太字斜体"};
		combo3 = new JComboBox(styles);
		combo3.setFont(font);
		combo3.setBounds(x, y, width, height);
		panel4.add(combo3);
		y += 30;
		
		String yose[] = {"左寄せ", "中央寄せ", "右寄せ"};
		combo4 = new JComboBox(yose);
		combo4.setFont(font);
		combo4.setBounds(x, y, width, height);
		panel4.add(combo4);
		y += 30;
		
		String muki[] = {"横書き", "縦書き"};
		combo5 = new JComboBox(muki);
		combo5.setFont(font);
		combo5.setBounds(x, y, width, height);
		panel4.add(combo5);
		
		label = new JLabel("色");
		label.setBounds(175, 50, 22, 14);
		panel4.add(label);
		
		color_btn = new JPanel();
		color_btn.setBounds(195, 48, 32, 20);
		color_btn.setBackground(Color.black);
		color_btn.setBorder(new BevelBorder(BevelBorder.RAISED));
		panel4.add(color_btn);
				
		JButton color_select_btn;
		x = 175;
		y = 78;
		width = 24;
		height = 14;
		int dx = 28;
		int dy = 19;
		for (i = 0; i < colors.length; i++) {
			if (i != 0 && (i % 7) == 0) {
				x = 175;
				y += dy;
			}
			color_sel_btn[i] = new JPanel();
			color_sel_btn[i].setBounds(x, y, width, height);
			color_sel_btn[i].setBorder(new BevelBorder(BevelBorder.RAISED));

			color_sel_btn[i].setBackground(new Color(colors[i][0], colors[i][1], colors[i][2]));
			color_sel_btn[i].addMouseListener(this);	
			panel4.add(color_sel_btn[i]);
			x += dx;
		}
		
/*	
		cancel_btn = new JButton(Utility.ImageIcon(this, "img/close.gif"));
		cancel_btn.setRolloverIcon(Utility.ImageIcon(this, "img/close_rollover.gif"));
		cancel_btn.setPressedIcon(Utility.ImageIcon(this, "img/close_press.gif"));
		cancel_btn.setBorder(null);
		cancel_btn.setBounds(162, 292, 128, 21);
		cancel_btn.addActionListener(this);	
		back_panel.add(cancel_btn);
*/
		
		ok_btn = new JButton(Utility.ImageIcon(this, "img/ok.gif"));
		ok_btn.setRolloverIcon(Utility.ImageIcon(this, "img/ok_rollover.gif"));
		ok_btn.setPressedIcon(Utility.ImageIcon(this, "img/ok_press.gif"));
		ok_btn.setDisabledIcon(Utility.ImageIcon(this, "img/ok_disable.gif"));
		ok_btn.setBorder(null);
		if (type == 0) {
			ok_btn.setBounds(268, 500, 170, 34);
		} else {
			ok_btn.setBounds(268, 540, 170, 34);
		}
		ok_btn.addActionListener(this);	
//		ok_btn.setEnabled(false);	
		back_panel.add(ok_btn);
	

		// コンボボックスの初期値を設定
		combo2.setSelectedItem("12");
		combo3.setSelectedItem("標準");
		combo4.setSelectedItem("左寄せ");
		combo5.setSelectedItem("横書き");
		
		
		font_info.mFont = localfont2[0];
		font_info.mSize = 12;
		font_info.mStyle = 0;
		font_info.mTume = 0;
		font_info.mMuki = 0;
		font_info.mColor = Color.black;
		
		
		combo1.addActionListener(this); 
		combo2.addActionListener(this); 
		combo3.addActionListener(this); 
		combo4.addActionListener(this); 
		combo5.addActionListener(this); 

		edenpo.Graphics_Panel.setTextInputFont(font_info, false);
	
		
		if (type == 0) {
			setSize(FRAME_WIDTH, FRAME_HEIGHT);
			setMinimumSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
			setMaximumSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
		} else {
			setSize(FRAME_WIDTH, FRAME_HEIGHT2);
			setMinimumSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT2));
			setMaximumSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT2));
		}
		
		setResizable(false);	
		
		addComponentListener(this);
		mInitStr = null;

		
		// スクリーンの中央に配置
		Rectangle screen = getGraphicsConfiguration().getBounds();
		setLocation(screen.x + screen.width/2  - FRAME_WIDTH/2, 
				screen.y + screen.height/2 - FRAME_HEIGHT/2);

	}
	
	public void setInfo(TextObject obj) {
		mObj = obj;

		mInitStr = obj.getString();
		
		font_info = obj.getFontInfo();
			
		combo1.setSelectedItem(font_info.mFont);
		combo2.setSelectedItem(""+font_info.mSize);
		combo3.setSelectedIndex(font_info.mStyle);
		combo4.setSelectedIndex(font_info.mTume);
		combo5.setSelectedIndex(font_info.mMuki);
		color_btn.setBackground(font_info.mColor);
		
		combo1.repaint();
		combo1.revalidate();
//JOptionPane.showMessageDialog(this, "font="+font_info.mFont+"  size="+font_info.mSize+"  style="+font_info.mStyle);

setResizable(true);	
		repaint();
		// 少しサイズ変更しないと、JARではコンボボックスが更新しないため
		setSize(FRAME_WIDTH+1, FRAME_HEIGHT+1);
//setResizable(false);	

	}
	public FontInfo getInfo() {
		return font_info;
	}

	public void actionPerformed(ActionEvent e) {
		int index;
		String str;
		
		// フォント
		if (e.getSource() == combo1) {
			index = combo1.getSelectedIndex();
			str = (String)combo1.getSelectedItem();
			font_info.mFont = str;
			edenpo.Graphics_Panel.setTextInputFont(font_info, false);
			edenpo.Text_Panel.setFontInfo2(font_info);
			return;
		}
		// サイズ
		if (e.getSource() == combo2) {
			index = combo2.getSelectedIndex();
			str = (String)combo2.getSelectedItem();
			font_info.mSize = Integer.parseInt(str); 
			edenpo.Graphics_Panel.setTextInputFont(font_info, false);
			edenpo.Text_Panel.setFontInfo2(font_info);
			return;
		}
		
		// スタイル
		if (e.getSource() == combo3) {
			index = combo3.getSelectedIndex();
			str = (String)combo3.getSelectedItem();
			font_info.mStyle = (short)index;
			edenpo.Graphics_Panel.setTextInputFont(font_info, false);
			edenpo.Text_Panel.setFontInfo2(font_info);
			return;
		}
		
		// 文字詰め
		if (e.getSource() == combo4) {
			index = combo4.getSelectedIndex();
			str = (String)combo4.getSelectedItem();
			font_info.mTume = (short)index;
			edenpo.Graphics_Panel.setTextInputFont(font_info, false);
			edenpo.Text_Panel.setFontInfo2(font_info);
			return;
		}
		
		// 向き
		if (e.getSource() == combo5) {
			index = combo5.getSelectedIndex();
			str = (String)combo5.getSelectedItem();
			font_info.mMuki = (short)index;
			edenpo.Graphics_Panel.setTextInputFont(font_info, false);
			edenpo.Text_Panel.setFontInfo2(font_info);
			return;
		}
		
		// 閉じる
		if (e.getSource() == cancel_btn) {
//			hide();
			if (mObj == null) {
				edenpo.Text_Panel.setEnabled(false);
			}
			
			edenpo.Graphics_Panel.setTextInput(0);
			edenpo.Graphics_Panel.setTextInputFont(font_info, false);
			
			mObj = null;
			mInitStr = null;
			setVisible(false);
			return;
		}
		
		// 決定
		if (e.getSource() == ok_btn) {
			index = combo5.getSelectedIndex();
			str = (String)combo5.getSelectedItem();
			font_info.mMuki = (short)index;
			
			edenpo.Graphics_Panel.setTextInput(0);
			
			// 文字入力中のフォントを変更する
			edenpo.Graphics_Panel.setTextInputFont(font_info, false);
			
			str = text_area.getText();
			if (str.length() != 0) {
				if (mObj == null) {
					if (mType == 0) {
						OpeAddText ope = new OpeAddText(str, font_info);
						edenpo.Stack.push(ope);
					} else {
						OpeAddSashidashi ope = new OpeAddSashidashi(str, font_info);
						edenpo.Stack.push(ope);
					}
				} else {
					OpeTextInfo ope = new OpeTextInfo(mObj, str, font_info);
					edenpo.Stack.push(ope);
				}
			} else {
				mInitStr = null;				
			}
			
			mObj = null;
			setVisible(false);
			return;
		}
		
/*
  		for (int i = 0; i < colors.length; i++) {
   			if (e.getSource() == color_sel_btn[i]) {
   				Color col = new Color(colors[i][0], colors[i][1], colors[i][2]);
   				color_btn.setBackground(col);
   				color_btn.repaint();
   				font_info.mColor = col;
   				edenpo.Graphics_Panel.setTextInputFont(font_info, false);
   				edenpo.Text_Panel.setFontInfo2(font_info);
 			}
        }
*/
	}
	
	public void mouseEntered(MouseEvent e){}
	public void mousePressed(MouseEvent e){
  		for (int i = 0; i < colors.length; i++) {
   			if (e.getSource() == color_sel_btn[i]) {
   				Color col = new Color(colors[i][0], colors[i][1], colors[i][2]);
   				color_btn.setBackground(col);
   				color_btn.repaint();
   				font_info.mColor = col;
   				edenpo.Graphics_Panel.setTextInputFont(font_info, false);
   				edenpo.Text_Panel.setFontInfo2(font_info);
 			}
        }
	}
	
	public void mouseReleased(MouseEvent e){}
	public void mouseClicked(MouseEvent e){}
	public void mouseExited(MouseEvent e){}

	public void insertUpdate(DocumentEvent e)
	{
		Document d = e.getDocument();
		if (d == doc) {
			String str = text_area.getText();
			if (str.length() == 0) {
//				ok_btn.setEnabled(false);					
			} else {
				ok_btn.setEnabled(true);					
			}
			edenpo.Graphics_Panel.setTextInputStr(str);
		}
	}
					// 文字の削除
	public void removeUpdate(DocumentEvent e)
	{
		Document d = e.getDocument();
		if (d == doc) {
			String str = text_area.getText();
			edenpo.Graphics_Panel.setTextInputStr(str);
			if (str.length() == 0) {
//				ok_btn.setEnabled(false);		
			}
		}
	}
					// 文字以外の変更（フォーマットの変更等）
	public void changedUpdate(DocumentEvent e) {}
	
	protected void processWindowEvent(WindowEvent e) {
		if (e.getID() == WindowEvent.WINDOW_CLOSING ) {
			if (mObj == null) {
				edenpo.Text_Panel.setEnabled(false);
			}
			
			// Xボタンを押したとき　非表示にするだけにする。
			edenpo.Graphics_Panel.setTextInput(0);
			edenpo.Graphics_Panel.setTextInputFont(font_info, false);
			mObj = null;
			mInitStr = null;
			setVisible(false);
	    }
	}
	
	public void componentHidden(ComponentEvent e) {}
	public void componentMoved(ComponentEvent e) {}
	public void componentResized(ComponentEvent e) {
		if (isShowing()) {
			if (mType == 0) {
				setSize(FRAME_WIDTH, FRAME_HEIGHT);
			} else {
				setSize(FRAME_WIDTH, FRAME_HEIGHT2);				
			}
		}
	}
	
	public void componentShown(ComponentEvent e) {
		/**
		 * この処理がないと、前回のrolloverが残ってしまう
		 */
//		cancel_btn.setEnabled(false);
//		cancel_btn.setEnabled(true);
	
		edenpo.Text_Panel.setEnabled(true);
		edenpo.Text_Panel.setFontInfo2(font_info);
		
		if (mInitStr != null) {
			text_area.setText(mInitStr);
			mInitStr = null;
		} else {
			text_area.setText("");
		}
		
		text_area.requestFocus();

/*
		var elm = doc.getElementById('text'); // テキストエリアのelement取得
		elm.focus();

		if (elm.createTextRange) {
		  var range = elm.createTextRange();
		  range.move('character', elm.value.length);
		  range.select();
		} else if (elm.setSelectionRange) {
		  elm.setSelectionRange(elm.value.length, elm.value.length);
		}
*/
	}


}
