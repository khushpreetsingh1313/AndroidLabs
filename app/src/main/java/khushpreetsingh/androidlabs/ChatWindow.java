package khushpreetsingh.androidlabs;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ChatWindow extends Activity {
    protected static final String ACTIVITY_NAME = "ChatWindow";
    static ChatAdapter messageAdapter;
    static ArrayList<String> list = new ArrayList<>();
    ListView listView;
    EditText editText;
    Button button;
    static SQLiteDatabase db;
    boolean isTablet;
    static Cursor cursor;
    static  ChatDatabaseHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);
        isTablet =((findViewById(R.id.frame))!=null);

        helper = new ChatDatabaseHelper(this);
        messageAdapter =new ChatAdapter( this );

        db = helper.getWritableDatabase();
        list.clear();

        moveCursor();

        Log.i(ACTIVITY_NAME, "Cursor's  column count =" + cursor.getColumnCount() );


        listView = (ListView) findViewById(R.id.listView1);
        editText = (EditText) findViewById(R.id.editText3);
        button = (Button) findViewById(R.id.button4);
        button.setOnClickListener(e->{
            list.clear();

            ContentValues contentValues = new ContentValues();
            contentValues.put(ChatDatabaseHelper.KEY_MESSAGE,editText.getText().toString());
            db.insert(ChatDatabaseHelper.TABLE_NAME, null, contentValues);

            moveCursor();
            editText.setText("");
        });

        listView.setAdapter (messageAdapter);



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle bundle_message = new Bundle();
                bundle_message.putString("message",listView.getItemAtPosition(i).toString() );
                bundle_message.putString("id", String.valueOf(messageAdapter.getId(i)) );
                bundle_message.putBoolean("isTablet", isTablet);
                if(isTablet){
                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    MessageFragment mf = new MessageFragment();
                    mf.setArguments(bundle_message);
                    ft.replace(R.id.frame, mf);
                    ft.commit();
                }
                else{
                    Intent intent = new Intent(ChatWindow.this, MessageDetails.class);
                    intent.putExtra("message", listView.getItemAtPosition(i).toString());
                    intent.putExtra("id",String.valueOf(messageAdapter.getId(i) ));
                    startActivityForResult(intent, 500);
                }
            }
        });


    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if ( resultCode == 600) {
            Bundle extras = data.getExtras();
            String comingID = (String) extras.get("sendingID");
            db.delete(ChatDatabaseHelper.TABLE_NAME,ChatDatabaseHelper.KEY_ID +"="+comingID,null);
            list.clear();
            moveCursor();
        }

    }


    private class ChatAdapter extends ArrayAdapter<String>{
        public ChatAdapter(Context ctx) {
            super(ctx, 0);
        }

        public int getCount(){
            return list.size();
        }
        public String getItem(int position){
            return list.get(position);
        }
        public View getView(int position, View convertView, ViewGroup parent){
            LayoutInflater inflater = ChatWindow.this.getLayoutInflater();
            View result = null;
            if(position%2 == 0)
                result = inflater.inflate(R.layout.chat_row_incoming, null);
            else
                result = inflater.inflate(R.layout.chat_row_outgoing, null);

            TextView message = (TextView)result.findViewById(R.id.message_text);
            message.setText(   getItem(position)  ); // get the string at position
            return result;
        }

        public long getId(int position){
            cursor.moveToPosition(position);
            return cursor.getLong(cursor.getColumnIndex(ChatDatabaseHelper.KEY_ID));
        };
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }

    public static  void moveCursor(){
        cursor = db.query(true, ChatDatabaseHelper.TABLE_NAME,
                new String[] { ChatDatabaseHelper.KEY_ID, ChatDatabaseHelper.KEY_MESSAGE},
                ChatDatabaseHelper.KEY_MESSAGE + " Not Null" , null, null, null, null, null);

        cursor.moveToFirst();

        while(!cursor.isAfterLast() ) {
            list.add(cursor.getString(cursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE)));
            messageAdapter.notifyDataSetChanged();
            cursor.moveToNext();
        }
        messageAdapter.notifyDataSetChanged();

    }
}

