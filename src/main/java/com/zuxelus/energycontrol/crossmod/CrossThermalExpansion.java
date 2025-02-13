package com.zuxelus.energycontrol.crossmod;

import cofh.core.block.TileNameable;
import cofh.core.block.TilePowered;
import cofh.redstoneflux.api.IEnergyStorage;
import cofh.thermalexpansion.block.device.TileFluidBuffer;
import cofh.thermalexpansion.block.dynamo.TileDynamoBase;
import cofh.thermalexpansion.block.dynamo.TileDynamoCompression;
import cofh.thermalexpansion.block.dynamo.TileDynamoMagmatic;
import cofh.thermalexpansion.block.machine.TileBrewer;
import cofh.thermalexpansion.block.machine.TileRefinery;
import com.zuxelus.energycontrol.api.ItemStackHelper;
import com.zuxelus.energycontrol.init.ModItems;
import com.zuxelus.energycontrol.items.cards.ItemCardType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.IFluidTank;

import java.util.ArrayList;
import java.util.List;

public class CrossThermalExpansion extends CrossModBase {

	@Override
	public NBTTagCompound getEnergyData(TileEntity te) {
		if (te instanceof TilePowered) {
			NBTTagCompound tag = new NBTTagCompound();
			IEnergyStorage storage = ((TilePowered) te).getEnergyStorage();
			if (storage != null) {
				tag.setInteger("type", ItemCardType.EU_RF);
				tag.setDouble("storage", storage.getEnergyStored());
				tag.setDouble("maxStorage", storage.getMaxEnergyStored());
				return tag;
			}
		}
		return null;
	}

	@Override
	public ItemStack getGeneratorCard(TileEntity te) {
		if (te instanceof TileDynamoBase) {
			ItemStack card = new ItemStack(ModItems.itemCard, 1, ItemCardType.CARD_GENERATOR);
			ItemStackHelper.setCoordinates(card, te.getPos());
			return card;
		}
		return ItemStack.EMPTY;
	}

	@Override
	public NBTTagCompound getGeneratorData(TileEntity te) {
		try {
			NBTTagCompound tag = new NBTTagCompound();
			tag.setString("euType", "RF");
			if (te instanceof TileDynamoBase) {
				tag.setInteger("type", 1);
				IEnergyStorage storage = ((TileDynamoBase)te).getEnergyStorage();
				tag.setDouble("storage", storage.getEnergyStored());
				tag.setDouble("maxStorage", storage.getMaxEnergyStored());
				int production = ((TileDynamoBase) te).getInfoEnergyPerTick();
				tag.setBoolean("active", ((TileDynamoBase) te).isActive);
				tag.setDouble("production", production);
				return tag;
			}
		} catch (Throwable t) { }
		return null;
	}

	@Override
	public List<IFluidTank> getAllTanks(TileEntity te) {
		List<IFluidTank> result = new ArrayList<>();
		if (te instanceof TileDynamoCompression) {
			IFluidTank tank0 = ((TileDynamoCompression) te).getTank(0);
			IFluidTank tank1 = ((TileDynamoCompression) te).getTank(1);
			if (tank0 != null && tank1 != null) {
				result.add(tank0);
				result.add(tank1);
				return result;
			}
		}
		if (te instanceof TileDynamoMagmatic) {
			IFluidTank tank0 = ((TileDynamoMagmatic) te).getTank(0);
			IFluidTank tank1 = ((TileDynamoMagmatic) te).getTank(1);
			if (tank0 != null && tank1 != null) {
				result.add(tank0);
				result.add(tank1);
				return result;
			}
		}
		if (te instanceof TileRefinery) {
			IFluidTank tank0 = ((TileRefinery) te).getTank(0);
			IFluidTank tank1 = ((TileRefinery) te).getTank(1);
			if (tank0 != null && tank1 != null) {
				result.add(tank0);
				result.add(tank1);
				return result;
			}
		}
		if (te instanceof TileBrewer) {
			IFluidTank tank0 = ((TileBrewer) te).getTank(0);
			IFluidTank tank1 = ((TileBrewer) te).getTank(1);
			if (tank0 != null && tank1 != null) {
				result.add(tank0);
				result.add(tank1);
				return result;
			}
		}
		if (te instanceof TileFluidBuffer) {
			IFluidTank tank0 = ((TileFluidBuffer) te).getTank(0);
			IFluidTank tank1 = ((TileFluidBuffer) te).getTank(1);
			IFluidTank tank2 = ((TileFluidBuffer) te).getTank(2);
			if (tank0 != null && tank1 != null && tank2 != null) {
				result.add(tank0);
				result.add(tank1);
				result.add(tank2);
				return result;
			}
		}
		if (te instanceof TileNameable) {
			IFluidTank tank = ((TileNameable) te).getTank();
			if (tank != null) {
				result.add(tank);
				return result;
			}
		}
		if (te instanceof TileDynamoBase) {
			IFluidTank tank = ((TileDynamoBase) te).getTank(0);
			if (tank != null) {
				result.add(tank);
				return result;
			}
		}
		return null;
	}
}
