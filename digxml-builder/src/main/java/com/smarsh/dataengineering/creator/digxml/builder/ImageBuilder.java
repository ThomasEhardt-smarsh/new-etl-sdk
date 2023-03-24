package com.smarsh.dataengineering.creator.digxml.builder;

import com.smarsh.dataengineering.conversionsdk.builder.AbstractBuilder;
import com.smarsh.dataengineering.model.digxml.UserInfo.Image;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class ImageBuilder implements AbstractBuilder<ImageBuilder, Image> {
    @NotNull
    private String type;

    @Nullable
    private byte[] value;

    public ImageBuilder(@NotNull String type) {
        this.type = type;
    }

    @Override
    public Image build() {
        var image = new Image();

        image.setType(type);

        Optional.ofNullable(value).ifPresent(image::setValue);

        return image;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setValue(byte[] value) {
        this.value = value;
    }
}
