package tw.idv.dindin00.jsontutorialsample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;

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
    }
}
