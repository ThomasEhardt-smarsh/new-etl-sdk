package com.smarsh.dataengineering.digconverter.builder;

import com.smarsh.dataengineering.model.Groups;
import com.smarsh.dataengineering.model.NetworkEndpoint;
import com.smarsh.dataengineering.conversionsdk.builder.AbstractBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class NetworkEndpointBuilder implements AbstractBuilder<NetworkEndpointBuilder, NetworkEndpoint> {
    @NotNull
    private final String network;

    @NotNull
    private final String endpointId;

    @Nullable
    private String displayName;

    private final Groups groups = new Groups();

    @Nullable
    private String endpointIdType;

    public NetworkEndpointBuilder(@NotNull String network, @NotNull String endpointId) {
        this.network = network;
        this.endpointId = endpointId;
    }

    @Override
    public NetworkEndpoint build() {
        NetworkEndpoint networkEndpoint = new NetworkEndpoint();

        networkEndpoint.setNetwork(network);
        networkEndpoint.setEndpointId(endpointId);

        Optional.ofNullable(displayName).ifPresent(networkEndpoint::setDisplayName);

        if (!groups.getGroup().isEmpty()) {
            networkEndpoint.setGroups(groups);
        }

        Optional.ofNullable(endpointIdType).ifPresent(networkEndpoint::setEndpointIdType);

        return networkEndpoint;
    }

    public void setDisplayName(@NotNull String displayName) {
        this.displayName = displayName;
    }

    public void addGroup(@NotNull String group) {
        // TODO: check for duplicates?
        this.groups.getGroup().add(group);
    }

    public void setEndpointIdType(@NotNull String endpointIdType) {
        this.endpointIdType = endpointIdType;
    }
}
