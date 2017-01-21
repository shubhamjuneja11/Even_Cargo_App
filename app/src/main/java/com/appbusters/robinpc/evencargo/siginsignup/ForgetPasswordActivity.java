package com.appbusters.robinpc.evencargo.siginsignup;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.appbusters.robinpc.evencargo.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPasswordActivity extends AppCompatActivity {
AutoCompleteTextView at;
    private FirebaseAuth mAuth;
    String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_forget_password);
        at=(AutoCompleteTextView)findViewById(R.id.emailfo);


    }
    public void forget(View view){
        email=at.getText().toString();
        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(ForgetPasswordActivity.this, "Verification email sent", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(ForgetPasswordActivity.this,SigninActivity.class);
                            startActivity(intent);
                        }
                        else
                        {
                            Toast.makeText(ForgetPasswordActivity.this, "You are not registered", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    public void fun2(View view){
        Intent intent=new Intent(this,SigninActivity.class);
        startActivity(intent);
    }

}
