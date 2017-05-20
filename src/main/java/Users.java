/**
 * Created by x2009 on 20.05.2017.
 */
public class Users {

    private String nume;
    private String prenume;
    private String email;
    private String username;
    private String password;

    public Users(String nume, String prenume, String email, String username, String password) {
        this.nume = nume;
        this.prenume = prenume;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public String getNume() {

        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
