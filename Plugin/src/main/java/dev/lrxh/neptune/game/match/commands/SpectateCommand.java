package dev.lrxh.neptune.game.match.commands;


import com.jonahseguin.drink.annotation.Command;
import com.jonahseguin.drink.annotation.Sender;
import dev.lrxh.neptune.API;
import dev.lrxh.neptune.configs.impl.MessagesLocale;
import dev.lrxh.neptune.profile.data.ProfileState;
import dev.lrxh.neptune.profile.impl.Profile;
import dev.lrxh.neptune.providers.clickable.Replacement;
import dev.lrxh.neptune.utils.CC;
import org.bukkit.entity.Player;

public class SpectateCommand {

    @Command(name = "", desc = "", usage = "<player>")
    public void spectate(@Sender Player player, Player target) {
        if (player.getName().equalsIgnoreCase(target.getName())) {
            player.sendMessage(CC.error("You can't spectate yourself!"));
            return;
        }

        Profile profile = API.getProfile(player);

        if (profile.getMatch() != null) {
            player.sendMessage(CC.error("You can't spectate while in a match!"));
            return;
        }

        Profile targetProfile = API.getProfile(target);
        if (targetProfile.getMatch() == null) {
            player.sendMessage(CC.error("Player isn't in a match!"));
            return;
        }

        if (!targetProfile.getSettingData().isAllowSpectators()) {
            MessagesLocale.SPECTATE_NOT_ALLOWED.send(player.getUniqueId(), new Replacement("<player>", target.getName()));
            return;
        }

        targetProfile.getMatch().addSpectator(player, target, true, true);
    }

    @Command(name = "leave", aliases = "exit", desc = "")
    public void leave(@Sender Player player) {
        Profile profile = API.getProfile(player);

        if (profile.getState().equals(ProfileState.IN_SPECTATOR)) {
            API.getProfile(player).getMatch().removeSpectator(player.getUniqueId(), true);
        }
    }
}