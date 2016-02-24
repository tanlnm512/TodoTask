package vn.homemade.todotask.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import vn.homemade.todotask.R;
import vn.homemade.todotask.model.TaskModel;

/**
 * Created by tanlnm on 2/23/2016.
 */
public class ActionDlg extends DialogFragment implements View.OnClickListener {
    private EditText etName, etDesc;
    private Button btnAdd;
    private Spinner spPriority;

    private String priority;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_task_dlg, container, false);

        etName = (EditText) view.findViewById(R.id.dlg_et_name);
        etDesc = (EditText) view.findViewById(R.id.dlg_et_desc);
        btnAdd = (Button) view.findViewById(R.id.dlg_btn_add);
        spPriority = (Spinner) view.findViewById(R.id.dlg_sp_priority);

        btnAdd.setOnClickListener(this);

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

        return view;
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

    @Override
    public void onClick(View v) {
        Intent dataIntent = new Intent();
        Bundle dataBundle = new Bundle();

        TaskModel task = new TaskModel(etName.getText().toString(),
                etDesc.getText().toString(),
                System.currentTimeMillis(),
                getPriority(priority));
        dataBundle.putSerializable("task", task);
        dataIntent.putExtras(dataBundle);

        IActionDlgListener activity = (IActionDlgListener) getFragmentManager().findFragmentByTag("TaskListFrg");
        activity.onFinish(dataIntent);

        dismiss();
    }
}
