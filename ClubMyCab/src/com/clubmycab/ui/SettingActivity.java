package com.clubmycab.ui;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.clubmycab.BookaCabFragmentActivity;
import com.clubmycab.CircularImageView;
import com.clubmycab.R;
import com.clubmycab.ShareLocationFragmentActivity;
import com.clubmycab.utility.GlobalVariables;
import com.clubmycab.utility.Log;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.navdrawer.SimpleSideDrawer;

public class SettingActivity extends Activity {

	CircularImageView profilepic;
	TextView username;
	ImageView notificationimg;

	ImageView sidemenu;

	private SimpleSideDrawer mNav;
	CircularImageView drawerprofilepic;
	TextView drawerusername;

	TextView myprofile;
	TextView myrides;
	TextView bookacab;
	TextView sharemylocation;
	TextView myclubs;
	TextView sharethisapp;
	TextView mypreferences;
	TextView about;

	String FullName;
	String MobileNumber;

	RelativeLayout unreadnoticountrl;
	TextView unreadnoticount;

	RelativeLayout pushnotificationsonoffrl;
	ImageView pushonoffimg;

	RelativeLayout sharelocationtimerl;
	TextView sharelocationtimevalue;

	String readunreadnotiresp;

	

	RelativeLayout settingsrl;
	Tracker tracker;

	boolean exceptioncheck = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting_details);

		// Check if Internet present
		if (!isOnline()) {

			AlertDialog.Builder builder = new AlertDialog.Builder(
					SettingActivity.this);
			builder.setMessage("No Internet Connection. Please check and try again!");
			builder.setCancelable(false);

			builder.setPositiveButton("Retry",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							Intent intent = getIntent();
							intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

							finish();

							startActivity(intent);

						}
					});

			builder.show();
			return;
		}

		GoogleAnalytics analytics = GoogleAnalytics
				.getInstance(SettingActivity.this);
		tracker = analytics.newTracker(GlobalVariables.GoogleAnalyticsTrackerId);

		// All subsequent hits will be send with screen name = "main screen"
		tracker.setScreenName("Settings");

		settingsrl = (RelativeLayout) findViewById(R.id.settingsrl);
		settingsrl.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Log.d("settingsrl", "settingsrl");
			}
		});

		

