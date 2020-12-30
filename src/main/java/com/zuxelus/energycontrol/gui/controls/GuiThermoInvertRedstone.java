package com.zuxelus.energycontrol.gui.controls;

import org.lwjgl.opengl.GL11;

import com.zuxelus.energycontrol.EnergyControl;
import com.zuxelus.energycontrol.tileentities.TileEntityThermo;
import com.zuxelus.zlib.network.NetworkHelper;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class GuiThermoInvertRedstone extends GuiButton {
	private static final ResourceLocation TEXTURE = new ResourceLocation(
			EnergyControl.MODID + ":textures/gui/gui_thermal_monitor.png");

	TileEntityThermo thermo;
	private boolean checked;

	public GuiThermoInvertRedstone(int id, int x, int y, TileEntityThermo thermo) {
		super(id, x, y, 0, 0, "");
		height = 15;
		width = 51;
		this.thermo = thermo;
		checked = thermo.getInvertRedstone();
	}

	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY) {
		if (!visible)
			return;

		mc.getTextureManager().bindTexture(TEXTURE);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		int delta = checked ? 15 : 0;
		drawTexturedModalRect(xPosition, yPosition + 1, 199, delta, 51, 15);
	}

	@Override
	public int getHoverState(boolean flag) {
		return 0;
	}

	@Override
	public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
		if (!super.mousePressed(mc, mouseX, mouseY))
			return false;
		checked = !checked;
		if (thermo.getWorldObj().isRemote && thermo.getInvertRedstone() != checked) {
			NetworkHelper.updateSeverTileEntity(thermo.xCoord, thermo.yCoord, thermo.zCoord, 2, checked ? (int) 1 : (int) 0);
			thermo.setInvertRedstone(checked);
		}
		return true;
	}
}
