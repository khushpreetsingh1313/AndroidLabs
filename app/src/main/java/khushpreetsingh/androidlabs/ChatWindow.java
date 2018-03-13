package khushpreetsingh.androidlabs;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import java.lang.reflect.Array;
import java.util.ArrayList;

import static khushpreetsingh.androidlabs.ChatDatabaseHelper.TABLE_NAME;

public class ChatWindow extends Activity {

    ArrayList<String> list1 = new ArrayList<>();
    protected static final String ACTIVITY_NAME = "StartActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);

        ChatDatabaseHelper myOpener = new ChatDatabaseHelper(this);
        SQLiteDatabase db = myOpener.getWritableDatabase();

        Button btn = (Button) findViewById(R.id.button4);
        EditText edt = (EditText) findViewById(R.id.editText3);
        ListView list = (ListView) findViewById(R.id.listView1);
        ChatAdapter messageAdapter = new ChatAdapter(this);

        Cursor cursor= db.query(true, ChatDatabaseHelper.TABLE_NAME,
                new String[] { ChatDatabaseHelper.KEY_ID, ChatDatabaseHelper.KEY_MESSAGE},
                ChatDatabaseHelper.KEY_MESSAGE + " Not Null" , null, null, null, null, null);
           cursor.moveToFirst();

        while(!cursor.isAfterLast() ) {

            list1.add(cursor.getString(cursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE)));
            messageAdapter.notifyDataSetChanged(); //this restarts the process of getCount() & getView()

           Log.i(ACTIVITY_NAME, "SQL MESSAGE:" + cursor.getString(cursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE)));
            cursor.moveToNext();



        }
        Log.i(ACTIVITY_NAME, "Cursor's  column count =" + cursor.getColumnCount() );

        list.setAdapter(messageAdapter);

        btn.setOnClickListener( (View e) -> {
                    list1.add(edt.getText().toString());
            ContentValues newData = new ContentValues();

            newData.put(ChatDatabaseHelper.KEY_MESSAGE, edt.getText().toString());

            db.insert(ChatDatabaseHelper.TABLE_NAME, null, newData);

            messageAdapter.notifyDataSetChanged(); //this restarts the process of getCount() & getView()
                    edt.setText("");



                }
        );

    }//

    private class ChatAdapter extends ArrayAdapter<String> {

        public ChatAdapter(Context ctx) {
            super(ctx, 0);
        }

        public int getCount() {

            return list1.size();
        }

        public String getItem(int position) {

            return list1.get(position);
        }

        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = ChatWindow.this.getLayoutInflater();
            View result = null;
            if (position % 2 == 0)
                result = inflater.inflate(R.layout.chat_row_incoming, null);

            else
                result = inflater.inflate(R.layout.chat_row_outgoing, null);

            TextView message = (TextView) result.findViewById(R.id.message_text);
            message.setText(getItem(position)); // get the string at position
            return result;

        }

        public long getId(int position) {

            return position;
        }
    }
}

