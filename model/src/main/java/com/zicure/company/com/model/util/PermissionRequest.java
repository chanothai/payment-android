package com.zicure.company.com.model.util;

import android.Manifest;
import android.content.Context;

/**
 * Created by 4GRYZ52 on 2/1/2017.
 */

public class PermissionRequest {
    private static PermissionRequest me = null;
    private Context context = null;


    public PermissionRequest(Context context){
        this.context = context;
    }

    public static PermissionRequest newInstance(Context context){
        if (me == null){
            me = new PermissionRequest(context);
        }
        return me;
    }

    public void requestCamera(){
        if (!PermissionManager.getInstance(context).checkPermission(Manifest.permission.CAMERA, 123)){

        }
    }
}
