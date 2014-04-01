package org.djodjo.jjson.atools.util;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import org.djodjo.jjson.atools.R;
import org.djodjo.json.JsonException;
import org.djodjo.json.JsonObject;
import org.djodjo.json.schema.Schema;
import org.djodjo.json.schema.SchemaV4;

import java.io.IOException;
import java.util.ArrayList;


public class OneOfFragment extends Fragment {

    private static final String ARG_SCHEMAS = "schemas";


    private ArrayList<Schema> schemas =  new ArrayList<Schema>();

    public static OneOfFragment newInstance(ArrayList<String> schemas) {
        OneOfFragment fragment = new OneOfFragment();
        Bundle args = new Bundle();
        args.putStringArrayList(ARG_SCHEMAS, schemas);
        fragment.setArguments(args);
        return fragment;
    }
    public OneOfFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            ArrayList<String> stringSchemas = getArguments().getStringArrayList(ARG_SCHEMAS);
            for(String schema:stringSchemas) {
                try {
                    schemas.add((Schema)new SchemaV4().wrap(JsonObject.readFrom(schema)));
                } catch (JsonException e) {e.printStackTrace();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_one_of_radio, container, false);

        RadioGroup oneOfRadioGroup = (RadioGroup) v.findViewById(R.id.oneOfRadioGroup);

        for(Schema schema:schemas) {
            RadioButton button = new RadioButton(getActivity());
            button.setText(schema.getTitle());
            oneOfRadioGroup.addView(button);
        }

        ViewGroup vgcontainer = (ViewGroup)v.findViewById(R.id.oneOfContainer);

        return v;
    }


}
