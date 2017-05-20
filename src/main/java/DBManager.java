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
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;
    HashMap<Integer,String> map;

    private void connectToDb(){

        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            // Setup the connection with the DB
            connect = DriverManager
                    .getConnection("jdbc:mysql://localhost/PAO_App?"
                            + "user=x2009&password=fuckriot12");


            statement = connect.createStatement();

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
        int views = s.getInt(1);
        views++;
        statement.executeUpdate("UPDATE articole SET `views`='" + views + "' WHERE `articol_id`='"+ id + "'");
    }


    public List<Articles> getNextSet() throws SQLException {
        List<Articles> a = new ArrayList<Articles>();

        int i = 0;
        while(resultSet.next() && i < 40){
            int id;
            String title;
            String content;
            String auth;
            Date date;
            int views;
            int authid;

            id = resultSet.getInt(1);
            title = resultSet.getString(2);
            content = resultSet.getString(3);
            date = resultSet.getDate(5);
            views = resultSet.getInt(6);

            authid = resultSet.getInt(4);


            if(map.get(authid) == null) {
                ResultSet s = statement.executeQuery("select username from users where user_id = '" + authid + "'");
                auth = s.getString(1);
                map.put(authid,auth);
            }else{
                auth = map.get(authid);
            }

            a.add(new Articles(id,title,content,auth,date, views));
            i++;

        }

        return  a;


    }


    public void loadArticles() throws SQLException {



        resultSet = statement.executeQuery("select * from articole order by date desc limit 300");

    }

    public int getNrOfArticles() throws SQLException {
        ResultSet s = statement.executeQuery("select count(*) from articole");

        return s.getInt(1);
    }



    public void getUsers() throws SQLException {



    }

}
