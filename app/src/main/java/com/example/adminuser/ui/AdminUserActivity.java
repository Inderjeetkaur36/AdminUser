package com.example.adminuser.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.adminuser.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdminUserActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.buttonAddProduct)
    Button btnAddProduct;

    @BindView(R.id.buttonViewProduct)
    Button btnViewProduct;

    @BindView(R.id.buttonViewCustomer)
    Button btnViewCustomer;

    @BindView(R.id.buttonViewOrder)
    Button btnViewOrder;

    ProgressDialog progressDialog;

    void initViews() {

        ButterKnife.bind(this);
        btnAddProduct.setOnClickListener(this);
        btnViewProduct.setOnClickListener(this);
        btnViewCustomer.setOnClickListener(this);
        btnViewOrder.setOnClickListener(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_user);

        initViews();
    }
    @Override
    public void onClick(View v) {

        if(v == btnAddProduct){

            Intent intent = new Intent(AdminUserActivity.this,AddProductActivity.class);
            startActivity(intent);

        }else if(v == btnViewProduct){

            Intent intent = new Intent(AdminUserActivity.this,ViewProductActivity.class);
            startActivity(intent);

        }else if(v == btnViewCustomer){

            Intent intent = new Intent(AdminUserActivity.this,ViewCustomerActivity.class);
            startActivity(intent);

        }else{

        }
    }

}