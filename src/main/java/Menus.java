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
    private List<Articles> articles = new ArrayList<Articles>();

    private List<Drafts> drafts = new ArrayList<Drafts>();


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


    private int min(int a,int b){
        return a>b ? b: a;
    }

    private void printArticles(){
        articles.forEach((art)->{
            System.out.println("ID: " + art.getId());
            System.out.println("Title: " + art.getTitle());
            System.out.println("Author: " + art.getAuth());
            System.out.println("Published at: " + art.getDate().toString());
            System.out.println("Preview: " + art.getContent().substring(0, min(art.getContent().length()-1,20)));
            System.out.println("Views: " + art.getViews());
            System.out.println("-----------------");
        });

    }


    private void readArticle(int id){
        boolean opened = false;
        List<Articles> l =  articles;

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
                    art.setViews(art.getViews()+1);
                } catch (SQLException e) {
                    System.out.println("Error incrementing views!!" + e.getMessage());
                }
                break;
            }
        }

        if(opened == false){
            System.out.println("ID does not exist");
        }else{
            System.out.println("Press ENTER to return");
            scan.nextLine();
        }

    }

    private void articlesMenu(){
        int ch = 0;

        int nr = 0;


        try {
            articles = dbm.loadArticles();
            nr = dbm.getNrOfArticles();
            if (articles.size() == 0) {
                System.out.println("No articles to load");
            } else {
                printArticles();

            }
        } catch (SQLException e) {
            System.out.println("Error loading new articles" + e.getMessage());
        }


        do{
            System.out.println("There are " + nr  + " articles!:D");
            System.out.println("Articles menu:\n 1. Reload articles\n 2.Open an article\n 3.exit");

            ch = scan.nextInt();
            scan.nextLine();

            switch (ch) {
                case 1:
                    try {
                        articles = dbm.loadArticles();
                        if (articles.size() == 0) {
                            System.out.println("No articles to load");
                        } else {
                            printArticles();

                        }
                    } catch (SQLException e) {
                        System.out.println("Error loading new articles" + e.getMessage());
                    }

                    break;

                case 2:
                    System.out.println("Please enter the article id:");
                    int id = scan.nextInt();
                    scan.nextLine();
                    readArticle(id);
                    break;
            }
        } while (ch != 3);



    }


    private void publishArticle(String title,String content){

        try {
            dbm.publishArticle(title,loggedUser,content);
            articles.clear();


        } catch (SQLException e) {
            System.out.println("Error publish ing the article " + e.getMessage());
        }

    }

    private void saveToDraft(String title, String content){
        try{
            dbm.saveToDrafts(title,loggedUser,content);
        }catch (Exception e){
            System.out.println("Error saving to drafts"  + e.getMessage());
        }
    }


    private void writeArticleMenu(){
        String title;
        String content = "";
        System.out.println("Title:");
        title =  scan.nextLine();
        System.out.println("Content: (  Write '!end!' on a single line to finish  ) ");
        do {
            String s = scan.nextLine();
            if(s.equals( "!end!")) break;

            content = content + s;
        }while(true);

        int ch = 0;

        do{
            System.out.println("What do you want to do with your article? \n    1.Publish\n    2.Save to drafts\n    3.Discard");
            ch = scan.nextInt();
            scan.nextLine();

            switch (ch){
                case 1 : publishArticle(title,content);
                case 2 : saveToDraft(title,content);
            }

        }while(ch != 1 && ch != 2 && ch != 3);


    }


    public void printDrafts(){

        drafts.forEach((d)->{
            System.out.println("ID: "+ d.getId());
            System.out.println("Title: "+ d.getTitle());
            System.out.println("Author: "+ d.getAuth());
            System.out.println("Published at: "+ d.getDate());
            System.out.println("Preview: "+ d.getContent().substring(0,min(d.getContent().length()-1,20)));
            System.out.println("-----------------");
        });

    }

    private Drafts readDraft(int id){
        for(Drafts d : drafts){
            if (d.getId() == id){

                return  d;
            }
        }

        return null;
    }

    private void previewDraft(){
        int id;
        Drafts d;
        do {
            System.out.println("Please enter the draft id :");

            id = scan.nextInt();
        }while ( (d = readDraft(id)) == null);

        System.out.println("ID: "+ d.getId());
        System.out.println("Title: "+ d.getTitle());
        System.out.println("Author: "+ d.getAuth());
        System.out.println("Published at: "+ d.getDate());
        System.out.println("Preview: "+ d.getContent().substring(0,min(d.getContent().length()-1,20)));
        System.out.println("-----------------");


    }

    private void updateDraft(){
        int ch = 0;
        Drafts d;

        int id;

        do {
            System.out.println("Please enter the draft id :");

            id = scan.nextInt();
        }while ( (d = readDraft(id)) == null);


        do {
            System.out.println("What do you want to change?\n 1. The title\n 2. The content\n 3. exit");

            switch (ch){
                case 1:
                    System.out.println("Enter the new title : ");
                    String s = scan.nextLine();
                    try {
                        dbm.updateDraft(id,s,loggedUser,d.getContent());
                    } catch (SQLException e) {
                        System.out.println("Error while updating draft" + e.getMessage());
                    }
                    break;
                case 2:
                    String content = "";
                    System.out.println("Enter the new content : (  Write '!end!' on a single line to finish  ) ");
                    do {
                        s = scan.nextLine();
                        if(s.equals( "!end!")) break;

                        content = content + s;
                    }while(true);
                    try {
                        dbm.updateDraft(id,d.getTitle(),loggedUser,content);
                    } catch (SQLException e) {
                        System.out.println("Error while updating draft" + e.getMessage());
                    }
                    break;

            }

        }while (ch != 3);
    }

    private void viewDraftsMenu(){

        int ch = 0;

        try {
            drafts = dbm.loadDrafts(loggedUser);
            printDrafts();
        }catch (Exception e){
            System.out.println("Exception while loading drafts" + e.getMessage());
        }

        do{
            System.out.println("There are "+ drafts.size() + " drafts");
            System.out.println("Drafts menu: \n 1.Reload Drafts\n 2.Update Draft\n 3.Preview Draft\n 4.Delete Draft\n 5.exit");

            ch = scan.nextInt();

            switch (ch){
                case 1:
                    try {
                        drafts = dbm.loadDrafts(loggedUser);
                        printDrafts();
                    }catch (Exception e){
                        System.out.println("Exception while loading drafts" + e.getMessage());
                    }
                    break;
                case 2:
                    updateDraft();
                    break;
                case 3:
                    previewDraft();
                    break;

            }

        }while(ch != 4);

    }


    private void userMenu(){
        int ch = 0;

        do {
            System.out.println("Welcome," + loggedUser + "!!\n  1.Read articles\n  2.Write an article\n  3.View your drafts\n  4.exit");
            ch = scan.nextInt();
            scan.nextLine();

            switch (ch){
                case 1 : articlesMenu();
                case 2 : writeArticleMenu();
                case 3 : viewDraftsMenu();
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
            System.out.println("Welcome! \n1.register\n2.login\n3.view published articles\n4.Print this menu again");
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
