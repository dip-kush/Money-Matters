package com.example.moneymatters;




import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DbConnector {

	static final int  min=7;
	public static final String ROW_ID="_id";
	public static final String ROW_NAME="name";
	public static final String ROW_PHONE="phone";
	//public static final String ROW_AMOUNT="daily_amount";
	
	public static final String DATABASE_NAME="money_record";
	public static final String TABLE_NAME="borrow";
	public static final int DATABASE_VERSION=2;
	
	private MyOpenHelper openHelper;
	private SQLiteDatabase database;
	
	public DbConnector(Context context)
	{
		openHelper=new MyOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION );
	}
	
	//opening a database
	public void open() throws SQLException{
		database=openHelper.getWritableDatabase();
	}
	//closing a database
	public void close(){
		if(database!=null){
			database.close();
		}
	}
		//inserting  values in the database
		public void insertValue(String name,int daily_amount,String dates,String phone){
			ContentValues newcon=new ContentValues();
			newcon.put(ROW_NAME, name);
			newcon.put("daily_amount", daily_amount);
			newcon.put("dates", dates);
			newcon.put("phone", phone);
			open();
			database.insert(TABLE_NAME, null, newcon);
			close();
		}
		
	//deleting value from the database
		
		public void update(long id,String name,int daily_amount,String dates,String phone){
			ContentValues editcon=new ContentValues();
			editcon.put(ROW_NAME, name);
			editcon.put("daily_amount", daily_amount);
			editcon.put("dates", dates);
			editcon.put("phone", phone);
			open();
			database.update(TABLE_NAME, editcon, "_id="+id, null);
			close();
			
		}
		
		//public void gettting all the names of borrowed money
		
		public Cursor getAllNames(){
		
			return database.query(TABLE_NAME, null, null, null, null, null, "name");
	
		}
		public Cursor getOneName(long id){
			Cursor mcursor= database.query(TABLE_NAME, null, "_id="+id, null,null,null,null);
		
			
			if(mcursor!=null){
				mcursor.moveToFirst();
			}
			return mcursor;
			
		}
		public Cursor getTheTime(){
			String query="Select _id,name,daily_amount,dates,phone,julianday('now') - julianday(dates) as difference from borrow";
			return database.rawQuery(query, null);
			
		}
	
		
		public Cursor getDistinctName(){
			Cursor cursor=database.rawQuery("Select distinct name,_id from borrow", null);
			if(cursor!=null){
				cursor.moveToFirst();
			}
			return cursor;
			
		}
		public void deleteContact(long id){
			open();
			database.delete(TABLE_NAME, "_id="+id, null);
			close();
		}
		
}
