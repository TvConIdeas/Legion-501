package json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.*;

/**
 *Clase con métodos estáticos para leer y escrbir en el archivo.
 */
public final class ReadWriteOperations {

    public ReadWriteOperations() {
    }

    /** write() ==> Escribir en el archivo. */
    public static void write(String file, JSONObject jsonObject){
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(jsonObject.toString()); // Pasar el JSONObject al archivo
            fileWriter.close();

        } catch (IOException e){ // Capturar excepcion de entrada/salida
            e.printStackTrace();

        }
    }


    /** read() ==> Leer el archivo. */
    public static JSONArray read(String file){
        JSONArray jsonArray = new JSONArray();
        File fileObj = new File(file);

        // Si el archivo no existe, se crea vacío
        /*if (!fileObj.exists()) {
            try {
                fileObj.createNewFile(); // Crea el archivo vacío si no existe
            } catch (IOException e) {
                e.printStackTrace();
            }

            return jsonArray; // En caso de que el archivo no exista, se devuelve el JSONArray vacío
        }*/

        try {
            // Leer el archivo
            FileReader fileReader = new FileReader(file);
            JSONTokener jsonTokener = new JSONTokener(fileReader);
            // Convertir contenido del archivo a JSONObject
            JSONObject jsonObject = new JSONObject(jsonTokener);

            if(jsonObject.has("users")){ // Ver si existe clave 'users'
                jsonArray = jsonObject.getJSONArray("users");
            }
            fileReader.close();

        } catch (FileNotFoundException e) { // Capturar excepcion de archivo no encontrado
            e.printStackTrace();

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return jsonArray;
    }
}
