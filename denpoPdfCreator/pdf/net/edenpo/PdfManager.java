package pdf.net.edenpo;

import java.awt.*;
import java.io.*;
import java.text.*;
import java.util.*;

import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.*;

public class PdfManager {
    // 設定ファイル
    private Properties prop;
    
    // 一時変数
    private Document doc;
    private ByteArrayOutputStream out; 
    private PdfWriter writer;
    private PdfReader reader;
    private PdfContentByte cb;
    private ColumnText ct;
    private Font font;
    private Paragraph par;
    private String buf;
    // 一時変数（位置）
    private float startX;    // 開始点のＸ座標
    private float startY;    // 開始点のＹ座標
    private int maxLine;     // 最大行数
    private int maxCount;    // １行の最大の文字数
    private int angle;       // 回転角度
    private float tmpX;      // Ｘ座標の調整値
    private float tmpY;      // Ｙ座標の調整値
    private float x;         // 現在のＸ座標
    private float y;         // 現在のＹ座標
    private int line;        // 現在の行数
    private int count;       // 現在の行の文字数
    private char c;          // 部分文字列の格納先
    private int index;       // 文字列の現在位置インデックス
    
    /*
     * 特殊文字
     */
    // ぶら下げ文字(特殊インデント１)
    static String sepStr = "、。，．";
    // ぶら下げ文字(特殊インデント２)
    static String smallStr = "ぁぃぅぇぉっゃゅょゎァィゥェォヵヶッャュョヮ";
    // ぶら下げ文字
    static String dangleStr = "々ヽヾゝゞ！？";
    // 回転文字
    static String rotateStr0 = "＝≠≦≧∈∋⊆⊇⊂⊃∪∩←↑→↓";
    // 回転ぶら下げ文字
    static String rotateStr1 = "＞≫」ー－‐』｝）》】〕］〉｜―～…‥：";
    // 回転回り込み文字
    static String rotateStr2 = "＜≪「『｛（《【〔［〈";
    // 回転半角文字
    static String rotateStr3 = "1234567890^\\qwertyuiop@asdfghjklzxcvbnm #$%|QWERTYUIOPASDFGHJKLZXCVBNM_ﾇﾌｱｳｴｵﾔﾕﾖﾜﾎﾍﾀﾃｲｽｶﾝﾅﾆﾗｾﾁﾄｼﾊｷｸﾏﾉﾘﾚｹﾑﾂｻｿﾋｺﾐﾓﾈﾙﾒﾛｦ";
    // 回転ぶら下げ半角文字
    static String rotateStr4 = "-;:],./)!\"&=+*}>?ﾞﾟｩｪｫｬｭｮ｣ｨｯ｡､･";
    // 回転回り込み半角文字
    static String rotateStr5 = "(['`~{<｢";// 改行時に回り込ませる半角文字
    // 全角へ変換する半角文字のリスト
    static String singleByteStr = "1234567890^\\qwertyuiop@asdfghjklzxcvbnm #$%|QWERTYUIOPASDFGHJKLZXCVBNM_-;:],./)!\"&=+*}>?ｩｪｫｬｭｮ｣ｨｯ｡､･(['`~{<｢??";
    // 対応する全角文字のリスト
    static String doubleByteStr = "１２３４５６７８９０＾￥ｑｗｅｒｔｙｕｉｏｐ＠ａｓｄｆｇｈｊｋｌｚｘｃｖｂｎｍ　＃＄％｜ＱＷＥＲＴＹＵＩＯＰＡＳＤＦＧＨＪＫＬＺＸＣＶＢＮＭ＿－；：］，．／）！”＆＝＋＊｝＞？ゥェォャュョ」ィッ。、・（［’‘～｛＜「－－";
    // 時刻フォーマット
    static private SimpleDateFormat datetime_format = new SimpleDateFormat("yyyy 年 MM 月 dd 日 HH:mm");
    static private SimpleDateFormat date_format = new SimpleDateFormat("yyyy 年 MM 月 dd 日");
    // 敬称
    static private String[] titleAry = new String[100];
    