//		mNav = new SimpleSideDrawer(this);
//		mNav.setLeftBehindContentView(R.layout.activity_behind_left_simple);
//
//		findViewById(R.id.sidemenu).setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//
//				mNav.toggleLeftDrawer();
//
//			}
//		});
//
//		myprofile = (TextView) findViewById(R.id.myprofile);
//		myprofile.setTypeface(Typeface.createFromAsset(getAssets(),
//				"NeutraText-Light.ttf"));
//		myrides = (TextView) findViewById(R.id.myrides);
//		myrides.setTypeface(Typeface.createFromAsset(getAssets(),
//				"NeutraText-Light.ttf"));
//		bookacab = (TextView) findViewById(R.id.bookacab);
//		bookacab.setTypeface(Typeface.createFromAsset(getAssets(),
//				"NeutraText-Light.ttf"));
//		sharemylocation = (TextView) findViewById(R.id.sharemylocation);
//		sharemylocation.setTypeface(Typeface.createFromAsset(getAssets(),
//				"NeutraText-Light.ttf"));
//		myclubs = (TextView) findViewById(R.id.myclubs);
//		myclubs.setTypeface(Typeface.createFromAsset(getAssets(),
//				"NeutraText-Light.ttf"));
//		sharethisapp = (TextView) findViewById(R.id.sharethisapp);
//		sharethisapp.setTypeface(Typeface.createFromAsset(getAssets(),
//				"NeutraText-Light.ttf"));
//		mypreferences = (TextView) findViewById(R.id.mypreferences);
//		mypreferences.setTypeface(Typeface.createFromAsset(getAssets(),
//				"NeutraText-Light.ttf"));
//		about = (TextView) findViewById(R.id.about);
//		about.setTypeface(Typeface.createFromAsset(getAssets(),
//				"NeutraText-Light.ttf"));
//
//		myprofile.setOnClickListener(new View.OnClickListener() {
//			@SuppressWarnings("deprecation")
//			@Override
//			public void onClick(View arg0) {
//				mNav.toggleDrawer();
//
//				tracker.send(new HitBuilders.EventBuilder()
//						.setCategory("MyProfile Click")
//						.setAction("MyProfile Click")
//						.setLabel("MyProfile Click").build());
//
//				Intent mainIntent = new Intent(SettingActivity.this,
//						MyProfileActivity.class);
//				startActivityForResult(mainIntent, 500);
//				overridePendingTransition(R.anim.slide_in_right,
//						R.anim.slide_out_left);
//			}
//		});
//
//		myrides.setOnClickListener(new View.OnClickListener() {
//			@SuppressWarnings("deprecation")
//			@Override
//			public void onClick(View arg0) {
//				mNav.toggleDrawer();
//
//				tracker.send(new HitBuilders.EventBuilder()
//						.setCategory("MyRides Click")
//						.setAction("MyRides Click").setLabel("MyRides Click")
//						.build());
//
//				Intent mainIntent = new Intent(SettingActivity.this,
//						MyRidesActivity.class);
//				startActivityForResult(mainIntent, 500);
//				overridePendingTransition(R.anim.slide_in_right,
//						R.anim.slide_out_left);
//			}
//		});
//
//		bookacab.setOnClickListener(new View.OnClickListener() {
//			@SuppressWarnings("deprecation")
//			@Override
//			public void onClick(View arg0) {
//				mNav.toggleDrawer();
//
//				tracker.send(new HitBuilders.EventBuilder()
//						.setCategory("BookaCab Click")
//						.setAction("BookaCab Click").setLabel("BookaCab Click")
//						.build());
//
//				Intent mainIntent = new Intent(SettingActivity.this,
//						BookaCabFragmentActivity.class);
//				startActivityForResult(mainIntent, 500);
//				overridePendingTransition(R.anim.slide_in_right,
//						R.anim.slide_out_left);
//			}
//		});
//
//		sharemylocation.setOnClickListener(new View.OnClickListener() {
//			@SuppressWarnings("deprecation")
//			@Override
//			public void onClick(View arg0) {
//				mNav.toggleDrawer();
//
//				tracker.send(new HitBuilders.EventBuilder()
//						.setCategory("ShareLocation Click")
//						.setAction("ShareLocation Click")
//						.setLabel("ShareLocation Click").build());
//
//				Intent mainIntent = new Intent(SettingActivity.this,
//						ShareLocationFragmentActivity.class);
//				startActivityForResult(mainIntent, 500);
//				overridePendingTransition(R.anim.slide_in_right,
//						R.anim.slide_out_left);
//			}
//		});
//
//		myclubs.setOnClickListener(new View.OnClickListener() {
//			@SuppressWarnings("deprecation")
//			@Override
//			public void onClick(View arg0) {
//				mNav.toggleDrawer();
//
//				tracker.send(new HitBuilders.EventBuilder()
//						.setCategory("MyClubs Click")
//						.setAction("MyClubs Click").setLabel("MyClubs Click")
//						.build());
//
//				Intent mainIntent = new Intent(SettingActivity.this,
//						MyClubsActivity.class);
//				startActivityForResult(mainIntent, 500);
//				overridePendingTransition(R.anim.slide_in_right,
//						R.anim.slide_out_left);
//			}
//		});
//
//		sharethisapp.setOnClickListener(new View.OnClickListener() {
//			@SuppressWarnings("deprecation")
//			@Override
//			public void onClick(View arg0) {
//				mNav.toggleDrawer();
//
//				tracker.send(new HitBuilders.EventBuilder()
//						.setCategory("ShareApp Click")
//						.setAction("ShareApp Click").setLabel("ShareApp Click")
//						.build());
//
//				Intent sendIntent = new Intent();
//				sendIntent.setAction(Intent.ACTION_SEND);
//				sendIntent
//						.putExtra(
//								Intent.EXTRA_TEXT,
//								"I am using this cool app 'ClubMyCab' to share & book cabs. Check it out @ http://tinyurl.com/n7j6chq");
//				sendIntent.setType("text/plain");
//				startActivity(Intent.createChooser(sendIntent, "Share Via"));
//
//			}
//		});
//
//		mypreferences.setOnClickListener(new View.OnClickListener() {
//			@SuppressWarnings("deprecation")
//			@Override
//			public void onClick(View arg0) {
//				mNav.toggleDrawer();
//			}
//		});
//
//		about.setOnClickListener(new View.OnClickListener() {
//			@SuppressWarnings("deprecation")
//			@Override
//			public void onClick(View arg0) {
//				mNav.toggleDrawer();
//
//				tracker.send(new HitBuilders.EventBuilder()
//						.setCategory("About Click").setAction("About Click")
//						.setLabel("About Click").build());
//
//				Intent mainIntent = new Intent(SettingActivity.this,
//						AboutPagerFragmentActivity.class);
//				startActivityForResult(mainIntent, 500);
//				overridePendingTransition(R.anim.slide_in_right,
//						R.anim.slide_out_left);
//			}
//		});
		
		UniversalDrawer drawer = new UniversalDrawer(this,tracker);
		drawer.createDrawer();

		profilepic = (CircularImageView) findViewById(R.id.profilepic);
		notificationimg = (ImageView) findViewById(R.id.notificationimg);
		drawerprofilepic = (CircularImageView) findViewById(R.id.drawerprofilepic);

		SharedPreferences mPrefs = getSharedPreferences("FacebookData", 0);
		FullName = mPrefs.getString("FullName", "");
		MobileNumber = mPrefs.getString("MobileNumber", "");

		username = (TextView) findViewById(R.id.username);
		username.setTypeface(Typeface.createFromAsset(getAssets(),
				"NeutraText-Bold.ttf"));
		username.setText(FullName);

		drawerusername = (TextView) findViewById(R.id.drawerusername);
		drawerusername.setTypeface(Typeface.createFromAsset(getAssets(),
				"NeutraText-Bold.ttf"));
		drawerusername.setText(FullName);

		notificationimg.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent mainIntent = new Intent(SettingActivity.this,
						NotificationListActivity.class);
				startActivityForResult(mainIntent, 500);
				overridePendingTransition(R.anim.slide_in_right,
						R.anim.slide_out_left);

			}
		});

		unreadnoticountrl = (RelativeLayout) findViewById(R.id.unreadnoticountrl);
		unreadnoticount = (TextView) findViewById(R.id.unreadnoticount);

		// ///////////////
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			new ConnectionTaskForreadunreadnotification()
					.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
		} else {
			new ConnectionTaskForreadunreadnotification().execute();
		}

		// ///////////////
		SharedPreferences mPrefs111 = getSharedPreferences("userimage", 0);
		String imgname = mPrefs111.getString("imgname", "");
		String imagestr = mPrefs111.getString("imagestr", "");

		if (imagestr.isEmpty() || imagestr == null
				|| imagestr.equalsIgnoreCase("")) {
		} else {

			byte[] b = Base64.decode(imagestr, Base64.DEFAULT);
			InputStream is = new ByteArrayInputStream(b);
			Bitmap yourSelectedImage = BitmapFactory.decodeStream(is);

			profilepic.setImageBitmap(yourSelectedImage);
			drawerprofilepic.setImageBitmap(yourSelectedImage);
		}

		SharedPreferences mPrefs1111 = getSharedPreferences("usersettings", 0);
		String switchstatus = mPrefs1111.getString("switchstatus", "");
		String sharelocationinterval = mPrefs1111.getString(
				"sharelocationinterval", "");

		pushnotificationsonoffrl = (RelativeLayout) findViewById(R.id.pushnotificationsonoffrl);
		pushonoffimg = (ImageView) findViewById(R.id.pushonoffimg);

		sharelocationtimerl = (RelativeLayout) findViewById(R.id.sharelocationtimerl);
		sharelocationtimevalue = (TextView) findViewById(R.id.sharelocationtimevalue);

		if (switchstatus.isEmpty() || switchstatus == null
				|| switchstatus.equalsIgnoreCase("")) {
			pushonoffimg.setImageDrawable(getResources().getDrawable(
					R.drawable.switch_on_icon));
		} else if (switchstatus.trim().equalsIgnoreCase("on")) {
			pushonoffimg.setImageDrawable(getResources().getDrawable(
					R.drawable.switch_on_icon));
		} else {
			pushonoffimg.setImageDrawable(getResources().getDrawable(
					R.drawable.switch_off_icon));
		}

		if (sharelocationinterval.isEmpty() || sharelocationinterval == null
				|| sharelocationinterval.equalsIgnoreCase("")) {
			sharelocationtimevalue.setText("5 minutes");
		} else {
			sharelocationtimevalue.setText(sharelocationinterval);
		}

		pushnotificationsonoffrl.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				SharedPreferences mPrefs1111 = getSharedPreferences(
						"usersettings", 0);
				String switchstatus = mPrefs1111.getString("switchstatus", "");

				if (switchstatus.isEmpty() || switchstatus == null
						|| switchstatus.equalsIgnoreCase("")) {

					// //on hai
					pushonoffimg.setImageDrawable(getResources().getDrawable(
							R.drawable.switch_off_icon));

					SharedPreferences.Editor editor = mPrefs1111.edit();
					editor.putString("switchstatus", "off");
					editor.commit();

					tracker.send(new HitBuilders.EventBuilder()
							.setCategory("Set Push Notifications Off")
							.setAction("Set Push Notifications Off")
							.setLabel("Set Push Notifications Off").build());

					if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
						new updatepushnotificationstatus().executeOnExecutor(
								AsyncTask.THREAD_POOL_EXECUTOR, "off");
					} else {
						new updatepushnotificationstatus().execute("off");
					}

				} else if (switchstatus.trim().equalsIgnoreCase("on")) {

					// //on hai
					pushonoffimg.setImageDrawable(getResources().getDrawable(
							R.drawable.switch_off_icon));

					SharedPreferences.Editor editor = mPrefs1111.edit();
					editor.putString("switchstatus", "off");
					editor.commit();

					tracker.send(new HitBuilders.EventBuilder()
							.setCategory("Set Push Notifications Off")
							.setAction("Set Push Notifications Off")
							.setLabel("Set Push Notifications Off").build());

					if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
						new updatepushnotificationstatus().executeOnExecutor(
								AsyncTask.THREAD_POOL_EXECUTOR, "off");
					} else {
						new updatepushnotificationstatus().execute("off");
					}

				} else {

					// //off hai
					pushonoffimg.setImageDrawable(getResources().getDrawable(
							R.drawable.switch_on_icon));

					SharedPreferences.Editor editor = mPrefs1111.edit();
					editor.putString("switchstatus", "on");
					editor.commit();

					tracker.send(new HitBuilders.EventBuilder()
							.setCategory("Set Push Notifications On")
							.setAction("Set Push Notifications On")
							.setLabel("Set Push Notifications On").build());

					if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
						new updatepushnotificationstatus().executeOnExecutor(
								AsyncTask.THREAD_POOL_EXECUTOR, "on");
					} else {
						new updatepushnotificationstatus().execute("on");
					}

				}
			}
		});

		sharelocationtimerl.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// TODO Auto-generated method stub
				AlertDialog dialog;
				AlertDialog.Builder builder = new AlertDialog.Builder(
						SettingActivity.this);
				builder.setTitle(null);

				final CharSequence str[] = { "2 minutes", "5 minutes",
						"10 minutes" };

				builder.setItems(str, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int position) {
						// TODO Auto-generated method stub

						sharelocationtimevalue.setText(str[position]);

						SharedPreferences sharedPreferences = getSharedPreferences(
								"usersettings", 0);
						SharedPreferences.Editor editor = sharedPreferences
								.edit();
						editor.putString("sharelocationinterval", str[position]
								.toString().trim());
						editor.commit();
					}
				});

				dialog = builder.create();
				dialog.show();

			}
		});
	}

	// ///////
	private class ConnectionTaskForreadunreadnotification extends
			AsyncTask<String, Void, Void> {

		@Override
		protected void onPreExecute() {

		}

		@Override
		protected Void doInBackground(String... args) {
			AuthenticateConnectionreadunreadnotification mAuth1 = new AuthenticateConnectionreadunreadnotification();
			try {
				mAuth1.connection();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				exceptioncheck = true;
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void v) {

			if (exceptioncheck) {
				exceptioncheck = false;
				Toast.makeText(SettingActivity.this,
						getResources().getString(R.string.exceptionstring),
						Toast.LENGTH_LONG).show();
				return;
			}

			if (readunreadnotiresp.equalsIgnoreCase("0")) {

				unreadnoticountrl.setVisibility(View.GONE);

			} else {

				unreadnoticountrl.setVisibility(View.VISIBLE);
				unreadnoticount.setText(readunreadnotiresp);
			}
		}

	}

	public class AuthenticateConnectionreadunreadnotification {

		public AuthenticateConnectionreadunreadnotification() {

		}

		public void connection() throws Exception {

			// Connect to google.com
			HttpClient httpClient = new DefaultHttpClient();
			String url_select = GlobalVariables.ServiceUrl
					+ "/FetchUnreadNotificationCount.php";

			HttpPost httpPost = new HttpPost(url_select);
			BasicNameValuePair MobileNumberBasicNameValuePair = new BasicNameValuePair(
					"MobileNumber", MobileNumber);

			List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();
			nameValuePairList.add(MobileNumberBasicNameValuePair);

			UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(
					nameValuePairList);
			httpPost.setEntity(urlEncodedFormEntity);
			HttpResponse httpResponse = httpClient.execute(httpPost);

			Log.d("httpResponse", "" + httpResponse);

			InputStream inputStream = httpResponse.getEntity().getContent();
			InputStreamReader inputStreamReader = new InputStreamReader(
					inputStream);

			BufferedReader bufferedReader = new BufferedReader(
					inputStreamReader);

			StringBuilder stringBuilder = new StringBuilder();

			String bufferedStrChunk = null;

			while ((bufferedStrChunk = bufferedReader.readLine()) != null) {
				readunreadnotiresp = stringBuilder.append(bufferedStrChunk)
						.toString();
			}

			Log.d("readunreadnotiresp", "" + readunreadnotiresp);

		}
	}

	// ///////
	private class updatepushnotificationstatus extends
			AsyncTask<String, Void, Void> {

		@Override
		protected void onPreExecute() {
		}

		@Override
		protected Void doInBackground(String... args) {
			Authenticateupdatepushnotificationstatus mAuth1 = new Authenticateupdatepushnotificationstatus();
			try {
				mAuth1.status = args[0];
				mAuth1.connection();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void v) {

		}

	}

	public class Authenticateupdatepushnotificationstatus {

		public String status;

		public Authenticateupdatepushnotificationstatus() {

		}

		public void connection() throws Exception {

			// Connect to google.com
			HttpClient httpClient = new DefaultHttpClient();
			String url_select = GlobalVariables.ServiceUrl
					+ "/updatepushnotificationstatus.php";

			HttpPost httpPost = new HttpPost(url_select);
			BasicNameValuePair MobileNumberBasicNameValuePair = new BasicNameValuePair(
					"MobileNumber", MobileNumber.trim());
			BasicNameValuePair PasswordBasicNameValuePair = new BasicNameValuePair(
					"PushStatus", status);

			List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();
			nameValuePairList.add(MobileNumberBasicNameValuePair);
			nameValuePairList.add(PasswordBasicNameValuePair);

			UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(
					nameValuePairList);
			httpPost.setEntity(urlEncodedFormEntity);
			HttpResponse httpResponse = httpClient.execute(httpPost);
		}
	}

	public boolean isOnline() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting()) {
			return true;
		}
		return false;
	}
}
