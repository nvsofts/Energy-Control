package com.zuxelus.energycontrol.api;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;

public interface ICardReader {

	/**
	 * Method to get target coordinates for card.
	 */
	BlockPos getTarget();

	void setInt(String name, Integer value);

	Integer getInt(String name);

	void setLong(String name, Long value);

	Long getLong(String name);

	void setDouble(String name, Double value);

	Double getDouble(String name);

	void setString(String name, String value);

	String getString(String name);

	void setBoolean(String name, Boolean value);

	Boolean getBoolean(String name);

	/**
	 * Changes the title of the card. Can be used if you want tricky way to
	 * change title (not via Information Panel gui).
	 * 
	 * @param title
	 */
	void setTitle(String title);

	/**
	 * Get Title of the card. Title is set by player via Information Panel gui.
	 * 
	 * @return
	 */
	String getTitle();

	/**
	 * Get current card state. In most cases shouldn't be called by card.
	 * 
	 * @return
	 */
	CardState getState();

	/**
	 * Set the state of card. In most cases shouldn't be called by card, use
	 * return value of
	 * {@link IPanelDataSource#update(TileEntity, ICardWrapper, int)} instead.
	 * 
	 * @param state
	 */
	void setState(CardState state);

	/**
	 * Check is field exists
	 * 
	 * @param field
	 *            field name
	 * @return
	 */
	boolean hasField(String name);

	void removeField(String name);

	/**
	 * Used to send changed data to nearby players. In most cases shouldn't be
	 * called by card.
	 * 
	 * @param panel
	 */
	void updateClient(ItemStack stack, TileEntity panel, int slot);

	void updateServer(ItemStack stack, TileEntity panel, int slot);

	void setTag(String name, NBTBase value);

	NBTTagCompound getTag(String name);

	NBTTagList getTagList(String name, int type);

	ArrayList<ItemStack> getItemStackList(boolean reset);
	
	void setItemStackList(ArrayList<ItemStack> list);

	List<PanelString> getTitleList();

	int getCardCount();

	int getCardType();

}
