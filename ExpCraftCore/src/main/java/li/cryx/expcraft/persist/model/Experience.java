/*
 * Copyright (c) 2011 Urs P. Stettler, https://github.com/cryxli
 * 
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 * 
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
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

	@Column(name = "player", nullable = false)
	private String player;

	@Column(name = "playerUuid", nullable = false)
	private String playerUuid;

	@Column(name = "module", length = 10, nullable = false)
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

	public String getPlayerUuid() {
		return playerUuid;
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
		setModule(module.getInfo().getAbbr());
	}

	public void setPlayer(final String player) {
		this.player = player;
	}

	public void setPlayerEntity(final Player player) {
		setPlayer(player.getName());
		setPlayerUuid(player.getUniqueId().toString());
	}

	public void setPlayerUuid(final String playerUuid) {
		this.playerUuid = playerUuid;
	}
}
