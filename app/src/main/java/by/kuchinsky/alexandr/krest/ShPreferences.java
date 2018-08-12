package by.kuchinsky.alexandr.krest;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
    }

    private void saveText() {

        sp = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor et = sp.edit();




    }
}
