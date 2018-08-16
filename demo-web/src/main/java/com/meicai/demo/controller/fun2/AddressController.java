package com.meicai.demo.controller.fun2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.sprucetec.bone.common.Page;
import com.sprucetec.bone.common.PageCondition;
import com.sprucetec.bone.common.ServiceResult;
import com.sprucetec.bone.common.util.SecurityUtil;
import com.meicai.demo.fun2.entity.Address;
import com.meicai.demo.fun2.service.AddressService;

@Controller
public class AddressController {
    @Autowired
    private AddressService addressService;

    @RequestMapping(value = "/address", method = RequestMethod.GET)
    public ModelAndView showAddess(long id, String pk) {
        if (id > 0 && pk == null) {
            ModelAndView mav = new ModelAndView("error/error");
            return mav;
        }
        ModelAndView mav = new ModelAndView("address/address");
        Address address = null;
        if (id > 0) {

            address = this.addressService.queryAddress(id);
        }
        if (address == null) {
            address = new Address();
        }
        mav.addObject("address", address);
        PageCondition condition = new PageCondition(20, 1);
        Page<Address> page = this.addressService.queryAddresses(condition);
        mav.addObject("page", page);
        this.addUrlKey(page.getItems(), mav);
        return mav;
    }

    @RequestMapping(value = "/address", method = RequestMethod.POST)
    public
    @ResponseBody
    ServiceResult<Address> saveAddress(@Valid Address addressInfo, BindingResult errors) {
        ServiceResult<Address> result = new ServiceResult<>();
        if (errors.hasErrors()) {
            result.setSuccess(false);
            JSONObject json = new JSONObject();
            for (ObjectError error : errors.getAllErrors()) {
                if (error instanceof FieldError) {
                    json.put(((FieldError) error).getField(), error.getDefaultMessage());
                } else {
                    json.put(error.getObjectName(), error.getDefaultMessage());
                }
            }
            result.setMessage(json.toJSONString());
            return result;
        }
        if (addressInfo.getId() > 0) {
            this.addressService.editAddress(addressInfo);
            result.setBody(addressInfo);

        } else {
            result.setBody(this.addressService.addAddress(addressInfo));
        }
        return result;
    }

    private void addUrlKey(List<Address> list, ModelAndView mav) {
        Map<String, String> map = new HashMap<>();
        for (Address address : list) {
            map.put("pk_" + address.getId(), SecurityUtil.encryptString("id=" + address.getId()));
        }
        mav.addObject("map", map);
    }
}
