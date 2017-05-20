/**
 * Created by x2009 on 19.05.2017.
 */

import java.awt.geom.Arc2D;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.regex.Pattern;


public class Server {



    public static void main(String[] args) throws SQLException {

        Menus m = new Menus();
        m.mainMenu();

    }

}
