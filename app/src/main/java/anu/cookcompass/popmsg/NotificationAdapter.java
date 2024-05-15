package anu.cookcompass.popmsg;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import anu.cookcompass.R;
import anu.cookcompass.Utils;

/**
 * @author u7759982, Jiangbei Zhang,
 * @feature Data-Stream
 * This class is a self defined array adapter to show Pop message instance
 */
public class NotificationAdapter extends ArrayAdapter<PopMsg> {
    public List<PopMsg> dataSet;//store pop message instance

    /**
     * @param context application context
     * @param dataSet the list contains PopMsg
     *                initialize the adapter and set context and dataSet
     */
    public NotificationAdapter(Context context, List<PopMsg> dataSet) {
        super(context, R.layout.notification_item, dataSet);
        this.dataSet = dataSet;
    }

    /**
     * update the dataSet,clear and addAll
     */
    public void setDataSet(List<PopMsg> popMsgs) {
        dataSet.clear();
        dataSet.addAll(popMsgs);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.notification_item, parent, false);
        }

        PopMsg popMsg = getItem(position);
        //bind the text with item
        TextView notificationText = convertView.findViewById(R.id.notification_text);
        //standardize the message
        String message = String.format("[%s] %s (location: %s) %s the recipe %s.",
                Utils.timestamp2string(popMsg.timestamp),
                popMsg.username,
                popMsg.location,
                popMsg.type.value,
                popMsg.title
        );
        notificationText.setText(message);
        //if click,just remove that item
        convertView.setOnClickListener(v -> {
            // Handle item click and dismissal
            dataSet.remove(position);
            notifyDataSetChanged();
        });

        return convertView;
    }
}