package com.example.SurveyApplication.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.SurveyApplication.QuestionCategoriesActivity;
import com.example.SurveyApplication.R;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class VerificationSuccessMessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification_success_message);
        ButterKnife.bind(this);
    }
    @OnClick(R.id.continueButton)
    void onContinueButtonClicked(){
        startActivity(new Intent(VerificationSuccessMessageActivity.this, QuestionCategoriesActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP));
    }
}