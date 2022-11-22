package com.konnitiha.MCDiffusionMod.client.Packet;

import com.konnitiha.MCDiffusionMod.entity.ImagePaintingEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.PacketUtils;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.resources.ResourceLocation;

import java.util.UUID;

public class ClientboundAddScreenPacket implements Packet<ClientGamePacketListener> {

    private final int id;
    private final UUID uuid;
    private final BlockPos pos;
    private final Direction direction;

    private final ResourceLocation imageLocation;

    public ClientboundAddScreenPacket(ImagePaintingEntity imagePaintingEntity) {
        this.id = imagePaintingEntity.getId();
        this.uuid = imagePaintingEntity.getUUID();
        this.pos = imagePaintingEntity.getPos();
        this.direction = imagePaintingEntity.getDirection();
        this.imageLocation = imagePaintingEntity.imageLocation;
    }

    public ClientboundAddScreenPacket(FriendlyByteBuf byteBuf) {
        this.id = byteBuf.readVarInt();
        this.uuid = byteBuf.readUUID();
        this.imageLocation = byteBuf.readResourceLocation();
        this.pos = byteBuf.readBlockPos();
        this.direction = Direction.from2DDataValue(byteBuf.readUnsignedByte());
    }

    public void write(FriendlyByteBuf p_131582_) {
        p_131582_.writeVarInt(this.id);
        p_131582_.writeUUID(this.uuid);
        p_131582_.writeResourceLocation(this.imageLocation);
        p_131582_.writeBlockPos(this.pos);
        p_131582_.writeByte(this.direction.get2DDataValue());
    }

    @Override
    public void handle(ClientGamePacketListener packetListener) {
        ClientLevel level = Minecraft.getInstance().level;
        PacketUtils.ensureRunningOnSameThread(this, packetListener, Minecraft.getInstance());
        ImagePaintingEntity imagePaintingEntity = new ImagePaintingEntity(level, this.getPos(), this.getDirection(), this.getImageLocation());
        imagePaintingEntity.setId(this.getId());
        imagePaintingEntity.setUUID(this.getUuid());
        level.putNonPlayerEntity(this.getId(), imagePaintingEntity);
        Minecraft.getInstance().gui.getChat().addMessage(new TextComponent("aa"));
    }

    public int getId() {
        return id;
    }

    public Direction getDirection() {
        return direction;
    }

    public UUID getUuid() {
        return uuid;
    }

    public BlockPos getPos() {
        return pos;
    }

    public ResourceLocation getImageLocation() {
        return imageLocation;
    }
}
