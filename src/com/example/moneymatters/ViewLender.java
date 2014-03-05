package com.example.moneymatters;





import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class ViewLender extends ListActivity {

	public static final String ROW_ID2="row_id";
	private ListView listv2;
	private SimpleCursorAdapter mAdapter;
	private static final String TAG="mytag";
	private static final String TAG1="fuckthis";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  setContentView(R.layout.view_lender);
        Log.d(TAG, "Hey i am here");
       ConnectIt connect = new ConnectIt(this);
       
       Log.d(TAG1, "Insertin penis");
       connect.open();
       Cursor c=connect.getAllNames();
       String[] from=new String[]{"name","daily_amount","dates"};
       int[] to=new int[]{R.id.textView1,R.id.textView2,R.id.textView4 };
       mAdapter=new SimpleCursorAdapter(this, R.layout.view_lender, c, from, to);
       setListAdapter(mAdapter);
       listv2=getListView();
       registerForContextMenu(listv2);
       	listv2.setOnItemClickListener(new OnItemClickListener() {
       		
       		public void onItemClick(AdapterView<?> arg0, View arg1,
						int position, long id) {
					
       			Intent i=new Intent(ViewLender.this,LenderDetails.class);
				i.putExtra(ROW_ID2, id);
				startActivity(i);// TODO Auto-generated method stub
				}	
				});
       connect.close();

    }
    protected void onResume(){
    	super.onResume();
    	new LoadtheNames().execute((Object[])null);
    	
    }
    protected void onStop(){
    	Cursor cursor=mAdapter.getCursor();
    	if(cursor!=null)
    		cursor.deactivate();
    	
    	mAdapter.changeCursor(null);
    	super.onStop();
    }
    
    
    public class LoadtheNames extends AsyncTask<Object, Object, Cursor> {

    	ConnectIt connect= new ConnectIt(ViewLender.this);
		@Override
		protected Cursor doInBackground(Object... params) {
			
			connect.open();
			return connect.getAllNames();
		// TODO Auto-generated method stub
		
		}
		 
		protected void onPostExecute(Cursor result){
		mAdapter.changeCursor(result);
		connect.close();
			
		}
    	
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.view_borrow, menu);
        return true;
    }
    public void onCreateContextMenu(ContextMenu menu,View view,ContextMenuInfo info){
    	super.onCreateContextMenu(menu, view, info);
    	getMenuInflater().inflate(R.menu.context_menu, menu);
    	
    }
    public boolean onContextItemSelected(MenuItem item) {
    	ConnectIt connect= new ConnectIt(this);
    AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.edit:
            	Intent i=new Intent(ViewLender.this,AddLender.class);
				i.putExtra(ROW_ID2, info.id);
				startActivity(i);
                return true;
            case R.id.delete:
                
                connect.deleteContact(info.id);
                new LoadtheNames().execute((Object[])null);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
    
   public boolean onOptionsItemSelected(MenuItem item){
	   switch(item.getItemId()){
	   case R.id.add_row:
		   Intent i=new Intent(this,AddLender.class);
		   startActivity(i);
		  return true;
	
	   case R.id.total_money:
		   int total=0;
		   ConnectIt connect=new ConnectIt(ViewLender.this);
		   connect.open();
	        Cursor c=connect.getAllNames();
	        
	       if(c.moveToFirst()){
				do{
					total+=c.getInt(2);
				}while(c.moveToNext());
	        }
	        connect.close();
		  
	        Toast.makeText(this, "your total amount worth is"+total, Toast.LENGTH_SHORT).show();
	        
	        
	        
		
		   
	   }
	 return true;  
   }
}
