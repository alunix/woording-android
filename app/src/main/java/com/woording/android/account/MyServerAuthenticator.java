package com.woording.android.account;

import com.woording.android.NetworkCaller;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class MyServerAuthenticator implements IServerAuthenticator {

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static Map<String, String> mCredentialsRepo;

    static {
        Map<String, String> credentials = new HashMap<String, String>();
        credentials.put("demo@example.com", "demo");
        credentials.put("foo@example.com", "foobar");
        credentials.put("user@example.com", "pass");
        mCredentialsRepo = Collections.unmodifiableMap(credentials);
    }

    @Override
    public String signUp(String email, String username, String password) {
        // TODO: register new user on the server and return its auth token
        return null;
    }

    @Override
    public String signIn(String username, String password) {
        // TODO: 12-17-2015 Get Auth token from server
        String authToken = null;
        try {
            // Setup connection
            URL url = new URL(NetworkCaller.API_LOCATION + "/authenticate");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(15000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            urlConnection.setUseCaches(true);
            urlConnection.setInstanceFollowRedirects(false);
            // Set the content-type as json --> Important
            urlConnection.setRequestProperty("Content-Type", "application/json;charset=utf-8");

            // Now create the JSONObject
            JSONObject data = new JSONObject();
            data.put("username", username);
            data.put("password", password);
            // And send the data
            OutputStream output = urlConnection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(output, "UTF-8"));
            writer.write(data.toString());
            writer.flush();
            writer.close();
            output.close();
            // And now connect to the server
            urlConnection.connect();

            // Now check the response code
            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = urlConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder json = new StringBuilder();
                String inputLine = "";

                while ((inputLine = bufferedReader.readLine()) != null) {
                    json.append(inputLine);
                }

                JSONObject response = new JSONObject(json.toString());
                authToken = response.getString("token");

                inputStream.close();
            }
            urlConnection .disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return authToken;
    }
}
