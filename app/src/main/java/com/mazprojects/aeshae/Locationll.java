package com.mazprojects.aeshae;

import android.app.Activity;

public class Locationll {

	public double lang;
	public double let;
	GPSTracker loc;
	Locationll(Activity activity) 
	{
		loc = new GPSTracker(activity);		
		lang=0;
		let=0;
	}
	public void getLocation()
	{
		
		//GPSTracker loc = new GPSTracker(activity);
		loc.getLocation();
		lang = loc.getLongitude();
		let = loc.getLatitude();
		//=================================
		/*
		Geocoder geocoder;
	     String bestProvider;
	     List<Address> user = null;
	  
	    LocationManager lm = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);

	     Criteria criteria = new Criteria();
	      Location location = null ;
	    
	    
	    	   
	    	
	    // lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 10, locationListener);
	     bestProvider = lm.getBestProvider(criteria, false);
	     location = lm.getLastKnownLocation(bestProvider);

	     if (location == null){
	         Toast.makeText(activity ,"Location Not found",Toast.LENGTH_LONG).show();
	      }else{
	        geocoder = new Geocoder(activity);
	        try {
	            user = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
	            let=(double)user.get(0).getLatitude();
	        lang=(double)user.get(0).getLongitude();
	        System.out.println(" DDD lat: " +let+",  longitude: "+lang);

	        }catch (Exception e) {
	                e.printStackTrace();
	        }
	    }
		
		/*
		LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE); 
		//Location location = lm.getLastKnownLocation(lm.getBestProvider(null, true));
		 
		
		 final LocationListener locationListener = new LocationListener() {
		    public void onLocationChanged(Location location) {
		        longitude = location.getLongitude();
		        latitude = location.getLatitude();
		    }

			@Override
			public void onProviderDisabled(String provider) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onProviderEnabled(String provider) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onStatusChanged(String provider, int status,
					Bundle extras) {
				// TODO Auto-generated method stub
				
			}
		};

		//lm.getBestProvider(null, enabledOnly)
		 //getBestProvider()
		lm.requestLocationUpdates(lm.getBestProvider(, true), 2000, 10, locationListener);
		
		//double longitude = location.getLongitude();
		//double latitude = location.getLatitude();

		
		//=================================
		
		
		
		
		*/
	}
	
	public boolean getLocActive()
	{
		
		return lang != 0 && let != 0;
	}
}

