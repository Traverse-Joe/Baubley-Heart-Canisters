package net.minecraft.client.gui.screen;

import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import java.net.IDN;
import java.util.function.Predicate;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.StringUtils;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class AddServerScreen extends Screen {
   private Button field_195179_a;
   private final BooleanConsumer field_213032_b;
   private final ServerData serverData;
   private TextFieldWidget field_146308_f;
   private TextFieldWidget field_146309_g;
   private Button field_152176_i;
   private final Screen field_228179_g_;
   private final Predicate<String> addressFilter = (p_210141_0_) -> {
      if (StringUtils.isNullOrEmpty(p_210141_0_)) {
         return true;
      } else {
         String[] astring = p_210141_0_.split(":");
         if (astring.length == 0) {
            return true;
         } else {
            try {
               String s = IDN.toASCII(astring[0]);
               return true;
            } catch (IllegalArgumentException var3) {
               return false;
            }
         }
      }
   };

   public AddServerScreen(Screen p_i225927_1_, BooleanConsumer p_i225927_2_, ServerData p_i225927_3_) {
      super(new TranslationTextComponent("addServer.title"));
      this.field_228179_g_ = p_i225927_1_;
      this.field_213032_b = p_i225927_2_;
      this.serverData = p_i225927_3_;
   }

   public void tick() {
      this.field_146309_g.tick();
      this.field_146308_f.tick();
   }

   protected void init() {
      this.minecraft.keyboardListener.enableRepeatEvents(true);
      this.field_146309_g = new TextFieldWidget(this.font, this.width / 2 - 100, 66, 200, 20, I18n.format("addServer.enterName"));
      this.field_146309_g.setFocused2(true);
      this.field_146309_g.setText(this.serverData.serverName);
      this.field_146309_g.func_212954_a(this::func_213028_a);
      this.children.add(this.field_146309_g);
      this.field_146308_f = new TextFieldWidget(this.font, this.width / 2 - 100, 106, 200, 20, I18n.format("addServer.enterIp"));
      this.field_146308_f.setMaxStringLength(128);
      this.field_146308_f.setText(this.serverData.serverIP);
      this.field_146308_f.setValidator(this.addressFilter);
      this.field_146308_f.func_212954_a(this::func_213028_a);
      this.children.add(this.field_146308_f);
      this.field_152176_i = this.addButton(new Button(this.width / 2 - 100, this.height / 4 + 72, 200, 20, I18n.format("addServer.resourcePack") + ": " + this.serverData.getResourceMode().getMotd().getFormattedText(), (p_213031_1_) -> {
         this.serverData.setResourceMode(ServerData.ServerResourceMode.values()[(this.serverData.getResourceMode().ordinal() + 1) % ServerData.ServerResourceMode.values().length]);
         this.field_152176_i.setMessage(I18n.format("addServer.resourcePack") + ": " + this.serverData.getResourceMode().getMotd().getFormattedText());
      }));
      this.field_195179_a = this.addButton(new Button(this.width / 2 - 100, this.height / 4 + 96 + 18, 200, 20, I18n.format("addServer.add"), (p_213030_1_) -> {
         this.func_195172_h();
      }));
      this.addButton(new Button(this.width / 2 - 100, this.height / 4 + 120 + 18, 200, 20, I18n.format("gui.cancel"), (p_213029_1_) -> {
         this.field_213032_b.accept(false);
      }));
      this.func_228180_b_();
   }

   public void resize(Minecraft p_resize_1_, int p_resize_2_, int p_resize_3_) {
      String s = this.field_146308_f.getText();
      String s1 = this.field_146309_g.getText();
      this.init(p_resize_1_, p_resize_2_, p_resize_3_);
      this.field_146308_f.setText(s);
      this.field_146309_g.setText(s1);
   }

   private void func_213028_a(String p_213028_1_) {
      this.func_228180_b_();
   }

   public void removed() {
      this.minecraft.keyboardListener.enableRepeatEvents(false);
   }

   private void func_195172_h() {
      this.serverData.serverName = this.field_146309_g.getText();
      this.serverData.serverIP = this.field_146308_f.getText();
      this.field_213032_b.accept(true);
   }

   public void onClose() {
      this.func_228180_b_();
      this.minecraft.displayGuiScreen(this.field_228179_g_);
   }

   private void func_228180_b_() {
      String s = this.field_146308_f.getText();
      boolean flag = !s.isEmpty() && s.split(":").length > 0 && s.indexOf(32) == -1;
      this.field_195179_a.active = flag && !this.field_146309_g.getText().isEmpty();
   }

   public void render(int p_render_1_, int p_render_2_, float p_render_3_) {
      this.renderBackground();
      this.drawCenteredString(this.font, this.title.getFormattedText(), this.width / 2, 17, 16777215);
      this.drawString(this.font, I18n.format("addServer.enterName"), this.width / 2 - 100, 53, 10526880);
      this.drawString(this.font, I18n.format("addServer.enterIp"), this.width / 2 - 100, 94, 10526880);
      this.field_146309_g.render(p_render_1_, p_render_2_, p_render_3_);
      this.field_146308_f.render(p_render_1_, p_render_2_, p_render_3_);
      super.render(p_render_1_, p_render_2_, p_render_3_);
   }
}