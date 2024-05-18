package com.isep.appli.controllers;

import com.isep.appli.dbModels.Item;
import com.isep.appli.dbModels.Personnage;
import com.isep.appli.models.Shop;
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
    public String shop(Model model, HttpSession session) {
        Personnage character = (Personnage) session.getAttribute("personnage");
        if (session.getAttribute("user") == null || session.getAttribute("personnage") == null) {
            return "errors/error-401";
        }
        model.addAttribute("personnage", character);
        model.addAttribute("allShop", shopService.getAllShop());
        model.addAttribute("shopCell", new Shop());
        model.addAttribute("item",new Item());
        return "/shop";
    }

    @PostMapping("/findItem")
    public String searchItem(@Valid Item item, BindingResult result, Model model, HttpSession session) {
        Personnage character = (Personnage) session.getAttribute("personnage");
        model.addAttribute("shop", new Shop());
        long characterId = character.getId();
        String itemName = item.getName();
        ItemCategory itemCategory = item.getCategory();
        if (!itemName.equals("") && itemCategory != null) {
            Map<Item, Shop> playerInventory = shopService.getShopByItemNameAndItemCategory(characterId, itemName, itemCategory);
            model.addAttribute("allShop", playerInventory);
        } else if (!itemName.equals("") && itemCategory == null) {
            Map<Item, Shop> playerInventory = shopService.getShopByItemName(characterId, itemName);
            model.addAttribute("allShop", playerInventory);
        } else if (itemName.equals("") && itemCategory != null) {
            Map<Item, Shop> playerInventory = shopService.getShopByItemCategory(characterId, itemCategory);
            model.addAttribute("allShop", playerInventory);
        } else {
            Map<Item, Shop> playerInventory = shopService.getAllShop();
            model.addAttribute("allShop", playerInventory);
        }
        return "/shop";
    }
}