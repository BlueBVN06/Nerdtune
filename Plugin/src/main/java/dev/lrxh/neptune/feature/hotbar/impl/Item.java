package dev.lrxh.neptune.feature.hotbar.impl;

import dev.lrxh.neptune.feature.hotbar.HotbarService;
import dev.lrxh.neptune.profile.data.ProfileState;
import dev.lrxh.neptune.providers.clickable.Replacement;
import dev.lrxh.neptune.utils.ItemBuilder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;


@Getter
@AllArgsConstructor
public class Item {
    private ItemAction action;
    private String displayName;
    private String material;
    private boolean enabled;
    private byte slot;

    public static Item getByItemStack(ProfileState profileState, ItemStack itemStack, UUID playerUUID) {
        Hotbar inventory = HotbarService.get().getItems().get(profileState);
        return getItemFromInventory(itemStack, inventory, playerUUID);
    }

    private static Item getItemFromInventory(ItemStack itemStack, Hotbar inventory, UUID playerUUID) {
        Player player = Bukkit.getPlayer(playerUUID);
        if (player == null) return null;

        if (inventory != null) {
            for (int slot = 0; slot <= 8; slot++) {
                Item item = HotbarService.get().getItemForSlot(inventory, slot);
                if (item != null && item.constructItem(playerUUID).getItemMeta().getDisplayName().equals(itemStack.getItemMeta().getDisplayName())) {
                    return item;
                }
            }
        }
        return null;
    }

    public ItemStack constructItem(UUID playerUUID) {
        Player player = Bukkit.getPlayer(playerUUID);
        if (player == null) return new ItemStack(Material.BARRIER);

        return new ItemBuilder(material, playerUUID).name(displayName).makeUnbreakable().build();
    }

    public ItemStack constructItem(UUID playerUUID, Replacement... replacements) {
        Player player = Bukkit.getPlayer(playerUUID);
        if (player == null) return new ItemStack(Material.BARRIER);

        for (Replacement replacement : replacements) {
            if (replacement.getReplacement() instanceof String string) {
                displayName = displayName.replace(replacement.getPlaceholder(), string);
            }
        }

        return new ItemBuilder(material, playerUUID).name(displayName).makeUnbreakable().build();
    }
}
