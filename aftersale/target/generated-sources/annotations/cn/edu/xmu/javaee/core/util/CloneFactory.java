package cn.edu.xmu.javaee.core.util;

import cn.edu.xmu.oomall.aftersale.controller.dto.AftersaleDto;
import cn.edu.xmu.oomall.aftersale.controller.dto.ApproveDto;
import cn.edu.xmu.oomall.aftersale.controller.dto.ArbitrationDto;
import cn.edu.xmu.oomall.aftersale.controller.dto.OrderItemDto;
import cn.edu.xmu.oomall.aftersale.controller.vo.ApproveVo;
import cn.edu.xmu.oomall.aftersale.controller.vo.ArbitrationVo;
import cn.edu.xmu.oomall.aftersale.controller.vo.OrderItemVo;
import cn.edu.xmu.oomall.aftersale.dao.bo.Aftersale;
import cn.edu.xmu.oomall.aftersale.dao.bo.Arbitration;
import cn.edu.xmu.oomall.aftersale.mapper.po.AftersalePo;
import cn.edu.xmu.oomall.aftersale.mapper.po.ArbitrationPo;
import cn.edu.xmu.oomall.aftersale.service.AftersaleService;

public final class CloneFactory {
  /**
   * Copy all fields from source to target
   * @param target the target object
   * @param source the source object
   * @return the copied target object
   */
  public static Aftersale copy(Aftersale target, AftersalePo source) {
    target.setId(source.getId());
    target.setOrderItemId(source.getOrderItemId());
    target.setCustomerId(source.getCustomerId());
    target.setShopId(source.getShopId());
    target.setAftersaleSn(source.getAftersaleSn());
    target.setType(source.getType());
    target.setReason(source.getReason());
    target.setConclusion(source.getConclusion());
    target.setQuantity(source.getQuantity());
    target.setRegionId(source.getRegionId());
    target.setAddress(source.getAddress());
    target.setConsignee(source.getConsignee());
    target.setMobile(source.getMobile());
    target.setStatus(source.getStatus());
    target.setServiceId(source.getServiceId());
    target.setSerialNo(source.getSerialNo());
    target.setName(source.getName());
    target.setCreatorId(source.getCreatorId());
    target.setCreatorName(source.getCreatorName());
    target.setModifierId(source.getModifierId());
    target.setModifierName(source.getModifierName());
    target.setInArbitrated(source.getInArbitrated());
    target.setGmtCreate(source.getGmtCreate());
    target.setGmtModified(source.getGmtModified());
    return target;
  }

  /**
   * Copy all fields from source to target
   * @param target the target object
   * @param source the source object
   * @return the copied target object
   */
  public static Aftersale copy(Aftersale target, OrderItemDto source) {
    target.setOrderItemId(source.getOrderItemId());
    target.setCustomerId(source.getCustomerId());
    target.setShopId(source.getShopId());
    target.setType(source.getType());
    target.setReason(source.getReason());
    target.setRegionId(source.getRegionId());
    target.setAddress(source.getAddress());
    target.setMobile(source.getMobile());
    return target;
  }

  /**
   * Copy all fields from source to target
   * @param target the target object
   * @param source the source object
   * @return the copied target object
   */
  public static ArbitrationPo copy(ArbitrationPo target, Arbitration source) {
    target.setGmtAccept(source.getGmtAccept());
    target.setGmtArbitration(source.getGmtArbitration());
    target.setId(source.getId());
    target.setShopReply(source.getShopReply());
    target.setAftersale_id(source.getAftersale_id());
    target.setReason(source.getReason());
    target.setResult(source.getResult());
    target.setStatus(source.getStatus());
    target.setCreatorId(source.getCreatorId());
    target.setCreatorName(source.getCreatorName());
    target.setModifierId(source.getModifierId());
    target.setModifierName(source.getModifierName());
    target.setArbitratorId(source.getArbitratorId());
    target.setArbitratorName(source.getArbitratorName());
    target.setGmtCreate(source.getGmtCreate());
    target.setGmtModified(source.getGmtModified());
    return target;
  }

  /**
   * Copy all fields from source to target
   * @param target the target object
   * @param source the source object
   * @return the copied target object
   */
  public static AftersaleService copy(AftersaleService target, OrderItemDto source) {
    return target;
  }

  /**
   * Copy all fields from source to target
   * @param target the target object
   * @param source the source object
   * @return the copied target object
   */
  public static ArbitrationDto copy(ArbitrationDto target, Arbitration source) {
    target.setShopReply(source.getShopReply());
    target.setGmtAccept(source.getGmtAccept());
    target.setGmtArbitration(source.getGmtArbitration());
    target.setId(source.getId());
    target.setAftersale_id(source.getAftersale_id());
    target.setReason(source.getReason());
    target.setResult(source.getResult());
    target.setStatus(source.getStatus());
    target.setGmtCreate(source.getGmtCreate());
    target.setGmtModified(source.getGmtModified());
    target.setCreatorId(source.getCreatorId());
    target.setCreatorName(source.getCreatorName());
    target.setModifierId(source.getModifierId());
    target.setModifierName(source.getModifierName());
    target.setArbitratorId(source.getArbitratorId());
    target.setArbitratorName(source.getArbitratorName());
    return target;
  }

