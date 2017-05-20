import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Created by x2009 on 20.05.2017.
 */
public class Menus {
    private DBManager dbm = new DBManager();
    private Scanner scan = new Scanner(System.in);
    private List<List<Articles>> articles = new ArrayList<List<Articles>>();
    private int articlesIndex = 0;
    private String loggedUser;


    private boolean loginUser(String username, String pw){
        try{
            if(!dbm.checkUsername(username)){
                System.out.println("Incorrect credentials!");
                return false;
            }

        }catch (Exception e){
            return false;
        }

        try {
            if(!dbm.checkPassword(username,pw)){
                System.out.println("Incorrect credentials!");
                return false;
            }
        } catch (SQLException e) {
            return false;
        }

        return true;
    }


    private boolean validateUsername(String username){

        if(!validateName(username)){
            return false;
        }

        if(username.length() < 3){
            System.out.println("Username should be at least 3 characters length");
            return false;
        }

        try{
            if(dbm.checkUsername(username)) {
                System.out.println("Username already taken");
                return false;
            }

        }catch (Exception e){
            return false;
        }

        return true;
    }

    private boolean validateEmail(String email){
        Pattern emailp = Pattern.compile("[a-zA-Z.0-9]+@\\w+.\\w+");

        if(email.length() < 3){
            System.out.println("Email should be at least 6 characters length");
            return false;
        }

        if(!emailp.matcher(email).matches()){
            System.out.println("PLEASE ENTER A VALID EMAIL!!");
            return false;
        }

        try{
            if(dbm.checkEmail(email)){
                System.out.println("Email already taken");
                return false;
            }


        }catch (Exception e){
            System.out.println("Error :"  +e.getMessage() );
            return false;
        }

        return true;
    }

    private boolean validateName(String userName) {

        if(userName.length() < 3){
            System.out.println("Your name should be at least 3 characters length");
            return false;
        }

        Pattern pula = Pattern.compile(".*pula.*");
        Pattern fuck = Pattern.compile(".*fuck.*");
        Pattern dick = Pattern.compile(".*dick.*");


        if(pula.matcher(userName).matches() ||
                pula.matcher(userName).matches() ||
                pula.matcher(userName).matches() ||
                fuck.matcher(userName).matches() ||
                fuck.matcher(userName).matches() ||
                fuck.matcher(userName).matches() ||
                dick.matcher(userName).matches() ||
                dick.matcher(userName).matches() ||
                dick.matcher(userName).matches()){
            System.out.println("ESTI UN NESIMTIT!!");
            return false;
        }

        return true;
    }

    private void printArticles(){
        articles.get(articlesIndex).forEach((art)->{
            System.out.println("ID: " + art.getId());
            System.out.println("Title: " + art.getTitle());
            System.out.println("Author: " + art.getAuth());
            System.out.println("Published at: " + art.getDate().toString());
            System.out.println("Preview: " + art.getContent().substring(0,20));
            System.out.println("Views: " + art.getViews());
            System.out.println("-----------------");
        });

    }


    private void readArticle(int id){
        boolean opened = false;
        List<Articles> l =  articles.get(articlesIndex);

        for(Articles art : l){
            if(art.getId() == id){
                opened = true;
                System.out.println("ID: " + art.getId());
                System.out.println("Title: " + art.getTitle());
                System.out.println("Author: " + art.getAuth());
                System.out.println("Published at: " + art.getDate().toString());
                System.out.println("Preview: " + art.getContent());
                System.out.println("Views: " + art.getViews());
                System.out.println("-----------------");
                try {
                    dbm.incrementViews(id);
                } catch (SQLException e) {
                    System.out.println("Error incrementing views!!");
                }
                break;
            }
        }

        if(opened = false){
            System.out.println("ID does not exist");
        }else{
            System.out.println("Press ENTER to return");
            scan.nextLine();
        }

    }

