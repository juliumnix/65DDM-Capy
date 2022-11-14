package com.example.capy_65ddm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.annotations.NotNull;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatScreen extends AppCompatActivity {
    private FirebaseFirestore db;
    private Boolean isReceiverAvailable = false;


    private ChatItemRecycleViewAdapter adapter = new ChatItemRecycleViewAdapter(this);
    private RecyclerView recyclerView;

    @Override
    protected void onPause() {
        super.onPause();
        db.collection("Usuarios").document(getIntent().getStringExtra("UsuarioAtual")).update("disponivel", 0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        db.collection("Usuarios").document(getIntent().getStringExtra("UsuarioAtual")).update("disponivel", 1);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_screen);
        recyclerView = findViewById(R.id.recyclerViewChat);

        db = FirebaseFirestore.getInstance();
        Intent in = getIntent();


//        getMessages();


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.scrollToPosition(adapter.getSize() - 1);



        TextView nomeUsuario = findViewById(R.id.nomeUsuarioChat);
        nomeUsuario.setText(in.getStringExtra("NomeUsuario"));


        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        TextView campoTexto = findViewById(R.id.mensagemTxt);
        ImageButton sendButton = findViewById(R.id.sendButton);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String, Object> message = new HashMap<>();
                message.put("Enviou", in.getStringExtra("UsuarioAtual"));
                message.put("Recebeu", in.getStringExtra("NomeUsuario"));
                message.put("Mensagem", campoTexto.getText().toString());
                Date date = new Date();
                Timestamp ts = new Timestamp(date.getTime());
                message.put("Date", ts.getTime());
                db.collection("Chats").add(message);
                FCMSend.pushNotification(getBaseContext(), in.getStringExtra("TokenRecebe"), in.getStringExtra("UsuarioAtual"), campoTexto.getText().toString());
                recyclerView.setAdapter(adapter);
                campoTexto.setText("");
                recyclerView.scrollToPosition(adapter.getSize() - 1);




            }
        });

        listenMessages();
        listenAvailabilityOfReceiver();
    }

    private void listenAvailabilityOfReceiver(){
        View onlineIndicator =  findViewById(R.id.online_indicator);
        db.collection("Usuarios").document(getIntent().getStringExtra("NomeUsuario")).addSnapshotListener(ChatScreen.this, (value, error)->{
            if(error != null){
                return;
            }
            if(value!= null){
                if(value.getLong("disponivel") != null){
                    int availability = Objects.requireNonNull(value.getLong("disponivel").intValue());
                    isReceiverAvailable = availability == 1;
                }
            }
            if(isReceiverAvailable){
                onlineIndicator.setVisibility(View.VISIBLE);
            } else{
                onlineIndicator.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void showToast(String message){
        Toast.makeText(getApplicationContext(),message, Toast.LENGTH_SHORT ).show();
    }


    private void listenMessages(){
        Query orderDataUsuarioAtual = db.collection("Chats")
                .whereEqualTo("Enviou", getIntent().getStringExtra("UsuarioAtual"))
                .whereEqualTo("Recebeu", getIntent().getStringExtra("NomeUsuario"));


        Query orderDataNomeUsuario =
            db.collection("Chats")
                    .whereEqualTo("Enviou", getIntent().getStringExtra("NomeUsuario"))
                    .whereEqualTo("Recebeu", getIntent().getStringExtra("UsuarioAtual"));

                    orderDataNomeUsuario.addSnapshotListener(eventListener);
                    orderDataUsuarioAtual.addSnapshotListener(eventListener);

    }

    private final EventListener<QuerySnapshot> eventListener = (value, error) -> {
        if (error != null){
            return;
        }

        if(value != null){
            int count = adapter.getSize();
            for(DocumentChange documentChange: value.getDocumentChanges()) {
                if (documentChange.getType() == DocumentChange.Type.ADDED) {

                    MensagemModel msg = new MensagemModel(documentChange.getDocument().getString("Enviou"),
                            documentChange.getDocument().getString("Recebeu"),
                            documentChange.getDocument().getString("Mensagem"),
                            documentChange.getDocument().getLong("Date")
                            );
                    adapter.add(msg);
                }
            }
            Collections.sort(adapter.getMensagens(), new Comparator<MensagemModel>() {
                @Override
                public int compare(MensagemModel mensagemModel, MensagemModel t1) {
                    return mensagemModel.getDateObjetc().compareTo(t1.getDateObjetc());
                }
            });

            if(count == 0){
                adapter.notifyDataSetChanged();
                recyclerView.scrollToPosition(adapter.getSize() - 1);
            }else {
                adapter.notifyItemRangeChanged(adapter.getSize(), adapter.getSize());
                recyclerView.smoothScrollToPosition(adapter.getSize() - 1);
            }
            recyclerView.setVisibility(View.VISIBLE);
        }

    };



    }
