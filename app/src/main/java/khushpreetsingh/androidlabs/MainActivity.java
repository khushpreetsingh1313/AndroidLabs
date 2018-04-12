package khushpreetsingh.androidlabs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {

Button buttonToolbar;
    protected static final String ACTIVITY_NAME = "StartActivity";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button2 = (Button)findViewById(R.id.button2);
        buttonToolbar = findViewById(R.id.buttonToolbar);

        button2.setOnClickListener(new View.OnClickListener() {
           @Override
            public void onClick(View v) {
                Intent secondIntent = new Intent(MainActivity.this,
                        ListItemsActivity.class);
                startActivityForResult(secondIntent,50);
                //Log.i(ACTIVITY_NAME, “Returned to MainActivity.onActivityResult”);
            }


        });

        Button btn = (Button)findViewById(R.id.button3);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent secondIntent = new Intent(MainActivity.this,
                        ChatWindow.class);
                startActivityForResult(secondIntent, 50);
                //Log.i(ACTIVITY_NAME, “Returned to MainActivity.onActivityResult”);
            }
        });

        Button weather =(Button)findViewById(R.id.weather_app);

        weather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent secondIntent = new Intent(MainActivity.this,WeatherForecast.class);
                startActivityForResult(secondIntent,52);
            }
        });
buttonToolbar.setOnClickListener(e->{
    Intent intent = new Intent(MainActivity.this,TestToolbar.class);
      startActivity(intent);
});

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
