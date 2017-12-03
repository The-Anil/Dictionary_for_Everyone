package com.example.the_anil.vocabbuilder;

import android.app.PendingIntent;
import android.app.admin.NetworkEvent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

import static com.example.the_anil.vocabbuilder.R.drawable.widget;

/**
 * Implementation of App Widget functionality.
 */
public class Widget extends AppWidgetProvider {

    private static final String MY_BUTTTON_START = "myButtonStart";

    static void updateAppWidget(final Context context, final AppWidgetManager appWidgetManager,
                                final int appWidgetId) {

       // CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        final RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget);

        /////////////////////////////////////////////
        String alphabet[] = {"-","A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};

            String lex1 = (alphabet[new Random().nextInt(alphabet.length)]);
            String lex2 = (alphabet[new Random().nextInt(alphabet.length)]);
            String lex3 = (alphabet[new Random().nextInt(alphabet.length)]);

            DatabaseReference mRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://dictionary-d1392.firebaseio.com/Lexical_Dictionary/"+lex1);

            mRef.orderByKey().startAt(lex1 + lex2 + lex3).endAt(lex1+"\\uf8ff").limitToFirst(1).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                    CharSequence data = dataSnapshot.getKey();
                    CharSequence value = dataSnapshot.getValue(String.class);


                    views.setTextViewText(R.id.textView, data+ " : \n");
                    views.setTextViewText(R.id.textView2, value);


                    Intent intent = new Intent(context, Widget.class);
                    intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
                 // intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetId);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                            0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                    views.setOnClickPendingIntent(R.id.imageButton,
                            getPendingSelfIntent(context, MY_BUTTTON_START));

                    // Instruct the widget manager to update the widget
                    appWidgetManager.updateAppWidget(appWidgetId, views);
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

        /////////////////////////////////////////////

    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        if (MY_BUTTTON_START.equals(intent.getAction())){
            Toast.makeText(context, "Click", Toast.LENGTH_SHORT).show();

        }
    };

    protected static PendingIntent getPendingSelfIntent(Context context, String action){
        Intent intent = new Intent(context,Widget.class);
        intent.setAction(action);
        return PendingIntent.getBroadcast(context, 0 , intent, 0);
    }




}

