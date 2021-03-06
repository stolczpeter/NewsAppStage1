package com.example.peterstolcz.newsappstage1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;


public class NewsAdapter extends ArrayAdapter<News> {

    public NewsAdapter(Context context, List<News> newslist) {
        super(context, 0, newslist);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.news_list_item, parent, false);
        }

        News currentNews = getItem(position);

        TextView titleView = (TextView) listItemView.findViewById(R.id.title);
        String formattedTitle = currentNews.getmTitle();
        titleView.setText(formattedTitle);

        TextView sectionnameView = (TextView) listItemView.findViewById(R.id.sectionname);
        String formattedSectionname = currentNews.getmSectionname();
        sectionnameView.setText(formattedSectionname);

        TextView dateView = (TextView) listItemView.findViewById(R.id.date);
        String formattedDate = currentNews.getmDate();
        dateView.setText(formattedDate);

        TextView authorView = (TextView) listItemView.findViewById(R.id.author);
        String formattedAuthor = currentNews.getmAuthor();
        authorView.setText(formattedAuthor);

        return listItemView;

    }

}
