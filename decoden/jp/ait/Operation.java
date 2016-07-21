package jp.ait;

import java.awt.image.*;
import java.io.IOException;
import java.util.LinkedList;

import javax.swing.JOptionPane;


/**
 * undo対象のオペレーションの親オブジェクト
 * @author kagi
 *
 */
public class Operation {
	
	protected GPanel mGPanel;
	protected Group obj;
		
	Operation() {
		mGPanel = edenpo.Graphics_Panel;
	};
	
	public void undo() {};
	
	public void delete() {};
	public void clear() {
		obj.clear();
		obj = null;
	};
	
	public Group getObject() {
		return obj;
	}
	
	public void draw() {
		mGPanel.draw();
	}
	
	public void exchangeLayer(int start_no, int end_no) {
		mGPanel.exchange(start_no, end_no);
		
		edenpo.Layer_Panel.exchange(mGPanel.getListSize()-start_no, mGPanel.getListSize()-end_no);		
		
		// レイヤー番号の振りなおし
		mGPanel.resetLayer();

		// ツールパネルの状態を更新
		mGPanel.resetToolandLayer(false);
		
		draw();
	}
	
	/**
	 * 指定したobjectだけを選択状態にする
	 * 
	 * @param obj
	 */
	public void setSelect(Group obj) {
		mGPanel.clearSelect();
		
		//　選択状態にする
		mGPanel.setSelect(obj);
		// レイヤー内のこのオブジェクトを選択状態にする
		int [] indices = new int[1];
		indices[0] = obj.mLayer;
		edenpo.Layer_Panel.setSelect(indices, true);
	}
}

/**
 * 画像追加オペレーション
 * 
 * @author kagi
 */
class OpeAddImage extends Operation {

/*
/	OpeAddImage(String path) {
		BufferedImage img;
        try{
        	img = ImageIO.read(new File(path));
        	
        	OpeAddImage(img);
        }
        catch(Exception ex){
          	JOptionPane.showMessageDialog(null, "ファイルの読み込みに失敗しました", "エラー", JOptionPane.ERROR_MESSAGE);
        }		
	}
*/
	OpeAddImage(BufferedImage img) {
		double width = img.getWidth();
		double height = img.getHeight();
		
		if (width > height) {
			height = (double)height / (double)width * 100;
			width = 100;
		} else {
			width = (double)width / (double)height * 100;
			height = 100;
			
		}
		
		try
		{
			obj = new ImageObject(img);
			obj.mType = 1;
			obj.size(0, 0, width, height);
			/*
				obj.mX = 0;
				obj.mY = 0;
				obj.mWidth = width;
				obj.mHeight = height;
			*/
			obj.mSelected = true;		// 入力したばかりは選択状態にする
			obj.mLayer = mGPanel.getListSize();	// 階層を一番上（リストの最後）にセット

			// 現在を倍率を設定
			obj.setScale(mGPanel.getScale());
		
			// ディスプレイリストに登録
			mGPanel.addList(obj);
		}
		catch( OutOfMemoryError e )
		{
			JOptionPane.showMessageDialog(edenpo.Main_Frame, Message.MEM_ERROR);
		}
		
// DEBUG
//mGPanel.printDisplayList();		 
		draw();
   	}
	
	OpeAddImage(Thumbnail thum) {
		BufferedImage img = thum.getOriginalImage();
		
		double width = img.getWidth();
		double height = img.getHeight();
		
		if (width > height) {
			height = (double)height / (double)width * 100;
			width = 100;
		} else {
			width = (double)width / (double)height * 100;
			height = 100;
			
		}
		
		try
		{
			obj = new ImageObject(thum);
			obj.mType = 1;
			obj.size(0, 0, width, height);
/*
			obj.mX = 0;
			obj.mY = 0;
			obj.mWidth = width;
			obj.mHeight = height;
*/
			obj.mSelected = true;		// 入力したばかりは選択状態にする
			obj.mLayer = mGPanel.getListSize();	// 階層を一番上（リストの最後）にセット

			// 現在を倍率を設定
			obj.setScale(mGPanel.getScale());
		
			// ディスプレイリストに登録
			mGPanel.addList(obj);
		}
		catch( OutOfMemoryError e )
		{
			JOptionPane.showMessageDialog(edenpo.Main_Frame, Message.MEM_ERROR);
		}
		
// DEBUG
//mGPanel.printDisplayList();		 
		draw();
   	}
   	
