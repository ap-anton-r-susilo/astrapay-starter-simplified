package com.astrapay.starter.snapbi.enums;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;

@Getter
public enum SnapBiResponseCode {
    SUCCESSFUL(ResponseCodeCategory.SUCCESS.getCategory(), HttpStatus.OK.value(), "00", "Successful", "Successful",false),
    REQUEST_IN_PROGRESS(ResponseCodeCategory.SUCCESS.getCategory(), HttpStatus.ACCEPTED.value(), "00", "Request In Progress", "Transaction still on process",false),
    BAD_REQUEST(ResponseCodeCategory.SYSTEM.getCategory(), HttpStatus.BAD_REQUEST.value(), "00", "Bad Request", "General request failed error, including message parsing failed.",false),
    INVALID_FIELD_FORMAT(ResponseCodeCategory.MESSAGE.getCategory(), HttpStatus.BAD_REQUEST.value(), "01","Invalid Field Format","Invalid format",true),
    INVALID_MANDATORY_FIELD(ResponseCodeCategory.MESSAGE.getCategory(), HttpStatus.BAD_REQUEST.value(), "02","Invalid Mandatory Field","Missing or invalid format on mandatory field",true),
    UNAUTHORIZED(ResponseCodeCategory.SYSTEM.getCategory(), HttpStatus.UNAUTHORIZED.value(), "00", "Unauthorized", "General unauthorized error (No Interface Def, API is Invalid, Oauth Failed, Verify Client Secret Fail, Client Forbidden Access API, Unknown Client, Key not Found)",false),
    INVALID_TOKEN_B2B(ResponseCodeCategory.SYSTEM.getCategory(),HttpStatus.UNAUTHORIZED.value(), "01", "Invalid Token (B2B)", "Token found in request is invalid (Access Token Not Exist, Access Token Expiry)",false),
    INVALID_CUSTOMER_TOKEN(ResponseCodeCategory.SYSTEM.getCategory(), HttpStatus.UNAUTHORIZED.value(), "02", "Invalid Customer Token", "Token found in request is invalid (Access Token Not Exist, Access Token Expiry)",false),
    TOKEN_NOT_FOUND_B2B(ResponseCodeCategory.SYSTEM.getCategory(), HttpStatus.UNAUTHORIZED.value(), "03", "Token Not Found (B2B)", "Token not found in the system. This occurs on any API that requires token as input parameter",false),
    CUSTOMER_TOKEN_NOT_FOUND(ResponseCodeCategory.SYSTEM.getCategory(), HttpStatus.UNAUTHORIZED.value(), "04", "Customer Token Not Found", "Token not found in the system. This occurs on any API that requires token as input parameter",false),
    TRANSACTION_EXPIRED(ResponseCodeCategory.BUSINESS.getCategory(), HttpStatus.FORBIDDEN.value(), "00", "Transaction Expired", "Transaction expired",false),
    FEATURE_NOT_ALLOWED(ResponseCodeCategory.SYSTEM.getCategory(), HttpStatus.FORBIDDEN.value(), "01", 	"Feature Not Allowed", "This merchant is not allowed to call Direct Debit APIs",false),
    EXCEEDS_TRANSACTION_AMOUNT_LIMIT(ResponseCodeCategory.BUSINESS.getCategory(), HttpStatus.FORBIDDEN.value(), "02", "Exceeds Transaction Amount Limit", "Exceeds Transaction Amount Limit",false),
    SUSPECTED_FRAUD(ResponseCodeCategory.BUSINESS.getCategory(),HttpStatus.FORBIDDEN.value(), "03", "Suspected Fraud", "Suspected Fraud",false),
    ACTIVITY_COUNT_LIMIT_EXCEEDED(ResponseCodeCategory.BUSINESS.getCategory(), HttpStatus.FORBIDDEN.value(), "04","Activity Count Limit Exceeded", "Too many request, Exceeds Transaction Frequency Limit",false),
    DO_NOT_HONOR(ResponseCodeCategory.BUSINESS.getCategory(), HttpStatus.FORBIDDEN.value(), "05","Do Not Honor","Account or User status is abnormal",false),
    FEATURE_NOT_ALLOWED_AT_THIS_TIME(ResponseCodeCategory.SYSTEM.getCategory(), HttpStatus.FORBIDDEN.value(), "06", "Feature Not Allowed At This Time.", "Cut off In Progress",false),
    CARD_BLOCKED(ResponseCodeCategory.BUSINESS.getCategory(), HttpStatus.FORBIDDEN.value(), "07", "Card Blocked", "The payment card is blocked",false),
    CARD_EXPIRED(ResponseCodeCategory.BUSINESS.getCategory(), HttpStatus.FORBIDDEN.value(), "08", "Card Expired", "The payment card is expired",false),
    DORMANT_ACCOUNT(ResponseCodeCategory.BUSINESS.getCategory(), HttpStatus.FORBIDDEN.value(), "09", "Dormant Account", "The account is dormant",false),
    NEED_TO_SET_TOKEN_LIMIT(ResponseCodeCategory.BUSINESS.getCategory(), HttpStatus.FORBIDDEN.value(), "10", "Need To Set Token Limit", "Need to set token limit",false),
    OTP_BLOCKED(ResponseCodeCategory.SYSTEM.getCategory(), HttpStatus.FORBIDDEN.value(), "11", "OTP Blocked", "OTP has been blocked",false),
    OTP_LIFETIME_EXPIRED(ResponseCodeCategory.SYSTEM.getCategory(), HttpStatus.FORBIDDEN.value(), "12", "OTP Lifetime Expired", "OTP has been expired",false),
    OTP_SET_TO_CARDHOLDER(ResponseCodeCategory.SYSTEM.getCategory(), HttpStatus.FORBIDDEN.value(), "13", "OTP Sent To Cardholer", "initiates request OTP to the issuer",false),
    INSUFFICIENT_FUNDS(ResponseCodeCategory.BUSINESS.getCategory(), HttpStatus.FORBIDDEN.value(), "14", "Insufficient Funds", "Insufficient Funds",false),
    TRANSACTION_NOT_PERMITTED(ResponseCodeCategory.BUSINESS.getCategory(), HttpStatus.FORBIDDEN.value(), "15", "Transaction Not Permitted", "Transaction Not Permitted",false),
    SUSPEND_TRANSACTION(ResponseCodeCategory.BUSINESS.getCategory(), HttpStatus.FORBIDDEN.value(), "16", "Suspend Transaction", "Suspend Transaction",false),
    TOKEN_LIMIT_EXCEEDED(ResponseCodeCategory.BUSINESS.getCategory(), HttpStatus.FORBIDDEN.value(), "17", "Token Limit Exceeded", "Purchase amount exceeds the token limit set prior",false),
    INACTIVE_CARD_ACCOUNT_CUSTOMER(ResponseCodeCategory.BUSINESS.getCategory(), HttpStatus.FORBIDDEN.value(), "18", "Inactive Card/Account/Customer", "Indicates inactive account",false),
    MERCHANT_BLACKLISTED(ResponseCodeCategory.BUSINESS.getCategory(), HttpStatus.FORBIDDEN.value(), "19", "Merchant Blacklisted", "Merchant is suspended from calling any APIs",false),
    MERCHANT_LIMIT_EXCEEDED(ResponseCodeCategory.BUSINESS.getCategory(), HttpStatus.FORBIDDEN.value(), "20","Merchant Limit Exceed", "Merchant aggregated purchase amount on that day exceeds the agreed limit",false),
    SET_LIMIT_NOT_ALLOWED(ResponseCodeCategory.BUSINESS.getCategory(), HttpStatus.FORBIDDEN.value(), "21","Set Limit Not Allowed", "Set limit not allowed on particular token",false),
    TOKEN_LIMIT_INVALID(ResponseCodeCategory.BUSINESS.getCategory(), HttpStatus.FORBIDDEN.value(), "22", "Token Limit Invalid", "The token limit desired by the merchant is not within the agreed range between the merchant and the Issuer",false),
    ACCOUNT_LIMIT_EXCEEDED(ResponseCodeCategory.BUSINESS.getCategory(), HttpStatus.FORBIDDEN.value(), "23", "Account Limit Exceed","Account aggregated purchase amount on that day exceeds the agreed limit",false),
    INVALID_TRANSACTION_STATUS(ResponseCodeCategory.BUSINESS.getCategory(), HttpStatus.NOT_FOUND.value(), "00", "Invalid Transaction Status", "Invalid Transaction Status",false),
    TRANSACTION_NOT_FOUND(ResponseCodeCategory.BUSINESS.getCategory(), HttpStatus.NOT_FOUND.value(), "01", "Transaction Not Found", "Transaction Not Found",false),
    INVALID_ROUTING(ResponseCodeCategory.SYSTEM.getCategory(), HttpStatus.NOT_FOUND.value(), "02", "Invalid Routing", "Invalid Routing",false),
    BANK_NOT_SUPPORTED_BY_SWITCH(ResponseCodeCategory.SYSTEM.getCategory(), HttpStatus.NOT_FOUND.value(), "03", "Bank Not Supported By Switch", "Bank Not Supported By Switch",false),
    TRANSACTION_CANCELLED(ResponseCodeCategory.BUSINESS.getCategory(), HttpStatus.NOT_FOUND.value(), "04", "Transaction Cancelled", "Transaction is cancelled by customer",false),
    MERCHANT_IS_NOT_REGISTERED_FOR_CARD_REGISTRATION_SERVICES(ResponseCodeCategory.BUSINESS.getCategory(), HttpStatus.NOT_FOUND.value(), "05","Merchant Is Not Registered For Card Registration Services", "Merchant is not registered for Card Registration services",false),
    NEED_TO_REQUEST_OTP(ResponseCodeCategory.SYSTEM.getCategory(), HttpStatus.NOT_FOUND.value(), "06", "Need To Request OTP","Need To Request OTP",false),
    JOURNEY_NOT_FOUND(ResponseCodeCategory.SYSTEM.getCategory(), HttpStatus.NOT_FOUND.value(), "07", "Journey Not Found", "The journeyID cannot be found in the system",false),
    INVALID_MERCHANT(ResponseCodeCategory.BUSINESS.getCategory(), HttpStatus.NOT_FOUND.value(), "08", "Invalid Merchant", "Merchant does not exist or status abnormal",false),
    NO_ISSUER(ResponseCodeCategory.BUSINESS.getCategory(), HttpStatus.NOT_FOUND.value(), "09", "No Issuer", "No Issuer",false),
    INVALID_API_TRANSITION(ResponseCodeCategory.SYSTEM.getCategory(), HttpStatus.NOT_FOUND.value(), "10", "Invalid API Transition", "Invalid API transition within a journey",false),
    INVALID_CARD_ACCOUNT_CUSTOMER_VIRTUAL_ACCOUNT(ResponseCodeCategory.BUSINESS.getCategory(), HttpStatus.NOT_FOUND.value(), "11", "Invalid Card/Account/Customer [info]/Virtual Account", "Card information may be invalid, or the card account may be blacklisted, or Virtual Account number maybe invalid.",false),
    INVALID_BILL_VIRTUAL_ACCOUNT(ResponseCodeCategory.BUSINESS.getCategory(), HttpStatus.NOT_FOUND.value(), "12", "Invalid Bill/Virtual Account", "The bill is blocked/ suspended/not found. Virtual account is suspend/not found",false),
    INVALID_AMOUNT(ResponseCodeCategory.BUSINESS.getCategory(), HttpStatus.NOT_FOUND.value(), "13","Invalid Amount","The amount doesn't match with what supposed to",false),
    PAID_BILL(ResponseCodeCategory.BUSINESS.getCategory(), HttpStatus.NOT_FOUND.value(), "14", "Paid Bill", "The bill has been paid",false),
    INVALID_OTP(ResponseCodeCategory.SYSTEM.getCategory(), HttpStatus.NOT_FOUND.value(), "15","Invalid OTP","OTP is incorrect",false),
    PARTNER_NOT_FOUND(ResponseCodeCategory.BUSINESS.getCategory(), HttpStatus.NOT_FOUND.value(), "16", "Partner Not Found", "Partner number can't be found",false),
    INVALID_TERMINAL(ResponseCodeCategory.BUSINESS.getCategory(), HttpStatus.NOT_FOUND.value(), "17", "Invalid Terminal", "Terminal does not exist in the system",false),
    INCONSISTENT_REQUEST(ResponseCodeCategory.BUSINESS.getCategory(), HttpStatus.NOT_FOUND.value(), "18", "Inconsistent Request", "Inconsistent request parameter found for the same partner reference number/transaction id\n" +
            "It can be considered as failed in transfer debit, but it should be considered as success in transfer credit.\n" +
            "Considered as success:\n" +
            "- Transfer credit = (i) Intrabank transfer; (ii) Interbank transfer; (iii) RTGS transfer; (iv) SKNBI transfer;\n" +
            "- Virtual account = (i) Payment VA; (ii) Payment to VA;\n" +
            "- Transfer debit = (i) Refund payment; (ii) Void;\n" +
            "Considered as failed:\n" +
            "- Transfer credit = (i) Transfer to OTC;\n" +
            "- Transfer debit = (i) Direct debit payment; (ii) QR CPM payment; (iii) Auth payment; (iv) Capture;",false),
    INVALID_BILL_VIRTUAL_ACCOUNT_EXPIRED(ResponseCodeCategory.BUSINESS.getCategory(), HttpStatus.NOT_FOUND.value(), "19", "Invalid Bill/Virtual Account", "The bill is expired.",false),
    REQUESTED_FUNCTION_IS_NOT_SUPPORTED(ResponseCodeCategory.SYSTEM.getCategory(), HttpStatus.METHOD_NOT_ALLOWED.value(), "00", "Requested Function Is Not Supported", "Requested Function Is Not Supported",false),
    REQUESTED_OPERATION_IS_NOT_ALLOWED(ResponseCodeCategory.BUSINESS.getCategory(), HttpStatus.METHOD_NOT_ALLOWED.value(), "01", "Requested Opearation Is Not Allowed", "Requested operation to cancel/refund transaction Is not allowed at this time.",false),
    CONFLICT(ResponseCodeCategory.SYSTEM.getCategory(), HttpStatus.CONFLICT.value(), "00", "Conflict", 	"Cannot use same X-EXTERNAL-ID in same day",false),
    DUPLICATE_PARTNER_REFERENCE_NO(ResponseCodeCategory.SYSTEM.getCategory(), HttpStatus.CONFLICT.value(), "01", "Duplicate partnerReferenceNo", "Transaction has previously been processed indicates the same partnerReferenceNo already success",false),
    TOO_MANY_REQUESTS(ResponseCodeCategory.SYSTEM.getCategory(), HttpStatus.TOO_MANY_REQUESTS.value(), "00", "Too Many Requests", "Maximum transaction limit exceeded",false),
    GENERAL_ERROR(ResponseCodeCategory.SYSTEM.getCategory(), HttpStatus.INTERNAL_SERVER_ERROR.value(), "00", "General Error", "General Error",false),
    INTERNAL_SERVER_ERROR(ResponseCodeCategory.SYSTEM.getCategory(), HttpStatus.INTERNAL_SERVER_ERROR.value(), "01", "Internal Server Error", "Unknown Internal Server Failure, Please retry the process again",false),
    EXTERNAL_SERVER_ERROR(ResponseCodeCategory.SYSTEM.getCategory(), HttpStatus.INTERNAL_SERVER_ERROR.value(), "02", "External Server Error","Backend system failure, etc",false),
    TIMEOUT(ResponseCodeCategory.SYSTEM.getCategory(), HttpStatus.GATEWAY_TIMEOUT.value(), "00", "Timeout", "timeout from the issuer", false);
    
    
    private final String category;
    private final Integer httpCode;
    private final String caseCode;
    private final String responseMessage;
    private final String description;
    private final boolean requiresFieldName;

