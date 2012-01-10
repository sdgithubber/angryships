package com.gemserk.games.angryships.entities;

public class Collisions {

	public static final short All = 0xFF;
	public static final short None = 0x00;
	
	public static final short Bomb = 0x01;
	public static final short Target = 0x02;
	public static final short Explosion = 0x04;
	public static final short AreaTrigger = 0x08;

	public static final String ExplosionType = "Explosion";
	public static final String RemoveAreaType = "RemoveArea";
	
}
