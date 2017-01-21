package com.appbusters.robinpc.evencargo.siginsignup;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.appbusters.robinpc.evencargo.Constants;
import com.appbusters.robinpc.evencargo.R;
import com.appbusters.robinpc.evencargo.managerpanel.ManagerPanelActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class SigninActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    public static String mypref="Mypref";
    EditText ema, pass;
    public static String myemail;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        //psharedPreferences=getSharedPreferences(mypref, Context.MODE_PRIVATE);
        if(sharedPreferences.contains("email")) {
            SigninActivity.myemail=sharedPreferences.getString("email","");
            Constants.from=Constants.convert1(myemail);
            Constants.fun();
            Log.e("haaaaaa",Constants.from);
            String si,sender;
            si=getIntent().getStringExtra("FROM_USER");
            sender=getIntent().getStringExtra("TO_USER");
            int flag=getIntent().getIntExtra("flag",0);
            Intent intent;
            intent = new Intent(SigninActivity.this, ManagerPanelActivity.class);
            intent.putExtra("FROM_USER", si);
            intent.putExtra("TO_USER", sender);
            intent.putExtra("flag",flag);

            //intent.putExtra("FROM_USER", email);
            startActivity(intent);
            finish();
        }

        ema=(EditText)findViewById(R.id.emailsi);
        pass = (EditText) findViewById(R.id.passwordsi);
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d("ds", "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d("ds", "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };

    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    public void onClick(View view) {
        if (view.getId() == R.id.signin) {
            final String email = ema.getText().toString();
            String password = pass.getText().toString();
            if(!email.contains("@")||email.isEmpty())
                Toast.makeText(this,"Enter valid email address",Toast.LENGTH_SHORT).show();
            else if(password.length()<6)
                Toast.makeText(this,"Enter valid password greater than 5 characters",Toast.LENGTH_SHORT).show();
            else {
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {
                                    Log.w("hi", "signInWithEmail:failed", task.getException());
                                    Toast.makeText(SigninActivity.this, R.string.auth_failed,
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    Log.d("hi", "signInWithEmail:onComplete:" + task.isSuccessful());
                                    myemail = email;
                                    Constants.from=email;
                                    Constants.fun();
                                    Log.e("email", email);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("email", myemail);
                                    editor.commit();
                                    Intent intent = new Intent(SigninActivity.this, ManagerPanelActivity.class);
                                    intent.putExtra("FROM_USER", email);
                                    startActivity(intent);
                                    finish();
                                }

                                // ...
                            }
                        });
            }

        }
        else if(view.getId()==R.id.textViewDoSignUp){
            Intent intent=new Intent(SigninActivity.this,SignupActivity.class);
            startActivity(intent);
        }
        else if(view.getId()==R.id.textViewDoForgotPassword){
            Intent intent=new Intent(SigninActivity.this,ForgetPasswordActivity.class);
            startActivity(intent);
        }
    }
}
