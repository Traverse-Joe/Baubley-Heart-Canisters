package net.minecraft.client.renderer.entity.layers;

import com.mojang.authlib.GameProfile;
import com.mojang.blaze3d.matrix.MatrixStack;
import java.util.UUID;
import net.minecraft.block.AbstractSkullBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.IHasHead;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.SkullTileEntityRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.monster.ZombieVillagerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.SkullTileEntity;
import net.minecraft.util.Direction;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.commons.lang3.StringUtils;

@OnlyIn(Dist.CLIENT)
public class HeadLayer<T extends LivingEntity, M extends EntityModel<T> & IHasHead> extends LayerRenderer<T, M> {
   public HeadLayer(IEntityRenderer<T, M> p_i50946_1_) {
      super(p_i50946_1_);
   }

   public void func_225628_a_(MatrixStack p_225628_1_, IRenderTypeBuffer p_225628_2_, int p_225628_3_, T p_225628_4_, float p_225628_5_, float p_225628_6_, float p_225628_7_, float p_225628_8_, float p_225628_9_, float p_225628_10_) {
      ItemStack itemstack = p_225628_4_.getItemStackFromSlot(EquipmentSlotType.HEAD);
      if (!itemstack.isEmpty()) {
         Item item = itemstack.getItem();
         p_225628_1_.func_227860_a_();
         boolean flag = p_225628_4_ instanceof VillagerEntity || p_225628_4_ instanceof ZombieVillagerEntity;
         if (p_225628_4_.isChild() && !(p_225628_4_ instanceof VillagerEntity)) {
            float f = 2.0F;
            float f1 = 1.4F;
            p_225628_1_.func_227861_a_(0.0D, 0.03125D, 0.0D);
            p_225628_1_.func_227862_a_(0.7F, 0.7F, 0.7F);
            p_225628_1_.func_227861_a_(0.0D, 1.0D, 0.0D);
         }

         ((IHasHead)this.getEntityModel()).func_205072_a().func_228307_a_(p_225628_1_);
         if (item instanceof BlockItem && ((BlockItem)item).getBlock() instanceof AbstractSkullBlock) {
            float f3 = 1.1875F;
            p_225628_1_.func_227862_a_(1.1875F, -1.1875F, -1.1875F);
            if (flag) {
               p_225628_1_.func_227861_a_(0.0D, 0.0625D, 0.0D);
            }

            GameProfile gameprofile = null;
            if (itemstack.hasTag()) {
               CompoundNBT compoundnbt = itemstack.getTag();
               if (compoundnbt.contains("SkullOwner", 10)) {
                  gameprofile = NBTUtil.readGameProfile(compoundnbt.getCompound("SkullOwner"));
               } else if (compoundnbt.contains("SkullOwner", 8)) {
                  String s = compoundnbt.getString("SkullOwner");
                  if (!StringUtils.isBlank(s)) {
                     gameprofile = SkullTileEntity.updateGameProfile(new GameProfile((UUID)null, s));
                     compoundnbt.put("SkullOwner", NBTUtil.writeGameProfile(new CompoundNBT(), gameprofile));
                  }
               }
            }

            p_225628_1_.func_227861_a_(-0.5D, 0.0D, -0.5D);
            SkullTileEntityRenderer.func_228879_a_((Direction)null, 180.0F, ((AbstractSkullBlock)((BlockItem)item).getBlock()).getSkullType(), gameprofile, p_225628_5_, p_225628_1_, p_225628_2_, p_225628_3_);
         } else if (!(item instanceof ArmorItem) || ((ArmorItem)item).getEquipmentSlot() != EquipmentSlotType.HEAD) {
            float f2 = 0.625F;
            p_225628_1_.func_227861_a_(0.0D, -0.25D, 0.0D);
            p_225628_1_.func_227863_a_(Vector3f.field_229181_d_.func_229187_a_(180.0F));
            p_225628_1_.func_227862_a_(0.625F, -0.625F, -0.625F);
            if (flag) {
               p_225628_1_.func_227861_a_(0.0D, 0.1875D, 0.0D);
            }

            Minecraft.getInstance().getFirstPersonRenderer().func_228397_a_(p_225628_4_, itemstack, ItemCameraTransforms.TransformType.HEAD, false, p_225628_1_, p_225628_2_, p_225628_3_);
         }

         p_225628_1_.func_227865_b_();
      }
   }
}