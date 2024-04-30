package com.isep.appli.controllers;

import com.isep.appli.models.Item;
import com.isep.appli.services.InventoryService;
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
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;


    /*******************************************************************************/
    /******************************** ALL ******************************************/
    /*******************************************************************************/


    /*******************************************************************************/
    /******************************** GUEST ****************************************/
    /*******************************************************************************/


    /*******************************************************************************/
    /******************************** PLAYER ***************************************/
    /*******************************************************************************/

    @GetMapping("/inventory")
    public ModelAndView inventory() {
        Map<Item,Integer> playerInventory = inventoryService.getPlayerInventory(1L);

        ModelAndView modelAndView = new ModelAndView("inventory");
        modelAndView.addObject("playerInventory", playerInventory);
        modelAndView.addObject("item",new Item());
        return modelAndView;
    }

    @PostMapping("/inventory")
    public String searchItem(@Valid Item item, BindingResult result, Model model){
        return "redirect:/inventory";
    }
}