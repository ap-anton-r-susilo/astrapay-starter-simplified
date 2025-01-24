package com.astrapay.starter.snapbi.enums;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum SnapStatus {
    SUCCESSFUL(HttpStatus.OK, "00", "Successful"),
    REQUEST_IN_PROGRESS(HttpStatus.PROCESSING, "00", "Request In Progress"),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "00","Bad Request"),
    INVALID_FORMAT_FIELD(HttpStatus.BAD_REQUEST, "01", "Invalid Field Format"),

    // ada custom message sesuai service masing masing (liat docs)
    INVALID_MANDATORY_FIELD(HttpStatus.BAD_REQUEST, "02", "Invalid Mandatory Field "),


    // ada custom message sesuai service masing masing (liat docs)
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "00", "Unauthorized"),

    INVALID_TOKEN_B2B(HttpStatus.UNAUTHORIZED, "01", "Invalid Token (B2B)"),
    INVALID_CUSTOMER_TOKEN(HttpStatus.UNAUTHORIZED, "02", "Invalid Customer Token"),
    TOKEN_NOT_FOUND_B2B(HttpStatus.UNAUTHORIZED, "03", "Token Not Found (B2B)"),
    CUSTOMER_TOKEN_NOT_FOUND(HttpStatus.UNAUTHORIZED, "04", "Customer Token Not Found"),
    TRANSACTION_EXPIRED(HttpStatus.FORBIDDEN, "00", "Transaction Expired"),

    // ada custom message sesuai service masing masing (liat docs)
    FEATURE_NOT_ALLOWED(HttpStatus.FORBIDDEN, "01", "Feature Not Allowed"),

    EXCEEDS_TRANSACTION_AMOUNT_LIMIT(HttpStatus.FORBIDDEN, "02", "Exceeds Transaction Amount Limit"),
    SUSPECTED_FRAUD(HttpStatus.FORBIDDEN, "03", "Suspected Fraud"),
    ACTIVITY_COUNT_LIMIT_EXCEEDED(HttpStatus.FORBIDDEN, "04", "Activity Count Limit Exceeded"),
    DO_NOT_HONOR(HttpStatus.FORBIDDEN, "05", "Do Not Honor"),

    // ada custom message sesuai service masing masing (liat docs)
    FEATURE_NOT_ALLOWED_AT_THIS_TIME(HttpStatus.FORBIDDEN, "06", "Feature Not Allowed At This Time."),
    CARD_BLOCKED(HttpStatus.FORBIDDEN, "07", "Card Blocked"),
    CARD_EXPIRED(HttpStatus.FORBIDDEN, "08", "Card Expired"),
    DORMANT_ACCOUNT(HttpStatus.FORBIDDEN, "09", "Dormant Account"),
    NEED_TO_SET_TOKEN_LIMIT(HttpStatus.FORBIDDEN, "10", "Need To Set Token Limit"),
    OTP_BLOCKED(HttpStatus.FORBIDDEN, "11", "OTP Blocked"),
    OTP_LIFETIME_EXPIRED(HttpStatus.FORBIDDEN, "12", "OTP Lifetime Expired"),
    OTP_SENT_TO_CARD_HOLDER(HttpStatus.FORBIDDEN, "13", "OTP Sent To Cardholer"),
    INSUFFICIENT_FUNDS(HttpStatus.FORBIDDEN, "14", "Insufficient Funds"),

    // ada custom message sesuai service masing masing (liat docs)
    TRANSACTION_NOT_PERMITTED(HttpStatus.FORBIDDEN, "15", "Transaction Not Permitted"),

    SUSPEND_TRANSACTION(HttpStatus.FORBIDDEN, "16", "Suspend Transaction"),
    TOKEN_LIMIT_EXCEEDED(HttpStatus.FORBIDDEN, "17", "Token Limit Exceeded"),
    INACTIVE_CARD_ACCOUNT_CUSTOMER(HttpStatus.FORBIDDEN, "18", "Inactive Card Account Customer"),
    MERCHANT_BLACKLISTED(HttpStatus.FORBIDDEN, "19", "Merchant Blacklisted"),
    MERCHANT_LIMIT_EXCEEDED(HttpStatus.FORBIDDEN, "20", "Merchant Limit Exceeded"),
    SET_LIMIT_NOT_ALLOWED(HttpStatus.FORBIDDEN, "21", "Set Limit Not Allowed"),
    TOKEN_LIMIT_INVALID(HttpStatus.FORBIDDEN, "22", "Token Limit Invalid"),
    ACCOUNT_LIMIT_EXCEED(HttpStatus.FORBIDDEN, "23", "Account Limit Exceed"),



    INVALID_TRANSACTION_STATUS(HttpStatus.NOT_FOUND, "00", "Invalid Transaction Status"),
    TRANSACTION_NOT_FOUND(HttpStatus.NOT_FOUND, "01", "Transaction Not Found"),
    INVALID_ROUTING(HttpStatus.NOT_FOUND, "02", "Invalid Routing"),
    BANK_NOT_SUPPORTED_BY_SWITCH(HttpStatus.NOT_FOUND, "03", "Bank Not Supported By Switch"),
    TRANSACTION_CANCELLED(HttpStatus.NOT_FOUND, "04", "Transaction Cancelled"),
    MERCHANT_IS_NOT_REGISTERED_FOR_CARD(HttpStatus.NOT_FOUND, "05", "Merchant Is Not Registered For Card Registration Services"),
    NEED_TO_REQUEST_OTP(HttpStatus.NOT_FOUND, "06", "Need To Request OTP"),
    JOURNEY_NOT_FOUND(HttpStatus.NOT_FOUND, "07", "Journey Not Found"),
    INVALID_MERCHANT(HttpStatus.NOT_FOUND, "08", "Invalid Merchant"),
    NO_ISSUER(HttpStatus.NOT_FOUND, "09", "No Issuer"),
    INVALID_API_TRANSACTION(HttpStatus.NOT_FOUND, "10", "Invalid API Transaction"),


    // ada custom message sesuai service masing masing (liat docs)
    INVALID_BILL_VIRTUAL_ACCOUNT_WITH_REASON(HttpStatus.NOT_FOUND, "12", "Invalid Bill/Virtual Account"),
    INVALID_AMOUNT(HttpStatus.NOT_FOUND, "13", "Invalid Amount"),
    PAID_BILL(HttpStatus.NOT_FOUND, "14", "Paid Bill"),
    INVALID_OTP(HttpStatus.NOT_FOUND, "15", "Invalid OTP"),
    PARTNER_NOT_FOUND(HttpStatus.NOT_FOUND, "16", "Partner Not Found"),
    INVALID_TERMINAL(HttpStatus.NOT_FOUND, "17", "Invalid Terminal"),
    INCONSISTENT_REQUEST(HttpStatus.NOT_FOUND, "18", "Inconsistent Request"),
    INVALID_BILL_VIRTUAL_ACCOUNT(HttpStatus.NOT_FOUND, "19", "Invalid Bill/Virtual Account"),


    REQUESTED_FUNCTION_IS_NOT_SUPPORTED(HttpStatus.METHOD_NOT_ALLOWED, "00", "Requested Function Is Not Supported"),
    REQUESTED_OPERATION_IS_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "01", "Rquested Opearation Is Not Allowed"),

    CONFLICT(HttpStatus.CONFLICT, "00", "Conflict"),
    DUPLICATE_PARTNER_REFERENCE_NO(HttpStatus.CONFLICT, "01", "Duplicate partnerReferenceNo"),


    TOO_MANY_REQUESTS(HttpStatus.TOO_MANY_REQUESTS, "00", "Too Many Requests"),


    GENERAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "00", "General Error"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "01", "Internal Server Error"),
    EXTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "02", "External Server Error"),


    TIMEOUT(HttpStatus.GATEWAY_TIMEOUT, "00", "Timeout"),







    INVALID_COMBINED_SIGNATURE(HttpStatus.UNAUTHORIZED, "00", "Unauthorized. Invalid Client ID or Client Secret or Signature"),
    UNAUTHORIZED_SIGNATURE(HttpStatus.UNAUTHORIZED, "00", "Unauthorized. Signature"),



    MISSING_MANDATORY_FIELD(HttpStatus.BAD_REQUEST, "02", "Invalid Mandatory Field"),
    INVALID_PARTNER_ID(HttpStatus.NOT_FOUND, "16", "Partner Not Found");

    public final HttpStatus httpStatus;
    public final String caseCode;
    public final String message;

    SnapStatus(HttpStatus httpStatus, String caseCode, String message) {
        this.httpStatus = httpStatus;
        this.caseCode = caseCode;
        this.message = message;
    }

    public String generateResponseCode(String serviceCode) {
        return httpStatus.value() + serviceCode + caseCode;
    }
}
