package com.example.api.integrationtests.vo.wrappers;

import com.example.api.integrationtests.vo.BookEmbeddedVO;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class WrapperBookVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("_embedded")
    private BookEmbeddedVO embedded;

    public WrapperBookVO() {}

    public BookEmbeddedVO getEmbedded() {
        return embedded;
    }

    public void setEmbedded(BookEmbeddedVO embedded) {
        this.embedded = embedded;
    }
}