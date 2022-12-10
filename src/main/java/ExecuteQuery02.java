import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static java.sql.DriverManager.getConnection;

public class ExecuteQuery02 {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        Connection con = getConnection("jdbc:postgresql://localhost:5432/Batch103", "postgres", "1234");
        Statement st = con.createStatement();

        //1. Örnek: companies tablosundan en yüksek ikinci number_of_employees değeri olan company ve number_of_employees değerlerini çağırın.

        String sql = "select company,number_of_employees \n" +
                "from companies\n" +
                "order by number_of_employees desc \n" +
                "offset 1 row\n" +
                "fetch next 1 row only";

        ResultSet resultSet1 = st.executeQuery(sql);
        while (resultSet1.next()) { //while loop siradakileri almak icin var
            System.out.println(resultSet1.getString("company") + "--" + resultSet1.getInt("number_of_employees"));
        }

        //2. Yol -> Subquery ile
        String sql2 = " SELECT company, number_of_employees\n" +
                "FROM companies\n" +
                "WHERE number_of_employees = (SELECT MAX(number_of_employees)\n" +
                " FROM companies\n" +
                " WHERE number_of_employees < (SELECT MAX(number_of_employees)\n" +
                "FROM companies))";

        ResultSet resultSet2 = st.executeQuery(sql2);

        while (resultSet2.next()) {
            System.out.println(resultSet2.getString(1) + "--" + resultSet2.getString(2));
        }
        con.close();
        st.close();
        resultSet1.close();
        resultSet2.close();

    }
}
