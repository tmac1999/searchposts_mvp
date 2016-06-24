package com.mrz.searchposts.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

public class CommonUtils {
	public static int dp2px(Context context,int dpValue){
		float density = context.getResources().getDisplayMetrics().density;
		int px = (int) (dpValue * density + 0.5f);
		return px;
	}
    /**
     * Try to return the absolute file path from the given Uri
     *
     * @param context
     * @param uri
     * @return the file path or null
     */
    public static String getRealFilePath( final Context context, final Uri uri ) {
        if ( null == uri ) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if ( scheme == null )
            data = uri.getPath();
        else if ( ContentResolver.SCHEME_FILE.equals( scheme ) ) {
            data = uri.getPath();
        } else if ( ContentResolver.SCHEME_CONTENT.equals( scheme ) ) {
            Cursor cursor = context.getContentResolver().query( uri, new String[] { MediaStore.Images.ImageColumns.DATA ,MediaStore.Images.ImageColumns.DISPLAY_NAME,MediaStore.Images.ImageColumns.SIZE,MediaStore.Images.ImageColumns.DATE_ADDED }, null, null, null );
            if ( null != cursor ) {
                if ( cursor.moveToFirst() ) {
                    int index = cursor.getColumnIndex( MediaStore.Images.ImageColumns.DATA );
                    if ( index > -1 ) {
                        data = cursor.getString( index );
                        String display = cursor.getString( 1 );
                        String data1 = cursor.getString( 2 );
                        String data2 = cursor.getString( 3 );
                        //TODO upload img in android 5.0+
                    }
                }
                cursor.close();
            }
        }
        return data;
    }
    public static void getUri(){
//        String type = Utils.ensureNotNull(intent.getType());
//        Log.d(TAG, "uri is " + uri);
//        if (uri.getScheme().equals("file") && (type.contains("image/"))) {
//            String path = uri.getEncodedPath();
//            Log.d(TAG, "path1 is " + path);
//            if (path != null) {
//                path = Uri.decode(path);
//                Log.d(TAG, "path2 is " + path);
//                ContentResolver cr = this.getContentResolver();
//                StringBuffer buff = new StringBuffer();
//                buff.append("(")
//                        .append(Images.ImageColumns.DATA)
//                        .append("=")
//                        .append("'" + path + "'")
//                        .append(")");
//                Cursor cur = cr.query(
//                        Images.Media.EXTERNAL_CONTENT_URI,
//                        new String[] { Images.ImageColumns._ID },
//                        buff.toString(), null, null);
//                int index = 0;
//                for (cur.moveToFirst(); !cur.isAfterLast(); cur
//                        .moveToNext()) {
//                    index = cur.getColumnIndex(Images.ImageColumns._ID);
//                    // set _id value
//                    index = cur.getInt(index);
//                }
//                if (index == 0) {
//                    //do nothing
//                } else {
//                    Uri uri_temp = Uri
//                            .parse("content://media/external/images/media/"
//                                    + index);
//                    Log.d(TAG, "uri_temp is " + uri_temp);
//                    if (uri_temp != null) {
//                        uri = uri_temp;
//                    }
//                }
//
           }
}
