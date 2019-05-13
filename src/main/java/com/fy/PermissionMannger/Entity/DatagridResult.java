package com.fy.PermissionMannger.Entity;

import java.io.Serializable;
import java.util.List;

/**
 * 〈前端结果集合〉
 *
 * @author fangyan
 * @create 2019/5/4
 * @since 1.0.0
 */
public class DatagridResult implements Serializable {
    private long total;// 记录总数
    private List rows; // 记录集合

    public DatagridResult() {
    }

    public DatagridResult(long total, List rows) {
        this.total = total;
        this.rows = rows;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List getRows() {
        return rows;
    }

    public void setRows(List rows) {
        this.rows = rows;
    }
}
