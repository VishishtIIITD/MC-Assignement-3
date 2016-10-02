package iiitd.vishisht.favouritecontacts;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "FavouriteContacts";
    private static final String FILENAMEInternal = "Favourites";

    ListView lv;
    Button add;
    ArrayList<String> months = new ArrayList<String>();

    ArrayAdapter<String> adapter;
    ArrayList<String> list;
    int defaultValue = 0;
    MainActivity activity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        try {
            FileInputStream fin = openFileInput(FILENAMEInternal);
            ObjectInputStream ois = new ObjectInputStream(fin);
            months = (ArrayList<String>) ois.readObject();
            ois.close();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        add = (Button)findViewById(R.id.button);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, Main2Activity.class);
                activity.startActivityForResult(intent,1);
            }
        });

        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        long sort_order = sharedPref.getInt(getString(R.string.sort_order), defaultValue);

        lv = (ListView)findViewById (R.id.listView);
        lv.setFastScrollEnabled(true);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> myAdapter, View myView, int myItemInt, long mylng) {
                String selectedFromList =(String) (lv.getItemAtPosition(myItemInt));
                Intent intent = new Intent(activity,Main3Activity.class);
                intent.putExtra("name", selectedFromList);
                startActivityForResult(intent,2);
                Log.d(TAG,selectedFromList);
            }
        });

        if (sort_order == 0){
            adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, months);
            lv.setAdapter(adapter);
        }
        else if (sort_order == 1){
            list = new ArrayList<String>(months);
            Collections.sort(list);
            adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
            lv.setAdapter(adapter);
        }
        else if (sort_order == 2){
            list = new ArrayList<String>(months);
            Collections.sort(list,Collections.<String>reverseOrder());
            adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
            lv.setAdapter(adapter);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the main; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        switch (item.getItemId()) {
            case R.id.sortAsc:
                editor.putInt(getString(R.string.sort_order), 1);
                editor.commit();

                list = new ArrayList<String>(months);
                Collections.sort(list);
                adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
                lv.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                return true;

            case R.id.sortDesc:
                editor.putInt(getString(R.string.sort_order), 2);
                editor.commit();

                list = new ArrayList<String>(months);
                Collections.sort(list,Collections.<String>reverseOrder());
                adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
                lv.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                return true;

            case R.id.def:
                editor.putInt(getString(R.string.sort_order), 0);
                editor.commit();

                adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, months);
                lv.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                lv.setAdapter(adapter);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        int sort_order = sharedPref.getInt(getString(R.string.sort_order), defaultValue);

        super.onActivityResult(requestCode, resultCode, intent);

        if (requestCode == 1 && resultCode == RESULT_OK) {

            months.add(intent.getStringExtra("name"));

            try {
                FileOutputStream fout = openFileOutput(FILENAMEInternal, Context.MODE_PRIVATE);
                ObjectOutputStream oos = new ObjectOutputStream(fout);
                oos.writeObject(months);
                oos.close();
            } catch (java.io.IOException e) {
                e.printStackTrace();
            }

            if(sort_order == 0){
                adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, months);
                lv.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                lv.setAdapter(adapter);
            }
            else if(sort_order == 1){
                list = new ArrayList<String>(months);
                Collections.sort(list);
                adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
                lv.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
            else if (sort_order == 2){
                list = new ArrayList<String>(months);
                Collections.sort(list,Collections.<String>reverseOrder());
                adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
                lv.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        }

        if (requestCode == 2 && resultCode == RESULT_OK) {
            if(intent.getBooleanExtra("delete",false)){

                months.remove(intent.getStringExtra("name"));

                try {
                    FileOutputStream fout = openFileOutput(FILENAMEInternal, Context.MODE_PRIVATE);
                    ObjectOutputStream oos = new ObjectOutputStream(fout);
                    oos.writeObject(months);
                    oos.close();
                } catch (java.io.IOException e) {
                    e.printStackTrace();
                }

                if(sort_order == 0){
                    adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, months);
                    lv.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    lv.setAdapter(adapter);
                }
                else if(sort_order == 1){
                    list = new ArrayList<String>(months);
                    Collections.sort(list);
                    adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
                    lv.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
                else if (sort_order == 2){
                    list = new ArrayList<String>(months);
                    Collections.sort(list,Collections.<String>reverseOrder());
                    adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
                    lv.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }
        }

    }


}
