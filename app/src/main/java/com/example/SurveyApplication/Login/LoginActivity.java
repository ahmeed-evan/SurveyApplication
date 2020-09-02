package com.example.SurveyApplication.Login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.SurveyApplication.ConstantKeys;
import com.example.SurveyApplication.R;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    private boolean isValid = false;
    private PhoneAuthProvider phoneAuthProvider;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks onVerificationStateChangedCallbacks;

    @BindView(R.id.phoneNumberEditText)
    EditText phoneNumberEditText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        phoneAuthProvider=PhoneAuthProvider.getInstance(FirebaseAuth.getInstance());

    }

    @OnClick(R.id.signInButton)
    void onSignButtonClicked() {
        String phoneNumber = phoneNumberEditText.getText().toString();
        if (!TextUtils.isEmpty(phoneNumber)) {
            if (formatNumber(phoneNumber)) {
                String validPhoneNumber = "+880" + phoneNumber;
                verificationProcess(validPhoneNumber);
            } else {
                phoneNumberEditText.setText("Invalid Number");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        phoneNumberEditText.setText("");
                    }
                }, 3000);
            }
        }
        return;
    }

    private void verificationProcess(String phoneNumber) {

    onVerificationStateChangedCallbacks=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

        }

        @Override
        public void onVerificationFailed(FirebaseException e) {

        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            startActivity(new Intent(LoginActivity.this, OtpCodeVerifyActivity.class)
                    .putExtra(ConstantKeys.PHONE_NUMBER, phoneNumber).putExtra(ConstantKeys.VERIFICATION_ID,s)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
        }
    };

        phoneAuthProvider.verifyPhoneNumber(phoneNumber,
                45, TimeUnit.SECONDS, LoginActivity.this, onVerificationStateChangedCallbacks);
}

    public boolean formatNumber(String number) {
        if (number.charAt(0) != '0' && number.length() == 10) {
            isValid = true;
        }
        return isValid;
    }
}
