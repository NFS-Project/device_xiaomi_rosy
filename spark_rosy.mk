#
# Copyright (C) The LineageOS Project
#
# SPDX-License-Identifier: Apache-2.0
#

# Inherit from those products. Most specific first.
$(call inherit-product, $(SRC_TARGET_DIR)/product/core_64_bit.mk)
$(call inherit-product, $(SRC_TARGET_DIR)/product/full_base_telephony.mk)
$(call inherit-product, $(SRC_TARGET_DIR)/product/product_launched_with_n_mr1.mk)

# Inherit some common Spark-OS stuff.
$(call inherit-product, vendor/spark/config/common_full_phone.mk)

# Pixel customization
TARGET_SUPPORTS_GOOGLE_RECORDER := false
TARGET_INCLUDE_STOCK_ARCORE := false
TARGET_INCLUDE_LIVE_WALLPAPERS := false

# Inherit from rosy device
$(call inherit-product, device/xiaomi/rosy/device.mk)

# Spark-OS Propertie
PRODUCT_SYSTEM_PROPERTIES += \
ro.spark.device.name=Redmi 5 \
ro.spark.group.url=https://t.me/cri_grup \
ro.spark.maintainer=NFS-Project™ \
ro.spark.maintainer.username=MeiSitaL0mAnia

# Device identifier. This must come after all inclusions
PRODUCT_NAME := spark_rosy
PRODUCT_DEVICE := rosy
PRODUCT_BRAND := Xiaomi
PRODUCT_MODEL := Redmi 5
PRODUCT_MANUFACTURER := Xiaomi

# Boot animation
TARGET_BOOT_ANIMATION_RES := 720
TARGET_BOOTANIMATION_HALF_RES := true

# GApps
TARGET_GAPPS_ARCH := arm64

PRODUCT_GMS_CLIENTID_BASE := android-xiaomi
