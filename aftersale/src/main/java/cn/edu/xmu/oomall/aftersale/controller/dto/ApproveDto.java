package cn.edu.xmu.oomall.aftersale.controller.dto;

import cn.edu.xmu.javaee.core.aop.CopyFrom;
import cn.edu.xmu.oomall.aftersale.controller.vo.ApproveVo;

@CopyFrom({ApproveVo.class})
public class ApproveDto {
    private boolean result;
    private String commit;
    public void setresult(boolean result){
        this.result=result;
    }
    public boolean getresult(){
        return this.result;
    }

    public void setcommit(String commit){
        this.commit=commit;
    }
    public String getcommit(){
        return this.commit;
    }
}
