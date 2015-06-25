package com.clubmycab;

import android.app.Activity;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TNCActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_tnc);
		
		setResult(Activity.RESULT_CANCELED);
		
		TextView textView = (TextView)findViewById(R.id.textViewTNCLink);
		textView.setMovementMethod(LinkMovementMethod.getInstance());
		
		Button button = (Button)findViewById(R.id.buttonTNCAccept);
		button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				setResult(Activity.RESULT_OK);
				finish();
			}
		});
	}
	

}
