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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class changePasswordActivity extends AppCompatActivity
{
    Button submitbtn;
    String oldpassword;
    String newpassword;
    FirebaseAuth myAuth;
    FirebaseUser user;
    TextView textView;
    EditText newpasswordEditText;
    EditText newpasswordTypeAgain;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        getSupportActionBar().hide();
        ini();
        myAuth=FirebaseAuth.getInstance();
        user=myAuth.getCurrentUser();
        submitbtn.setOnClickListener(new View.OnClickListener()
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
                newpassword=newpasswordEditText.getText().toString();
                String doublecheck=newpasswordTypeAgain.getText().toString();
                if(newpassword.equals(doublecheck)==false)
                {
                    EditText text=findViewById(R.id.passwordNew);
                    EditText text2=findViewById(R.id.passwordTypeAgain);
                    text.setText("");
                    text2.setText("");
                    textView.setText("不一致");
                }
               
                else ChangePassword(newpassword);

            }
        });
    }
    void ChangePassword(String newpas)
    {

        user.updatePassword(newpas).addOnCompleteListener(new OnCompleteListener<Void>()
        {
            @Override
            public void onComplete(@NonNull Task<Void> task)
            {
                if(task.isSuccessful())
                {
                    textView.setText("修改成功");
                    goLogin();
                }
                else textView.setText("修改失敗!"+task.getException());
            }
        });
    }
    void goLogin()
    {
        Intent intent = new Intent();
        intent.setClass(changePasswordActivity.this, loginActivity.class);
        startActivity(intent);
        finish();
    }
    void ini()
    {
        submitbtn=findViewById(R.id.submitBtn);
        Bundle bundle = getIntent().getExtras();
        textView=findViewById(R.id.resulText);
        oldpassword = bundle.getString("password");
        newpasswordEditText=findViewById(R.id.passwordNew);
        newpasswordTypeAgain=findViewById(R.id.passwordTypeAgain);
    }
}