package com.example.ecofit.DB;

import static android.app.PendingIntent.getActivity;
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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TooManyListenersException;
import java.util.concurrent.CompletableFuture;

public class MyFireBaseHelper {


    private Context context;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public MyFireBaseHelper(Context context){
        this.context = context;
    }

    // Add a new document with a generated ID
    public void AddDocument(Map<String,Object> taskUserList,String whichTask){

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


    public interface gotUser
    {
        void onGotUser(LinkedList<String> name, LinkedList<String> phone);
    }

    public void ReadDocument(String whichTask, gotUser callback) {

        db.collection(whichTask)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> taskUserList) {

                        LinkedList<String> tempList1, tempList2;
                        tempList1 = new LinkedList<>();
                        tempList2 = new LinkedList<>();

                        if (taskUserList.isSuccessful()) {
                            for (QueryDocumentSnapshot document : taskUserList.getResult()) {
                                tempList1.add(document.getData().get("name").toString());
                                tempList2.add(document.getData().get("phone").toString());
                            }
                            callback.onGotUser(tempList1, tempList2);
                        } else {
                            Log.w(TAG, "Error getting documents.", taskUserList.getException());
                        }
                    }
                });

    }

    public interface getTasks
    {
        void onGetTasks(LinkedList<String> TaskName,LinkedList<String> title, LinkedList<String> details);
    }
    public void GetAllTasks(getTasks callback) {

        db.collection("AllTasks")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> taskUserList) {

                        LinkedList<String> tempList1, tempList2,tempList3;
                        tempList1 = new LinkedList<>();
                        tempList2 = new LinkedList<>();
                        tempList3 = new LinkedList<>();

                        if (taskUserList.isSuccessful()) {
                            for (QueryDocumentSnapshot document : taskUserList.getResult()) {

                                tempList1.add(document.getData().get("title").toString());
                                tempList2.add(document.getData().get("details").toString());
                                tempList3.add(document.getData().get("TaskName").toString());
                            }
                            callback.onGetTasks(tempList3, tempList1, tempList2);
                        } else {
                            Log.w(TAG, "Error getting documents.", taskUserList.getException());
                        }
                    }
                });

    }

    public interface UserExistenceCallback {
        void onUserExistenceChecked(boolean userExists);
    }

    public void checkIfUserExists(String whichTask,String phone, UserExistenceCallback callback) {


        if(whichTask == "UsersList"){

            Query query = db.collection(whichTask).whereEqualTo("phone", phone);


            query.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    boolean phoneExists = false;
                    for (DocumentSnapshot document : task.getResult()) {
                        if (document.exists()) {
                            // Phone exists
                            callback.onUserExistenceChecked(true);
                            phoneExists = true;
                            break; // exit loop since phone is found
                        }
                    }
                    if (!phoneExists) {
                        // Phone doesn't exist
                        callback.onUserExistenceChecked(false);
                    }
                } else {
                    // Error retrieving documents
                    callback.onUserExistenceChecked(false); // Indicate failure to check existence
                }
            });
        }
        else if(whichTask == "AllTasks"){
            Query query = db.collection(whichTask).whereEqualTo("TaskName", phone);

            query.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    boolean phoneExists = false;
                    for (DocumentSnapshot document : task.getResult()) {
                        if (document.exists()) {
                            // Phone exists
                            callback.onUserExistenceChecked(true);
                            phoneExists = true;
                            break; // exit loop since phone is found
                        }
                    }
                    if (!phoneExists) {
                        // Phone doesn't exist
                        callback.onUserExistenceChecked(false);
                    }
                } else {
                    // Error retrieving documents
                    callback.onUserExistenceChecked(false); // Indicate failure to check existence
                }
            });
        }
        else{
            Toast.makeText(context, "error", Toast.LENGTH_SHORT).show();
        }


    }
    public void DelFromFireStore(String whichTask, int idToDel){
        db.collection(whichTask)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> taskUserList) {

                        if (taskUserList.isSuccessful()) {
                            int i = 0;
                            for (QueryDocumentSnapshot document : taskUserList.getResult()) {
//        ..................................................DeleteUser

                                if (i == idToDel){
                                    db.collection(whichTask).document(document.getId()).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {


                                        }
                                    });
                                }

                                i++;
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", taskUserList.getException());
                        }
                    }
                });


    }


    public interface whenDone {
        void whenDoneToUpdate();
    }
    public void GetDataToUpdate(String phone , String name, String lname, String pass, int price, String whichTask, int idToDel, int toApp, whenDone callBack){
        db.collection("UsersList")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> taskUserList) {

                        if (taskUserList.isSuccessful()) {

                            for (QueryDocumentSnapshot document : taskUserList.getResult()) {
                                if(document.getData().get("phone").toString().equals(phone)){

                                    Map<String,Object> userData = new HashMap<>();
                                    if(toApp == -1){
                                        userData.put("name", document.getData().get("name").toString());
                                        userData.put("Lname",  document.getData().get("Lname").toString());
                                        userData.put("pass",  document.getData().get("pass").toString());
                                        userData.put("phone", phone);
                                        userData.put("price",  Integer.valueOf(document.getData().get("price").toString())+5);
                                        db.collection("UsersList").document(document.getId()).update(userData).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()) {
                                                    DelFromFireStore(whichTask,idToDel);
                                                }
                                            }
                                        });
                                    } else if (toApp == 0) {
                                       // Toast.makeText(context, "bbbbbbbbbbbbbbbbbbb", Toast.LENGTH_SHORT).show();
                                        userData.put("name", document.getData().get("name").toString());
                                        userData.put("Lname",  document.getData().get("Lname").toString());
                                        userData.put("pass",  document.getData().get("pass").toString());
                                        userData.put("phone", phone);
                                        userData.put("price",  Integer.valueOf(document.getData().get("price").toString())-35);
                                        db.collection("UsersList").document(document.getId()).update(userData).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()) {
                                                   // Toast.makeText(context, "bbbbbbbbbbbbbbbbbbb", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                        callBack.whenDoneToUpdate();
                                    }
                                    else if(toApp == 1){
                                        userData.put("name", name);
                                        userData.put("Lname", lname);
                                        userData.put("pass", pass);
                                        userData.put("phone", phone);
                                        userData.put("price", price );
                                        db.collection("UsersList").document(document.getId()).update(userData).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()) {

                                                    Toast.makeText(context, "המשתמש עודכן בהצלחה", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                    }




                                }
                            }

                        } else {
                            Log.w(TAG, "Error getting documents.", taskUserList.getException());
                        }
                    }
                });


    }

    public interface gotCoin
    {
        public void onGotCoin(int coin);
    }
    public void GetNumberOfCoinsByPhone(String phone, gotCoin callback) {
        db.collection("UsersList").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult()) {

                                if(document.getData().get("phone").toString().equals(phone)){
                                    callback.onGotCoin(Integer.valueOf(document.getData().get("price").toString()));
                                }

                            }
                        } else {
                            callback.onGotCoin(-99);
                        }

                    }
                });
    }



}
