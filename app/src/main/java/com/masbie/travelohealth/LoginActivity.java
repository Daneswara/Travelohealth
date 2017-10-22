package com.masbie.travelohealth;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import cn.pedant.SweetAlert.SweetAlertDialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity
{
    private static final String TAG = "Login Service";
    SweetAlertDialog prosses_login;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private View mSignUpFormView;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null)
        {
            Intent keluar = new Intent(LoginActivity.this, Home.class);
            startActivity(keluar);
            finish();
        }
        else
        {
            setContentView(R.layout.activity_login);
            // Set up the login form.
            mEmailView = findViewById(R.id.email);

            mPasswordView = findViewById(R.id.password);
            mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener()
            {
                @Override
                public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent)
                {
                    if(id == R.id.login || id == EditorInfo.IME_NULL)
                    {
                        attemptLogin();
                        return true;
                    }
                    return false;
                }
            });

            Button mEmailSignInButton = findViewById(R.id.email_sign_in_button);
            mEmailSignInButton.setOnClickListener(new OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    attemptLogin();
                }
            });

            mLoginFormView = findViewById(R.id.login_form);
            mSignUpFormView = findViewById(R.id.signup);
            mProgressView = findViewById(R.id.login_progress);
            mSignUpFormView.setOnClickListener(new OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    Intent regis = new Intent(LoginActivity.this, RegisterActivity.class);
                    startActivity(regis);
                }
            });
        }
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin()
    {
        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if(TextUtils.isEmpty(password))
        {
            mPasswordView.setError(getString(R.string.error_field_required));
            focusView = mPasswordView;
            cancel = true;
        }
        else if(!isPasswordValid(password))
        {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if(TextUtils.isEmpty(email))
        {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        }
        else if(!isEmailValid(email))
        {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if(cancel)
        {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        }
        else
        {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            //showProgress(true);
            prosses_login = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
            prosses_login.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            prosses_login.setTitleText("Loading");
            prosses_login.setCancelable(false);
            prosses_login.show();
            mAuth.signInWithEmailAndPassword(email, password)
                 .addOnCompleteListener(this, new OnCompleteListener<AuthResult>()
                 {
                     @Override
                     public void onComplete(@NonNull Task<AuthResult> task)
                     {

                         if(task.isSuccessful())
                         {
                             // Sign in success, update UI with the signed-in user's information
                             Log.d(TAG, "signInWithEmail:success");
                             FirebaseUser user = mAuth.getCurrentUser();
                             prosses_login.dismissWithAnimation();
                             String token = FirebaseInstanceId.getInstance().getToken();
                             mDatabase.child("users").child(user.getUid()).child("token").setValue(token);
                             FirebaseMessaging.getInstance().subscribeToTopic("news");
                             Intent masuk = new Intent(LoginActivity.this, Home.class);
                             startActivity(masuk);
                             finish();
                         }
                         else
                         {
                             // If sign in fails, display a message to the user.
                             Log.w(TAG, "signInWithEmail:failure", task.getException());
                         }

                         // ...
                     }
                 }).addOnFailureListener(new OnFailureListener()
            {
                @Override
                public void onFailure(@NonNull Exception e)
                {
                    prosses_login.dismissWithAnimation();
                    if(e instanceof FirebaseAuthInvalidUserException)
                    {
                        Toast.makeText(LoginActivity.this, "This User Not Found , Create A New Account", Toast.LENGTH_SHORT).show();
                        mEmailView.setError(getString(R.string.error_incorrect_email));
                        mEmailView.requestFocus();
                    }
                    if(e instanceof FirebaseAuthInvalidCredentialsException)
                    {
                        Toast.makeText(LoginActivity.this, "The Password Is Invalid, Please Try Valid Password", Toast.LENGTH_SHORT).show();
                        mPasswordView.setError(getString(R.string.error_incorrect_password));
                        mPasswordView.requestFocus();
                    }
                    if(e instanceof FirebaseNetworkException)
                    {
                        mEmailView.requestFocus();
                        Toast.makeText(LoginActivity.this, "Please Check Your Connection", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private boolean isEmailValid(String email)
    {
        return email.contains("@");
    }

    private boolean isPasswordValid(String password)
    {
        return password.length() > 8;
    }
}

