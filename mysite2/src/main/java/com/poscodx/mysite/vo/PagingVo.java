package com.poscodx.mysite.vo;

import com.poscodx.mysite.dao.BoardDao;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PagingVo {
    private final static int pageSize = 5;
    private int groupStartNum = 0;
    private int groupEndNum = 0;
    private int endPageNum = 0;

    public static int getPageSize() {
        return pageSize;
    }

    public void setGroup(int currentPage) {
        int groupNum = 0;

        groupNum = (int) Math.floor((currentPage - 1) / pageSize);
        groupStartNum = (pageSize * groupNum) + 1;
        groupEndNum = groupStartNum + (pageSize - 1);
    }

    public void setLastPageNum(String keyword) {

        int count = new BoardDao().findSearchCount(keyword);

        if (count % pageSize == 0) {
            endPageNum = (int) Math.floor(count / pageSize);
        } else {
            endPageNum = (int) Math.floor(count / pageSize) + 1;
        }
    }
}