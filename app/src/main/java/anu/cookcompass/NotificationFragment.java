package anu.cookcompass;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import anu.cookcompass.model.PopMsg;
import anu.cookcompass.model.ThemeColor;
import anu.cookcompass.model.ThemeConfig;

public class NotificationFragment extends Fragment {
    private View rootView;
    private ListView NotiListView;
    private NotificationAdapter adapter;
    private List<PopMsg> notificationList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rootView = inflater.inflate(R.layout.fragment_notification, container, false);
        //change the color when create
        ThemeConfig themeConfig = ((MainActivity) requireActivity()).getThemeConfig();
        rootView.setBackgroundColor(Color.parseColor(themeConfig.getTheme()));
        System.out.println("theme config in notification" + themeConfig.getTheme());

        NotiListView = rootView.findViewById(R.id.notification_listview);

        // TODO: don't work
        PopMsg popMsg = new PopMsg();
        popMsg.uid = "uid";
        popMsg.location = "location";
        popMsg.rid = 1;
        popMsg.username = "username";
        popMsg.title = "title";
        notificationList.add(popMsg);
        // TODO: don't work

        adapter = new NotificationAdapter(requireContext(),notificationList);
        NotiListView.setAdapter(adapter);

        // set initial theme
        rootView.setBackgroundColor(Color.parseColor(ThemeColor.getThemeColor()));

//        PopMsgManager.getInstance().addObserver(popMsgs->{
//            adapter.dataSet = popMsgs;
//            adapter.notifyDataSetChanged();
//        });
        return rootView;
    }

    public void onNewNotification(PopMsg popMsg) {
        notificationList.add(0, popMsg);
            adapter.notifyDataSetChanged();
        NotiListView.smoothScrollToPosition(0);
    }

    private class NotificationAdapter extends ArrayAdapter<PopMsg> {
        public NotificationAdapter(Context context,List<PopMsg> dataSet) {
            super(context, R.layout.notification_item, dataSet);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.notification_item, parent, false);
            }

            PopMsg popMsg = getItem(position);
            TextView notificationText = convertView.findViewById(R.id.notification_text);

            String message = String.format("%s from %s just favorited the recipe %s.", popMsg.username, popMsg.location, popMsg.title);
            notificationText.setText(message);

            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) notificationText.getLayoutParams();
            if (position % 2 == 0) {
                params.gravity = Gravity.START;
            } else {
                params.gravity = Gravity.END;
            }
            notificationText.setLayoutParams(params);

            convertView.setOnClickListener(v -> {
                // Handle item click and dismissal
                notificationList.remove(position);
                notifyDataSetChanged();
            });

            return convertView;
        }
    }


}