    private static final int HTTP_STATUS_CODE_LENGTH = 3;
    private static final int CASE_CODE_LENGTH = 2;
    
    
    SnapBiResponseCode(String category, Integer httpCode, String caseCode, String responseMessage, String description, boolean requiresFieldName) {
        this.category = category;
        this.httpCode = httpCode;
        this.caseCode = caseCode;
        this.responseMessage = responseMessage;
        this.description = description;
        this.requiresFieldName = requiresFieldName;
    }
    
    public String getResponseCode(String serviceCode) {
        // responseCode = HTTP status code + service code + case code
        return this.httpCode + serviceCode + this.caseCode;
    }
    
    public String getResponseMessage(String fieldName) {
        String message = responseMessage;
        if(this.requiresFieldName) {
            message = message + " " + fieldName;
        }
        return message;
    }

    public static Optional<SnapBiResponseCode> findByHttpCodeAndCaseCode(String responseCode) {
        Integer httpCode = Integer.parseInt(responseCode.substring(BigDecimal.ZERO.intValue(), HTTP_STATUS_CODE_LENGTH));
        String caseCode = responseCode.substring(responseCode.length() - CASE_CODE_LENGTH);
        return Arrays.stream(SnapBiResponseCode.values())
                .filter(code -> code.getHttpCode().equals(httpCode) && code.getCaseCode().equals(caseCode))
                .findFirst();
    }
}
