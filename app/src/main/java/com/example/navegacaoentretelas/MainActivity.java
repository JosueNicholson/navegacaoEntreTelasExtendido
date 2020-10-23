package com.example.navegacaoentretelas;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.navegacaoentretelas.adapters.BookAdapter;
import com.example.navegacaoentretelas.models.Book;
import com.example.navegacaoentretelas.services.CloudFirestore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.EventListener;

public class MainActivity extends AppCompatActivity {
    public static final int REQUEST_ADD = 1;
    public static final int RESULT_CANCEL = 2;
    public static final int REQUEST_UPDATE = 3;
    public static final int REQUEST_DELETE = 4;

    CloudFirestore cd;

    private static int id = 1;
    ArrayList<Book> books;
    BookAdapter bookAdapter;
    ListView booksListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        books = new ArrayList<Book>();
        bookAdapter = new BookAdapter(this, R.layout.book_card, books);
        booksListView = findViewById(R.id.bookListView);
        booksListView.setAdapter(bookAdapter);
        cd = new CloudFirestore();

        cd.getBooks(bookAdapter, books);

        booksListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startEditarLivroActivity(view, position);
            }
        });
    }

    public void startEditarLivroActivity(View view, int position){
        Intent intent = new Intent(view.getContext(), CadastrarLivro.class);
        Book book = books.get(position);

        String bookName = book.getBookName();
        String authorName = book.getAuthorName();
        int releaseYear = book.getReleaseYear();
        String id = book.getId();

        System.out.println(bookName + " " + authorName + " " + releaseYear);
        intent.putExtra("id", id);
        intent.putExtra("bookName",  bookName);
        intent.putExtra("authorName",  authorName);
        intent.putExtra("releaseYear",  releaseYear);

        intent.putExtra("position", position);
        startActivityForResult(intent, REQUEST_UPDATE);

    }

    public void startCadastrarLivroActivity(View view) {
        Intent intent = new Intent(view.getContext(), CadastrarLivro.class);
        startActivityForResult(intent, REQUEST_ADD);
    }

    public void adicionarNaLista(Intent data){
        if(data.getExtras() != null){
            String bookName = (String) data.getExtras().get("bookName");
            String authorName = (String) data.getExtras().get("authorName");
            int releaseYear = (int) data.getExtras().get("releaseYear");

            cd.addBook(bookName, authorName, releaseYear, bookAdapter, books);
            Toast.makeText(this, "O livro " + bookName + " foi adicionado!", Toast.LENGTH_LONG).show();
        }
    }
    
    public void deletarLivro(Intent data){
        int position = (int) data.getExtras().get("position");
        String id = (String) data.getExtras().get("id");
        cd.deleteBook(id, bookAdapter, books, position);
        Toast.makeText(this, "O livro " + books.get(position).getBookName() + " foi deletado!", Toast.LENGTH_LONG).show();
    }


    public void editarLivro(Intent data){
        String bookName = (String) data.getExtras().get("bookName");
        String authorName = (String) data.getExtras().get("authorName");
        int releaseYear = (int) data.getExtras().get("releaseYear");
        int position = (int) data.getExtras().get("position");
        String id = (String) data.getExtras().get("id");

        cd.updateBook(bookName, authorName, releaseYear, id, bookAdapter, books, position);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode){ 
            case REQUEST_ADD:
                adicionarNaLista(data);
                break;
            case RESULT_CANCEL:
                Toast.makeText(this, "cancelado", Toast.LENGTH_SHORT).show();
                break;
            case REQUEST_UPDATE:
                editarLivro(data);
                break;
            case REQUEST_DELETE:
                deletarLivro(data);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + resultCode);
        }
    }

}