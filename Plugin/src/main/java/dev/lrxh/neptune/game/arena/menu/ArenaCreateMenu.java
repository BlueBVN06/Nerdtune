package dev.lrxh.neptune.game.arena.menu;

import dev.lrxh.neptune.game.arena.menu.button.ArenaSelectTypeButton;
import dev.lrxh.neptune.utils.menu.Button;
import dev.lrxh.neptune.utils.menu.Filter;
import dev.lrxh.neptune.utils.menu.Menu;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ArenaCreateMenu extends Menu {
    private final String name;

    public ArenaCreateMenu(String name) {
        super("&eSelect Arena type", 45, Filter.FILL);
        this.name = name;
    }

    @Override
    public List<Button> getButtons(Player player) {
        List<Button> buttons = new ArrayList<>();

        buttons.add(new ArenaSelectTypeButton(21, true, name));
        buttons.add(new ArenaSelectTypeButton(23, false, name));
        return buttons;
    }
}
