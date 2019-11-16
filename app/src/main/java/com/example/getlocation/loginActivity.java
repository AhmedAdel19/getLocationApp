package com.example.getlocation;

import androidx.appcompat.app.AppCompatActivity;
import com.example.getlocation.MyLocationService;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;




import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class loginActivity extends AppCompatActivity
{
  public EditText usernameText , passwordText;
    static String username , password;
    private Button login_btn;
    private ProgressDialog pDialog;


    JSONPareser jsonParser = new JSONPareser();
    private static final String LOGIN_URL = "http://www.chapeautravel.com/loginApp.php";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameText = findViewById(R.id.username_view);
        passwordText = findViewById(R.id.password_view);
        login_btn = findViewById(R.id.login_btn);


        login_btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                new AttemptLogin().execute();


//                username = usernameText.getText().toString();
//                password = passwordText.getText().toString();
//
//                if (TextUtils.isEmpty(username) || TextUtils.isEmpty(username) )
//                {
//                    Toast.makeText(loginActivity.this, "please fill the empty fields !", Toast.LENGTH_SHORT).show();
//                }
//                else if(MyLocationService.usernameJ.equals("null") || MyLocationService.passwordJ.equals("null"))
//                {
//                    Toast.makeText(loginActivity.this, "You are not a valid user !", Toast.LENGTH_SHORT).show();
//
//                }
//                else
//                    {
//                        Intent go_map = new Intent(getApplicationContext() , getLocationActivity.class);
//                        startActivity(go_map);
//                }
////                else
////                {
////
////                    //downloadJSON("http://www.chapeautravel.com/contest2.php?username="+username+"&password="+password+"");

////
//
////
////                }



            }
        });
    }


    //-------------------------------------------------------------------------------------------------------------

//    private void downloadJSON(final String urlWebService) {
//
//        class DownloadJSON extends AsyncTask<Void, Void, String> {
//
//            @Override
//            protected void onPreExecute() {
//                super.onPreExecute();
//            }
//
//
//            @Override
//            protected void onPostExecute(String result) {
//                super.onPostExecute(result);
////parse json data
//
//                try {
//
//                    String s = "";
//
//                    JSONArray jArray = new JSONArray(result);
//
//                    for (int i = 0; i < jArray.length(); i++) {
//
//                        JSONObject json = jArray.getJSONObject(i);
//
//                        s = s + "info : " + json.getString("id") + " " + json.getString("username")+ " " + json.getString("password");
//                    }
//
//
//                } catch (Exception e) {
//
//// TODO: handle exception
//
//                    Log.e("log_tag", "Error Parsing Data " + e.toString());
//
//                }
//            }
//
//            @Override
//            protected String doInBackground(Void... voids) {
//                try {
//                    URL url = new URL(urlWebService);
//                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
//                    StringBuilder sb = new StringBuilder();
//                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
//                    String json;
//                    while ((json = bufferedReader.readLine()) != null) {
//                        sb.append(json + "\n");
//                    }
//                    return sb.toString().trim();
//                } catch (Exception e) {
//                    return null;
//                }
//            }
//        }
//        DownloadJSON getJSON = new DownloadJSON();
//        getJSON.execute();
//    }



    class AttemptLogin extends AsyncTask<String, String, String>
    { /** * Before starting background thread Show Progress Dialog * */
    boolean failure = false;


        @Override
        protected void onPreExecute()
        {
            username = usernameText.getText().toString();
            password = passwordText.getText().toString();
            super.onPreExecute();

            pDialog = new ProgressDialog(loginActivity.this);
            pDialog.setMessage("Please Wait we Are check your data...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();

        }
        @Override
        protected String doInBackground(String... args) {
            // TODO Auto-generated method stub // here Check for success tag
            int success;

            try
            {
                String userN = username;
                String pass = password;
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("username", userN));
                params.add(new BasicNameValuePair("password", pass));
                Log.d("request!", "starting");
                JSONObject json = JSONPareser.makeHttpRequest( LOGIN_URL, "POST", params);
                // checking log for json response
                Log.d("Login attempt", json.toString());
                // success tag for json
                success = json.getInt(TAG_SUCCESS);
                if (success == 1)
                {
                    Log.d("Successfully Login!", json.toString());




                    Intent ii = new Intent(loginActivity.this,getLocationActivity.class);

                    // this finish() method is used to tell android os that we are done with current //activity now! Moving to other activity
                    startActivity(ii);
                    finish();
                   // Toast.makeText(loginActivity.this, json.getString(TAG_MESSAGE)+"", Toast.LENGTH_SHORT).show();
                    return json.getString(TAG_MESSAGE);
                }
                else{
                //    Toast.makeText(loginActivity.this, json.getString(TAG_MESSAGE)+"", Toast.LENGTH_SHORT).show();

                    return json.getString(TAG_MESSAGE);
                }


            } catch(JSONException e)
            {
                e.printStackTrace();
            }
            return null;

//                     MyLocationService.username_loc = username;
//                    MyLocationService.password_loc = password;
        }

        protected void onPostExecute(String message)
        {
            pDialog.dismiss();
            if (message != null)
            {
                Toast.makeText(loginActivity.this, message, Toast.LENGTH_LONG).show();
            }
        }


//                    MyLocationService.username_loc = username;
//                    MyLocationService.password_loc = password;

    }
//    protected String doInBackground(String... args)
//    { // TODO Auto-generated method stub // here Check for success tag
//         int success;
//         String username = usernameText.getText().toString();
//         String password = passwordText.getText().toString();
//         try
//         {
//             List<NameValuePair> params = new ArrayList<NameValuePair>();
//             params.add(new BasicNameValuePair("username", username));
//             params.add(new BasicNameValuePair("password", password));
//             Log.d("request!", "starting");
//             JSONObject json = jsonParser.makeHttpRequest( LOGIN_URL, "POST", params);
//             // checking log for json response
//             Log.d("Login attempt", json.toString());
//             // success tag for json
//             success = json.getInt(TAG_SUCCESS);
//             if (success == 1)
//             {
//                 Log.d("Successfully Login!", json.toString());
//                 Intent ii = new Intent(loginActivity.this,getLocationActivity.class);
//                 finish();
//                 // this finish() method is used to tell android os that we are done with current //activity now! Moving to other activity
//                 startActivity(ii);
//                 return json.getString(TAG_MESSAGE);
//             }
//             else{
//                 return json.getString(TAG_MESSAGE);
//             }
//
//
//    } catch(JSONException e)
//    {
//        e.printStackTrace();
//    }
//    return null;
//} /** * Once the background process is done we need to Dismiss the progress dialog asap * **/
//protected void onPostExecute(String message)
//{
//    if (message != null)
//    {
//        Toast.makeText(loginActivity.this, message, Toast.LENGTH_LONG).show();
//    }
//}
}




