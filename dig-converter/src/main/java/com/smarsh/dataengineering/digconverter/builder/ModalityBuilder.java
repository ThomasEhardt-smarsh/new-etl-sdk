package com.smarsh.dataengineering.digconverter.builder;

import com.smarsh.dataengineering.model.Modality;
import com.smarsh.dataengineering.model.ModalityClass;
import com.smarsh.dataengineering.model.ModalityType;
import com.smarsh.dataengineering.conversionsdk.builder.AbstractBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class ModalityBuilder implements AbstractBuilder<ModalityBuilder, Modality> {

    @NotNull
    private final String channel;
    @NotNull
    private final String network;

    @Nullable
    private ModalityClass classification;
    @Nullable
    private ModalityType type;
    @Nullable
    private String vendor;
    @Nullable
    private String description;

    public ModalityBuilder(@NotNull String channel, @NotNull String network) {
        this.channel = channel;
        this.network = network;
    }

    @Override
    public Modality build() {
        var modality = new Modality();

        modality.setChannel(channel);
        modality.setNetwork(network);

        Optional.ofNullable(classification).ifPresent(modality::setClassification);
        Optional.ofNullable(type).ifPresent(modality::setType);
        Optional.ofNullable(vendor).ifPresent(modality::setVendor);
        Optional.ofNullable(description).ifPresent(modality::setDescription);

        return modality;
    }

    public void setClassification(@NotNull ModalityClass classification) {
        this.classification = classification;
    }

    public void setType(@NotNull ModalityType type) {
        this.type = type;
    }

    public void setVendor(@NotNull String vendor) {
        this.vendor = vendor;
    }

    public void setDescription(@NotNull String description) {
        this.description = description;
    }
}
