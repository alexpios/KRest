package by.kuchinsky.alexandr.krest;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.CharArrayBuffer;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SQliteMain extends AppCompatActivity implements View.OnClickListener{
EditText etName, etEmail;
Button btnAdd, btnRead, btnClear;
DBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite_main);
        btnAdd=(Button)findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);
        btnClear=(Button)findViewById(R.id.btnClear);
        btnClear.setOnClickListener(this);
        btnRead=(Button)findViewById(R.id.btnRead);
        btnRead.setOnClickListener(this);
        etName=(EditText)findViewById(R.id.name);
        etEmail=(EditText)findViewById(R.id.email);
        dbHelper = new DBHelper(this);

    }

    @Override
    public void onClick(View v) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
String name = etName.getText().toString();
String mail = etEmail.getText().toString();
switch (v.getId()){
    case R.id.btnAdd:
contentValues.put(DBHelper.KEY_NAME, name);
contentValues.put(DBHelper.KEY_EMAIL, mail);
database.insert(DBHelper.TABLE_CONTACTS, null, contentValues);
        break;

        case R.id.btnRead:
            Cursor cursor = database.query(DBHelper.TABLE_CONTACTS,null,null,null,null,null,null);
            if (cursor.moveToFirst()){
                int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
                int nameIndex = cursor.getColumnIndex(DBHelper.KEY_NAME);
                int mailIndex = cursor.getColumnIndex(DBHelper.KEY_EMAIL);

                do {
                    Log.d("mLog", "ID = " + cursor.getInt(idIndex) + ", name = "+cursor.getString(nameIndex)
                    +", Email = "+cursor.getString(mailIndex));
                    Toast.makeText(this, "ID = " + cursor.getInt(idIndex) + ", name = "+cursor.getString(nameIndex)
                            +", Email = "+cursor.getString(mailIndex), Toast.LENGTH_SHORT).show();
                }while (cursor.moveToNext());
            }else
                Log.d("mLog", "Table has 0 rows");
            cursor.close();
        break;

        case R.id.btnClear:
database.delete(DBHelper.TABLE_CONTACTS, null, null);
            Toast.makeText(this, "WP", Toast.LENGTH_SHORT).show();
        break;
}dbHelper.close();
    }
}
