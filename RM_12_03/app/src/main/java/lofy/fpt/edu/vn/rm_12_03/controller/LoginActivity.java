package lofy.fpt.edu.vn.rm_12_03.controller;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import lofy.fpt.edu.vn.rm_12_03.R;
import lofy.fpt.edu.vn.rm_12_03.entity.User;
import lofy.fpt.edu.vn.rm_12_03.entity.UserAccount;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, SharedPreferences.OnSharedPreferenceChangeListener {

    private static final String TAG = "TAG_LOGIN";
    private TextView tvCreateAcc;
    private EditText edtUsername;
    private EditText edtPassword;
    private Button btnLogin;
    private Button btnFacebook;
    private Button btnGmail;
    private TextView tvForgotPass;

    private String username;
    private String password;

    private Intent intent;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference accountRef;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor editor;
    private CallbackManager callbackManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//        getKeyHash();
        initView();
        loginFaceboook();
    }

    private void initView() {
        mSharedPreferences = getSharedPreferences(IntroActivity.FILE_NAME, Context.MODE_PRIVATE);
        mSharedPreferences.registerOnSharedPreferenceChangeListener(this);
        editor = mSharedPreferences.edit();

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        accountRef = mFirebaseDatabase.getReference("accounts");

        tvCreateAcc = (TextView) findViewById(R.id.login_tv_createAcc);
        tvForgotPass = (TextView) findViewById(R.id.login_tv_forgotPassword);
        edtUsername = (EditText) findViewById(R.id.login_edt_username);
        edtPassword = (EditText) findViewById(R.id.login_edt_pass);
        btnLogin = (Button) findViewById(R.id.login_btn_login);
        btnFacebook = (Button) findViewById(R.id.login_btn_fb);
        btnGmail = (Button) findViewById(R.id.login_btn_email);

        tvCreateAcc.setOnClickListener(this);
        tvForgotPass.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        btnFacebook.setOnClickListener(this);
        btnGmail.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_tv_createAcc:
                intent = new Intent(this, SignUpActivity.class);
                startActivity(intent);
                break;
            case R.id.login_tv_forgotPassword:

                break;
            case R.id.login_btn_login:
                username = edtUsername.getText().toString();
                password = edtPassword.getText().toString();
                loginWithAcc(username, password);
                break;
            case R.id.login_btn_fb:
                LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile"));
                break;
            case R.id.login_btn_email:

                break;
            default:
                break;
        }
    }

    private void loginFaceboook() {
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code
                        requestUserProfile(loginResult);
                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                    }
                });
        if (AccessToken.getCurrentAccessToken() != null) {
            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public void requestUserProfile(LoginResult loginResult) {
        GraphRequest mGraphRequest = GraphRequest.newMeRequest(
                loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        if (response.getError() != null) {
                            // handle error
                        } else {
                            URL profile_picture = null;
                            try {
                                profile_picture = new URL("https://graph.facebook.com/" + object.getString("id") + "/picture?with=250&height=250");
                                final String id = object.optString("id");
                                String name = object.optString("name");

                                UserAccount ua = new UserAccount.UserAccountBuilder()
                                        .withAccountId(id)
                                        .withAccountName(name)
                                        .withAccountPassword(id)
                                        .build();
                                // push data user to firebase
                                accountRef.child(id).setValue(ua).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        // put data userID to sharepreference
                                        editor.putString(IntroActivity.USER_ID, id);
                                        editor.apply();
                                        // next to home
                                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                                        startActivity(intent);
                                    }
                                });
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email,gender, birthday");
        mGraphRequest.setParameters(parameters);
        mGraphRequest.executeAsync();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void loginWithAcc(String username, final String password) {
        accountRef.orderByChild("accountName").equalTo(username).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()) {
                    try {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                        Log.d(TAG, "onDataChange_2_1: " + snapshot.child("accountId").getValue(String.class));
                            if (snapshot.child("accountPassword").getValue(String.class).equals(password)) {
                                // put data userID to sharepreference
                                editor.putString(IntroActivity.USER_ID, snapshot.child("accountId").getValue(String.class));
                                editor.apply();
                                // next to home
                                intent = new Intent(getApplicationContext(), HomeActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(LoginActivity.this, "Email or password not correct !", Toast.LENGTH_LONG).show();
                            }
                        }
                    } catch (Exception e) {
                        Toast.makeText(LoginActivity.this, "Email or password not correct !", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Email or password not correct !", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    // get key hash
    public void getKeyHash() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo("lofy.fpt.edu.vn.rm_12_03", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KEYHASH", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
    }

    @Override
    protected void onDestroy() {
        mSharedPreferences.unregisterOnSharedPreferenceChangeListener(this);
        super.onDestroy();
    }
}
