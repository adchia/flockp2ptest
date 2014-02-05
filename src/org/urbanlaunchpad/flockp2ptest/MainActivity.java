package org.urbanlaunchpad.flockp2ptest;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.urbanlaunchpad.flockp2p.FlockP2PManager;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {
	private static final String PEER_GROUP_NAME = "testPeerGroup";
	private static final String PEER_GROUP_KEY = "testKey";
	private static final String TEXT_MESSAGE_TYPE = "TEXT";
	private static final String REQUEST_URL = "http://requestb.in/10aketa1";

	private FlockP2PManager p2pManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Setup FlockP2P
		p2pManager = new FlockP2PManager(this);
		p2pManager.addMessageType(TEXT_MESSAGE_TYPE);
		ArrayList<String> collection = new ArrayList<String>();

		// Note: add your own device addresses
		collection.add("14:10:9f:dd:56:03");
		collection.add("36:23:ba:9e:a9:f0");
		p2pManager.addPeerGroup(PEER_GROUP_NAME, PEER_GROUP_KEY, collection);

		final EditText editText = (EditText) findViewById(R.id.editText);
		Button button = (Button) findViewById(R.id.button);
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// Create post request for FlockP2P
				JSONObject postRequest = new JSONObject();
				try {
					postRequest.put(FlockP2PManager.REQUEST_URL, REQUEST_URL);
					JSONArray requestParams = new JSONArray();

					JSONObject userInputParams = new JSONObject();
					userInputParams.put(FlockP2PManager.REQUEST_KEY,
							"USER_INPUT");
					userInputParams.put(FlockP2PManager.REQUEST_VALUE, editText
							.getText().toString());

					JSONObject deviceParams = new JSONObject();
					deviceParams.put(FlockP2PManager.REQUEST_KEY, "DEVICE");
					deviceParams.put(FlockP2PManager.REQUEST_VALUE,
							android.os.Build.MODEL);

					requestParams.put(userInputParams);
					requestParams.put(deviceParams);
					postRequest.put(FlockP2PManager.REQUEST_PARAMS,
							requestParams);
				} catch (JSONException e) {
					e.printStackTrace();
				}

				p2pManager.enqueueMessage(TEXT_MESSAGE_TYPE,
						postRequest.toString(), PEER_GROUP_NAME);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
