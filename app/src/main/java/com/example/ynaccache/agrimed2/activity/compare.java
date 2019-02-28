package com.example.ynaccache.agrimed2.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.ynaccache.agrimed2.R;

import org.joda.time.DateTime;
import org.joda.time.Days;

import java.text.SimpleDateFormat;
import java.util.Date;

public class compare extends Activity {
	public Date d1,d2;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.compare_date);
        
        String textViewContents = "Joda Time Results:\n\n";
        
        TextView tv = (TextView) findViewById(R.id.tvDisplay);
        tv.setText(textViewContents);
		try{


			SimpleDateFormat formatYmd = new SimpleDateFormat("yyyy/MM/dd");

			 d1 = formatYmd.parse("2017/12/01");
			 d2 = formatYmd.parse("2013/09/02");

			DateTime dt1 = new DateTime(d1);
			DateTime dt2 = new DateTime(d2);
			//dt1.isBefore(dt2)



			// Days between 2013-09-01 and 2013-09-02: 1

		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if( d1.before(d2)){
			tv.setText("avant");


		}else{
			tv.setText("aprés");

		}



        //int daysBetween = getNumDaysBetween("2013-09-01", "2013-09-02");
        //tv.setText("Days between 2017-09-01 and 2017-09-02: " + daysBetween);
        
    }

    public int getNumDaysBetween(String dateStart, String dateEnd){
		
		int numDays = 0;
		
		try{
			
	    	SimpleDateFormat formatYmd = new SimpleDateFormat("yyyy-MM-dd");
	     
	    	Date d1 = formatYmd.parse(dateStart);
	    	Date d2 = formatYmd.parse(dateEnd);
	
			DateTime dt1 = new DateTime(d1);
			DateTime dt2 = new DateTime(d2);
			//dt1.isBefore(dt2)

			
	       	numDays = Days.daysBetween(dt1, dt2).getDays();
	       	
	       	// Days between 2013-09-01 and 2013-09-02: 1
	       	
		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return numDays;
	}

}
