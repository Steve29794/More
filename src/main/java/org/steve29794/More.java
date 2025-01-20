package org.steve29794;

import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.attribute.Attributable;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Damageable;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.UUID;

public class More extends PlaceholderExpansion {
    @Override
    public @NotNull String getIdentifier() {
        return "more";
    }

    @Override
    public @NotNull String getAuthor() {
        return "Steve29794";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0.0";
    }

    @Override
    public String onRequest(OfflinePlayer player, @NotNull String params) {
        String[] args = params.split("_", 3);
        switch (args[0]) {
            // %more_attribute_<uuid>_<identifier>%
            case "attribute" -> {
                if (args.length != 3) return "WRONG ARGUMENT LENGTH";
                UUID uuid = UUID.fromString(PlaceholderAPI.setBracketPlaceholders(player, args[1]));
                if (!(Bukkit.getEntity(uuid) instanceof Attributable e)) return "NOT AN ATTRIBUTABLE";
                try {
                    return String.valueOf(Objects.requireNonNull(e.getAttribute(Attribute.valueOf(args[2].toUpperCase()))).getValue());
                } catch (IllegalArgumentException ex) {
                    return "UNKNOWN ATTRIBUTE";
                }
            }
            // %more_time_<type>_<world>
            // type: 12th, 24th, half
            case "time" -> {
                if (args.length != 3) return "WRONG ARGUMENT LENGTH";
                World w = Bukkit.getWorld(PlaceholderAPI.setBracketPlaceholders(player, args[2]));
                if (w == null) return "UNKNOWN WORLD";
                switch (args[1].toLowerCase()) {
                    case  "12th" -> {
                        long time = w.getTime();
                        int hours = (int) (time / 1000) + 6;
                        int minutes = (int) ( (time % 1000) * 60 / 1000 );
                        if (hours > 24) {
                            return (hours - 24) + ":" + minutes + " PM";
                        } else if (hours > 12) {
                            return (hours - 12) + ":" + minutes + " PM";
                        } else {
                            return hours + ":" + String.format("%02d", minutes) + " AM";
                        }
                    }
                    case "24th" -> {
                        long time = w.getTime();
                        int hours = (int) (time / 1000);
                        int minutes = (int) ( (time % 1000) * 60 / 1000 );
                        return hours + ":" + String.format("%02d", minutes);
                    }
                    case "half" -> {
                        return w.isDayTime() ? "DAY" : "NIGHT";
                    }
                    default -> {
                        return "UNKNOWN TYPE";
                    }
                }
            }
            // %more_health_<uuid>%
            case "health" -> {
                if (args.length != 2) return "WRONG ARGUMENT LENGTH";
                UUID uuid = UUID.fromString(PlaceholderAPI.setBracketPlaceholders(player, args[1]));
                if (!(Bukkit.getEntity(uuid) instanceof Damageable e)) return "NOT AN DAMAGEABLE";
                return String.valueOf(e.getHealth());
            }
            default -> {
                return "UNKNOWN OPTION";
            }
        }
    }
}
