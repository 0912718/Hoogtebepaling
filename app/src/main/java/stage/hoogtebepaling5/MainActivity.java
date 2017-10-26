package stage.hoogtebepaling5;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.List;

import stage.hoogtebepaling5.Database.*;
import stage.hoogtebepaling5.Location.LocationActivity;


public class MainActivity extends AppCompatActivity {
    public DatabaseOpenHelper mDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startActivity(new Intent(this, LocationActivity.class));
        mDBHelper = new DatabaseOpenHelper(this);
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        //Database access openen
        databaseAccess.open();
        //query uit getData() wordt in een list geladen
        List<String> lijst = databaseAccess.getData();
        //database wordt gesloten
        databaseAccess.close();
        //ter controle
        System.out.println(lijst);

    }

//    public void onButtonClick(View  view){
//        startActivity(new Intent(this, LocationActivity.class));
//    }

}
