package com.example.student.fitabs;

import android.os.AsyncTask;

/**
 * Created by KarishoK27 on 17.12.7.
 */

public class connectTask extends AsyncTask<String,String,ChatClient> {

    @Override
    protected ChatClient doInBackground(String... message) {

        //we create a Client object and
        ChatClient client = new ChatClient(new ChatClient.OnMessageReceived() {
            @Override
            //here the messageReceived method is implemented
            public void messageReceived(String message) {
                //this method calls the onProgressUpdate
                publishProgress(message);
            }
        });
        client.run();

        return null;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
    }

}