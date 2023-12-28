package com.Users.beefood.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.Users.beefood.R;

import com.Users.beefood.adapter.ChatAdapter;
import com.Users.beefood.model.ChatMessage;
import com.Users.beefood.utils.Utils;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class ChatActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ImageView imgSend;
    EditText edtMess;
    FirebaseFirestore db;
    ChatAdapter adapter;
    List<ChatMessage> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        initView();
        initControl();
        listenMess();
        inserUser();
    }



    private void inserUser() {
        HashMap<String, Object> user = new HashMap<>();
        user.put("id", Utils.user_current.getId());
        user.put("username", Utils.user_current.getUsername());
        db.collection("user").document(String.valueOf(Utils.user_current.getId())).set(user);
    }


    private void initControl() {
        imgSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Kiểm tra xem có dữ liệu trong tin nhắn hay không
                if (TextUtils.isEmpty(edtMess.getText().toString())) {
                    // Hiển thị Toast khi không có tin nhắn
                    Toast.makeText(ChatActivity.this, "Bạn hãy nhập tin nhắn!", Toast.LENGTH_SHORT).show();
                } else {
                    // Gửi tin nhắn đến Fire
                    sendMessToFire();
                }
            }
        });
    }

    private void sendMessToFire() {
        String str_mess = edtMess.getText().toString().trim();
        if (TextUtils.isEmpty(str_mess)){

        }else {
            HashMap<String, Object> message = new HashMap<>();
            message.put(Utils.SENDID, String.valueOf(Utils.user_current.getId()));
            message.put(Utils.RECEIVEDID, Utils.ID_RECEIVED);
            message.put(Utils.MESS, str_mess);
//            message.put(Utils.DATETIME, new Date());
            message.put(Utils.DATETIME, new Date());
            db.collection(Utils.PATH_CHAT).add(message);
            edtMess.setText("");
        }
    }

private void listenMess() {
    db.collection(Utils.PATH_CHAT)
            .whereEqualTo(Utils.SENDID, String.valueOf(Utils.user_current.getId()))
            .whereEqualTo(Utils.RECEIVEDID, Utils.ID_RECEIVED)
            .addSnapshotListener(eventListener);
    db.collection(Utils.PATH_CHAT)
            .whereEqualTo(Utils.SENDID, Utils.ID_RECEIVED)
            .whereEqualTo(Utils.RECEIVEDID,  String.valueOf(Utils.user_current.getId()))
            .addSnapshotListener(eventListener);
}



    private final EventListener<QuerySnapshot> eventListener = (value, error) -> {
        if (error !=null) {
            return;
        }
        if (value !=null) {
            int count = list.size();
            for (DocumentChange documentChange : value.getDocumentChanges()) {
                if (documentChange.getType() == DocumentChange.Type.ADDED) {
                    ChatMessage chatMessage = new ChatMessage();
                    chatMessage.sendid = documentChange.getDocument().getString(Utils.SENDID);
                    chatMessage.receivedid = documentChange.getDocument().getString(Utils.RECEIVEDID);
                        chatMessage.mess = documentChange.getDocument().getString(Utils.MESS);
                    chatMessage.dateObj = documentChange.getDocument().getDate(Utils.DATETIME);
                    chatMessage.datetime = format_date(documentChange.getDocument().getDate(Utils.DATETIME)) ;

                    list.add(chatMessage);
                }
            }


            Collections.sort(list, (obj1, obj2)->obj1.dateObj.compareTo(obj2.dateObj));
            if(count == 0){
                adapter.notifyDataSetChanged();
            }else {
                adapter.notifyItemRangeInserted(list.size(), list.size());
                recyclerView.smoothScrollToPosition(list.size()-1);
            }
//            Collections.sort(list, (obj1, obj2) -> obj1.dateObj.compareTo(obj2.dateObj));
////            Collections.sort(list, (obj1, obj2) ->  obj1.dateObj.compareTo(obj2.dateObj));
//
//            if (count == 0) {
//                // Nếu danh sách ban đầu rỗng, thông báo cho adapter biết rằng toàn bộ danh sách đã thay đổi và cần được cập nhật.
//                adapter.notifyDataSetChanged();
//            } else {
//                // Nếu danh sách không rỗng (đã có tin nhắn trước đó), chỉ thông báo cho adapter biết rằng có tin nhắn mới được thêm vào danh sách.
//                adapter.notifyItemRangeInserted(count, list.size() - count);
//            }

// Cuộn đến tin nhắn mới nhất, nhưng vẫn hiển thị tin nhắn cũ
//            recyclerView.smoothScrollToPosition(list.size() - 1);


        }
    };


    private String format_date(Date date) {
        return new SimpleDateFormat ("MMMM dd, yyyy- hh:mm a", Locale.getDefault()).format(date);

    }
    private void initView() {
        list = new ArrayList<>();
        db = FirebaseFirestore.getInstance();
        recyclerView = findViewById(R.id.recycleview_chat);
        imgSend = findViewById(R.id.imagechat);
        edtMess = findViewById(R.id.edtinputtex);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new ChatAdapter(getApplicationContext(),list,String.valueOf(Utils.user_current.getId()));
        recyclerView.setAdapter(adapter);
    }
    }