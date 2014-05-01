package com.barbourbooks.biblecrosswordpuzzles;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.barbourbooks.biblecrosswordpuzzles.util.CustomImageView;

public class GameSelection extends BaseActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_selection);
		
		TextView newpuzzle = (TextView) findViewById(R.id.button_newpuzzle);
		newpuzzle.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				 startActivity(new Intent(GameSelection.this,Newgame.class));
				
			}
		});
		TextView savedpuzzle = (TextView) findViewById(R.id.button_savedpuzzle);
		savedpuzzle.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				 startActivity(new Intent(GameSelection.this,Savedgame.class));
				
			}
		});
		TextView optionpuzzle = (TextView) findViewById(R.id.button_options);
		optionpuzzle.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				 startActivity(new Intent(GameSelection.this,Options.class));
				
			}
		});
		TextView helppuzzle = (TextView) findViewById(R.id.button_help);
		helppuzzle.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				 startActivity(new Intent(GameSelection.this,Help.class));
				
			}
		});
		TextView highScore = (TextView) findViewById(R.id.button_highscore);
		highScore.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
//				 startActivity(new Intent(GameSelection.this,Help.class));
				 startActivity(new Intent(GameSelection.this,HighScore.class));
				
			}
		});
		CustomImageView facebook = (CustomImageView) findViewById(R.id.facebook);
		facebook.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				 onClickLogin();
			}
		});
	}
	
	public void onBibbleClick(View view) {
		
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setData(Uri.parse(url));
		startActivity(i);
	}
	public void goToHome(View view) {
		finish();
		navigatetoHome();
	}
	
	public void onScreenSelection(View view){
	}
}
