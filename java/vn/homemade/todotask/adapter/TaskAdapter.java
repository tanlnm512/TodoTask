package vn.homemade.todotask.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import vn.homemade.todotask.R;
import vn.homemade.todotask.model.TaskModel;

/**
 * Created by tanlnm on 2/23/2016.
 */
public class TaskAdapter extends BaseAdapter {
    private Context context;
    private List<TaskModel> taskList;
    private Holder holder;

    public TaskAdapter(Context context, List<TaskModel> taskList) {
        this.context = context;
        this.taskList = taskList;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if (view == null || view.getTag() == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_task, viewGroup, false);

            holder = new Holder();

            holder.tvName = (TextView) view.findViewById(R.id.item_task_tv_name);
            holder.tvDesc = (TextView) view.findViewById(R.id.item_task_tv_desc);
            holder.tvPriority = (TextView) view.findViewById(R.id.item_task_tv_priority);

            view.setTag(holder);
        } else {
            holder = (Holder) view.getTag();
        }

        TaskModel task = taskList.get(i);
        if (task != null) {
            holder.tvName.setText(task.getName());
            holder.tvDesc.setText(task.getDesc());
            holder.tvPriority.setText(loadPriorityValue(task.getPriority()));
        }

        return view;
    }

    @Override
    public int getCount() {
        return taskList != null && taskList.size() > 0 ? taskList.size() : 0;
    }

    @Override
    public Object getItem(int i) {
        return taskList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public void updateAdapter(TaskModel task) {
        this.taskList.add(task);
        notifyDataSetChanged();
    }

    private String loadPriorityValue(int priority) {
        switch (priority) {
            case 0:
                return "Low";

            case 1:
                return "Medium";

            case 2:
                return "High";

            case 3:
                return "Critical";

            default:
                return "Medium";
        }
    }

    private static class Holder {
        TextView tvName, tvDesc, tvPriority;
    }
}