  /**
   * Copy all fields from source to target
   * @param target the target object
   * @param source the source object
   * @return the copied target object
   */
  public static AftersalePo copy(AftersalePo target, Aftersale source) {
    target.setId(source.getId());
    target.setOrderItemId(source.getOrderItemId());
    target.setCustomerId(source.getCustomerId());
    target.setShopId(source.getShopId());
    target.setAftersaleSn(source.getAftersaleSn());
    target.setType(source.getType());
    target.setReason(source.getReason());
    target.setConclusion(source.getConclusion());
    target.setQuantity(source.getQuantity());
    target.setRegionId(source.getRegionId());
    target.setAddress(source.getAddress());
    target.setConsignee(source.getConsignee());
    target.setMobile(source.getMobile());
    target.setStatus(source.getStatus());
    target.setServiceId(source.getServiceId());
    target.setSerialNo(source.getSerialNo());
    target.setName(source.getName());
    target.setCreatorId(source.getCreatorId());
    target.setCreatorName(source.getCreatorName());
    target.setModifierId(source.getModifierId());
    target.setModifierName(source.getModifierName());
    target.setGmtCreate(source.getGmtCreate());
    target.setGmtModified(source.getGmtModified());
    target.setInArbitrated(source.getInArbitrated());
    return target;
  }

  /**
   * Copy all fields from source to target
   * @param target the target object
   * @param source the source object
   * @return the copied target object
   */
  public static OrderItemDto copy(OrderItemDto target, OrderItemVo source) {
    target.setCustomerId(source.getCustomerId());
    target.setShopId(source.getShopId());
    target.setAddress(source.getAddress());
    target.setType(source.getType());
    target.setReason(source.getReason());
    target.setRegionId(source.getRegionId());
    target.setMobile(source.getMobile());
    return target;
  }

  /**
   * Copy all fields from source to target
   * @param target the target object
   * @param source the source object
   * @return the copied target object
   */
  public static OrderItemDto copy(OrderItemDto target, Aftersale source) {
    target.setCustomerId(source.getCustomerId());
    target.setShopId(source.getShopId());
    target.setAddress(source.getAddress());
    target.setOrderItemId(source.getOrderItemId());
    target.setType(source.getType());
    target.setReason(source.getReason());
    target.setRegionId(source.getRegionId());
    target.setMobile(source.getMobile());
    return target;
  }

  /**
   * Copy all fields from source to target
   * @param target the target object
   * @param source the source object
   * @return the copied target object
   */
  public static Arbitration copy(Arbitration target, ArbitrationPo source) {
    target.setId(source.getId());
    target.setGmtAccept(source.getGmtAccept());
    target.setGmtArbitration(source.getGmtArbitration());
    target.setCreatorId(source.getCreatorId());
    target.setShopReply(source.getShopReply());
    target.setCreatorName(source.getCreatorName());
    target.setModifierId(source.getModifierId());
    target.setModifierName(source.getModifierName());
    target.setArbitratorId(source.getArbitratorId());
    target.setArbitratorName(source.getArbitratorName());
    target.setAftersale_id(source.getAftersale_id());
    target.setReason(source.getReason());
    target.setResult(source.getResult());
    target.setStatus(source.getStatus());
    target.setGmtCreate(source.getGmtCreate());
    target.setGmtModified(source.getGmtModified());
    return target;
  }

  /**
   * Copy all fields from source to target
   * @param target the target object
   * @param source the source object
   * @return the copied target object
   */
  public static Arbitration copy(Arbitration target, ArbitrationVo source) {
    target.setReason(source.getReason());
    return target;
  }

  /**
   * Copy all fields from source to target
   * @param target the target object
   * @param source the source object
   * @return the copied target object
   */
  public static AftersaleDto copy(AftersaleDto target, Aftersale source) {
    target.setId(source.getId());
    target.setOrderItemId(source.getOrderItemId());
    target.setCustomerId(source.getCustomerId());
    target.setShopId(source.getShopId());
    target.setAftersaleSn(source.getAftersaleSn());
    target.setType(source.getType());
    target.setReason(source.getReason());
    target.setConclusion(source.getConclusion());
    target.setQuantity(source.getQuantity());
    target.setRegionId(source.getRegionId());
    target.setAddress(source.getAddress());
    target.setConsignee(source.getConsignee());
    target.setMobile(source.getMobile());
    target.setStatus(source.getStatus());
    target.setServiceId(source.getServiceId());
    target.setSerialNo(source.getSerialNo());
    target.setName(source.getName());
    target.setCreatorId(source.getCreatorId());
    target.setCreatorName(source.getCreatorName());
    target.setModifierId(source.getModifierId());
    target.setModifierName(source.getModifierName());
    target.setGmtCreate(source.getGmtCreate());
    target.setGmtModified(source.getGmtModified());
    target.setInArbitrated(source.getInArbitrated());
    return target;
  }

  /**
   * Copy all fields from source to target
   * @param target the target object
   * @param source the source object
   * @return the copied target object
   */
  public static ApproveDto copy(ApproveDto target, ApproveVo source) {
    target.setresult(source.getresult());
    target.setcommit(source.getcommit());
    return target;
  }
}
