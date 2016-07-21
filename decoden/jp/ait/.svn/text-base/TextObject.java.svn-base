package jp.ait;

import java.awt.*;
import java.util.*;
import java.awt.image.*;
import java.io.*;

import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.VerticalText;


import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.PageSize;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfTemplate;
import com.lowagie.text.pdf.PdfWriter;
import java.awt.geom.*;


class FontInfo {
	  String mFont;
	  int mSize;
	  short mStyle;
	  short mTume;
	  short mMuki;
	  Color mColor;
	  
	  public void copy(FontInfo src) {
		  mFont = src.mFont;
		  mSize = src.mSize;
		  mStyle = src.mStyle;
		  mTume = src.mTume;
		  mMuki = src.mMuki;
		  mColor = src.mColor;
	  }
};

class TextObject extends Group {
		protected String mStr;
		protected FontInfo mFontInfo;
		
		
		protected static int fontDotSize;

		/**
		 * 文字ごとのインデント(X)
		 */
		protected static int 		Xadjust 	= 0;
		/**
		 * 文字ごとのインデント(Y)
		 */
		protected static int 		Yadjust 	= 0;
		
		/**
		 * 解析後の文字列データを格納するベクター
		 */
		protected static Vector			strVec		= null;
		
		/**
		 * 一行の文字数
		 */
		protected static int 		lineLength	= 42;
		/*
		 * 特殊文字
		 */
		/*
		// 　　ぶら下げ　　文字(特殊インデント１)
		String sepStr		= "、。，";
		// 　　ぶら下げ　　文字(特殊インデント２)
		String smallStr 	= "ぁぃぅぇぉっゃゅょァィゥェォヵヶッャュョ";
		// 　　ぶら下げ　　文字
		String dangleStr		= "々ヽヾゝゞ！？";
		// 回転　　　　　　文字
		String rotateStr0		= "＝≠≦≧∈∋⊆⊇⊂⊃∪∩←↑→↓";
		// 回転ぶら下げ　　文字
		String rotateStr1	= "＞≫」ー－』｝）》】〕］〉｜―～…‥：";
		// 回転回り込み　　文字
		String rotateStr2 = "＜≪「『｛（《【〔［〈";
		// 回転　　　　半角文字
		String rotateStr3 = "1234567890^\\qwertyuiop@asdfghjklzxcvbnm #$%|QWERTYUIOPASDFGHJKLZXCVBNM_ﾇﾌｱｳｴｵﾔﾕﾖﾜﾎﾍﾀﾃｲｽｶﾝﾅﾆﾗｾﾁﾄｼﾊｷｸﾏﾉﾘﾚｹﾑﾂｻｿﾋｺﾐﾓﾈﾙﾒﾛｦ";
		// 回転ぶら下げ半角文字
		String rotateStr4 = "-;:],./)!\"&=+*}>?ﾞﾟｩｪｫｬｭｮ｣ｨｯ｡､･";
		// 回転回り込み半角文字
		String rotateStr5 = "(['`~{<｢";// 改行時に回り込ませる半角文字
	*/
//		private static	 final	String		sepStr		= "\u3001\u3002\uFF0C";
		protected static	 final	String		sepStr		= "\u3001\uFF0C\u3002\uFF0E";
		protected static	 final	String		smallStr
		= "\u3041\u3043\u3045\u3047\u3049\u3063\u3083\u3085\u3087\u30A1\u30A3"
		+	"\u30A5\u30A7\u30A9\u30F5\u30F6\u30C3\u30E3\u30E5\u30E7";
		protected static	 final	String		dangleStr
		= "\u3005\u30FD\u30FE\u309D\u309E\uFF01\uFF1F";
		protected static	 final	String		rotateStr0
		= "\uFF1D\u2260\u2266\u2267\u2208\u220B\u2286\u2287\u2282\u2283\u222A"
		+	"\u2229\u2190\u2191\u2192\u2193";
		protected static	 final	String		rotateStr1
		= "\uFF1E\u226B\u300D\u30FC\u2212\u300F\uFF5D\uFF09\u300B\u3011\u3015"
		+	"\uFF3D\u3009\uFF5C\u2015\u301C\uFF5E\u2026\u2025\uFF1A";
		protected static	 final	String		rotateStr2
		= "\uFF1C\u226A\u300C\u300E\uFF5B\uFF08\u300A\u3010\u3014\uFF3B\u3008";
		protected static	 final	String		rotateStr3
		= "1234567890^\\qwertyuiop@asdfghjklzxcvbnm #$%|QWERTYUIOPASDFGHJKLZXC"
		+	"VBNM_\uFF87\uFF8C\uFF71\uFF73\uFF74\uFF75\uFF94\uFF95\uFF96\uFF9C"
		+	"\uFF8E\uFF8D\uFF80\uFF83\uFF72\uFF7D\uFF76\uFF9D\uFF85\uFF86\uFF97"
		+	"\uFF7E\uFF81\uFF84\uFF7C\uFF8A\uFF77\uFF78\uFF8F\uFF89\uFF98\uFF9A"
		+	"\uFF79\uFF91\uFF82\uFF7B\uFF7F\uFF8B\uFF7A\uFF90\uFF93\uFF88\uFF99"
		+	"\uFF92\uFF9B\uFF66";
		protected static	 final	String		rotateStr4
		= "-;:],./)!\"&=+*}>?\uFF9E\uFF9F\uFF69\uFF6A\uFF6B\uFF6C\uFF6D\uFF6E"
		+	"\uFF63\uFF68\uFF6F\uFF61\uFF64\uFF65";
		protected static	 final	String		rotateStr5	= "(['`~{<\uFF62";
		
