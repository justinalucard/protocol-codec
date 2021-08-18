package io.github.justinalucard.protocalcodec.protocols;

public interface SerializeLengthDetermination {
    int getLength(LengthField lengthField);
}
