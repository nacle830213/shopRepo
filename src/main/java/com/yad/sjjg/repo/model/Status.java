package com.yad.sjjg.repo.model;

public enum Status {
    OK(0,"成功"),
    ISNULL(1,"元素不存在"),
    INSERTINDEXERROR(2,"插入位置不合法"),
    REMOVEINDEXERROR(3,"删除位置不合法")
    ;
    private Integer status;
    private String  errormsg;
    Status(Integer status, String errormsg){
        this.status=status;
        this.errormsg=errormsg;
    }
}
