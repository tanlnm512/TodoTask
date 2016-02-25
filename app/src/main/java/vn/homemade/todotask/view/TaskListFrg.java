package vn.homemade.todotask.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import vn.homemade.todotask.R;
import vn.homemade.todotask.adapter.TaskAdapter;
import vn.homemade.todotask.model.TaskModel;
import vn.homemade.todotask.sqlite.DBHelper;

/**
 * Created by tanlnm on 2/23/2016.
 */
public class TaskListFrg extends Fragment implements IActionDlgListener, AdapterView.OnItemClickListener {
    private ListView lvTaskList;
    private List<TaskModel> taskList;
    private TaskAdapter taskAdapter;
    private DBHelper dbHelper;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.task_list, container, false);
        lvTaskList = (ListView) view.findViewById(R.id.lv_task);
        lvTaskList.setOnItemClickListener(this);

        dbHelper = new DBHelper(getContext());

        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        fab.setVisibility(View.VISIBLE);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActionDlg addDlg = new ActionDlg();
                addDlg.show(getFragmentManager(), "AddDlg");
            }
        });

        taskList = dbHelper.getAllTasks();
        taskAdapter = new TaskAdapter(this.getContext(), taskList);
        lvTaskList.setAdapter(taskAdapter);

        return view;
    }

    @Override
    public void onFinish(Intent data) {
        Snackbar.make(getView(), "Task has been added!", Snackbar.LENGTH_SHORT).show();

        TaskModel task = (TaskModel) data.getExtras().getSerializable("task");

        dbHelper.addTask(task);
        taskAdapter.updateAdapter(task);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("task", taskList.get(position));

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = new TaskDetailFrg();
        fragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.content_frg, fragment, "TaskDetailFrg")
                .addToBackStack("TaskDetailFrg")
                .commit();
    }
}
