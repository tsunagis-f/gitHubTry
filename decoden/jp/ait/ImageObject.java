package jp.ait;

import java.awt.*;
import java.awt.image.*;
import java.io.*;

import javax.swing.JOptionPane;

//MyImageObserver.java
import java.awt.*;
import java.awt.image.*;

/*
class MyImageObserver implements ImageObserver{
	public boolean imageUpdate(Image img, int infoflags, int x,int y, int width, int height) {
		if((infoflags & ALLBITS)==ALLBITS){
			System.out.println("画像のロード完了");
		} else {
			System.out.println("infoflags="+infoflags);			
		}
		return true;
	}
}
*/

/**
 * イメージ画像オブジェクト
 * 
 * @author kagi
 */
class ImageObject extends Group {
	// 表示用のサイズになっている画像データ
    private BufferedImage mImg;
    private Container mContainer;
    
	// 2007/12/30 移動速度向上
    private Thumbnail mThumbnail;	// サムネイルの中に元画像が入っている
    						// これが null の場合は　お客様素材からのデータとなる
    
    private BufferedImage mUserImg;	// お客様素材の画像
   
    ImageObject(BufferedImage img) {
    	setImage(img);
    	mRotate = 0;
		mType = 1;
		
		// 2007/12/30 移動速度向上
		mThumbnail = null;
   }
    
   ImageObject(Thumbnail thum) {
    	mRotate = 0;
		mType = 1;
		
		// 2007/12/30 移動速度向上
		mThumbnail = thum;
		mUserImg = null;
   }
    
    ImageObject() {
    	mRotate = 0;
		mType = 1;
		
		// 2007/12/30 移動速度向上
		mThumbnail = null;
		mUserImg = null;
    }
    
    public void clear() {
    	mImg = null;
    	mThumbnail = null;
    	mUserImg = null;
    }

    public void setImage(BufferedImage img) {
    	mUserImg = img;
    }

    public BufferedImage getImage() {
      return mImg;
    }

	public void draw(Graphics g, Container boad) {
		mContainer = boad;
		int x = (int)(edenpo.Graphics_Panel.mDocX + mX*mScale);
		int y = (int)(edenpo.Graphics_Panel.mDocY +mY*mScale);
		int width = (int)(mWidth*mScale);
		int height= (int)(mHeight*mScale);
		
		boolean bRet;
		// ★ PDF書き出しの時巨大な元画像から drawImageを行うと消える場合があるので
		//    描画用(描画サイズの４倍(通常))の画像を使う
/*
		if (g instanceof com.lowagie.text.pdf.PdfGraphics2D) {
			BufferedImage original_img;
			if (mUserImg != null) {
				original_img = mUserImg;
			} else {
				original_img = mThumbnail.getOriginalImage();
			}
//			original_img.flush();
			bRet = g.drawImage(original_img, x, y, width, height, new MyImageObserver());
			
		} else {
			bRet = g.drawImage(mImg, x, y, width, height, new MyImageObserver());
		}
*/
		bRet = g.drawImage(mImg, x, y, width, height, boad);

//System.out.println("ImageObject draw bRet="+bRet);
//System.out.println("ImageObject draw x="+x+" y="+y+" width="+width+" height="+height+" mScale="+mScale+"  mWidth="+mWidth+"  mHeight="+mHeight);
	}
	
