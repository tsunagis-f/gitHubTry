package pdf.net.edenpo;

import com.swetake.util.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.*;
import java.io.File;

import javax.imageio.ImageIO;

public class QRCodeImage
{
    public static int CELL_SIZE = 1;
    
    private BufferedImage _qrCodeImagePNG;
    public Image getQRCodeImagePNG()
    {
        return this._qrCodeImagePNG;
    }
    
    
    /************* コンストラクタ **************/
    public QRCodeImage(String target)
    {
        Qrcode qrcodeLogic = new Qrcode();
        qrcodeLogic.setQrcodeEncodeMode('*');
        qrcodeLogic.setQrcodeErrorCorrect('M');
        qrcodeLogic.setQrcodeVersion(7);
        
        byte[] targetBytes = target.getBytes();
        boolean[][] qrcodeData = qrcodeLogic.calQrcode(targetBytes);
        
        BufferedImage image = new BufferedImage(
                QRCodeImage.CELL_SIZE * (qrcodeData[0].length + 8),
                QRCodeImage.CELL_SIZE * (qrcodeData[0].length + 8),
                BufferedImage.TYPE_INT_BGR);
        Graphics g = image.getGraphics();
        g.setColor(Color.white);
        g.fillRect(0, 0, image.getWidth(), image.getHeight());
        
        for(int i = 0; i < qrcodeData.length; i++)
        {
            for(int j = 0; j < qrcodeData[i].length; j++)
            {
                boolean cellFilled = qrcodeData[i][j];
                Color cellColor = cellFilled ? Color.BLACK : Color.WHITE;
                g.setColor(cellColor);
                g.fillRect(QRCodeImage.CELL_SIZE * (i + 4),
                        QRCodeImage.CELL_SIZE * (j + 4),
                        QRCodeImage.CELL_SIZE, QRCodeImage.CELL_SIZE);
            }
        }
        this._qrCodeImagePNG = image;
    }
    
    public boolean createQRCodeImageFile()
    {
        if(this._qrCodeImagePNG == null) return false;
        
        boolean retbool;
        try
        {
            retbool = ImageIO.write(this._qrCodeImagePNG, "png", new File("qrcodeImage.png"));
        }
        catch(Exception e)
        {
            e.printStackTrace();
            retbool = false;
        }

        return retbool;
    }

}
