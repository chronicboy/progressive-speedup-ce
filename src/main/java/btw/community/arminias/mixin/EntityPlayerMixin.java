package btw.community.arminias.mixin;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntityPlayerSP;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(EntityPlayer.class)
public abstract class EntityPlayerMixin {
    @Shadow private ItemStack itemInUse;
    @Shadow public net.minecraft.src.InventoryPlayer inventory;
    
    @Unique private static java.lang.reflect.Method setTimerMethod;
    @Unique private static java.lang.reflect.Method getTimerMethod;
    @Unique private static java.lang.reflect.Method resetTimerMethod;
    @Unique private static boolean methodsChecked = false;
    @Unique private static final float SPEED_INCREMENT = 0.013F;
    @Unique private static final float MAX_SPEED = 10.0F;

    @Inject(method = "onItemUseFinish", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/EntityPlayer;clearItemInUse()V"), locals = LocalCapture.CAPTURE_FAILEXCEPTION)
    private void onItemUseFinish(CallbackInfo ci, int var1, ItemStack var2) {
        resetTimer();
    }

    @Inject(method = "onUpdate", at = @At("HEAD"))
    private void onUpdate(CallbackInfo ci) {
        if (itemInUse == null) return;
        EntityPlayer p = (EntityPlayer)(Object)this;
        ItemStack curr = inventory.getCurrentItem();
        if (curr != itemInUse && (curr == null || curr.itemID != itemInUse.itemID)) {
            resetTimer();
        } else if (itemInUse.getItemDamage() > 0 && itemInUse.getMaxDamage() > 0) {
            speedUp(p);
        }
    }

    @Inject(method = "clearItemInUse", at = @At("HEAD"))
    private void clearItemInUse(CallbackInfo ci) {
        resetTimer();
    }

    @Unique
    private void speedUp(EntityPlayer p) {
        if (!methodsChecked) checkMethods();
        try {
            if (setTimerMethod != null && getTimerMethod != null) {
                float curr = (Float)getTimerMethod.invoke(p);
                setTimerMethod.invoke(p, Math.min(curr + SPEED_INCREMENT / Math.max(curr, 1.0F), MAX_SPEED));
                return;
            }
        } catch (Exception ignored) {}
        if (p instanceof EntityPlayerSP) {
            Minecraft mc = Minecraft.getMinecraft();
            if (mc != null && mc.getTimer() != null) {
                float curr = mc.getTimer().timerSpeed;
                mc.getTimer().timerSpeed = Math.min(curr + SPEED_INCREMENT / Math.max(curr, 1.0F), MAX_SPEED);
            }
        }
    }

    @Unique
    private void resetTimer() {
        EntityPlayer p = (EntityPlayer)(Object)this;
        if (!methodsChecked) checkMethods();
        try {
            if (resetTimerMethod != null) {
                resetTimerMethod.invoke(p);
                return;
            }
        } catch (Exception ignored) {}
        if (p instanceof EntityPlayerSP) {
            Minecraft mc = Minecraft.getMinecraft();
            if (mc != null && mc.getTimer() != null) mc.getTimer().timerSpeed = 1.0F;
        }
    }

    @Unique
    private static void checkMethods() {
        try {
            setTimerMethod = EntityPlayer.class.getMethod("setTimerSpeedModifier", float.class);
            getTimerMethod = EntityPlayer.class.getMethod("getTimerSpeedModifier");
            resetTimerMethod = EntityPlayer.class.getMethod("resetTimerSpeedModifier");
        } catch (NoSuchMethodException ignored) {}
        methodsChecked = true;
    }
}
