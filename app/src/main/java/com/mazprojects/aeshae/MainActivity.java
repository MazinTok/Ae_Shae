package com.mazprojects.aeshae;


import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnShowListener;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

//import android.util.Log;
//import com.google.android.gms.ads.AdListener;
//import com.google.android.gms.ads.AdRequest;
//import com.google.android.gms.ads.AdView;
//import com.google.android.gms.ads.InterstitialAd;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class MainActivity extends Activity {

	private AdView mAdView;

	int destance = 5;
	 private SeekBar seekBar;
	 private TextView desNumTV;
	 Dialog RandDialog ;
		List<Venues> LVenues = new ArrayList<Venues>();
	 private AnimationDrawable progressAnimation;
	 
	 ImageButton imB ;
	 Venues venue ;
	 Switch sw ;
	 //Locationll loc = new Locationll() ;
	 final Locationll loc = new Locationll(this) ;
	//private static String TAG = "MainActivity";
	public static String url;
	public static String urlService;
	
	public static String Cid ="RWKO2LV1P154LDWKLQU4YDMBBBJ5PLBNOCAWECS45OTME4AF"; 	
	public static String CSec ="T2PLIPSQVW0OQWVBLRPM3SS0P0BDVHACSTJWRHTCMH12VT0H";
	public static String VCategoryId;
	ArrayAdapter<String> adapter;
	 private static final String TAG_venues = "venues";
	 private static final String TAG_NAME = "name";
	
	List<String> NamesList = new ArrayList<String>();
	
	JSONArray contacts = null;
    double longitude =0.0;
	double latitude =0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		ActionBar bar = getActionBar();

		imB =(ImageButton) findViewById(R.id.imgOne);
		sw = (Switch) findViewById(R.id.switch1);
		bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#341558")));
	
		bar.setDisplayShowTitleEnabled(false);  // required to force redraw, without, gray color
		bar.setDisplayShowTitleEnabled(true);
		//Log.i(TAG, "onCreate  ");
		
		//================RandDialog=====================================
		RandDialog = new Dialog(MainActivity.this);
		RandDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		 //dialog.setTitle("loading...");
		 //Set the View of the Dialog - Custom
		RandDialog.setContentView(R.layout.random_diloge);
		//RandDialog.setCancelable(false);
		
	
		//=====================================================
	
		 NamesList.clear();
		 loc.getLocation();
		 longitude =loc.lang;
		 latitude =loc.let;
	
		 //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-
		
		 seekBar = (SeekBar) findViewById(R.id.seekBar1);
		 
		
		 desNumTV = (TextView) findViewById(R.id.destinceNumTxt);
		 destance = seekBar.getProgress();
		 desNumTV.setText(" " + Integer.toString(destance) + " ");
		 
		  // Initialize the textview with '0'
		 //desNumTV.setText(seekBar.getProgress());
		  seekBar.setOnSeekBarChangeListener(
		                new OnSeekBarChangeListener() {
		    
		        @Override
		      public void onProgressChanged(SeekBar seekBar, 
		                                            int progresValue, boolean fromUser) {
		        	destance = progresValue;
		      }

		      @Override
		      public void onStartTrackingTouch(SeekBar seekBar) {
		        // Do something here, 
		                      //if you want to do anything at the start of
		        // touching the seekbar
		      }

		      @Override
		      public void onStopTrackingTouch(SeekBar seekBar) {
		        // Display the value in textview
		    	  desNumTV.setText(" "+Integer.toString(destance)+" ");
		      }
		  });

		  
		  //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-
			
			 
         imB.setOnClickListener(new View.OnClickListener() {
			 public void onClick(View v) {
				 // Perform action on click

				 buttenPreesed();

			 }
		 });

		 //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-
		mAdView = (AdView)findViewById(R.id.ad_view);
		AdRequest adRequest = new AdRequest.Builder()
				.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
				.build();

		// Start loading the ad in the background.
		mAdView.loadAd(adRequest);

	}

	public void buttenPreesed()
    {
        if (!loc.checkLocationPermission(this))
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        // Perform action on click
        loc.getLocation();
        longitude =loc.lang;
        latitude =loc.let;
        if (isNetworkAvailable() && loc.getLocActive() && destance != 0 )
        {

            try{

                NamesList.clear();
                int  destanceTemp = destance *1000;

                new GetVenues().execute(Integer.toString(destanceTemp));
            }
            catch(Exception e){

				Log.e("error", e.toString());  //Toast.makeText(MainActivity.this ,e.toString(),Toast.LENGTH_LONG).show();
            }
        }
        else if(! isNetworkAvailable())
            Toast.makeText(MainActivity.this ,getString(R.string.noNetwarktx),Toast.LENGTH_LONG).show();
        else if ( destance == 0 )
        {
            Toast.makeText(MainActivity.this ,getString(R.string.zeroNotavalibaltx),Toast.LENGTH_LONG).show();

        }
        else
        {
            Toast.makeText(MainActivity.this ,getString(R.string.noGPStx),Toast.LENGTH_LONG).show();

        }

    }
  
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

