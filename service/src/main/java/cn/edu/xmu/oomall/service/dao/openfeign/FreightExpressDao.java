package cn.edu.xmu.oomall.service.dao.openfeign;

import cn.edu.xmu.javaee.core.exception.BusinessException;
import cn.edu.xmu.javaee.core.model.InternalReturnObject;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.oomall.service.dao.bo.ServiceOrder;
import cn.edu.xmu.oomall.service.mapper.openfeign.FreightExpressMapper;
import cn.edu.xmu.oomall.service.mapper.openfeign.po.FreightExpress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class FreightExpressDao {
    private Logger logger = LoggerFactory.getLogger(FreightExpressDao.class);

    private FreightExpressMapper freightExpressMapper;
    private FreightExpress freightExpress;
    @Autowired
    public FreightExpressDao(FreightExpressMapper freightExpressMapper) {
        this.freightExpressMapper = freightExpressMapper;
    }

    public FreightExpress findById(Long id){
        InternalReturnObject<FreightExpress> ret = this.freightExpressMapper.getFreightExpressById(id);
        if (ret.getErrno().equals(ReturnNo.OK)){
            return ret.getData();
        }else{
            throw new BusinessException(ReturnNo.getReturnNoByCode(ret.getErrno()), ret.getErrmsg());
        }
    }

    /**
     * 核对运单信息
     */
    public void checkProduct(ServiceOrder serviceOrder, FreightExpress freightExpress){
        Long serviceProviderId = serviceOrder.getServiceProviderId();
        InternalReturnObject<FreightExpress> express = freightExpressMapper.getFreightExpressById(serviceProviderId);
        FreightExpress expressInfo = express.getData();
        if(expressInfo != freightExpress){
            throw new BusinessException(ReturnNo.INCONSISTENT_DATA, String.format(ReturnNo.INCONSISTENT_DATA.getMessage(),freightExpress.getId()));
        }
    }

    /**
     * 创建运单
     */
    public void createFreightExpress(ServiceOrder serviceOrder){
        Long serviceProviderId = serviceOrder.getServiceProviderId();
        InternalReturnObject<FreightExpress> freightExpress = this.freightExpressMapper.createFreightExpressById(serviceProviderId, serviceOrder.getSerialNo());
    }
}
