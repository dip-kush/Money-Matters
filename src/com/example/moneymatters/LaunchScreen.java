package com.example.moneymatters;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class LaunchScreen extends ListActivity {
	 private ListView listview;
	private static final String[] list_item=new String[]{
	"Borrowed Money","Lent Money","Bank Account Management"
		
	};
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_launch_screen);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,R.layout.activity_launch_screen,list_item);
        setListAdapter(adapter);
        
        listview=getListView();
        listview.setTextFilterEnabled(true);
        
        listview.setOnItemClickListener(new OnItemClickListener() {
        			public void onItemClick(AdapterView<?> parent,View view,int position,long id){
        				switch(position){
        				case 0:
        					Intent i=new Intent(LaunchScreen.this,ViewBorrow.class);
            				startActivity(i);
            				break;
        				case 1:
        					Intent i2=new Intent(LaunchScreen.this,ViewLender.class);
        					startActivity(i2);
        					break;
        				case 2:
        					Intent i3=new Intent(LaunchScreen.this,BankAccount.class);
        					startActivity(i3);
        					break;
        					
        				}
        				
        			}
		});
        
        
        
        
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_launch_screen, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item){
    	switch(item.getItemId()){
    	
    	case R.id.about_me:
    		AlertDialog.Builder dialog= new AlertDialog.Builder(LaunchScreen.this);
    		dialog.setTitle("About Me!!");
    		dialog.setMessage("Hey, This app is developed by Deepak Kushwaha. Hope This interactive app may be useful for " +
    				"the user. Please look into all the features of the app. Enjoy..!!");
    		dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					
				}
			});
    		dialog.create();
    		dialog.show();
    		return true;
    		
    	case R.id.exit:
    		finish();
    		
    		
    		
    	
    	}
    	return true;
    }
}
