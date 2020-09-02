package com.example.SurveyApplication.Login;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.SurveyApplication.ConstantKeys;
import com.example.SurveyApplication.CustomLoadingDialog;
import com.example.SurveyApplication.OtpReceiver;
import com.example.SurveyApplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.mukesh.OtpView;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OtpCodeVerifyActivity extends AppCompatActivity {

    private String phoneNumber;
    private CountDownTimer countDownTimer;
    private static final long TIME_TO_COUNTDOWN = 60000;
    private long timeLeft = TIME_TO_COUNTDOWN;
    private CustomLoadingDialog customLoadingDialog;
    private String verificationId;
    private FirebaseAuth firebaseAuth;


    @BindView(R.id.enteredNumberTextView)
    TextView enteredNumberTextView;
    @BindView(R.id.otpCodeEditText)
    OtpView otpCodeEditText;
    @BindView(R.id.timerTextView)
    TextView timerTextView;
    @BindView(R.id.resendTextView)
    TextView resendTextView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_code_verrify);
        ButterKnife.bind(this);
        requestPermission();
        startCountdown();
        customLoadingDialog=new CustomLoadingDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        phoneNumber = getIntent().getExtras().getString(ConstantKeys.PHONE_NUMBER);
        verificationId = getIntent().getExtras().getString(ConstantKeys.VERIFICATION_ID);
        enteredNumberTextView.setText(phoneNumber);
        new OtpReceiver().setEditText(otpCodeEditText);
    }

    private void requestPermission() {
        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECEIVE_SMS}, 1);
        }
    }

    @OnClick(R.id.continueButton)
    void onContinueButtonClicked() {
        String otpCode = otpCodeEditText.getText().toString();
        if (!TextUtils.isEmpty(otpCode)) {
            customLoadingDialog.startLoadingDialog();
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, otpCode);
            signInWithCredential(credential);
        }
    }

    private void signInWithCredential(PhoneAuthCredential credential) {
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            customLoadingDialog.stopLoadingDialog();
                            startActivity(new Intent(OtpCodeVerifyActivity.this, VerificationSuccessMessageActivity.class)
                                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        } else {
                            customLoadingDialog.stopLoadingDialog();
                            Toast.makeText(OtpCodeVerifyActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }


    private void startCountdown() {
        countDownTimer = new CountDownTimer(timeLeft, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeft = millisUntilFinished;
                int sec = (int) ((timeLeft / 1000) % 60);
                String timeLeftFormatted = String.format(Locale.getDefault(), "%02d", sec);
                timerTextView.setText(timeLeftFormatted + " sec");
            }

            @Override
            public void onFinish() {
                timerTextView.setVisibility(View.INVISIBLE);
                resendTextView.setVisibility(View.VISIBLE);
            }
        }.start();
    }
}
