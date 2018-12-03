package lofy.fpt.edu.vn.rm_12_03.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.FirebaseDatabase;

import lofy.fpt.edu.vn.rm_12_03.R;

public class IntroActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnNext;

    private FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        initView();
    }

    private void initView() {
        firebaseDatabase = FirebaseDatabase.getInstance();
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
}