    // 初期化
    public PdfManager(int i) {
        // プロパティファイルの読み込み
        prop = new Properties();
        try {
            switch(i) {
            case 1: // 横、画像なし
                prop.load(new FileInputStream("Layout1.properties"));
                break;
            case 2: // 横、画像１枚
                prop.load(new FileInputStream("Layout2.properties"));
                break;
            case 3: // 縦、画像１枚
                prop.load(new FileInputStream("Layout3.properties"));
                break;
            case 4: // 縦、画像なし
                prop.load(new FileInputStream("Layout4.properties"));
                break;
            case 5: // 横、画像２枚
                prop.load(new FileInputStream("Layout5.properties"));
                break;
            case 6: // 縦、画像２枚
                prop.load(new FileInputStream("Layout6.properties"));
                break;
            case 7: // 横、画像３枚
                prop.load(new FileInputStream("Layout7.properties"));
                break;
            case 8: // 縦、画像３枚
                prop.load(new FileInputStream("Layout8.properties"));
                break;
            case 10: // エンディング電報、横、画像なし
                prop.load(new FileInputStream("Layout10.properties"));
                break;
            case 11: // エンディング電報、横、画像１枚
                prop.load(new FileInputStream("Layout11.properties"));
                break;
            case 12: // エンディング電報、横、画像２枚
                prop.load(new FileInputStream("Layout12.properties"));
                break;
            case 13: // エンディング電報、横、画像３枚
                prop.load(new FileInputStream("Layout13.properties"));
                break;
            }
            // 肩書き配列の初期化
            titleAry[0] = "";
            titleAry[1] = "様";
            titleAry[2] = "御中";
            titleAry[3] = "殿";
            titleAry[4] = "君";
            titleAry[5] = "くん";
            titleAry[6] = "さん";
            titleAry[7] = "ちゃん";
            titleAry[8] = "先生";
            titleAry[9] = "師匠";
            titleAry[10] = "師範";
            titleAry[11] = "親方";
            titleAry[12] = "気付";
            titleAry[13] = "様方";
            titleAry[99] = "肩書";
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    // 電報メイン領域を出力する
    public PdfReader getMainPdfReader(Purchase purchase) throws DocumentException, IOException {
        // 動画電報はメインが空白
        if ((purchase.movieFlag != null) && (purchase.movieFlag)) {
            return getMovieMainPdfReader(purchase);
        }
        
        int direction = Integer.parseInt(prop.getProperty("direction"));
        if (purchase.isMainImagePdf()) {
            // 電報メイン領域（画像）
            return getImageMainPdfReader(purchase);
        } else if (direction == 1) {
            // 電報メイン領域（横書）
            return getHorizontalMainPdfReader(purchase);
        } else {
            // 電報メイン領域（縦書）
            return getVerticalMainPdfReader(purchase);
        }
    }
    
    // 電報メイン領域（横書）を出力
    public PdfReader getHorizontalMainPdfReader(Purchase purchase) throws DocumentException, IOException {
        // Pdfの初期化
        doc = new Document(PageSize.A5.rotate());
        try {
        this.initPdf();
        
        // ベースフォントの初期化
        BaseFont bfs[] = new BaseFont[3];
        bfs[0] = BaseFont.createFont("A-OTF-RyuminPr6N-ExBold.ttf" , BaseFont.IDENTITY_H, false); // 明朝
        bfs[1] = BaseFont.createFont("A-OTF-ShinGoPr6N-Regular.otf", BaseFont.IDENTITY_H, false); // ゴシック
        bfs[2] = BaseFont.createFont("A-OTF-KakuGyoStd-Medium.otf" , BaseFont.IDENTITY_H, false); // 毛筆
        //bfs[0] = BaseFont.createFont("HGRME.TTC,0" , BaseFont.IDENTITY_H, false); // 明朝
        //bfs[1] = BaseFont.createFont("HGRGM.TTC,0" , BaseFont.IDENTITY_H, false); // ゴシック
        //bfs[2] = BaseFont.createFont("HGRSNG.TTC,0", BaseFont.IDENTITY_H, false); // 毛筆
        // 受取人
        font = new Font(bfs[purchase.recipientFontId - 1], purchase.recipientFontSize);
        ct.setSimpleColumn(mm2pt(key2int("recipient.position.x1")), mm2pt(key2int("recipient.position.y1")), mm2pt(key2int("recipient.position.x2")), mm2pt(key2int("recipient.position.y2")), 14, Element.ALIGN_TOP);
        par = new Paragraph();
        buf = convertHyphen(purchase.recipient);
        // 肩書の追加
        if(purchase.recipientTitle > 0) {
            buf = buf + "　" + titleAry[purchase.recipientTitle];
        }
        par.add(new Phrase(buf, font));
        ct.addText(par);
        ct.go();
        // recipient2は存在する場合のみ追加
        if(purchase.recipient2 != null && purchase.recipient2.length() > 0) {
            ct.setSimpleColumn(mm2pt(key2int("recipient2.position.x1")), mm2pt(key2int("recipient2.position.y1")), mm2pt(key2int("recipient2.position.x2")), mm2pt(key2int("recipient2.position.y2")), 14, Element.ALIGN_TOP);
            par = new Paragraph();
            buf = convertHyphen(purchase.recipient2);
            // 肩書の追加
            if(purchase.recipient2Title > 0) {
                buf = buf + "　" + titleAry[purchase.recipient2Title];
            }
            par.add(new Phrase(buf, font));
            ct.addText(par);
            ct.go();
        }
        cb.stroke();
        
        // 差出人項目
        font = new Font(bfs[purchase.senderFontId - 1], purchase.senderFontSize);
        // 差出人項目は、それぞれ存在する時のみ追加
        if(purchase.sender1 != null) {
            ct.setSimpleColumn(mm2pt(key2int("sender.position.x1")), mm2pt(key2int("sender.position.y1")), mm2pt(key2int("sender.position.x2")), mm2pt(key2int("sender.position.y2")), 14, Element.ALIGN_TOP);
            par = new Paragraph();
            par.add(new Phrase(0, convertHyphen(purchase.sender1), font));
            ct.addText(par);
            ct.go();
        }
        if(purchase.sender2 != null) {
            ct.setSimpleColumn(mm2pt(key2int("sender2.position.x1")), mm2pt(key2int("sender2.position.y1")), mm2pt(key2int("sender2.position.x2")), mm2pt(key2int("sender2.position.y2")), 14, Element.ALIGN_TOP);
            par = new Paragraph();
            par.add(new Phrase(0, convertHyphen(purchase.sender2), font));
            ct.addText(par);
            ct.go();
        }
        if(purchase.sender3 != null) {
            ct.setSimpleColumn(mm2pt(key2int("sender3.position.x1")), mm2pt(key2int("sender3.position.y1")), mm2pt(key2int("sender3.position.x2")), mm2pt(key2int("sender3.position.y2")), 14, Element.ALIGN_TOP);
            par = new Paragraph();
            par.add(new Phrase(0, convertHyphen(purchase.sender3), font));
            ct.addText(par);
            ct.go();
        }
        if(purchase.sender4 != null) {
            ct.setSimpleColumn(mm2pt(key2int("sender4.position.x1")), mm2pt(key2int("sender4.position.y1")), mm2pt(key2int("sender4.position.x2")), mm2pt(key2int("sender4.position.y2")), 14, Element.ALIGN_TOP);
            par = new Paragraph();
            par.add(new Phrase(0, convertHyphen(purchase.sender4), font));
            ct.addText(par);
            ct.go();
        }
        if(purchase.sender5 != null) {
            ct.setSimpleColumn(mm2pt(key2int("sender5.position.x1")), mm2pt(key2int("sender5.position.y1")), mm2pt(key2int("sender5.position.x2")), mm2pt(key2int("sender5.position.y2")), 14, Element.ALIGN_TOP);
            par = new Paragraph();
            par.add(new Phrase(0, convertHyphen(purchase.sender5), font));
            ct.addText(par);
            ct.go();
        }
        if(purchase.sender6 != null) {
            ct.setSimpleColumn(mm2pt(key2int("sender6.position.x1")), mm2pt(key2int("sender6.position.y1")), mm2pt(key2int("sender6.position.x2")), mm2pt(key2int("sender6.position.y2")), 14, Element.ALIGN_TOP);
            par = new Paragraph();
            par.add(new Phrase(0, convertHyphen(purchase.sender6), font));
            ct.addText(par);
            ct.go();
        }
        if(purchase.sender7 != null) {
            ct.setSimpleColumn(mm2pt(key2int("sender7.position.x1")), mm2pt(key2int("sender7.position.y1")), mm2pt(key2int("sender7.position.x2")), mm2pt(key2int("sender7.position.y2")), 14, Element.ALIGN_TOP);
            par = new Paragraph();
            par.add(new Phrase(0, convertHyphen(purchase.sender7), font));
            ct.addText(par);
            ct.go();
        }
        cb.stroke();
        
        // 本文
        font = new Font(bfs[purchase.subscriptionFontId - 1], purchase.subscriptionFontSize);
        ct.setSimpleColumn(mm2pt(key2int("subscription.position.x1")), mm2pt(key2int("subscription.position.y1")), mm2pt(key2int("subscription.position.x2")), mm2pt(key2int("subscription.position.y2")), 25, Element.ALIGN_TOP);
        par = new Paragraph();
        par.add(new Phrase(0, convertHyphen(purchase.subscription), font));
        ct.addText(par);
        ct.go();
        cb.stroke();
        
        // ユーザー画像を追加
        this.addPurchaseImage(purchase, doc, cb);

        // 20130722 : kawasaki : [1822]バーコードキッティングの為の改修
        this.addDeliveryBarcodeImage(purchase, doc, true);
        
        } finally { doc.close(); }
        reader = new PdfReader(out.toByteArray());
        return reader;
    }
    
    // 電報メイン領域（縦書）を出力
    public PdfReader getVerticalMainPdfReader(Purchase purchase) throws DocumentException, IOException {
        // Pdfの初期化
        doc = new Document(PageSize.A5);
        try {
        this.initPdf();
        
        // ベースフォントの初期化
        BaseFont bfs[] = new BaseFont[3];
        bfs[0] = BaseFont.createFont("A-OTF-RyuminPr6N-ExBold.ttf" , BaseFont.IDENTITY_V, false); // 明朝
        bfs[1] = BaseFont.createFont("A-OTF-ShinGoPr6N-Regular.otf", BaseFont.IDENTITY_V, false); // ゴシック
        bfs[2] = BaseFont.createFont("A-OTF-KakuGyoStd-Medium.otf" , BaseFont.IDENTITY_V, false); // 毛筆
        //bfs[0] = BaseFont.createFont("MIN.ttf"    , BaseFont.IDENTITY_V, false); // 明朝
        //bfs[1] = BaseFont.createFont("HGRGM.TTC,0", BaseFont.IDENTITY_V, false); // ゴシック
        //bfs[2] = BaseFont.createFont("MOU.ttf"    , BaseFont.IDENTITY_V, false); // 毛筆
        // 縦書きの場合、念のため明示的に書式を「右から左」に指定
        ct.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
        
        // 受取人
        font = new Font(bfs[purchase.recipientFontId - 1], purchase.recipientFontSize);
        startX = mm2pt(key2int("recipient.position.x"));    // 開始点のＸ座標
        startY = mm2pt(key2int("recipient.position.y"));    // 開始点のＹ座標
        maxLine = 2;    // 最大行数
        maxCount = setMaxCount(purchase.recipientFontSize, 0);    // １行の最大の文字数
        tmpX = 0;   // Ｘ座標の調整値
        tmpY = 0;   // Ｙ座標の調整値
        x = startX; // 現在のＸ座標
        y = startY; // 現在のＹ座標
        line = 0;   // 現在の行数
        count = 0;  // 現在の行の文字数
        
        String text = purchase.recipient;
        
        // 肩書の追加
        if(purchase.recipientTitle > 0) {
            text = text + "　" + titleAry[purchase.recipientTitle];
        }
        if(purchase.recipient2 != null && purchase.recipient2.length() > 0) {
            text = text + "\n" + purchase.recipient2;
            if(purchase.recipient2Title > 0) {
                text = text + "　" + titleAry[purchase.recipient2Title];
            }
        }
        for(int i = 0; i < text.length() ; i++){
            c = text.charAt(i);
            if(c == '\n') {
                /* ここが行の調整！ */
                x -= 18;    // 行間は固定
                y = startY;
                count = 0;
                line++;
            }else {
                index = singleByteStr.indexOf(c + "");
                if(index != -1) {
                    c = doubleByteStr.charAt(index);
                }
                if(ifRotateString(c + "")) {
                    angle = 270;
                    // フォントサイズに応じて適当に調整
                    tmpX = purchase.recipientFontSize / 2;
                    tmpY = -(purchase.recipientFontSize / 2);
                }else if(ifSmallString(c + "")) {
                    angle = 0;
                    // フォントサイズに応じて適当に調整
                    tmpX = purchase.recipientFontSize / 4 - 1;
                    tmpY = purchase.recipientFontSize / 4;
                }else if(ifSepString(c + "")) {
                    angle = 0;
                    // フォントサイズに応じて適当に調整
                    tmpX = purchase.recipientFontSize / 2;
                    tmpY = purchase.recipientFontSize / 2;
                }else {
                    angle = 0;
                    tmpX = 0;
                    tmpY = 0;
                }
                ColumnText.showTextAligned(cb,
                                    PdfContentByte.ALIGN_LEFT,
                                    new Phrase(c + "", font), x + tmpX, y + tmpY, angle);
                y -= purchase.recipientFontSize;
                count++;
            }
            //下まで行ったら折り返し
            if(count >= maxCount){
                x -= 14;    // 行間は固定
                y = startY;
                count = 0;
                line++;
            }
            //左下までいったら無視
            if(line >= maxLine){
                break;
            }
        }
        cb.stroke();
        
        // 差出人項目
        font = new Font(bfs[purchase.senderFontId - 1], purchase.senderFontSize);
        startX = mm2pt(key2int("sender.position.x"));    // 開始点のＸ座標
        startY = mm2pt(key2int("sender.position.y"));    // 開始点のＹ座標
        maxLine = 7;    // 最大行数
        maxCount = setMaxCount(purchase.senderFontSize, 1);    // １行の最大の文字数
        tmpX = 0;   // Ｘ座標の調整値
        tmpY = 0;   // Ｙ座標の調整値
        x = startX; // 現在のＸ座標
        y = startY; // 現在のＹ座標
        line = 0;   // 現在の行数
        count = 0;  // 現在の行の文字数
        
        text = "";
        if(purchase.sender1 != null && purchase.sender1.length() > 0) {
            text = text + purchase.sender1 + "\n";
        }else {
            text = text + "" + "\n";
        }
        if(purchase.sender2 != null && purchase.sender2.length() > 0) {
            text = text + purchase.sender2 + "\n";
        }else {
            text = text + "" + "\n";
        }
        if(purchase.sender3 != null && purchase.sender3.length() > 0) {
            text = text + purchase.sender3 + "\n";
        }else {
            text = text + "" + "\n";
        }
        if(purchase.sender4 != null && purchase.sender4.length() > 0) {
            text = text + purchase.sender4 + "\n";
        }else {
            text = text + "" + "\n";
        }
        if(purchase.sender5 != null && purchase.sender5.length() > 0) {
            text = text + purchase.sender5 + "\n";
        }else {
            text = text + "" + "\n";
        }
        if(purchase.sender6 != null && purchase.sender6.length() > 0) {
            text = text + purchase.sender6 + "\n";
        }else {
            text = text + "" + "\n";
        }
        if(purchase.sender7 != null && purchase.sender7.length() > 0) {
            text = text + purchase.sender7 + "\n";
        }else {
            text = text + "" + "\n";
        }
        for(int i = 0; i < text.length() ; i++){
            c = text.charAt(i);
            if(c == '\n'){
                x -= 18;    // 行間は固定
                y = startY;
                count = 0;
                line++;
            }else{
                index = singleByteStr.indexOf(c + "");
                if(index != -1) {
                    c = doubleByteStr.charAt(index);
                }
                if(ifRotateString(c + "")){
                    angle = 270;
                    // フォントサイズに応じて適当に調整
                    tmpX = purchase.senderFontSize / 2;
                    tmpY = -(purchase.senderFontSize  / 2);
                }else if(ifSmallString(c + "")){
                    angle = 0;
                    // フォントサイズに応じて適当に調整
                    tmpX = purchase.senderFontSize / 4 - 1;
                    tmpY = purchase.senderFontSize / 4;
                }else if(ifSepString(c + "")){
                    angle = 0;
                    // フォントサイズに応じて適当に調整
                    tmpX = purchase.senderFontSize / 2;
                    tmpY = purchase.senderFontSize / 2;
                }else {
                    angle = 0;
                    tmpX = 0;
                    tmpY = 0;
                }
                ColumnText.showTextAligned(cb,
                                    PdfContentByte.ALIGN_LEFT,
                                    new Phrase(c + "", font), x + tmpX, y + tmpY, angle);
                y -= purchase.senderFontSize;
                count++;
            }
            //下まで行ったら折り返し
            if(count >= maxCount){
                x -= 18;    // 行間は固定
                y = startY;
                count = 0;
                line++;
            }
            //左下までいったら無視
            if(line >= maxLine){
                break;
            }
        }
        
        cb.stroke();
        // 本文
        font = new Font(bfs[purchase.subscriptionFontId - 1], purchase.subscriptionFontSize);
        startX = mm2pt(key2int("subscription.position.x"));    // 開始点のＸ座標
        startY = mm2pt(key2int("subscription.position.y"));    // 開始点のＹ座標
        maxLine = 6;    // 最大行数
        maxCount = setMaxCount(purchase.subscriptionFontSize, 1);    // １行の最大の文字数
        
        tmpX = 0; // Ｘ座標の調整値
        tmpY = 0; // Ｙ座標の調整値
        
        x = startX; // 現在のＸ座標
        y = startY; // 現在のＹ座標
        line = 0;   // 現在の行数
        count = 0;  // 現在の行の文字数
        
        text = purchase.subscription;
        for(int i = 0; i < text.length() ; i++){
            c = text.charAt(i);
            if(c == '\n'){
                x -= 25;    // 行間は固定
                y = startY;
                count = 0;
                line++;
            }else{
                index = singleByteStr.indexOf(c + "");
                if(index != -1) {
                    c = doubleByteStr.charAt(index);
                }
                if(ifRotateString(c + "")){
                    angle = 270;
                    // フォントサイズに応じて適当に調整
                    tmpX = purchase.subscriptionFontSize / 2;
                    tmpY = -(purchase.subscriptionFontSize  / 2);
                }else if(ifSmallString(c + "")){
                    angle = 0;
                    // フォントサイズに応じて適当に調整
                    tmpX = purchase.subscriptionFontSize / 4 - 1;
                    tmpY = purchase.subscriptionFontSize / 4;
                }else if(ifSepString(c + "")){
                    angle = 0;
                    // フォントサイズに応じて適当に調整
                    tmpX = purchase.subscriptionFontSize / 2;
                    tmpY = purchase.subscriptionFontSize / 2;
                }else{
                    angle = 0;
                    tmpX = 0;
                    tmpY = 0;
                }
                ColumnText.showTextAligned(cb,
                                    PdfContentByte.ALIGN_LEFT,
                                    new Phrase(c + "", font), x + tmpX, y + tmpY, angle);
                y -= purchase.subscriptionFontSize;
                count++;
            }
            //下まで行ったら折り返し
            if(count >= maxCount){
                x -= 25;    // 行間は固定
                y = startY;
                count = 0;
                line++;
            }
            //左下までいったら無視
            if(line >= maxLine){
                break;
            }
        }
        cb.stroke();
        
        // ユーザー画像を追加
        this.addPurchaseImage(purchase, doc, cb);

        // 20130722 : kawasaki : [1822]バーコードキッティングの為の改修
        this.addDeliveryBarcodeImage(purchase, doc, true);
        
        } finally { doc.close(); }
        reader = new PdfReader(out.toByteArray());
        return reader;
    }
    
    // 電報メイン領域（画像）を出力
    public PdfReader getImageMainPdfReader(Purchase purchase) throws DocumentException, IOException {
        if(purchase.siteTypeId == 6) {
          // 20130319 : kawasaki : Denpoppo対応
          doc = new Document(PageSize.A5);
          try {
          // Pdfの初期化
          this.initPdf();
          
          // ************メイン画像をはめ込む**********************
          Image msgImage = Image.getInstance(purchase.getMainImage());
          msgImage.scaleToFit(mm2pt(130), mm2pt(185));
          msgImage.setAbsolutePosition((doc.getPageSize().getWidth() - msgImage.getPlainWidth())/2, mm2pt(20));
          doc.add(msgImage);
          
          // 商品画像描画
          try {
            if (purchase.getItemImage() != null) {
                Image itemImage = purchase.getItemImage();
                itemImage.scaleToFit(mm2pt(12), mm2pt(11));
                itemImage.setAbsolutePosition(mm2pt(82), mm2pt(6));
                doc.add(itemImage);
            }
          } catch (Exception e) {
          }
          
          // 20130330 : kawasaki : [1475]配送確認用バーコード画像を追加(KDDIのみ)
          this.addDeliveryBarcodeImage(purchase, doc, true);
          
          } finally { doc.close(); }
        } else {
          // メイン領域が画像の注文は、全て横で固定
          doc = new Document(PageSize.A5.rotate());
          try {
          // Pdfの初期化
          this.initPdf();
          
          // ************メイン画像をはめ込む**********************
          Image msgImage = Image.getInstance(purchase.getMainImage());
          msgImage.scaleToFit(mm2pt(180), mm2pt(127));
          msgImage.setAbsolutePosition((mm2pt(14) + ((mm2pt(180) - msgImage.getPlainWidth())/2) + 1), mm2pt(14));
          doc.add(msgImage);
          
          // 20130722 : kawasaki : [1822]バーコードキッティングの為の改修
          this.addDeliveryBarcodeImage(purchase, doc, true);
          
          } finally { doc.close(); }
        }
        reader = new PdfReader(out.toByteArray());
        return reader;
    }
    
    // 電報メイン領域（動画）を出力
    public PdfReader getMovieMainPdfReader(Purchase purchase) throws DocumentException, IOException {
      // メイン領域が動画の注文は、全て横で固定
      doc = new Document(PageSize.A5.rotate());
      try {
        // Pdfの初期化
        this.initPdf();
        
        // 動画電報表示
        BaseFont bf = BaseFont.createFont("A-OTF-RyuminPr6N-ExBold.ttf" , BaseFont.IDENTITY_H, false); // 明朝
        font = new Font(bf, 20);
        ct.setSimpleColumn(mm2pt(10), mm2pt(140), mm2pt(200), mm2pt(20), 20, Element.ALIGN_TOP);
        par = new Paragraph();
        par.add(new Phrase(0, "動画電報", font));
        ct.addText(par);
        ct.go();
        
        // 20130722 : kawasaki : [1822]バーコードキッティングの為の改修
        this.addDeliveryBarcodeImage(purchase, doc, true);
        
      } finally { doc.close(); }
      
      reader = new PdfReader(out.toByteArray());
      return reader;
    }

    // エンディング電報受取人メイン領域を出力
    public PdfReader getEndingMainPdfReader(Purchase purchase) throws DocumentException, IOException {
        // Pdfの初期化
        doc = new Document(PageSize.A5.rotate());
        try {
        this.initPdf();
        
        // ベースフォントの初期化
        BaseFont bfs[] = new BaseFont[3];
        bfs[0] = BaseFont.createFont("A-OTF-RyuminPr6N-ExBold.ttf" , BaseFont.IDENTITY_H, false); // 明朝
        bfs[1] = BaseFont.createFont("A-OTF-ShinGoPr6N-Regular.otf", BaseFont.IDENTITY_H, false); // ゴシック
        bfs[2] = BaseFont.createFont("A-OTF-KakuGyoStd-Medium.otf" , BaseFont.IDENTITY_H, false); // 毛筆
        // 受取人
        font = new Font(bfs[purchase.recipientFontId - 1], purchase.recipientFontSize);
        ct.setSimpleColumn(mm2pt(key2int("recipient.position.x1")), mm2pt(key2int("recipient.position.y1")), mm2pt(key2int("recipient.position.x2")), mm2pt(key2int("recipient.position.y2")), 14, Element.ALIGN_TOP);
        par = new Paragraph();
        buf = convertHyphen(purchase.recipient);
        // 肩書の追加
        if(purchase.recipientTitle > 0) {
            buf = buf + "　" + titleAry[purchase.recipientTitle];
        }
        par.add(new Phrase(buf, font));
        ct.addText(par);
        ct.go();
        // recipient2は存在する場合のみ追加
        if(purchase.recipient2 != null && purchase.recipient2.length() > 0) {
            ct.setSimpleColumn(mm2pt(key2int("recipient2.position.x1")), mm2pt(key2int("recipient2.position.y1")), mm2pt(key2int("recipient2.position.x2")), mm2pt(key2int("recipient2.position.y2")), 14, Element.ALIGN_TOP);
            par = new Paragraph();
            buf = convertHyphen(purchase.recipient2);
            // 肩書の追加
            if(purchase.recipient2Title > 0) {
                buf = buf + "　" + titleAry[purchase.recipient2Title];
            }
            par.add(new Phrase(buf, font));
            ct.addText(par);
            ct.go();
        }
        cb.stroke();
        
        // 差出人項目
        font = new Font(bfs[purchase.senderFontId - 1], purchase.senderFontSize);
        // 差出人項目は、それぞれ存在する時のみ追加
        if(purchase.sender1 != null) {
            ct.setSimpleColumn(mm2pt(key2int("sender.position.x1")), mm2pt(key2int("sender.position.y1")), mm2pt(key2int("sender.position.x2")), mm2pt(key2int("sender.position.y2")), 14, Element.ALIGN_TOP);
            par = new Paragraph();
            par.add(new Phrase(0, convertHyphen(purchase.sender1), font));
            ct.addText(par);
            ct.go();
        }
        if(purchase.sender2 != null) {
            ct.setSimpleColumn(mm2pt(key2int("sender2.position.x1")), mm2pt(key2int("sender2.position.y1")), mm2pt(key2int("sender2.position.x2")), mm2pt(key2int("sender2.position.y2")), 14, Element.ALIGN_TOP);
            par = new Paragraph();
            par.add(new Phrase(0, convertHyphen(purchase.sender2), font));
            ct.addText(par);
            ct.go();
        }
        if(purchase.sender3 != null) {
            ct.setSimpleColumn(mm2pt(key2int("sender3.position.x1")), mm2pt(key2int("sender3.position.y1")), mm2pt(key2int("sender3.position.x2")), mm2pt(key2int("sender3.position.y2")), 14, Element.ALIGN_TOP);
            par = new Paragraph();
            par.add(new Phrase(0, convertHyphen(purchase.sender3), font));
            ct.addText(par);
            ct.go();
        }
        if(purchase.sender4 != null) {
            ct.setSimpleColumn(mm2pt(key2int("sender4.position.x1")), mm2pt(key2int("sender4.position.y1")), mm2pt(key2int("sender4.position.x2")), mm2pt(key2int("sender4.position.y2")), 14, Element.ALIGN_TOP);
            par = new Paragraph();
            par.add(new Phrase(0, convertHyphen(purchase.sender4), font));
            ct.addText(par);
            ct.go();
        }
        if(purchase.sender5 != null) {
            ct.setSimpleColumn(mm2pt(key2int("sender5.position.x1")), mm2pt(key2int("sender5.position.y1")), mm2pt(key2int("sender5.position.x2")), mm2pt(key2int("sender5.position.y2")), 14, Element.ALIGN_TOP);
            par = new Paragraph();
            par.add(new Phrase(0, convertHyphen(purchase.sender5), font));
            ct.addText(par);
            ct.go();
        }
        if(purchase.sender6 != null) {
            ct.setSimpleColumn(mm2pt(key2int("sender6.position.x1")), mm2pt(key2int("sender6.position.y1")), mm2pt(key2int("sender6.position.x2")), mm2pt(key2int("sender6.position.y2")), 14, Element.ALIGN_TOP);
            par = new Paragraph();
            par.add(new Phrase(0, convertHyphen(purchase.sender6), font));
            ct.addText(par);
            ct.go();
        }
        if(purchase.sender7 != null) {
            ct.setSimpleColumn(mm2pt(key2int("sender7.position.x1")), mm2pt(key2int("sender7.position.y1")), mm2pt(key2int("sender7.position.x2")), mm2pt(key2int("sender7.position.y2")), 14, Element.ALIGN_TOP);
            par = new Paragraph();
            par.add(new Phrase(0, convertHyphen(purchase.sender7), font));
            ct.addText(par);
            ct.go();
        }
        cb.stroke();

        // 注釈文を代入
        font = new Font(bfs[1], 12);
        ct.setSimpleColumn(mm2pt(key2int("annotation.position.x1")), mm2pt(key2int("annotation.position.y1")), mm2pt(key2int("annotation.position.x2")), mm2pt(key2int("annotation.position.y2")), 14, Element.ALIGN_TOP);
        par = new Paragraph();
        par.add(new Phrase(0, "こちらの商品は差出人様から生前に受付を賜り", font));
        par.add(Chunk.NEWLINE);
        par.add(new Phrase(0, "配送させていただきました。", font));
        par.add(Chunk.NEWLINE);
        par.add(new Phrase(0, "本商品には遺言書のような法的効力は一切ございません。", font));
        ct.addText(par);
        ct.go();
        cb.stroke();
        
        // 注文番号がない場合は、非表示
        if (purchase.id != 0) {
            // ページ番号
            font = new Font(bfs[1], 16);
            ct.setSimpleColumn(mm2pt(key2int("page_number.position.x1")), mm2pt(key2int("page_number.position.y1")), mm2pt(key2int("page_number.position.x2")), mm2pt(key2int("page_number.position.y2")), 14, Element.ALIGN_TOP);
            par = new Paragraph();
            par.add(new Phrase(0, "" + 1 + "/" + (purchase.getEndingSubscriptionPageCount() + 1), font));
            ct.addText(par);
            ct.go();
            cb.stroke();
        }
        
        // ユーザー画像を追加
        this.addPurchaseImage(purchase, doc, cb);

        // 20130722 : kawasaki : [1822]バーコードキッティングの為の改修
        this.addDeliveryBarcodeImage(purchase, doc, true);
        
        } finally { doc.close(); }
        reader = new PdfReader(out.toByteArray());
        return reader;
    }
    
    // エンディング電報本文メイン領域を出力
    public PdfReader getEndingSubscriptionPdfReader(Purchase purchase, int page_number) throws DocumentException, IOException {
        // ページ数を超えていた場合、nullを返して処理を抜ける。
        if (purchase.getEndingSubscriptionPageCount() < page_number) {
            return null;
        }
        
        // Pdfの初期化
        doc = new Document(PageSize.A5.rotate());
        try {
        this.initPdf();
        
        // ベースフォントの初期化
        BaseFont bfs[] = new BaseFont[3];
        bfs[0] = BaseFont.createFont("A-OTF-RyuminPr6N-ExBold.ttf" , BaseFont.IDENTITY_H, false); // 明朝
        bfs[1] = BaseFont.createFont("A-OTF-ShinGoPr6N-Regular.otf", BaseFont.IDENTITY_H, false); // ゴシック
        bfs[2] = BaseFont.createFont("A-OTF-KakuGyoStd-Medium.otf" , BaseFont.IDENTITY_H, false); // 毛筆

        // 本文
        font = new Font(bfs[purchase.subscriptionFontId - 1], purchase.subscriptionFontSize);
        ct.setSimpleColumn(mm2pt(key2int("subscription.position.x1")), mm2pt(key2int("subscription.position.y1")), mm2pt(key2int("subscription.position.x2")), mm2pt(key2int("subscription.position.y2")), 25, Element.ALIGN_TOP);
        par = new Paragraph(); 
        par.add(new Phrase(0, convertHyphen(purchase.getEndingSubscription(page_number)), font));
        ct.addText(par);
        ct.go();
        cb.stroke();
        
        // 注文番号がない場合は、非表示
        if (purchase.id != 0) {
            // ページ番号
            font = new Font(bfs[1], 16);
            ct.setSimpleColumn(mm2pt(key2int("page_number.position.x1")), mm2pt(key2int("page_number.position.y1")), mm2pt(key2int("page_number.position.x2")), mm2pt(key2int("page_number.position.y2")), 14, Element.ALIGN_TOP);
            par = new Paragraph();
            par.add(new Phrase(0, "" + (page_number + 1) + "/" + (purchase.getEndingSubscriptionPageCount() + 1), font));
            ct.addText(par);
            ct.go();
            cb.stroke();
        }
        
        // 20130722 : kawasaki : [1822]バーコードキッティングの為の改修
        this.addDeliveryBarcodeImage(purchase, doc, true);
        
        } finally { doc.close(); }
        reader = new PdfReader(out.toByteArray());
        return reader;
    }
    
    // エンディング電報本文Info領域を出力
    public PdfReader getEndingSubscriptionInfoPdfReader(Purchase purchase, int page_number) throws DocumentException, IOException {
      // ページ数を超えていた場合、nullを返して処理を抜ける。
      if (purchase.getEndingSubscriptionPageCount() < page_number) {
          return null;
      }
      // メイン領域が動画の注文は、全て横で固定
      doc = new Document(PageSize.A5.rotate());
      try {
        // Pdfの初期化
        this.initPdf();
        
        // 動画電報表示
        BaseFont bf = BaseFont.createFont("A-OTF-RyuminPr6N-ExBold.ttf" , BaseFont.IDENTITY_H, false); // 明朝
        font = new Font(bf, 32);
        ct.setSimpleColumn(mm2pt(10), mm2pt(140), mm2pt(200), mm2pt(20), 32, Element.ALIGN_TOP);
        par = new Paragraph();
        par.add(new Phrase(0, "エンディング電報　" + (page_number + 1) + "/" + (purchase.getEndingSubscriptionPageCount() + 1), font));
        par.add(Chunk.NEWLINE);
        par.add(Chunk.NEWLINE);
        par.add(new Phrase(0, "注文No.：" + purchase.id, font));
        ct.addText(par);
        ct.go();
        
      } finally { doc.close(); }
      
      reader = new PdfReader(out.toByteArray());
      return reader;
    }
    
    // 配送情報領域を出力する
    public PdfReader getInfoPdfReader(Purchase purchase) throws DocumentException, IOException {
        // 20121207 : kawasaki :  
        int padding_x = 4;  
        doc = new Document(PageSize.A5.rotate());
        try {
        // Pdfの初期化
        this.initPdf();
        int kddi_offset_x = 0;
        // 201313 : kawasaki : KDDI電報対応
        if (purchase.siteTypeId == 6) {
          kddi_offset_x = - 22;
        }
        
        BaseFont bf = BaseFont.createFont("lib/ipag.otf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        /* 切り取り線上部 */
        // お届け先情報見出し
        font = new Font(bf, 10);
        ct.setSimpleColumn(mm2pt(4) + padding_x, mm2pt(145) - 10, mm2pt(120) + padding_x, mm2pt(145), 10, Element.ALIGN_TOP);
        par = new Paragraph();
        par.add(new Phrase(0, "お届け先                       ＜荷物領収原票（着店用）＞", font));
        ct.addText(par);
        ct.go();
        cb.stroke();
        
        // お届け先情報項目名
        font = new Font(bf, 8);
        ct.setSimpleColumn(mm2pt(4) + padding_x, mm2pt(144) - 162, mm2pt(24) + padding_x, mm2pt(144) - 10, 11, Element.ALIGN_TOP);
        par = new Paragraph();
        par.add(new Phrase(0, "郵便番号：", font));
        par.add(Chunk.NEWLINE);
        par.add(new Phrase(0, "住所：", font));
        par.add(Chunk.NEWLINE);
        par.add(Chunk.NEWLINE);
        par.add(new Phrase(0, "届先名：", font));
        par.add(Chunk.NEWLINE);
        par.add(new Phrase(0, "TEL：", font));
        par.add(Chunk.NEWLINE);        
        // KDDI電報専用
        if (purchase.siteTypeId == 6) {
          par.add(new Phrase(0, "受取人: ", font));
          par.add(Chunk.NEWLINE);
        } else {
          par.add(new Phrase(0, "お届け先氏名: ", font));
          par.add(Chunk.NEWLINE);
          par.add(new Phrase(0, "受取人：", font));
        }
        par.add(Chunk.NEWLINE);
        par.add(Chunk.NEWLINE);
        par.add(new Phrase(0, "お届希望日時: ", font));
        par.add(Chunk.NEWLINE);
        par.add(Chunk.NEWLINE);
        par.add(new Phrase(0, "式典日時：", font));
        par.add(Chunk.NEWLINE);
        par.add(new Phrase(0, "引受日時：", font));
        par.add(Chunk.NEWLINE);
        ct.addText(par);
        ct.go();
        cb.stroke();
        
        // お届け先情報値
        font = new Font(bf, 8);
        Font address_font = new Font(bf,9);
        Font smallFont = new Font(bf, 6);
        
        ct.setSimpleColumn(mm2pt(24), mm2pt(144) - 162, mm2pt(120), mm2pt(144) - 10, 11, Element.ALIGN_TOP);
        par = new Paragraph();
        // 郵便番号
        par.add(new Phrase(0, purchase.postalCode, font));
        par.add(Chunk.NEWLINE);

        // 201313 : kawasaki : KDDI電報対応
        if (purchase.siteTypeId == 6) {
            address_font = new Font(bf,8);
            ct.addText(par);
            ct.go();
            cb.stroke();
            
            ct.setSimpleColumn(mm2pt(24) + kddi_offset_x, mm2pt(144) - 162, mm2pt(120), mm2pt(144) - 21, 11, Element.ALIGN_TOP);
            par = new Paragraph();
        }
        
        // 住所1
        if(purchase.stateName != null && purchase.stateName.length() > 0 && purchase.address != null && purchase.address.length() > 0) {
            buf = purchase.stateName + purchase.address;
            par.add(new Phrase(0, buf, address_font));
        }
        par.add(Chunk.NEWLINE);
        // 住所2
        if(purchase.addressMore != null && purchase.addressMore.length() > 0) {
            par.add(new Phrase(0, purchase.addressMore, address_font));
        }
        par.add(Chunk.NEWLINE);
        // 届け先名
        Font fontArriveName = new Font(bf, 8);
        fontArriveName.setColor(new BaseColor(255, 0, 0)); //赤
        par.add(new Phrase(0, purchase.arriveName, fontArriveName));
        par.add(Chunk.NEWLINE);
        
        // 20130313 : kawasaki : KDDI電報対応
        if (purchase.siteTypeId == 6) {
            address_font = new Font(bf,8);
            ct.addText(par);
            ct.go();
            cb.stroke();
            
            ct.setSimpleColumn(mm2pt(24), mm2pt(144) - 162, mm2pt(120), mm2pt(144) - 54, 11, Element.ALIGN_TOP);
            par = new Paragraph();
        }
        // TEL
        String purchase_tel = purchase.tel;
        if ((purchase.siteTypeId == 6) && purchase_tel.equals("0000-0000-0000")) {
            purchase_tel = "";
        }
        par.add(new Phrase(0, purchase_tel, font));
        par.add(Chunk.NEWLINE);
        // お届け先氏名
        buf = purchase.recipientName;
        // 肩書の追加
        if(purchase.recipientNameTitle > 0) {
            buf = buf + "　" + titleAry[purchase.recipientNameTitle];
        }
        // 20121015 : kawasaki : ALSOK対応
        // 20130313 : kawasaki : KDDI対応
        if (((purchase.siteTypeId == 4) && (30 < purchase.recipientName.length())) || (purchase.siteTypeId == 6))  {
            par.add(new Phrase(0, buf, smallFont));
        } else {
            par.add(new Phrase(0, buf, font));
        }
        par.add(Chunk.NEWLINE);
        
        // 宛名1
        buf = purchase.recipient;
        // 肩書の追加
        if(purchase.recipientTitle > 0) {
            buf = buf + "　" + titleAry[purchase.recipientTitle];
        }
        // 宛名2
        par.add(new Phrase(0, buf, font));
        par.add(Chunk.NEWLINE);
        if(purchase.recipient2 != null && purchase.recipient2.length() > 0) {
            buf = purchase.recipient2;
            // 肩書の追加
            if(purchase.recipient2Title > 0) {
                buf = buf + "　" + titleAry[purchase.recipient2Title];
            }
            par.add(new Phrase(0, buf, font));
        }
        par.add(Chunk.NEWLINE);
        par.add(Chunk.NEWLINE);
        par.add(Chunk.NEWLINE);
        // 式典日時
        if(purchase.celemonyAt != null) {
            //日付と時刻の処理を分ける2008/06/17
            par.add(new Phrase(0, date_format.format(purchase.celemonyAt)+" "+formatDate2Term_cemeonyAt(purchase.celemonyAt), font));
        }
        par.add(Chunk.NEWLINE);
        // 注文日
        if(purchase.createdAt != null) {
            par.add(new Phrase(0, datetime_format.format(purchase.createdAt), font));
        }
        par.add(Chunk.NEWLINE);
        ct.addText(par);
        ct.go();
        cb.stroke();

        // 指定日
        if(purchase.wishedAt != null) {
            Font font_wishedAt = new Font(bf, 13);
            font_wishedAt.setColor(new BaseColor(255, 0, 0));
            ct.setSimpleColumn(mm2pt(24), mm2pt(84), mm2pt(130), mm2pt(84) + 65, 11, Element.ALIGN_TOP);
            par = new Paragraph();
            // 2010/09/17 modified by Shibata 文字色を赤にする処理を採用
            par.add(new Phrase(0, date_format.format(purchase.wishedAt)+" "+formatDate2Term_wishedAt(purchase.wishedAt, purchase.speedFlag, true), font_wishedAt)); 
            ct.addText(par);
            ct.go();
            cb.stroke();
        }
        
        // でんぽっぽ以外
        if (purchase.siteTypeId != 6) {
          // 注文No
          ct.setSimpleColumn(mm2pt(56), mm2pt(84), mm2pt(114), mm2pt(84) + 50, 11, Element.ALIGN_RIGHT);
          par = new Paragraph();
          par.add(new Phrase(0, "注文No: " + String.valueOf(purchase.id), font));
          ct.addText(par);
          ct.go();
          cb.stroke();
        }
        
        // サイト注文番号
        // 201304 : kawasaki : 味のふる里便対応
        if ((purchase.siteTypeId == 5) || (purchase.siteTypeId == 6)) {
          ct.setSimpleColumn(mm2pt(56), mm2pt(84), mm2pt(114), mm2pt(84) + 39, 11, Element.ALIGN_RIGHT);
          par = new Paragraph();
          par.add(new Phrase(0, "サイト注文番号: " + purchase.orderId, font));
          par.add(Chunk.NEWLINE);
          ct.addText(par);
          ct.go();
          cb.stroke();
        }
        
        /* 090628 イオマーク追加 */
        if(purchase.isEo()) {
            font = new Font(bf, 18);
            font.setColor(new BaseColor(0, 0, 255));    //青
            ct.setSimpleColumn(mm2pt(100), mm2pt(84), mm2pt(120), mm2pt(84) + 18, 11, Element.ALIGN_TOP);
            par = new Paragraph();
            par.add(new Phrase(0, "イオ", font));
            ct.addText(par);
            ct.go();
            cb.stroke();
        }else{
            // 20100502追加
            //それ以外は、e-denpoを表示
            font = new Font(bf, 18);
            font.setColor(new BaseColor(190, 0, 0));    //ちょっと暗めの赤
            ct.setSimpleColumn(mm2pt(80), mm2pt(84), mm2pt(120), mm2pt(84) + 18, 11, Element.ALIGN_TOP);
            par = new Paragraph();
            //20110808 : kawasaki : [213]敬老注文ユーザー処理追加
            if("ajinofurusato".equals(purchase.userLogin)) {
                par.add(new Phrase(0, "味のふる里便", font));
            } else {
                if (purchase.siteTypeId == 4) {
                    // 20121015 : kawasaki : ALSOK便対応
                    par.add(new Phrase(0, "ALSOK電報", font));
                } else if (purchase.siteTypeId == 5) {
                    // 20120920 : kawasaki : 味のふる里便対応
                    par.add(new Phrase(0, "味のふる里便", font));
                } else if (purchase.siteTypeId == 6) {
                    // 20130313 : kawasaki : KDDI電報対応
                    par.add(new Phrase(0, "でんぽっぽ", font));
                } else if (purchase.siteTypeId == 7) {
                    par.add(new Phrase(0, "ｴﾝﾃﾞｨﾝｸﾞ電報", font));
                } else {
                    par.add(new Phrase(0, "e-denpo", font));
                }
            }
            ct.addText(par);
            ct.go();
            cb.stroke();
        }
        
        // 20120920 : kawasaki : 味のふる里便対応
        if (purchase.siteTypeId == 5) {
          font = new Font(bf, 10);
          ct.setSimpleColumn(mm2pt(10), mm2pt(80), mm2pt(120), mm2pt(80) + 18);
          par = new Paragraph();
          par.add(new Phrase(0, "【味のふる里便はバーコード非表示です】", font));
          ct.addText(par);
          ct.go();
          cb.stroke();
        } else {
            // バーコード or QRコード印刷を表示
            this.addCodeImage(purchase, doc, cb);
        }
        
        // 差出人名見出し
        font = new Font(bf, 10);
        ct.setSimpleColumn(mm2pt(121) + kddi_offset_x, mm2pt(145) - 10, mm2pt(210), mm2pt(145), 10, Element.ALIGN_TOP);
        par = new Paragraph();
        par.add(new Phrase(0, "差出人名：", font));
        ct.addText(par);
        ct.go();
        cb.stroke();
        
        // 差出人項目
        font = new Font(bf, 7);
        Font readFont = new Font(bf, 6);
        ct.setSimpleColumn(mm2pt(121) + kddi_offset_x, mm2pt(144) - 135, mm2pt(210), mm2pt(144) - 10, 8, Element.ALIGN_TOP);
        par = new Paragraph();
        if (purchase.siteTypeId != 6) {
            if(purchase.sender1Read != null) {
                par.add(new Phrase(0, purchase.sender1Read, readFont));
            }
            par.add(Chunk.NEWLINE);
        }
        if(purchase.sender1 != null) {
            // 20130313 : kawasaki : KDDI電報対応
            if (purchase.siteTypeId == 6) {
                if(purchase.sender1.length() < 40) {
                    par.add(new Phrase(0, purchase.sender1.substring(0,purchase.sender1.length()), smallFont));
                    par.add(Chunk.NEWLINE);
                } else {
                    System.out.println(purchase.sender1.length());
                    par.add(new Phrase(0, purchase.sender1.substring(0,40), smallFont));
                    par.add(Chunk.NEWLINE);
                    par.add(new Phrase(0, purchase.sender1.substring(40), smallFont));
                }
            } else {
              par.add(new Phrase(0, purchase.sender1, font));
            }
        }
        par.add(Chunk.NEWLINE);
        
        if (purchase.siteTypeId != 6) {
            if(purchase.sender2Read != null) {
                par.add(new Phrase(0, purchase.sender2Read, readFont));
            }
            par.add(Chunk.NEWLINE);
        }
        if(purchase.sender2 != null) {
            par.add(new Phrase(0, purchase.sender2, font));
        }
        par.add(Chunk.NEWLINE);
        
        if (purchase.siteTypeId != 6) {
            if(purchase.sender3Read != null) {
                par.add(new Phrase(0, purchase.sender3Read, readFont));
            }
            par.add(Chunk.NEWLINE);
        }
        if(purchase.sender3 != null) {
            par.add(new Phrase(0, purchase.sender3, font));
        }
        par.add(Chunk.NEWLINE);
        
        if (purchase.siteTypeId != 6) {
            if(purchase.sender4Read != null) {
                par.add(new Phrase(0, purchase.sender4Read, readFont));
            }
            par.add(Chunk.NEWLINE);
        }
        if(purchase.sender4 != null) {
            par.add(new Phrase(0, purchase.sender4, font));
        }
        par.add(Chunk.NEWLINE);
        
        if (purchase.siteTypeId != 6) {
            if(purchase.sender5Read != null) {
                par.add(new Phrase(0, purchase.sender5Read, readFont));
            }
            par.add(Chunk.NEWLINE);
        }
        if(purchase.sender5 != null) {
            par.add(new Phrase(0, purchase.sender5, font));
        }
        par.add(Chunk.NEWLINE);
        
        if (purchase.siteTypeId != 6) {
            if(purchase.sender6Read != null) {
                par.add(new Phrase(0, purchase.sender6Read, readFont));
            }
            par.add(Chunk.NEWLINE);
        }
        if(purchase.sender6 != null) {
            par.add(new Phrase(0, purchase.sender6, font));
        }
        par.add(Chunk.NEWLINE);
        
        if (purchase.siteTypeId != 6) {
            if(purchase.sender7Read != null) {
                par.add(new Phrase(0, purchase.sender7Read, readFont));
            }
            par.add(Chunk.NEWLINE);
        }
        if(purchase.sender7 != null) {
            par.add(new Phrase(0, purchase.sender7, font));
        }
        ct.addText(par);
        ct.go();
        cb.stroke();
        
        //*************20100502 福山通運用サイン領域*********************
        // サイン領域画像用イメージ呼び出し
        String fName;
        if (purchase.siteTypeId == 6) {
            fName = "info_image2.bmp";
        } else {
            fName = "info_image.bmp";
        }
        FileInputStream fInput = null;
        byte[] fByte = null;
        try {
            fInput = new FileInputStream(fName);
            fByte = new byte[(int)new File(fName).length()];
            fInput.read(fByte);
        }catch(Exception e) {
            e.printStackTrace();
        }finally {
            try{
                if(fInput != null) {
                    fInput.close();
                }
            }catch(Exception e) {
                e.printStackTrace();
            }
        }

        if (purchase.siteTypeId == 6) {
            // サイン領域画像描画
            if(fByte != null) {
                Image infoImage = Image.getInstance(fByte);
                infoImage.scaleToFit(mm2pt(21), mm2pt(30));
                infoImage.setAbsolutePosition(mm2pt(177) - padding_x, mm2pt(84));
                doc.add(infoImage);
            }
            ct.go();
            cb.stroke();
            
            // 見出し
            font = new Font(bf, 10);
            ct.setSimpleColumn(mm2pt(121), mm2pt(102) + 33, mm2pt(180), mm2pt(102) + 43);
            par = new Paragraph();
            par.add(new Phrase(0, "品番：", font));
            ct.addText(par);
            ct.go();
            cb.stroke();
            
            // 本体
            font = new Font(bf, 18);
            // 祝電なら赤文字
            if(purchase.telegramTypeId == 1) {
                font.setColor(new BaseColor(255, 0, 0));
            }
            ct.setSimpleColumn(mm2pt(121), mm2pt(102) - 72, mm2pt(175), mm2pt(102) + 36, 18, Element.ALIGN_TOP);
        } else {
            // サイン領域画像描画
            if(fByte != null) {
                Image infoImage = Image.getInstance(fByte);
                infoImage.scaleToFit(mm2pt(35), mm2pt(14));
                infoImage.setAbsolutePosition(mm2pt(169) - padding_x, mm2pt(84));
                doc.add(infoImage);
            }
            ct.go();
            cb.stroke();
            
            // 見出し
            font = new Font(bf, 10);
            ct.setSimpleColumn(mm2pt(121), mm2pt(82) + 36, mm2pt(180), mm2pt(82) + 46);
            par = new Paragraph();
            par.add(new Phrase(0, "品番：", font));
            ct.addText(par);
            ct.go();
            cb.stroke();
            
            // 本体
            font = new Font(bf, 18);
            // 祝電なら赤文字
            if(purchase.telegramTypeId == 1) {
                font.setColor(new BaseColor(255, 0, 0));
            }
            ct.setSimpleColumn(mm2pt(121), mm2pt(82) + 0, mm2pt(146), mm2pt(82) + 36, 18, Element.ALIGN_TOP);
        }
        par = new Paragraph();
        par.add(new Phrase(0, purchase.itemCode, font));
        ct.addText(par);
        ct.go();
        cb.stroke();
        
        /* 切り取り線下部 */
        // お届け先情報見出し
        font = new Font(bf, 10);
        ct.setSimpleColumn(mm2pt(4) + padding_x, mm2pt(71) - 13, mm2pt(120) + padding_x, mm2pt(71), 13, Element.ALIGN_TOP);
        par = new Paragraph();
        //par.add(new Phrase(0, "お届け先：", font));
        par.add(new Phrase(0, "お届け先                       ＜送り状（受取人控）＞", font));
        ct.addText(par);
        ct.go();
        cb.stroke();
        // お届け先情報項目名
        font = new Font(bf, 8);
        ct.setSimpleColumn(mm2pt(4) + padding_x, mm2pt(70) - 162, mm2pt(24) + padding_x, mm2pt(70) - 10, 11, Element.ALIGN_TOP);
        par = new Paragraph();
        par.add(new Phrase(0, "郵便番号：", font));
        par.add(Chunk.NEWLINE);
        par.add(new Phrase(0, "住所：", font));
        par.add(Chunk.NEWLINE);
        par.add(Chunk.NEWLINE);
        par.add(new Phrase(0, "届先名：", font));
        par.add(Chunk.NEWLINE);
        par.add(new Phrase(0, "TEL：", font));
        par.add(Chunk.NEWLINE);
        // KDDI電報専用
        if (purchase.siteTypeId == 6) {
          par.add(new Phrase(0, "受取人: ", font));
          par.add(Chunk.NEWLINE);
        } else {
          par.add(new Phrase(0, "お届け先氏名: ", font));
          par.add(Chunk.NEWLINE);
          par.add(new Phrase(0, "受取人：", font));
        }
        par.add(Chunk.NEWLINE);
        par.add(Chunk.NEWLINE);
        par.add(new Phrase(0, "お届希望日時: ", font));
        par.add(Chunk.NEWLINE);
        par.add(new Phrase(0, "式典日時：", font));
        par.add(Chunk.NEWLINE);
        par.add(new Phrase(0, "引受日時：", font));
        par.add(Chunk.NEWLINE);
        if (purchase.siteTypeId != 6) {
        par.add(new Phrase(0, "注文No.：", font));
        par.add(Chunk.NEWLINE);
        }
        ct.addText(par);
        ct.go();
        cb.stroke();
        // お届け先情報値
        font = new Font(bf, 8);
        ct.setSimpleColumn(mm2pt(24), mm2pt(70) - 162, mm2pt(120), mm2pt(70) - 10, 11, Element.ALIGN_TOP);
        par = new Paragraph();
        par.add(new Phrase(0, purchase.postalCode, font));
        par.add(Chunk.NEWLINE);

        // 201313 : kawasaki : KDDI電報対応
        if (purchase.siteTypeId == 6) {
            address_font = new Font(bf,8);
            ct.addText(par);
            ct.go();
            cb.stroke();

            ct.setSimpleColumn(mm2pt(24) + kddi_offset_x, mm2pt(70) - 162, mm2pt(120), mm2pt(70) - 21, 11, Element.ALIGN_TOP);
            par = new Paragraph();
        }        
        if(purchase.stateName != null && purchase.stateName.length() > 0 && purchase.address != null && purchase.address.length() > 0) {
            buf = purchase.stateName + purchase.address;
            par.add(new Phrase(0, buf, address_font));
        }
        par.add(Chunk.NEWLINE);

        if(purchase.addressMore != null && purchase.addressMore.length() > 0) {
            par.add(new Phrase(0, purchase.addressMore, address_font));
        }
        par.add(Chunk.NEWLINE);
        
        par.add(new Phrase(0, purchase.arriveName, font));
        par.add(Chunk.NEWLINE);

        // 201313 : kawasaki : KDDI電報対応
        if (purchase.siteTypeId == 6) {
            ct.addText(par);
            ct.go();
            cb.stroke();

            ct.setSimpleColumn(mm2pt(24), mm2pt(70) - 162, mm2pt(120), mm2pt(70) - 54, 11, Element.ALIGN_TOP);
            par = new Paragraph();
        }
        
        par.add(new Phrase(0, purchase_tel, font));
        par.add(Chunk.NEWLINE);
        
        // お届け先氏名
        buf = purchase.recipientName;
        // 肩書の追加
        if(purchase.recipientNameTitle > 0) {
            buf = buf + "　" + titleAry[purchase.recipientNameTitle];
        }
        // 20121015 : kawasaki : ALSOK対応
        // 20130313 : kawasaki : KDDI対応
        if (((purchase.siteTypeId == 4) && (30 < purchase.recipientName.length())) || (purchase.siteTypeId == 6))  {
            par.add(new Phrase(0, buf, smallFont));
        } else {
            par.add(new Phrase(0, buf, font));
        }
        par.add(Chunk.NEWLINE);

        // 宛名1,2
        buf = purchase.recipient;
        // 肩書の追加
        if(purchase.recipientTitle > 0) {
            buf = buf + "　" + titleAry[purchase.recipientTitle];
        }
        par.add(new Phrase(0, buf, font));
        par.add(Chunk.NEWLINE);
        if(purchase.recipient2 != null && purchase.recipient2.length() > 0) {
            buf = purchase.recipient2;
            // 肩書の追加
            if(purchase.recipient2Title > 0) {
                buf = buf + "　" + titleAry[purchase.recipient2Title];
            }
            par.add(new Phrase(0, buf, font));
        }
        par.add(Chunk.NEWLINE);
        
        if(purchase.wishedAt != null) {
            par.add(new Phrase(0, date_format.format(purchase.wishedAt)+" "+formatDate2Term_wishedAt(purchase.wishedAt, purchase.speedFlag, false), font));
        }
        par.add(Chunk.NEWLINE);
        
        if(purchase.celemonyAt != null) {
            //日付と時刻・時間帯を連結して書き込む2008/06/17
            par.add(new Phrase(0, date_format.format(purchase.celemonyAt)+" "+formatDate2Term_cemeonyAt(purchase.celemonyAt), font));
        }
        par.add(Chunk.NEWLINE);
        
        if(purchase.createdAt != null) {
            par.add(new Phrase(0, date_format.format(purchase.createdAt), font));
        }
        par.add(Chunk.NEWLINE);

        if (purchase.siteTypeId != 6) {
            par.add(new Phrase(0, String.valueOf(purchase.id), font));
            par.add(Chunk.NEWLINE);
        }
        ct.addText(par);
        ct.go();
        cb.stroke();
        
        // サイト注文番号
        // 201304 : kawasaki : 味のふる里便対応
        if (purchase.siteTypeId == 5) {
          ct.setSimpleColumn(mm2pt(50), mm2pt(10), mm2pt(120), mm2pt(10) + 40, 11, Element.ALIGN_TOP);
          par = new Paragraph();
          par.add(new Phrase(0, "サイト注文番号: " + purchase.orderId, font));
          par.add(Chunk.NEWLINE);
          ct.addText(par);
          ct.go();
          cb.stroke();
        }
        
        // 20130828 : kawasaki : [1910]電報PDFレイアウト修正
        if (purchase.siteTypeId == 6) {
            //ct.setSimpleColumn(mm2pt(50), mm2pt(10), mm2pt(120), mm2pt(10) + 40, 11, Element.ALIGN_TOP);
            ct.setSimpleColumn(mm2pt(56) + padding_x, mm2pt(10) + 32, mm2pt(114), mm2pt(10) + 72, 11, Element.ALIGN_RIGHT);
            par = new Paragraph();
            //par.add(new Phrase(0, "　　　　　　　注文No.：" + purchase.id, font));
            par.add(Chunk.NEWLINE);
            par.add(new Phrase(0, "サイト注文番号: " + purchase.orderId, font));
            par.add(Chunk.NEWLINE);
            ct.addText(par);
            ct.go();
            cb.stroke();
            
            try {
                if (purchase.getItemImage() != null) {
                    Image itemImage = purchase.getItemImage();
                    itemImage.scaleToFit(mm2pt(20), mm2pt(20));
                    itemImage.setAbsolutePosition(mm2pt(80), mm2pt(8));
                    doc.add(itemImage);
                }
            } catch (Exception e) {
            }
        }
        
        // 差出人名見出し
        font = new Font(bf, 10);
        ct.setSimpleColumn(mm2pt(121) + kddi_offset_x, mm2pt(71) - 13, mm2pt(210), mm2pt(71), 13, Element.ALIGN_TOP);
        par = new Paragraph();
        par.add(new Phrase(0, "差出人名：", font));
        ct.addText(par);
        ct.go();
        cb.stroke();
        // 差出人項目
        font = new Font(bf, 7);
        ct.setSimpleColumn(mm2pt(121) + kddi_offset_x, mm2pt(70) - 135, mm2pt(210), mm2pt(70) - 10, 8, Element.ALIGN_TOP);
        par = new Paragraph();
        if (purchase.siteTypeId != 6) {
            if(purchase.sender1Read != null) {
                par.add(new Phrase(0, purchase.sender1Read, readFont));
            }
            par.add(Chunk.NEWLINE);
        }
        if(purchase.sender1 != null) {
            // 20130313 : kawasaki : KDDI電報対応
            if (purchase.siteTypeId == 6) {
              if(purchase.sender1.length() < 40) {
                  par.add(new Phrase(0, purchase.sender1.substring(0,purchase.sender1.length()), smallFont));
                  par.add(Chunk.NEWLINE);
              } else {
                  System.out.println(purchase.sender1.length());
                  par.add(new Phrase(0, purchase.sender1.substring(0,40), smallFont));
                  par.add(Chunk.NEWLINE);
                  par.add(new Phrase(0, purchase.sender1.substring(40), smallFont));
              }
            } else {
              par.add(new Phrase(0, purchase.sender1, font));
            }
        }
        par.add(Chunk.NEWLINE);

        if (purchase.siteTypeId != 6) {
            if(purchase.sender2Read != null) {
                par.add(new Phrase(0, purchase.sender2Read, readFont));
            }
            par.add(Chunk.NEWLINE);
        }
        if(purchase.sender2 != null) {
            par.add(new Phrase(0, purchase.sender2, font));
        }
        par.add(Chunk.NEWLINE);

        if (purchase.siteTypeId != 6) {
            if(purchase.sender3Read != null) {
                par.add(new Phrase(0, purchase.sender3Read, readFont));
            }
            par.add(Chunk.NEWLINE);
        }
        if(purchase.sender3 != null) {
            par.add(new Phrase(0, purchase.sender3, font));
        }
        par.add(Chunk.NEWLINE);

        if (purchase.siteTypeId != 6) {
            if(purchase.sender4Read != null) {
                par.add(new Phrase(0, purchase.sender4Read, readFont));
            }
            par.add(Chunk.NEWLINE);
        }
        if(purchase.sender4 != null) {
            par.add(new Phrase(0, purchase.sender4, font));
        }
        par.add(Chunk.NEWLINE);

        if (purchase.siteTypeId != 6) {
            if(purchase.sender5Read != null) {
                par.add(new Phrase(0, purchase.sender5Read, readFont));
            }
            par.add(Chunk.NEWLINE);
        }
        if(purchase.sender5 != null) {
            par.add(new Phrase(0, purchase.sender5, font));
        }
        par.add(Chunk.NEWLINE);

        if (purchase.siteTypeId != 6) {
            if(purchase.sender6Read != null) {
                par.add(new Phrase(0, purchase.sender6Read, readFont));
            }
            par.add(Chunk.NEWLINE);
        }
        if(purchase.sender6 != null) {
            par.add(new Phrase(0, purchase.sender6, font));
        }
        par.add(Chunk.NEWLINE);

        if (purchase.siteTypeId != 6) {
            if(purchase.sender7Read != null) {
                par.add(new Phrase(0, purchase.sender7Read, readFont));
            }
            par.add(Chunk.NEWLINE);
        }
        if(purchase.sender7 != null) {
            par.add(new Phrase(0, purchase.sender7, font));
        }
        par.add(Chunk.NEWLINE);
        ct.addText(par);
        ct.go();
        cb.stroke();

        if(purchase.siteTypeId == 6) {
          // 見出し
          font = new Font(bf, 10);
          ct.setSimpleColumn(mm2pt(105), mm2pt(8) + 36, mm2pt(210), mm2pt(8) + 46);
          par = new Paragraph();
          par.add(new Phrase(0, "品名：", font));
          ct.addText(par);
          ct.go();
          cb.stroke();
          
          // 本体
          font = new Font(bf, 8);
          ct.setSimpleColumn(mm2pt(105), mm2pt(8) + 8, mm2pt(210), mm2pt(8) + 36);
          par = new Paragraph();
          par.add(new Phrase(0, purchase.itemName, font));
          ct.addText(par);
          ct.go();
          cb.stroke();
          
        } else {
          // 見出し
          font = new Font(bf, 10);
          ct.setSimpleColumn(mm2pt(121), mm2pt(8) + 36, mm2pt(180), mm2pt(8) + 46);
          par = new Paragraph();
          par.add(new Phrase(0, "品番：", font));
          ct.addText(par);
          ct.go();
          cb.stroke();
          
          // 本体
          font = new Font(bf, 18);
          // 祝電なら赤文字
          if(purchase.telegramTypeId == 1) {
              font.setColor(new BaseColor(255, 0, 0));
          }
          ct.setSimpleColumn(mm2pt(121), mm2pt(8) + 8, mm2pt(180), mm2pt(8) + 26);
          par = new Paragraph();
          par.add(new Phrase(0, purchase.itemCode, font));
          ct.addText(par);
          ct.go();
          cb.stroke();
        }
        // **********品番の右横に、商品画像を表示する***********
        // 20120306 : kawasaki : 注文画像のエラーをスルー
        try {
            if (purchase.getItemImage() != null) {
                Image itemImage = purchase.getItemImage();
                if(purchase.siteTypeId == 6) {
                    itemImage.scaleToFit(mm2pt(35), mm2pt(30));
                    itemImage.setAbsolutePosition(mm2pt(131) - padding_x, mm2pt(76));
                } else {
                    itemImage.scaleToFit(mm2pt(20), mm2pt(20));
                    itemImage.setAbsolutePosition(mm2pt(147) - padding_x, mm2pt(76));
                }
                doc.add(itemImage);
            }
        } catch (Exception e) {
        }
        
        ct.go();
        cb.stroke();
        
        // 20130330 : kawasaki : [1475]配送確認用バーコード画像を追加
        this.addDeliveryBarcodeImage(purchase, doc, false);
        
        if(purchase.siteTypeId == 6) {
          // 備考欄左
          ct.setSimpleColumn(mm2pt(4) + padding_x, mm2pt(8), mm2pt(105), mm2pt(8) + 32, 8, Element.ALIGN_TOP);
          font = new Font(bf,6);
          Font strong_font = new Font(bf,8);
          par = new Paragraph();
          par.add(new Phrase(0, "サービス提供：", font));
          par.add(new Phrase(0, "株式会社KDDIエボルバ　でんぽっぽ", font));
          par.add(Chunk.NEWLINE);
          if((purchase.tel_115Flag != null) && purchase.tel_115Flag) {
            par.add(new Phrase(0, "住所　　　　：愛媛県松山市千舟町4-6-3 アヴァンサ千舟", font));
            par.add(Chunk.NEWLINE);
            par.add(new Phrase(0, "お問合せ先　：0120-907-115", font));
          } else {
            par.add(new Phrase(0, "住所　　　　：東京都新宿区西新宿2-7-1 小田急第一生命ビル 7F", font));
            par.add(Chunk.NEWLINE);
            par.add(new Phrase(0, "お問合せ先　：0120-993-133", font));              
          }
          par.add(Chunk.NEWLINE);
          par.add(new Phrase(0, "WebサイトURL：http://www.denpoppo.com", font));
          par.add(Chunk.NEWLINE);
          ct.addText(par);
          ct.go();
          cb.stroke();
          
          // 備考欄右
          ct.setSimpleColumn(mm2pt(105), mm2pt(8), mm2pt(205), mm2pt(8) + 24, 9, Element.ALIGN_TOP);
          font = new Font(bf,6);
          strong_font = new Font(bf,9);
          par = new Paragraph();
          par.add(new Phrase(0, "【信書便物】", strong_font));
          par.add(Chunk.NEWLINE);
          par.add(new Phrase(0, "※本品は、破損及び変質しやすくなっておりますので、取り扱いには十分ご注意ください。", font));
          par.add(Chunk.NEWLINE);
          ct.addText(par);
          ct.go();
          cb.stroke();
          
        } else {
          // 備考欄左
          ct.setSimpleColumn(mm2pt(5) + padding_x, mm2pt(8), mm2pt(105), mm2pt(8) + 28, 7, Element.ALIGN_TOP);;
          font = new Font(bf, 6);
          par = new Paragraph();
          par.add(new Phrase(0, "＜備考＞", font));
          // 090628 eo 向けかどうかで文章を変更
          String site_name = "";
          if(purchase.isEo()) {
              site_name = "e-denpo for eo";
          } else {
              site_name = "e-denpo";
          }
          
          // 配送種別ごとに備考文を変更
          switch(purchase.memberDeliveryType){
          case 2: // 郵便差出
              par.add(Chunk.NEWLINE);
              par.add(new Phrase(0, "注意事項:本品は破損及び変質しやすくなっておりますので、取り扱いには十分ご注意ください。", font));
              par.add(Chunk.NEWLINE);
              par.add(new Phrase(0, "このお荷物は、差出人様よりご依頼を受けて、当社が差出人となり日本郵便株式会社へ差し出す", font));
              par.add(Chunk.NEWLINE);
              par.add(new Phrase(0, "" + site_name + "郵便差出代行サービスにてお届けいたしました。", font));
            break;
          case 3: // 差出代行
              par.add(Chunk.NEWLINE);
              par.add(new Phrase(0, "注意事項:本品は破損及び変質しやすくなっておりますので、取り扱いには十分ご注意ください。", font));
              par.add(Chunk.NEWLINE);
              par.add(new Phrase(0, "このお荷物は、差出人様よりご依頼を受けて、当社が差出人となり宅配事業社へ差し出す", font));
              par.add(Chunk.NEWLINE);
              par.add(new Phrase(0, "" + site_name + "宅配差出代行サービスにてお届けいたしました。", font));
              break;
          default: // 信書便
              par.add(Chunk.NEWLINE);
              par.add(new Phrase(0, "注意事項:本品は破損及び変質しやすくなっておりますので、取り扱いには十分ご注意ください。", font));
              par.add(Chunk.NEWLINE);
              par.add(new Phrase(0, "「" + site_name + "」が提供いたしますメッセージカードは信書便に相当いたします。", font));
          }
          ct.addText(par);
          ct.go();
          cb.stroke();
          
          // 備考欄右
          font = new Font(bf, 6);
          String rightPhrase;
          ct.setSimpleColumn(mm2pt(105), mm2pt(8), mm2pt(205), mm2pt(8) + 20, 10, Element.ALIGN_TOP);
          par = new Paragraph();
          // 090628 eo 向けかどうかで文章を変更
          rightPhrase = "URL:http://www.e-denpo.net";
          if(purchase.isEo()) {
              rightPhrase += "/eo";
          }
          rightPhrase += " TEL:06-4796-0304 E-mail:";
          // 090719 eo 向けかどうかでアドレスを変更
          rightPhrase += purchase.isEo() ? "eo" : "info";
          rightPhrase += "@e-denpo.net";
          par.add(new Phrase(0, rightPhrase, font));
          par.add(Chunk.NEWLINE);
          par.add(new Phrase(0, "(C)KSG International Co,.LTD 2-5-25 Umeda Kita-Ku Osaka-City Osaka", font));
          ct.addText(par);
          ct.go();
          cb.stroke();
        }

        // 受領表の色分け
        PdfContentByte canvas = writer.getDirectContent();
        //canvas.setRGBColorFill(0xFF, 0x00, 0x00);
        if(purchase.siteTypeId == 6) {
            // でんぽっぽ：青
            canvas.setRGBColorFill(0x00, 0x00, 0xFF);
        } else {
            // E電報：緑
            canvas.setRGBColorFill(0x00, 0x80, 0x00);
        }
        canvas.rectangle(mm2pt(210) - 22 - 180, mm2pt(144) - 5, 180, 5);
        canvas.rectangle(mm2pt(210) - 22 - 5, mm2pt(144) - 40, 5, 40);
        canvas.fill();
        canvas.fillStroke();
        
        } finally { doc.close(); }
        reader = new PdfReader(out.toByteArray());
        return reader;
    }
    
    // doc初期化
    public void initPdf() throws DocumentException {
        out = new ByteArrayOutputStream();
        writer = PdfWriter.getInstance(doc, out);
        doc.open();
        doc.newPage();
        // 共通の表示用変数群
        cb = writer.getDirectContent();
        ct = new ColumnText(cb);
    }
    
 // 配送用のコード画像を追加
    public boolean addPurchaseImage(Purchase purchase, Document doc, PdfContentByte cb) throws DocumentException, IOException {
        // 画像の表示数カウンタ
        int iCount = 1;
        for(Image purcahseImage : purchase.getPurchaseImages()) {
            // 電報に設定されている使用数を超えていたら、処理を抜ける
            if(key2int("image.count") < iCount) { break; }
            // 20120314 : kawasaki : 画像が無いPDFに対応
            if(purcahseImage != null) {
                purcahseImage.scalePercent(5f);
                purcahseImage.scaleToFit(mm2pt(key2int("image.scale.w" + iCount)), mm2pt(key2int("image.scale.h" + iCount)));
                purcahseImage.setAbsolutePosition(mm2pt(key2int("image.position.x" + iCount)), mm2pt(key2int("image.position.y" + iCount)));
                doc.add(purcahseImage);
            }
            iCount ++;
        }
        return true;
    }
    
    // 配送用のコード画像を追加
    public boolean addCodeImage(Purchase purchase, Document doc, PdfContentByte cb) throws DocumentException, IOException {
        // プリントコード種別で分岐
        if(purchase.printCodeType == null) {
            return false;
        } else if(purchase.printCodeType.equals("barcode")) {
            return this.addCodeImageBarcode(purchase, doc, cb);
        } else if(purchase.printCodeType.equals("qrcode")) {
            return this.addCodeImageQRcode(purchase, doc, cb);
        } else if(purchase.printCodeType.equals("none")) {
            // なにもしない
        } else {
            // それ以外が来た時も何もしない
        }
        return true;
    }
    
    // 配送用のコード画像を追加（バーコード）
    public boolean addCodeImageBarcode(Purchase purchase, Document doc, PdfContentByte cb) throws DocumentException, IOException {
        if(purchase.barcodeString == null) return false;
        
        BarcodeCodabar codabar = new BarcodeCodabar();
        String bs = purchase.barcodeString.replaceAll("-", "");
        codabar.setCode("a" + bs + "a");
        codabar.setStartStopText(true);
        Image bImage = codabar.createImageWithBarcode(cb, null, null);
        bImage.scaleToFit(this.mm2pt(60), this.mm2pt(25));
        bImage.setAbsolutePosition(mm2pt(10), mm2pt(75));
        doc.add(bImage);
        
        return true;
    }
    
    // 配送用のコード画像を追加（QRコード）
    public boolean addCodeImageQRcode(Purchase purchase, Document doc, PdfContentByte cb) throws DocumentException, IOException {
        if(purchase.qrcodeString == null) return false;
        
        QRCodeImage qimage = new QRCodeImage(purchase.qrcodeString);
        Image qrcodeImage = Image.getInstance(qimage.getQRCodeImagePNG(), Color.WHITE, true);
        qrcodeImage.setAbsolutePosition(mm2pt(40), mm2pt(79));
        doc.add(qrcodeImage);
        
        return true;
    }

    // 配送確認用バーコード画像を追加
    public boolean addDeliveryBarcodeImage(Purchase purchase, Document doc, boolean main) throws DocumentException, IOException {
        // 注文番号がない場合は、非表示
        if (purchase.id == 0) {
          return true;
        }
        
        Image bImage = null;
        Barcode codabar = new Barcode128();
        String str = purchase.id + "_" + purchase.itemCode;
        int padding_x = 4;
        //System.out.printf(str);
        if(main) {
            // main PDF
            // 通信文
            if(purchase.siteTypeId == 6) {
                codabar.setCode("1" + str);
                codabar.setStartStopText(true);
                bImage = codabar.createImageWithBarcode(cb, null, BaseColor.WHITE);
                bImage.scaleToFit(this.mm2pt(40), this.mm2pt(10));
                bImage.setAbsolutePosition(mm2pt(97), mm2pt(4));
                doc.add(bImage);
            } else {
                int padding_y = 0;
                int direction = 0;
                if ((purchase.movieFlag != null) && (purchase.movieFlag)) {
                    // 動画電報は横固定
                    direction = 1;
                } else {
                    // 通常
                    direction = Integer.parseInt(prop.getProperty("direction"));
                }
                if (direction != 1) {
                    padding_y = 10;
                }
                codabar.setCode("1" + str);
                codabar.setStartStopText(true);
                bImage = codabar.createImageWithBarcode(cb, null, BaseColor.WHITE);
                bImage.scaleToFit(this.mm2pt(40), this.mm2pt(8));
                bImage.setAbsolutePosition(doc.right() - mm2pt(30), mm2pt(0) + padding_y);
                doc.add(bImage);
            }
        } else {
            // sub PDF
            // 受領書
            codabar.setCode("2" + str);
            codabar.setStartStopText(true);
            bImage = codabar.createImageWithBarcode(cb, null, null);
            bImage.scaleToFit(this.mm2pt(40), this.mm2pt(8));
            bImage.setAbsolutePosition(mm2pt(170) - padding_x, mm2pt(75));
            doc.add(bImage);
            
            // 送り状
            codabar.setCode("3" + str);
            codabar.setStartStopText(true);
            bImage = codabar.createImageWithBarcode(cb, null, null);
            bImage.scaleToFit(this.mm2pt(40), this.mm2pt(8));
            bImage.setAbsolutePosition(mm2pt(170) - padding_x, mm2pt(15));
            doc.add(bImage);
        }
        
        return true;
    }
    
    // プロパティのkey値を取得し、int型で返す
    private int key2int(String key) {
        return Integer.parseInt(prop.getProperty(key));
    }
    
    // 回転文字か
    private static boolean ifRotateString(String tmpStr) {
        if(     (rotateStr0.indexOf(tmpStr) != -1)
            ||  (rotateStr1.indexOf(tmpStr) != -1)
            ||  (rotateStr2.indexOf(tmpStr) != -1)
            ||  (rotateStr3.indexOf(tmpStr) != -1)
            ||  (rotateStr4.indexOf(tmpStr) != -1)
            ||  (rotateStr5.indexOf(tmpStr) != -1)) {
            return true;
        }
        return false;
    }
    
    // 小さい文字か
    private static boolean ifSmallString(String tmpStr) {
        if(smallStr.indexOf(tmpStr) != -1) {
            return true;
        }
        return false;
    }
    
    // 特殊インデント文字か
    private static boolean ifSepString(String tmpStr) {
        if(sepStr.indexOf(tmpStr) != -1) {
            return true;
        }
        return false;
    }
    
    // 横書き専用、ハイフン系文字の強制変換
    private String convertHyphen(String tmpStr) {
    	/*
        String retStr = "";
        String convertStr = "??";
        char c;
        for(int i = 0; i < tmpStr.length() ; i++){
            c = tmpStr.charAt(i);
            if(convertStr.indexOf(c) != -1) {
                retStr += "－";
            }else {
                retStr += c;
            }
        }
        return retStr;
        */
    	return tmpStr;
    }
    
    // 最大文字数の設定
    private int setMaxCount(int size, int target) {
        int length = 0;
        switch(target) {
        // 受取人については、肩書分を考慮に入れる
        case 0:
            switch(size) {
            case 10:
                length = 25 + 4;
                break;
            case 14:
                length = 25 + 4;
                break;
            case 16:
            case 18:
                length = 25 + 4;
                break;
            }
            break;
        // 差出人
        case 1:
            switch(size) {
            case 10:
                length = 49;
                break;
            case 14:
                length = 35;
                break;
            case 16:
            case 18:
                length = 27;
                break;
            }
            break;
        // 本文
        case 2:
            switch(size) {
            case 10:
                length = 25;
                break;
            case 14:
                length = 16;
                break;
            case 16:
            case 18:
                length = 14;
                break;
            }
            break;
        }
        return length;
    }
    
    //お届け希望日時の変換
    private static String formatDate2Term_wishedAt(Date wishedAt, Boolean speedFlag, Boolean showTime){
        //電報の式典日時を修正する
        String wished_at_time;
        String term;
        
        //時刻のみ、文字列として抽出
        SimpleDateFormat time_format = new SimpleDateFormat("HH:mm");
        wished_at_time = time_format.format(wishedAt);
        
        if(showTime) {
          if(wished_at_time.equals("19:02")){
              term = "指定なし（9時～19時）";
          }else if(wished_at_time.equals("11:59")){
              term = "午前（9時～12時） ";
          }else if(wished_at_time.equals("19:01")){
              term = "午後（12時～19時） ";
          }else if(wished_at_time.equals("18:59")){
              term = "20時まで";
          }else if(wished_at_time.equals("11:00")){
              term = "11時まで（9時～11時）";
          }else if(wished_at_time.equals("15:00")){
              if((speedFlag == null) || (speedFlag == false)) {
                  term = "15時まで（9時～15時）";
              } else {
                  term = "15時まで（12時～15時）";
              }
          }else if(wished_at_time.equals("18:00")){
              term = "18時まで（12時～18時） ";
          }else{
              term = wished_at_time;
          }
        } else {
            if(wished_at_time.equals("19:02")){
                term = "指定なし";
            }else if(wished_at_time.equals("11:59")){
                term = "午前";
            }else if(wished_at_time.equals("19:01")){
                term = "午後 ";
            }else if(wished_at_time.equals("18:59")){
                term = "20時まで ";
            }else if(wished_at_time.equals("11:00")){
                term = "11時まで";
            }else if(wished_at_time.equals("15:00")){
                if((speedFlag == null) || (speedFlag == false)) {
                    term = "15時まで";
                } else {
                    term = "15時まで";
                }
            }else if(wished_at_time.equals("18:00")){
                term = "18時まで";
            }else{
                term = wished_at_time;
            }
        }
        
        wished_at_time =null;
        wishedAt=null;
        
        return term;
    }
    
    //式典日時の変換
    private static String formatDate2Term_cemeonyAt(Date celemonyAt){
        //電報の式典日時を修正する 2008/06/17
        String celemony_at_time;
        String term;
        
        //時刻のみ、文字列として抽出2008/06/17
        SimpleDateFormat time_format = new SimpleDateFormat("HH:mm");
        celemony_at_time = time_format.format(celemonyAt);
        
        if(celemony_at_time.equals("19:02")){
            term = "指定無";
        }else if(celemony_at_time.equals("11:59")){
            term = "午前";
        }else if(celemony_at_time.equals("19:01")){
            term = "午後";
        }else if(celemony_at_time.equals("18:59")){
            term = "当日中";
        }else if(celemony_at_time.equals("00:00")){
            term ="";
        }else {
            term = celemony_at_time;
        }
        
        celemony_at_time =null;
        celemonyAt=null;
        
        return term;
    }
    
    // mm単位をpt単位に変換する
    public float mm2pt(float mm) {
        return (float)(mm * 2.835);
    }
    
}