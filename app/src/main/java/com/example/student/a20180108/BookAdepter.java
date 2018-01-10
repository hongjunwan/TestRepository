package com.example.student.a20180108;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.student.a20180108.vo.BookVO;

import java.util.List;

/**
 * Created by student on 2018-01-08.
 */

public class BookAdepter extends ArrayAdapter<BookVO> {
    private Activity context;
    private int layout;
    private List<BookVO> bookVOList;

    public BookAdepter(@NonNull Context context, int resource, @NonNull List<BookVO> objects) {
        super(context, resource, objects);

        this.context = (Activity) context;
        this.layout = resource;
        this.bookVOList = objects;

    }

    class BookHolder {
        TextView tvTitle;
        TextView tvPrice;
        TextView tvPub;
        TextView tvAuthor;
        ImageView imageBook;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        BookHolder holder = null;

        if (convertView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            convertView = inflater.inflate(layout, parent, false);
            holder = new BookHolder();
            holder.tvAuthor = convertView.findViewById(R.id.book_author);
            holder.tvPrice = convertView.findViewById(R.id.book_price);
            holder.tvPub = convertView.findViewById(R.id.book_pub);
            holder.tvTitle = convertView.findViewById(R.id.book_title);
            holder.imageBook = convertView.findViewById(R.id.book_image);

            convertView.setTag(holder);
        } else {
            holder = (BookHolder) convertView.getTag();
        }

        BookVO b = bookVOList.get(position);
        holder.tvTitle.setText(b.getBookTitle());
        holder.tvPub.setText(b.getPublisher());
        holder.tvPrice.setText(b.getPrice());
        holder.tvAuthor.setText(b.getAuthor());

//        new BookImageTask(holder.imageBook,b.getImgUrl()).execute();
        Glide.with(context).load(b.getImgUrl()).into(holder.imageBook);

        return convertView;
    }
}
