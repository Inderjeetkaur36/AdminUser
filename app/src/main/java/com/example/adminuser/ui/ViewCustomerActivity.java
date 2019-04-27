package com.example.adminuser.ui;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.adminuser.R;
import com.example.adminuser.adapter.CustomerAdapter;
import com.example.adminuser.listener.OnRecyclerCusItemClickListener;
import com.example.adminuser.model.Customer;
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

public class ViewCustomerActivity extends AppCompatActivity implements OnRecyclerCusItemClickListener {

    RecyclerView recyclerViewCus;
    ArrayList<Customer> list;

    Customer customer;
    int position;
    CustomerAdapter customerAdapter;
    RelativeLayout relativeLayout;

    FirebaseAuth auth;
    FirebaseFirestore db;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_customer);

        relativeLayout = findViewById(R.id.relative);
        recyclerViewCus = findViewById(R.id.recyclerViewCus);
        list = new ArrayList<Customer>();

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        firebaseUser = auth.getCurrentUser();

        if (Util.isInternetConnected(this)) {
            fetchCustomersFromFirebase();
        } else {
            Toast.makeText(ViewCustomerActivity.this, "Please Connect to Internet and Try Again", Toast.LENGTH_LONG).show();
        }
    }

    void fetchCustomersFromFirebase() {

        db.collection("Persons").get()
                .addOnCompleteListener(this, new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isComplete()) {

                            list = new ArrayList<Customer>();

                            QuerySnapshot querySnapshot = task.getResult();
                            List<DocumentSnapshot> documentSnapshots = querySnapshot.getDocuments();

                            for (DocumentSnapshot snapshot : documentSnapshots) {
                                String docId = snapshot.getId();
                                Customer customer = snapshot.toObject(Customer.class);
                                customer.docId = docId;
                                list.add(customer);
                            }

                            getSupportActionBar().setTitle("Total Customer :" +list.size());

                            customerAdapter = new CustomerAdapter(ViewCustomerActivity.this, R.layout.customer_item, list);
                            customerAdapter.setOnRecyclerCusItemClickListener(ViewCustomerActivity.this);
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ViewCustomerActivity.this);
                            recyclerViewCus.setLayoutManager(linearLayoutManager);
                            recyclerViewCus.setAdapter(customerAdapter);

                        } else {
                            Toast.makeText(ViewCustomerActivity.this, "Some Error", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }


    @Override
    public void ItemClick(int position) {
        this.position = position ;
        customer = list.get(position);

        //Intent intent = new Intent(ViewCustomerActivity.this,ViewCartActivity.class);
        //startActivity(intent);
    }
}