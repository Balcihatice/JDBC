import org.postgresql.gss.GSSOutputStream;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static java.sql.DriverManager.getConnection;

public class ExecuteUpdate01 {
    /*1) Doğrudan veri çekme(SELECT) veya veri listeme işlemi için executeQuery metodu kullanılabilir.(statement.executeQuery("sql-komutları");
      2)Veri ekleme, veri güncelleme ve veri silme işlemi sonrası eklenen, güncellenen veya
      silinen kayıt sayısı bilgisi için executeUpdate metodu kullanılabilir.(statement.executeUpdate("sql-komutları");
      3)Birden fazla-toplu işlem yapmak için executeBatch metodu kullanılır.(statement.executeBatch();
      4)Metodu kullanabilmek için komutların addBatch metodu ile eklenmesi gerekir.
      statement.addBatch("komut-1");
      statement.addBatch("komut-2");
      statement.addBatch("komut-3");
       statement.executeBatch();
    */


    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        Connection con = getConnection("jdbc:postgresql://localhost:5432/Batch103", "postgres", "1234");
        Statement st = con.createStatement();

        //1. Örnek: number_of_employees değeri ortalama çalışan sayısından az olan number_of_employees değerlerini
        // 16000 olarak UPDATE edin.
        String sql1 = "UPDATE companies \n" +
                "SET number_of_employees =16000\n" +
                "WHERE number_of_employees<(SELECT AVG(number_of_employees) FROM companies)";


        int updateEdilenSatirSayisi = st.executeUpdate(sql1);//kac satir degistirildi bilgi verdi
        System.out.println("updateEdilenSatirSayisi = " + updateEdilenSatirSayisi);
       ResultSet resultSet1 = st.executeQuery("SELECT * FROM companies");//yukarida update edildi, burada cagirip goruntuledik
       while (resultSet1.next()) {
           System.out.println(resultSet1.getInt(1)+"--"+resultSet1.getString(2)+"--"+resultSet1.getInt(3));
       }
        con.close();
        st.close();
        resultSet1.close();


    }
}
