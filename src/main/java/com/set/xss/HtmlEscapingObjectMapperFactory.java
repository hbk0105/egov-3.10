package com.set.xss;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.text.translate.AggregateTranslator;
import org.apache.commons.lang3.text.translate.CharSequenceTranslator;
import org.apache.commons.lang3.text.translate.EntityArrays;
import org.apache.commons.lang3.text.translate.LookupTranslator;
import org.springframework.beans.factory.FactoryBean;

import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.core.io.CharacterEscapes;
import com.fasterxml.jackson.core.io.SerializedString;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class HtmlEscapingObjectMapperFactory implements FactoryBean {
    private final ObjectMapper objectMapper;

    public HtmlEscapingObjectMapperFactory() {

        objectMapper = new ObjectMapper();

        objectMapper.getFactory().setCharacterEscapes(new HTMLCharacterEscapes());

        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    }

    @Override
    public ObjectMapper getObject() throws Exception {

        return objectMapper;

    }

    @Override
    public Class<?> getObjectType() {

        return ObjectMapper.class;

    }

    @Override
    public boolean isSingleton() {

        return true;

    }

    public static class HTMLCharacterEscapes extends CharacterEscapes {

        private final int[] asciiEscapes;

        private final CharSequenceTranslator translator;

        public HTMLCharacterEscapes() {

            // start with set of characters known to require escaping (double-quote,
            // backslash etc)

            asciiEscapes = CharacterEscapes.standardAsciiEscapesForJSON();

            // and force escaping of a few others:

            asciiEscapes['<'] = CharacterEscapes.ESCAPE_CUSTOM;

            asciiEscapes['>'] = CharacterEscapes.ESCAPE_CUSTOM;

            asciiEscapes['&'] = CharacterEscapes.ESCAPE_CUSTOM;

            asciiEscapes['"'] = CharacterEscapes.ESCAPE_CUSTOM;

            asciiEscapes['\''] = CharacterEscapes.ESCAPE_CUSTOM;

            //2.XSS방지처리특수문자인코딩값지정
            translator = new AggregateTranslator(
                    new LookupTranslator(EntityArrays.BASIC_ESCAPE()),//<,>,&,"는여기에포함됨
                    new LookupTranslator(EntityArrays.ISO8859_1_ESCAPE()),
                    new LookupTranslator(EntityArrays.HTML40_EXTENDED_ESCAPE()),
                    //여기에서커스터마이징가능
                    new LookupTranslator(
                            new String[][]{
                                    {"(","&#40;"},
                                    {")","&#41;"},
                                    {"#","&#35;"},
                                    {"\'","&#39;"}
                            }
                    )
            );

        }

        @Override
        public int[] getEscapeCodesForAscii() {

            return asciiEscapes;

        }

        // and this for others; we don't need anything special here
        // and this for others; we don't need anything special here
        @Override
        public SerializableString getEscapeSequence(int ch) {

            //return new SerializedString(StringEscapeUtils.escapeHtml4(Character.toString((char) ch)));

            SerializedString serializedString = null;
            char charAt = (char) ch;
            //emoji jackson parse 오류에 따른 예외 처리
            if (Character.isHighSurrogate(charAt) || Character.isLowSurrogate(charAt)) {
                StringBuilder sb = new StringBuilder();
                sb.append("\\u");
                sb.append(String.format("%04x",ch));
                serializedString = new SerializedString(sb.toString());
            } else {
                serializedString = new SerializedString(StringEscapeUtils.escapeHtml4(Character.toString(charAt)));
            }
            return serializedString;
        }
    }
}