package com.isep.appli.controllers;

import com.isep.appli.models.Item;
import com.isep.appli.models.Personnage;
import com.isep.appli.services.InventoryService;
import com.isep.appli.services.ShopService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
public class ShopController {

    @Autowired
    private ShopService shopService;


    /*******************************************************************************/
    /******************************** ALL ******************************************/
    /*******************************************************************************/


    /*******************************************************************************/
    /******************************** GUEST ****************************************/
    /*******************************************************************************/


    /*******************************************************************************/
    /******************************** PLAYER ***************************************/
    /*******************************************************************************/

    @GetMapping("/shop")
    public ModelAndView shop() {
        ModelAndView modelAndView = new ModelAndView("inventory");
        modelAndView.addObject("shop", shopService.getAll());
        modelAndView.addObject("item",new Item());
        return modelAndView;
    }
}