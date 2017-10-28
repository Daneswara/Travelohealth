package com.masbie.travelohealth;

import static android.Manifest.permission.READ_CONTACTS;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.masbie.travelohealth.object.User;
import com.masbie.travelohealth.pojo.auth.Register;
import com.masbie.travelohealth.service.service.auth.LoginService;

import java.util.ArrayList;
import java.util.List;

/**
 * A login screen that offers login via email/password.
 */
public class RegisterActivity extends AppCompatActivity implements LoaderCallbacks<Cursor> {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;
    private static final String TAG = "Login Service";
    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };
    SweetAlertDialog prosses_login;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;

    // UI references.
//    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView, mCekPasswordView, mNamaView;
    private View mProgressView;
    private View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        setupActionBar();
        // Set up the login form.
//        mEmailView = findViewById(R.id.email);
//        populateAutoComplete();


        mPasswordView = findViewById(R.id.password);
        mNamaView = findViewById(R.id.name);
        mCekPasswordView = findViewById(R.id.cek_password);
//        mCekPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
//                if (id == R.id.login || id == EditorInfo.IME_NULL) {
//                    attemptLogin();
//                    return true;
//                }
//                return false;
//            }
//        });

        Button mEmailSignInButton = findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }
//
//    private void populateAutoComplete()
//    {
//        if(!mayRequestContacts())
//        {
//            return;
//        }
//
//        getLoaderManager().initLoader(0, null, this);
//    }
//
//    private boolean mayRequestContacts()
//    {
//        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
//        {
//            return true;
//        }
//        if(checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED)
//        {
//            return true;
//        }
//        if(shouldShowRequestPermissionRationale(READ_CONTACTS))
//        {
//            Snackbar.make(mEmailView, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
//                    .setAction(android.R.string.ok, new View.OnClickListener()
//                    {
//                        @Override
//                        @TargetApi(Build.VERSION_CODES.M)
//                        public void onClick(View v)
//                        {
//                            requestPermissions(new String[] {READ_CONTACTS}, REQUEST_READ_CONTACTS);
//                        }
//                    });
//        }
//        else
//        {
//            requestPermissions(new String[] {READ_CONTACTS}, REQUEST_READ_CONTACTS);
//        }
//        return false;
//    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                populateAutoComplete();
            }
        }
    }

    /**
     * Set up the {@link android.app.ActionBar}, if the API is available.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void setupActionBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            // Show the Up button in the action bar.
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private static Retrofit retrofit = null;

    public static Retrofit getClient(String baseUrl) {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
//        mEmailView.setError(null);
        mPasswordView.setError(null);
        mCekPasswordView.setError(null);
        mNamaView.setError(null);

        // Store values at the time of the login attempt.
//        String email        = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();
        String cek_password = mCekPasswordView.getText().toString();
        String name = mNamaView.getText().toString();


        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(name)) {
            mNamaView.setError(getString(R.string.error_field_required));
            focusView = mNamaView;
            cancel = true;
        }

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.error_field_required));
            focusView = mPasswordView;
            cancel = true;
        } else if (!isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
//        if(TextUtils.isEmpty(email))
//        {
//            mEmailView.setError(getString(R.string.error_field_required));
//            focusView = mEmailView;
//            cancel = true;
//        }
//        else if(!isEmailValid(email))
//        {
//            mEmailView.setError(getString(R.string.error_invalid_email));
//            focusView = mEmailView;
//            cancel = true;
//        }

        if (TextUtils.isEmpty(cek_password)) {
            mCekPasswordView.setError(getString(R.string.error_field_required));
            focusView = mCekPasswordView;
            cancel = true;
        }

        if (!password.equals(cek_password)) {
            mCekPasswordView.setError(getString(R.string.error_invalid_cek_password));
            focusView = mCekPasswordView;
            cancel = true;
        }


        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            //            showProgress(true);
            prosses_login = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
            prosses_login.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            prosses_login.setTitleText("Loading");
            prosses_login.setCancelable(false);
            prosses_login.show();
            getClient("https://travelohealth.000webhostapp.com/");
            LoginService api = retrofit.create(LoginService.class);
            Call<Register> result = api.register(name, password, cek_password);
            result.enqueue(new Callback<Register>() {
                @Override
                public void onResponse(Call<Register> call, final Response<Register> response) {
                    prosses_login.dismissWithAnimation();
                    try {
                        if (response.body() != null) {
                            if (response.body().getData().getMessage().getMessage().get(0).equals("Account Successfully Created")) {
                                new SweetAlertDialog(RegisterActivity.this, SweetAlertDialog.WARNING_TYPE)
                                        .setTitleText("Akun Berhasil Dibuat")
                                        .setContentText("Gunakan username "+response.body().getData().getResult().getCoupon()+" untuk login")
                                        .setConfirmText("Ya")
                                        .showCancelButton(false)
                                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                Intent masuk = new Intent(RegisterActivity.this, LoginActivity.class);
                                                masuk.putExtra("usernameregister", response.body().getData().getResult().getCoupon());
                                                startActivity(masuk);
                                                finish();
                                            }
                                        })
                                        .show();

                            } else {
                                Toast.makeText(RegisterActivity.this, "Error: " + response.body().getData().getMessage().getMessage().get(0), Toast.LENGTH_LONG).show();
                            }
                        }
//                            Toast.makeText(MainActivity.this," response message "+response.body().string(),Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<Register> call, Throwable t) {
                    Toast.makeText(RegisterActivity.this, "Error", Toast.LENGTH_LONG).show();
                    prosses_login.dismissWithAnimation();
                }
            });

            //            mAuthTask = new UserLoginTask(email, password);
            //            mAuthTask.execute((Void) null);
//            mAuth.createUserWithEmailAndPassword(email, password)
//                 .addOnCompleteListener(this, new OnCompleteListener<AuthResult>()
//                 {
//                     @Override
//                     public void onComplete(@NonNull Task<AuthResult> task)
//                     {
//                         if(task.isSuccessful())
//                         {
//                             prosses_login.dismissWithAnimation();
//                             // Sign in success, update UI with the signed-in user's information
//                             Log.d(TAG, "createUserWithEmail:success");
//                             FirebaseUser user    = mAuth.getCurrentUser();
//                             User         newuser = new User(mNamaView.getText().toString(), mEmailView.getText().toString());
//                             mDatabase.child("users").child(user.getUid()).setValue(newuser);
//                             String token = FirebaseInstanceId.getInstance().getToken();
//                             mDatabase.child("users").child(user.getUid()).child("token").setValue(token);
//                             FirebaseMessaging.getInstance().subscribeToTopic("news");
//                             Intent masuk = new Intent(RegisterActivity.this, Home.class);
//                             startActivity(masuk);
//                             finish();
//                         }
//                         else
//                         {
//                             // If sign in fails, display a message to the user.
//                             Log.w(TAG, "createUserWithEmail:failure", task.getException());
//                         }
//                         //                            showProgress(false);
//
//                         // ...
//                     }
//                 }).addOnFailureListener(new OnFailureListener()
//            {
//                @Override
//                public void onFailure(@NonNull Exception e)
//                {
//                    //                    showProgress(false);
//                    prosses_login.dismissWithAnimation();
//                    if(e instanceof FirebaseAuthUserCollisionException)
//                    {
//                        Toast.makeText(RegisterActivity.this, "This User Already Registered , Please Login", Toast.LENGTH_SHORT).show();
//                        mEmailView.setError(getString(R.string.error_incorrect_email));
//                        mEmailView.requestFocus();
//                    }
//                    if(e instanceof FirebaseAuthWeakPasswordException)
//                    {
//                        Toast.makeText(RegisterActivity.this, "Password is Weak, Minimum Password Is 6 Digits", Toast.LENGTH_SHORT).show();
//                        mPasswordView.setError(getString(R.string.error_incorrect_password));
//                        mPasswordView.requestFocus();
//                    }
//                    if(e instanceof FirebaseNetworkException)
//                    {
//                        mEmailView.requestFocus();
//                        Toast.makeText(RegisterActivity.this, "Please Check Your Connection", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            });
        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 7;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        addEmailsToAutoComplete(emails);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(RegisterActivity.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

//        mEmailView.setAdapter(adapter);
    }


    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;

        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {
                // Simulate network access.
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return false;
            }

            for (String credential : DUMMY_CREDENTIALS) {
                String[] pieces = credential.split(":");
                if (pieces[0].equals(mEmail)) {
                    // Account exists, return true if the password matches.
                    return pieces[1].equals(mPassword);
                }
            }

            // TODO: register the new account here.
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {
                finish();
            } else {
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
}

