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
            fileWriter.write(jsonObject.toString(4)); // Pasar el JSONObject al archivo
            fileWriter.close();

        } catch (IOException e){ // Capturar excepcion de entrada/salida
            e.printStackTrace();

        }
    }

    public static void write(String file, JSONArray jsonArray) {
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(jsonArray.toString(4));
            fileWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /** read() ==> Leer el archivo. */
    public static JSONArray read(String file){
        JSONArray jsonArray = new JSONArray();

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
