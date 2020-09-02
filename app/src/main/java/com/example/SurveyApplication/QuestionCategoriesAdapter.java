package com.example.SurveyApplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.SurveyApplication.Model.QuestionCategoryModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class QuestionCategoriesAdapter extends RecyclerView.Adapter<QuestionCategoriesAdapter.ViewHolder> {

    List<QuestionCategoryModel> questionCategoryModelList;

    public QuestionCategoriesAdapter(List<QuestionCategoryModel> questionCategoryModelList) {
        this.questionCategoryModelList = questionCategoryModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.question_category_item, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setData(questionCategoryModelList.get(position).getCategoryImageURL(),
                questionCategoryModelList.get(position).getCategoryName());
    }

    @Override
    public int getItemCount() {
        return questionCategoryModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.categoryImageView)
        ImageView categoryImageView;
        @BindView(R.id.categoryNameTextView)
        TextView categoryNameTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void setData(String url, String text) {
            Glide.with(itemView.getContext()).load(url).into(categoryImageView);
            categoryNameTextView.setText(text);
        }
    }
}
