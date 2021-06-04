package be.bxl.formation.demo_06_asynctask_and_requestapi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import be.bxl.formation.demo_06_asynctask_and_requestapi.asynctasks.CounterTask;
import be.bxl.formation.demo_06_asynctask_and_requestapi.webapi.RequestWeatherTask;

public class MainActivity extends AppCompatActivity {

    TextView tvCounter, tvWeather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvCounter = findViewById(R.id.tv_main_counter);

        // Création de la taches asynchrone
        CounterTask counterTask = new CounterTask();

        // Ajouter des listener sur les events de ma tache
        counterTask.setEventMessage(msg -> {
            Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
        });

        counterTask.setEventCounter(count -> {
            tvCounter.setText(String.valueOf(count));
        });

        // Démarrage de la tache asynchrone (une par une)
        // counterTask.execute(999);

        // Démarrage de la tache asynchrone en autorisant plusieur tache en meme temps
        counterTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,999);


        //-----------------------------------------------------
        tvWeather = findViewById(R.id.tv_main_weather);

        RequestWeatherTask requestWeatherTask = new RequestWeatherTask();

        requestWeatherTask.setWeatherListener(data -> {
            tvWeather.setText(data.getCity() + " " + data.getTemp() + "°c");
        });

        requestWeatherTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,"Bruxelles, CA");
    }
}