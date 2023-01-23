package com.jbs.general.annotation;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
@IntDef({
        HTTPResponseStatusCode.CONTINUE,
        HTTPResponseStatusCode.SWITCHING_PROTOCOLS,
        HTTPResponseStatusCode.EARLY_HINTS,
        HTTPResponseStatusCode.OK,
        HTTPResponseStatusCode.CREATED,
        HTTPResponseStatusCode.ACCEPTED,
        HTTPResponseStatusCode.NON_AUTHORITATIVE_INFORMATION,
        HTTPResponseStatusCode.NO_CONTENT,
        HTTPResponseStatusCode.RESET_CONTENT,
        HTTPResponseStatusCode.PARTIAL_CONTENT,
        HTTPResponseStatusCode.MULTIPLE_CHOICES,
        HTTPResponseStatusCode.MOVED_PERMANENTLY,
        HTTPResponseStatusCode.FOUND,
        HTTPResponseStatusCode.SEE_OTHER,
        HTTPResponseStatusCode.NOT_MODIFIED,
        HTTPResponseStatusCode.TEMPORARY_REDIRECT,
        HTTPResponseStatusCode.PERMANENT_REDIRECT,
        HTTPResponseStatusCode.BAD_REQUEST,
        HTTPResponseStatusCode.UNAUTHORIZED,
        HTTPResponseStatusCode.PAYMENT_REQUIRED,
        HTTPResponseStatusCode.FORBIDDEN,
        HTTPResponseStatusCode.NOT_FOUND,
        HTTPResponseStatusCode.METHOD_NOT_ALLOWED,
        HTTPResponseStatusCode.NOT_ACCEPTABLE,
        HTTPResponseStatusCode.PROXY_AUTHENTICATION_REQUIRED,
        HTTPResponseStatusCode.REQUEST_TIMEOUT,
        HTTPResponseStatusCode.CONFLICT,
        HTTPResponseStatusCode.GONE,
        HTTPResponseStatusCode.LENGTH_REQUIRED,
        HTTPResponseStatusCode.PRECONDITION_FAILED,
        HTTPResponseStatusCode.PAYLOAD_TOO_LARGE,
        HTTPResponseStatusCode.URI_TOO_LONG,
        HTTPResponseStatusCode.UNSUPPORTED_MEDIA_TYPE,
        HTTPResponseStatusCode.RANGE_NOT_SATISFIABLE,
        HTTPResponseStatusCode.EXPECTATION_FAILED,
        HTTPResponseStatusCode.I_M_A_TEAPOT,
        HTTPResponseStatusCode.UNPROCESSABLE_ENTITIY,
        HTTPResponseStatusCode.TOO_EARLY,
        HTTPResponseStatusCode.UPGRADE_REQUIRED,
        HTTPResponseStatusCode.PRECONDITION_REQUIRED,
        HTTPResponseStatusCode.TOO_MANY_REQUESTS,
        HTTPResponseStatusCode.REQUEST_HEADER_FIELDS_TOO_LARGE,
        HTTPResponseStatusCode.UNAVAILABLE_FOR_LEGAL_REASONS,
        HTTPResponseStatusCode.INTERNAL_SERVER_ERROR,
        HTTPResponseStatusCode.NOT_IMPLEMENTED,
        HTTPResponseStatusCode.BAD_GATEWAY,
        HTTPResponseStatusCode.SERVICE_UNAVAILABLE,
        HTTPResponseStatusCode.GATEWAY_TIMEOUT,
        HTTPResponseStatusCode.HTTP_VERSION_NOT_SUPPORTED,
        HTTPResponseStatusCode.VARIANT_ALSO_NEGOTIATES,
        HTTPResponseStatusCode.INSUFFICIENT_STORAGE,
        HTTPResponseStatusCode.LOOP_DETECTED,
        HTTPResponseStatusCode.NOT_EXTENDED,
        HTTPResponseStatusCode.NETWORK_AUTHENTICATION_REQUIRED,
        HTTPResponseStatusCode.FAILURE
})
public @interface HTTPResponseStatusCode {
    int CONTINUE = 100;
    int SWITCHING_PROTOCOLS = 101;
    int EARLY_HINTS = 103;
    int OK = 200;
    int CREATED = 201;
    int ACCEPTED = 202;
    int NON_AUTHORITATIVE_INFORMATION = 203;
    int NO_CONTENT = 204;
    int RESET_CONTENT = 205;
    int PARTIAL_CONTENT = 206;
    int MULTIPLE_CHOICES = 300;
    int MOVED_PERMANENTLY = 301;
    int FOUND = 302;
    int SEE_OTHER = 303;
    int NOT_MODIFIED = 304;
    int TEMPORARY_REDIRECT = 307;
    int PERMANENT_REDIRECT = 308;
    int BAD_REQUEST = 400;
    int UNAUTHORIZED = 401;
    int PAYMENT_REQUIRED = 402;
    int FORBIDDEN = 403;
    int NOT_FOUND = 404;
    int METHOD_NOT_ALLOWED = 405;
    int NOT_ACCEPTABLE = 406;
    int PROXY_AUTHENTICATION_REQUIRED = 407;
    int REQUEST_TIMEOUT = 408;
    int CONFLICT = 409;
    int GONE = 410;
    int LENGTH_REQUIRED = 411;
    int PRECONDITION_FAILED = 412;
    int PAYLOAD_TOO_LARGE = 413;
    int URI_TOO_LONG = 414;
    int UNSUPPORTED_MEDIA_TYPE = 415;
    int RANGE_NOT_SATISFIABLE = 416;
    int EXPECTATION_FAILED = 417;
    int I_M_A_TEAPOT = 418;
    int UNPROCESSABLE_ENTITIY = 422;
    int TOO_EARLY = 425;
    int UPGRADE_REQUIRED = 426;
    int PRECONDITION_REQUIRED = 428;
    int TOO_MANY_REQUESTS = 429;
    int REQUEST_HEADER_FIELDS_TOO_LARGE = 431;
    int UNAVAILABLE_FOR_LEGAL_REASONS = 451;
    int INTERNAL_SERVER_ERROR = 500;
    int NOT_IMPLEMENTED = 501;
    int BAD_GATEWAY = 502;
    int SERVICE_UNAVAILABLE = 503;
    int GATEWAY_TIMEOUT = 504;
    int HTTP_VERSION_NOT_SUPPORTED = 505;
    int VARIANT_ALSO_NEGOTIATES = 506;
    int INSUFFICIENT_STORAGE = 507;
    int LOOP_DETECTED = 508;
    int NOT_EXTENDED = 510;
    int NETWORK_AUTHENTICATION_REQUIRED = 511;

    int FAILURE = 600; //CUSTOM CODE FROM RETHINKSOFT
}