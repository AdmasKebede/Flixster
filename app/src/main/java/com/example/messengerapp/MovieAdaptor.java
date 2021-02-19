package com.example.messengerapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.bumptech.glide.Glide;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;

public class MovieAdaptor extends RecyclerView.Adapter<MovieAdaptor.ViewHolder> {

    private List<Movie> movieList;
    private Context context;

    public MovieAdaptor(List<Movie> movies){
        movieList = movies;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View movieView = inflater.inflate(R.layout.movie_view,parent,false);

        ViewHolder viewHolder = new ViewHolder(movieView);
        context=parent.getContext();
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Movie movie = movieList.get(position);
        holder.titleTextView.setText(movie.title);
        holder.descriptionTextView.setText(movie.description);
        holder.ratingTextView.setText(movie.rating);
        Glide.with(context).load(movie.imageLink).into(holder.imageLinkImageView);
        //holder.imageLinkImageView.setImageURI(movie.imageLink);


    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView titleTextView;
        public TextView descriptionTextView;
        public TextView ratingTextView;
        public ImageView imageLinkImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            titleTextView =  itemView.findViewById(R.id.movie_title_textView);
            descriptionTextView =  itemView.findViewById(R.id.movie_description_textView);
            imageLinkImageView = itemView.findViewById(R.id.movieImage);
            ratingTextView = itemView.findViewById(R.id.movie_rate_textView);
        }
    }
}
