package com.example.navegacaoentretelas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.navegacaoentretelas.models.Book;

import java.io.Serializable;

public class CadastrarLivro extends AppCompatActivity {
    public static int RESULT_ADD = 1;
    public static int RESULT_CANCEL = 2;
    public static int RESULT_UPDATE = 3;
    public static int RESULT_DELETE = 4;


    EditText bookNameEdtText;
    EditText authorNameEdtText;
    EditText releaseYearEdtText;
    Button deleteBtn;
    Button cancelBtn;
    String itemId;
    int position = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_livro);

        bookNameEdtText = findViewById(R.id.edtTextBookName);
        authorNameEdtText = findViewById(R.id.edtTextAuthorName);
        releaseYearEdtText = findViewById(R.id.edtTextReleaseYear);
        deleteBtn = findViewById(R.id.deleteBookBtn);
        deleteBtn.setVisibility(View.INVISIBLE);
        cancelBtn = findViewById(R.id.cancelBtn);

        if(getIntent().getExtras() != null){
            itemId = (String) getIntent().getExtras().get("id");
            String bookName = (String) getIntent().getExtras().get("bookName");
            String authorName = (String) getIntent().getExtras().get("authorName");
            int releaseYear = (int) getIntent().getExtras().get("releaseYear");

            bookNameEdtText.setText(bookName);
            authorNameEdtText.setText(authorName);
            releaseYearEdtText.setText(Integer.toString(releaseYear));
            deleteBtn.setVisibility(View.VISIBLE);
            position = (int) getIntent().getExtras().get("position");

        }
    }

    public void cancelar(View view){
        setResult(RESULT_CANCEL);
        finish();
    }

    public void deletarLivro(View view){
        Intent intent = new Intent();
        System.out.println("Im calling");
        intent.putExtra("position", position);
        intent.putExtra("id", itemId);

        setResult(RESULT_DELETE, intent);
        finish();
    }

    public void salvarLivro(View view){
        String bookNameString = bookNameEdtText.getText().toString();
        String authorNameString = authorNameEdtText.getText().toString();
        int releaseYearNumber = Integer.parseInt(releaseYearEdtText.getText().toString());

//        Book book = new Book(bookNameString, authorNameString, releaseYearNumber);
        Intent returnToMain = new Intent();
        returnToMain.putExtra( "bookName", bookNameString);
        returnToMain.putExtra("authorName", authorNameString);
        returnToMain.putExtra("releaseYear", releaseYearNumber);

        if(position == -1){
            setResult(RESULT_ADD, returnToMain);
        }
        else{
            returnToMain.putExtra("id", itemId);
            returnToMain.putExtra("position", position);
            setResult(RESULT_UPDATE, returnToMain);
        }

        finish();
    }
}