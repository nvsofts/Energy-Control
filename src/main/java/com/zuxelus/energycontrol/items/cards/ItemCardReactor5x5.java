package com.zuxelus.energycontrol.items.cards;

import com.zuxelus.energycontrol.api.CardState;
import com.zuxelus.energycontrol.api.ICardReader;
import com.zuxelus.energycontrol.api.PanelSetting;
import com.zuxelus.energycontrol.api.PanelString;
import com.zuxelus.energycontrol.crossmod.CrossModLoader;
import com.zuxelus.energycontrol.crossmod.ModIDs;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

public class ItemCardReactor5x5 extends ItemCardBase {
	public ItemCardReactor5x5() {
		super(ItemCardType.CARD_REACTOR5X5, "card_reactor_5x5");
	}

	@Override
	public CardState update(World world, ICardReader reader, int range, BlockPos pos) {
		BlockPos target = reader.getTarget();
		if (target == null) 
			return CardState.NO_TARGET;

		return CrossModLoader.getCrossMod(ModIDs.IC2).updateCardReactor5x5(world, reader, target);
	}

	@Override
	public List<PanelString> getStringData(int settings, ICardReader reader, boolean isServer, boolean showLabels) {
		List<PanelString> result = reader.getTitleList();
		if ((settings & 2) > 0)
			addHeat(result, reader.getInt("heat"), reader.getInt("maxHeat"), showLabels);
		if ((settings & 4) > 0)
			result.add(new PanelString("msg.ec.InfoPanelMaxHeat", reader.getInt("maxHeat"), showLabels));
		if ((settings & 8) > 0)
			result.add(new PanelString("msg.ec.InfoPanelMelting", reader.getInt("maxHeat") * 85 / 100, showLabels));
		if ((settings & 16) > 0)
			result.add(new PanelString("msg.ec.InfoPanelOutputHeat", reader.getInt("output"), showLabels));
		int timeLeft = reader.getInt("timeLeft");
		if ((settings & 32) > 0) {
			int hours = timeLeft / 3600;
			int minutes = (timeLeft % 3600) / 60;
			int seconds = timeLeft % 60;
			result.add(new PanelString("msg.ec.InfoPanelTimeRemaining", String.format("%d:%02d:%02d", hours, minutes, seconds), showLabels));
		}

		if ((settings & 1) > 0)
			addOnOff(result, isServer, reader.getBoolean("reactorPoweredB"));
		return result;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public List<PanelSetting> getSettingsList() {
		List<PanelSetting> result = new ArrayList<>(6);
		result.add(new PanelSetting(I18n.format("msg.ec.cbInfoPanelOnOff"), 1, damage));
		result.add(new PanelSetting(I18n.format("msg.ec.cbInfoPanelHeat"), 2, damage));
		result.add(new PanelSetting(I18n.format("msg.ec.cbInfoPanelMaxHeat"), 4, damage));
		result.add(new PanelSetting(I18n.format("msg.ec.cbInfoPanelMelting"), 8, damage));
		result.add(new PanelSetting(I18n.format("msg.ec.cbInfoPanelOutput"), 16, damage));
		result.add(new PanelSetting(I18n.format("msg.ec.cbInfoPanelTimeRemaining"), 32, damage));
		return result;
	}

	@Override
	public int getKitFromCard() {
		return ItemCardType.KIT_REACTOR;
	}
}
