package vn.homemade.todotask.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import vn.homemade.todotask.R;
import vn.homemade.todotask.model.TaskModel;
import vn.homemade.todotask.sqlite.DBHelper;

/**
 * Created by tanlnm on 2/24/2016.
 */
public class TaskDetailFrg extends Fragment implements View.OnClickListener {
    private DBHelper dbHelper;

    private EditText etName, etDesc;
    private Button btnUpdate, btnDelete;
    private Spinner spPriority;

    private String priority;
    private TaskModel task;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.task_detail, container, false);
        dbHelper = new DBHelper(getContext());
        getActivity().findViewById(R.id.fab).setVisibility(View.GONE);

        etName = (EditText) view.findViewById(R.id.task_detail_et_name);
        etDesc = (EditText) view.findViewById(R.id.task_detail_et_desc);
        btnUpdate = (Button) view.findViewById(R.id.task_detail_btn_update);
        btnDelete = (Button) view.findViewById(R.id.task_detail_btn_delete);
        spPriority = (Spinner) view.findViewById(R.id.task_detail_sp_priority);

        btnUpdate.setOnClickListener(this);
        btnDelete.setOnClickListener(this);

        List<String> categories = new ArrayList<>();
        categories.add("Low");
        categories.add("Medium");
        categories.add("High");
        categories.add("Critical");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spPriority.setAdapter(dataAdapter);
        spPriority.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                priority = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Bundle bundle = getArguments();
        task = (TaskModel) bundle.getSerializable("task");
        if (task != null) {
            etName.setText(TextUtils.isEmpty(task.getName()) ? "" : task.getName());
            etDesc.setText(TextUtils.isEmpty(task.getDesc()) ? "" : task.getDesc());
            spPriority.setSelection(task.getPriority());
        }

        return view;
    }

    @Override
    public void onClick(View v) {
        if (v == btnUpdate) {
            task.setName(etName.getText().toString());
            task.setDesc(etDesc.getText().toString());
            task.setPriority(getPriority(priority));
            dbHelper.updateTask(task);

        } else if (v == btnDelete) {
            dbHelper.deleteTask(task);
        }
        getActivity().onBackPressed();
    }

    private int getPriority(String priorityValue) {
        switch (priorityValue){
            case "Critical":
                return 3;

            case "High":
                return 2;

            case "Medium":
                return 1;

            case "Low":
                return 0;

            default:
                return 1;
        }
    }
}
