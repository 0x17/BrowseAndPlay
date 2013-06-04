package com.andreschnabel.browseandplay;

import java.io.File;
import java.io.FileInputStream;

import android.media.MediaPlayer;

public class Song implements Disposable {

    private MediaPlayer mediaPlayer;

    public Song(File file) throws Exception {
        mediaPlayer = new MediaPlayer();
        FileInputStream fis = new FileInputStream(file.getAbsolutePath());
        mediaPlayer.setDataSource(fis.getFD());
        fis.close();
        mediaPlayer.prepare();
        mediaPlayer.start();
    }

    public void dispose() {
        mediaPlayer.stop();
        mediaPlayer.release();
        mediaPlayer = null;
    }
}
