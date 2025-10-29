package io.github.savatware.mapstruct.addons.spring.processor;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.SourceVersion;
import java.time.ZonedDateTime;

public abstract class AbstractAddonProcessor extends AbstractProcessor {

    private ZonedDateTime now;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    public ZonedDateTime getDateTime() {
        if (now == null) {
            now = ZonedDateTime.now();
        }
        return now;
    }

    public void setDateTime(ZonedDateTime dateTime) {
        now = dateTime;
    }

}
