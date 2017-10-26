package stage.hoogtebepaling5;

        import android.app.Activity;
        import android.content.Intent;
        import android.os.Bundle;

/**
 * Created by vamsikrishna on 12-Feb-15.
 */
public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Creates the splashscreen content from the splashscreen.xml file in /layour
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen);

        Thread timerThread = new Thread(){
            public void run(){
                try{
                    //goes on for two seconds
                    sleep(2000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }finally{
                    //after two seconds, go to mainactivity classs
                    Intent intent = new Intent(SplashActivity.this,MainActivity.class);
                    startActivity(intent);
                }
            }
        };
        timerThread.start();
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        finish();
    }

}