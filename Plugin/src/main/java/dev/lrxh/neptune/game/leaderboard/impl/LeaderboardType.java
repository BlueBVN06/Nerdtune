package dev.lrxh.neptune.game.leaderboard.impl;

import dev.lrxh.neptune.profile.data.KitData;
import lombok.Getter;

import javax.annotation.Nullable;

@Getter
public enum LeaderboardType {
    WINS("Wins") {
        @Override
        public int get(KitData kitData) {
            return kitData.getWins();
        }
    },
    BEST_WIN_STREAK("Best Win Streak") {
        @Override
        public int get(KitData kitData) {
            return kitData.getBestStreak();
        }
    },
    DEATHS("Deaths") {
        @Override
        public int get(KitData kitData) {
            return kitData.getLosses();
        }
    };

    private final String name;

    LeaderboardType(String name) {
        this.name = name;
    }

    @Nullable
    public static LeaderboardType value(String value) {
        for (LeaderboardType leaderboardType : values()) {
            if (leaderboardType.toString().equalsIgnoreCase(value)) {
                return leaderboardType;
            }
        }

        return null;
    }

    public abstract int get(KitData kitData);
}
