package com.example.student.a20180108;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.student.a20180108.vo.BookVO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by student on 2018-01-08.
 */

public class BookFragment extends Fragment {
    private Context context;
    private EditText bookKeyword;
    private Button bookSearch, btnPrev, btnAfter;
    private ListView listViewBook;

    private List<BookVO> bookVOList;

    private int tot;
    private int startNum = 0;
    private String keyword;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            tot = msg.arg1;
            bookVOList.clear();
            bookVOList.addAll((List<BookVO>) msg.obj);
            adapter.notifyDataSetChanged();
        }
    };

    private BookAdepter adapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book, container, false);

        bookKeyword = view.findViewById(R.id.book_keyword);
        bookSearch = view.findViewById(R.id.book_search);
        btnPrev = view.findViewById(R.id.book_prev);
        btnAfter = view.findViewById(R.id.book_after);
        listViewBook = view.findViewById(R.id.listview_book);

        bookVOList = new ArrayList<>();

        context = container.getContext();

        adapter = new BookAdepter(context, R.layout.item_book, bookVOList);

        listViewBook.setAdapter(adapter);

        bookSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keyword = bookKeyword.getText() + "";
                if (keyword.equals("")) {
                    Toast.makeText(context, "검색어를 입력해주세요", Toast.LENGTH_SHORT).show();
                } else {
                    startNum = 1;
                    new BookThread(handler, keyword, startNum).start();
                }
            }
        });

        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (startNum == 1) {
                    Toast.makeText(context, "첫 페이지 입니다.", Toast.LENGTH_SHORT).show();
                } else if (startNum == 0) {
                    Toast.makeText(context, "검색 결과가 없습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    startNum = startNum - 10;
                    keyword = bookKeyword.getText() + "";
                    new BookThread(handler, keyword, startNum).start();
                }
            }
        });

        btnAfter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ((startNum + 10) > tot) {
                    if (startNum == 0) {
                        Toast.makeText(context, "검색 결과가 없습니다.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "마지막 페이지 입니다.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    startNum = startNum + 10;
                    keyword = bookKeyword.getText() + "";
                    new BookThread(handler, keyword, startNum).start();
                }
            }
        });
        return view;
    }
}
