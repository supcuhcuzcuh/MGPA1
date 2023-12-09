package com.sdm.mgpa1;

import android.content.res.Resources;
import android.media.MediaPlayer;
import android.view.SurfaceView;

import java.util.HashMap;

public class AudioManager {
    public final static AudioManager Instance = new AudioManager();

    private Resources res = null;
    private SurfaceView view = null;
    private HashMap<Integer, MediaPlayer> audioMap = new HashMap<Integer, MediaPlayer>();

    private AudioManager()
    {

    }

    public void Init(SurfaceView _view)
    {
        view = _view;
        res = _view.getResources();
    }

    public void PlayAudio(int _id, float leftVol) {
        if (audioMap.containsKey(_id)) {
            audioMap.get(_id).reset();
            audioMap.remove(_id);
        }

        // Load the audio
        MediaPlayer newAudio = MediaPlayer.create(view.getContext(), _id);
        audioMap.put(_id, newAudio);

        // Set the left volume
        if (leftVol >= 0.0f && leftVol <= 1.0f) {
            newAudio.setVolume(leftVol, 5000);  // leftVol for left channel, 1.0f for right channel
        } else {
            // Handle invalid left volume (should be between 0.0 and 1.0)
            // You can set a default value or log an error.
        }

        // Start playback
        newAudio.start();
    }

    public boolean IsPlaying(int _id)
    {
        if (!audioMap.containsKey(_id))
            return false;

        return audioMap.get(_id).isPlaying();
    }
}
