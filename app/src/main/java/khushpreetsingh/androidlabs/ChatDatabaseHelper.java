package khushpreetsingh.androidlabs;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;


/**
 * Created by khushpreet singh on 2018-02-20.
 */

public class ChatDatabaseHelper extends SQLiteOpenHelper {

    public static final int VERSION_NUM = 4;
    public static final String DATABASE_NAME= "message.db";
    public static final String TABLE_NAME= "chat";
    public final static String KEY_ID="id_key";
    public final static String KEY_MESSAGE="message_key";

    public ChatDatabaseHelper(Context ctx) {
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL( "CREATE TABLE " + TABLE_NAME + " ( "+KEY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT," +KEY_MESSAGE+" text);" );
        Log.i("ChatDatabaseHelper", "Calling onCreate");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);  //make a new database
        Log.i("ChatDatabaseHelper", "Calling onUpgrade, oldVersion=" + oldVer + " newVersion=" + newVer);
    }
}
