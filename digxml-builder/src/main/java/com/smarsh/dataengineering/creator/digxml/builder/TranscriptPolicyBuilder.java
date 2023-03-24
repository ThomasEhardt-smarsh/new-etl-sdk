package com.smarsh.dataengineering.creator.digxml.builder;

import com.smarsh.dataengineering.conversionsdk.builder.AbstractBuilder;
import com.smarsh.dataengineering.model.digxml.TranscriptPolicy;
import com.smarsh.dataengineering.model.digxml.TranscriptPolicy.Retention;
import com.smarsh.dataengineering.model.digxml.TranscriptPolicy.Retention.Policy;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TranscriptPolicyBuilder implements AbstractBuilder<TranscriptPolicyBuilder, TranscriptPolicy> {
    @NotNull
    private final Retention retention;
    @NotNull
    private final String disposition;

    public TranscriptPolicyBuilder(@NotNull Retention retention, @NotNull String disposition) {
        this.retention = retention;
        this.disposition = disposition;
    }

    @Override
    public TranscriptPolicy build() {
        var transcriptPolicy = new TranscriptPolicy();

        transcriptPolicy.setRetention(retention);
        transcriptPolicy.setDisposition(disposition);

        return transcriptPolicy;
    }

    public static class RetentionBuilder implements AbstractBuilder<RetentionBuilder, Retention> {

        private final List<Policy> policyList = new ArrayList<>();

        @Override
        public Retention build() {
            var retention = new Retention();

            retention.getPolicy().addAll(policyList);

            return retention;
        }

        public void addPolicy(@NotNull Policy policy) {
            policyList.add(policy);
        }
    }

    public static class PolicyBuilder implements AbstractBuilder<PolicyBuilder, Policy> {
        @NotNull
        private final String action;

        @Nullable
        private String description;
        @Nullable
        private String id;

        public PolicyBuilder(@NotNull String action) {
            this.action = action;
        }

        @Override
        public Policy build() {
            var policy = new Policy();

            policy.setAction(action);
            Optional.ofNullable(description).ifPresent(policy::setDecription);
            Optional.ofNullable(id).ifPresent(policy::setId);

            return policy;
        }

        public void setDescription(@NotNull String description) {
            this.description = description;
        }

        public void setId(@NotNull String id) {
            this.id = id;
        }
    }
}
