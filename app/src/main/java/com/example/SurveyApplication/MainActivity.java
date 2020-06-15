package com.example.SurveyApplication;

import android.animation.Animator;
import android.os.Bundle;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    private int count=0;
    private int position=0;
    private List<QuestionModel>questionList=new ArrayList<>();

    @BindView(R.id.optionLayout)
    LinearLayout optionLayout;
    @BindView(R.id.questionTextView)
    TextView questionTextView;
    @BindView(R.id.saveButton)
    Button saveButton;
    @BindView(R.id.nextButton)
    Button nextButton;
    @BindView(R.id.questionCounter)
    TextView questionCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        addQuestion();
        playAnim(questionTextView,0,questionList.get(position).getQuestion());
    }

    private void addQuestion() {
        questionList.add(new QuestionModel("question1","A","B","C","D"));
        questionList.add(new QuestionModel("question2","E","F","G","H"));
        questionList.add(new QuestionModel("question3","I","J","K","L"));
        questionList.add(new QuestionModel("question4","M","N","O","P"));
        questionList.add(new QuestionModel("question5","Q","R","S","T"));
        questionList.add(new QuestionModel("question6","U","V","W","X"));
    }


    @OnClick(R.id.nextButton)
    void onNextButtonClicked(){
        count=0;
        position++;
        if (position==questionList.size()-1){
            saveButton.setVisibility(View.VISIBLE);
            nextButton.setVisibility(View.GONE);
        }
        playAnim(questionTextView,0,questionList.get(position).getQuestion());
    }

    private void playAnim(View view, int value,String data){
        view.animate().alpha(value).scaleX(value).scaleY(value)
                .setDuration(500).setStartDelay(100).setInterpolator(new DecelerateInterpolator()).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                if (value==0 && count<4){
                    String option="";
                    if (count==0){
                        option=questionList.get(position).getOptionA();
                    }else if(count==1){
                        option=questionList.get(position).getOptionB();
                    }else if(count==2){
                        option=questionList.get(position).getOptionC();
                    }else if(count==3){
                        option=questionList.get(position).getOptionD();
                    }
                    playAnim(optionLayout.getChildAt(count),0,option);
                    count ++;
                }
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                ((TextView)view).setText(data);
                questionCounter.setText(position+1+"/"+questionList.size());
                if (value==0){
                    playAnim(view,1,data);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }
}