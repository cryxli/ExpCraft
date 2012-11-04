package li.cryx.expcraft.farming;

/**
 * Enum to track states of <code>Material.CARROT</code>.
 * 
 * @author cryxli
 */
public enum CarrotState {
	/** Carrot has just been planted. */
	PLANTED(0), //
	/** Carrot has grown one state. */
	LIGHT_GROW(2), //
	/** Carrot has grown two states. */
	HIGH_GROW(4), //
	/** Carrot is fully grown. */
	RIPE(7);

	private byte data;

	private CarrotState(final int data) {
		this.data = (byte) data;
	}

	public byte getData() {
		return data;
	}
}
