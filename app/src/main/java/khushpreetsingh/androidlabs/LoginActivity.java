package khushpreetsingh.androidlabs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends Activity {

    protected static final String ACTIVITY_NAME = "StartActivity";
private static final String userName = "file";
private static final String loginName = "loginName";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button button2 = (Button)findViewById(R.id.button1);
        EditText editText = (EditText) findViewById((R.id.editText));

        SharedPreferences prefs = getSharedPreferences(userName , Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = prefs.edit(); //edit the file

        editText.setText(prefs.getString(loginName, "email@domain.com"));



        button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    edit.putString(loginName, editText.getText().toString());
                    edit.commit();
                    Intent secondIntent = new Intent(LoginActivity.this,
                            MainActivity.class);
                    startActivityForResult(secondIntent, 275);
                }
            });

//        button2.setOnClickListener( e ->
//                {
//                    Intent secondIntent = new Intent(MainActivity.this,
//                            ListItemsActivity.class);
//                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
//                        startActivityForResult(takePictureIntent, 326);
//                    }
//
//
//                }
//        );
        }


    @Override
    protected void onStart() {




        Log.i(ACTIVITY_NAME, "In onCreate()");
        super.onStart();
    }

    @Override
    protected void onResume() {
        Log.i(ACTIVITY_NAME, "In onResume()");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.i(ACTIVITY_NAME, "In onPause()");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.i(ACTIVITY_NAME, "In onStop()");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.i(ACTIVITY_NAME, "In onDestroy()");
        super.onDestroy();
    }


}

