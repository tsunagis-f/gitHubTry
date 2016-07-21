package pdf.net.edenpo;

import java.io.*;
import java.lang.reflect.*;
import java.text.*;
import java.util.*;
import java.util.List;

import org.dom4j.*;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.*;

import com.itextpdf.text.*;

public class Purchase {
    // 商品画像を取得
    public Image getItemImage() throws IOException, BadElementException {
        if(this.itemImage == null) {
            // 画像情報の読込
            Properties prop = new Properties();
            prop.load(new FileInputStream("Filepath.properties"));
            String imagePath = prop.getProperty("item_front_image_path") + this.itemId + "/" + this.itemFrontImage;
            this.itemImage = Image.getInstance(imagePath);
        }
        return this.itemImage;
    }
    
    // 注文画像を取得
    public Image[] getPurchaseImages() throws IOException, BadElementException {
        if(this.purchaseImages == null) {
            // 画像情報の読込
            Properties prop = new Properties();
            prop.load(new FileInputStream("Filepath.properties"));
            // 注文画像を初期化
            this.purchaseImages = new Image[3];
            for(int i=0;i<this.purchaseImages.length; i++) {
                this.purchaseImages[i] = null;
            }
            // NASから画像を取得
            String except_path = prop.getProperty("purchase_except_path");
            String except_path2 = prop.getProperty("purchase_except_path2");
            String image1 = "";
            String image2 = "";
            String image3 = "";
            // 画像1
            try {
                if (this.firstImage.indexOf(except_path) != -1) {
                    image1 = "../.." + this.firstImage;
                } else if (this.firstImage.indexOf(except_path2) != -1) {
                    image1 = this.firstImage;
                } else {
                    image1 = prop.getProperty("purchase_first_image_path")  + this.id + "/" + this.firstImage;
                }
                this.purchaseImages[0] = Image.getInstance(image1); 
            } catch (Exception e) {
            }
            // 画像2
            try {
                if (this.secondImage.indexOf(except_path) != -1) {
                    image2 = "../.." + this.secondImage;
                } else if (this.secondImage.indexOf(except_path2) != -1) {
                    image2 = this.secondImage;
                } else {
                    image2 = prop.getProperty("purchase_second_image_path") + this.id + "/" + this.secondImage;
                }
                this.purchaseImages[1] = Image.getInstance(image2); 
            } catch (Exception e) {
            }
            // 画像3
            try {
                if (this.thirdImage.indexOf(except_path) != -1) {
                    image3 = "../.." + this.thirdImage;
                } else if (this.thirdImage.indexOf(except_path2) != -1) {
                    image3 = this.thirdImage;
                } else {
                    image3 = prop.getProperty("purchase_third_image_path")  + this.id + "/" + this.thirdImage;
                }
                this.purchaseImages[2] = Image.getInstance(image3); 
            } catch (Exception e) {
            }
        }
        return this.purchaseImages; 
    }

    // 注文メイン画像（サイト連携）を取得
    public Image getMainImage() throws IOException, BadElementException {
        // 注文メイン画像を使用しない場合、nullを帰す
        if (this.mainImage == null) {
            return null;
        }
        
        if(this.purchaseMainImage == null) {
            // 画像情報の読込
            Properties prop = new Properties();
            prop.load(new FileInputStream("Filepath.properties"));
            String imagePath = prop.getProperty("purchase_main_image_path") + this.id + "/" + this.mainImage;
            this.purchaseMainImage = Image.getInstance(imagePath);
        }
        return this.purchaseMainImage;
    }
    
    // eo電報の場合、trueを返す
    public boolean isEo() {
        return this.siteTypeId == 2;
    }

    // メイン領域(PDF上部)に画像を使用する注文の場合、true
    public boolean isMainImagePdf() {
        return !((this.mainImage == null) || (this.mainImage == ""));
    }
    