		/**
		 * フォントの最大幅
		 */
		protected static int 			mFontDotSize = 0;
		
		/**
		 * フォントの高さ
		 */
		protected static double 			mHig = 0;

		/**
		 * フォント色
		 */
		protected static Color 			mColor;

		protected static Container mContainer;
		
		TextObject(String str, FontInfo info) {
			mStr = str;
			mFontInfo = info;
			mType = 2;
		}
		
		TextObject() {
			mType = 2;
		}

		public void setString(String str) {
			mStr = str;
		}

		public String getString() {
			return mStr;
		}

		public void setFontInfo(String str, FontInfo finfo) {
			mStr = str;
			mFontInfo.copy(finfo);
		}

		public FontInfo getFontInfo() {
		      return mFontInfo;
		}

		public void draw(Graphics g, Container boad) {

			mContainer = boad;
			
			int style;
			switch(mFontInfo.mStyle) {
			case 0:
				style = Font.PLAIN;
				break;
			case 1:
				style = Font.BOLD;
				break;
			case 2:
				style = Font.PLAIN | Font.ITALIC;
				break;
			case 3:
				style = Font.BOLD | Font.ITALIC;
				break;
			}
			Font font = new Font(mFontInfo.mFont, mFontInfo.mStyle, (int)(mFontInfo.mSize*mScale));

			g.setColor(mFontInfo.mColor);

//System.out.println("TextObject mDocX="+edenpo.Graphics_Panel.mDocX+"  mDocY="+edenpo.Graphics_Panel.mDocY);
			drawStringExt(g, boad, mStr, 
					(int)(edenpo.Graphics_Panel.mDocX +mX*mScale), 
					(int)(edenpo.Graphics_Panel.mDocY +mY*mScale), 
					(int)(mWidth*mScale), (int)(mHeight*mScale), font, mFontInfo.mTume, mFontInfo.mMuki, mFontInfo.mColor);
		}
		public Group copy() {
			FontInfo font_info = new FontInfo();
			font_info.copy(mFontInfo);
			
			TextObject obj = new TextObject(mStr, font_info);
			obj.mX = mX + COPY_OFFSET;
			obj.mY = mY + COPY_OFFSET;
			obj.mWidth = mWidth;
			obj.mHeight = mHeight;
			obj.mType = mType;
			
			// 現在を倍率を設定
			obj.setScale(edenpo.Graphics_Panel.getScale());
			
			return obj;
		}
		
		
		/**
		 * 
		 * 文字の描画を行う
		 * @param	g		グラフィックスコンテキスト
		 * @param	str		文字列
		 * @param	x		表示Boxの左上のＸ座標
		 * @param	y		表示Boxの左上の座標
		 * @param	width	表示Boxの幅
		 * @param	height	表示Boxの高さ
		 * @param	tume	文字列の方向（横/縦)
		 * @param	muki	右詰め/中央/左詰め
		 */
		public static void drawStringExt(Graphics g, Container boad, String str, int x, int y, int width, int height, Font font , int tume, int muki, Color color) {

			Graphics2D g2 = (Graphics2D)g;
			g.setFont(font);
			mColor = color;

			mContainer = boad;
			
			FontMetrics fm = g.getFontMetrics();

			// 改行文字を得る
			String strSep = getSepStr(str);

			// 範囲と改行文字で　文字列を分割する
			// 分割した文字列を代入するベクター
			Vector	vec		= new Vector();
			int fromPoint;
			int i;

			mHig = fm.getHeight();
			mFontDotSize = fm.stringWidth("\u2015"/*"―"*/);

//System.out.println("文字高さ="+getCharWidth("\u2015") * mFontDotSize);
			mHig = (getCharWidth("\u2015") * mFontDotSize) / 2.0;
//System.out.println("文字高さ="+getCharWidth("\u2015")+" mFontDotSize="+fm.stringWidth("\u2015"/*"―"*/)+"  height="+height);
			fromPoint = 0;
			double h = 0;
			for(i = 0; i <str.length(); i++){
				char code = str.charAt(i);
				String scode = "" + code;
				if (strSep.equals(scode)) {
					if (muki == 0) {
						vec.addElement(str.substring(fromPoint, i));	
						fromPoint = i;
//						width = 0;
						continue;
					} else {
//System.out.println(str.substring(fromPoint, i));
						vec.addElement(str.substring(fromPoint, i));							
						fromPoint = i+1;
						h = 0;
						continue;
					}
				}

				if (muki == 0) {
					// 横書き
					if (width < fm.stringWidth(str.substring(fromPoint, i+1))) {
						vec.addElement(str.substring(fromPoint, i));	
						fromPoint = i;
					}
				} else {
					h += mHig;
					// PDFだと通常画面と解像度が違うため
					// font サイズが小数点以下が切り捨てされ　改行位置が変わる。
					// ここではそのための微調整を行っている。
					if (g instanceof com.lowagie.text.pdf.PdfGraphics2D) {
						double a = mFontDotSize *  edenpo.Graphics_PanelM.DPI / 72.0;
						int b = (int)a;
						a = a - b;
						if (a > 0.15) {
							if (a < 0.5) {
								h += 0.5;
							} else if (a < 0.9){
								h += 0.25;
							}
						}
					}
					// 縦書き
//System.out.println("i="+i+" height="+height+" h="+h+" "+str.substring(fromPoint, i)+" Hig="+mHig);
					if (height < h) {
						vec.addElement(str.substring(fromPoint, i));	
						fromPoint = i;
						h = mHig;
					}
				}

			}
			String str2 = str.substring(fromPoint);
			vec.addElement(str2);

			String	drawStringLine = "";
			String tmpStr;
			int currentLineOnPage = 0;
			int baseX = 0;

			for (i = 0; i < vec.size(); i++) {
				
				drawStringLine = (String)vec.elementAt(i);
				// 横書き
				if (muki == 0) {
					if ((i+1)*mHig > height)
						break;
					
					int strWidth = fm.stringWidth(drawStringLine);
					switch(tume) {
					case 0:  // 左詰め
						g.drawString(drawStringLine, x, y + (int)((i+1)*mHig));
						break;
					case 1:  // 中央詰め
						g.drawString(drawStringLine, (x+width/2) - strWidth/2, y + (int)((i+1)*mHig));
						break;
					case 2: // 右詰め
						g.drawString(drawStringLine, (x+width)-strWidth, y + (int)((i+1)*mHig));
						break;
					}
					
				//　縦書き
				} else {
					
					// 縦書きの際に横倒しになる文字か
					boolean 	ifVarticle			= false;
					int Ylocate = 0;

					switch(tume) {
					case 0:  // 左詰め
						baseX = x + (mFontDotSize * 1) * (vec.size());
						break;
					case 1:  // 中央詰め
						baseX = x + width/2 + ((mFontDotSize * 1) * vec.size() / 2);
						break;
					case 2: // 右詰め
						baseX = x + width;
						break;
					}

					int doubleX = baseX - (mFontDotSize * 1) * (i+1);
					if (doubleX < x)
						break;
					
					if (doubleX + mFontDotSize > x+width)
						continue;
					
					for (int j = 0; j < drawStringLine.length(); j++){
						tmpStr = drawStringLine.substring(j, j+1);
						Ylocate += (getCharWidth(tmpStr) * mFontDotSize);
//System.out.println("tmpStr="+tmpStr);

						if (tmpStr.equals("ー")) {
							tmpStr = "―";
						}
						ifVarticle = ifRotateString(tmpStr);

						int doubleY = (y+Yadjust) * 2;
						
						if (ifVarticle){	// 回転処理が必要な文字か？
							// 文字回転処理
							if (g instanceof com.lowagie.text.pdf.PdfGraphics2D) {
								AffineTransform  aT = g2.getTransform();
								
								//回転の中心を指定(原点移動)
								int w = fm.stringWidth(tmpStr) - 1;
//System.out.println("indexOf="+fm.stringWidth(tmpStr)+"  getStringBounds="+fm.getStringBounds(tmpStr, g) );							

								g.translate((int)(doubleX + (mFontDotSize/2.0)/4.0) , (doubleY + Ylocate) / 2);				
//								g.translate((int)(doubleX + (w /2.0)) , (doubleY + Ylocate) / 2);				
//								g.translate((int)(doubleX) , (doubleY + Ylocate) / 2);				

								// 回転
								double r = 90.0;
								g2.rotate(Math.PI * r / 180.0); 	//＋90度回転して描画
								g.drawString(tmpStr, 
										0, 0);
								
								g2.setTransform(aT);
							} else {
								rotateStr(g, tmpStr, (int)(doubleX + mFontDotSize/2 ) * 2, doubleY, Ylocate, font, fm);
							}
						} else {
							g.setColor(mColor);
//							int testx = doubleX - fm.stringWidth(tmpStr) / 2;
//System.out.println("indexOf="+sepStr.indexOf(tmpStr));
							if (sepStr.indexOf(tmpStr) != -1){
								g.drawString(tmpStr, 
										doubleX + mFontDotSize/2,
										(doubleY + Ylocate) / 2);
							} else {
								g.drawString(tmpStr, 
									doubleX ,
									(doubleY + Ylocate) / 2);
							}
						}
						Xadjust = 0;
						Yadjust = 0;
					}
					currentLineOnPage ++;
				}
			}
		}

