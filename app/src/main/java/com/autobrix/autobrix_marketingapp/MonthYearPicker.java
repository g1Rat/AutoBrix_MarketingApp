package com.autobrix.autobrix_marketingapp;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;

import androidx.fragment.app.DialogFragment;

import com.autobrix.autobrix_marketingapp.common_classes.SPHelper;

import java.util.Calendar;
import java.util.Date;

public class MonthYearPicker extends DialogFragment {


    private DatePickerDialog.OnDateSetListener listener1;
    public  int month,year,monthvalue,yearvalue;
    public  String displaymonth=null;
    public NumberPicker monthPicker;
    public  NumberPicker yearPicker;
    public Calendar cal;
    public Date date;
    public void setListener(DatePickerDialog.OnDateSetListener listener1)
    {
        this.listener1 = listener1;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        cal = Calendar.getInstance();
        date = new Date();
        int currentYear = cal.get(Calendar.YEAR);
        int currentMonth = cal.get(Calendar.MONTH);
        int currentDay = cal.get(Calendar.DAY_OF_MONTH);
        String[] display={"JAN","FEB","MAR","APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV","DEC"};
        View dialog = inflater.inflate(R.layout.month_year_picker, null);
        NumberPicker monthPicker = (NumberPicker) dialog.findViewById(R.id.picker_month);
        NumberPicker yearPicker = (NumberPicker) dialog.findViewById(R.id.picker_year);
        month=(cal.get(Calendar.DAY_OF_MONTH));
        year = cal.get(Calendar.YEAR);
        cal.set(currentYear-1,month,currentDay);
        monthPicker.setMinValue(1);
        monthPicker.setMaxValue(12);
        monthPicker.setValue(currentMonth+1);
        monthPicker.setDisplayedValues(display);
        yearPicker.setMinValue(currentYear-1);
        yearPicker.setMaxValue(currentYear);
        yearPicker.setValue(year);
        builder.setView(dialog)
                .setPositiveButton("ok", new DialogInterface.OnClickListener()
                {

                    @SuppressLint("SetTextI18n")
                    public void onClick(DialogInterface dialog, int id)
                    {
                        monthvalue=monthPicker.getValue();
                        yearvalue=yearPicker.getValue();
                        if(monthvalue==1)
                        {
                            displaymonth="JAN";
                        }
                        else  if(monthvalue==2)
                            displaymonth="FEB";
                        else  if(monthvalue==3)
                            displaymonth="MAR";
                        else  if(monthvalue==4)
                            displaymonth="APR";
                        else  if(monthvalue==5)
                            displaymonth="MAY";
                        else  if(monthvalue==6)
                            displaymonth="JUN";
                        else  if(monthvalue==7)
                            displaymonth="JUL";
                        else  if(monthvalue==8)
                            displaymonth="AUG";
                        else  if(monthvalue==9)
                            displaymonth="SEP";
                        else  if(monthvalue==10)
                            displaymonth="OCT";
                        else  if(monthvalue==11)
                            displaymonth="NOV";
                        else  if(monthvalue==12)
                            displaymonth="DEC";
                        if(SPHelper.picker_month.equals("dr")){
                            SPHelper.monthvalue_reminder=String.valueOf(monthvalue);
                            SPHelper.yearvalue_reminder=String.valueOf(yearvalue);
                            SPHelper.displaymonthvalue_reminder=displaymonth;
                            ((DealerRequests) requireActivity()).month_picker.setText(displaymonth+"-"+yearvalue);
                            if(((DealerRequests) requireActivity()).isInspect_clicked.equals("y")){
                                ((DealerRequests) requireActivity()).get_Inspection_ReqList();
                            }else{
                                ((DealerRequests) requireActivity()).get_insp_vehList();
                            }
                        }else if(SPHelper.picker_month.equals("db")){
                            SPHelper.dash_month_value=String.valueOf(monthvalue);
                            SPHelper.dash_year_value=String.valueOf(yearvalue);
                            SPHelper.displaymonthvalue_dashboard1=String.valueOf(displaymonth);
                            ((DashBoardPage) requireActivity()).pick_month.setText(displaymonth+"-"+yearvalue);
                            ((DashBoardPage) requireActivity()).servicecall_getdashboardcount();
                        }else if(SPHelper.picker_month.equals("dbm"))
                        {
                            SPHelper.dash_month_value=String.valueOf(monthvalue);
                            SPHelper.dash_year_value=String.valueOf(yearvalue);
                            SPHelper.displaymonthvalue_dashboard1=String.valueOf(displaymonth);
                            ((DashBoardPage) requireActivity()).pick_month.setText(displaymonth+"-"+yearvalue);
                            ((DashBoardPage) requireActivity()).servicecall_mngr_dashboardcount();
                        }
                        else if(SPHelper.picker_month.equals("sales"))
                        {
                            SPHelper.sales_mv=String.valueOf(monthvalue);
                            SPHelper.sales_yv=String.valueOf(yearvalue);
                            SPHelper.displaymv_sales=String.valueOf(displaymonth);
                            ((MngrSalesPage) requireActivity()).month_picker.setText(displaymonth+"-"+yearvalue);
                            ((MngrSalesPage) requireActivity()).getMngrEmpList();
                        }
                        else if(SPHelper.picker_month.equals("target"))
                        {
                            SPHelper.sales_mv=String.valueOf(monthvalue);
                            SPHelper.sales_yv=String.valueOf(yearvalue);
                            SPHelper.displaymv_sales=String.valueOf(displaymonth);
                            ((MngrTargetList) requireActivity()).month_picker.setText(displaymonth+"-"+yearvalue);
                            ((MngrTargetList) requireActivity()).getMngrEmpList();
                        }
                        else if(SPHelper.picker_month.equals("mngrinsp"))
                        {
                            SPHelper.monthvalue_reminder=String.valueOf(monthvalue);
                            SPHelper.yearvalue_reminder=String.valueOf(yearvalue);
                            SPHelper.displaymonthvalue_reminder=displaymonth;
                            ((MngrInspList) requireActivity()).month_picker.setText(displaymonth+"-"+yearvalue);
                            if(((MngrInspList) requireActivity()).isInspect_clicked.equals("y")){
                                ((MngrInspList) requireActivity()).get_Inspection_ReqList();
                            }else{
                                ((MngrInspList) requireActivity()).get_insp_vehList();
                            }
                        }
                        else if(SPHelper.picker_month.equals("mng_mmt")){
                            SPHelper.monthvalue=String.valueOf(monthvalue);
                            SPHelper.yearvalue=String.valueOf(yearvalue);
                            SPHelper.displaymonthvalue_dashboard1=String.valueOf(displaymonth);
                            ((MngrMtrlList) getActivity()).month_picker.setText(displaymonth+"-"+yearvalue);
                            ((MngrMtrlList) requireActivity()).get_All_market_mtrl_list();
                        }
                        else if(SPHelper.picker_month.equals("mmt")){
                            SPHelper.monthvalue=String.valueOf(monthvalue);
                            SPHelper.yearvalue=String.valueOf(yearvalue);
                            SPHelper.displaymonthvalue_dashboard1=String.valueOf(displaymonth);
                            ((MarketMtrlList) getActivity()).month_picker.setText(displaymonth+"-"+yearvalue);
                            ((MarketMtrlList) requireActivity()).get_All_market_mtrl_list();
                        }
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        MonthYearPicker.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }

}
