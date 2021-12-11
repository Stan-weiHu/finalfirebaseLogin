package tw.scu.edu.firebaselogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HelloActivity extends AppCompatActivity
{
    TextView textView;
    Button button;
    String account;
    String password;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello);
        getSupportActionBar().hide();
        ini();
        Bundle bundle = getIntent().getExtras();
        account = bundle.getString("account" );
        password = bundle.getString("password");
        int size = account.indexOf("@");
        textView.setText("Hello,"+account.substring(0,size));
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                goChange();
            }
        });
    }
    void goChange()
    {
        Intent intent = new Intent();
        intent.setClass(HelloActivity.this, changePasswordActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("password",password);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }
    void ini()
    {
        textView=findViewById(R.id.textView);
        button=findViewById(R.id.button2);

    }
}