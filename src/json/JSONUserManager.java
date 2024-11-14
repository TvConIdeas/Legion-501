package json;

import main.Game;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import users.User;

import javax.print.attribute.standard.Fidelity;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Clase encargada de gestionar el archivo json de usuarios.
 */
public class JSONUserManager {

    // ====================> ATRIBUTOS <====================

    // ====================> CONTRUCTOR <====================
    public JSONUserManager() {
    }

    // ====================> METODOS <====================
    /** userToFile() ==> Pasar User al archivo. */
    public void userToFile(User user){
        JSONArray jsonArray = ReadWriteOperations.read(Game.nomJSON); // Pasar el contenido a un JSONArray

        JSONObject userJson = serialize(user); // Serializar user
        jsonArray.put(userJson); // Agregarlo a array

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("users", jsonArray); // Pasar

        ReadWriteOperations.write(Game.nomJSON, jsonObject);
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
        Set<User> users = new HashSet<>();

        try {
            // Obtener JSONArray con contenido del archivo
            JSONArray usersArray = ReadWriteOperations.read(Game.nomJSON);

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
}