	public BufferedImage getImage() {
		return obj.getImage();
	}
	
	public void undo() {
		// Mainの mGroupList からこの Objectを削除する
		// undoした後は２度と使わないので obj = nullを設定
		mGPanel.removeList(obj);
		obj = null;
		
		draw();
	}
	
	// スタックから削除されて最終的にﾒﾓﾘから削除される時に呼ばれる
	public void delete() {
//System.out.println("AddImage delete");		
		obj = null;
	}
}

/**
 * 文字追加オペレーション
 * 
 * @author kagi
 */
class OpeAddText extends Operation {
	private FontInfo info2;
	
   	OpeAddText(String str, FontInfo info) {
		try
		{

			info2 = new FontInfo();
			info2.copy(info);
			obj = new TextObject(str, info2);
			obj.mType = 2;
			obj.mX = 4;
			obj.mY = 4;
			obj.mWidth = (edenpo.Graphics_Panel.mDocWidth / mGPanel.getScale())-8;
			obj.mHeight = 100;
			obj.mSelected = true;	// 入力したばかりは選択状態にする
			obj.mLayer =  mGPanel.getListSize();	// 階層を一番上（リストの最後）にセット
//System.out.println("OpeAddText   mDocXY="+edenpo.Graphics_Panel.mDocY+" mDocHeight="+edenpo.Graphics_Panel.mDocHeight+" box_y="+obj.mY);
		
			// 現在を倍率を設定
			obj.setScale(mGPanel.getScale());
		
			// ディスプレイリストに登録
			mGPanel.addList(obj);
		}
		catch( OutOfMemoryError e )
		{
			JOptionPane.showMessageDialog(edenpo.Main_Frame, Message.MEM_ERROR);
		}
		
 		draw();
  	}
   	
	public void undo() {
		// Mainの mGroupList からこの Objectを削除する
		mGPanel.removeList(obj);
		obj = null;
		
		draw();
	}
	
	// スタックから削除されて最終的にﾒﾓﾘから削除される時に呼ばれる
	public void delete() {
//		System.out.println("AddImage delete");
		info2 = null;
		obj = null;
	}
	

}

/**
 * 差出人追加オペレーション
 * 
 * @author kagi
 */
class OpeAddSashidashi extends Operation {
	private FontInfo info2;
	
	OpeAddSashidashi(String str, FontInfo info) {
		try
		{

			FontInfo info2 = new FontInfo();
			info2.copy(info);
			obj = new SashidashiObject(str, info2);
			obj.mType = 3;
			obj.mX = 4.0 / mGPanel.getScale();
			obj.mHeight = GraphicsPanel.SASHIDASHI_HEIGHT;
			obj.mWidth = GraphicsPanel.SASHIDASHI_WIDTH;

			obj.mX = (((int)(edenpo.Graphics_Panel.mDocWidth/ mGPanel.getScale()) - obj.mWidth ) ) - 4.0 / mGPanel.getScale();
			obj.mY = (((int)(edenpo.Graphics_Panel.mDocHeight/ mGPanel.getScale()) - obj.mHeight ) ) - 4.0 / mGPanel.getScale();
//System.out.println("OpeAddSashidashi   mDocXY="+edenpo.Graphics_Panel.mDocY+" mDocHeight="+edenpo.Graphics_Panel.mDocHeight+" box_y="+obj.mY);
//			obj.mWidth = (edenpo.Graphics_Panel.mDocWidth / mGPanel.getScale())-8;
			obj.mSelected = true;	// 入力したばかりは選択状態にする
			obj.mLayer =  mGPanel.getListSize();	// 階層を一番上（リストの最後）にセット
		
			// 現在を倍率を設定
			obj.setScale(mGPanel.getScale());
		
			// ディスプレイリストに登録
			mGPanel.addList(obj);
		}
		catch( OutOfMemoryError e )
		{
			JOptionPane.showMessageDialog(edenpo.Main_Frame, Message.MEM_ERROR);
		}
		
 		draw();
  	}
   	
