package susinth.josh.weatherapplication;



import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private TextView text_temp;
    private TextView text_city;
    private TextView text_description;
    private TextView text_date;
    private EditText enterCity;
    private String cityName;
    private ImageView Search;
    private EditText editCityName;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text_temp=findViewById(R.id.txtTemp);
        text_city=findViewById(R.id.txtCity);
        text_description=findViewById(R.id.txtDesc);
        text_date=findViewById(R.id.txtDate);
        Search=(ImageView)findViewById(R.id.imgSearch);
        editCityName=findViewById(R.id.editCityName);
        find_weather("Chennai");
        Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cityName=editCityName.getText().toString();
                editCityName.setText("");
                if(!cityName.isEmpty()){
                    find_weather(cityName);
                }
                else{
                    Toast.makeText(getApplicationContext(),"Enter City Name", Toast.LENGTH_SHORT).show();
                    //find_weather("Chennai");
                }
            }
        });

    }



    public void find_weather(String cityName) {


        cityName=cityName.substring(0,1).toUpperCase()+cityName.substring(1).toLowerCase();



        String url="https://api.openweathermap.org/data/2.5/weather?q="+cityName+"&appid=01138a7db759c13b7b0a1390ed862fff&units=metric";
        JsonObjectRequest jor= new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONObject main_object=response.getJSONObject("main");
                    JSONArray weatherArray=response.getJSONArray("weather");
                    JSONObject object=weatherArray.getJSONObject(0);

                    String temp= String.valueOf(main_object.getDouble("temp"));
                    String description=object.getString("description");
                    String city=response.getString("name");
                    System.out.println(temp);
                    Calendar calendar=Calendar.getInstance();
                    SimpleDateFormat sdf=new SimpleDateFormat("EEEE-MM-dd");
                    String formattedDate=sdf.format(calendar.getTime());


                    text_date.setText(formattedDate);
                    text_city.setText(city);
                    text_description.setText(description);
                    text_temp.setText(temp);


                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        RequestQueue queue= Volley.newRequestQueue(this);
        queue.add(jor);
    }
}