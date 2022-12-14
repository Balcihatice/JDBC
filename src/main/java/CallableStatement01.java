import java.sql.*;

import static java.sql.DriverManager.getConnection;

public class CallableStatement01 {
    /*
    Javada methodlar return type sahibi olsada olmasada method olarak adlandirilir.
   SQL de ise data return ediyorsa 'function' denir. Return yapmiyorsa "precedure" olarak adlandirilir

    */

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        Connection con = getConnection("jdbc:postgresql://localhost:5432/Batch103", "postgres", "1234");
        Statement st = con.createStatement();

        //CallableStatement ile function cagirmayi parametrelendirecegiz
        //1.Adim: function kodunu yaz
        String sql1 = "CREATE OR REPLACE FUNCTION toplamaF (x NUMERIC, y NUMERIC)\n" +
                "RETURNS NUMERIC\n" +
                "LANGUAGE plpgsql\n" +
                "AS \n" +
                "$$\n" +
                "BEGIN\n" +
                "\n" +
                "RETURN x+y;\n" +
                "\n" +
                "END\n" +
                "$$";
        //2.Adim Function`i calistir.
        st.execute(sql1);

        //3.Adim : Function`i cagir.

        CallableStatement cst1 = con.prepareCall("{?=call toplamaF(?,?)}");
        //                return
        // 4.Adim : Return icin registerOutParameter() methodunu, parametreler icin ise set()... methodlarini uygula
         cst1.registerOutParameter(1, Types.NUMERIC);
         cst1.setInt(2,6);
         cst1.setInt(3,4);

        // 5.Adim execute() method ile CallableStatement`i calistir
        cst1.execute();

        //6.Adim: Sonucu cagirmak icin return datatype tipine gore
        System.out.println(cst1.getBigDecimal(1));





        //2. Örnek: Koninin hacmini hesaplayan bir function yazın.

        //1.Adım: Function kodunu yaz:
        String sql2 = "CREATE OR REPLACE FUNCTION  konininHacmiF(r NUMERIC, h NUMERIC)\n" +
                "RETURNS NUMERIC\n" +
                "LANGUAGE plpgsql\n" +
                "AS\n" +
                "$$\n" +
                "BEGIN\n" +
                "\n" +
                "RETURN 3.14*r*r*h/3;\n" +
                "\n" +
                "END\n" +
                "$$";

        //2. Adım: Function'ı çalıştır.
        st.execute(sql2);

        //3. Adım: Fonction'ı çağır.
        CallableStatement cst2 = con.prepareCall("{? = call konininHacmiF(?, ?)}");

        //4. Adım: Return için registerOurParameter() methodunu, parametreler için ise set() ... methodlarını uygula.
        cst2.registerOutParameter(1, Types.NUMERIC);
        cst2.setInt(2, 6);
        cst2.setInt(3, 9);

        //5. Adım: execute() methodu ile CallableStatement'ı çalıştır.
        cst2.execute();

        //6. Adım: Sonucu çağırmak için return data type tipine göre
        System.out.printf("%.2f",cst2.getBigDecimal(1));

    }
}
