package jp.ait;

import java.awt.event.*;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

import javax.swing.border.*;

class TextPanel  extends JPanel implements ActionListener, MouseListener  {
	final static Color LABEL_COLOR = new Color(255, 94, 100);	
	
	private  JLabel label1;
	private  JLabel label2;
	private  JLabel label3;
	private  JLabel label4;
	private  JLabel label5;
	private  JComboBox combo1, combo2, combo3, combo4, combo5;
	private JPopupMenu pop = new JPopupMenu("ColorSelect");
	private  JPanel color_btn;
	private ColorMenuItem [] mitems = new ColorMenuItem[28];
	private int [][] colors = 
			{{0, 0, 0}, {102, 0, 102}, {0, 51, 204}, {0, 102, 0}, {204, 153, 0}, {102, 51, 0}, {153, 0, 0}, 
			{102, 102, 102}, {102, 51, 255}, {0, 153, 255}, {0, 153, 0}, {255, 204, 0}, {255, 102, 0}, {255, 0, 0}, 
			{204, 204, 204}, {153, 153, 255},{153, 204, 255}, {153, 204, 51}, {255, 255, 51}, {255, 204, 153},  {255, 153, 153}, 
			{255, 255, 255}, {204, 204, 255}, {204, 255, 255}, {204, 255, 204}, {255, 255, 153}, {255, 255, 204}, {255, 204, 204}};
	
	
	 FontInfo mFontInfo;
	 TextObject mObj = null;

	
	private boolean mEnabled;
	
	TextPanel() {
		int y = 0;
		int x = 6;
		int height = 19;
		
		setLayout(null);
		setBorder(BorderFactory.createLineBorder(edenpo.FRAME_COLOR2, 2));
		setBackground(Color.white);
		
		Font font = new Font("Default", Font.PLAIN, 12);
		
		JLabel title_label = new JLabel(Utility.ImageIcon(this, "img/text_top.gif"));
		title_label.setBounds(0, y, 170, 25);
	 	add(title_label);
		y += height+10;
	 	
		label1 = new JLabel ("フォント");
		label1.setBounds(x, y, 100, height);
		label1.setForeground(LABEL_COLOR);
 		add(label1);
  		y += height;
		
	    String localfont[];
	    String localfont2[];
	        // まず、使用可能なフォントを探す。
		localfont = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
//	    int size = localfont.length;
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
	    combo1.setBounds(x, y, 160, height);
	    combo1.setBorder(null);
	    combo1.setBackground(Color.white);
    	add(combo1);
     	y += height;
    	
    	y += 2;
   		
    	label2 = new JLabel ("サイズ");
    	label2.setForeground(LABEL_COLOR);
    	label2.setBounds(x, y, 80, height);
		add(label2);
		String sizes[] = {"6", "7", "8", "9", "10", "11", "12", "14", "16", "18", "20", "22", "24", "26", "28", "30",
				"32", "34", "36", "38", "40", "42", "44", "46", "48", "50", "55", "60", "65", "70", "72", "74",
				"76", "78", "80", "82", "84", "90", "94", "98", "100", "110", "120", "140", "160", "180", "200" };
		combo2 = new JComboBox(sizes);
		combo2.setFont(font);
		combo2.setBounds(x, y+height, 70, height);
		combo2.setBorder(null);
		combo2.setBackground(Color.white);
		add(combo2);
		
		label3 = new JLabel ("スタイル");
		label3.setForeground(LABEL_COLOR);
		label3.setBounds(82, y, 80, height);
		add(label3);
		String styles[] = {"標準", "太字", "斜体", "太字斜体"};
		combo3 = new JComboBox(styles);
		combo3.setFont(font);
		combo3.setBounds(82, y+height, 80, height);
		combo3.setBorder(null);
		combo3.setBackground(Color.white);
		add(combo3);
    	y += (height*2);
    	
    	y += 2;
   		   		
    	label4 = new JLabel ("文字揃え");
    	label4.setForeground(LABEL_COLOR);
    	label4.setBounds(x, y, 80, height);
		add(label4);
    	y += height;
		String yose[] = {"左寄せ", "中央寄せ", "右寄せ"};
		combo4 = new JComboBox(yose);
		combo4.setFont(font);
		combo4.setBounds(x, y, 80, height);
		combo4.setBorder(null);
		combo4.setBackground(Color.white);
		add(combo4);
       	y += height;
        y += 2;
       
	   		
        label5 = new JLabel ("向き");
        label5.setForeground(LABEL_COLOR);
        label5.setBounds(x, y, 80, height);
		add(label5);
       	y += height;
		String muki[] = {"横書き", "縦書き"};
		combo5 = new JComboBox(muki);
		combo5.setFont(font);
		combo5.setBounds(x, y, 70, height);
		combo5.setBorder(null);
		combo5.setBackground(Color.white);
		add(combo5);
		
		color_btn = new JPanel();
		color_btn.setBounds(82, y-1, 60, height+2);
		color_btn.setBorder(new BevelBorder(BevelBorder.RAISED));
		color_btn.setBackground(Color.black);
		add(color_btn);
		
		
		
		for (i = 0; i < colors.length; i++) {
			mitems[i] = new ColorMenuItem("  ", this);
			mitems[i].setBackground(new Color(colors[i][0], colors[i][1], colors[i][2]));
			mitems[i].addActionListener(this);
 			pop.add(mitems[i]);
		
		}
		pop.setPopupSize(200, 108);
		pop.setLayout(new GridLayout(4, 7));

		combo1.addActionListener(this); 
		combo2.addActionListener(this); 
		combo3.addActionListener(this); 
		combo4.addActionListener(this); 
		combo5.addActionListener(this); 
		color_btn.addMouseListener(this);
		
		setSize(185, 160);
 		setPreferredSize(new Dimension(168, 160));
 		setBackground(Color.white);
		setVisible(true);
		
		mEnabled = true;
		setEnabled(false);
	}
	
