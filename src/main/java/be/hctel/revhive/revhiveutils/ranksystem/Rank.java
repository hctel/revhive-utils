package be.hctel.revhive.revhiveutils.ranksystem;
/**
 * 
 * @author hctel
 *
 */
public enum Rank {
	/**
	 * <p style="color:blue">Regular rank.</p> Rank will be automatically given to new players. 1 vote
	 */
	REGULAR("§9", 1, "Regular", false),
	/**
	 * <p style="color:gold">Gold rank.</p> Rank will be given when player buys the rank. 3 votes
	 */
	GOLD("§6", 3, "Premium Gold", false),
	/**
	 * <p style="color:cyan">Diamond rank.</p> Rank will be given when player buys the rank. 3 votes
	 */
	DIAMOND("§b", 3, "Premium Diamond", false),
	/**
	 * <p style="color:chartreuse">Emerald rank.</p> Rank will be given when player buys the rank. 3 votes
	 */
	EMERALD("§a", 3, "Premium Emerald", false),
	/**
	 * <p style="color:fuchsia">Ultimate rank.</p> Rank will be given when player buys the rank. 4 votes. Can fly in lobbies.
	 */
	ULTIMATE("§d", 4, "Premium Ultimate", true),
	/**
	 * <p style="color:green">Helper rank.</p> Rank will be given when player will be ranked. 4 votes. Can fly in lobbies.
	 */
	HELPER("§2", 4, "Helper", true),
	/**
	 * <p style="color:firebrick">Moderator rank.</p> Rank will be given when player will be ranked. 4 votes. Can fly in lobbies.
	 */
	MODERATOR("§c", 4, "Moderator", true),
	/**
	 * <p style="color:firebrick">Staff manager rank.</p> Rank will be given when player will be ranked. 4 votes. Can fly in lobbies.
	 */
	MANAGER("§c", 4, "Staff Manager", true),
	/**
	 * <p style="color:goldenrod">Developper rank.</p> Rank will be given when player will be ranked. 5 votes. Can fly in lobbies.
	 */
	DEVELOPPER("§6", 5, "Developper", true),
	/**
	 * <p style="color:gold">Owner rank.</p> Rank can only be given to owners. 5 votes. Can fly in lobbies.
	 */
	OWNER("§e", 5, "Owner", true);
	
	String color;
	int votes;
	String name;
	boolean canFly;
	private Rank(String color, int votes, String name, boolean canFly) {
		this.color = color;
		this.votes = votes;
		this.name = name;
		this.canFly = canFly;
	}
	/**
	 * Use to get the color code of a rank. For instance, if used with the value of Rank.ULTIMATE, it will return "§d".
	 * @return the "§ code" of the rank
	 */
	public String getColor() {
		return color;
	}
	/**
	 * Use to get the amount of map votes accorded to a player. For instance, if used with the value of Rank.DIAMOND, it will return 3
	 * @return the amount of map votes accorded to a player who has the rank
	 */
	public int getVoteAmount() {
		return votes;
	}
	/**
	 * Use to get the name of the rank. For instance, if used with the value of Rank.EMERALD, it will return "Emerald Premium".
	 * @return the name of the rank
	 */
	public String getRankName() {
		return name;
	}
	/**
	 * Use to check if the players who has this rank can fly. For instance, if used with the value of Rank.REGULAR, it will return false.
	 * @return if the players who has this rank can fly
	 */
	public boolean canFlyInLobbies() {
		return canFly;
	}
}

