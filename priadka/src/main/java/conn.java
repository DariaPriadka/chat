import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class conn {
    public static Connection conn;
    public static Statement statmt;
    public static ResultSet resSet;

    // --------ПОДКЛЮЧЕНИЕ К БАЗЕ ДАННЫХ--------
    public static void Conn() throws ClassNotFoundException, SQLException
    {
        conn = null;
        Class.forName("org.sqlite.JDBC");
        conn = DriverManager.getConnection("jdbc:sqlite:TEST1.s3db");

        System.out.println("База Подключена!");
    }

    // --------Создание таблицы--------
    public static void CreateDB() throws ClassNotFoundException, SQLException
    {
        statmt = conn.createStatement();
        statmt.execute("CREATE TABLE if not exists 'users' ('id_user' INTEGER PRIMARY KEY AUTOINCREMENT, 'name' text);");
        statmt.execute("CREATE TABLE if not exists 'messages' ('id_message' INTEGER PRIMARY KEY AUTOINCREMENT, FOREIGN KEY (user) REFERENCES users(id_user), 'message' text, 'date' datetime);");

        System.out.println("Таблица создана или уже существует.");
    }

    // --------Заполнение таблицы--------
    public static void WriteDB() throws SQLException
    {
        statmt.execute("INSERT INTO 'users' ('name') VALUES ('Igor'); ");
        statmt.execute("INSERT INTO 'messages' ('user', 'message', 'date') VALUES ('1', 'Privet', 'jan 1 2009 13:22:15'); ");
    }

    // -------- Вывод таблицы--------
    public static void ReadDB() throws ClassNotFoundException, SQLException
    {
        resSet = statmt.executeQuery("SELECT * FROM users");

        while(resSet.next())
        {
            int id_user = resSet.getInt("id_user");
            String  name = resSet.getString("name");
            int id_message = resSet.getInt("id_message");
            int user = resSet.getInt("user");
            String  message = resSet.getString("message");
           // datetime date = resSet.getDate("id_message");

            System.out.println( "ID user = " + id_user );
            System.out.println( "name = " + name );
            System.out.println( "ID message= " + id_message );
            System.out.println( "user = " + user );
            System.out.println( "message = " + message );
            System.out.println();
        }

        System.out.println("Таблица выведена");
    }

    // --------Закрытие--------
    public static void CloseDB() throws ClassNotFoundException, SQLException
    {
        conn.close();
        statmt.close();
        resSet.close();

        System.out.println("Соединения закрыты");
    }

}