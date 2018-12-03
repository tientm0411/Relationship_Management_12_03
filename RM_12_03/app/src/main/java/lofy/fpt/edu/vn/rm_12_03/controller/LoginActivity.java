package lofy.fpt.edu.vn.rm_12_03.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import lofy.fpt.edu.vn.rm_12_03.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
    }

    private void initView() {
        tvCreateAcc = (TextView) findViewById(R.id.login_tv_createAcc);
        tvForgotPass = (TextView) findViewById(R.id.login_tv_forgotPassword);
        edtUsername = (EditText) findViewById(R.id.login_edt_username);
        edtPassword = (EditText) findViewById(R.id.login_edt_pass);
        btnLogin = (Button) findViewById(R.id.login_btn_login);
        btnFacebook = (Button) findViewById(R.id.login_btn_fb);
        btnGmail = (Button) findViewById(R.id.login_btn_email);
        username = edtUsername.getText().toString();
        password = edtPassword.getText().toString();

        tvCreateAcc.setOnClickListener(this);
        tvForgotPass.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        btnFacebook.setOnClickListener(this);
        btnGmail.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.login_tv_createAcc:
                intent = new Intent(this,SignUpActivity.class);
                startActivity(intent);
                break;
            case R.id.login_tv_forgotPassword:

                break;
            case R.id.login_btn_login:

                break;
            case R.id.login_btn_fb:

                break;
            case R.id.login_btn_email:

                break;
            default:
                break;
        }
    }

}
