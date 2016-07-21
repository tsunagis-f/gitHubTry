package jp.ait;

import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.util.*;

import javax.swing.JOptionPane;

/**
 * ImageObject や TextObjectの親オブジェクト
 * グループ化した時のグループとしても使用
 * 
 * @author kagi
 */
class Group {

	/**
	 * 子グループを保持するリスト
	 */
	public LinkedList mGroupList;
	
	/**
	 * レイヤー番号
	 */
	public int mLayer;			

	/**
	 * グループタイプ  0:groupのみ  1:image   2:text
	 */
    public int mType;   
    
    /**
     * 位置とサイズ
     */
    public  double mX, mY, mWidth, mHeight;   // 位置はドキュメントの基点からの相対位置
    
    /**
     * 回転パラメータ　（Imageだけで仕様)
     */
    public int mRotate;
    
    /**
     * 選択されているかどうかのフラグ
     */
    public boolean mSelected;
    
    /** 
     * 倍率
     */
    protected float mScale;
   
    /**
     * コピーするときのオフセット値
     */
    final public static int COPY_OFFSET = 15;
    
    
    /**
     * 表示/非表示のコントロール  (文字ダブルクリック時に一時的に使用）
     */
    public boolean mShow;
    
    Group() {
      mType = 0;
      mSelected = true;
      mGroupList = new LinkedList();
      mScale = 1.0f;
      mShow = true;
    }

 	/**
	 * 指定した点がオブジェクトのBox内かどうか判定する
     */
    public boolean HitTest(int x, int y) {
    	int x0 = (int)(edenpo.Graphics_Panel.mDocX + (mX*mScale));
    	int y0 = (int)(edenpo.Graphics_Panel.mDocY + (mY*mScale));

//JOptionPane.showMessageDialog(edenpo.Main_Frame, "HitTest mDocX="+edenpo.Graphics_Panel.mDocX+"  mDocY="+edenpo.Graphics_Panel.mDocY+"  mX="+mX+"  mY="+mY+"  mScale="+mScale);
//System.out.println("HitTest mDocX="+edenpo.Graphics_Panel.mDocX+"  mDocY="+edenpo.Graphics_Panel.mDocY+"  mX="+mX+"  mY="+mY+" mWidth="+mWidth+" mHeight="+mHeight+"  mScale="+mScale);	
    	if (x0 <= x && x <= (x0+mWidth*mScale) && y0 <= y && y <= (y0+mHeight*mScale))
    		return true;
    	else
    		return false;
    }
    
	/**
	 * 指定した四角の領域ががオブジェクトのBoxを包んでいるかどうか判定する
	 * @param	x1	四角形の右上のX座標
	 * @param1	y1	四角形の右上のY座標
	 * @param	x2	四角形の左下のX座標
	 * @param	y2	四角形の左下のY座標
	 * @return  
     */
    public boolean HitTest(int x1, int y1, int x2, int y2) {
    	int x0 = (int)(edenpo.Graphics_Panel.mDocX + (mX*mScale));
    	int y0 = (int)(edenpo.Graphics_Panel.mDocY + (mY*mScale));


   	
      	if (x1 <= x0 && (x0+mWidth*mScale) <= x2 && y1 <= y0 && (y0+mHeight*mScale) <= y2) {
      		mSelected = true;
      	} else {
      		mSelected = false;
      	}
      	
      	return mSelected;
	}
   
    /**
     * 移動する
     * 
     * @param sw   Topのグループも移動するかどうか
     * @param x 	Ｘ方向の移動量
     * @param y		Y 方向の移動量
     */
    public void move(boolean sw, double x, double y) {
    	double dx = x;
    	double dy = y;
    	
    	if (sw) {
    		mX += dx;
    		mY += dy;
    	}
    	
    	// グループについている子も全て移動する
    	for (int i = 0; i < mGroupList.size(); i++) {
    		Group obj = (Group)mGroupList.get(i);
    		if (obj.mType == 0) {
    			obj.move(sw, x, y);    			
    		} else {
    			obj.mX += dx;
    			obj.mY += dy;
    		}
    	}
     }
    
	/**
	 * サイズを変更する
     */
//    public void size(double width, double height) {
   
    public void size(double x, double y, double width, double height) {
    	mX = x;
    	mY = y;
    	mWidth = width;
    	mHeight = height;
     }

	/**
	 * オブジェクトのタイプを返す
     */
    public int getType() {
    	return mType;
     }