    private void articlesMenu(){
        int ch = 0;

        try {
            dbm.loadArticles();
        } catch (SQLException e) {
            System.out.println("error loadin articles");
            return;
        }

        do{

            System.out.println("Articles menu:\n 1. Load next 40 articles\n2. Load previous 40 articles\n3.to open an article\n 4.exit");

            ch = scan.nextInt();
            scan.nextLine();

            switch (ch){
                case 1 : if(articlesIndex < articles.size()){
                    printArticles();
                    articlesIndex++;
                }else{
                    try {
                        List<Articles> l = dbm.getNextSet();
                        if(l.size() == 0){
                            System.out.println("No more articles to load");
                        }else {
                            articles.add(l);
                            printArticles();
                            articlesIndex++;
                        }
                    } catch (SQLException e) {
                        System.out.println("Error loading new articles");
                    }
                }
                    break;

                case 2 : if(articlesIndex > 0){
                    printArticles();
                    articlesIndex--;
                }else if(articlesIndex == 0){
                    printArticles();
                }else{
                    System.out.println("No more articles to go back to!");
                }
                    break;
                case 3 : System.out.println("Please enter the article id:");
                            int id = scan.nextInt(); scan.nextLine();
                            readArticle(id);
            }

        } while (ch != 4);


    }

    private void publishArticle(String title,String content){

        dbm.publishArticle(title,loggedUser,content);

    }

    private void writeArticle(){
        String title;
        String content = "";
        System.out.println("Title:");
        title =  scan.nextLine();
        System.out.println("Content: (  Write '!end!' on a single line to finish  ) ");
        do {
            String s = scan.nextLine();
            if(s == "!end!") break;

            content = content + s;
        }while(true);

        int ch = 0;

        do{
            System.out.println("What do you want to do with your article? \n1.Publish\n2.Save to drafts\n 3.Discard");
            ch = scan.nextInt();
            scan.nextLine();

            switch (ch){
                case 1 : publishArticle(title,content);
                case 2 : saveToDraft(title,content);
            }

        }while(ch != 1 && ch != 2 && ch != 3);


    }


    private void userMenu(){
        int ch = 0;

        do {
            System.out.println("Welcome," + loggedUser + "!!\n1.Read articles\n2.Write an article\n3.View your drafts\n4.exit");
            ch = scan.nextInt();
            scan.nextLine();

            switch (ch){
                case 1 : articlesMenu();
                case 2 : writeArticle();
                case 3 : viewDrafts();
            }


        }while (ch != 4);
    }


    private void loginMenu(){
        String username;
        String password;
        String pw;

        do {

            System.out.println("Please enter your username");
            username = scan.nextLine();


            System.out.println("Please enter your password");
            password = scan.nextLine();

            pw = Encryptor.cryptWithMD5(password);

        }while (!loginUser(username,pw));

        loggedUser = username;

        userMenu();
    }


    private void registerMenu(){
        String firstname;
        String lastname;
        String username;
        String password;
        String email;
        do {
            System.out.println("Please enter your firstname");
            firstname = scan.nextLine();
        }while(!validateName(firstname));

        do {
            System.out.println("Please enter your lastname");
            lastname = scan.nextLine();
        }while (!validateName(lastname));

        do {
            System.out.println("Please enter your email");
            email = scan.nextLine();
        }while (!validateEmail(email));


        do {
            System.out.println("Please enter your username");
            username = scan.nextLine();
        }while (!validateUsername(username));

        do {
            System.out.println("Please enter your password ( minimum 5 characters )");
            password = scan.nextLine();
        }while (password.length() < 5);


        String encryptedPw = Encryptor.cryptWithMD5(password);

        Users user = new Users(lastname,firstname,email,username,encryptedPw);

        try {
            dbm.addUser(user);
        } catch (SQLException e) {
            System.out.println("We had an error trying to register you. Please try again");
        }

    }

    public void mainMenu(){

        int choice = 0;



        do {
            System.out.println("Welcome! \n 1.register\n2.login\n3.view published articles\n4.Print this menu again");
            choice = scan.nextInt();
            scan.nextLine();

            switch (choice) {
                case 1:
                    registerMenu();
                    break;
                case 2:
                    loginMenu();
                    break;
                case 3:
                    articlesMenu();
                    break;
            }
        }while (choice!=0);
        mainMenu();
    }
}