public boolean onOptionsItemSelected(MenuItem item)
{
	/*
	 * xml
	 *  <item
        android:id="@+id/about"
        android:orderInCategory="100"
        android:showAsAction="never"
        android:title="@string/about"/>
    
     <item
        android:id="@+id/share"
        android:orderInCategory="100"
        android:showAsAction="never"
        android:title="@string/share"/>
        */
	
   /* case R.id.about:
        Toast.makeText(MainActivity.this,"@String/about", Toast.LENGTH_SHORT).show();
        return true;*/
    if (item.getItemId() == R.id.share)
    	{
    		Intent sendIntent = new Intent();
    		sendIntent.setAction(Intent.ACTION_SEND);
    		sendIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.shareTxt) );
    		sendIntent.setType("text/plain");
    		startActivity(sendIntent);
    	//Toast.makeText(MainActivity.this, "@String/share", Toast.LENGTH_SHORT).show();
    		return true;
    	}
	return false;
	
	
	
}
public void aeshaeCliecked(View view)
		 {
		
			RandDialog.dismiss();
		
		 }


public void goToVenou(String lat, String lng)
{
String uri = String.format("geo:"+lat+","+lng+"?z=17&q="+lat+","+lng);
Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
startActivity(intent);
}

public void infoVenou(String _venouName, String _lat, String _lng)
{
	String link = "https://foursquare.com/explore?ll="+_lat+"%2C"+_lng+"&mode=url&q="+_venouName;
	try {
		
	    //Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
	  //  startActivity(myIntent);

		Intent intent = new Intent(this,InfoAactivity.class);
		intent.putExtra("URL", link);
		//String temp = "http://he.moe.gov.sa/ar/Mobile/Det.aspx?&Service=Yes&ServiceID=" + ServiceHashMap.get(country);
		//intent.putExtra("URL", temp);//ServiceHashMap.get(country));
		startActivity(intent);


	} catch (ActivityNotFoundException e) {
	    Toast.makeText(this, "No application can handle this request."
	        + " Please instal l a webbrowser",  Toast.LENGTH_LONG).show();
	    e.printStackTrace();
	}
}


