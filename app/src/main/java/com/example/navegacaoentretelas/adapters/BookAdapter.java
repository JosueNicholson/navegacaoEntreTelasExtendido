package com.example.navegacaoentretelas.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.navegacaoentretelas.CadastrarLivro;
import com.example.navegacaoentretelas.R;
import com.example.navegacaoentretelas.models.Book;

import java.util.ArrayList;
import java.util.List;

public class BookAdapter extends ArrayAdapter<Book> {
    private Context context;
    private List<Book> books;
    private int resource;

    public BookAdapter(@NonNull Context context, int resource, ArrayList<Book> books) {
        super(context, resource, books);
        this.context = context;
        this.resource = resource;
        this.books = books;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view ;
        ViewHolder holder;
        if( convertView == null) {
            view = LayoutInflater.from(context).inflate(resource, parent, false);
            holder = new BookAdapter.ViewHolder(view);
            view.setTag(holder);
        } else {
            view = convertView;
            holder = (BookAdapter.ViewHolder) view.getTag();
        }
        Book book = books.get(position);
        holder.bookName.setText(book.getBookName());
        holder.authorName.setText(book.getAuthorName());
        holder.releaseYear.setText(Integer.toString(book.getReleaseYear()));
        return view;
    }

    private class ViewHolder{
        final TextView bookName;
        final TextView authorName;
        final TextView releaseYear;

        public ViewHolder(View view){
            this.bookName = view.findViewById(R.id.bookNameTextView);
            this.authorName = view.findViewById(R.id.authorNameTextView);
            this.releaseYear = view.findViewById(R.id.yearTextView);
        }
    }
}
