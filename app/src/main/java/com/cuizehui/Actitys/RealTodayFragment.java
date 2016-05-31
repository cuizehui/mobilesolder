package com.cuizehui.Actitys;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cuizehui.mobilesoder.R;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.ColumnChartView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RealTodayFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RealTodayFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RealTodayFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private ColumnChartView column;
    private ColumnChartData data;
    private String[] apkname=new  String[8];
    private float[] apksize=new float[8];
    private Bundle bundle;
    public RealTodayFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RealTodayFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RealTodayFragment newInstance(String param1, String param2) {
        RealTodayFragment fragment = new RealTodayFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        bundle=getArguments();

        for(int i=0;i<8;i++){
            apkname[i] = bundle.getString("第"+i+"组数据名");
            apksize[i] = (bundle.getFloat("第"+i+"组数据值")/1048576);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view =inflater.inflate(R.layout.fragment_real_today, container, false);
            column= (ColumnChartView) view.findViewById(R.id.realtoday_colunChart);
            initcolumndata();
        return view;
    }

    private void initcolumndata() {
            int numSubcolumns = 1;
            int numColumns = 8;
            // Column can have many subcolumns, here by default I use 1 subcolumn in each of 8 columns.
            List<Column> columns = new ArrayList<Column>();
            List<SubcolumnValue> values;

                for (int i = 0; i < numColumns; ++i) {
                values = new ArrayList<SubcolumnValue>();
                for (int j = 0; j < numSubcolumns; ++j) {
                    values.add(new SubcolumnValue((float) apksize[i], ChartUtils.pickColor()));
                }

                Column column = new Column(values);
                column.setHasLabels(true);
                column.setHasLabelsOnlyForSelected(true);
                columns.add(column);
            }

                data = new ColumnChartData(columns);
                List<AxisValue> axisValues = new ArrayList<AxisValue>();
                //这里可以将标签和数据一起放进去
                for (int i = 0; i < apkname.length; ++i) {
                 axisValues.add(new AxisValue(i).setLabel(apkname[i]));
                }
                 Axis axisX = new Axis(axisValues);
                 Axis axisY = new Axis().setHasLines(true);
                 axisX.setName("");
                 axisY.setName("下载流量情况:(MB)");
                data.setAxisXBottom(axisX);
                data.setAxisYLeft(axisY);
                column.setColumnChartData(data);

        }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
