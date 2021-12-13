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
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity
{

    EditText accountNum;
    EditText password;
    Button btn;
    Button logInBtn;
    //String mail;
    FirebaseAuth myAuth;
    TextView resultText;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        ini();
        btn.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                 createAccount();
            }
        });
        logInBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                login();
            }
        });
    }

    void ini()
    {
        accountNum=findViewById(R.id.passwordNew);
        myAuth=FirebaseAuth.getInstance();
        password=findViewById(R.id.passwordTypeAgain);
        btn=findViewById(R.id.create);

        //btn.getBackground().setColorFilter(0xFF000000, android.graphics.PorterDuff.Mode.MULTIPLY );
        resultText=findViewById(R.id.resultText);
        logInBtn=findViewById(R.id.loginbutton);

    }
    void createAccount()
    {
        myAuth.createUserWithEmailAndPassword(accountNum.getText().toString(),password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>()
        {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task)
            {
                if(task.isSuccessful())
                {
                    FirebaseUser user=myAuth.getCurrentUser();
                    resultText.setText(user.getEmail()+"註冊成功!");

                    InputMethodManager inputManager =
                            (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

                    View currentFocus = getCurrentFocus();
                    if (currentFocus != null) {
                        // Base interface for a remotable object
                        IBinder windowToken = currentFocus.getWindowToken();

                        // Hide type
                        int hideType = InputMethodManager.HIDE_NOT_ALWAYS;

                        // Hide the KeyBoard
                        inputManager.hideSoftInputFromWindow(windowToken, hideType);
                    }
                }
                else
                {
                    resultText.setText("註冊失敗!"+task.getException());
                }
                resultText.setVisibility(View.VISIBLE);
            }
        });
    }
    void login()
    {
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, loginActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("account",accountNum.getText().toString());
        bundle.putString("password",password.getText().toString());
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }

}