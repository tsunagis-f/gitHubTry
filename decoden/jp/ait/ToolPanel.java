package jp.ait;


import java.awt.*;
import java.awt.event.*;

import javax.swing.*;


class ToolPanel extends JPanel  implements ActionListener {
	private JButton btn[] = new JButton[8];
	
	final static int btn_width = 34;
	final static int btn_height = 34;
	final static int space = 5;
	
	GPanel mGPanel;
	
	Group[] select_obj;
	
	ToolPanel(GPanel gpanel) {	
		mGPanel = gpanel;
		
		int y = 0;
		int x = 10;
		JLabel label;
		
		
		setLayout(null);
	    setBorder(BorderFactory.createLineBorder(edenpo.FRAME_COLOR2, 6));
	    setBackground(Color.white);
//	    setBackground(Color.black);
		
		label = new JLabel(Utility.ImageIcon(this, "img/tool_top.gif"));
		label.setBounds(0, y, 170, 25);
 		add(label);
		y += (25+space);
		
		QuitAction quitAction = new QuitAction(mGPanel, btn);
	   
		btn[0] = new JButton(Utility.ImageIcon(this, "img/tool/rotate.gif"));
		btn[0].setBounds(x, y, btn_width, btn_height);
		btn[0].setBorder(null);
		btn[0].setDisabledIcon(Utility.ImageIcon(this, "img/tool/rotate_disable.gif"));
		btn[0].setRolloverIcon(Utility.ImageIcon(this, "img/tool/rotate_rollover.gif"));
		btn[0].setPressedIcon(Utility.ImageIcon(this, "img/tool/rotate_press.gif"));
		btn[0].getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ctrl R"), quitAction.NAME);
		btn[0].getActionMap().put(quitAction.NAME, quitAction);
 		add(btn[0]);
 		//ボタンにイベントの監視人をつける
 		btn[0].addActionListener(this);
 		
 		Font font = new Font("Default", Font.PLAIN, 11);

 		label = new JLabel("画像を90度右に回転");
 		label.setFont(font);
 		label.setBounds(x+btn_width+5, y-12+8, 100, btn_height+5);
		add(label);
		
		y += (btn_height+space);
		
 		btn[1] = new JButton (Utility.ImageIcon(this, "img/tool/copy.gif"));
 		btn[1].setBounds(x, y, btn_width, btn_height);
 		btn[1].setBorder(null);
		btn[1].setDisabledIcon(Utility.ImageIcon(this, "img/tool/copy_disable.gif"));
		btn[1].setRolloverIcon(Utility.ImageIcon(this, "img/tool/copy_rollover.gif"));
		btn[1].setPressedIcon(Utility.ImageIcon(this, "img/tool/copy_press.gif"));
		btn[1].getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ctrl C"), quitAction.NAME);
		btn[1].getActionMap().put(quitAction.NAME, quitAction);
		add(btn[1]);
 		btn[1].addActionListener(this);

 		label = new JLabel("選択画像をコピー");
 		label.setFont(font);
 		label.setBounds(x+btn_width+5, y-12+8, 100, btn_height+5);
		add(label);
		
		y += (btn_height+space);
		
 		btn[2] = new JButton (Utility.ImageIcon(this, "img/tool/group.gif"));
		btn[2].setDisabledIcon(Utility.ImageIcon(this, "img/tool/group_disable.gif"));
		btn[2].setRolloverIcon(Utility.ImageIcon(this, "img/tool/group_rollover.gif"));
		btn[2].setPressedIcon(Utility.ImageIcon(this, "img/tool/group_press.gif"));
		btn[2].getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ctrl G"), quitAction.NAME);
		btn[2].getActionMap().put(quitAction.NAME, quitAction);
		btn[2].setBorder(null);
 		btn[2].setBounds(x, y, btn_width, btn_height);
 		add(btn[2]);
 		btn[2].addActionListener(this);


 		label = new JLabel("選択画像をグループ化");
 		label.setFont(font);
 		label.setBounds(x+btn_width+5, y-12+8, 120, btn_height+5);
		add(label);
		
 		y += (btn_height+space);
		
 		btn[3] = new JButton (Utility.ImageIcon(this, "img/tool/ungroup.gif"));
		btn[3].setDisabledIcon(Utility.ImageIcon(this, "img/tool/ungroup_disable.gif"));
		btn[3].setRolloverIcon(Utility.ImageIcon(this, "img/tool/ungroup_rollover.gif"));
		btn[3].setPressedIcon(Utility.ImageIcon(this, "img/tool/ungroup_press.gif"));
		btn[3].getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ctrl U"), quitAction.NAME);
		btn[3].getActionMap().put(quitAction.NAME, quitAction);
		btn[3].setBorder(null);
 		btn[3].setBounds(x, y, btn_width, btn_height);
 		add(btn[3]);
 		btn[3].addActionListener(this);

 		label = new JLabel("グループ化解除");
 		label.setFont(font);
 		label.setBounds(x+btn_width+5, y-12+8, 80, btn_height+5);
		add(label);
		
 		y += (btn_height+space);
		
 		btn[4] = new JButton (Utility.ImageIcon(this, "img/tool/front.gif"));
		btn[4].setDisabledIcon(Utility.ImageIcon(this, "img/tool/front_disable.gif"));
		btn[4].setRolloverIcon(Utility.ImageIcon(this, "img/tool/front_rollover.gif"));
		btn[4].setPressedIcon(Utility.ImageIcon(this, "img/tool/front_press.gif"));
		btn[4].getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ctrl F"), quitAction.NAME);
		btn[4].getActionMap().put(quitAction.NAME, quitAction);
		btn[4].setBorder(null);
 		btn[4].setBounds(x, y, btn_width, btn_height);
		add(btn[4]);
		btn[4].addActionListener(this);

		label = new JLabel("選択画像を前面へ");
 		label.setFont(font);
 		label.setBounds(x+btn_width+5, y-12+8, 100, btn_height+5);
		add(label);
		
 		y += (btn_height+space);
 		
		
 		btn[5] = new JButton (Utility.ImageIcon(this, "img/tool/back.gif"));
		btn[5].setDisabledIcon(Utility.ImageIcon(this, "img/tool/back_disable.gif"));
		btn[5].setRolloverIcon(Utility.ImageIcon(this, "img/tool/back_rollover.gif"));
		btn[5].setPressedIcon(Utility.ImageIcon(this, "img/tool/back_press.gif"));
		btn[5].getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ctrl B"), quitAction.NAME);
		btn[5].getActionMap().put(quitAction.NAME, quitAction);
		btn[5].setBorder(null);
 		btn[5].setBounds(x, y, btn_width, btn_height);
 		add(btn[5]);
 		btn[5].addActionListener(this);

 		label = new JLabel("選択画像を背面へ");
 		label.setFont(font);
 		label.setBounds(x+btn_width+5, y-12+8, 100, btn_height+5);
		add(label);
		
 		y += (btn_height+space);
		
  		btn[6] = new JButton (Utility.ImageIcon(this, "img/tool/undo.gif"));
		btn[6].setDisabledIcon(Utility.ImageIcon(this, "img/tool/undo_disable.gif"));
		btn[6].setRolloverIcon(Utility.ImageIcon(this, "img/tool/undo_rollover.gif"));
		btn[6].setPressedIcon(Utility.ImageIcon(this, "img/tool/undo_press.gif"));
		btn[6].getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ctrl Z"), quitAction.NAME);
		btn[6].getActionMap().put(quitAction.NAME, quitAction);
		btn[6].setBorder(null);
 		btn[6].setBounds(x, y, btn_width, btn_height);
 		add(btn[6]);
  		btn[6].addActionListener(this);

  		label = new JLabel("ーつ前の状態に戻る");
 		label.setFont(font);
 		label.setBounds(x+btn_width+5, y-12+8, 100, btn_height+5);
		add(label);
		
 		y += (btn_height+space);
		
  		btn[7] = new JButton (Utility.ImageIcon(this, "img/tool/delete.gif"));
		btn[7].setDisabledIcon(Utility.ImageIcon(this, "img/tool/delete_disable.gif"));
		btn[7].setRolloverIcon(Utility.ImageIcon(this, "img/tool/delete_rollover.gif"));
		btn[7].setPressedIcon(Utility.ImageIcon(this, "img/tool/delete_press.gif"));
		btn[7].getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("DELETE"), quitAction.NAME);
		btn[7].getActionMap().put(quitAction.NAME, quitAction);
		btn[7].setBorder(null);
 		btn[7].setBounds(x, y, btn_width, btn_height);
 		add(btn[7]);
 		btn[7].addActionListener(this);

