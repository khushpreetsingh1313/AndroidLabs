package khushpreetsingh.androidlabs;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class MessageFragment extends Fragment {
    TextView tv_message;
    TextView tv_id;
    Button delete_button;
    SQLiteDatabase db;
    ChatWindow cw;
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        cw = new ChatWindow();
        db = ChatWindow.helper.getWritableDatabase();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View page;
        page = inflater.inflate(R.layout.activity_phone_fragment, container, false);


        tv_message = page.findViewById(R.id.message_here);
        tv_id = page.findViewById(R.id.id_here);
        delete_button = page.findViewById(R.id.Delete_message_button);

        Bundle b = this.getArguments();


        tv_message.setText(b.getString("message"));
        Log.i("Message selected",b.getString("message") );
        tv_id.setText(b.getString("id"));
        Log.i("Message ID ",b.getString("id") );


        delete_button.setOnClickListener(e->{

            if(b.getBoolean("isTablet")){
                db.delete(ChatDatabaseHelper.TABLE_NAME,ChatDatabaseHelper.KEY_ID +"="+b.getString("id"),null);
                ChatWindow.list.clear();
                ChatWindow.moveCursor();

                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.remove(this);
                ft.commit();
            }
            else{
                Intent resultIntent = new Intent();
                resultIntent.putExtra("sendingID",b.getString("id") );
                getActivity().setResult(600, resultIntent);
                getActivity().finish();}
        });
        return page;
    }
}