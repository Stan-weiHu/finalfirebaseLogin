package tw.scu.edu.firebaselogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class loginActivity extends AppCompatActivity
{
    EditText accountt;
    EditText passwordt;
    Button btn;
    FirebaseAuth myAuth;
    TextView textView;

    @Override
    public void onResume()
    {
        super.onResume();
        // put your code here...
        passwordt.setText("");
    }
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Bundle bundle = getIntent().getExtras();
        getSupportActionBar().hide();
        String account = bundle.getString("account" );
        String password = bundle.getString("password");
        ini(account);
        btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                InputMethodManager inputManager =
                        (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

                View currentFocus = getCurrentFocus();
                if (currentFocus != null)
                {
                    // Base interface for a remotable object
                    IBinder windowToken = currentFocus.getWindowToken();

                    // Hide type
                    int hideType = InputMethodManager.HIDE_NOT_ALWAYS;

                    // Hide the KeyBoard
                    inputManager.hideSoftInputFromWindow(windowToken, hideType);
                }
                myAuth.signInWithEmailAndPassword(account,passwordt.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>()
                {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if(task.isSuccessful())
                        {
                            Intent intent = new Intent();
                            intent.setClass(loginActivity.this, HelloActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("account",account);
                            bundle.putString("password",password);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                        else
                        {
                            textView.setText(task.getException().toString());
                        }
                    }
                });

            }
        });
    }
    void ini(String account)
    {
        this.accountt=findViewById(R.id.passwordNew);
        this.passwordt=findViewById(R.id.passwordTypeAgain);
        this.accountt.setText(account);
        this.passwordt.setText("");
        btn=findViewById(R.id.submitBtn);
        myAuth=FirebaseAuth.getInstance();
        textView=findViewById(R.id.textView2);
    }
}