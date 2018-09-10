package ksotis.com.android_tutorial;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity {
    private TextView todo_text;
    private Button fetch_data_button;
    private static final String API_PREFIX = "https://jsonplaceholder.typicode.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        todo_text = findViewById(R.id.todo_text);
        fetch_data_button = findViewById(R.id.fetch_data_button);

        fetch_data_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Thread urlConnectionThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            URLConnection connection = new URL(API_PREFIX.concat("/todos/1")).openConnection();
                            InputStream response = connection.getInputStream();
                            JSONParser jsonParser = new JSONParser();
                            JSONObject responseObject = (JSONObject)jsonParser.parse(
                                    new InputStreamReader(response, "UTF-8"));
                            todo_text.setText((String)responseObject.get("title"));
                            Log.d("Debugging", responseObject.toJSONString());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

                urlConnectionThread.start();
            }
        });

    }
}
