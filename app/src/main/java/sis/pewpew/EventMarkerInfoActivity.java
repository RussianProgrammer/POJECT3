package sis.pewpew;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EventMarkerInfoActivity extends AppCompatActivity {

    String title;
    private String snippet;
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private String detailsFromDatabase;
    private String addressFromDatabase;
    private String workTimeFromDatabase;
    private String contactsPhoneFromDatabase;
    private String contactsEmailFromDatabase;
    private String contactsUrlFromDatabase;
    private String prizeFundFromDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marker_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            snippet = "ev" + extras.getInt("POSITION");
        }

        //setTitle(title);

        final ImageView markerInfoIcon = (ImageView) findViewById(R.id.marker_info_icon);
        //markerInfoIcon.setImageResource(R.drawable.test_vkus);

        /*StorageReference storageRef = storage.getReference();
        StorageReference pathReference = storageRef.child("marker_info_icons/demo_qr.png");



        Glide.with(this)
                .using(new FirebaseImageLoader())
                .load(pathReference)
                .into(markerInfoIcon);*/

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    if (dataSnapshot.child("markers").child(snippet).child("title").getValue() != null) {
                        title = dataSnapshot.child("markers").child(snippet).child("title").getValue().toString();
                    } else {
                        title = "Событие";
                    }
                    if (dataSnapshot.child("markers").child(snippet).child("details").getValue() != null) {
                        detailsFromDatabase = dataSnapshot.child("markers").child(snippet).child("details").getValue().toString();
                    } else {
                        detailsFromDatabase = "Без описания";
                    }
                    if (dataSnapshot.child("markers").child(snippet).child("address").getValue() != null) {
                        addressFromDatabase = dataSnapshot.child("markers").child(snippet).child("address").getValue().toString();
                    } else {
                        addressFromDatabase = "Адрес не указан";
                    }
                    if (dataSnapshot.child("markers").child(snippet).child("workTime").getValue() != null) {
                        workTimeFromDatabase = dataSnapshot.child("markers").child(snippet).child("workTime").getValue().toString();
                    } else {
                        workTimeFromDatabase = "Круглосуточно";
                    }
                    if (dataSnapshot.child("markers").child(snippet).child("contactsPhone").getValue() != null) {
                        contactsPhoneFromDatabase = dataSnapshot.child("markers").child(snippet).child("contactsPhone").getValue().toString();
                    } else {
                        contactsPhoneFromDatabase = "Номер телефона не указан";
                    }
                    if (dataSnapshot.child("markers").child(snippet).child("contactsEmail").getValue() != null) {
                        contactsEmailFromDatabase = dataSnapshot.child("markers").child(snippet).child("contactsEmail").getValue().toString();
                    } else {
                        contactsEmailFromDatabase = "Электронная почта не указана";
                    }
                    if (dataSnapshot.child("markers").child(snippet).child("contactsUrl").getValue() != null) {
                        contactsUrlFromDatabase = dataSnapshot.child("markers").child(snippet).child("contactsUrl").getValue().toString();
                    } else {
                        contactsUrlFromDatabase = "Сайт не указан";
                    }
                    if (dataSnapshot.child("markers").child(snippet).child("prizeFund").getValue() != null) {
                        prizeFundFromDatabase = dataSnapshot.child("markers").child(snippet).child("contactsUrl").getValue().toString();
                    } else {
                        prizeFundFromDatabase = "1000";
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }

                setTitle(title);

                ((TextView) findViewById(R.id.event_marker_info_details)).setText(detailsFromDatabase);
                ((TextView) findViewById(R.id.event_marker_info_address)).setText(addressFromDatabase);
                ((TextView) findViewById(R.id.event_marker_info_work_time)).setText(workTimeFromDatabase);
                ((TextView) findViewById(R.id.event_marker_info_contacts_phone)).setText(contactsPhoneFromDatabase);
                ((TextView) findViewById(R.id.event_marker_info_contacts_email)).setText(contactsEmailFromDatabase);
                ((TextView) findViewById(R.id.event_marker_info_contacts_url)).setText(contactsUrlFromDatabase);
                ((TextView) findViewById(R.id.event_marker_info_prize_fund)).setText(prizeFundFromDatabase);
                ((TextView) findViewById(R.id.event_marker_info_work_time_call)).setText("Сроки");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "Не удалось загрузить информацию", Toast.LENGTH_SHORT).show();
                Log.w("TAG", "loadPost:onCancelled", databaseError.toException());
            }
        };
        mDatabase.addValueEventListener(postListener);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(EventMarkerInfoActivity.this);
                builder.setTitle("Жалоба: " + title + ", " + snippet);
                builder.setIcon(R.drawable.ic_report_icon);
                builder.setMessage("Вы можете сообщить нам, если описание не соответствует экособытию. " +
                        "Не отправляйте жалобы, касающиеся присутствия в событий, которые уже завершились или еще не начались.");
                final EditText input = new EditText(EventMarkerInfoActivity.this);
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

                builder.setPositiveButton("Отправить", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mDatabase.child("markers").child(snippet).child("reports").child(user.getUid())
                                .setValue(input.getText().toString());
                        showGratitudeDialog();
                    }
                });
                builder.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            }
        });
    }

    private void showGratitudeDialog() {
        android.app.AlertDialog.Builder gratitudeDialog = new android.app.AlertDialog.Builder(this);
        gratitudeDialog.setTitle("Спасибо");
        gratitudeDialog.setMessage("Благодаря Вам наше приложение становится лучше. Мы рассмотрим Вашу жалобу и примем необходимые меры.");
        gratitudeDialog.setNegativeButton("Закрыть", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        gratitudeDialog.show();
    }
}