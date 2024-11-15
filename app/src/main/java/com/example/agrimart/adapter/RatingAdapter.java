package com.example.agrimart.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.agrimart.R;
import com.example.agrimart.data.model.Rating;
import com.example.agrimart.viewmodel.ProductRatingFragmentViewModel;

import java.util.List;

import per.wsj.library.AndRatingBar;

public class RatingAdapter extends RecyclerView.Adapter<RatingAdapter.RatingViewHolder> {

    private final Context context;
    private final List<Rating> ratings;
    private ProductRatingFragmentViewModel productRatingFragmentViewModel;

    // Constructor
    public RatingAdapter(Context context, List<Rating> ratings, ProductRatingFragmentViewModel productRatingFragmentViewModel) {
        this.context = context;
        this.ratings = ratings;
        this.productRatingFragmentViewModel = productRatingFragmentViewModel;
    }

    @NonNull
    @Override
    public RatingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_rating_product, parent, false);
        return new RatingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RatingViewHolder holder, int position) {
        Rating rating = ratings.get(position);

        productRatingFragmentViewModel.fetchUserProfile(rating.getUserId());

        productRatingFragmentViewModel.getFullNameLiveData().observeForever(fullName -> {
            holder.userName.setText(fullName);  // Update full name
        });

        productRatingFragmentViewModel.getUserImageLiveData().observeForever(userImage -> {
            if (userImage != null && !userImage.isEmpty()) {
                Glide.with(holder.itemView.getContext())
                        .load(userImage)
                        .apply(RequestOptions.circleCropTransform()) // Bo tròn ảnh khi tải lên
                        .placeholder(R.drawable.user_img) // ảnh mặc định khi đang tải
                        .error(R.drawable.user_img) // ảnh mặc định nếu URL không tồn tại hoặc tải ảnh lỗi
                        .into(holder.userImage);
            } else {
                Glide.with(holder.itemView.getContext())
                        .load(R.drawable.error_image)
                        .apply(RequestOptions.circleCropTransform()) // Bo tròn ảnh mặc định
                        .into(holder.userImage);
            }
        });

        if (rating.getRating() != null) {
            holder.ratingBar.setRating(Float.parseFloat(rating.getRating()));
        } else {
            holder.ratingBar.setRating(0);  // Gán giá trị mặc định là 0 nếu null
        }


        holder.date.setText(rating.getUpdatedAt());
        holder.review.setText(rating.getReview());
    }

    @Override
    public int getItemCount() {
        return ratings.size();
    }

    // ViewHolder cho Adapter
    public static class RatingViewHolder extends RecyclerView.ViewHolder {
        TextView userName;
        AndRatingBar ratingBar;
        TextView date;
        TextView review;
        ImageView userImage;

        public RatingViewHolder(@NonNull View itemView) {
            super(itemView);

            // Ánh xạ các thành phần từ layout item
            userName = itemView.findViewById(R.id.itemName);
            ratingBar = itemView.findViewById(R.id.itemRatingBar);
            date = itemView.findViewById(R.id.itemDate);
            review = itemView.findViewById(R.id.itemReview);
            userImage = itemView.findViewById(R.id.userImage);
        }
    }
}
