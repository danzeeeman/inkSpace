package cc.openframeworks.inkSpace;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.channels.FileChannel;
import java.util.Calendar;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.Date;
import java.text.SimpleDateFormat;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import cc.openframeworks.OFAndroid;
import android.util.Log;

public class OFActivity extends cc.openframeworks.OFActivity{


	private static Context context;
	 
	public static Boolean copyFile(File msg, File destFile)
		      throws IOException {
		    if (!destFile.exists()) {
		      destFile.createNewFile();

		      FileChannel source = null;
		      FileChannel destination = null;
		      try {
		        source = new FileInputStream(msg).getChannel();
		        destination = new FileOutputStream(destFile).getChannel();
		        destination.transferFrom(source, 0, source.size());
		      } finally {
		        if (source != null)
		          source.close();
		        if (destination != null)
		          destination.close();
		      }
		      return true;
		    }
		    return false;
		  }
		
	
	
	
	
	public static void shareImage(String msg){  
		

		 File path = Environment.getExternalStoragePublicDirectory(
			     Environment.DIRECTORY_PICTURES
			  );
		 
		 Date today = Calendar.getInstance().getTime();
		 SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");
		 String dateString = formatter.format(today);
		    
		 
		 
		  File imageFileName = new File(path, dateString + ".gif"); //imageFileFolder

		  
		  File temp = new File(msg);
		 
		  try {
			copyFile(temp, imageFileName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  
		  String[] files = new String[1];
		  files[0] = imageFileName.toString();
		  scanMedia(files);
		  
		  Intent intent = getShareIntent(imageFileName.toString(),"made with ink space");
		  intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		  context.startActivity(intent);
		  
	}
	
	public static Intent getShareIntent(String path, String caption)
    {
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("image/*");
 
        //add the file uri
        Uri uri = Uri.parse("file://" + path);
        share.putExtra(Intent.EXTRA_STREAM, uri);
 
        //add caption if present
        if(caption != "")
            share.putExtra(Intent.EXTRA_TEXT, caption);
 
        return share;
    }
	
	
	 private static void scanMedia(String[] files)
	    {
	        MediaScannerConnection.scanFile(
	        		context,
	                files,
	                null,
	                new MediaScannerConnection.OnScanCompletedListener() {
	                    @Override
	                    public void onScanCompleted(String path, Uri uri) {
	                        Log.d("file", "file " + path + " was scanned successfully: " + uri);
	                    }
	                }
	        );
	    }
	
	private static void scanPhoto(String imageFileName)
	{
	  Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
	  File f = new File(imageFileName);
	  Uri contentUri = Uri.fromFile(f);
	  mediaScanIntent.setData(contentUri);
	  //this.cordova.getContext().sendBroadcast(mediaScanIntent); //this is deprecated
	  context.sendBroadcast(mediaScanIntent); 
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onDetachedFromWindow() {
	}

	// Menus
	// http://developer.android.com/guide/topics/ui/menus.html
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Create settings menu options from here, one by one or infalting an xml
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// This passes the menu option string to OF
		// you can add additional behavior from java modifying this method
		// but keep the call to OFAndroid so OF is notified of menu events
		if(OFAndroid.menuItemSelected(item.getItemId())){

			return true;
		}
		return super.onOptionsItemSelected(item);
	}


	@Override
	public boolean onPrepareOptionsMenu (Menu menu){
		// This method is called every time the menu is opened
		//  you can add or remove menu options from here
		return  super.onPrepareOptionsMenu(menu);
	}
	
}



