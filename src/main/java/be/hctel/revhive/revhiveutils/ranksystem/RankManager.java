package be.hctel.revhive.revhiveutils.ranksystem;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.entity.Player;

import be.hctel.revhive.hiveutils.sqlutil.SQLConnection;
/**
 * 
 * @author hctel
 *
 */
public class RankManager {
	SQLConnection connection;
	HashMap<Player, Rank> cache = new HashMap<Player, Rank>();
	HashMap<Player, Boolean> cacheHidden = new HashMap<Player, Boolean>();
	/**
	 * 
	 * @param connection
	 */
	public RankManager(SQLConnection connection) {
		this.connection = connection;
	}
	/**
	 * Returns the rank of a player
	 * @param player : The player to get the rank of
	 * @return the rank of the player
	 */
	public Rank getRank(Player player) {
		if(cache.containsKey(player)) {
			return cache.get(player);
		} else {
			String uuid = getUUID(player);
			int rankID = connection.getInt("RANK", uuid, "rankID");
			switch (rankID) {
			case 0:
				cache.put(player, Rank.REGULAR);
				return Rank.REGULAR;
			case 1:
				cache.put(player, Rank.GOLD);
				return Rank.GOLD;
			case 2:
				cache.put(player, Rank.DIAMOND);
				return Rank.DIAMOND;
			case 3:
				cache.put(player, Rank.EMERALD);
				return Rank.EMERALD;
			case 4 : 
				cache.put(player, Rank.ULTIMATE);
				return Rank.ULTIMATE;
			case 5 :
				cache.put(player, Rank.HELPER);
				return Rank.HELPER;
			case 6 : 
				cache.put(player, Rank.MODERATOR);
				return Rank.MODERATOR;
			case 7:
				cache.put(player, Rank.MANAGER);
				return Rank.MANAGER;
			case 8: 
				cache.put(player, Rank.DEVELOPPER);
				return Rank.DEVELOPPER;
			case 9:
				cache.put(player, Rank.OWNER);
				return Rank.OWNER;
			default:
					return Rank.REGULAR;
			}
		}
	}
	/**
	 * Check if a player has chosen to hide their rank or not
	 * @param player the player to check
	 * @return if the rank should be hidden or not
	 */
	public boolean rankHidden(Player player) {
		if(cacheHidden.containsKey(player)) {
			return cacheHidden.get(player);
		} else {
			String uuid = getUUID(player);
			int hid = connection.getInt("RANK", uuid, "hiddenRank");
			switch(hid) {
			case 0:
				cacheHidden.put(player, false);
				return false;
			case 1:
				cacheHidden.put(player, true);
				return true;
			default:
					 return false;
			}
		}
	}
	/**
	 * Sets the rank of a player
	 * @param player the player to change the rank
	 * @param rank the new rank of the player
	 */
	public void setRank(Player player, Rank rank) {
		String uuid = getUUID(player);
		int rankID;
		switch (rank) {
		case REGULAR:
			rankID = 0;
		case GOLD:
			rankID = 1;
		case DIAMOND:
			rankID = 2;
		case EMERALD:
			rankID = 3;
		case ULTIMATE:
			rankID = 4;
		case HELPER:
			rankID = 5;
		case MODERATOR:
			rankID = 6;
		case MANAGER:
			rankID = 7;
		case DEVELOPPER:
			rankID = 8;
		case OWNER:
			rankID = 9;
		default:
			rankID = 0;
		}
		if(cache.containsKey(player)) {
			cache.replace(player, rank);
		} else {
			cache.put(player, rank);
		}
		connection.insert("RANK", uuid, "rankID", rankID);
	}
	/**
	 * Sets if a player wants their rank to be hidden or not
	 * @param player the player who wants to hide/show their rank
	 * @param hidden if the rank should be hidden
	 */
	public void setRankHidden(Player player, boolean hidden) {
		String uuid = getUUID(player);
		int sql;
		if(cacheHidden.containsKey(player)) {
			cacheHidden.replace(player, hidden);
			if(hidden) {
				sql = 1;
			} else {
				cacheHidden.replace(player, true);
				sql = 0;
			}
		} else {
			cacheHidden.put(player, hidden);
			if(hidden) {
				sql = 1;
			} else {
				sql = 0;
			}
		}
		connection.insert("RANK", uuid, "hiddenRank", sql);
	}
	/**
	 * Get the color code of the rank of a player and checks if they want their rank to be shown or not.
	 * @param player the player to get the color code from
	 * @return the "§" color code
	 */
	public String getRankColorCode(Player player) {
		Rank rank = this.getRank(player);
		boolean hidden = this.rankHidden(player);
		if(hidden) {
			return "§9";
		} else {
			return rank.getColor();
		}
	}
	
	private String getUUID(Player player) {
		UUID u = player.getUniqueId();
		String uu = u.toString();
		String uui = uu.replace("-", "");
		return uui;
	}
}