		/**
		 * 改行文字を取得する
		 *
		 * @param	tmpStr	改行文字を取得する文字列
		 * @return	改行文字
		 */
		private static String getSepStr(String tmpStr){

			String sepStr	=	"\r\n";

			if (tmpStr.indexOf(sepStr) == -1){
				sepStr = "\r";
				if (tmpStr.indexOf(sepStr) == -1){
					sepStr = "\n";
				}
			}

			return sepStr;
		}


		/**
		 * 文字列が半角単位で何文字か数える
		 * @param	tmpStr	チェックする文字列
		 * @return	文字列の文字数
		 */
		private static int getWidthSize(String tmpStr){
			int retInt = 0;

			for (int lp=0; lp<tmpStr.length(); lp++){
				retInt += getCharWidth(tmpStr.substring(lp, lp+1));
			}

			return retInt;
		}

		/**
		 * 一文字が半角単位で何文字か数える
		 * @param	tmpStr	チェックする文字(一文字だけ)
		 * @return	文字数
		 */
		private static int getCharWidth(String tmpStr){
			int retInt = 1;

			if (ifDoublebyteString(tmpStr)){
				retInt = 2;
			}

			return retInt;
		}

		/**
		 * 文字が回転文字かをチェックする
		 * @param	tmpStr	チェックする文字
		 * @return	回転文字ならtrue、さもなくばfalseを返す
		 */
		private static boolean ifRotateString(String	tmpStr){
			boolean retBool = false;

			// 句読点チェック
			if (sepStr.indexOf(tmpStr) != -1){
				Xadjust = +(mFontDotSize / 2);
				Yadjust = -(mFontDotSize / 2);
				retBool = false;
			} else {
				// 撥促音便チェック
				if (smallStr.indexOf(tmpStr) != -1){
					Yadjust = -(mFontDotSize / 6);
					Xadjust = +(mFontDotSize / 7);
					retBool = false;
				} else {
					// 全角回転文字チェック
					if (	(rotateStr1.indexOf(tmpStr) != -1)
							||	(rotateStr0.indexOf(tmpStr) != -1)
							||	(rotateStr2.indexOf(tmpStr) != -1)){
						Yadjust = -(mFontDotSize) + 1;
						Xadjust =  (mFontDotSize / 2) - 6;
						retBool = true;
					} else {

						// 半角回転文字チェック
						if (	(rotateStr3.indexOf(tmpStr) != -1)
								||	(rotateStr4.indexOf(tmpStr) != -1)
								||	(rotateStr5.indexOf(tmpStr) != -1)) {
							Yadjust = -(mFontDotSize / 2);
							Xadjust =  ((mFontDotSize * 4) - 60) / 10;
							retBool = true;
						}

					}
				}
			}
			return retBool;
		}


