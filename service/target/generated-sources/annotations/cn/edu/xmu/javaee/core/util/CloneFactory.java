package cn.edu.xmu.javaee.core.util;

import cn.edu.xmu.oomall.service.controller.dto.ServiceOrderDto;
import cn.edu.xmu.oomall.service.controller.dto.ServiceOrdersDto;
import cn.edu.xmu.oomall.service.controller.dto.ServiceProviderDto;
import cn.edu.xmu.oomall.service.controller.vo.ServiceOrderVo;
import cn.edu.xmu.oomall.service.controller.vo.ServiceProviderVo;
import cn.edu.xmu.oomall.service.dao.bo.ProductService;
import cn.edu.xmu.oomall.service.dao.bo.ServiceOrder;
import cn.edu.xmu.oomall.service.dao.bo.ServiceProvider;
import cn.edu.xmu.oomall.service.dao.bo.Services;
import cn.edu.xmu.oomall.service.mapper.po.ProductServicePo;
import cn.edu.xmu.oomall.service.mapper.po.ServiceOrderPo;
import cn.edu.xmu.oomall.service.mapper.po.ServicePo;
import cn.edu.xmu.oomall.service.mapper.po.ServiceProviderPo;

public final class CloneFactory {
  /**
   * Copy all fields from source to target
   * @param target the target object
   * @param source the source object
   * @return the copied target object
   */
  public static ServiceOrderDto copy(ServiceOrderDto target, ServiceOrder source) {
    target.setId(source.getId());
    target.setType(source.getType());
    target.setAddress(source.getAddress());
    target.setConsignee(source.getConsignee());
    target.setMobile(source.getMobile());
    target.setStatus(source.getStatus());
    target.setDescription(source.getDescription());
    target.setGmtCreate(source.getGmtCreate());
    target.setGmtModified(source.getGmtModified());
    target.setGmtReceived(source.getGmtReceived());
    target.setGmtEnd(source.getGmtEnd());
    target.setResult(source.getResult());
    target.setServiceProviderId(source.getServiceProviderId());
    target.setProductId(source.getProductId());
    target.setRegionId(source.getRegionId());
    target.setShopId(source.getShopId());
    return target;
  }

