package com.example.the_anil.vocabbuilder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Random;

public class Wordlist extends AppCompatActivity {

    private ArrayList<String> mUsernames = new ArrayList<>();
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wordlist);

        mListView = (ListView) findViewById(R.id.listView);
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mUsernames);

        mListView.setAdapter(arrayAdapter);

        String alphabet[] = {"-","A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};



        for(int i=0;i<=5; i++) {
            String lex1 = (alphabet[new Random().nextInt(alphabet.length)]);
            String lex2 = (alphabet[new Random().nextInt(alphabet.length)]);
            String lex3 = (alphabet[new Random().nextInt(alphabet.length)]);

            DatabaseReference mRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://dictionary-d1392.firebaseio.com/Lexical_Dictionary/"+lex1);

            mRef.orderByKey().startAt(lex1 + lex2 + lex3).endAt(lex1+"\\uf8ff").limitToFirst(1).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                    String data = dataSnapshot.getKey();
                    String value = dataSnapshot.getValue(String.class);

                    mUsernames.add(data + " : \n" + value + "\n");
                    arrayAdapter.notifyDataSetChanged();

                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }
}
