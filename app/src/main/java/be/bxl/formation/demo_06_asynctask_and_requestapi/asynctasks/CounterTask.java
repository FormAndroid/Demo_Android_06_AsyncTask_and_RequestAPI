package be.bxl.formation.demo_06_asynctask_and_requestapi.asynctasks;

import android.os.AsyncTask;
import android.util.Log;

public class CounterTask extends AsyncTask<Integer, Integer, String> {
    //region Event
    public interface EventMessage {
        void onMessage(String message);
    }

    public interface EventCounter {
        void onProgressCount(int count);
    }

    private EventMessage eventMessage;
    private EventCounter eventCounter;

    public void setEventMessage(EventMessage eventMessage) {
        this.eventMessage = eventMessage;
    }

    public void setEventCounter(EventCounter eventCounter) {
        this.eventCounter = eventCounter;
    }
    //endregion


    @Override
    protected String doInBackground(Integer... valeursInitial) {
        if(valeursInitial == null || valeursInitial.length != 1) {
            throw new RuntimeException();
        }

        // On recupere la valeur initial
        int counter = valeursInitial[0];

        while(counter > 0) {
            publishProgress(counter);
            counter--;

            try {
                Thread.sleep(500);
            }
            catch (InterruptedException e) {
                Log.e("DEMO_ASYNC", "Erreur durant le sleep", e);
            }
        }

        // Envois du parametre de sortie
        return "BOUM !!!";
    }

    @Override
    protected void onPreExecute() {
        if(eventMessage != null) {
            eventMessage.onMessage("Debut du compte a rebours");
        }
    }

    @Override
    protected void onPostExecute(String s) {
        if(eventMessage != null) {
            eventMessage.onMessage(s);
        }

        if(eventCounter != null) {
            eventCounter.onProgressCount(0);
        }
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        int count = values[0];

        if(eventCounter != null) {
            eventCounter.onProgressCount(count);
        }
    }
}
