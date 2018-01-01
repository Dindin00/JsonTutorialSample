package tw.idv.dindin00.jsontutorialsample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
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
            //此處用於處理成功取得Json資料後的動作
            @Override
            public void onResponse(JSONObject response) {
                //初始化結果文字確保每次內容不會混雜在一起
                result = "";
                //網路處理可能會有錯誤因此都需要做try catch
                try {
                    //response已經剝去原本JSON資料的物件外殼，
                    //接著要剝去result物件外殼
                    JSONObject JO_result = response.getJSONObject("result");
                    //接著要剝去records陣列外殼
                    JSONArray JA_records = JO_result.getJSONArray("records");
                    //用迴圈讀出每個陣列裡面的JSON物件的內容
                    for (int i = 0; i < JA_records.length(); i++) {
                        JSONObject temp = JA_records.getJSONObject(i);
                        //將讀出來的內容存至結果文字中
                        result += temp.getString("name") + "：" + temp.getString("address") + "\n";
                    }
                    //將結果文字顯示在TextView上
                    ((TextView) findViewById(R.id.show)).setText(result);
                } catch (JSONException e) {
                    //將錯誤訊息顯示在TextView上
                    ((TextView) findViewById(R.id.show)).setText(e.toString());
                }
            }
        }, new Response.ErrorListener() {
            //此處用於處理無法取得Json資料的動作
            @Override
            public void onErrorResponse(VolleyError error) {
                //顯示獲取資料失敗在TextView上
                ((TextView) findViewById(R.id.show)).setText("獲取資料失敗");
            }
        });

        //為jsonObjectRequest套上標籤方便管理
        jsonObjectRequest.setTag(jsonTAG);

        //將設定好的jsonObjectRequest放入佇列開始工作
        requestQueue.add(jsonObjectRequest);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //APP結束後要關閉網路連線工作
        //如果佇列不是空的就代表有工作
        if (requestQueue != null) {
            //將抓取json資料的工作取消
            requestQueue.cancelAll(jsonTAG);
        }
    }
}
