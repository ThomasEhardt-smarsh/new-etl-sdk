package com.smarsh.dataengineering.digconverter.builder;

import com.smarsh.dataengineering.model.GeneralAttributes;
import com.smarsh.dataengineering.model.Interaction;
import com.smarsh.dataengineering.model.NetworkEndpoint;
import com.smarsh.dataengineering.model.UserInfo;
import com.smarsh.dataengineering.conversionsdk.builder.AbstractBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class ParticipantBuilder implements AbstractBuilder<ParticipantBuilder, Interaction.Participants.Participant> {
    @NotNull
    private final NetworkEndpoint networkInfo;

    @NotNull
    private final UserInfo userInfo;

    private final GeneralAttributes generalAttributes = new GeneralAttributes();

    @NotNull
    private final String participantId;

    @Nullable
    private String participantRole;

    public ParticipantBuilder(@NotNull NetworkEndpoint networkInfo, @NotNull UserInfo userInfo, @NotNull String participantId) {
        this.networkInfo = networkInfo;
        this.userInfo = userInfo;
        this.participantId = participantId;
    }

    public Interaction.Participants.Participant build() {
        var participant = new Interaction.Participants.Participant();
        participant.setNetworkInfo(networkInfo);
        participant.setUserInfo(userInfo);
        if (!generalAttributes.getAttribute().isEmpty()) {
            participant.setAttributes(generalAttributes);
        }
        participant.setParticipantId(participantId);
        Optional.ofNullable(participantRole).ifPresent(participant::setParticipantRole);

        return participant;
    }

    public void addGeneralAttribute(@NotNull GeneralAttributes.Attribute generalAttribute) {
        this.generalAttributes.getAttribute().add(generalAttribute);
    }

    public void setParticipantRole(@NotNull String participantRole) {
        this.participantRole = participantRole;
    }
}
