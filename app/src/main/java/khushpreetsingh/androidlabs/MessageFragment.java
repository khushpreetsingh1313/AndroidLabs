package khushpreetsingh.androidlabs;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by khushpreetsingh on 2018-03-27.
 */

public class MessageFragment extends Fragment {

    private String mItem;
        public void onCreate(Bundle b)
        {
            super.onCreate(b);
            Bundle infoPassed = getArguments();

           // userText = infoPassed.getString("UserInput");
        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {




            // Show the dummy content as text in a TextView.




            View gui = inflater.inflate(R.layout.activity_phone_fragment ,container, false);
            TextView tv =(TextView) gui.findViewById(R.id.id_here);
            TextView tv1 = (TextView) gui.findViewById(R.id.meassage_here);
            Button btn = (Button) gui.findViewById(R.id.Delete_message_button);
           // tv.setText("To:" + userText);
            return gui;
        }
    }

