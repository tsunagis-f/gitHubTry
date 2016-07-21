package pdf.net.edenpo;

import java.io.*;
import java.lang.reflect.*;
import java.sql.*;
import java.util.*;

public class DatabaseManager {
    static public Purchase getPurchase(int id) {
        // 実行SQL
        String sql = 
              " SELECT                    "
            + "    p.*                    "
            + "  , s.name AS state_name   "
            + "  , i.id AS item_id        "
            + "  , i.front_image AS item_front_image "
            + "  , i.code AS item_code    "
            + "  , i.name AS item_name    "
            + "  , u.login AS user_login  "
            + "  , m.delivery_type AS member_delivery_type "
            + " FROM purchases p          "
            + "  LEFT JOIN states s  ON p.state_id = s.id "
            + "  LEFT JOIN items i   ON p.item_id = i.id "
            + "  LEFT JOIN users u   ON p.user_id = u.id "
            + "  LEFT JOIN members m ON p.member_id = m.id "
            + " WHERE p.id = " + id
            + " LIMIT 1 "
        ;
        
        // 注文情報取得
        Purchase purchase = new Purchase();
        DatabaseManager.setParams(sql, purchase);
        
        return purchase;
    }
    
    static public void setParams(String sql, Object obj) {
        // 接続情報の読込
        Properties prop = new Properties();
        try {
            prop.load(new FileInputStream("Connection.properties"));
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        // 接続情報を設定
        String driver   = prop.getProperty("driver");
        String url      = prop.getProperty("host");
        String user     = prop.getProperty("user");
        String password = prop.getProperty("password");
        
        try {            
            // DB接続
            Class.forName (driver);
            Connection con = DriverManager.getConnection(url, user, password);
            
            // SQL実行
            Statement stmt = con.createStatement ();
            ResultSet rs = stmt.executeQuery (sql);
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            String[] columnNames =  new String[columnCount];
            for (int i = 0; i < columnCount; i++ ) {
                columnNames[i] = rsmd.getColumnLabel(i+1);
            }
            
            Field[] fields = obj.getClass().getFields();
            while(rs.next()) {
                for(Field field: fields) {
                    // Java命名規則のメンバ変数名をDBのcolumn名に変換
                    String column_name = field.getName().replaceAll("([A-Z])","_$1").toLowerCase();
                    if(Arrays.asList(columnNames).contains(column_name)) {
                      // 結果を取得し
                      Object value = rs.getObject(column_name);
                      // 本文のみ改行記号を修正
                      if ((column_name == "subscription") && (value != null)) {
                          value = value.toString().replaceAll("\r\n", "\n");
                      }
                      if(value != null) {
                          field.set(obj, value);
                      }
                    }
                }
            }
            
            // DB切断
            rs.close();
            stmt.close();
            con.close();
        } catch(Exception e) {
            System.err.println("Error");
            e.printStackTrace();
        }
    }
}