    /**
     * 全ての子供のグループから　このグループの位置とサイズをリセットする
     * @return
     */
    public void reset() {
    	if (mGroupList.size() < 1)
    		return;
    	
    	double min_x, min_y, max_x, max_y;
     	
    	min_x = min_y = 10000;
    	max_x = max_y = -1;
    	// グループについている子も全て移動する
    	for (int i = 0; i < mGroupList.size(); i++) {
    		Group obj = (Group)mGroupList.get(i);
    		if (min_x > obj.mX)
    			min_x = obj.mX;
    		if (min_y > obj.mY)
    			min_y = obj.mY;
    		if (max_x < (obj.mX+obj.mWidth))
    			max_x = obj.mX+obj.mWidth;
        	if (max_y < (obj.mY+obj.mHeight))
        		max_y = obj.mY+obj.mHeight;    			
    	}
    	
    	mX = min_x;
    	mY = min_y;
    	mWidth = max_x - min_x;
    	mHeight = max_y - min_y;   	
    }
    
    /**
     * 倍率を設定する
     * @param scale
     */
    public void setScale(float scale) {
    	mScale = scale;
    	
    	
    	// グループについている子も全て移動する
    	for (int i = 0; i < mGroupList.size(); i++) {
    		Group obj = (Group)mGroupList.get(i);
    		obj.setScale(scale);
    	}
    }
    
    /**
     * 子オブジェクトでオーバーライドする
     * @return
     */
    public BufferedImage getImage() { return null; }
    public void rotate() {}
    public void rotate(int r) {}
//    public Group copy() { return null; }
    public void setFontInfo(String str, FontInfo info) {};
    public FontInfo getFontInfo() { return null; };
    public void setFontSize(float scale) {};
 
 	/**
	 * 指定した四角の領域ががオブジェクトのBoxを包んでいるかどうか判定する
     * グループリストが無い場合は、個別のグラフィックスオブジェクトによって
     * オーバーライドされている
     */
    public void draw(Graphics g, Container boad) {
    	if (mGroupList != null) {
    		for (int i = 0; i < mGroupList.size(); i++) {
    			Group group = (Group)mGroupList.get(i);
    			if (group != null) {
    				group.draw(g, boad);
    			}
    		}
    	}
    }
    public Group copy() {
    	Group group = new Group();
		
		
    	if (mGroupList != null) {
    		for (int i = 0; i < mGroupList.size(); i++) {
    			Group src_obj = (Group)mGroupList.get(i);
    			if (src_obj != null) {
     				group.mGroupList.add(src_obj.copy());
     			}
    		}
    	}
    	
    	group.mScale = mScale;
    	
    	// グループの外枠を計算する
    	group.reset();
     	
    	return group;
    }
    
    public void clear() {
    	if (mType == 1) {
    		((ImageObject)this).clear();
    	} else {    	
    		if (mGroupList != null) {
    			for (int i = 0; i < mGroupList.size(); i++) {
    				Group group = (Group)mGroupList.get(i);
	    			if (group != null) {
	    				group.clear();
	    				group = null;
	    			}
    			}
    			mGroupList.clear();
    			mGroupList = null;
    		}
    	}
    }

    	
  
    public void load(DataInputStream in) {
    	int num = 0;
    	char type = 0;
    	try {
			mX = in.readDouble();
			mY = in.readDouble();   
			mWidth = in.readDouble();   
			mHeight = in.readDouble();   
			num = in.readInt();		// 子供の数
 	   	 	    		
    	
 			if (num > 0) {
 				for (int i = 0; i < num; i++) {
 					type = in.readChar();
 					Group obj = null;
 					switch(type) {
 					case 'G':
//System.out.println("load group");
						obj = new Group();
						obj.mType = 0;
						obj.mX = mX;
 						obj.mY = mY;
 						obj.mWidth = mWidth;
 						obj.mHeight = mHeight;
 						obj.mScale = mScale;
 						obj.mRotate = mRotate;
  						
 						obj.load(in);
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
 						if (obj.mType != 0) {
 							obj.load(in);
 						}
			
 						// ディスプレイリストに登録
 						mGroupList.add(obj);
 					}
 				}
 			}
    	} catch(Exception  ex){
    		assert(false);
    	}
    }
    
    public void save(DataOutputStream out) {
	   	try {
	   		out.writeChar('G');					// Group
    		out.writeDouble(mX);
    		out.writeDouble(mY);   
    		out.writeDouble(mWidth);   
    		out.writeDouble(mHeight);   
	   		
	   		if (mGroupList != null) {
    	   		out.writeInt(mGroupList.size());	// 子供の数を書き出す
 	   		} else {
	   			out.writeInt(0);					// 子供の数を書き出す   	   	 	    	
	   		}
 	   	} catch(Exception  ex){
    		assert(false);
    	}
 		
     	if (mGroupList != null) {
    		for (int i = 0; i < mGroupList.size(); i++) {
    			Group group = (Group)mGroupList.get(i);
    			if (group != null) {
    				group.save(out);
    			}
    		}
    	} 
    }

}


