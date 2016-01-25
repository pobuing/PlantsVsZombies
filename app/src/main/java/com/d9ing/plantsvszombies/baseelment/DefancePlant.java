package com.d9ing.plantsvszombies.baseelment;

/**
 * 防御类型植物
 * @author Administrator
 *
 */
public abstract class DefancePlant extends Plant {

	public DefancePlant(String filepath) {
		super(filepath);
		life = 200;
	}

}
