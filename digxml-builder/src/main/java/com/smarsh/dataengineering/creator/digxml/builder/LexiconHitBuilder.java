package com.smarsh.dataengineering.creator.digxml.builder;

import com.smarsh.dataengineering.conversionsdk.builder.AbstractBuilder;
import com.smarsh.dataengineering.model.digxml.LexiconHit;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.math.BigInteger;
import java.util.Optional;

public class LexiconHitBuilder implements AbstractBuilder<LexiconHitBuilder, LexiconHit> {
    @NotNull
    private final String matchType;

    @Nullable
    private String value;
    @Nullable
    private BigInteger nearDistance;
    @Nullable
    private Boolean caseSensitive;
    @Nullable
    private Boolean plainText;
    @Nullable
    private Long offset;
    @Nullable
    private BigInteger length;
    @Nullable
    private String origin;
    @Nullable
    private String category;
    @Nullable
    private String policy;

    public LexiconHitBuilder(@NotNull String matchType) {
        this.matchType = matchType;
    }

    @Override
    public LexiconHit build() {
        var lexiconHit = new LexiconHit();

        lexiconHit.setMatchType(matchType);

        Optional.ofNullable(value).ifPresent(lexiconHit::setValue);
        Optional.ofNullable(nearDistance).ifPresent(lexiconHit::setNearDistance);
        Optional.ofNullable(caseSensitive).ifPresent(lexiconHit::setCaseSensitive);
        Optional.ofNullable(plainText).ifPresent(lexiconHit::setPlainText);
        Optional.ofNullable(offset).ifPresent(lexiconHit::setOffset);
        Optional.ofNullable(length).ifPresent(lexiconHit::setLength);
        Optional.ofNullable(origin).ifPresent(lexiconHit::setOrigin);
        Optional.ofNullable(category).ifPresent(lexiconHit::setCategory);
        Optional.ofNullable(policy).ifPresent(lexiconHit::setPolicy);

        return lexiconHit;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setNearDistance(BigInteger nearDistance) {
        this.nearDistance = nearDistance;
    }

    public void setCaseSensitive(Boolean caseSensitive) {
        this.caseSensitive = caseSensitive;
    }

    public void setPlainText(Boolean plainText) {
        this.plainText = plainText;
    }

    public void setOffset(Long offset) {
        this.offset = offset;
    }

    public void setLength(BigInteger length) {
        this.length = length;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setPolicy(String policy) {
        this.policy = policy;
    }
}
