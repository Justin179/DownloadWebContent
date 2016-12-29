package tw.com.nec.justin_chen.downloadwebcontent;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    // 用另一個執行緒在背景執行，這樣載入才會快
    // 第一個參數: 傳入url http://www.ecowebhosting.co.uk/
    // 第三個參數: 回傳的字串
    public class DownloadTask extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... urls) {

            //Log.i("url: ", urls[0]);

            StringBuilder result = new StringBuilder();
            URL url;
            // open up a browser
            HttpURLConnection urlConnection = null;

            try{

                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection)url.openConnection();

                InputStream in = urlConnection.getInputStream();

                InputStreamReader reader = new InputStreamReader(in);

                int data = reader.read();

                while(data!=-1){
                    char current = (char)data;
                    result.append(current); // 加一個字元
                    data = reader.read(); // 讀下一個字元，直到最後(-1)
                }

                return result.toString();



            } catch(Exception e){
                e.printStackTrace();
                return "failed";
            }

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DownloadTask task = new DownloadTask();
        String result = null;
        try {

            result = task.execute("http://www.ecowebhosting.co.uk/").get();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        Log.i("Content of URL: ",result);

    }
}
