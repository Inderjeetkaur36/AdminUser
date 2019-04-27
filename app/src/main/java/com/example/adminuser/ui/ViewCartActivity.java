package com.example.adminuser.ui;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.adminuser.R;
import com.example.adminuser.adapter.RecyclerAdapter;
import com.example.adminuser.listener.OnRecyclerItemClickListener;
import com.example.adminuser.model.Shoes;
import com.example.adminuser.model.Util;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ViewCartActivity extends AppCompatActivity implements OnRecyclerItemClickListener{

    RecyclerView recyclerView;
    ArrayList<Shoes> list;

    Shoes shoes;
    RecyclerAdapter recyclerAdapter;
    RelativeLayout relativeLayout;

    int position;
    FirebaseAuth auth;
    FirebaseFirestore db;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cart);

        getSupportActionBar().setTitle("View Cart");
        relativeLayout = findViewById(R.id.relative);
        recyclerView = findViewById(R.id.recyclerView);
        list = new ArrayList<>();

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        firebaseUser = auth.getCurrentUser();

        if(Util.isInternetConnected(this)) {
            fetchCartFromFirebase();
        }else{
            Toast.makeText(ViewCartActivity.this,"Please Connect to Internet and Try Again",Toast.LENGTH_LONG).show();
        }
    }

    public void fetchCartFromFirebase(){

        db.collection("Persons").document(firebaseUser.getUid())
                .collection("Cart").get()
                .addOnCompleteListener(this, new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isComplete()) {

                            list = new ArrayList<>();

                            QuerySnapshot querySnapshot = task.getResult();
                            List<DocumentSnapshot> documentSnapshots = querySnapshot.getDocuments();

                            for (DocumentSnapshot snapshot : documentSnapshots) {
                                String docId = snapshot.getId();
                                Shoes shoes = snapshot.toObject(Shoes.class);
                                shoes.docId = docId;
                                list.add(shoes);
                            }

                            getSupportActionBar().setTitle("Products " );

                            recyclerAdapter = new RecyclerAdapter(ViewCartActivity.this, R.layout.bluepop_item, list);

                            recyclerAdapter.setOnRecyclerItemClickListener(ViewCartActivity.this);

                            final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ViewCartActivity.this);
                            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                            recyclerView.setLayoutManager(linearLayoutManager);
                            recyclerView.setAdapter(recyclerAdapter);

                        } else {
                            Toast.makeText(ViewCartActivity.this, "Some Error", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    @Override
    public void onItemClick(int position) {

        this.position = position;
        shoes = list.get(position);
    }
}

