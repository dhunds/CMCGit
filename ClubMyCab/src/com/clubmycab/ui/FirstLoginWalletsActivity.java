package com.clubmycab.ui;

import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.clubmycab.R;
import com.clubmycab.asynctasks.GlobalAsyncTask;
import com.clubmycab.asynctasks.GlobalAsyncTask.AsyncTaskResultListener;
import com.clubmycab.utility.GlobalMethods;
import com.clubmycab.utility.GlobalVariables;
import com.clubmycab.utility.Log;

public class FirstLoginWalletsActivity extends Activity implements
		AsyncTaskResultListener {

	String FullName, MobileNumber;

	LinearLayout otpLinearLayout;

	EditText otpEditText, mobileEditText, emailEditText;
	Button continuewithotp, sendOTP;

	private final String LINK_WALLET = "LinkWallet";
	private final String CREATE_WALLET = "CreateWallet";
	String walletAction = "";
	
	String mobilenumber;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_first_login_wallet);

		SharedPreferences mPrefs = getSharedPreferences("FacebookData", 0);
		FullName = mPrefs.getString("FullName", "");
		MobileNumber = mPrefs.getString("MobileNumber", "");

		mobilenumber = "8200012345";
		// mobilenumber = MobileNumber.substring(4);
		// String email = "testingone@mail.com";

		otpLinearLayout = (LinearLayout) findViewById(R.id.walletFLLinearLayout);

		mobileEditText = (EditText) findViewById(R.id.editTextWalletMobile);
		mobileEditText.setText(MobileNumber.substring(4));

		emailEditText = (EditText) findViewById(R.id.editTextWalletEmail);

		otpEditText = (EditText) findViewById(R.id.otpedittext);

		sendOTP = (Button) findViewById(R.id.buttonSendOTP);
		sendOTP.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (walletAction.equals(LINK_WALLET)) {
					generateOTP();
				} else if (walletAction.equals(CREATE_WALLET)) {

				}
			}
		});

		continuewithotp = (Button) findViewById(R.id.continuewithotp);
		continuewithotp.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				
				String msgcode = "507";
				String amount = "10";
				String tokenType = "1";
				String otp = otpEditText.getText().toString().trim();

				String checksumstring = GlobalMethods
						.calculateCheckSumForService("'" + amount + "''"
								+ mobilenumber + "''"
								+ GlobalVariables.Mobikwik_MerchantName + "''"
								+ GlobalVariables.Mobikwik_Mid + "''" + msgcode
								+ "''" + otp + "''" + tokenType + "'",
								GlobalVariables.Mobikwik_14SecretKey);
				String endpoint = GlobalVariables.Mobikwik_ServerURL
						+ "/tokengenerate";
				String params = "cell=" + mobilenumber + "&amount=" + amount
						+ "&otp=" + otp + "&msgcode=" + msgcode + "&mid="
						+ GlobalVariables.Mobikwik_Mid + "&merchantname="
						+ GlobalVariables.Mobikwik_MerchantName + "&tokentype="
						+ tokenType + "&checksum=" + checksumstring;
				Log.d("otpgenerate", "tokengenerate endpoint : " + endpoint
						+ " params : " + params);
				new GlobalAsyncTask(FirstLoginWalletsActivity.this, endpoint,
						params, null, FirstLoginWalletsActivity.this, true,
						"tokengenerate", true);
			}
		});
		
		String msgcode = "500";
		String action = "existingusercheck";

		String checksumstring = GlobalMethods.calculateCheckSumForService("'"
				+ action + "''" + mobilenumber + "''"
				+ GlobalVariables.Mobikwik_MerchantName + "''"
				+ GlobalVariables.Mobikwik_Mid + "''" + msgcode + "'",
				GlobalVariables.Mobikwik_14SecretKey);
		String endpoint = GlobalVariables.Mobikwik_ServerURL + "/querywallet";
		String params = "cell=" + mobilenumber + "&msgcode=" + msgcode
				+ "&action=" + action + "&mid=" + GlobalVariables.Mobikwik_Mid
				+ "&merchantname=" + GlobalVariables.Mobikwik_MerchantName
				+ "&checksum=" + checksumstring;
		Log.d("WalletsActivity", "querywallet endpoint : " + endpoint
				+ " params : " + params);
		new GlobalAsyncTask(this, endpoint, params, null, this, true,
				"querywallet", true);
	}

	private void generateOTP() {
		String msgcode = "504";
		String amount = "10";
		String tokenType = "1";

		String checksumstring = GlobalMethods.calculateCheckSumForService("'"
				+ amount + "''" + mobilenumber + "''"
				+ GlobalVariables.Mobikwik_MerchantName + "''"
				+ GlobalVariables.Mobikwik_Mid + "''" + msgcode + "''"
				+ tokenType + "'", GlobalVariables.Mobikwik_14SecretKey);
		String endpoint = GlobalVariables.Mobikwik_ServerURL + "/otpgenerate";
		String params = "cell=" + mobilenumber + "&amount=" + amount
				+ "&msgcode=" + msgcode + "&mid="
				+ GlobalVariables.Mobikwik_Mid + "&merchantname="
				+ GlobalVariables.Mobikwik_MerchantName + "&tokentype="
				+ tokenType + "&checksum=" + checksumstring;
		Log.d("otpgenerate", "querywallet endpoint : " + endpoint
				+ " params : " + params);
		new GlobalAsyncTask(FirstLoginWalletsActivity.this, endpoint, params,
				null, FirstLoginWalletsActivity.this, true, "otpgenerate", true);
	}

	@Override
	public void getResult(String response, String uniqueID) {

		if (uniqueID.equals("querywallet")) {
			try {
				JSONObject jsonObject = new JSONObject(response);
				Log.d("FirstLoginWalletActivity", "querywallet jsonObject : "
						+ jsonObject);
				if (jsonObject.getString("status").equals("SUCCESS")) {

					AlertDialog.Builder builder = new AlertDialog.Builder(
							FirstLoginWalletsActivity.this);
					builder.setMessage("You already have a Mobikwik wallet registered with your number, would you like to link it with the app?");
					builder.setCancelable(false);

					builder.setPositiveButton("Yes",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {

									walletAction = LINK_WALLET;
									continuewithotp.setText("Link Wallet");
									Toast.makeText(
											FirstLoginWalletsActivity.this,
											"Press the 'Send OTP' button to proceed",
											Toast.LENGTH_LONG).show();
								}
							});

					builder.setNegativeButton("Maybe later",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									Intent mainIntent = new Intent(
											FirstLoginWalletsActivity.this,
											FirstLoginClubsActivity.class);
									startActivity(mainIntent);
								}
							});

					builder.show();
				} else {
					AlertDialog.Builder builder = new AlertDialog.Builder(
							FirstLoginWalletsActivity.this);
					builder.setMessage("You do not have a Mobikwik wallet registered with your number, would you like to create one?");
					builder.setCancelable(false);

					builder.setPositiveButton("Yes",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {

									walletAction = CREATE_WALLET;

									continuewithotp.setText("Create Wallet");
									Toast.makeText(
											FirstLoginWalletsActivity.this,
											"Press the 'Send OTP' button to proceed",
											Toast.LENGTH_LONG).show();
								}
							});

					builder.setNegativeButton("Maybe later",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									Intent mainIntent = new Intent(
											FirstLoginWalletsActivity.this,
											FirstLoginClubsActivity.class);
									startActivity(mainIntent);
								}
							});

					builder.show();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (uniqueID.equals("otpgenerate")) {
			Log.d("FirstLoginWalletActivity", "otpgenerate response : "
					+ response);
			try {
				JSONObject jsonObject = new JSONObject(response);
				if (jsonObject.getString("status").equals("SUCCESS")) {
					otpLinearLayout.setVisibility(View.VISIBLE);
				} else {

				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (uniqueID.equals("tokengenerate")) {
			Log.d("FirstLoginWalletActivity", "tokengenerate response : "
					+ response);
		}
	}

}
