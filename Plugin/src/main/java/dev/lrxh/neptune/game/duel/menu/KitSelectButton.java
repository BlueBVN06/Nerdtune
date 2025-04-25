package dev.lrxh.neptune.game.duel.menu;

import dev.lrxh.neptune.API;
import dev.lrxh.neptune.configs.impl.MenusLocale;
import dev.lrxh.neptune.game.arena.Arena;
import dev.lrxh.neptune.game.duel.DuelRequest;
import dev.lrxh.neptune.game.kit.Kit;
import dev.lrxh.neptune.profile.impl.Profile;
import dev.lrxh.neptune.utils.CC;
import dev.lrxh.neptune.utils.ItemBuilder;
import dev.lrxh.neptune.utils.menu.Button;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class KitSelectButton extends Button {
    private final Kit kit;
    private final UUID receiver;
    private final boolean party;

    public KitSelectButton(int slot, Kit kit, UUID receiver, boolean party) {
        super(slot);
        this.kit = kit;
        this.receiver = receiver;
        this.party = party;
    }

    @Override
    public ItemStack getItemStack(Player player) {
        return new ItemBuilder(kit.getIcon()).name(MenusLocale.QUEUE_SELECT_KIT_NAME.getString().replace("<kit>", kit.getDisplayName()))
                .lore(MenusLocale.DUEL_LORE.getStringList(), player)

                .build();
    }

    @Override
    public void onClick(ClickType type, Player player) {
        if (party) {
            Profile profile = API.getProfile(receiver);
            if (profile == null) return;
            Arena arena = kit.getRandomArena();
            if (arena == null) {
                player.sendMessage(CC.error("No arena found, please contact and admin"));
                return;
            }
            DuelRequest duelRequest = new DuelRequest(player.getUniqueId(), kit, arena, true, 1);
            profile.sendDuel(duelRequest);
            player.closeInventory();
        } else {
            new RoundsSelectMenu(kit, receiver).open(player);
        }
    }
}