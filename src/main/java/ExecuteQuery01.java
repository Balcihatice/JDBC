import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static java.sql.DriverManager.getConnection;

public class ExecuteQuery01 {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        Connection con = getConnection("jdbc:postgresql://localhost:5432/Batch103", "postgres", "1234");
        Statement st = con.createStatement();

        //1. Örnek:  region id'si 1 olan "country name" değerlerini çağırın.

        String sql1 = "SELECT country_name FROM countries WHERE region_id=1";
        boolean r1 = st.execute(sql1);
        System.out.println("r1 =" + r1);

        //Recordlari(satir=row) gormek icin ExecuteQuery() methodunu kullanmaliyiz.
        ResultSet resultSet1 = st.executeQuery(sql1); //ResultSet return edecek

        while (resultSet1.next()) {
            System.out.println(resultSet1.getString("country_name"));
            //country_name yerine 1 yazabiliriz -->> 1.satir

        }
        System.out.println("*******************************");
        //2.Örnek: "region_id"nin 2'den büyük olduğu "country_id" ve "country_name"

        String sql2 = "SELECT country_name,country_id FROM countries WHERE region_id>2 ";
        ResultSet resultSet2 = st.executeQuery(sql2);
        while (resultSet2.next()) {
            System.out.println(resultSet2.getString("country_name") + "__" + resultSet2.getString("country_id"));
        }
        System.out.println("************************");

        //3.Örnek: "number_of_employees" değeri en düşük olan satırın tüm değerlerini çağırın.
        String sql3 = "SELECT * FROM companies WHERE number_of_employees=(SELECT MIN(number_of_employees) FROM companies)";
        ResultSet resultSet3 = st.executeQuery(sql3);
        while (resultSet3.next()) {
            System.out.println(resultSet3.getInt(1)+"_"+resultSet3.getString(2)+"_"+resultSet3.getInt(3));
        }
         con.close();
        st.close();
    }
}
