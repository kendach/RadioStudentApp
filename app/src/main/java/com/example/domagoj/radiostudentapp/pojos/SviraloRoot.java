
package com.example.domagoj.radiostudentapp.pojos;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SviraloRoot {

    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("sql")
    @Expose
    private String sql;
    @SerializedName("rows")
    @Expose
    private List<SviraloRow> rows = null;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public List<SviraloRow> getRows() {
        return rows;
    }

    public void setRows(List<SviraloRow> rows) {
        this.rows = rows;
    }

}
