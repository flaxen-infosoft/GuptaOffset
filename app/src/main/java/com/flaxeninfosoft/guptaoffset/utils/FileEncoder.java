package com.flaxeninfosoft.guptaoffset.utils;

import android.content.ContentResolver;
import android.net.Uri;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileEncoder {

    public static String encodeImage(ContentResolver resolver, Uri uri) throws IOException {

        InputStream inputStream = resolver.openInputStream(uri);
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }

        byte[] bytes = byteBuffer.toByteArray();

        String encode = Base64.encodeToString(bytes, Base64.DEFAULT);
        Log.i("CRM-LOG-ENCODED", encode);
        return encode;
    }
}
