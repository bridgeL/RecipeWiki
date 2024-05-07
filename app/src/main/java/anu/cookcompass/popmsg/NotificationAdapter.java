package anu.cookcompass.popmsg;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import anu.cookcompass.R;

public class NotificationAdapter extends ArrayAdapter<PopMsg> {
    public List<PopMsg> dataSet;

    public NotificationAdapter(Context context, List<PopMsg> dataSet) {
        super(context, R.layout.notification_item, dataSet);
        this.dataSet = dataSet;
    }

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
        TextView notificationText = convertView.findViewById(R.id.notification_text);

        String message = String.format("%s (location: %s) just %s the recipe %s.", popMsg.username, popMsg.location, popMsg.type.value, popMsg.title);
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
            dataSet.remove(position);
            notifyDataSetChanged();
        });

        return convertView;
    }
}