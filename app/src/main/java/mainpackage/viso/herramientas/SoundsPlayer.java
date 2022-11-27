package mainpackage.viso.herramientas;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import mainpackage.viso.R;

public class SoundsPlayer {
    public static SoundPool soundPool;
    private static int tapSound;
    private static int doneSound;
    private static int successSound;
    public SoundsPlayer(Context context){
        soundPool = new SoundPool(2, AudioManager.STREAM_MUSIC,0);
        tapSound = soundPool.load(context,R.raw.clap,1);
        doneSound = soundPool.load(context,R.raw.success,1);
        successSound = soundPool.load(context,R.raw.finish,1);
    }
    public void playTapSound(){
        soundPool.play(tapSound,1.0f,1.0f,1,0,1.0f);
    }
    public void playDoneSound(){
        soundPool.play(doneSound,1.0f,1.0f,1,0,1.0f);
    }
    public void playSuccessSound(){
        soundPool.play(successSound,1.0f,1.0f,1,0,1.0f);
    }

}
