package tw.idv.dindin00.jsontutorialsample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    // volley用來處理網路連線工作的佇列
    RequestQueue requestQueue;
    //目標json的網址
    String jsonUrl;
    // volley用來要求json資料的類別，
    // 由於本次範例資料最外層是jsonObject故採用此，
    // 若是jsonArray則改JsonArrayRequest即可
    JsonObjectRequest jsonObjectRequest;
    // 用來標記我們的網路連線工作
    String jsonTAG;
    // 最後顯示的結果文字
    String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //初始化requestQueue，注意此處不用new產生
        requestQueue = Volley.newRequestQueue(this);
        //初始化網址，此處採用新北市政府新聞局的新北市電影院名冊
        jsonUrl = "http://data.ntpc.gov.tw/api/v1/rest/datastore/382000000A-000132-002";
        //初始化jsonTAG，方便後面快速標記
        jsonTAG = "getOpenData";
        //初始化jsonObjectRequest，並進行json解析
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, jsonUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //初始化結果文字確保每次內容不會混雜在一起
                result = "";
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }
}
