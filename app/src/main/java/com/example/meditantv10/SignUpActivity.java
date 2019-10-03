package com.example.meditantv10;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity  implements View.OnClickListener {
    private FirebaseAuth mAuth;
    private TextInputLayout signUpName,signUpEmail,signUpPassword;
    private Button signUpButton;
    private TextView signInTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        this.setTitle("Sign Up");

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();


        signUpName = findViewById(R.id.textInputNameId);
        signUpEmail = findViewById(R.id.textInputEmailId);
        signUpPassword = findViewById(R.id.textInputPasswordId);
        signUpButton = findViewById(R.id.signUpButtonId);
        signInTextView = findViewById(R.id.signInTextViewId);

        signInTextView.setOnClickListener(this);
        signUpButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.signUpButtonId:
                userRegister();
                break;
            case  R.id.signInTextViewId:
                Intent intent = new Intent(getApplicationContext(),SignInActivity.class);
                startActivity(intent);
                break;
        }

    }

    private void userRegister() {

        String email = signUpEmail.toString().trim();
        String password = signUpPassword.toString().trim();

        if(email.isEmpty()){
            signUpEmail.setError("Enter an Email Address");
            signUpEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            signUpEmail.setError("Enter a valid email address");
            signUpEmail.requestFocus();
            return;
        }

        if(password.isEmpty()){
            signUpPassword.setError("Enter a password");
            signUpPassword.requestFocus();
            return;
        }

        if(password.length()<6){
            signUpPassword.setError("Minimum length of a password should be a 6");
            signUpPassword.requestFocus();
            return;
        }
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Register is successful", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Register is not successful", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
