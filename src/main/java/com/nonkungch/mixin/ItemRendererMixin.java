package com.yourmodid.mixin;

import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin {

    /**
     * @author Gemini (Based on community performance mods)
     * @reason ปรับปรุงประสิทธิภาพโดยบังคับให้ค่า renderGlint เป็น false 
     * เมื่อไอเทมถูกเรนเดอร์ในหน้าจอ GUI/Inventory (โหมด GUI)
     */
    @ModifyArgs(
        method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformationMode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IILnet/minecraft/client/render/model/BakedModel;)V",
        at = @At(
            value = "INVOKE",
            // จุดที่ Glint ถูกวาด (ใช้การเรียกเมธอด renderGlint เป็นจุดแทรก)
            target = "Lnet/minecraft/client/render/item/ItemRenderer;renderGlint(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/item/ItemStack;I)V"
        )
    )
    private void removeGlintOnGui(Args args) {
        // พารามิเตอร์ของเมธอด renderItem ที่เราแก้ไข:
        // Index 0: ItemStack stack
        // Index 1: ModelTransformationMode renderMode
        // Index 2: boolean renderGlint (ค่าที่เราต้องการเปลี่ยน)
        
        ItemStack stack = args.get(0);
        ModelTransformationMode renderMode = args.get(1);
        boolean renderGlint = args.get(2);
        
        // ตรวจสอบ: ถ้า Glint ถูกเปิดใช้งานอยู่ (renderGlint == true) 
        //         และไอเทมมี Glint (stack.hasGlint()) 
        //         และเรากำลังเรนเดอร์ในโหมด GUI (Inventory, Chests)
        if (renderGlint && stack.hasGlint() && renderMode == ModelTransformationMode.GUI) {
            // บังคับเปลี่ยนค่า renderGlint (พารามิเตอร์ตัวที่ 3) ให้เป็น false
            args.set(2, false); 
        }
    }
}
