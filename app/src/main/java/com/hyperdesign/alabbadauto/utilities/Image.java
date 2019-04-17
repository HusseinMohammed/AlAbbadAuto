package com.hyperdesign.alabbadauto.utilities;

import android.net.Uri;
import android.util.Log;

import static android.content.ContentValues.TAG;

/**
 * Created by Hyper Design Hussien on 2/17/2018.
 */

public class Image {

    //Concert uri to string path of image
    public String getPath(Uri uri){
        //check uri to handle if have null uri
        if (uri == null){
            Log.d(TAG, "getPath: null");
            return null;
        }
        //try to retrieve image from gallery
        return null;
    }
}
