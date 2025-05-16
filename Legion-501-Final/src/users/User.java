package users;

import java.util.Objects;

public class User {

    // ====================> ATRIBUTOS <====================
    private String name;
    private String password;
    private int bestScore;
    private boolean isAdmin;

    // ====================> CONTRUCTOR <====================
    public User(){}

    public User(String name, String password){
        this.name = name;
        this.password = password;
        this.isAdmin = false;
        bestScore = 0;
    }

    // ====================> GET | SET <====================
    public int getBestScore() {
        return bestScore;
    }

    public void setBestScore(int bestScore) {
        this.bestScore = bestScore;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    // ====================> METODOS <====================
    @Override
    public String toString() {
        return "User{" +
                "bestScore=" + bestScore +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", isAdmin=" + isAdmin +
                '}';
    }

    // Comparar por nombre
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof User user)) return false;
        return Objects.equals(name, user.name); // Compara por nombre
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }
}