	public void setFontInfo(TextObject obj) {
		mObj = obj;
		mFontInfo = new FontInfo();
		mFontInfo.copy(obj.getFontInfo());
		
		// 修正イベントを発生させない。
		combo1.removeActionListener(this); 
		combo2.removeActionListener(this); 
		combo3.removeActionListener(this); 
		combo4.removeActionListener(this); 
		combo5.removeActionListener(this); 
		color_btn.removeMouseListener(this);
		
		
		combo1.setSelectedItem(mFontInfo.mFont);
		combo2.setSelectedItem(""+mFontInfo.mSize);
		combo3.setSelectedIndex(mFontInfo.mStyle);
		combo4.setSelectedIndex(mFontInfo.mTume);
		combo5.setSelectedIndex(mFontInfo.mMuki);
		color_btn.setBackground(mFontInfo.mColor);
		
		combo1.addActionListener(this); 
		combo2.addActionListener(this); 
		combo3.addActionListener(this); 
		combo4.addActionListener(this); 
		combo5.addActionListener(this); 
		color_btn.addMouseListener(this);
		

	}
	public void setFontInfo2(FontInfo info) {
		
		// 修正イベントを発生させない。
		combo1.removeActionListener(this); 
		combo2.removeActionListener(this); 
		combo3.removeActionListener(this); 
		combo4.removeActionListener(this); 
		combo5.removeActionListener(this); 
		color_btn.removeMouseListener(this);
		
		
		combo1.setSelectedItem(info.mFont);
		combo2.setSelectedItem(""+info.mSize);
		combo3.setSelectedIndex(info.mStyle);
		combo4.setSelectedIndex(info.mTume);
		combo5.setSelectedIndex(info.mMuki);
		color_btn.setBackground(info.mColor);
		
		combo1.addActionListener(this); 
		combo2.addActionListener(this); 
		combo3.addActionListener(this); 
		combo4.addActionListener(this); 
		combo5.addActionListener(this); 
		color_btn.addMouseListener(this);		
	}
	
