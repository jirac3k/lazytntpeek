package com.example.lazytntpeek.mixin;

import com.example.lazytntpeek.LazyTntPeekClient;
import net.minecraft.world.entity.item.PrimedTnt;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * The client normally "predicts" primed TNT: it applies gravity/drag locally and counts the
 * fuse down itself, discarding the entity at 0. In lazy chunks the server isn't ticking the
 * TNT, so that prediction is wrong (wrong physics, entity vanishes after 80 gt).
 * While enabled, we skip the prediction and just show what the server last told us.
 */
@Mixin(PrimedTnt.class)
public abstract class PrimedTntMixin {
    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    private void lazytntpeek$skipClientPrediction(CallbackInfo ci) {
        PrimedTnt self = (PrimedTnt) (Object) this;
        if (LazyTntPeekClient.enabled && self.level().isClientSide()) {
            self.baseTick(); // keeps old-pos bookkeeping so rendering interpolation stays sane
            ci.cancel();
        }
    }
}
