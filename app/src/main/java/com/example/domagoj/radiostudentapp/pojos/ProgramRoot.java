
package com.example.domagoj.radiostudentapp.pojos;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProgramRoot {

    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("rows")
    @Expose
    private List<Row> rows = null;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<Row> getRows() {
        return rows;
    }

    public void setRows(List<Row> rows) {
        this.rows = rows;
    }

}