		/**
		 * 文字の回転処理を行う。
		 * @param	tmpStr		回転させる文字
		 * @param	doubleX 	描画基準Ｘ座標(倍サイズ)
		 * @param	doubleY 	描画基準Ｙ座標(倍サイズ)
		 * @param	Ylocate 	描画相対Ｙ座標
		 * @param	fm			フォントメトリクス
		 */
		public static void rotateStr(Graphics g,
				String		tmpStr,
				int			doubleX,
				int			doubleY,
				int			Ylocate,
				Font			font,
				FontMetrics	fm		){
			
			PixelGrabber	pg			= null;
			int 			pixel1[]	= null;
			int 			tmpBgClr	= 0;
			Image			tmpImg		= null;
			Graphics		tmpG		= null;
			int 			tmpWidth	= fm.stringWidth(tmpStr) + 1;
			int 			tmpHeight	= fm.getMaxAscent()+fm.getMaxDescent();

			tmpImg = mContainer.createImage(tmpWidth, tmpHeight);
			tmpG = tmpImg.getGraphics();

			tmpG.setColor(Color.white);
			tmpG.fillRect(0, 0, tmpWidth, tmpHeight);
			tmpG.setFont(font);
			tmpG.setColor(mColor);

			tmpG.drawString(tmpStr, 1, fm.getMaxAscent());
//			g.drawString(tmpStr, doubleX/2, doubleY/2);

			pixel1 = new int[tmpWidth * tmpHeight];
			pg = new PixelGrabber(tmpImg, 0, 0, tmpWidth, tmpHeight, pixel1, 0, tmpWidth);
			try {
				pg.grabPixels();
			} catch(InterruptedException e){}
			
			tmpBgClr = pixel1[0];

	        Graphics2D g2 = (Graphics2D)g;
	        g2.setStroke(new BasicStroke(1.0f));
			for (int yy = 0; yy < tmpHeight; yy++){
				for (int xx = 0; xx < tmpWidth; xx++){
					if (pixel1[xx + (yy * tmpWidth)] != tmpBgClr){
						int tmpX = (tmpHeight-yy-2) + (doubleX-fm.getHeight()) / 2;
						int tmpY = ((xx*2)+ doubleY + Ylocate
								+ (mFontDotSize-tmpWidth)) / 2;
						g.setColor(new Color(pixel1[xx + (yy * tmpWidth)]));
						g.drawLine(tmpX+1, tmpY, tmpX+1, tmpY);
					}
				}
			}
			
			tmpG.dispose();
			tmpImg.flush();
		}

