package khushpreetsingh.androidlabs;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

import static khushpreetsingh.androidlabs.R.id.imageButton1;

public class ListItemsActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_items);


        ImageButton button3 = (ImageButton) findViewById(imageButton1);
        button3.setOnClickListener(e ->
                {
                    Intent secondIntent = new Intent(ListItemsActivity.this,
                            ListItemsActivity.class);
                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                        startActivityForResult(takePictureIntent, 326);
                    }
                }
        );


        Switch switch_button = (Switch) findViewById(R.id.switch1);

        switch_button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    CharSequence text  = getResources().getString(R.string.switchOn);
                    Toast.makeText(getApplicationContext(), text , Toast.LENGTH_SHORT).show();
                } else {
                    CharSequence text  = getResources().getString(R.string.switchOff);

                    Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
                }
            }
        });


        CheckBox checkBox = (CheckBox) findViewById(R.id.checkBox1);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()  {

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked ){
                if(isChecked) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ListItemsActivity.this);
                    builder.setMessage(R.string.dialog_message)
                            .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Intent resultIntent = new Intent();
                                    resultIntent.putExtra("Response", "Here is my response");
                                    setResult(Activity.RESULT_OK, resultIntent);
                                    finish();

                                }
                            })
                            .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                 checkBox.setChecked(false);
                                        }
                                    }
                            )
                            .show();

                }}});




}


    protected static final String ACTIVITY_NAME = "StartActivity";
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode ==326 && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            ImageButton button3 = (ImageButton)findViewById(imageButton1);

            Bitmap imageBitmap = (Bitmap) extras.get("data");
            button3.setImageBitmap(imageBitmap);
        }
    }


}
