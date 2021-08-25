package io.github.justinalucard.protocolcodec.annotations;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.util.Set;

@SupportedAnnotationTypes({"io.github.justinalucard.protocolcodec.annotations.Protocol"})
public class ProtocolProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
//        Messager messager = processingEnv.getMessager();
//        messager.printMessage(Diagnostic.Kind.ERROR, "some error");
//        return true;
        return false;
    }
}
