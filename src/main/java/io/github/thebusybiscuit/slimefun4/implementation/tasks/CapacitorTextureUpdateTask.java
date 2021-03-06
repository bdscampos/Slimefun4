package io.github.thebusybiscuit.slimefun4.implementation.tasks;

import javax.annotation.Nonnull;

import org.apache.commons.lang.Validate;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.block.Block;

import io.github.thebusybiscuit.cscorelib2.skull.SkullBlock;
import io.github.thebusybiscuit.slimefun4.implementation.items.electric.Capacitor;
import io.github.thebusybiscuit.slimefun4.utils.HeadTexture;

/**
 * This task is run whenever a {@link Capacitor} needs to update their texture.
 * <strong>This must be executed on the main {@link Server} {@link Thread}!</strong>
 * 
 * @author TheBusyBiscuit
 *
 */
public class CapacitorTextureUpdateTask implements Runnable {

    /**
     * The {@link Location} of the {@link Capacitor}.
     */
    private final Location l;

    /**
     * The level of how "full" this {@link Capacitor} is.
     * From 0.0 to 1.0.
     */
    private final double filledPercentage;

    /**
     * This creates a new {@link CapacitorTextureUpdateTask} with the given parameters.
     * 
     * @param l
     *            The {@link Location} of the {@link Capacitor}
     * @param charge
     *            The amount of charge in this {@link Capacitor}
     * @param capacity
     *            The capacity of this {@link Capacitor}
     */
    public CapacitorTextureUpdateTask(@Nonnull Location l, double charge, double capacity) {
        Validate.notNull(l, "The Location cannot be null");

        this.l = l;
        this.filledPercentage = charge / capacity;
    }

    @Override
    public void run() {
        Block b = l.getBlock();
        Material type = b.getType();

        // Ensure that this Block is still a Player Head
        if (type == Material.PLAYER_HEAD || type == Material.PLAYER_WALL_HEAD) {
            if (filledPercentage <= 0.25) {
                SkullBlock.setFromHash(b, HeadTexture.CAPACITOR_25.getTexture());
            } else if (filledPercentage <= 0.5) {
                SkullBlock.setFromHash(b, HeadTexture.CAPACITOR_50.getTexture());
            } else if (filledPercentage <= 0.75) {
                SkullBlock.setFromHash(b, HeadTexture.CAPACITOR_75.getTexture());
            } else {
                SkullBlock.setFromHash(b, HeadTexture.CAPACITOR_100.getTexture());
            }
        }
    }

}
