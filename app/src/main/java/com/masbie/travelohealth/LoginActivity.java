package com.masbie.travelohealth;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
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
import com.google.firebase.iid.FirebaseInstanceId;
import com.masbie.travelohealth.dao.TokenDao;
import com.masbie.travelohealth.dao.external.Dao;
import com.masbie.travelohealth.dao.external.auth.FirebaseDao;
import com.masbie.travelohealth.dao.external.auth.LoginDao;
import com.masbie.travelohealth.dao.external.personal.AccountDao;
import com.masbie.travelohealth.pojo.auth.FcmTokenPojo;
import com.masbie.travelohealth.pojo.auth.TokenPojo;
import com.masbie.travelohealth.pojo.auth.UserLoginPojo;
import com.masbie.travelohealth.pojo.personal.AccountPojo;
import com.masbie.travelohealth.pojo.response.ResponsePojo;
import com.masbie.travelohealth.pojo.response.ResultPojo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity
{
    private static final String TAG = "Login Service";
    private SweetAlertDialog prosses_login;

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText             mPasswordView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        Timber.d("onCreate");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        mEmailView = findViewById(R.id.email);

        mPasswordView = findViewById(R.id.password);
//        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener()
//        {
//            @Override
//            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent)
//            {
//                if(id == R.id.login || id == EditorInfo.IME_NULL)
//                {
//                    attemptLogin();
//                    return true;
//                }
//                return false;
//            }
//        });
        String usernameregister = null;
        if(getIntent().getExtras()!=null){
            usernameregister = getIntent().getExtras().getString("usernameregister");
            mEmailView.setText(usernameregister);
            mPasswordView.setText("");
        }

        Button mEmailSignInButton = findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                attemptLogin();
            }
        });

        View mSignUpFormView = findViewById(R.id.signup);
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

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin()
    {
        Timber.d("attemptLogin");

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email    = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel    = false;
        View    focusView = null;

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

        if(cancel)
        {
            focusView.requestFocus();
        }
        else
        {
            prosses_login = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
            prosses_login.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            prosses_login.setTitleText("Loading");
            prosses_login.setCancelable(true);
            prosses_login.show();

            final Call<ResponsePojo<TokenPojo>> login = LoginDao.login(new UserLoginPojo(email, password), this, new Callback<ResponsePojo<TokenPojo>>()
            {
                @SuppressWarnings("ConstantConditions")
                @Override public void onResponse(@NonNull Call<ResponsePojo<TokenPojo>> call, @NonNull Response<ResponsePojo<TokenPojo>> response)
                {
                    @NonNull final ResultPojo<TokenPojo> result = response.body().getData();
                    if(result.getStatus() == 0)
                    {
                        prosses_login.dismissWithAnimation();
                        Toast.makeText(LoginActivity.this, result.getMessage().getMessage().get(0), Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        TokenDao.storeToken(LoginActivity.this, result.getResult());
                        LoginActivity.this.retrievePersonalAccount();
                    }
                }

                @Override public void onFailure(@NonNull Call<ResponsePojo<TokenPojo>> call, @NonNull Throwable throwable)
                {
                    prosses_login.dismissWithAnimation();
                    Dao.defaultFailureTask(LoginActivity.this, call, throwable);
                }
            });

            prosses_login.setOnCancelListener(new DialogInterface.OnCancelListener()
            {
                @Override public void onCancel(DialogInterface dialog)
                {
                    login.cancel();
                }
            });
        }
    }

    private void retrievePersonalAccount()
    {
        Timber.d("retrievePersonalAccount");

        AccountDao.getAccount(this, new Callback<ResponsePojo<AccountPojo>>()
        {
            @SuppressWarnings("ConstantConditions") @Override public void onResponse(@NonNull Call<ResponsePojo<AccountPojo>> call, @NonNull Response<ResponsePojo<AccountPojo>> response)
            {
                @NonNull final ResultPojo<AccountPojo> result = response.body().getData();
                if(result.getStatus() == 0)
                {
                    prosses_login.dismissWithAnimation();
                    Toast.makeText(LoginActivity.this, result.getMessage().getMessage().get(0), Toast.LENGTH_SHORT).show();
                }
                else
                {
                    AccountDao.storeAccount(LoginActivity.this, result.getResult());
                    LoginActivity.this.registerFirebaseToken();
                }
            }

            @Override public void onFailure(@NonNull Call<ResponsePojo<AccountPojo>> call, @NonNull Throwable throwable)
            {
                prosses_login.dismissWithAnimation();
                Dao.defaultFailureTask(LoginActivity.this, call, throwable);
            }
        });
    }

    private void registerFirebaseToken()
    {
        Timber.d("registerFirebaseToken");

        FirebaseDao.registerToken(new FcmTokenPojo(FirebaseInstanceId.getInstance().getToken()), this, new Callback<ResponsePojo<Void>>()
        {
            @Override public void onResponse(@NonNull Call<ResponsePojo<Void>> call, @NonNull Response<ResponsePojo<Void>> response)
            {
                prosses_login.dismissWithAnimation();
                Intent masuk = new Intent(LoginActivity.this, Home.class);
                startActivity(masuk);
                finish();
            }

            @Override public void onFailure(@NonNull Call<ResponsePojo<Void>> call, @NonNull Throwable throwable)
            {
                prosses_login.dismissWithAnimation();
                Dao.defaultFailureTask(LoginActivity.this, call, throwable);
            }
        });
    }

    private boolean isPasswordValid(String password)
    {
        Timber.d("isPasswordValid");

        return password.length() >= 8;
    }
}

