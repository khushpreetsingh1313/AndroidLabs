package khushpreetsingh.androidlabs;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import org.xmlpull.v1.XmlPullParser;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherForecast extends Activity {

    protected static final String ACTIVITY_NAME = "WeatherForecast";

    ProgressBar progressBar;
    TextView Min_temp;
    TextView Max_temp;
    TextView Current_temp;
    TextView windspeed;
    ImageView Weather_Imageview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);

        Min_temp = (TextView) findViewById(R.id.min_temp);
        Max_temp = (TextView) findViewById(R.id.max_temp);
        Current_temp = (TextView) findViewById(R.id.current_temp);
        windspeed = (TextView) findViewById(R.id.wind_speed);
        Weather_Imageview = (ImageView) findViewById(R.id.weather_img);

        progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);        // seting visibilty of progress bar
        ForecastQuery fQuery = new ForecastQuery();  // creating object for inner class
        fQuery.execute("http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=d99666875e0e51521f0040a3d97d0f6a&mode=xml&units=metric");
    }


    protected static Bitmap getImage(URL url) {

        HttpURLConnection iconConn = null;
        try {
            iconConn = (HttpURLConnection) url.openConnection();
            iconConn.connect();
            int response = iconConn.getResponseCode();
            if (response == 200) {
                return BitmapFactory.decodeStream(iconConn.getInputStream());
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (iconConn != null) {
                iconConn.disconnect();
            }
        }
    }

    public boolean fileExistance(String fname) {
        File file = getBaseContext().getFileStreamPath(fname);
        return file.exists();
    }

    public class ForecastQuery extends AsyncTask<String, Integer, String> {

        String mintemp;
        String maxtemp;
        String current_temp;
        String iconName;
        String wind;
        Bitmap current_temperature;

        @Override
        protected String doInBackground(String... string) {
            Log.i(ACTIVITY_NAME, "doInBackground is started");
            try {
                URL url = new URL(string[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection(); // establishing connection
                InputStream stream = conn.getInputStream();
                XmlPullParser parser = Xml.newPullParser(); // creating parser object to parse xml
                parser.setInput(stream, null);

                while (parser.next() != XmlPullParser.END_DOCUMENT) {
                    if (parser.getEventType() != XmlPullParser.START_TAG) {
                        continue;
                    }
                   else if (parser.getName().equals("temperature")) {
                        current_temp = parser.getAttributeValue(null, "value");
                        publishProgress(25);
                        mintemp = parser.getAttributeValue(null, "min");
                        publishProgress(50);
                        maxtemp = parser.getAttributeValue(null, "max");
                        publishProgress(75);
                    }
                    else if(parser.getName().equals("speed")) {
                        wind = parser.getAttributeValue(null, "value");
                    }
                  else  if (parser.getName().equals("weather")) {
                        iconName = parser.getAttributeValue(null, "icon");
                        String iconFile = iconName+".png";
                        if (fileExistance(iconFile)) {
                            FileInputStream fis = null;
                            try {
                                fis = openFileInput(iconFile);
                               fis = new FileInputStream(getBaseContext().getFileStreamPath(iconFile));

                            }
                            catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                            current_temperature = BitmapFactory.decodeStream(fis);


                            Log.i(ACTIVITY_NAME, "Image is in file");
                        } else {

                            URL imageURL = new URL("http://openweathermap.org/img/w/" + iconName + ".png");
                            current_temperature = getImage(imageURL);
                            FileOutputStream outputStream = openFileOutput(iconName + ".png", Context.MODE_PRIVATE);
                            current_temperature.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
                            outputStream.flush();
                            outputStream.close();
                            Log.i(ACTIVITY_NAME, "downloading new image");
                        }
                        Log.i(ACTIVITY_NAME, "file name="+iconFile);
                        publishProgress(100);  // publishing progress for progress bar
                       // Log.i(ACTIVITY_NAME, "publishprogress"+progressBar);
                    }
                }
            } catch(Exception e) {
                e.printStackTrace();
            }
            return "value";
        }

        public void onProgressUpdate(Integer... data) {
            progressBar.setVisibility(View.VISIBLE);        // seting visibilty of progress bar

            progressBar.setProgress(data[0]);  // progress for progressbar


        }

        public void onPostExecute(String result) {

            Current_temp.setText("current temperture:  " + current_temp + " °C"); // passing values to textview
            Min_temp.setText("min temp:  " + mintemp + " °C");
            Max_temp.setText("max temp:  " + maxtemp + " °C");
            windspeed.setText("Wind speed:  " + wind);
            Weather_Imageview.setImageBitmap(current_temperature);
            progressBar.setVisibility(View.INVISIBLE);        // seting visibilty of progress bar

        }


    }
}
