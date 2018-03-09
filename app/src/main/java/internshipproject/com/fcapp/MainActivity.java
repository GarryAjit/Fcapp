package internshipproject.com.fcapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

public class MainActivity extends AppCompatActivity {


    TextView tvBreakfast, tvLunch, tvHitea, tvDinner;
    CheckBox cbBreakfast, cbLunch, cbHitea, cbDinner;
    public static final String Firebase_Server_url = "https://mitfc-7b03f.firebaseio.com/";
    Firebase firebase;
    ChildEventListener mChildEventListener;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaserefernce,childRefernce;
    SelectedItems selectedItems = new SelectedItems();
    FirebaseRemoteConfig mFirebaseRemoteConfig;

    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvBreakfast = (TextView) findViewById(R.id.tvBreakfast);
        tvLunch = (TextView) findViewById(R.id.tvLunch);
        tvHitea = (TextView) findViewById(R.id.tvHitea);
        tvDinner = (TextView) findViewById(R.id.tvDinner);


        cbBreakfast = (CheckBox) findViewById(R.id.cbBreakfast);
        cbLunch = (CheckBox) findViewById(R.id.cbLunch);
        cbHitea = (CheckBox) findViewById(R.id.cbHitea);
        cbDinner = (CheckBox) findViewById(R.id.cbDinner);

        submit = (Button) findViewById(R.id.btnSubmit);

        Firebase.setAndroidContext(MainActivity.this);
        firebase = new Firebase(Firebase_Server_url);


        firebaseDatabase = FirebaseDatabase.getInstance();
        databaserefernce = FirebaseDatabase.getInstance().getReference("SelectedItems");
        childRefernce = databaserefernce.child("SelectedItems");

        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setDeveloperModeEnabled(true)
                .build();
        mFirebaseRemoteConfig.setConfigSettings(configSettings);



       // attachDatabaseReadListener();



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(cbBreakfast.isChecked())
                {
                    selectedItems.setBreakfast(++selectedItems.breakfast);
                }
                else
                {
                    selectedItems.setBreakfast(selectedItems.breakfast);
                }

                if(cbLunch.isChecked())
                {
                    selectedItems.setLunch(++selectedItems.lunch);
                }
                else
                {
                    selectedItems.setLunch(selectedItems.lunch);
                }

                if(cbHitea.isChecked())
                {
                    selectedItems.setHitea(++selectedItems.hitea);
                }
                else
                {
                    selectedItems.setHitea(selectedItems.hitea);
                }

                if(cbDinner.isChecked())
                {
                    selectedItems.setDinner(++selectedItems.dinner);
                }

                else
                {
                    selectedItems.setDinner(selectedItems.dinner);
                }



                firebase.child("SelectedItems").setValue(selectedItems);


                Toast.makeText(MainActivity.this,"Data Inserted Successfully", Toast.LENGTH_LONG).show();

            }
        });


    }

    public void attachDatabaseReadListener()
    {
        if(mChildEventListener == null)
        {
            mChildEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                    //selectedItems = dataSnapshot.getValue(SelectedItems.class);
//                    selectedItems.breakfast = dataSnapshot.child("breakfast").getValue(Integer.class);
//                    selectedItems.lunch = dataSnapshot.child("lunch").getValue(Integer.class);
//                    selectedItems.hitea = dataSnapshot.child("hitea").getValue(Integer.class);
//                    selectedItems.dinner = dataSnapshot.child("dinner").getValue(Integer.class);

                    for(DataSnapshot ds : dataSnapshot.getChildren())
                    {
                        selectedItems.breakfast = ds.child("breakfast").getValue(Integer.class);
                        selectedItems.lunch = ds.child("lunch").getValue(Integer.class);
                        selectedItems.hitea = ds.child("hitea").getValue(Integer.class);
                        selectedItems.dinner = ds.child("dinner").getValue(Integer.class);
                    }

                    Log.i("values", selectedItems.breakfast + " " + selectedItems.lunch + " " + selectedItems.hitea + " " + selectedItems.dinner);

                    Toast.makeText(MainActivity.this, "gyutfyuftf", Toast.LENGTH_SHORT).show();
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
            };

            databaserefernce.addChildEventListener(mChildEventListener);
        }
    }

    @Override
    protected void onStart() {

        databaserefernce.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot ds) {

//                for(DataSnapshot ds : dataSnapshot.getChildren())
//                {

                    selectedItems.breakfast = ds.child("breakfast").getValue(Integer.class);
                    selectedItems.lunch = ds.child("lunch").getValue(Integer.class);
                    selectedItems.hitea = ds.child("hitea").getValue(Integer.class);
                    selectedItems.dinner = ds.child("dinner").getValue(Integer.class);

//                    SelectedItems selectedItems = ds.getValue(SelectedItems.class);
               // }



                Log.i("values", selectedItems.breakfast + " " + selectedItems.lunch + " " + selectedItems.hitea + " " + selectedItems.dinner);

                Toast.makeText(MainActivity.this, "gyutfyuftf", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        super.onStart();
    }
}
