package com.wassana.wassana_restaurant;

import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    //Explicit
    private UserTable objUserTABLE;
    private FoodTABLE objFoodTABLE;
    private OrderTABLE objOrderTABLE;
    private  MySQLite mySQLite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Connected SQLite
        connectedSQLite();

        //Test Add value
        testAddValue();

        //Synchronize MySQL to SQLite
        synAndDelete();

        //Request SQLite
        mySQLite = new MySQLite(this);



    }   //OnCreate
    private void synAndDelete(){
        SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(MySQLiteOpenHelper.DATABASE_NAME,
                MODE_PRIVATE,null);
        sqLiteDatabase.delete(MySQLite.user_table,null,null);
        MySynJSON mySynJSON = new MySynJSON();
        mySynJSON.execute();
    }//SynAndDelete

    //Create Inner Class for Connected JSON
    public class MySynJSON extends AsyncTask<Void, Void, String>{

        @Override
        protected String doInBackground(Void... voids){
            try {
                String strURL = "http://www.csclub.ssru.ac.th/s56122201003/CSC4202/php_get_userTABLE.php";
                OkHttpClient okHttpClient = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                Request request = builder.url(strURL).build();
                Response response = okHttpClient.newCall(request).execute();
                return response.body().string();
            } catch (Exception e){
                Log.d("Satien","doInBack ==> " + e.toString());
                return null;
            }

        }//doInBackground

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("Satien", "strJSON ==>" + s);
            try {
                JSONArray jsonArray = new JSONArray(s);
                for (int i=0; i<jsonArray.length(); i++){

                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String strUser = jsonObject.getString(MySQLite.column_user);
                    String strPassword = jsonObject.getString(mySQLite.column_password);
                    String strName = jsonObject.getString(mySQLite.column_name);
                    mySQLite.addNewUser(strUser, strPassword, strName);
                }//for
                Toast.makeText(MainActivity.this,"Synchronize mySQL to SQLite Finish",
                        Toast.LENGTH_SHORT).show();
            }catch (Exception e){
                Log.d("Satien", "onPost ==> " + e.toString());
            }
        }// OnPostExcute
    }//MySynJSON Class

    private void testAddValue(){
        objUserTABLE.addNewUser("testUser", "testPass", "testName");
        objFoodTABLE.addNewFood("testFood", "testSource", "testPrice");
        objOrderTABLE.addOrder("testOfficer", "testDesk", "testFood", "testItem");
    }//testAddValue

    private void connectedSQLite() {
        objUserTABLE = new UserTable(this);
        objFoodTABLE = new FoodTABLE(this);
        objOrderTABLE = new OrderTABLE(this);
    }   //ConnectedSQLite
}   //Main Class
