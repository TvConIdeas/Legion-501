package users;

public class User {

    // ====================> ATRIBUTOS <====================
    private String name;
    private String password;
    private int bestScore;
    private boolean isAdmin;

    // ====================> CONTRUCTOR <====================
    public User(String name, String password, boolean isAdmin){
        this.name = name;
        this.password = password;
        this.isAdmin = isAdmin;
        bestScore = 0;
    }

    // ====================> GET | SET <====================

    // ====================> METODOS <====================
}
