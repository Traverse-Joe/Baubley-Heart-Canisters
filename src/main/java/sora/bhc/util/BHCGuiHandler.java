//package sora.bhc.util;
//
//import sora.bhc.gui.GuiPendant;
//import sora.bhc.gui.container.HeartPendantContainer;
//import net.minecraft.entity.player.EntityPlayer;
//import net.minecraft.util.EnumHand;
//import net.minecraft.world.World;
//import net.minecraftforge.fml.common.network.IGuiHandler;
//import net.minecraftforge.fml.relauncher.Side;
//import net.minecraftforge.fml.relauncher.SideOnly;
//
//import javax.annotation.Nullable;
//
///**
// * @author UpcraftLP
// */
//public class BHCGuiHandler implements IGuiHandler {
//
//    //mod-specific GUI IDs
//    public static final int PENDANT_GUI = 0;
//
//    @Nullable
//    @Override
//    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int handIndex, int unused, int unused1) {
//        EnumHand hand = EnumHand.values()[handIndex]; //small hack :)
//        switch (ID) {
//            case PENDANT_GUI:
//                return new HeartPendantContainer(player.getHeldItem(hand), player.inventory, hand);
//            default:
//                return null;
//        }
//    }
//
//    @SideOnly(Side.CLIENT)
//    @Nullable
//    @Override
//    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int handIndex, int unused, int unused1) {
//        EnumHand hand = EnumHand.values()[handIndex];
//        switch (ID) {
//            case PENDANT_GUI:
//            return new GuiPendant(new HeartPendantContainer(player.getHeldItem(hand), player.inventory, hand));
//            default:
//                return null;
//        }
//
//    }
//}
