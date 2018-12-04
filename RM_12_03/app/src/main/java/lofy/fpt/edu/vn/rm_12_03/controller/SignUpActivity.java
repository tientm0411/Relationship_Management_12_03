package lofy.fpt.edu.vn.rm_12_03.controller;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import lofy.fpt.edu.vn.rm_12_03.R;
import lofy.fpt.edu.vn.rm_12_03.entity.UserAccount;

public class SignUpActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    private static final String TAG = "SignUpActivity_TAG";
    private Button btnSignUp;
    private EditText edtUsername;
    private EditText edtPass0;
    private EditText edtPass1;
    private TextView signup_invalid_1;
    private TextView signup_invalid_2;
    private TextView signup_invalid_3;

    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private String passwordPattern = "^[a-zA-Z0-9._-]{6,10}$";

    private String username;
    private String pass0;
    private String pass1;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference accountRef;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        initView();
    }

    private void initView() {
        mSharedPreferences = getSharedPreferences(IntroActivity.FILE_NAME, Context.MODE_PRIVATE);
        mSharedPreferences.registerOnSharedPreferenceChangeListener(this);
        editor = mSharedPreferences.edit();

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        accountRef = mFirebaseDatabase.getReference("accounts");

        signup_invalid_1 = (TextView) findViewById(R.id.signup_invalid_1);
        signup_invalid_2 = (TextView) findViewById(R.id.signup_invalid_2);
        signup_invalid_3 = (TextView) findViewById(R.id.signup_invalid_3);

        signup_invalid_1.setVisibility(View.GONE);
        signup_invalid_2.setVisibility(View.GONE);
        signup_invalid_3.setVisibility(View.GONE);


        edtUsername = (EditText) findViewById(R.id.signup_edt_username);
        edtPass0 = (EditText) findViewById(R.id.signup_edt_pass_0);
        edtPass1 = (EditText) findViewById(R.id.signup_edt_pass_1);
        btnSignUp = (Button) findViewById(R.id.signup_btn_signup);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signup_invalid_1.setVisibility(View.GONE);
                signup_invalid_2.setVisibility(View.GONE);
                signup_invalid_3.setVisibility(View.GONE);

                username = edtUsername.getText().toString();
                pass0 = edtPass0.getText().toString();
                pass1 = edtPass1.getText().toString();
                if (validAccount(username, pass0, pass1)) {
                    sendRequestCode(username, pass0);
                }
            }
        });
    }

    private boolean validAccount(String email, String pas0, String pas1) {
        if (!email.matches(emailPattern)) {
//            Toast.makeText(getApplicationContext(), "Invalid email address !", Toast.LENGTH_SHORT).show();
            signup_invalid_1.setVisibility(View.VISIBLE);
//            edtUsername.setText("");
            return false;
        }
        if (!pas0.matches(passwordPattern)) {
//            Toast.makeText(getApplicationContext(), "Password must have minimum six charactersa and no special character !", Toast.LENGTH_LONG).show();
            signup_invalid_2.setVisibility(View.VISIBLE);
            edtPass0.setText("");
            return false;
        }
        if (!pas0.equals(pas1)) {
            signup_invalid_3.setVisibility(View.VISIBLE);
            edtPass1.setText("");
            return false;
        }
        return true;
    }

    private void sendRequestCode(final String username, String pass0) {
        final String userKey = accountRef.push().getKey();
        final UserAccount ua = new UserAccount.UserAccountBuilder()
                .withAccountId(userKey)
                .withAccountName(username)
                .withAccountPassword(pass0)
                .build();

        accountRef.orderByChild("accountName").equalTo(username).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // check if user had be used ?
                if (dataSnapshot.hasChildren()) {
                    Toast.makeText(getApplicationContext(), "this email had be signuped !", Toast.LENGTH_LONG).show();
                } else {
                    // sign up
                    accountRef.child(userKey).setValue(ua).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            // put data userID to sharepreference
                            editor.putString(IntroActivity.USER_ID, userKey);
                            editor.apply();

                            Toast.makeText(getApplicationContext(), "Sign up successfully !", Toast.LENGTH_SHORT).show();

                            // next to home
                            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                            startActivity(intent);
                        }
                    });
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

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
