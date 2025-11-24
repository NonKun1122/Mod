package com.nonkungch;

import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// Mod นี้ใช้ ClientModInitializer เพราะทำงานด้านการเรนเดอร์ (Client-Side)
public class YourLagFixMod implements ClientModInitializer {
    
    // ตั้งค่า Mod ID และ Logger
    public static final String MOD_ID = "nonkungch";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitializeClient() {
        // ส่วนใหญ่ของตรรกะแก้ Lag อยู่ใน Mixin
        // เราเพียงแค่ Log ข้อความเพื่อยืนยันว่า Mod ถูกโหลดสำเร็จ
        LOGGER.info("NonkungCH Lag Fix Mod: Optimization for Inventory/Chest rendering initialized.");
    }
}
