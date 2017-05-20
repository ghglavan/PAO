import java.util.Date;

/**
 * Created by x2009 on 20.05.2017.
 */
public class Drafts {

    private int id;
    private String title;
    private String content;
    private String auth;
    private Date date;

    public Drafts(int id, String title, String content, String auth, Date date) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.auth = auth;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
