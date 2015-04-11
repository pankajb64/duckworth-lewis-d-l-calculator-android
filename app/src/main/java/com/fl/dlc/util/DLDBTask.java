package com.fl.dlc.util;

import android.os.AsyncTask;
import android.widget.TextView;

public class DLDBTask extends AsyncTask<Void, Void, String> {

    private TextView textView;
    private String waitText;

    public DLDBTask(TextView textView, String waitText) {

        this.textView = textView;
        this.waitText = waitText;
    }

    @Override
    protected void onPreExecute() {

        textView.setText(waitText);
    }

    @Override
    protected String doInBackground(Void... params) {

        return DLUtil.calculateResult();
    }

    @Override
    protected void onPostExecute(String result) {

        if (result == null) {
            result = "";
        }

        textView.setText(result);
    }
}
