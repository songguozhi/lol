package com.lol.web.system.handler;

import java.util.HashMap;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.lol.common.utils.FastMap;
import com.lol.web.common.bean.HonourParams;
import com.lol.web.common.enums.StatusCode;
import com.lol.web.system.entity.TbShop;
import com.lol.web.system.service.TbShopService;

@Controller
@RequestMapping("business/shop")
public class ShopHandler  extends BaseHandler{
    
    @Autowired
    private TbShopService shopService;
    
    /**
     * 修改保存店铺
     * @param shop
     * @return
     * @throws Exception
     */
    @RequestMapping("/saveOrUpdateShop")
    @ResponseBody
    public Map<String,Object> saveOrUpdateShop(TbShop shop) throws Exception{
        if(null == shop.getId())
            shop = shopService.save(shop);
        else
            shop = shopService.update(shop);
        
        return  new HashMap<String, Object>(new FastMap<String, Object>().newHashMap()
                .set("result", new HonourParams())
                .set("shop", shop));
    }
    
    /**
     * 查找店铺
     * @param shop
     * @return
     * @throws Exception
     */
    @RequestMapping("/findShop")
    @ResponseBody
    public TbShop findShop(TbShop shop) throws Exception{
        if(null != shop.getId()){
        	TbShop sp=shopService.get(shop.getId());
            return shopService.get(shop.getId());
        }
        
        return shop;
    }
    
    @RequestMapping("/delShop")
    @ResponseBody
    public String delShop(TbShop shop) throws Exception{
        if(null != shop.getId()){
            shopService.deleteById(shop.getId());
            return JSON.toJSONString(new HonourParams(StatusCode.SUCCESS.getCode(), "删除成功"));
        }
        
        return JSON.toJSONString(new HonourParams(StatusCode.ERROR.getCode(), "操作失败"));
    }


}
