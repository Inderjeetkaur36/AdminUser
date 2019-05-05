package com.example.adminuser.ui;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.adminuser.MainActivity;
import com.example.adminuser.R;

import com.example.adminuser.model.Shoes;
import com.example.adminuser.model.Util;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddProductActivity extends AppCompatActivity {

    @BindView(R.id.eTxtId)
    EditText eTxtId;

    @BindView(R.id.eTxtSize)
    EditText eTxtSize;

    @BindView(R.id.eTxtName)
    EditText eTxtName;

    @BindView(R.id.eTxtPrice)
    EditText eTxtPrice;

    @BindView(R.id.eTxtColor)
    EditText eTxtColor;

    @BindView(R.id.eTxtUrl)
    EditText eTxtUrl;

    @BindView(R.id.buttonAddProduct)
    Button buttonAddProduct;

    @BindView(R.id.buttonAddImage)
    Button buttonaddImage;

    Shoes shoes;
    ContentResolver resolver;

    boolean updateMode;
    FirebaseAuth auth;
    FirebaseUser firebaseUser;
    FirebaseFirestore db;
    ProgressDialog progressDialog;

    void initViews(){

        resolver = getContentResolver();

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        firebaseUser = auth.getCurrentUser();

        shoes = new Shoes();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);

        buttonaddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AddProductActivity.this, MainActivity.class);
                startActivityForResult(intent,101);

            }
        });

        buttonAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                shoes.id = eTxtId.getText().toString();
                shoes.size = eTxtSize.getText().toString();
                shoes.name = eTxtName.getText().toString();
                shoes.price = eTxtPrice.getText().toString();
                shoes.color = eTxtColor.getText().toString();
                shoes.imageUrl = eTxtUrl.getText().toString();

                if(Util.isInternetConnected(AddProductActivity.this)) {
                    progressDialog.show();

                    saveProductInFirebase();
                }else{
                    Toast.makeText(AddProductActivity.this,"Please Connect to Internet",Toast.LENGTH_LONG).show();
                }
            }
        });

        Intent rcv = getIntent();
        updateMode = rcv.hasExtra("keyShoes");

        if(updateMode){
            shoes = (Shoes) rcv.getSerializableExtra("keyShoes");
            eTxtId.setText(shoes.id);
            eTxtSize.setText(shoes.size);
            eTxtName.setText(shoes.name);
            eTxtPrice.setText("₹ "+shoes.price);
            eTxtColor.setText(shoes.color);
            buttonAddProduct.setText("Update Products");

        }
    }

    void saveProductInFirebase() {

        if(updateMode){

            db.collection("Products").document(shoes.docId)
                    .set(shoes)
                    .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(Task<Void> task) {
                            if(task.isComplete()){
                                Toast.makeText(AddProductActivity.this,"Updation Finished",Toast.LENGTH_LONG).show();
                                progressDialog.dismiss();
                                finish();
                            }else{
                                Toast.makeText(AddProductActivity.this,"Updation Failed",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }else {

            db.collection("Products").add(shoes)
                    .addOnCompleteListener(this, new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(Task<DocumentReference> task) {
                            if(task.isComplete()){
                                Toast.makeText(AddProductActivity.this,"Product saved ",Toast.LENGTH_LONG).show();
                                progressDialog.dismiss();
                                clearFields();
                            }
                        }
                    });
        }

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        ButterKnife.bind(this);
        initViews();
    }

    void clearFields(){
        eTxtId.setText("");
        eTxtSize.setText("");
        eTxtName.setText("");
        eTxtPrice.setText("");
        eTxtColor.setText("");
        eTxtUrl.setText("");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == 101 && resultCode == 201) {
            String Url = data.getStringExtra("KeyUrl");

            eTxtUrl.setText(Url);
        }
    }
}
