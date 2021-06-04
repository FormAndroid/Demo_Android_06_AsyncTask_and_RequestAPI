package be.bxl.formation.demo_06_asynctask_and_requestapi.webapi;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import be.bxl.formation.demo_06_asynctask_and_requestapi.models.WeatherData;

public class RequestWeatherTask extends AsyncTask<String, Void, WeatherData> {

    //region Event
    public interface WeatherListener {
        void onData(WeatherData data);
    }

    private WeatherListener listener;

    public void setWeatherListener(WeatherListener listener) {
        this.listener = listener;
    }
    //endregion

    private final String URL_BASE = "https://api.openweathermap.org/data/2.5/weather?q=__city__&appid=c3fa448b20d4333b499f552522c429d3&units=metric&lang=fr";

    @Override
    protected WeatherData doInBackground(String... cities) {
        if (cities == null || cities.length != 1 || cities[0].trim().equals("")) {
            throw new RuntimeException();
        }

        //region Request WebAPI
        String resquestResult = null;
        HttpURLConnection connection = null;

        try {
            // Création de l'url
            URL url = new URL(URL_BASE.replace("__city__", cities[0]));

            // Envois de la requete
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            // Gestion des erreurs de la requete
            if(connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return null;
            }

            // Traiter les données recu
            InputStreamReader streamReader = new InputStreamReader(connection.getInputStream());
            BufferedReader reader = new BufferedReader(streamReader);

            StringBuilder data = new StringBuilder();
            String line;
            while((line= reader.readLine()) != null) {
                data.append(line);
                data.append("\n");
            }
            reader.close();
            streamReader.close();

            resquestResult = data.toString();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if(connection != null) {
                connection.disconnect();
            }
        }
        //endregion

        //region Construction de l'objet resultat
        WeatherData result = null;

        if(resquestResult != null){
            try {
                JSONObject json = new JSONObject(resquestResult);

                String city = json.getString("name");

                JSONObject main = json.getJSONObject("main");
                double temp = main.getDouble("temp");

                result = new WeatherData(city, temp);
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
        }
        //endregion

        return result;
    }

    @Override
    protected void onPostExecute(WeatherData weatherData) {
        if (listener != null) {
            listener.onData(weatherData);
        }
    }
}
