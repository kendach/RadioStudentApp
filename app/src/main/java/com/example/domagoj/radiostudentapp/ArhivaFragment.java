package com.example.domagoj.radiostudentapp;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.domagoj.radiostudentapp.pojos.ArhivaRoot;
import com.example.domagoj.radiostudentapp.pojos.ArhivaRow;
import com.google.gson.Gson;

import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.List;

/**
 * Created by Kenda on 4/28/2018.
 */

public class ArhivaFragment extends Fragment {
    private View view;
    private ProgressDialog mProgressDialog;
    private TextView textView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.arhiva_fragment,container,false);

        Button refresh = view.findViewById(R.id.refreshArhiva);
        // Execute Description AsyncTask
        refresh.setOnClickListener(v -> new Description().execute());

        textView = view.findViewById(R.id.listArhiva);
        textView.setText(" ... arhiva ... ");
        return view;
    }

    // Description AsyncTask
    private class Description extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(view.getContext());
            //mProgressDialog.setTitle("Android Basic JSoup Tutorial");
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            String desc = null;
            try {
                // Connect to the web site
                desc = Jsoup.connect(RadioConstants.RS_PLAY_LIST_URI).ignoreContentType(true).execute().body();
            /*
            Document document = Jsoup.connect(RS_PLAY_LIST_URI).get();
            // Using Elements to get the Meta data
            Elements description = document
                    .select("meta[name=description]");
            // Locate the content attribute
            desc = description.attr("content");
            */
            } catch (IOException e) {
                e.printStackTrace();
            }
            return desc;
        }

        @Override
        protected void onPostExecute(String result) {
            // Set description into TextView
            // TextView txtdesc = (TextView) findViewById(R.id.desctxt);
            Gson gson = new Gson();
            ArhivaRoot arhivaRoot = gson.fromJson(result, ArhivaRoot.class);

            textView.setText(joinRows(arhivaRoot.getRows()));
            mProgressDialog.dismiss();
        }

        private String joinRows(List<ArhivaRow> rows)
        {
            StringBuilder stringBuilder = new StringBuilder();
            String delimiter = "";
            for (ArhivaRow row : rows) {
                stringBuilder
                        .append(delimiter)
                        .append(row.getPlayedTime())
                        .append(" ")
                        .append(row.getPlayedSong());
                delimiter = "\n";
            }
            return stringBuilder.toString();
        }
    }

}
