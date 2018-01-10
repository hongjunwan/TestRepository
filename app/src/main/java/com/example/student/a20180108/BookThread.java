package com.example.student.a20180108;

import android.os.Handler;
import android.os.Message;

import com.example.student.a20180108.vo.BookVO;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 네이버 API 서버에 키워드를 보내서 그 결과 데이터를 받아옴.
 * 결과 데이터를 자바에서 사용 가능한 vo 객체에 파싱해서 담기
 *
 * 해당 결과를 화면에 그리는건 직접 못함.
 * 메인 쓰레드의 Handler에게 Message로 보내야 함.
 */

public class BookThread extends Thread {
    private Handler handler;
    private String keyword;
    private int startNum;
    private int tot;

    public BookThread(Handler handler, String keyword,int startNum){
        this.handler = handler;
        this.keyword = keyword;
        this.startNum = startNum;
    }

    @Override
    public void run() {
        String clientId = "yM32xnZ1SocvkRUCWirp";//애플리케이션 클라이언트 아이디값";
        String clientSecret = "hvxkCTLlu6";//애플리케이션 클라이언트 시크릿값";
        try {
            keyword = URLEncoder.encode(keyword, "UTF-8");
            String apiURL = "https://openapi.naver.com/v1/search/book.xml?query=" + keyword + "&start=" + startNum + "&display=10";
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("X-Naver-Client-Id", clientId);
            con.setRequestProperty("X-Naver-Client-Secret", clientSecret);

            int responseCode = con.getResponseCode();
            BufferedReader br;
            if(responseCode==200) { // 정상 호출
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {  // 에러 발생
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }

            XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
            parser.setInput(br);
            List<BookVO> bookVOList = new ArrayList<>();
            BookVO bookVO = null;

            int eventType = parser.getEventType();
            while(eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {
                    if (parser.getName().equals("total")) {
                        String total = parser.nextText();
                        tot = Integer.parseInt(total);
                        break;
                    }
                }
                eventType = parser.next();
            }


            while(eventType != XmlPullParser.END_DOCUMENT){
                if(eventType == XmlPullParser.START_TAG){
                    String tagName = parser.getName();
                    if(tagName.equals("title")){
                        bookVO = new BookVO();
                        String title = parser.nextText();
                        Pattern pattern = Pattern.compile("<.?b>");
                        Matcher matcher = pattern.matcher(title);
                        if(matcher.find()==true){
                            title = matcher.replaceAll("");
                        }
                        bookVO.setBookTitle(title);
                    }else if(tagName.equals("image")){
                        String imgUrl = parser.nextText();
                        bookVO.setImgUrl(imgUrl);
                    }else if(tagName.equals("author")){
                        String author = parser.nextText();
                        bookVO.setAuthor(author);
                    }else if(tagName.equals("price")){
                        String price = parser.nextText();
                        bookVO.setPrice(price);
                    }else if(tagName.equals("publisher")){
                        String pub = parser.nextText();
                        bookVO.setPublisher(pub);
                        bookVOList.add(bookVO);
                    }
                }
                eventType = parser.next();
            }//end while

            Message msg = new Message();
            msg.arg1 = tot;
            msg.obj = bookVOList;
            handler.sendMessage(msg);

            br.close();
            //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
