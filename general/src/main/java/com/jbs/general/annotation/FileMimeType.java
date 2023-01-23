package com.jbs.general.annotation;

import androidx.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
@StringDef({
        FileMimeType.TEXT_HTML,
        FileMimeType.TEXT_PLAIN,
        FileMimeType.IMAGE_ALL

})
public @interface FileMimeType {
    String TEXT_HTML = "text/html";
    String TEXT_PLAIN = "text/plain";
    String IMAGE_ALL = "image/*";
}