	public void actionPerformed(ActionEvent e) {
		int index;
		String str;
			
		// フォント
		if (e.getSource() == combo1) {
			index = combo1.getSelectedIndex();
			str = (String)combo1.getSelectedItem();
			mFontInfo.mFont = str;
			
			OpeTextInfo ope = new OpeTextInfo(mObj, mObj.getString(), mFontInfo);
			edenpo.Stack.push(ope);
			
			edenpo.Graphics_Panel.setTextInputFont(mFontInfo, true);
			
			return;
		}
		// サイズ
		if (e.getSource() == combo2) {
			index = combo2.getSelectedIndex();
			str = (String)combo2.getSelectedItem();
			mFontInfo.mSize = Integer.parseInt(str); 
			
			OpeTextInfo ope = new OpeTextInfo(mObj, mObj.getString(), mFontInfo);
			edenpo.Stack.push(ope);
			
			edenpo.Graphics_Panel.setTextInputFont(mFontInfo, true);
			return;
		}
			
		// スタイル
		if (e.getSource() == combo3) {
			index = combo3.getSelectedIndex();
			str = (String)combo3.getSelectedItem();
			mFontInfo.mStyle = (short)index;
			
			OpeTextInfo ope = new OpeTextInfo(mObj, mObj.getString(), mFontInfo);
			edenpo.Stack.push(ope);

			edenpo.Graphics_Panel.setTextInputFont(mFontInfo, true);
			
			return;
		}
			
		// 文字詰め
		if (e.getSource() == combo4) {
			index = combo4.getSelectedIndex();
			str = (String)combo4.getSelectedItem();
			mFontInfo.mTume = (short)index;
			
			OpeTextInfo ope = new OpeTextInfo(mObj, mObj.getString(), mFontInfo);
			edenpo.Stack.push(ope);
			
			edenpo.Graphics_Panel.setTextInputFont(mFontInfo, true);
			return;
		}
			
		// 向き
		if (e.getSource() == combo5) {
			index = combo5.getSelectedIndex();
			str = (String)combo5.getSelectedItem();
			mFontInfo.mMuki = (short)index;
			
			OpeTextInfo ope = new OpeTextInfo(mObj, mObj.getString(), mFontInfo);
			edenpo.Stack.push(ope);

			edenpo.Graphics_Panel.setTextInputFont(mFontInfo, true);
			return;
		}
/*			
        if (e.getSource() == color_btn) {
    		pop.show(this, color_btn.getX(), color_btn.getY()+color_btn.getHeight());
        }
*/       
   		for (int i = 0; i < colors.length; i++) {
   			if (e.getSource() == mitems[i]) {
//   			if(e.getActionCommand().equals("ATION_MENU"+i)) {
  				Color col = new Color(colors[i][0], colors[i][1], colors[i][2]);
  				
  				// メニュー選択ではイベントが２回来るので、　ここで回避している
   				if (mFontInfo.mColor.getRGB() == col.getRGB())
  					return;
  				
  				mFontInfo.mColor = col;
   				color_btn.setBackground(col);
   				color_btn.repaint();
    			
    			OpeTextInfo ope = new OpeTextInfo(mObj, mObj.getString(), mFontInfo);
    			edenpo.Stack.push(ope);

    			edenpo.Graphics_Panel.setTextInputFont(mFontInfo, true);
    			return;
  			}
        }
	}
	
	public void mouseEntered(MouseEvent e){}
	public void mousePressed(MouseEvent e){
		if (mEnabled) {
			if (e.getSource() == color_btn) {
				pop.show(this, color_btn.getX(), color_btn.getY()+color_btn.getHeight());
			}
		}
	}
	
	public void mouseReleased(MouseEvent e){}
	public void mouseClicked(MouseEvent e){}
	public void mouseExited(MouseEvent e){}

	public void setEnabled(boolean sw) {
		if (mEnabled == sw) {
			return;
		}
		mEnabled = sw;
		
		label1.setEnabled(sw);
		label2.setEnabled(sw);
		label3.setEnabled(sw);
		label4.setEnabled(sw);
		label5.setEnabled(sw);
		combo1.setEnabled(sw);
		combo2.setEnabled(sw);
		combo3.setEnabled(sw);
		combo4.setEnabled(sw);
		combo5.setEnabled(sw);
		color_btn.setEnabled(sw);
	}
	
	static void reset(TextObject obj) {
		
	}
}

class ColorMenuItem extends JMenuItem {
	  /* コンストラクタ */
	  public ColorMenuItem(String label, ActionListener listener) {
	    super(label);
	    this.setFont(null);
		addActionListener(listener);
//		setBorder(new BevelBorder(BevelBorder.RAISED, Color.white, Color.black));		
		setBorder(new LineBorder(new Color(240, 240, 240) , 2));
	  }


	  /* メニュー選択状態変更 */
	  public void menuSelectionChanged(boolean sw) {
//	    super.menuSelectionChanged(sw);
	    
		if (sw == true) {
//			setBorder(new LineBorder(Color.BLACK , 2));
//			setBorder(new BevelBorder(BevelBorder.LOWERED));
			setBorder(new BevelBorder(BevelBorder.RAISED, Color.white, Color.black));		
			
		} else {
//			setBorder(new BevelBorder(BevelBorder.RAISED, Color.white, Color.black));		
//			setBorder(new LineBorder(Color.WHITE , 2));
			setBorder(new LineBorder(new Color(240, 240, 240) , 2));
		}
	}
}

