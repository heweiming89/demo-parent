package com.example.demo.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

import com.example.demo.util.ToStringUtils;

/**
 * Created by heweiming on 2017/5/17.
 */
@ApiModel(value = "Page", description = "Page模型")
public class Page<T> {

    /**
     * 页码
     */
    @ApiModelProperty(value = "页码", dataType = "int", example = "1", required = true)
    private int currentPage;

    /**
     * 总页数
     */
    @ApiModelProperty(value = "总页数", dataType = "int", example = "100", required = true)
    private int pages;

    /**
     * 每页显示记录大小
     */
    @ApiModelProperty(value = "每页显示记录大小", dataType = "int", example = "20", required = true)
    private int pageSize;

    /**
     * 总记录数
     */
    @ApiModelProperty(value = "总记录数", dataType = "long", example = "2000", required = true)
    private long total;

    /**
     * 当前页记录数据
     */
    @ApiModelProperty(value = "当前页记录数据", dataType = "List", required = true)
    private List<T> recoreds;

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPages() {
        try {
            pages = Long.valueOf(total / pageSize + ((total % pageSize) == 0 ? 0 : 1)).intValue();
        } catch (Exception e) {
            throw new IllegalArgumentException("total 或 pageSize 参数不正确");
        }
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getRecoreds() {
        return recoreds;
    }

    public void setRecoreds(List<T> recoreds) {
        this.recoreds = recoreds;
    }

    @Override
    public String toString() {
        return ToStringUtils.toStringForJSONStyle(this);
    }

}
