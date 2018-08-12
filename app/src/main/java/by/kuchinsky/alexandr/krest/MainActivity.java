package by.kuchinsky.alexandr.krest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
Button btnShP, btnSQLite, btnHome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnShP = (Button)findViewById(R.id.button);
        btnShP.setOnClickListener(this);
        btnSQLite = (Button)findViewById(R.id.button2);
        btnSQLite.setOnClickListener(this);
        btnHome = (Button)findViewById(R.id.button3);
        btnHome.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
switch (v.getId()){
    case R.id.button:
        Intent shared = new Intent(MainActivity.this, ShPreferences.class);
        startActivity(shared);
    break;

    case R.id.button2:
        Intent sql = new Intent(MainActivity.this, SQliteMain.class);
        startActivity(sql);
        break;

    case R.id.button3:

        break;

}
    }
}