 		label = new JLabel("選択画像を削除");
 		label.setFont(font);
 		label.setBounds(x+btn_width+5, y-12+8, 100, btn_height+5);
		add(label);
		
		
   	    setBorder(BorderFactory.createLineBorder(edenpo.BACK_COLOR2, 3));
// 		setPreferredSize(new Dimension(185, 330)); /*←これを追加*/
// 		setBackground(Color.white);
		setVisible(true);
	}
	
	
	public void actionPerformed(ActionEvent e) {
       	Group[] objs;
    	objs = mGPanel.getSelectedObject();
       	
		if (e.getSource() == btn[0]) {
       		OpeRotate ope = new OpeRotate(objs[0]);
			edenpo.Stack.push(ope);
		} else if (e.getSource() == btn[1]) {
        	OpeCopy ope = new OpeCopy(objs[0]);
			edenpo.Stack.push(ope);
		} else if (e.getSource() == btn[2]) {
        	OpeGroup ope = new OpeGroup(objs);
			edenpo.Stack.push(ope);
		} else if (e.getSource() == btn[3]) {
        	OpeUnGroup ope = new OpeUnGroup(objs[0]);
			edenpo.Stack.push(ope);
		} else if (e.getSource() == btn[4]) {
        	OpeFront ope = new OpeFront(objs[0]);
			edenpo.Stack.push(ope);
		} else if (e.getSource() == btn[5]) {
        	OpeBack ope = new OpeBack(objs[0]);
			edenpo.Stack.push(ope);
		} else if (e.getSource() == btn[6]) {
        	new OpeUndo();
		} else if (e.getSource() == btn[7]) {
//System.out.println("delete1");
        	OpeDelete ope = new OpeDelete(objs);
			edenpo.Stack.push(ope);
       }
	}

	public void setSelect(Group[] objs) {
		select_obj = objs;
		
		if (objs.length == 0) 
			return;
		
		// 1つだけ選択
		if (objs.length == 1) {
			switch(objs[0].mType){
			case 0: // グループ
				setEnable(5);
				break;
			case 1: // イメージ
				setEnable(2);			
				break;
			case 2: // テキスト
				setEnable(3);			
				break;
			}
		} else {
			// 複数選択
			setEnable(4);			
		}
	}
	/**
	 *　ボタンの使用可/不可の状態を設定
	 * @param no
	 *          0:全部使用不可  起動時とオブジェクトが無くもなくてUndoも無い場合
	 *          1:全部使用可
	 *          2:イメージを1つ選択
	 *          3:文字を1つ選択
	 *          4:複数選択
	 *          5:グループ
	 *          21:イメージを１つだけ選択しているが最前面にある
	 *          22:イメージを１つだけ選択しているが最後面にある
	 *          31:文字を１つだけ選択しているが最前面にある
	 *          32:文字を１つだけ選択しているが最後面にある
	 */
	public void setEnable(int no) {
		int i;
		
		switch(no) {
		case 7:
			// 前面を使用不可にする
			btn[4].setEnabled(false);
			btn[5].setEnabled(true);
			return;
		case 8:
			// 後面を使用不可にする
			btn[4].setEnabled(true);
			btn[5].setEnabled(false);
			return;
		case 9:
			// 前後面を使用不可にする
			btn[4].setEnabled(false);
			btn[5].setEnabled(false);
			return;
		}
		
		for (i = 0; i < btn.length; i++) {
			btn[i].setEnabled(false);
		}
		switch(no) {
		case 0:
			break;
		case 1:
			for (i = 0; i < btn.length; i++) {
				btn[i].setEnabled(true);
			}
			break;
		case 2:
			// 画像だけ１つ選択
			btn[0].setEnabled(true);
			btn[1].setEnabled(true);
			btn[4].setEnabled(true);
			btn[5].setEnabled(true);
			btn[6].setEnabled(true);
			btn[7].setEnabled(true);
			break;
		case 3:
			// 文字だけ１つ選択
			btn[1].setEnabled(true);
			btn[4].setEnabled(true);
			btn[5].setEnabled(true);
			btn[6].setEnabled(true);
			btn[7].setEnabled(true);
			break;
		case 4:
			// 複数選択のときは
			// グループ化とUndoとDeleteが有効
			btn[2].setEnabled(true);
			btn[6].setEnabled(true);
			btn[7].setEnabled(true);
			break;
		case 5:
			// グループを選択
			btn[1].setEnabled(true);
			btn[3].setEnabled(true);
			btn[4].setEnabled(true);
			btn[5].setEnabled(true);
			btn[6].setEnabled(true);
			btn[7].setEnabled(true);
			break;
		case 6:
			// Undoだけ
			btn[6].setEnabled(true);
			break;
		}
	}
	
	/**
	 * 他のオブジェクトから　コピーを実行する
	 */
	public void execCopy() {
		if (btn[1].isEnabled()) {
	      	Group[] objs;
	      	
	    	objs = mGPanel.getSelectedObject();
	        OpeCopy ope = new OpeCopy(objs[0]);
			edenpo.Stack.push(ope);			
		}
	}
}