	public void undo() {
		// Mainの mGroupList からこの Objectを削除する
		mGPanel.removeList(obj);
		obj = null;
		
		draw();
	}
	
	
	// スタックから削除されて最終的にﾒﾓﾘから削除される時に呼ばれる
	public void delete() {
		info2 = null;
		obj = null;
	}

}

/**
 * 文字属性修正オペレーション
 * 
 * @author kagi
 */
class OpeTextInfo extends Operation {
	String prev_str;
   	FontInfo prev_info;
   	
   	OpeTextInfo(Group in, String str, FontInfo info) {
   		obj = in;
   		
   		TextObject to = (TextObject)in;
   		prev_str = to.getString();
   		
   		prev_info = new FontInfo();
   		prev_info.copy(to.getFontInfo());
   		
   		obj.setFontInfo(str, info);
				  		
		draw();
  	}
  	
	public void undo() {
		// TextObjectのFont情報を変更する
		obj.setFontInfo(prev_str, prev_info);
		
		// 現在の文字属性でパネルの値を更新する  false:修正をスタックに積まない
		edenpo.Text_Panel.setFontInfo((TextObject)obj);
		
		draw();
	}

}

/**
 * 移動オペレーション
 * 
 * @author kagi
 */
class OpeMove extends Operation  {
    private double prev_mx, prev_my;
   	
  	OpeMove(Group in, double move_x, double move_y) {
   		obj = in;
   		prev_mx = move_x;
   		prev_my = move_y;
   		if (obj.mGroupList.size() > 1)
   			obj.move(false, prev_mx, prev_my);		// Top値はドラック中に変更している
   													// ここでは子だけを変更している
   		
		draw();
  	}
	public void undo() {
		obj.move(true, -prev_mx, -prev_my);
		
		setSelect(obj);
		
		draw();
	}
}

/**
 * サイズ変更オペレーション
 * 
 * @author kagi
 */
class OpeSize extends Operation  {
   	private double prev_x, prev_y;
	private double prev_width, prev_height;
   	
	OpeSize(Group in, double x, double y, double width, double height) {
//		OpeSize(Group in, double width, double height) {
  		obj = in;
  		
  		// mXなどの変数はドラック中に変更されている
  		// ここでの入力は変更前の値が来る
  		prev_x = x;
  		prev_y = y;
   		prev_width = width;
   		prev_height = height;
//		obj.size(x, y, width, height);	// 値はドラック中に変更している

   		
		draw();
  	}
	
	public void undo() {
		obj.size(prev_x, prev_y, prev_width, prev_height);
//		obj.size(prev_width, prev_height);
		
		setSelect(obj);
		
		draw();
	}
}

/**
 * 回転オペレーション
 * 
 * @author kagi
 */
class OpeRotate extends Operation  {
	private int prev_r;
   	
   	OpeRotate(Group in) {
		try
		{
			obj = in;
			prev_r = obj.mRotate;
   		
			obj.rotate();
		}
		catch( OutOfMemoryError e )
		{
			JOptionPane.showMessageDialog(edenpo.Main_Frame, Message.MEM_ERROR);
		}
		
		draw();
  	}
	
	public void undo() {
		obj.rotate(prev_r);
		
		setSelect(obj);
		
		draw();
	}
}

/**
 * コピーオペレーション
 * 
 * @author kagi
 */
class OpeCopy extends Operation  {
	Group prev_obj;
	
