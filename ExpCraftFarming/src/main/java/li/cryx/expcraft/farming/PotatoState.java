package li.cryx.expcraft.farming;

/**
 * Enum to track states of <code>Material.POTATO</code>.
 * 
 * @author cryxli
 */
public enum PotatoState {
	/** Potato has just been planted. */
	PLANTED(1), //
	/** Potato has grown one state. */
	LIGHT_GROW(2), //
	/** Potato has grown two states. */
	HIGH_GROW(3), //
	/** Potato is fully grown. */
	RIPE(4);

	private byte data;

	private PotatoState(final int data) {
		this.data = (byte) data;
	}

	public byte getData() {
		return data;
	}
}
