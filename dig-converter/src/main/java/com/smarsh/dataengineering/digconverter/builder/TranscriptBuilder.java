package com.smarsh.dataengineering.digconverter.builder;

import com.smarsh.dataengineering.model.Interaction;
import com.smarsh.dataengineering.model.Transcript;
import com.smarsh.dataengineering.model.TranscriptPolicy;
import com.smarsh.dataengineering.conversionsdk.builder.AbstractBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.xml.datatype.XMLGregorianCalendar;
import java.util.Optional;

public class TranscriptBuilder implements AbstractBuilder<TranscriptBuilder, Transcript> {

    @NotNull
    private final Interaction interaction;

    @Nullable
    private TranscriptPolicy transcriptPolicy;

    @NotNull
    private final String id;
    @NotNull
    private final String sourceEndpointId;
    @Nullable
    private String sourceEndpointVersion;

    @NotNull
    private final XMLGregorianCalendar timeStamp;


    public TranscriptBuilder(@NotNull Interaction interaction, @NotNull String id, @NotNull String sourceEndpointId, @NotNull XMLGregorianCalendar timeStamp) {
        this.interaction = interaction;
        this.id = id;
        this.sourceEndpointId = sourceEndpointId;
        this.timeStamp = timeStamp;
    }

    public Transcript build() {
        var transcript = new Transcript();

        transcript.setInteraction(interaction);

        Optional.ofNullable(transcriptPolicy).ifPresent(transcript::setPolicy);

        transcript.setId(id);

        transcript.setSourceEndpointId(sourceEndpointId);

        Optional.ofNullable(sourceEndpointVersion).ifPresent(transcript::setSourceEndpointVersion);

        transcript.setTimeStamp(timeStamp);

        return transcript;
    }

    public void setTranscriptPolicy(@NotNull TranscriptPolicy transcriptPolicy) {
        this.transcriptPolicy = transcriptPolicy;
    }

    public void setSourceEndpointVersion(@NotNull String sourceEndpointVersion) {
        this.sourceEndpointVersion = sourceEndpointVersion;
    }
}
