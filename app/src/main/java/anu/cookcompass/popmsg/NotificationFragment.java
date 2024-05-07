package anu.cookcompass.popmsg;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import anu.cookcompass.MainActivity;
import anu.cookcompass.R;
import anu.cookcompass.theme.ThemeColor;
import anu.cookcompass.theme.ThemeConfig;

public class NotificationFragment extends Fragment {
    private View rootView;
    private ListView NotiListView;
    private NotificationAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rootView = inflater.inflate(R.layout.fragment_notification, container, false);
        //change the color when create
        ThemeConfig themeConfig = ((MainActivity) requireActivity()).getThemeConfig();
        rootView.setBackgroundColor(Color.parseColor(themeConfig.getTheme()));
        System.out.println("theme config in notification" + themeConfig.getTheme());

        NotiListView = rootView.findViewById(R.id.notification_listview);
        adapter = new NotificationAdapter(requireContext(), PopMsgManager.getInstance().popMsgs);
        NotiListView.setAdapter(adapter);

        // set initial theme
        rootView.setBackgroundColor(Color.parseColor(ThemeColor.getThemeColor()));

        PopMsgManager.getInstance().addObserver(popMsgs -> {
            adapter.setDataSet(popMsgs);
            adapter.notifyDataSetChanged();
        });
        return rootView;
    }


}