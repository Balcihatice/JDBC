import java.sql.Connection;
import java.sql.Statement;

public class Runner {

    public static void main(String[] args) {
        //1. Adım: Driver'a kaydol
        //2. Adım: Datbase'e bağlan
        JdbcUtils.connectToDataBase("localhost","postgres","postgres","1234");

        //3.Adim Statement olustur.
        Statement statement = JdbcUtils.createStatement();

        //4.Adim Query calistir
        //JdbcUtils.execute("CREATE TABLE student(name VARCHAR(20)), id  INT, adress VARCHAR(80))");


        JdbcUtils.createTable("School","classes VARCHAR(20)","teacher_name VARCHAR(20)","id INT");


        //5.Adım: Balantı ve Statement'i kapat
        JdbcUtils.closeConnectionAndStatement();



    }
}
