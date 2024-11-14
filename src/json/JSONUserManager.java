package json;

import main.Game;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import users.User;

import javax.print.attribute.standard.Fidelity;
import java.io.File;
import java.util.*;

/**
 * Clase encargada de gestionar el archivo json de usuarios.
 */
public class JSONUserManager {

    // ====================> ATRIBUTOS <====================
    public final static String nomJSON = "users.json";


    // ====================> CONTRUCTOR <====================
    public JSONUserManager() {
    }

    // ====================> METODOS <====================
    /** userToFile() ==> Pasar User al archivo. */
    public void userToFile(User user){
        JSONArray jsonArray = ReadWriteOperations.read(nomJSON); // Pasar el contenido a un JSONArray

        JSONObject userJson = serialize(user); // Serializar user
        jsonArray.put(userJson); // Agregarlo a array

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("users", jsonArray); // Pasar

        ReadWriteOperations.write(nomJSON, jsonObject);
    }

    public void usersSetToFile(Set<User> users){
        JSONArray jsonArray = new JSONArray();
        for(User user : users){
            jsonArray.put(serialize(user));
        }
        ReadWriteOperations.write(nomJSON, jsonArray);
    }

    /** serialize() ==> Pasar de User a JSONObject. */
    public JSONObject serialize(User user){
        JSONObject jsonObject = null;

        try {
            jsonObject = new JSONObject();
            jsonObject.put("name", user.getName());
            jsonObject.put("password", user.getPassword());
            jsonObject.put("best score", user.getBestScore());
            jsonObject.put("is admin", user.isAdmin());

        } catch (JSONException e){
            e.printStackTrace();
        }

        return jsonObject;
    }

    /** fileToUser() ==> Pasar del archivo a User. */
    public Set<User> fileToUsers(){
        Set<User> users = new LinkedHashSet<>();

        try {
            // Obtener JSONArray con contenido del archivo
            JSONArray usersArray = ReadWriteOperations.read(nomJSON);

            // Convertir cada elemento en un JSONObject
            for(int i = 0; i< usersArray.length(); i++){
                JSONObject userJson = usersArray.getJSONObject(i);
                users.add(deserialize(userJson)); // Deserializar JSONObject a User
            }

        } catch (JSONException e){
            e.printStackTrace();
        }

        return users;
    }

    /** deserialize() ==> Pasar de JSONObject a User. */
    public User deserialize(JSONObject jsonObject){
        User user = new User();

        try {
            user.setName(jsonObject.getString("name"));
            user.setPassword(jsonObject.getString("password"));
            user.setBestScore(jsonObject.getInt("best score"));
            user.setAdmin(jsonObject.getBoolean("is admin"));

        } catch (JSONException e){
            e.printStackTrace();
        }

        return user;
    }

    /** isUsernameAvailable() ==> Method que verifica que no se repita el nombre. */
    public boolean isUsernameAvailable(String username){
        Set<User> users = fileToUsers(); // Pasar archivo a coleccion para comparar

        for(User user : users){
            if(user.getName().equals(username)){ // Comparar nombre de cada usuario con nombre requerido
                return false; // Retornar falso si ya hay uno igual
            }
        }
        return true; // Retornar true si no hay uno igual
    }

    public void overwriteUser(User newUser){
        Set<User> users = new HashSet<>();
        users = fileToUsers();

        for(User user : users){
            if(user.getName().equals(newUser.getName())){
                users.remove(user);
                users.add(newUser);
            }
        }

//        for(int i=0; i<users.size(); i++){
//            if(users.get(i).getName().equals(newUser.getName())){
//                users.add(i, newUser);
//                user
//            }
        }

        /*public void searchUser(String name){

        }*/
}