		/**
		 * 
		 * 文字がぶら下げ文字かをチェックする
		 * @param	tmpStr	チェックする文字
		 * @return	ぶら下げ文字ならtrue、さもなくばfalseを返す
		 */
		private static boolean ifDungleString(String	tmpStr	){
			boolean retBool = false;

			if (	(sepStr.indexOf(tmpStr) != -1)
					||	(smallStr  .indexOf(tmpStr) != -1)
					||	(dangleStr .indexOf(tmpStr) != -1)
					||	(rotateStr1.indexOf(tmpStr) != -1)
					||	(rotateStr4.indexOf(tmpStr) != -1)){
				retBool = true;
			}

			return retBool;
		}

		/**
		 * 文字が回り込み文字かをチェックする
		 * @param	tmpStr	チェックする文字
		 * @return	回り込み文字ならtrue、さもなくばfalseを返す
		 */
		private static boolean ifMawarikomiString(String	tmpStr){
			boolean retBool = false;

			if (	(rotateStr2.indexOf(tmpStr) != -1)
					||	(rotateStr5.indexOf(tmpStr) != -1)){
				retBool = true;
			}

			return retBool;
		}

		/**
		 * 文字が２バイト文字か調べる
		 * @param	tmpStr	検査する文字
		 * @return	２バイト文字ならtrue、さもなくばfalse
		 */
		private static boolean ifDoublebyteString(String	tmpStr){
			boolean retBool = true;

/*			if (	(rotateStr3.indexOf(tmpStr) != -1)
					||	(rotateStr4.indexOf(tmpStr) != -1)
					||	(rotateStr5.indexOf(tmpStr) != -1)){
				retBool = false;
			}
*/
			return retBool;
		}
	    public void load(DataInputStream in) {
	    	try {
	    		mType = 2;
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
	    		
	    		out.writeChar('T');		// Text
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
	    
	    public String readString(DataInputStream in) {
	    	try {

	    		int size = in.readInt();
	    		char[] buffer = new char[size];
	    		int index = 0;
	    		while (size-- > 0) {
	    			buffer[index++] = in.readChar();
	    		}
	    		
	    		return (new String(buffer));
	    		
	    	} catch(Exception  ex){
	    		assert(false);
	    	} 
	    	
	    	return null;
	    }

}
