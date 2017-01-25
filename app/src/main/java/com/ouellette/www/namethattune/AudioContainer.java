package com.ouellette.www.namethattune;

import android.content.Context;
import android.media.MediaMetadataRetriever;
import android.net.Uri;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by douellette20 on 1/24/2017.
 */

public class AudioContainer {

    MediaMetadataRetriever mmr = new MediaMetadataRetriever();
    HashMap<String, Integer> songs = new HashMap<>();
    Context con;

    public AudioContainer(Context context){
        con = context;
        Field[] fields = R.raw.class.getFields();
        for(Field f : fields){
            f.setAccessible(true);
            try {
                songs.put(getStringRep(Uri.parse("android.resource://com.ouellette.www.namethattune/"+ f.getInt(f))), f.getInt(f));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            catch (IllegalArgumentException e){
                e.printStackTrace();
            }
        }
    }


    private String getStringRep(Uri mediaPath){
        mmr.setDataSource(con, mediaPath);

        return String.format("%s - %s", mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE),
                mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST));
    }

    public HashMap<String, Integer> getMapClone(){
        return (HashMap<String, Integer>)songs.clone();
    }
}