    // 本文が何ページになるかを返す(エンディング電報専用)
    public int getEndingSubscriptionPageCount() {
        String[] sentence = this.subscription.replaceFirst("\\n\\$", "").split("\n");
        return ((sentence.length - 1) / 14) + 1;
    }
    // ページ番号を指定して本文取得
    public String getEndingSubscription(int pageNumber) {
        String[] sentence = this.subscription.replaceFirst("\\n\\$", "").split("\n");
        StringBuffer sb = new StringBuffer();
        for(int i=0; i < sentence.length; i++) {
          if (((i / 14) + 1) == pageNumber) {
            sb.append(sentence[i]);
            sb.append("\n");
          }
        }
        return sb.toString().replaceFirst("\\n\\$", "");
    }
    // XMLからデータを読み込む
    public Purchase loadXml(String filepath) throws DocumentException {
        SAXReader reader = new SAXReader();
        Document document = reader.read(filepath);
        @SuppressWarnings("unchecked")
        List<Node> nodes = document.selectNodes("//purchases/purchase");
        
        // 変数
        Field[] fields = null;
        String column_name = null;
        String class_name = null;
        Object value = null;
        // Date型のフォーマット
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        // XML解析
        for (Node node : nodes) {
            fields = this.getClass().getFields();
            for (Field field : fields) {
                // Java命名規則のメンバ変数名をDBのcolumn名に変換
                column_name = field.getName().replaceAll("([A-Z])","-$1").toLowerCase();
                class_name  = field.getType().getSimpleName();
                try {
                  // 入力値を取得
                  value = null;
                  if (class_name.equals("int")) {
                      value = Integer.parseInt(node.valueOf(column_name));
                  } else if (class_name.equals("Date")) {
                      value = sdf.parse(node.valueOf(column_name));
                  } else if (class_name.equals("Boolean")) {
                      value = Boolean.parseBoolean(node.valueOf(column_name));
                  } else { 
                      value = node.valueOf(column_name);
                  }
                  
                  // 入力値を代入
                  if(value != null) {
                      field.set(this, value);
                  }
                } catch(Exception e) {                        
                }
            }
        }
        return null;
    }
    
    // データをXMLに保存
    public Document saveXml() {
        Document document = null;
        try {
            document = DocumentHelper.createDocument();
            Element root = document.addElement("purchases").addElement("purchase");
            Field[] fields = this.getClass().getFields();
            String  column_name = null;
            Element column = null;
            for(Field field: fields) {
                column_name = field.getName().replaceAll("([A-Z])","_$1").toLowerCase();
                column = root.addElement(column_name);
                
                // クラス
                String column_type = field.getType().getSimpleName();
                if(column_type == "int") { column_type = "Integer"; }
                column.addAttribute("class", column_type);
                
                String value = "";
                try {
                    value = field.get(this).toString();
                } catch (Exception e) {}
                column.addText(value);
            }
            
            FileOutputStream fos = new FileOutputStream("simple.xml");
            OutputFormat format = OutputFormat.createPrettyPrint();
            XMLWriter writer = new XMLWriter(fos, format);
            writer.write(document);
            writer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return document;
    }
    
    // 配送情報
    public int id;
    public String orderId;
    public int externalOrderId;
    public Date wishedAt;
    public Date celemonyAt;
    public Date createdAt;
    // 宛先
    public String postalCode;
    public String stateName;
    public String address;
    public String addressMore;
    public String arriveName;
    public int arriveNameTitle;
    public String tel;
    // 電報情報
    public int telegramTypeId;
    public int telegramLayoutId;
    public int recipientFontId;
    public int recipientFontSize;
    public int subscriptionFontId;
    public int subscriptionFontSize;
    public int senderFontId;
    public int senderFontSize;
    public String recipientName;
    public int recipientNameTitle;
    public String recipient;
    public int recipientTitle;
    public String recipient2;
    public int recipient2Title;
    public String subscription;
    public String sender1;
    public String sender2;
    public String sender3;
    public String sender4;
    public String sender5;
    public String sender6;
    public String sender7;
    public String sender1Read;
    public String sender2Read;
    public String sender3Read;
    public String sender4Read;
    public String sender5Read;
    public String sender6Read;
    public String sender7Read;
    public String firstImage;
    public String secondImage;
    public String thirdImage;
    public String mainImage;
    // その他
    public String printCodeType;
    public String barcodeString;
    public String qrcodeString;
    public int siteTypeId;
    public String itemFrontImage;
    public Boolean movieFlag;
    public Boolean speedFlag;
    public Boolean tel_115Flag;
    // Join項目
    public int itemId;
    public String itemCode;
    public String itemName;
    public String userLogin;
    public int memberDeliveryType;
    // 画像
    private Image itemImage;
    private Image[] purchaseImages;
    private Image purchaseMainImage;
}