  /**
   * Copy all fields from source to target
   * @param target the target object
   * @param source the source object
   * @return the copied target object
   */
  public static ProductService copy(ProductService target, ProductServicePo source) {
    target.setServiceId(source.getServiceId());
    target.setShopId(source.getShopId());
    target.setProductId(source.getProductId());
    target.setRegionId(source.getRegionId());
    target.setStatus(source.getStatus());
    target.setId(source.getId());
    target.setCreatorId(source.getCreatorId());
    target.setCreatorName(source.getCreatorName());
    target.setModifierId(source.getModifierId());
    target.setModifierName(source.getModifierName());
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
  public static ServiceOrdersDto copy(ServiceOrdersDto target, ServiceOrder source) {
    target.setId(source.getId());
    target.setType(source.getType());
    target.setDescription(source.getDescription());
    target.setProductId(source.getProductId());
    target.setRegionId(source.getRegionId());
    target.setShopId(source.getShopId());
    return target;
  }

  /**
   * Copy all fields from source to target
   * @param target the target object
   * @param source the source object
   * @return the copied target object
   */
  public static Services copy(Services target, ServicePo source) {
    target.setName(source.getName());
    target.setStatus(source.getStatus());
    target.setType(source.getType());
    target.setDescription(source.getDescription());
    target.setServiceProviderId(source.getServiceProviderId());
    target.setId(source.getId());
    target.setCreatorId(source.getCreatorId());
    target.setCreatorName(source.getCreatorName());
    target.setModifierId(source.getModifierId());
    target.setModifierName(source.getModifierName());
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
  public static ServiceProvider copy(ServiceProvider target, ServiceProviderVo source) {
    target.setName(source.getName());
    target.setConsignee(source.getConsignee());
    target.setMobile(source.getMobile());
    target.setAddress(source.getAddress());
    return target;
  }

  /**
   * Copy all fields from source to target
   * @param target the target object
   * @param source the source object
   * @return the copied target object
   */
  public static ServiceProvider copy(ServiceProvider target, ServiceProviderPo source) {
    target.setId(source.getId());
    target.setName(source.getName());
    target.setStatus(source.getStatus());
    target.setConsignee(source.getConsignee());
    target.setMobile(source.getMobile());
    target.setAddress(source.getAddress());
    target.setCreatorId(source.getCreatorId());
    target.setCreatorName(source.getCreatorName());
    target.setModifierId(source.getModifierId());
    target.setModifierName(source.getModifierName());
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
  public static ServicePo copy(ServicePo target, Services source) {
    target.setId(source.getId());
    target.setCreatorId(source.getCreatorId());
    target.setCreatorName(source.getCreatorName());
    target.setModifierId(source.getModifierId());
    target.setModifierName(source.getModifierName());
    target.setGmtCreate(source.getGmtCreate());
    target.setGmtModified(source.getGmtModified());
    target.setName(source.getName());
    target.setStatus(source.getStatus());
    target.setType(source.getType());
    target.setDescription(source.getDescription());
    target.setServiceProviderId(source.getServiceProviderId());
    return target;
  }

  /**
   * Copy all fields from source to target
   * @param target the target object
   * @param source the source object
   * @return the copied target object
   */
  public static ServiceProviderPo copy(ServiceProviderPo target, ServiceProvider source) {
    target.setId(source.getId());
    target.setCreatorId(source.getCreatorId());
    target.setCreatorName(source.getCreatorName());
    target.setModifierId(source.getModifierId());
    target.setModifierName(source.getModifierName());
    target.setGmtCreate(source.getGmtCreate());
    target.setGmtModified(source.getGmtModified());
    target.setName(source.getName());
    target.setStatus(source.getStatus());
    target.setConsignee(source.getConsignee());
    target.setMobile(source.getMobile());
    target.setAddress(source.getAddress());
    return target;
  }

  /**
   * Copy all fields from source to target
   * @param target the target object
   * @param source the source object
   * @return the copied target object
   */
  public static ProductServicePo copy(ProductServicePo target, ProductService source) {
    target.setId(source.getId());
    target.setCreatorId(source.getCreatorId());
    target.setCreatorName(source.getCreatorName());
    target.setModifierId(source.getModifierId());
    target.setModifierName(source.getModifierName());
    target.setGmtCreate(source.getGmtCreate());
    target.setGmtModified(source.getGmtModified());
    target.setServiceId(source.getServiceId());
    target.setProductId(source.getProductId());
    target.setShopId(source.getShopId());
    target.setRegionId(source.getRegionId());
    target.setStatus(source.getStatus());
    return target;
  }

  /**
   * Copy all fields from source to target
   * @param target the target object
   * @param source the source object
   * @return the copied target object
   */
  public static ServiceOrder copy(ServiceOrder target, ServiceOrderVo source) {
    target.setId(source.getId());
    target.setType(source.getType());
    target.setAddress(source.getAddress());
    target.setConsignee(source.getConsignee());
    target.setMobile(source.getMobile());
    target.setMaintainerName(source.getMaintainerName());
    target.setMaintainerMobile(source.getMaintainerMobile());
    target.setStatus(source.getStatus());
    target.setDescription(source.getDescription());
    target.setGmtCreate(source.getGmtCreate());
    target.setGmtModified(source.getGmtModified());
    target.setGmtReceived(source.getGmtReceived());
    target.setGmtEnd(source.getGmtEnd());
    target.setResult(source.getResult());
    target.setServiceProviderId(source.getServiceProviderId());
    target.setCreatorId(source.getCreatorId());
    target.setCreatorName(source.getCreatorName());
    target.setModifierId(source.getModifierId());
    target.setModifierName(source.getModifierName());
    target.setProductId(source.getProductId());
    target.setRegionId(source.getRegionId());
    target.setShopId(source.getShopId());
    return target;
  }

  /**
   * Copy all fields from source to target
   * @param target the target object
   * @param source the source object
   * @return the copied target object
   */
  public static ServiceOrder copy(ServiceOrder target, ServiceOrderPo source) {
    target.setId(source.getId());
    target.setType(source.getType());
    target.setAddress(source.getAddress());
    target.setConsignee(source.getConsignee());
    target.setMobile(source.getMobile());
    target.setMaintainerName(source.getMaintainerName());
    target.setMaintainerMobile(source.getMaintainerMobile());
    target.setStatus(source.getStatus());
    target.setDescription(source.getDescription());
    target.setGmtCreate(source.getGmtCreate());
    target.setGmtModified(source.getGmtModified());
    target.setGmtReceived(source.getGmtReceived());
    target.setGmtEnd(source.getGmtEnd());
    target.setResult(source.getResult());
    target.setServiceProviderId(source.getServiceProviderId());
    target.setFreightExpressId(source.getFreightExpressId());
    target.setCreatorId(source.getCreatorId());
    target.setCreatorName(source.getCreatorName());
    target.setModifierId(source.getModifierId());
    target.setModifierName(source.getModifierName());
    target.setSerialNo(source.getSerialNo());
    target.setProductId(source.getProductId());
    target.setRegionId(source.getRegionId());
    target.setShopId(source.getShopId());
    return target;
  }

  /**
   * Copy all fields from source to target
   * @param target the target object
   * @param source the source object
   * @return the copied target object
   */
  public static ServiceProviderDto copy(ServiceProviderDto target, ServiceProvider source) {
    target.setId(source.getId());
    target.setName(source.getName());
    target.setStatus(source.getStatus());
    target.setConsignee(source.getConsignee());
    target.setMobile(source.getMobile());
    target.setAddress(source.getAddress());
    return target;
  }

  /**
   * Copy all fields from source to target
   * @param target the target object
   * @param source the source object
   * @return the copied target object
   */
  public static ServiceOrderPo copy(ServiceOrderPo target, ServiceOrder source) {
    target.setId(source.getId());
    target.setType(source.getType());
    target.setAddress(source.getAddress());
    target.setConsignee(source.getConsignee());
    target.setMobile(source.getMobile());
    target.setMaintainerName(source.getMaintainerName());
    target.setMaintainerMobile(source.getMaintainerMobile());
    target.setStatus(source.getStatus());
    target.setDescription(source.getDescription());
    target.setGmtCreate(source.getGmtCreate());
    target.setGmtModified(source.getGmtModified());
    target.setGmtReceived(source.getGmtReceived());
    target.setGmtEnd(source.getGmtEnd());
    target.setResult(source.getResult());
    target.setServiceProviderId(source.getServiceProviderId());
    target.setFreightExpressId(source.getFreightExpressId());
    target.setSerialNo(source.getSerialNo());
    target.setProductId(source.getProductId());
    target.setRegionId(source.getRegionId());
    target.setShopId(source.getShopId());
    target.setCreatorId(source.getCreatorId());
    target.setCreatorName(source.getCreatorName());
    target.setModifierId(source.getModifierId());
    target.setModifierName(source.getModifierName());
    return target;
  }
}
