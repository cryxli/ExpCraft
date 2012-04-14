package li.cryx.expcraft.persist.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import li.cryx.expcraft.module.ExpCraftModule;

import org.bukkit.entity.Player;

@Entity
@Table(name = "ExpCraftTable")
public class Experience {

	@Id
	private Long id;

	@Column(nullable = false)
	private String player;

	@Column(length = 10, nullable = false)
	private String module;

	@Column(name = "exp", nullable = false)
	private double experience;

	public double getExperience() {
		return experience;
	}

	public Long getId() {
		return id;
	}

	public String getModule() {
		return module;
	}

	public String getPlayer() {
		return player;
	}

	public void setExperience(final double experience) {
		this.experience = experience;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public void setModule(final String module) {
		this.module = module;
	}

	public void setModuleEntity(final ExpCraftModule module) {
		setModule(module.getAbbr());
	}

	public void setPlayer(final String player) {
		this.player = player;
	}

	public void setPlayerEntity(final Player player) {
		setPlayer(player.getName());
	}
}
