package li.cryx.expcraft;

import java.net.InetSocketAddress;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Achievement;
import org.bukkit.Effect;
import org.bukkit.EntityEffect;
import org.bukkit.GameMode;
import org.bukkit.Instrument;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Note;
import org.bukkit.Statistic;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.conversations.Conversation;
import org.bukkit.conversations.ConversationAbandonedEvent;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.entity.Vehicle;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.InventoryView.Property;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.map.MapView;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class DummyPlayer extends DummyCommandSender implements Player {

	private boolean allowFlight;

	public DummyPlayer(final String name) {
		super(name);
	}

	@Override
	public void abandonConversation(final Conversation arg0) {
	}

	@Override
	public void abandonConversation(final Conversation conversation,
			final ConversationAbandonedEvent details) {

	}

	@Override
	public void acceptConversationInput(final String arg0) {
	}

	@Override
	public boolean addPotionEffect(final PotionEffect arg0) {
		return false;
	}

	@Override
	public boolean addPotionEffect(final PotionEffect arg0, final boolean arg1) {
		return false;
	}

	@Override
	public boolean addPotionEffects(final Collection<PotionEffect> arg0) {
		return false;
	}

	@Override
	public void awardAchievement(final Achievement achievement) {
	}

	@Override
	public boolean beginConversation(final Conversation arg0) {
		return false;
	}

	@Override
	public boolean canSee(final Player arg0) {
		return false;
	}

	@Override
	public void chat(final String msg) {
	}

	@Override
	public void closeInventory() {
	}

	@Override
	public void damage(final int amount) {
	}

	@Override
	public void damage(final int amount, final Entity source) {
	}

	@Override
	public boolean eject() {
		return false;
	}

	@Override
	public Collection<PotionEffect> getActivePotionEffects() {
		return null;
	}

	@Override
	public InetSocketAddress getAddress() {
		return null;
	}

	@Override
	public boolean getAllowFlight() {
		return allowFlight;
	}

	@Override
	public Location getBedSpawnLocation() {
		return null;
	}

	@Override
	public Location getCompassTarget() {
		return null;
	}

	@Override
	public String getDisplayName() {
		return null;
	}

	@Override
	public int getEntityId() {
		return 0;
	}

	@Override
	public float getExhaustion() {
		return 0;
	}

	@Override
	public float getExp() {
		return 0;
	}

	@Override
	public double getEyeHeight() {
		return 0;
	}

	@Override
	public double getEyeHeight(final boolean ignoreSneaking) {
		return 0;
	}

	@Override
	public Location getEyeLocation() {
		return null;
	}

	@Override
	public float getFallDistance() {
		return 0;
	}

	@Override
	public int getFireTicks() {
		return 0;
	}

	@Override
	public long getFirstPlayed() {
		return 0;
	}

	@Override
	public int getFoodLevel() {
		return 0;
	}

	@Override
	public GameMode getGameMode() {
		return null;
	}

	@Override
	public int getHealth() {
		return 0;
	}

	@Override
	public PlayerInventory getInventory() {
		return null;
	}

	@Override
	public ItemStack getItemInHand() {
		return null;
	}

	@Override
	public ItemStack getItemOnCursor() {
		return null;
	}

	@Override
	public Player getKiller() {
		return null;
	}

	@Override
	public int getLastDamage() {
		return 0;
	}

	@Override
	public EntityDamageEvent getLastDamageCause() {
		return null;
	}

	@Override
	public long getLastPlayed() {
		return 0;
	}

	@Override
	public List<Block> getLastTwoTargetBlocks(final HashSet<Byte> transparent,
			final int maxDistance) {
		return null;
	}

	@Override
	public int getLevel() {
		return 0;
	}

	@Override
	public List<Block> getLineOfSight(final HashSet<Byte> transparent,
			final int maxDistance) {
		return null;
	}

	@Override
	public Set<String> getListeningPluginChannels() {
		return null;
	}

	@Override
	public Location getLocation() {
		return null;
	}

	@Override
	public int getMaxFireTicks() {
		return 0;
	}

	@Override
	public int getMaxHealth() {
		return 0;
	}

	@Override
	public int getMaximumAir() {
		return 0;
	}

	@Override
	public int getMaximumNoDamageTicks() {
		return 0;
	}

	@Override
	public List<MetadataValue> getMetadata(final String arg0) {
		return null;
	}

	@Override
	public List<Entity> getNearbyEntities(final double x, final double y,
			final double z) {
		return null;
	}

	@Override
	public int getNoDamageTicks() {
		return 0;
	}

	@Override
	public InventoryView getOpenInventory() {
		return null;
	}

	@Override
	public Entity getPassenger() {
		return null;
	}

	@Override
	public Player getPlayer() {
		return null;
	}

	@Override
	public String getPlayerListName() {
		return null;
	}

	@Override
	public long getPlayerTime() {
		return 0;
	}

	@Override
	public long getPlayerTimeOffset() {
		return 0;
	}

	@Override
	public int getRemainingAir() {
		return 0;
	}

	@Override
	public float getSaturation() {
		return 0;
	}

	@Override
	public int getSleepTicks() {
		return 0;
	}

	@Override
	public Block getTargetBlock(final HashSet<Byte> transparent,
			final int maxDistance) {
		return null;
	}

	@Override
	public int getTicksLived() {
		return 0;
	}

	@Override
	public int getTotalExperience() {
		return 0;
	}

	@Override
	public EntityType getType() {
		return EntityType.PLAYER;
	}

	@Override
	public UUID getUniqueId() {
		return null;
	}

	@Override
	public Vehicle getVehicle() {
		return null;
	}

	@Override
	public Vector getVelocity() {
		return null;
	}

	@Override
	public World getWorld() {
		return null;
	}

	@Override
	public void giveExp(final int amount) {
	}

	@Override
	public boolean hasMetadata(final String arg0) {
		return false;
	}

	@Override
	public boolean hasPlayedBefore() {
		return false;
	}

	@Override
	public boolean hasPotionEffect(final PotionEffectType arg0) {
		return false;
	}

	@Override
	public void hidePlayer(final Player arg0) {
	}

	@Override
	public void incrementStatistic(final Statistic statistic) {
	}

	@Override
	public void incrementStatistic(final Statistic statistic, final int amount) {
	}

	@Override
	public void incrementStatistic(final Statistic statistic,
			final Material material) {
	}

	@Override
	public void incrementStatistic(final Statistic statistic,
			final Material material, final int amount) {
	}

	@Override
	public boolean isBanned() {
		return false;
	}

	@Override
	public boolean isConversing() {
		return false;
	}

	@Override
	public boolean isDead() {
		return false;
	}

	@Override
	public boolean isEmpty() {
		return false;
	}

	@Override
	public boolean isInsideVehicle() {
		return false;
	}

	@Override
	public boolean isOnline() {
		return true;
	}

	@Override
	public boolean isPlayerTimeRelative() {
		return false;
	}

	@Override
	public boolean isSleeping() {
		return false;
	}

	@Override
	public boolean isSleepingIgnored() {
		return false;
	}

	@Override
	public boolean isSneaking() {
		return false;
	}

	@Override
	public boolean isSprinting() {
		return false;
	}

	@Override
	public boolean isWhitelisted() {
		return false;
	}

	@Override
	public void kickPlayer(final String message) {
	}

	@Override
	public <T extends Projectile> T launchProjectile(
			final Class<? extends T> arg0) {
		return null;
	}

	@Override
	public boolean leaveVehicle() {
		return false;
	}

	@Override
	public void loadData() {
	}

	@Override
	public InventoryView openEnchanting(final Location arg0, final boolean arg1) {
		return null;
	}

	@Override
	public InventoryView openInventory(final Inventory arg0) {
		return null;
	}

	@Override
	public void openInventory(final InventoryView arg0) {
	}

	@Override
	public InventoryView openWorkbench(final Location arg0, final boolean arg1) {
		return null;
	}

	@Override
	public boolean performCommand(final String command) {
		return false;
	}

	@Override
	public void playEffect(final EntityEffect type) {
	}

	@Override
	public void playEffect(final Location loc, final Effect effect,
			final int data) {
	}

	@Override
	public <T> void playEffect(final Location arg0, final Effect arg1,
			final T arg2) {
	}

	@Override
	public void playNote(final Location loc, final byte instrument,
			final byte note) {
	}

	@Override
	public void playNote(final Location loc, final Instrument instrument,
			final Note note) {
	}

	@Override
	public void remove() {
	}

	@Override
	public void removeMetadata(final String arg0, final Plugin arg1) {
	}

	@Override
	public void removePotionEffect(final PotionEffectType arg0) {
	}

	@Override
	public void resetPlayerTime() {
	}

	@Override
	public void saveData() {
	}

	@Override
	public void sendBlockChange(final Location loc, final int material,
			final byte data) {
	}

	@Override
	public void sendBlockChange(final Location loc, final Material material,
			final byte data) {
	}

	@Override
	public boolean sendChunkChange(final Location loc, final int sx,
			final int sy, final int sz, final byte[] data) {
		return false;
	}

	@Override
	public void sendMap(final MapView map) {
	}

	@Override
	public void sendMessage(final String[] arg0) {
	}

	@Override
	public void sendPluginMessage(final Plugin source, final String channel,
			final byte[] message) {
	}

	@Override
	public void sendRawMessage(final String message) {
	}

	@Override
	public Map<String, Object> serialize() {
		return null;
	}

	@Override
	public void setAllowFlight(final boolean flight) {
		allowFlight = flight;
	}

	@Override
	public void setBanned(final boolean banned) {
	}

	@Override
	public void setBedSpawnLocation(final Location location) {
	}

	@Override
	public void setCompassTarget(final Location loc) {
	}

	@Override
	public void setDisplayName(final String name) {
	}

	@Override
	public void setExhaustion(final float value) {
	}

	@Override
	public void setExp(final float exp) {
	}

	@Override
	public void setFallDistance(final float distance) {
	}

	@Override
	public void setFireTicks(final int ticks) {
	}

	@Override
	public void setFoodLevel(final int value) {
	}

	@Override
	public void setGameMode(final GameMode mode) {
	}

	@Override
	public void setHealth(final int health) {
	}

	@Override
	public void setItemInHand(final ItemStack item) {
	}

	@Override
	public void setItemOnCursor(final ItemStack arg0) {
	}

	@Override
	public void setLastDamage(final int damage) {
	}

	@Override
	public void setLastDamageCause(final EntityDamageEvent event) {
	}

	@Override
	public void setLevel(final int level) {
	}

	@Override
	public void setMaximumAir(final int ticks) {
	}

	@Override
	public void setMaximumNoDamageTicks(final int ticks) {
	}

	@Override
	public void setMetadata(final String arg0, final MetadataValue arg1) {
	}

	@Override
	public void setNoDamageTicks(final int ticks) {
	}

	@Override
	public boolean setPassenger(final Entity passenger) {
		return false;
	}

	@Override
	public void setPlayerListName(final String name) {
	}

	@Override
	public void setPlayerTime(final long time, final boolean relative) {
	}

	@Override
	public void setRemainingAir(final int ticks) {
	}

	@Override
	public void setSaturation(final float value) {
	}

	@Override
	public void setSleepingIgnored(final boolean isSleeping) {
	}

	@Override
	public void setSneaking(final boolean sneak) {
	}

	@Override
	public void setSprinting(final boolean sprinting) {
	}

	@Override
	public void setTicksLived(final int value) {
	}

	@Override
	public void setTotalExperience(final int exp) {
	}

	@Override
	public void setVelocity(final Vector velocity) {
	}

	@Override
	public void setWhitelisted(final boolean value) {
	}

	@Override
	public boolean setWindowProperty(final Property arg0, final int arg1) {
		return false;
	}

	@Override
	public Arrow shootArrow() {
		return null;
	}

	@Override
	public void showPlayer(final Player arg0) {
	}

	@Override
	public boolean teleport(final Entity destination) {
		return false;
	}

	@Override
	public boolean teleport(final Entity destination, final TeleportCause cause) {
		return false;
	}

	@Override
	public boolean teleport(final Location location) {
		return false;
	}

	@Override
	public boolean teleport(final Location location, final TeleportCause cause) {
		return false;
	}

	@Override
	public Egg throwEgg() {
		return null;
	}

	@Override
	public Snowball throwSnowball() {
		return null;
	}

	@Override
	public void updateInventory() {
	}

}
