package lofy.fpt.edu.vn.rm_12_03.controller;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import lofy.fpt.edu.vn.rm_12_03.R;

public class IntroActivity extends AppCompatActivity implements View.OnClickListener, SharedPreferences.OnSharedPreferenceChangeListener {
    private Button btnNext;

    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor editor;

    public static final String FILE_NAME = "Inital-Data-App-RM-ver1";
    public static final String USER_ID = "userId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        initView();
    }

    private void initView() {
        mSharedPreferences = getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        mSharedPreferences.registerOnSharedPreferenceChangeListener(this);
        editor = mSharedPreferences.edit();

        btnNext = (Button) findViewById(R.id.intro_btn_next);
        btnNext.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.intro_btn_next:
                nextActivity();
            default:
                break;
        }
    }

    private void nextActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
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
