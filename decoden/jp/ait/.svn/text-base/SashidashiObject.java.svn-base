package jp.ait;

import java.awt.Color;
import java.io.DataInputStream;
import java.io.DataOutputStream;

class SashidashiObject extends TextObject {
	
	SashidashiObject(String str, FontInfo info) {
		mStr = str;
		mFontInfo = info;
		mType = 3;
	}
	
	SashidashiObject() {
		mType = 3;
	}
	public Group copy() {
		FontInfo font_info = new FontInfo();
		font_info.copy(mFontInfo);
		
		TextObject obj = new SashidashiObject(mStr, font_info);
		obj.mX = mX + COPY_OFFSET;
		obj.mY = mY + COPY_OFFSET;
		obj.mWidth = mWidth;
		obj.mHeight = mHeight;
		obj.mType = mType;
		
		// 現在を倍率を設定
		obj.setScale(edenpo.Graphics_Panel.getScale());
		
		return obj;
	}
	
    public void load(DataInputStream in) {
    	try {
    		mType = 3;
			mX = in.readDouble();
			mY = in.readDouble();   
			mWidth = in.readDouble();   
			mHeight = in.readDouble();   
			mStr = readString(in);
				
			// FontInfo
			mFontInfo = new FontInfo();
			mFontInfo.mFont = readString(in);
			mFontInfo.mSize = in.readInt();
			mFontInfo.mStyle = in.readShort();
			mFontInfo.mTume = in.readShort();
			mFontInfo.mMuki = in.readShort();
			mFontInfo.mColor = new Color(in.readInt());
    		
    	} catch(Exception  ex){
    	}
    }
	
    public void save(DataOutputStream out) {
    	try {
    		
    		out.writeChar('S');		// 差出人
    		out.writeDouble(mX);
    		out.writeDouble(mY);   
    		out.writeDouble(mWidth);   
    		out.writeDouble(mHeight);   
    		out.writeInt(mStr.length());        				
    		out.writeChars(mStr);  
    		
    		// FontInfo
	    	out.writeInt(mFontInfo.mFont.length());        				
	    	out.writeChars(mFontInfo.mFont);  
	    	out.writeInt(mFontInfo.mSize);
	    	out.writeShort(mFontInfo.mStyle);
	    	out.writeShort(mFontInfo.mTume);
	    	out.writeShort(mFontInfo.mMuki);
	    	out.writeInt(mFontInfo.mColor.getRGB());

    		out.flush();
	    		
    	} catch(Exception  ex){
    		assert(false);
    	} 
    }

}
