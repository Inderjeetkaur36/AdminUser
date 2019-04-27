package com.example.adminuser.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.adminuser.R;
import com.example.adminuser.listener.OnRecyclerCusItemClickListener;
import com.example.adminuser.listener.OnRecyclerItemClickListener;
import com.example.adminuser.model.Customer;


import java.util.ArrayList;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.ViewHolder> {

    Context context;
    int resource;
    ArrayList<Customer> objects;
    OnRecyclerCusItemClickListener recyclerItemClickListener;

    public void setOnRecyclerCusItemClickListener(OnRecyclerCusItemClickListener recyclerItemClickListener){
        this.recyclerItemClickListener = recyclerItemClickListener;
    }
    public CustomerAdapter(Context context, int resource, ArrayList<Customer> objects) {

        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }
    @NonNull
    @Override
    public CustomerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(context).inflate(resource,viewGroup,false);
        final CustomerAdapter.ViewHolder holder = new CustomerAdapter.ViewHolder(view);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerItemClickListener.ItemClick(holder.getAdapterPosition());

            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final CustomerAdapter.ViewHolder holder, int position) {

        Customer customer = objects.get(position);

        holder.txtName.setText(customer.name);
        holder.txtPhone.setText(customer.phone);
        holder.txtEmail.setText(customer.email);
        holder.txtPassword.setText(customer.password);
        holder.txtAddress.setText(customer.address);
    }

    @Override
    public int getItemCount() {

        return objects.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtName;
        TextView txtPhone;
        TextView txtEmail;
        TextView txtPassword;
        TextView txtAddress;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtName= itemView.findViewById(R.id.txtName);
            txtPhone = itemView.findViewById(R.id.txtPhone);
            txtEmail = itemView.findViewById(R.id.txtEmail);
            txtPassword = itemView.findViewById(R.id.txtPassword);
            txtAddress = itemView.findViewById(R.id.txtAddress);

        }
    }
}
