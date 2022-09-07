package com.autobrix.autobrix_marketingapp.pojos;

public class PojoEmpTargetList {

    String emp_name;
    String emp_target_count;
    int emp_target_percent;
    String emp_pack_count;

    public PojoEmpTargetList(String emp_name, String emp_target_count, int emp_target_percent,String emp_pack_count) {
        this.emp_name = emp_name;
        this.emp_target_count = emp_target_count;
        this.emp_target_percent = emp_target_percent;
        this.emp_pack_count=emp_pack_count;
    }

    public String getEmp_name() {
        return emp_name;
    }

    public void setEmp_name(String emp_name) {
        this.emp_name = emp_name;
    }


}
