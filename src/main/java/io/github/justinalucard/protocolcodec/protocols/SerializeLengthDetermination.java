package io.github.justinalucard.protocolcodec.protocols;

public interface SerializeLengthDetermination {
    int getLength(LengthField lengthField);
}
