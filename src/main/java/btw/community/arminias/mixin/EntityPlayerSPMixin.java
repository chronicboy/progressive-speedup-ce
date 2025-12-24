package btw.community.arminias.mixin;

import net.minecraft.src.EntityPlayerSP;
import net.minecraft.src.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EntityPlayerSP.class)
public abstract class EntityPlayerSPMixin extends net.minecraft.src.EntityPlayer {
    @Unique private float smoothTimerSpeed = 1.0F;
    @Shadow protected Minecraft mc;
    @Shadow protected abstract float updateGloomFOVMultiplier();

    public EntityPlayerSPMixin(net.minecraft.src.World w, String s) {
        super(w, s);
    }

    @Redirect(method = "getFOVMultiplier", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/EntityPlayerSP;updateGloomFOVMultiplier()F"))
    private float getFOVMultiplier(EntityPlayerSP self) {
        float fov = updateGloomFOVMultiplier();
        if (!sleeping && mc != null && mc.getTimer() != null) {
            float ts = mc.getTimer().timerSpeed;
            if (ts > 1.0F) {
                // Smoothly interpolate to current timer speed
                smoothTimerSpeed += (ts - smoothTimerSpeed) * 0.05F;
                // Zoom out (increase FOV) as timer speeds up - max 2x zoom out at 10x speed
                float zoomOutFactor = 1.0F + (smoothTimerSpeed - 1.0F) * 0.11F; // 1.0 at normal, ~2.0 at 10x speed
                fov *= zoomOutFactor;
            } else {
                // Reset smoothly when timer returns to normal
                smoothTimerSpeed += (1.0F - smoothTimerSpeed) * 0.1F;
            }
        }
        return fov;
    }
}
