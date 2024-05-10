package com.isep.appli.controllers;

import com.isep.appli.models.Inventory;
import com.isep.appli.models.Item;
import com.isep.appli.models.Personnage;
import com.isep.appli.models.Shop;
import com.isep.appli.services.InventoryService;
import com.isep.appli.services.ItemService;
import com.isep.appli.services.ShopService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private ShopService shopService;
    @Autowired
    private ItemService itemService;


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
    public ModelAndView inventory(@Valid Personnage personnage) {
        Map<Item,Inventory> playerInventory = inventoryService.getPlayerInventory(1L);
        ModelAndView modelAndView = new ModelAndView("inventory");
        modelAndView.addObject("playerInventory", playerInventory);
        modelAndView.addObject("personnage", personnage);
        modelAndView.addObject("item",new Item());
        modelAndView.addObject("shop", new Shop());
        return modelAndView;
    }

    @PostMapping("/inventory")
    public String searchItem(@Valid Item item, BindingResult result, Model model){
        if (item.getName() == null) {
            return "/inventory";
        }
        Map<Item,Inventory> playerInventory = inventoryService.getPlayerInventoryByItemName(1L , item.getName());
        model.addAttribute("playerInventory", playerInventory);
        model.addAttribute("shop", new Shop());
        return "/inventory";
    }

    @PostMapping("/shop")
    public String sellItem(@Valid Shop shop, BindingResult result, Model model){
        Inventory inventory = inventoryService.getByItemId(1L, shop.getIdItem()).get(0);
        inventoryService.removeItemInInventory(inventory, shop.getQuantity());
        shop.setIdPlayer(1L);
        shopService.save(shop);
        return "redirect:/inventory";
    }

    @GetMapping("/inventory/{idInventory}")
    public ResponseEntity<Item> showSellItem(@PathVariable long idInventory){
        Inventory inventory = inventoryService.getById(idInventory).get();
        Item item = itemService.getById(inventory.getIdItem()).get();
        return ResponseEntity.ok(item);
    }
}