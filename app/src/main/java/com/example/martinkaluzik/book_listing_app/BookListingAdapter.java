package com.example.martinkaluzik.book_listing_app;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class BookListingAdapter extends ArrayAdapter<Book> {

    private static class ViewHolder {
        public ImageView HolderCover;
        public TextView HolderTitle;
        public TextView HolderAuthor;
    }

    public BookListingAdapter(Context context, ArrayList<Book> aBooks) {
        super(context, 0, aBooks);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final Book book = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.activity_book_row, parent, false);
            viewHolder.HolderCover = (ImageView) convertView.findViewById(R.id.CoverImageView);
            viewHolder.HolderTitle = (TextView) convertView.findViewById(R.id.titleTextView);
            viewHolder.HolderAuthor = (TextView) convertView.findViewById(R.id.authorTextView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // Populate the data into the template view using the data object
        viewHolder.HolderTitle.setText(book.getName());
        viewHolder.HolderAuthor.setText(book.getAuthorName());
        Picasso.with(getContext()).load(Uri.parse(book.getImageUrl())).error(R.drawable.no_cover).into(viewHolder.HolderCover);
        return convertView;
    }
}