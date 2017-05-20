/**
 * Created by x2009 on 19.05.2017.
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


public class DBManager {

    private Connection connect = null;
    private Statement statement = null;
    private Statement statement2 = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;
    HashMap<Integer,String> map = new HashMap<>();
    List<Articles> articlesL = new ArrayList<Articles>();
    int arti = 0;

    private void connectToDb(){

        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            // Setup the connection with the DB
            connect = DriverManager
                    .getConnection("jdbc:mysql://localhost/PAO_App?"
                            + "user=x2009&password=fuckriot12");


            statement = connect.createStatement();
            statement2 = connect.createStatement();

        }catch (Exception e){
            System.out.println("eroare la conectare:");
            System.out.println(e.getMessage());
        }
    }

    public DBManager(){
        connectToDb();
    }


    public boolean checkPassword(String username, String password) throws SQLException {
        ResultSet s = statement.executeQuery("Select username,password from users where username = '" + username + "'");
        if(s.next() == false){
            return false;
        }else if(s.getString("password").equals(password)){
            return true;
        }
        return false;
    }

    public void addUser(Users user) throws SQLException {

        //INSERT INTO `PAO_App`.`users` (`nume`, `prenume`, `username`, `email`, `password`) VALUES ('da', 'da', 'da', 'da', 'da');

        statement.executeUpdate("INSERT INTO users (`nume`, `prenume`, `username`, `email`, `password`) VALUES ('" + user.getNume() + "','" + user.getPrenume() + "','" + user.getUsername() + "','" + user.getEmail() + "','" + user.getPassword() + "')");
    }

    public boolean checkUsername(String user) throws SQLException {

        ResultSet s = statement.executeQuery("select username from users where username = '" + user + "'");

        return s.next();


    }

    public boolean checkEmail(String email) throws SQLException {

        ResultSet s = statement.executeQuery("select email from users where email = '" + email + "'");

        return s.next();


    }

    public void incrementViews(int id) throws SQLException {
        ResultSet s = statement.executeQuery("select views from articole where articol_id = '" + id +  "'");
        s.next();
        int views = s.getInt(1);
        views++;
        statement.executeUpdate("UPDATE articole SET `views`='" + views + "' WHERE `articol_id`='"+ id + "'");
    }




    public List<Articles> loadArticles() throws SQLException {

            articlesL.clear();

            ResultSet s = statement.executeQuery("Select * from articole order by date desc limit 200");


            int id;
            String title;
            int auth_id;
            String content;
            Date date;
            int views;

            while(s.next()){

                id = s.getInt(1);
                title = s.getString(2);
                auth_id = s.getInt(3);
                content = s.getString(4);
                date = s.getDate(5);
                views = s.getInt(6);

                ResultSet s2 = statement2.executeQuery("Select username from users where user_id = '" + auth_id +"'");

                s2.next();

                String auth = s2.getString(1);


                articlesL.add(new Articles(id,title,content,auth,date, views));
            }


            return articlesL;




    }

    public void publishArticle(String title,String auth, String content) throws SQLException {

        int auth_id;

        ResultSet s = statement.executeQuery("select user_id from users where username = '" + auth +"'");
        s.next();
        auth_id = s.getInt(1);

        statement.executeUpdate("insert into articole (`titlu`, `autor_id`, `continut`) VALUES ('" + title +"','" + auth_id + "','" + content +"')");

        articlesL.clear();

        loadArticles();

    }


    public int getNrOfArticles() throws SQLException {
        ResultSet s = statement.executeQuery("select count(*) from articole");
        s.next();
        return s.getInt(1);
    }



    public void getUsers() throws SQLException {



    }

}
