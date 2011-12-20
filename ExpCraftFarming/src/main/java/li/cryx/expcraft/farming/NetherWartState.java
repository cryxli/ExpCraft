package li.cryx.expcraft.farming;


/**
 * Enum to track states of <code>Material.NETHER_WARTS</code>.
 * 
 * @author cryxli
 */
public enum NetherWartState {
	/** Wart has just been planted. */
	PLANTED(1), //
	/** Wart has grown one state. */
	GROWING(2), //
	/** Wart is fully grown. */
	RIPE(3);

	private byte data;

	private NetherWartState(final int data) {
		this.data = (byte) data;
	}

	public byte getData() {
		return data;
	}
}
