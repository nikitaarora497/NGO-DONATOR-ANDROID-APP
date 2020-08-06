package com.example.ngo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class Available extends AppCompatActivity{

    private FirebaseFirestore firebaseFirestore;
    private FirestoreRecyclerAdapter adapter;

    String a_name,a_address,a_contact,a_no,a_amount,user_balance,a_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available);

        firebaseFirestore = FirebaseFirestore.getInstance();
        RecyclerView mfirestore_list = findViewById(R.id.firestore_list);

        Query query = firebaseFirestore.collection("Available_ngo");
        FirestoreRecyclerOptions<Ngo> options = new FirestoreRecyclerOptions.Builder<Ngo>().setQuery(query,Ngo.class).build();

        adapter = new FirestoreRecyclerAdapter<Ngo, NgoViewHoder>(options) {
            @NonNull
            @Override
            public NgoViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_single,parent,false);
                return new NgoViewHoder(view) ;
            }

            @Override
            protected void onBindViewHolder(@NonNull NgoViewHoder holder, int position, @NonNull Ngo model) {
              holder.list_name.setText(model.getName());
              holder.list_address.setText(model.getAddress());

              a_name = model.getName();
              a_address = model.getAddress();
              a_contact = model.getContact();
              a_no = model.getDonated();
              a_amount = model.getAmount();
              a_id = model.getId();

            }
        };

        mfirestore_list.setHasFixedSize(true);
        mfirestore_list.setLayoutManager(new LinearLayoutManager(this));
        mfirestore_list.setAdapter(adapter);

        //Receiving the balance
        Bundle i = getIntent().getExtras();
        user_balance = i.getString("user_balance");
    }

    private class NgoViewHoder extends RecyclerView.ViewHolder {

        private TextView list_name,list_address;

        public NgoViewHoder(@NonNull View itemView) {
            super(itemView);
            list_name = itemView.findViewById(R.id.name);  //listitemsingle
            list_address = itemView.findViewById(R.id.address);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                   Intent avail_ngo = new Intent(Available.this,Details.class);
                    avail_ngo.putExtra("name",String.valueOf(a_name));
                    avail_ngo.putExtra("address",String.valueOf(a_address));
                    avail_ngo.putExtra("contact",String.valueOf(a_contact));
                    avail_ngo.putExtra("donated",String.valueOf(a_no));
                    avail_ngo.putExtra("amount",String.valueOf(a_amount));
                    avail_ngo.putExtra("user_balance",String.valueOf(user_balance));
                    avail_ngo.putExtra("id",String.valueOf(a_id));
                    startActivity(avail_ngo);

                }
            });
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
