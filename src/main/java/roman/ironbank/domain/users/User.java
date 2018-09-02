package roman.ironbank.domain.users;

public class User {
    private Integer idInDb;
    private String name;
    private String pass;
    private String email;

    /**
     * Constructor without arguments
     */
    public User() {
        idInDb = 0;
        name = "";
        email = "";
        pass = "";
    }

    /**
     * Initializes a newly created object. Fills all fields.
     * @param idInDb    id in the database
     * @param name  user name
     * @param email user email
     * @param pass  user password
     */
    public User(Integer idInDb, String name, String pass, String email) {
        this.idInDb = idInDb;
        this.name = name;
        this.pass = pass;
        this.email = email;
    }
    /**
     * Safe Initialize (without password)
     * @param idInDb    id in the database
     * @param name  user name
     * @param email user email
     */
    public User(Integer idInDb, String name, String email) {
        this.idInDb = idInDb;
        this.name = name;
        this.email = email;
    }
    /**
     * Constructor fills all fields except one (id in the database)
     * @param name  user name
     * @param email user email
     * @param pass  user password
     */
    public User(String name, String email, String pass) {
        this.name = name;
        this.email = email;
        this.pass = pass;
    }

    public Integer getIdInDb() {
        return idInDb;
    }
    /**
     * Setter
     * @param idInDb    id in the database
     */
    public void setIdInDb(Integer idInDb) {
        this.idInDb = idInDb;
    }

    /**
     * Getter
     * @return user name
     */
    public String getName() {
        return name;
    }

    /**
     * Getter
     * @return  user password
     */
    public String getPass() {
        return pass;
    }
    /**
     * Getter
     * @return  user email
     */

    public String getEmail() {
        return email;
    }

    public boolean clear() {
        pass = "";
        return true;
    }
}