//Actionクラスの内容
class QuitAction extends AbstractAction {
	GPanel mGPanel;
	JButton [] btn;

	QuitAction(GPanel gpanel, JButton [] btns) {
		mGPanel = gpanel;
		btn = btns;

		// Actionの名前やアイコンなどを設定。
		// この設定が、GUIに反映される
		putValue(Action.NAME, "Open");
		putValue(Action.SHORT_DESCRIPTION, "Open");
		putValue(Action.LONG_DESCRIPTION, "When you want to open new window, press the mouse button then release the button quickly.");

	}	
	
	public void actionPerformed(ActionEvent e) {
		
       	Group[] objs;
    	objs = mGPanel.getSelectedObject();
    	
//System.out.println("getActionCommand="+e.getActionCommand());	

    	
    	if (e.getSource() == btn[0]) {
       		OpeRotate ope = new OpeRotate(objs[0]);
			edenpo.Stack.push(ope);
		} else if (e.getSource() == btn[1]) {
        	OpeCopy ope = new OpeCopy(objs[0]);
			edenpo.Stack.push(ope);
		} else if (e.getSource() == btn[2]) {
        	OpeGroup ope = new OpeGroup(objs);
			edenpo.Stack.push(ope);
		} else if (e.getSource() == btn[3]) {
        	OpeUnGroup ope = new OpeUnGroup(objs[0]);
			edenpo.Stack.push(ope);
		} else if (e.getSource() == btn[4]) {
        	OpeFront ope = new OpeFront(objs[0]);
			edenpo.Stack.push(ope);
		} else if (e.getSource() == btn[5]) {
        	OpeBack ope = new OpeBack(objs[0]);
			edenpo.Stack.push(ope);
		} else if (e.getSource() == btn[6]) {
        	new OpeUndo();
		} else if (e.getSource() == btn[7]) {
        	OpeDelete ope = new OpeDelete(objs);
			edenpo.Stack.push(ope);
       }
	}

}

