package roman.ironbank.domain.users;

public class Admin {
    private Integer idInDb;
    private String email;
    private String pass;

    /**
     Constructors
     */
    public Admin() {
    }

    public Admin(String email, String pass) {
        this.email = email;
        this.pass = pass;
    }

    public Admin(Integer idInDb, String email, String pass) {
        this.idInDb = idInDb;
        this.email = email;
        this.pass = pass;
    }

    public Integer getAdmin_id() {
        return idInDb;
    }

    public String getEmail() {
        return email;
    }

    public String getPass() {
        return pass;
    }

    public void setId(Integer id){
        idInDb = id;
    }

    public boolean clear() {
        pass = "";
        return true;
    }
}