	OpeCopy(Group in) {
		prev_obj = in;
		try
		{
			obj = in.copy();
			if (obj == null)
				return;
   		
			/**
			 * コピーした時　作業領域からはみ出る場合。
			 */
			double docWidth = edenpo.Graphics_Panel.mDocWidth/ mGPanel.getScale();
			double docHeight= edenpo.Graphics_Panel.mDocHeight/ mGPanel.getScale();
			if (obj.mX+obj.mWidth > docWidth ||
				obj.mY+obj.mHeight > docHeight) {
				obj.move(true, -obj.mX, -obj.mY);  
			
				// グループの外枠を再計算する
				obj.reset();
			}

			obj.mLayer = mGPanel.getListSize();	// 階層を一番上（リストの最後）にセット
  		
			// ディスプレイリストに登録
			mGPanel.addList(obj);
		

		} catch (OutOfMemoryError e) {
			JOptionPane.showMessageDialog(edenpo.Main_Frame, Message.MEM_ERROR);
		}
		
		draw();
  	}
	public void undo() {
   		   		// ディスプレイリストに登録
   		mGPanel.removeList(obj);
   		
		setSelect(prev_obj);

		draw();
	}
	
}


/**
 * グループ化オペレーション
 * 
 * @author kagi
 */
class OpeGroup extends Operation  {
   	
	OpeGroup(Group[] objs) {
		if (objs.length == 0)
			return;
		
		obj = new Group();
		
		int top_layer = -1;
		int del_num = 0;
		for (int i = 0; i < objs.length; i++) {
			if (objs[i].mLayer > top_layer) {						// 配列の最後が　画面の最上部となる
				top_layer = objs[i].mLayer;
			}
			obj.mGroupList.add(objs[i]);
			
			// ディスプレイリストからこのオブジェクトを削除する
			mGPanel.removeList(objs[i]);
			del_num++;
		}
		// Groupの 位置とサイズを設定する
		obj.reset();
		obj.mSelected = true;
		obj.mLayer = top_layer-del_num+1;
		
		// ディスプレイリストにグループ化したオブジェクトを挿入する
		// 挿入する位置は　グループ化した中の最上位オブジェクトの位置になる
		// 現在のグループオブジェクトを削除した分だけ引くを行う
		mGPanel.addList(obj.mLayer, obj);
		mGPanel.resetLayer();
		
		draw();
	}
	
	public void undo() {
		// ディスプレイリストからこのグループオブジェクトを削除する
		mGPanel.removeList(obj);
		
		for (int i = 0; i < obj.mGroupList.size(); i++) {
	       	Group obj2 = (Group)obj.mGroupList.get(i);
//			mGPanel.addList(obj.mLayer++, obj2);
	       	// グループ枠を計算しなおす
        	obj2.reset();
        	
        	mGPanel.addList(obj2.mLayer, obj2);
		}
		mGPanel.resetLayer();
		obj = null;
		
		draw();
	}
}

/**
 * グループ解除オペレーション
 * 
 * @author kagi
 */
class OpeUnGroup extends Operation  {
  
	private   LinkedList glist = new LinkedList();
	
	OpeUnGroup(Group in) {
		obj = in;
		
		// ディスプレイリストからこのグループオブジェクトを削除する
		mGPanel.removeList(obj);
		
		int layer = obj.mLayer;
		// objについているグループオブジェクト群をばらして
		// ディスプレイオブジェクトに付け直す
		for (int i = 0; i < obj.mGroupList.size(); i++) {
        	Group obj2 = (Group)obj.mGroupList.get(i);
        	// グループ枠を計算しなおす
        	obj2.reset();
        	
    		mGPanel.addList(layer++, obj2);
    		glist.add(obj2);
		}
		mGPanel.resetLayer();
		
		// 選択を解除
		mGPanel.clearSelect();
		mGPanel.resetToolandLayer(true);
		
		draw();
	}
	public void undo() {
		int layer = 0;
		for (int i = 0; i < glist.size(); i++) {
        	Group obj2 = (Group)glist.get(i);
        	
			// ディスプレイリストからこのオブジェクトを削除する
			mGPanel.removeList(obj2);
		}
		
   		mGPanel.addList(layer, obj);
   		obj.mSelected = true;
   		glist.clear();
		mGPanel.resetLayer();
		
		draw();
	}
}

/**
 * レイヤー変更オペレーション
 * 
 * @author kagi
 */
class OpeLayer extends Operation  {
	private int prev_no, current_no;
   	
