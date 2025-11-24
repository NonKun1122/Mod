package com.nonkungch;

import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// ใช้ ClientModInitializer เพราะ Mod นี้ทำงานแค่ฝั่ง Client (ด้านการเรนเดอร์)
public class YourLagFixMod implements ClientModInitializer {
    
    // ตั้งค่า Logger สำหรับการดีบัก
    public static final String MOD_ID = "yourlagfixmod";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitializeClient() {
        // โค้ดส่วนใหญ่ของ Mod นี้อยู่ใน Mixin แล้ว
        // เราเพียงแค่ Log ข้อความยืนยันการโหลด
        LOGGER.info("Client Lag Fix Mod initialized! Enchantment Glint optimization is active.");
        
        // **NOTE:** // หากคุณต้องการใช้ Mod นี้ร่วมกับ Mod ประสิทธิภาพอื่นๆ (Sodium, Lithium) 
        // ตรวจสอบให้แน่ใจว่าได้ระบุ dependency ใน fabric.mod.json อย่างถูกต้อง
    }
}
