package com.example.domagoj.radiostudentapp;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.domagoj.radiostudentapp.pojos.SviraloRoot;
import com.example.domagoj.radiostudentapp.pojos.SviraloRow;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.jsoup.Jsoup;
import org.jsoup.helper.StringUtil;

import java.io.IOException;
import java.util.List;
import java.util.StringJoiner;

/**
 * Created by User on 2/28/2017.
 */

public class SviraloFragment extends Fragment {
    private static final String RS_PLAY_LIST_URI ="http://www.radiostudent.hr/wp-admin/admin-ajax.php?action=rsplaylist_api";

    private Button refresh;
    private View view;
    private ProgressDialog mProgressDialog;
    private TextView textView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Log.i("importFomJson", String.format("onCreateView: "));
        view = inflater.inflate(R.layout.sviralo_fragment,container,false);

        refresh = (Button) view.findViewById(R.id.refresh);
        // Execute Description AsyncTask
        refresh.setOnClickListener(v -> new Description().execute());

        textView = (TextView) view.findViewById(R.id.listSviralo);
        textView.setText(" idem po JSON ");
        /*
        String json = readJSONFeed(RS_PLAY_LIST_URI);
        Log.i("importFomJson", String.format("NJSON: " + json));
        textView.setText(json);
        */

        /*
        try {
            String data = Jsoup.connect(RS_PLAY_LIST_URI).ignoreContentType(true).execute().body();
            textView.setText(data);
        } catch (IOException e) {
            textView.setText("Jsoup: IOException");
        }

        String str = null;
        try
        {
            //str = downloadUrl(new URL(RS_PLAY_LIST_URI));
            textView.setText(str);
        }
        catch (Throwable e) {
            Log.i("importFomJson", "Throwable e");
            textView.setText("Throwable e");
        }

        /*catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        */
        return view;
    }

    // Description AsyncTask
    private class Description extends AsyncTask<Void, Void, String> {

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mProgressDialog = new ProgressDialog(view.getContext());
        mProgressDialog.setTitle("Android Basic JSoup Tutorial");
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.show();
    }

    @Override
    protected String doInBackground(Void... params) {
        String desc = null;
        try {
            // Connect to the web site
            desc = Jsoup.connect(RS_PLAY_LIST_URI).ignoreContentType(true).execute().body();
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
        SviraloRoot sviraloRoot = gson.fromJson(result, SviraloRoot.class);

        textView.setText(joinRows(sviraloRoot.getRows()));
        mProgressDialog.dismiss();
    }

    private String joinRows(List<SviraloRow> rows)
    {
        StringBuilder stringBuilder = new StringBuilder();
        String delimiter = "";
        for (SviraloRow row : rows) {
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

    /**
     * Given a URL, sets up a connection and gets the HTTP response body from the server.
     * If the network request is successful, it returns the response body in String form. Otherwise,
     * it will throw an IOException.
     */
    /*
    private String downloadUrl(URL url) throws IOException {
        InputStream stream = null;
        HttpURLConnection connection = null;
        String result = null;
        try {
            connection = (HttpURLConnection) url.openConnection();
            // Timeout for reading InputStream arbitrarily set to 3000ms.
            connection.setReadTimeout(3000);
            // Timeout for connection.connect() arbitrarily set to 3000ms.
            connection.setConnectTimeout(3000);
            // For this use case, set HTTP method to GET.
            connection.setRequestMethod("GET");
            // Already true by default but setting just in case; needs to be true since this request
            // is carrying an input (response) body.
            connection.setDoInput(true);
            // Open communications link (network traffic occurs here).
            connection.connect();
            //* publishProgress(DownloadCallback.Progress.CONNECT_SUCCESS);
            int responseCode = connection.getResponseCode();
            if (responseCode != HttpsURLConnection.HTTP_OK) {
                throw new IOException("HTTP error code: " + responseCode);
            }
            // Retrieve the response body as an InputStream.
            stream = connection.getInputStream();
            //* publishProgress(DownloadCallback.Progress.GET_INPUT_STREAM_SUCCESS, 0);
            if (stream != null) {
                // Converts Stream to String with max length of 500.
                result = readStream(stream, 500);
            }
        } finally {
            // Close Stream and disconnect HTTPS connection.
            if (stream != null) {
                stream.close();
            }
            if (connection != null) {
                connection.disconnect();
            }
        }
        return result;
    }
    */
    /**
     * Converts the contents of an InputStream to a String.
     */
    /*
    public String readStream(InputStream stream, int maxReadSize)
            throws IOException, UnsupportedEncodingException {
        Reader reader = null;
        reader = new InputStreamReader(stream, "UTF-8");
        char[] rawBuffer = new char[maxReadSize];
        int readSize;
        StringBuffer buffer = new StringBuffer();
        while (((readSize = reader.read(rawBuffer)) != -1) && maxReadSize > 0) {
            if (readSize > maxReadSize) {
                readSize = maxReadSize;
            }
            buffer.append(rawBuffer, 0, readSize);
            maxReadSize -= readSize;
        }
        return buffer.toString();
    }
    */

    /*
    public String readJSONFeed(String URL) {
        StringBuilder stringBuilder = new StringBuilder();
        try (InputStream inputStream = new URL(URL).openStream())
        {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
        }
        catch (final UnknownHostException e)
        {
            addErrorLog("Ne postoji server: " + serverAdresa);
        }
        catch (final FileNotFoundException e)
        {
            addErrorLog("Nije odobren PN s oznakom: '" + pnOznaka + "'" );
        }
        catch (final Throwable e)
        {
            Log.d("importFomJson", String.format("Neispravnost: " + e.getLocalizedMessage()));
        }
        return stringBuilder.toString();
    }
        */
}
