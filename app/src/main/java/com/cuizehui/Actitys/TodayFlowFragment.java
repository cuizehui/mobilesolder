package com.cuizehui.Actitys;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cuizehui.mobilesoder.R;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.Chart;
import lecho.lib.hellocharts.view.LineChartView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TodayFlowFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TodayFlowFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TodayFlowFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public final static String[] months = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug",
            "Sep", "Oct", "Nov", "Dec",};

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public TodayFlowFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TodayFlowFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TodayFlowFragment newInstance(String param1, String param2) {
        TodayFlowFragment fragment = new TodayFlowFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       View rootView= inflater.inflate(R.layout.fragment_today_flow, container, false);
        LineChartView line = (LineChartView) rootView.findViewById(R.id.linechar);

        setLineData(line);
        return rootView;
    }

   public  void setLineData(LineChartView chart) {


       ArrayList<PointValue> values = new ArrayList<PointValue>();
       //这里输入的数据就是坐标值！
       values.add(new PointValue(0, 21));
       values.add(new PointValue(1, 56));
       values.add(new PointValue(8, 35));
       values.add(new PointValue(11, 46));
       //坐标轴属性值
       List<AxisValue> axisValues = new ArrayList<AxisValue>();
       for (int i = 0; i < months.length; ++i) {
           axisValues.add(new AxisValue(i).setLabel(months[i]));
       }
//In most cased you can call data model methods in builder-pattern-like manner.
       //点连成线。设置线的属性
       Line line = new Line(values).setColor(Color.argb(255,255,66,93)).setCubic(true);
       //是否填满
     //  line.setFilled(true);
       List<Line> lines = new ArrayList<Line>();
       lines.add(line);
       //设置图坐标系
       //做一个数据添加 刚刚的数据值
       LineChartData data = new LineChartData();
       //设置横坐标轴的值 一对一
       Axis axisX = new Axis(axisValues);
       axisX.setHasLines(true);
       Axis axisY = new Axis().setHasLines(true);
       axisX.setName("时间");
       axisY.setName("流量值");
       //设置横纵坐标
       data.setAxisXBottom(axisX);
       data.setAxisYLeft(axisY);
       data.setBaseValue(Float.NEGATIVE_INFINITY);
       //设置数据连线
       data.setLines(lines);
       //给整个图添加数据
       chart.setLineChartData(data);
       //设置数据显示的空间类型
       chart.setZoomType(ZoomType.HORIZONTAL);
       //设置是否可以拉伸
       chart.setViewportCalculationEnabled(false);
       //设置横纵坐标的最大显示值
       final Viewport v = new Viewport(chart.getMaximumViewport());
       v.bottom = 0;
       v.  top = 100;
       v.right=13;
       v.left = 0;
       chart.setMaximumViewport(v);
       chart.setCurrentViewport(v);
       chart.startDataAnimation(2000);
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
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
