package li.cryx.expcraft;

import java.net.InetSocketAddress;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Achievement;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Instrument;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Note;
import org.bukkit.Server;
import org.bukkit.Statistic;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.entity.Vehicle;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.map.MapView;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

public class DummyPlayer implements Player {

	private String name;

	public DummyPlayer(final String name) {
		this.name = name;
	}

	@Override
	public PermissionAttachment addAttachment(final Plugin plugin) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PermissionAttachment addAttachment(final Plugin plugin,
			final int ticks) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PermissionAttachment addAttachment(final Plugin plugin,
			final String name, final boolean value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PermissionAttachment addAttachment(final Plugin plugin,
			final String name, final boolean value, final int ticks) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void awardAchievement(final Achievement achievement) {
		// TODO Auto-generated method stub

	}

	@Override
	public void chat(final String msg) {
		// TODO Auto-generated method stub

	}

	@Override
	public void damage(final int amount) {
		// TODO Auto-generated method stub

	}

	@Override
	public void damage(final int amount, final Entity source) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean eject() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public InetSocketAddress getAddress() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Location getBedSpawnLocation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Location getCompassTarget() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDisplayName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<PermissionAttachmentInfo> getEffectivePermissions() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getEntityId() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getExhaustion() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getExperience() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getEyeHeight() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getEyeHeight(final boolean ignoreSneaking) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Location getEyeLocation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public float getFallDistance() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getFireTicks() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getFoodLevel() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public GameMode getGameMode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getHealth() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public PlayerInventory getInventory() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ItemStack getItemInHand() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getLastDamage() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public EntityDamageEvent getLastDamageCause() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Block> getLastTwoTargetBlocks(final HashSet<Byte> transparent,
			final int maxDistance) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getLevel() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Block> getLineOfSight(final HashSet<Byte> transparent,
			final int maxDistance) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Location getLocation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getMaxFireTicks() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getMaximumAir() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getMaximumNoDamageTicks() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public List<Entity> getNearbyEntities(final double x, final double y,
			final double z) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getNoDamageTicks() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Entity getPassenger() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPlayerListName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getPlayerTime() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long getPlayerTimeOffset() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getRemainingAir() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getSaturation() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Server getServer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getSleepTicks() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Block getTargetBlock(final HashSet<Byte> transparent,
			final int maxDistance) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getTicksLived() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTotalExperience() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public UUID getUniqueId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vehicle getVehicle() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vector getVelocity() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public World getWorld() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasPermission(final Permission perm) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean hasPermission(final String name) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void incrementStatistic(final Statistic statistic) {
		// TODO Auto-generated method stub

	}

	@Override
	public void incrementStatistic(final Statistic statistic, final int amount) {
		// TODO Auto-generated method stub

	}

	@Override
	public void incrementStatistic(final Statistic statistic,
			final Material material) {
		// TODO Auto-generated method stub

	}

	@Override
	public void incrementStatistic(final Statistic statistic,
			final Material material, final int amount) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isBanned() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isDead() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isInsideVehicle() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isOnline() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isOp() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isPermissionSet(final Permission perm) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isPermissionSet(final String name) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isPlayerTimeRelative() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isSleeping() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isSleepingIgnored() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isSneaking() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isSprinting() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isWhitelisted() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void kickPlayer(final String message) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean leaveVehicle() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void loadData() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean performCommand(final String command) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void playEffect(final Location loc, final Effect effect,
			final int data) {
		// TODO Auto-generated method stub

	}

	@Override
	public void playNote(final Location loc, final byte instrument,
			final byte note) {
		// TODO Auto-generated method stub

	}

	@Override
	public void playNote(final Location loc, final Instrument instrument,
			final Note note) {
		// TODO Auto-generated method stub

	}

	@Override
	public void recalculatePermissions() {
		// TODO Auto-generated method stub

	}

	@Override
	public void remove() {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeAttachment(final PermissionAttachment attachment) {
		// TODO Auto-generated method stub

	}

	@Override
	public void resetPlayerTime() {
		// TODO Auto-generated method stub

	}

	@Override
	public void saveData() {
		// TODO Auto-generated method stub

	}

	@Override
	public void sendBlockChange(final Location loc, final int material,
			final byte data) {
		// TODO Auto-generated method stub

	}

	@Override
	public void sendBlockChange(final Location loc, final Material material,
			final byte data) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean sendChunkChange(final Location loc, final int sx,
			final int sy, final int sz, final byte[] data) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void sendMap(final MapView map) {
		// TODO Auto-generated method stub

	}

	@Override
	public void sendMessage(final String message) {
		// TODO Auto-generated method stub

	}

	@Override
	public void sendRawMessage(final String message) {
		// TODO Auto-generated method stub

	}

	@Override
	public Map<String, Object> serialize() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setBanned(final boolean banned) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setCompassTarget(final Location loc) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setDisplayName(final String name) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setExhaustion(final float value) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setExperience(final int exp) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setFallDistance(final float distance) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setFireTicks(final int ticks) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setFoodLevel(final int value) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setGameMode(final GameMode mode) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setHealth(final int health) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setItemInHand(final ItemStack item) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setLastDamage(final int damage) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setLastDamageCause(final EntityDamageEvent event) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setLevel(final int level) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setMaximumAir(final int ticks) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setMaximumNoDamageTicks(final int ticks) {
		// TODO Auto-generated method stub

	}

	public void setName(final String name) {
		this.name = name;
	}

	@Override
	public void setNoDamageTicks(final int ticks) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setOp(final boolean value) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean setPassenger(final Entity passenger) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setPlayerListName(final String name) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setPlayerTime(final long time, final boolean relative) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setRemainingAir(final int ticks) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setSaturation(final float value) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setSleepingIgnored(final boolean isSleeping) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setSneaking(final boolean sneak) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setSprinting(final boolean sprinting) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setTicksLived(final int value) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setTotalExperience(final int exp) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setVelocity(final Vector velocity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setWhitelisted(final boolean value) {
		// TODO Auto-generated method stub

	}

	@Override
	public Arrow shootArrow() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean teleport(final Entity destination) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean teleport(final Location location) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Egg throwEgg() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Snowball throwSnowball() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateInventory() {
		// TODO Auto-generated method stub

	}

}
