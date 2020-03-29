package net.minecraft.client.renderer.tileentity;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.matrix.MatrixStack;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.model.ShulkerModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.crash.ReportedException;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TileEntityRendererDispatcher {
   private final Map<TileEntityType<?>, TileEntityRenderer<?>> renderers = Maps.newHashMap();
   public static final TileEntityRendererDispatcher instance = new TileEntityRendererDispatcher();
   private final BufferBuilder field_228849_g_ = new BufferBuilder(256);
   public FontRenderer fontRenderer;
   public TextureManager textureManager;
   public World world;
   public ActiveRenderInfo renderInfo;
   public RayTraceResult cameraHitResult;

   private TileEntityRendererDispatcher() {
      this.func_228854_a_(TileEntityType.SIGN, new SignTileEntityRenderer(this));
      this.func_228854_a_(TileEntityType.MOB_SPAWNER, new MobSpawnerTileEntityRenderer(this));
      this.func_228854_a_(TileEntityType.PISTON, new PistonTileEntityRenderer(this));
      this.func_228854_a_(TileEntityType.CHEST, new ChestTileEntityRenderer<>(this));
      this.func_228854_a_(TileEntityType.ENDER_CHEST, new ChestTileEntityRenderer<>(this));
      this.func_228854_a_(TileEntityType.TRAPPED_CHEST, new ChestTileEntityRenderer<>(this));
      this.func_228854_a_(TileEntityType.ENCHANTING_TABLE, new EnchantmentTableTileEntityRenderer(this));
      this.func_228854_a_(TileEntityType.LECTERN, new LecternTileEntityRenderer(this));
      this.func_228854_a_(TileEntityType.END_PORTAL, new EndPortalTileEntityRenderer<>(this));
      this.func_228854_a_(TileEntityType.END_GATEWAY, new EndGatewayTileEntityRenderer(this));
      this.func_228854_a_(TileEntityType.BEACON, new BeaconTileEntityRenderer(this));
      this.func_228854_a_(TileEntityType.SKULL, new SkullTileEntityRenderer(this));
      this.func_228854_a_(TileEntityType.BANNER, new BannerTileEntityRenderer(this));
      this.func_228854_a_(TileEntityType.STRUCTURE_BLOCK, new StructureTileEntityRenderer(this));
      this.func_228854_a_(TileEntityType.SHULKER_BOX, new ShulkerBoxTileEntityRenderer(new ShulkerModel(), this));
      this.func_228854_a_(TileEntityType.BED, new BedTileEntityRenderer(this));
      this.func_228854_a_(TileEntityType.CONDUIT, new ConduitTileEntityRenderer(this));
      this.func_228854_a_(TileEntityType.BELL, new BellTileEntityRenderer(this));
      this.func_228854_a_(TileEntityType.CAMPFIRE, new CampfireTileEntityRenderer(this));
   }

   private <E extends TileEntity> void func_228854_a_(TileEntityType<E> p_228854_1_, TileEntityRenderer<E> p_228854_2_) {
      this.renderers.put(p_228854_1_, p_228854_2_);
   }

   @Nullable
   public <E extends TileEntity> TileEntityRenderer<E> getRenderer(E tileEntityIn) {
      return (TileEntityRenderer<E>)this.renderers.get(tileEntityIn.getType());
   }

   public void func_217665_a(World p_217665_1_, TextureManager p_217665_2_, FontRenderer p_217665_3_, ActiveRenderInfo p_217665_4_, RayTraceResult p_217665_5_) {
      if (this.world != p_217665_1_) {
         this.setWorld(p_217665_1_);
      }

      this.textureManager = p_217665_2_;
      this.renderInfo = p_217665_4_;
      this.fontRenderer = p_217665_3_;
      this.cameraHitResult = p_217665_5_;
   }

   public <E extends TileEntity> void func_228850_a_(E p_228850_1_, float p_228850_2_, MatrixStack p_228850_3_, IRenderTypeBuffer p_228850_4_) {
      if (p_228850_1_.getDistanceSq(this.renderInfo.getProjectedView().x, this.renderInfo.getProjectedView().y, this.renderInfo.getProjectedView().z) < p_228850_1_.getMaxRenderDistanceSquared()) {
         TileEntityRenderer<E> tileentityrenderer = this.getRenderer(p_228850_1_);
         if (tileentityrenderer != null) {
            if (p_228850_1_.hasWorld() && p_228850_1_.getType().isValidBlock(p_228850_1_.getBlockState().getBlock())) {
               func_228853_a_(p_228850_1_, () -> {
                  func_228855_a_(tileentityrenderer, p_228850_1_, p_228850_2_, p_228850_3_, p_228850_4_);
               });
            }
         }
      }
   }

   private static <T extends TileEntity> void func_228855_a_(TileEntityRenderer<T> p_228855_0_, T p_228855_1_, float p_228855_2_, MatrixStack p_228855_3_, IRenderTypeBuffer p_228855_4_) {
      World world = p_228855_1_.getWorld();
      int i;
      if (world != null) {
         i = WorldRenderer.func_228421_a_(world, p_228855_1_.getPos());
      } else {
         i = 15728880;
      }

      p_228855_0_.func_225616_a_(p_228855_1_, p_228855_2_, p_228855_3_, p_228855_4_, i, OverlayTexture.field_229196_a_);
   }

   public <E extends TileEntity> boolean func_228852_a_(E p_228852_1_, MatrixStack p_228852_2_, IRenderTypeBuffer p_228852_3_, int p_228852_4_, int p_228852_5_) {
      TileEntityRenderer<E> tileentityrenderer = this.getRenderer(p_228852_1_);
      if (tileentityrenderer == null) {
         return true;
      } else {
         func_228853_a_(p_228852_1_, () -> {
            tileentityrenderer.func_225616_a_(p_228852_1_, 0.0F, p_228852_2_, p_228852_3_, p_228852_4_, p_228852_5_);
         });
         return false;
      }
   }

   private static void func_228853_a_(TileEntity p_228853_0_, Runnable p_228853_1_) {
      try {
         p_228853_1_.run();
      } catch (Throwable throwable) {
         CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Rendering Block Entity");
         CrashReportCategory crashreportcategory = crashreport.makeCategory("Block Entity Details");
         p_228853_0_.addInfoToCrashReport(crashreportcategory);
         throw new ReportedException(crashreport);
      }
   }

   public void setWorld(@Nullable World worldIn) {
      this.world = worldIn;
      if (worldIn == null) {
         this.renderInfo = null;
      }

   }

   public FontRenderer getFontRenderer() {
      return this.fontRenderer;
   }

   //Internal, Do not call Use ClientRegistry.
   public synchronized <T extends TileEntity> void setSpecialRendererInternal(TileEntityType<T> tileEntityType, TileEntityRenderer<? super T> specialRenderer) {
      this.renderers.put(tileEntityType, specialRenderer);
   }
}