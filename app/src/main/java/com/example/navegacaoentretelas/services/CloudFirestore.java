package com.example.navegacaoentretelas.services;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.navegacaoentretelas.adapters.BookAdapter;
import com.example.navegacaoentretelas.models.Book;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CloudFirestore {

    FirebaseFirestore db;

    public CloudFirestore(){
        db = FirebaseFirestore.getInstance();
    }

    public void addBook(final String bookName, final String authorName, final int releaseYear, final BookAdapter adapter, final ArrayList<Book> books){
        final Map<String, Object> book = new HashMap<>();
        book.put("book_name", bookName);
        book.put("author_name", authorName);
        book.put("release_year", releaseYear);
        db.collection("books").add(book)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference docRef) {
                        books.add(new Book(authorName, bookName, releaseYear, docRef.getId()));
                        adapter.notifyDataSetChanged();
                        Log.d("SUCCESS", "DocumentSnapshot successfully written with id: "+ docRef.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("ERROR", "Error writing document", e);
                    }
                });
    }
    public void updateBook(final String bookName, final String authorName, final int releaseYear, final String id, final BookAdapter adapter, final ArrayList<Book> books, final int position){
        final Map<String, Object> book = new HashMap<>();
        book.put("book_name", bookName);
        book.put("author_name", authorName);
        book.put("release_year", releaseYear);

        db.collection("books").document(id).set(book)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        books.set(position, new Book(authorName, bookName, releaseYear, id));
                        adapter.notifyDataSetChanged();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("ERROR", "Error writing document", e);
                    }
                });
    }

    public void getBooks(final BookAdapter adapter, final ArrayList<Book> books){
        final ArrayList<Book> booksFromWeb = new ArrayList<>();
        db.collection("books").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Map book = document.getData();

                        String bookName = book.get("book_name").toString();
                        String authorName = book.get("author_name").toString();
                        int releaseYear = Integer.parseInt(book.get("release_year").toString());
                        booksFromWeb.add(new Book(authorName, bookName, releaseYear));
                        Log.d("GETTED", document.getId() + " => " + document.getData());
                    }
                    books.addAll(booksFromWeb);
                    adapter.notifyDataSetChanged();
                } else {
                    Log.w("ERROR", "Error getting documents.", task.getException());
                }
            }

        });
    }

    public void deleteBook(String id, final BookAdapter adapter, final ArrayList<Book> books, final int position){
        db.collection("books").document(id)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        books.remove(position);
                        adapter.notifyDataSetChanged();
                        Log.d("SUCCESS", "DocumentSnapshot successfully deleted!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("ERROR", "Error deleting document", e);
                    }
                });
    }


}
