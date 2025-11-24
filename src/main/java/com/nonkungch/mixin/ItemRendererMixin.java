package com.nonkungch.mixin;

import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

// Mixin เข้าสู่คลาส ItemRenderer ซึ่งทำหน้าที่วาดไอเทมในเกม
@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin {

    /**
     * @reason Optimizes performance by forcing the renderGlint parameter to false 
     * when an item is rendered in the GUI (Inventory, Chests).
     * * เราใช้ ModifyArgs เพื่อแก้ไขพารามิเตอร์ของเมธอด renderItem ก่อนที่มันจะถูกส่งต่อไปยัง
     * เมธอดที่วาด Glint โดยตรง ซึ่งเป็นสาเหตุหลักของ Lag ในหน้าจอที่มีไอเทม Enchanted เยอะๆ
     */
    @ModifyArgs(
        // เมธอดที่เราต้องการแก้ไข: renderItem(ItemStack, ModelTransformationMode, boolean, ...)
        method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformationMode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IILnet/minecraft/client/render/model/BakedModel;)V",
        at = @At(
            value = "INVOKE",
            // จุดแทรก: ก่อนที่จะมีการเรียกเมธอดที่ใช้สำหรับวาด Glint
            target = "Lnet/minecraft/client/render/item/ItemRenderer;renderGlint(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/item/ItemStack;I)V"
        )
    )
    private void removeGlintOnGui(Args args) {
        
        // ดึงพารามิเตอร์ที่ถูกส่งเข้ามาในเมธอด renderItem
        ItemStack stack = args.get(0);              // พารามิเตอร์ตัวที่ 1 (ItemStack)
        ModelTransformationMode renderMode = args.get(1); // พารามิเตอร์ตัวที่ 2 (Render Mode)
        boolean renderGlint = args.get(2);          // พารามิเตอร์ตัวที่ 3 (Boolean for Glint)
        
        // ตรรกะ: ถ้า Glint ถูกเปิดใช้งาน (true) AND ไอเทมมีการร่ายมนตร์ AND โหมดคือ GUI
        if (renderGlint && stack.hasGlint() && renderMode == ModelTransformationMode.GUI) {
            // บังคับเปลี่ยนค่า renderGlint (พารามิเตอร์ตัวที่ 3) ให้เป็น false 
            // ทำให้ Glint ไม่ถูกวาดเมื่ออยู่ใน Inventory/Chest
            args.set(2, false); 
        }
    }
}
