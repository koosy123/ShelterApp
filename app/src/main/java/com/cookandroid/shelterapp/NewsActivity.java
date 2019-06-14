package com.cookandroid.shelterapp;


import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NewsActivity extends AppCompatActivity {
    XmlPullParser xpp;
    String data;
    String keyword;
    String clientID = "peNZCobLDtU0_9OqG7dw";//애플리케이션 클라이언트 아이디값";
    String clientSecret = "bYemkkmY0T";//애플리케이션 클라이언트 시크릿값";
    HttpURLConnection conn;
    ListView listview;
    ListViewAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news1);
        listview = (ListView)findViewById(R.id.listview);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                newsItem item = (newsItem) adapterView.getItemAtPosition(i);
                String link = item.getNewsurl();
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(link));
                    startActivity(intent);

            }
        });
    }

    //XmlPullParser를 이용하여 Naver 에서 제공하는 OpenAPI XML 파일 파싱하기(parsing)


    //Button을 클릭했을 때 자동으로 호출되는 callback method....
    public void mOnClick(View v){
        switch( v.getId() ){
            case R.id.button1:
                keyword="태풍";
                break;
            case R.id.button2:
                keyword="산불";
                break;
            case R.id.button3:
                keyword="지진";
                break;
            case R.id.button4:
                keyword="호우";
                break;

        }
        try {
            String apiURL = "https://openapi.naver.com/v1/search/news.xml?query=" + keyword + "&display=10" + "&start=1";
            Log.d("주소",apiURL);
            new DownloadWebpageTask().execute(apiURL);

        }catch(Exception e){};
    }//mOnClick method..
    private class DownloadWebpageTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            try {
                Log.d("유알엘",urls[0]);
                return (String)downloadUrl((String)urls[0]);
            } catch (IOException e) {
                return "==>다운로드 실패";
            }
        }
        protected void onPostExecute(String result) {
            try{
                Log.d("===" , result);
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(true);
                XmlPullParser xpp = factory.newPullParser();

                adapter = new ListViewAdapter();
                xpp.setInput(new StringReader(result));
                xpp.next();
                int eventType = xpp.getEventType();
                boolean blink = false, bTitle = false, bContent= false;
                boolean bNo = false;
                String link= "", title="", content="";
                while (eventType != XmlPullParser.END_DOCUMENT) {
                    switch (eventType) {
                        case  XmlPullParser.START_DOCUMENT:
                            break;
                        case XmlPullParser.START_TAG:
                            String tag = xpp.getName(); //태그 이름 얻어오기

                            if (tag.equals("item")) ; //첫번째 검색 결과
                            else if (tag.equals("link")) {
                                xpp.next();
                                link = xpp.getText();
                            }
                            else if (tag.equals("title")) {
                                xpp.next();

                                title = xpp.getText().replaceAll("<(/)?([a-zA-Z]*)(\\\\s[a-zA-Z]*=[^>]*)?(\\\\s)*(/)?>", "");
                                title = title.replaceAll("&quot;", "\"");

                            } else if (tag.equals("description")) {
                                xpp.next();
                                content = xpp.getText().replaceAll("<(/)?([a-zA-Z]*)(\\\\s[a-zA-Z]*=[^>]*)?(\\\\s)*(/)?>", "");
                                content = content.replaceAll("&quot;", "\"");
                                bNo = true;
                            }
                            if (bNo) {
                                if(!content.contains("Naver Search Result"))
                                    adapter.addItem(link,title,content);
                                bNo=false;
                            }
                            break;
                    }
                    eventType = xpp.next();
                }
                listview.setAdapter(adapter);
            }catch(Exception e){

            }
        }

    }
    private String downloadUrl(String myurl) throws IOException {
        conn = null;
        try {
            Log.d("downloadUrl : " , myurl);
            URL url = new URL(myurl);     // url 객체로 바꿈
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("X-Naver-Client-Id", clientID);
            conn.setRequestProperty("X-Naver-Client-Secret", clientSecret);
            BufferedInputStream buf = new BufferedInputStream(conn.getInputStream());
            BufferedReader bufreader = new BufferedReader(new InputStreamReader(buf, "utf-8"));
            String line = null;
            String page = "";
            while ((line = bufreader.readLine()) != null) {
                page += line;
                Log.d("라인", line);
            }
            return page;
        } catch(Exception e){
            return " ";
        }
        finally {
            conn.disconnect();
        }
    }

}//MainActivity class..