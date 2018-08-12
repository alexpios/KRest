package by.kuchinsky.alexandr.krest;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ShPreferences extends AppCompatActivity implements View.OnClickListener {
Button btnSave, btnLoad;
SharedPreferences sp;
EditText etText;
final String SAVED_TEXT = "saved_text";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sh_preferences);
        btnSave = (Button)findViewById(R.id.btnSave);
        btnSave.setOnClickListener(this);
        btnLoad=(Button)findViewById(R.id.btnLoad);
        btnLoad.setOnClickListener(this);
        etText=(EditText)findViewById(R.id.etText);
        
        loadText();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSave:
                saveText();
                break;
            case R.id.btnLoad:
                loadText();
                break;
                default:
                    break;
            
        }
    }

    private void loadText() {
        sp= getSharedPreferences("MyTextFromEditText", MODE_PRIVATE);
        String saved_text = sp.getString(SAVED_TEXT, "");
        etText.setText(saved_text);
        Toast.makeText(this, "Text saved.", Toast.LENGTH_SHORT).show();

    }

    private void saveText() {

        sp = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor et = sp.edit();
        et.putString(SAVED_TEXT, etText.getText().toString());
        et.commit();
        Toast.makeText(this, "Data saved ", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        saveText();
    }
}
