package com.isep.appli.controllers;

import com.isep.appli.dbModels.Item;
import com.isep.appli.dbModels.Personnage;
import com.isep.appli.dbModels.Shop;
import com.isep.appli.models.enums.ItemCategory;
import com.isep.appli.services.ShopService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

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
    public String shop(Model model, HttpSession session) {
        Personnage character = (Personnage) session.getAttribute("personnage");
        if (session.getAttribute("user") == null || session.getAttribute("personnage") == null) {
            return "errors/error-401";
        }
        model.addAttribute("personnage", character);
        model.addAttribute("allShop", shopService.getAll());
        model.addAttribute("shopCell", new Shop());
        model.addAttribute("item", new Item());
        return "/shop";
    }

    @PostMapping("/findItem")
    public String searchItem(@Valid Item item, BindingResult result, Model model, HttpSession session) {
        Personnage character = (Personnage) session.getAttribute("personnage");
        model.addAttribute("shop", new Shop());

        ItemCategory itemCategory = item.getCategory();
        if (!item.getName().equals("") && itemCategory != null) {
            List<Shop> playerInventory = shopService.getShopByItemNameAndItemCategory(item);
            model.addAttribute("allShop", playerInventory);
        } else if (!item.getName().equals("") && itemCategory == null) {
            List<Shop> playerInventory = shopService.getShopByItemName(item);
            model.addAttribute("allShop", playerInventory);
        } else if (item.getName().equals("") && itemCategory != null) {
            List<Shop> playerInventory = shopService.getShopByItemCategory(item);
            model.addAttribute("allShop", playerInventory);
        } else {
            List<Shop> playerInventory = (List<Shop>) shopService.getAll();
            model.addAttribute("allShop", playerInventory);
        }
        return "/shop";
    }
}