package com.isep.appli.controllers;


import com.isep.appli.dbModels.Inventory;
import com.isep.appli.dbModels.Item;
import com.isep.appli.dbModels.Personnage;
import com.isep.appli.models.Shop;
import com.isep.appli.models.enums.ItemCategory;
import com.isep.appli.services.InventoryService;
import com.isep.appli.services.ItemService;
import com.isep.appli.services.ShopService;
import jakarta.servlet.http.HttpSession;
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
    public String inventory(Model model, HttpSession session) {
        if(session.getAttribute("user") == null || session.getAttribute("personnage") == null){
            return "errors/error-401";
        }
        Personnage character = (Personnage) session.getAttribute("personnage");
        Map<Item, Inventory> playerInventory = inventoryService.getPlayerInventory(character.getId());
        model.addAttribute("playerInventory", playerInventory);
        model.addAttribute("personnage", character);
        model.addAttribute("item",new Item());
        model.addAttribute("shop", new Shop());
        return "/inventory";
    }

    @PostMapping("/inventory")
    public String searchItem(@Valid Item item, BindingResult result, Model model, HttpSession session){
        Personnage character = (Personnage) session.getAttribute("personnage");
        model.addAttribute("shop", new Shop());
        long characterId = character.getId();
        String itemName = item.getName();
        ItemCategory itemCategory = item.getCategory();
        if (!itemName.equals("") && itemCategory != null) {
            Map<Item,Inventory> playerInventory = inventoryService.getPlayerInventoryByItemNameAndItemCategory(characterId, itemName, itemCategory);
            model.addAttribute("playerInventory", playerInventory);
        } else if (!itemName.equals("") && itemCategory == null){
            Map<Item,Inventory> playerInventory = inventoryService.getPlayerInventoryByItemName(characterId, itemName);
            model.addAttribute("playerInventory", playerInventory);
        } else if (itemName.equals("") && itemCategory != null) {
            Map<Item,Inventory> playerInventory = inventoryService.getPlayerInventoryByItemCategory(characterId, itemCategory);
            model.addAttribute("playerInventory", playerInventory);
        } else {
            Map<Item,Inventory> playerInventory = inventoryService.getPlayerInventory(characterId);
            model.addAttribute("playerInventory", playerInventory);
        }
        return "/inventory";
    }

    @PostMapping("/shop")
    public String sellItem(@Valid Shop shop, BindingResult result, Model model, HttpSession session){
        Personnage character = (Personnage) session.getAttribute("personnage");
        Inventory inventory = inventoryService.getByItemId(character.getId(), shop.getIdItem()).get(0);
        inventoryService.removeItemInInventory(inventory, shop.getQuantity());
        shop.setIdPlayer(character.getId());
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