	public Group copy() {
		ImageObject obj = null;
//		try {
	
			obj = new ImageObject();
			
			obj.mImg =  new BufferedImage(mImg.getWidth(),mImg.getHeight(), BufferedImage.TYPE_INT_ARGB);
			Graphics g = obj.mImg.getGraphics();
			g.drawImage(mImg, 0, 0, null);
			g.dispose();
			obj.mImg.flush();

			obj.mX = mX + COPY_OFFSET * mScale;
			obj.mY = mY + COPY_OFFSET * mScale;
			obj.mWidth = mWidth;
			obj.mHeight = mHeight;
			obj.mRotate = mRotate;
			obj.mType = mType;
			
			// 2007/12/30 移動速度向上
			obj.mThumbnail = mThumbnail;
			obj.mUserImg = mUserImg;
		
			// 現在を倍率を設定
			obj.setScale(edenpo.Graphics_Panel.getScale());
		
			
//		} catch (OutOfMemoryError e) {
//			JOptionPane.showMessageDialog(edenpo.Main_Frame, Message.MEM_ERROR);
//		}
		
		return obj;
	}
	
	// 2007/12/30 移動速度向上
	// 元画像から表示用Imageを指定サイズで作成する
	/**
	 *  width, height : スケールに影響を受けない実サイズ
	 */
	public void size(double x, double y, double width, double height) {
		super.size(x, y, width, height);
		
//System.out.println("ImageObject size  width="+width+" height="+height);

		BufferedImage original_img;
		if (mUserImg != null) {
			original_img = mUserImg;
		} else {
			original_img = mThumbnail.getOriginalImage();
		}
//		int width2 = (int)(width*mScale);
//		int height2 = (int)(height*mScale);
		
		double width2 = width;
		double height2 = height;
		// 90度回転が入っている場合は元データの縦横サイズにする。
		if (mRotate == 1 || mRotate == 3) {
			height2 = width2;
			width2 = height;
			
            double tmp = mWidth;
            mWidth = mHeight;
            mHeight = tmp;
		}
		
		// サイズは解像度を考慮して4倍で作成
		width2 *= 4.0;
		height2 *= 4.0;
		if (width2 > original_img.getWidth() || height2 > original_img.getHeight()) {
			// 指定したサイズの4倍が　元画像より大きくなる場合
			
			if (original_img.getWidth() > edenpo.Graphics_Panel.mOrgDocWidth * 2 || 
					original_img.getHeight() > edenpo.Graphics_Panel.mOrgDocWidth * 2) {
				// 元画像がドキュメントの２倍より大きい時は、指定サイズの２倍にする
				width2 /= 2.0;
				height2 /= 2.0;
			} else {
				// 元画像がドキュメントの２倍より小さいので、元画像のサイズを使う
				width2 = original_img.getWidth();
				height2 = original_img.getHeight();
			}
		}
	
//System.out.println("ImageObject size  width2="+width2+" height2="+height2+"  mScale="+mScale+"  mWidth="+mWidth+"  mHeight="+mHeight+"  mRotate="+mRotate);
		BufferedImage img =  new BufferedImage((int)width2, (int)height2, BufferedImage.TYPE_INT_ARGB);		
		Graphics g = img.getGraphics();
		g.drawImage(original_img, 0, 0, (int)width2, (int)height2, null);
		g.dispose();
		img.flush();
		
		mImg = img;
		
		// 回転の指定があれば表示画像を回転させる
		int rotate_sav = mRotate;
		for (int i = 0; i < rotate_sav; i++) {
//System.out.println("size mRotate ="+mRotate);
			rotate();
		}
		mRotate = rotate_sav;
//System.out.println("ImageObject2 size  width="+width2+" height="+height2+"  mScale="+mScale+"  mWidth="+mWidth+"  mHeight="+mHeight+"  mRotate="+mRotate);

		}
	
	
    /**
     * 回転を変更する
     */
    public void rotate() {
   		mRotate++;
   		if (mRotate > 3)
   			mRotate = 0;   	
   		
   		rotate(mRotate);
    }
    public void rotate(int r) {
 
//System.out.println("ImageObject rotate ="+r);
 
    	boolean reverse = false;
    	
    	if (r == 3 && mRotate == 0) {
  			reverse = true;
    	} else {
    		if (mRotate >r) {
    			reverse = true;
    		}
    	}
         PixelGrabber pg = new PixelGrabber(mImg, 0, 0, -1, -1, true);
         try{
            if(pg.grabPixels()){
                int width = pg.getWidth();
                int height = pg.getHeight();
                int[] src = (int[])pg.getPixels();
                int[] dest = (int[])new int[width * height];
                int p1, p2;
                
                for (int i = 0; i < height; i++) {
                    for (int j = 0; j < width; j++) {
                   		p1 = i * width + j;
                    	if (reverse) {		// 逆回転
                    		p2 = i + (width - j - 1) * height;
                     		
                    	} else {
                     		p2 = (height - (i + 1)) + (j * height);
                    	}
                    	dest[p2] = src[p1];
                    }
                }
                Image img;
                img = mContainer.createImage(new MemoryImageSource(height, width, dest, 0, height));
//        		mImg =  new BufferedImage(height, width, BufferedImage.TYPE_INT_RGB);
        		mImg =  new BufferedImage(height, width, BufferedImage.TYPE_INT_ARGB);
        		Graphics2D g2 = (Graphics2D)mImg.getGraphics();
                g2.drawImage(img, 0, 0, mContainer);
                g2.dispose();
                mImg.flush();
                
                double tmp = mWidth;
                mWidth = mHeight;
                mHeight = tmp;
               
          		mRotate = r;
          		
//System.out.println("ImageObject rotate mDocWidth="+edenpo.Graphics_Panel.mDocWidth+" mDocHeight="+edenpo.Graphics_Panel.mDocHeight);
          		
				// mWidth, mHeight はスケールに影響しないサイズなので
				// スケールに影響しないドキュメントサイズを計算する
				double docWidth = edenpo.Graphics_Panel.mDocWidth / mScale;
				double docHeight = edenpo.Graphics_Panel.mDocHeight / mScale;
				
        		/**
        		 * 回転した時　作業領域からはみ出る場合。
        		 */
          		double rate;
        		if (mX+mWidth > docWidth ||
        				mY+mHeight > docHeight) {
        			if (mX+mWidth > docWidth) {
        				rate = mHeight / mWidth;
        				mWidth = docWidth - mX;
        				mHeight = mWidth * rate;
         			} else {
        				rate = mWidth / mHeight;
        				mHeight = docHeight - mY;
        				mWidth = mHeight * rate;
        			}
/*
        			ImageFilter fl;
        			fl = new AreaAveragingScaleFilter((int)mWidth , (int)mHeight);			// こっちの方が鮮明（だが少し遅い）
        			FilteredImageSource fis = new FilteredImageSource(mImg.getSource() , fl);       		
        			img = mContainer.createImage(fis);
            		mImg =  new BufferedImage((int)mWidth, (int)mHeight, BufferedImage.TYPE_INT_ARGB);
            		g2 = (Graphics2D)mImg.getGraphics();
                    g2.drawImage(img, 0, 0, mContainer);
                    g2.dispose();
                    mImg.flush();
*/
        		}

          	}
        }catch(InterruptedException ie){}
    }
 
    public void load(DataInputStream in) {
		try {	
			mType = 1;
			mX = in.readDouble();
			mY = in.readDouble();   
			mWidth = in.readDouble();   
			mHeight = in.readDouble();   
			int size = in.readInt();   
			mUserImg = 	edenpo.Graphics_Panel.readImage(in, size);
			size(mX, mY, mWidth, mHeight);
     		
    	} catch(Exception  ex){
    		assert(false);
    	}
    }

    public void save(DataOutputStream out) {
		try {	
    		out.writeChar('I');		
    		out.writeDouble(mX);
    		out.writeDouble(mY);   
    		out.writeDouble(mWidth);   
    		out.writeDouble(mHeight);   
 				
     		out.flush();
    		
     		edenpo.Graphics_Panel.writeImage(out, mImg);
     		
    	} catch(Exception  ex){
    		assert(false);
    	}
    }
    
 }
