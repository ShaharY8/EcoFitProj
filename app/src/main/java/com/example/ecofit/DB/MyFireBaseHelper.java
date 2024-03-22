package com.example.ecofit.DB;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Collection;
import java.util.Map;
import java.util.TooManyListenersException;

public class MyFireBaseHelper {


    private Context context;

    public MyFireBaseHelper(Context context){
        this.context = context;
    }

    // Add a new document with a generated ID
    public void AddDocument(FirebaseFirestore db, Map<String,Object> taskUserList,String whichTask){

        db.collection(whichTask)
                .add(taskUserList)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });

    }

    public void ReadDocument(FirebaseFirestore db, String whichTask){

        db.collection(whichTask)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> taskUserList) {
                        if (taskUserList.isSuccessful()) {
                            for (QueryDocumentSnapshot document : taskUserList.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                Toast.makeText(context, "" + document.getString("name") + ", " + document.getString("phone") , Toast.LENGTH_SHORT).show();

                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", taskUserList.getException());
                        }
                    }
                });
    }

}
