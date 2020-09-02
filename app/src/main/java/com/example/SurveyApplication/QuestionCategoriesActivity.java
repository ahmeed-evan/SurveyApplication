package com.example.SurveyApplication;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.SurveyApplication.Model.QuestionCategoryModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class QuestionCategoriesActivity extends AppCompatActivity {

    private List<QuestionCategoryModel> questionCategoryModelList = new ArrayList<>();
    private QuestionCategoriesAdapter questionCategoriesAdapter;
    private CustomLoadingDialog customLoadingDialog;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_categories);
        ButterKnife.bind(this);
        customLoadingDialog = new CustomLoadingDialog(this);

        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                addValue();
            }
        });
        recyclerViewImplementation();
    }

    private void recyclerViewImplementation() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        questionCategoriesAdapter = new QuestionCategoriesAdapter(questionCategoryModelList);
        recyclerView.setAdapter(questionCategoriesAdapter);

    }

    private void addValue() {
        customLoadingDialog.startLoadingDialog();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("Category").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    questionCategoryModelList.add(snapshot.getValue(QuestionCategoryModel.class));
                }
                questionCategoriesAdapter.notifyDataSetChanged();
                customLoadingDialog.stopLoadingDialog();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                customLoadingDialog.stopLoadingDialog();
            }
        });
    }
}