   	OpeLayer(int prev, int current) {
   		prev_no = prev;
   		current_no = current;
   		
		mGPanel.exchange(prev_no, current_no);		
		// レイヤー番号の振りなおし
		mGPanel.resetLayer();
		
		// ツールパネルの状態を更新
		mGPanel.resetToolandLayer(false);
		
		draw();
   		
   	}
   	
	public void undo() {
		// Mainの display_list からこの Objectを指定した場所に移動する
		exchangeLayer(current_no, prev_no);
	}
	
}

/**
 * 前面へオペレーション
 * 
 * @author kagi
 */
class OpeFront extends Operation  {
	private int prev_no, current_no;
   	
	OpeFront(Group in) {
		obj = in;
   		
		prev_no = obj.mLayer;
//		current_no = mGPanel.getListSize()-1;
		current_no = prev_no + 1;
		
		exchangeLayer(prev_no, current_no);
		
		// レイヤー内のこのオブジェクトを選択状態にする
		int [] indices = new int[1];
		indices[0] = obj.mLayer;
		edenpo.Layer_Panel.setSelect(indices, true);
   	}
   	
	public void undo() {
		// Mainの display_list からこの Objectを指定した場所に移動する
		exchangeLayer(current_no, prev_no);
		
		setSelect(obj);
		// レイヤー内のこのオブジェクトを選択状態にする
		int [] indices = new int[1];
		indices[0] = obj.mLayer;
		edenpo.Layer_Panel.setSelect(indices, true);

	}
	
}

/**
 * 後面へオペレーション
 * 
 * @author kagi
 */
class OpeBack extends Operation  {
	private int prev_no, current_no;
   	
	OpeBack(Group in) {
		obj = in;
   		
		prev_no = obj.mLayer;
//		current_no = 0;
		current_no = prev_no - 1;
		
		exchangeLayer(prev_no, current_no);
		
		
		// レイヤー内のこのオブジェクトを選択状態にする
		int [] indices = new int[1];
		indices[0] = obj.mLayer;
		edenpo.Layer_Panel.setSelect(indices, true);
 	}
   	
	public void undo() {
		// Mainの display_list からこの Objectを指定した場所に移動する
		exchangeLayer(current_no, prev_no);
		
		// レイヤー内のこのオブジェクトを選択状態にする
		int [] indices = new int[1];
		indices[0] = obj.mLayer;
		edenpo.Layer_Panel.setSelect(indices, true);
	}
	
}


/**
 * 削除オペレーション
 * 
 * @author kagi
 */
class OpeDelete extends Operation  {
   	
	OpeDelete(Group[] objs) {
		obj = new Group();
		for (int i = 0; i < objs.length; i++) {
			obj.mGroupList.add(objs[i]);
			
			// ディスプレイリストからこのオブジェクトを削除する
			mGPanel.removeList(objs[i]);
			
			// レイヤー番号の振りなおし
			mGPanel.resetLayer();
		}
		
		draw();
	}
	public void undo() {
		for (int i = 0; i < obj.mGroupList.size(); i++) {
			Group obj2 = (Group)obj.mGroupList.get(i);
			// Mainの mGroupList からこの Objectを戻す
			mGPanel.addList(obj2.mLayer, obj2);
//System.out.println("delete->undo mLayer="+obj2.mLayer);
			// レイヤー番号の振りなおし
			mGPanel.resetLayer();
		}
		draw();
		
		obj.mGroupList = null;
		obj = null;
	}	
	
	// スタックから削除される時に呼ばれる
	public void delete() {
System.out.println("delete obj="+obj);
		obj.clear();
		obj = null;
		System.gc();
	}
}

/**
 * Undoオペレーション
 * 
 * @author kagi
 */
class OpeUndo extends Operation  {
   	
	OpeUndo() {
		int size = edenpo.Stack.size();
		
		if (size > 0) {
			Operation ope = (Operation)(edenpo.Stack.pop());
			if (ope != null) {
				ope.undo();
		
				draw();
			}
		}
	}
	public void undo() {}
}


