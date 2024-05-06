package anu.cookcompass;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import anu.cookcompass.model.PopMsg;
import anu.cookcompass.model.ThemeColor;
import anu.cookcompass.model.ThemeConfig;
import anu.cookcompass.popmsg.NotificationAdapter;

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

        adapter = new NotificationAdapter(requireContext(), notificationList);
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
}