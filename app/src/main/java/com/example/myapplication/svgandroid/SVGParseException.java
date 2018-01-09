package com.example.myapplication.svgandroid;

/**
 * Runtime exception thrown when there is a problem parsing an SVGModel.
 *
 * @author Larva Labs, LLC
 */
public class SVGParseException extends RuntimeException {

    public SVGParseException(String s) {
        super(s);
    }

    public SVGParseException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public SVGParseException(Throwable throwable) {
        super(throwable);
    }
}
