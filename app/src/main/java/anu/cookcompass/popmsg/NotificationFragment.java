package anu.cookcompass.popmsg;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import anu.cookcompass.R;
import anu.cookcompass.theme.ThemeColor;

/**
 * @author u7759982,Jiangbei Zhang
 * @feature Data-Stream
 * This method controls the fragment of notification fragment  and
 * combine the data with view.
 */
public class NotificationFragment extends Fragment {
    private View rootView;
    private ListView NotiListView;
    private NotificationAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rootView = inflater.inflate(R.layout.fragment_notification, container, false);
        NotiListView = rootView.findViewById(R.id.notification_listview);
        adapter = new NotificationAdapter(requireContext(), PopMsgManager.getInstance().popMsgs);
        NotiListView.setAdapter(adapter);

        // set initial theme
        rootView.setBackgroundColor(Color.parseColor(ThemeColor.getThemeColor()));
        // add observer to the popMsg
        PopMsgManager.getInstance().addObserver(popMsgs -> {
            adapter.setDataSet(popMsgs);
            adapter.notifyDataSetChanged();
        });
        return rootView;
    }


}