private boolean isNetworkAvailable() {
    ConnectivityManager connectivityManager 
          = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
}

 
public void RandomSelect() throws ClientProtocolException, IOException
	  {
	

/*
	AdView mAdView = (AdView) getView().findViewById();
    AdRequest adRequest = new AdRequest.Builder().build();
    mAdView.loadAd(adRequest);*/


	System.setProperty("http.keepAlive", "false"); 
	final TextView VenTit = (TextView) RandDialog.findViewById(R.id.VenueTital); 
	final TextView VenInfo = (TextView) RandDialog.findViewById(R.id.VenueInfo); 
	 
	Button infoButton=(Button) RandDialog.findViewById(R.id.InfoBot);
	 Button navButton=(Button) RandDialog.findViewById(R.id.NavBot);
	 Button backButton=(Button) RandDialog.findViewById(R.id.ExitBtn);
	 ImageButton shareButten = (ImageButton) RandDialog.findViewById(R.id.shareVenButton);
     Button refrechButton = (Button) RandDialog.findViewById(R.id.backBot);


		  final int i;
		  Random rand =new Random();
			 i= rand.nextInt(LVenues.size());
		  try{
				
					
					 VenTit.setText(LVenues.get(i).getName());
					 VenInfo.setText(LVenues.get(i).getCity());
//					  AsyncCallWS  task = new AsyncCallWS();
//				        task.execute(LVenues.get(i).getName(),LVenues.get(i).getLatitude(),LVenues.get(i).getLongitude(),LVenues.get(i).getCity());
			 }
			 catch(Exception e){
				 //Toast.makeText(this ,e.toString(),Toast.LENGTH_LONG).show();
			 }
		  
		 
		infoButton.setOnClickListener( new OnClickListener() {
				
				
				@Override
				public void onClick(View v) {
					
					infoVenou(LVenues.get(i).getName(),LVenues.get(i).getLatitude(),LVenues.get(i).getLongitude());
				}
			});
		
		navButton.setOnClickListener( new OnClickListener() {
					
					
					@Override
					public void onClick(View v) {
					
						goToVenou(LVenues.get(i).getLatitude(),LVenues.get(i).getLongitude());
					}
				});
				
	    backButton.setOnClickListener( new OnClickListener() {
						
						
						@Override
						public void onClick(View v) {

                            //buttenPreesed();
							aeshaeCliecked(v);
						}
					});
		refrechButton.setOnClickListener(new OnClickListener() {


            @Override
            public void onClick(View v) {

                buttenPreesed();
                //aeshaeCliecked(v);
            }
        });
		shareButten.setOnClickListener( new OnClickListener() {
						
						
						@Override
						public void onClick(View v) {
							
							Intent sendIntent = new Intent();
					    	sendIntent.setAction(Intent.ACTION_SEND);
					    	sendIntent.putExtra(Intent.EXTRA_TEXT,LVenues.get(i).getName()+ getString(R.string.shareVenueTx) );
					    	sendIntent.setType("text/plain");
					    	startActivity(sendIntent);
						}
					});
				 
				 RandDialog.show();

	  }

	
	    
	  private class GetVenues extends AsyncTask<String, Void, Void> {
		  
		  final Dialog dialog = new Dialog(MainActivity.this);
		
		  String icecreamShop = "4bf58dd8d48988d1c9941735";
          String desertshop = "4bf58dd8d48988d1d0941735";
          String superMarket = "52f2ab2ebcbc57f1066b8b46";
          String cafe = "4bf58dd8d48988d16d941735";//4bf58dd8d48988d16d941735
          String  Coffee_Shop = "4bf58dd8d48988d1e0931735";
          String donuntShop = "4bf58dd8d48988d148941735";
          String cupCakeShop = "4bf58dd8d48988d1bc941735";
	        @SuppressLint("NewApi")
			@Override
	        protected void onPreExecute() {
	            super.onPreExecute();
	           
	            // -------------------Showing progress dialog-------------------
	            //Set Background of Dialog - Custom 
				// dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_backround);
				 //Remove the Title
				 dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
				 //dialog.setTitle("loading...");
				 //Set the View of the Dialog - Custom
				 dialog.setContentView(R.layout.myprogressbar);
				 dialog.setCancelable(false);
				 //Set the title of the Dialog
				// dialog.setTitle("Title...");
				 ImageView progressSpinner = (ImageView) dialog.findViewById(R.id.imgOne);

				 //Set the background of the image - In this case an animation (/res/anim folder)
				 progressSpinner.setBackgroundResource(R.anim.progress_animation);
				 //Get the image background and attach the AnimationDrawable to it.
				 progressAnimation = (AnimationDrawable) progressSpinner.getBackground();
				 //Start the animation after the dialog is displayed.
				 //progressAnimation.setBounds(left, top, right, bottom)
				dialog.setOnShowListener(new OnShowListener() {
				 @Override
				 public void onShow(DialogInterface dialog) {
				 progressAnimation.start();
				//progressAnimation.setCancelable("false");
				 }
				 });
				 dialog.show();
	            
	            
	            // --------------------------------------
	          
	            
	            if (sw.isChecked())
	            	VCategoryId ="4d4b7105d754a06374d81259";
	            else 
	            {
	            	VCategoryId =cafe +","+ Coffee_Shop+","+donuntShop;
	            }
	            String date;
	            //= new SimpleDateFormat("yyyyMMdd").format(new Date());
	            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd",Locale.US);
	            date = sdf.format(new Date());
	            url = "https://api.foursquare.com/v2/venues/search?ll="+latitude+","+longitude+"" +
	            		"&client_id="+Cid+"&client_secret="+CSec+"&v="+date+"&categoryId=" +VCategoryId+
	            				"&limit=50&radius=";
				//cafe cat 4bf58dd8d48988d16d941735
	            
	          
	        }
	 
	        @SuppressLint("NewApi")
			@Override
	        protected Void doInBackground(String... params) {
	            // Creating service handler class instance
	        	LVenues.clear();
	        	
	        	HttpHandelar sh = new HttpHandelar();
	 
	        	url+= params[0];
	        	String name;
	            // Making a request to url and getting response
	            String jsonStr = sh.makeServiceCall(url, HttpHandelar.GET);
	          // Log.d("Response: ", "> " + jsonStr);
	 
	            
	            	if (jsonStr != null) {
	                try {
	                	
	                    JSONObject jsonObj = new JSONObject(jsonStr); 
	                   // contacts = jsonObj.getJSONArray(TAG_venues);
	
	                    JSONObject foursquareResponse = (JSONObject) jsonObj.get("response");
	                    contacts    = (JSONArray) foursquareResponse.get(TAG_venues);
	                   // JSONObject group = (JSONObject)groups.get(0);
	                    //contacts = (JSONArray)group.get("items");
	                    // Getting JSON Array node                  
	 
	                    // looping through All Contacts
	                    for (int i = 0; i < contacts.length(); i++) {
	                    	 venue = new Venues();
	                        JSONObject c = contacts.getJSONObject(i);
	                         
	                       // String id = c.getString(TAG_ID);
	                         name = c.getString(TAG_NAME);
	                         JSONArray ob = (JSONArray) c.get("categories");
	                         JSONObject obLoc = (JSONObject) c.get("location");	                        
	                     //    JSONObject obPho = (JSONObject) c.get("contact");
		                     
	                       //  Log.i(TAG, name);
	                         JSONObject c2 =ob.getJSONObject(0);
	                       //  Log.i(TAG, c2.getString("id")); 
	                        // JSONObject cLoc =obLoc.getString("lat");
	                        // Log.i("LSON",obPho.toString());
	                         venue.setName(name);
	                         venue.setLatitude(obLoc.getString("lat"));
	                         venue.setLongitude(obLoc.getString("lng"));
	                     //    if (! obPho.isNull("phone"))
	                    //     venue.setCity(obPho.getString("phone"));
	                       
	                        boolean temp = sw.isChecked();
	                        String temp1 = c2.getString("id");
	                        
	        	            if (!(	temp  && (temp1.equals(Coffee_Shop)  || temp1.equals(donuntShop)
	        	            		|| temp1.equals(desertshop) || temp1.equals(superMarket) 
	        	            		|| temp1.equals(cafe) || temp1.equals(icecreamShop ) || temp1.equals(cupCakeShop)
	        	            		)))
	        	            {
	        	            	/* Log.i(TAG, c2.getString("id"));
	        	            }
	        	            else 
	                        {*/
	        	            	NamesList.add(name);
	        	            	LVenues.add(venue);
	                        }
	                        
	                        
	                    }
	                } catch (JSONException e) {
	                   // e.printStackTrace();
	                    //Toast.makeText(MainActivity.this ,"لا يمكن الاتصال بالانترنت",T oast.LENGTH_LONG).show();
	                    Log.d("error", e.toString());
	                    LVenues.clear();
	                }
	            } else {
	            	Toast.makeText(MainActivity.this ,getString(R.string.serviceNotAvatx),Toast.LENGTH_LONG).show(); 
	            	LVenues.clear();
	            }
	            	
	            return null;
	        }
	 
	        @SuppressLint("NewApi")
			
			@Override
	        protected void onPostExecute(Void result) {
	            super.onPostExecute(result);
	            // Dismiss the progress dialog
	         
	            /**
	             * Updating parsed JSON data into ListView
	             * */
	           
	         //   adapter.notifyDataSetChanged();
	        
	            if (dialog.isShowing())
	            	dialog.dismiss();
	        

	           try {
	        	   if (!LVenues.isEmpty() )
				RandomSelect();
			} catch (ClientProtocolException e) {
				
				e.printStackTrace();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
	
	        }

	      
	  }

	  private class AsyncCallWS extends AsyncTask<String, Void, String> {
			
			//ProgressDialog pdLoading = new ProgressDialog(CoverActivity.this);
	       
			@Override
	        protected String doInBackground(String... params) {
	          /*  Log.i(TAG, "doInBackground");
	            Log.i("doInBackground", params[0]);
	            Log.i("doInBackground", params[1]);
	            Log.i("doInBackground", params[2]);
	            Log.i("doInBackground", params[3]);
	            */
				String temp= "";
	            try{

			            CallSoap SCall = new CallSoap();	   
			            temp = SCall.Call_GetBeds(params[0],String.valueOf(latitude),String.valueOf(longitude),params[3]);
		         //  	 Log.i("soap1", temp);
		           	 
			       
		           	// Toast.makeText(MainActivity.this ,temp,Toast.LENGTH_LONG).show();
	           }
	            catch(Exception exception)
	            {
	            	
	         	   temp = exception.toString();
	         	   //Log.i("catch" , temp);
	            }
	         
	            return temp;
	        }

	        @Override
	        protected void onPostExecute(String result) {
	          //  Log.i(TAG, "onPostExecute");
	     	
	        }

	        @Override
	        protected void onPreExecute() {
	           // Log.i(TAG, "onPreExecute");
	       
	        }

	        @Override
	        protected void onProgressUpdate(Void... values) {
	            //Log.i(TAG, "onProgressUpdate");
	            
	        }

	  }
	/** Called when leaving the activity */
	@Override
	public void onPause() {
		if (mAdView != null) {
			mAdView.pause();
		}
		super.onPause();
	}

	/** Called when returning to the activity */
	@Override
	public void onResume() {
		super.onResume();
		if (mAdView != null) {
			mAdView.resume();
		}
	}

	/** Called before the activity is destroyed */
	@Override
	public void onDestroy() {
		if (mAdView != null) {
			mAdView.destroy();
		}
		super.onDestroy();
	}
}
