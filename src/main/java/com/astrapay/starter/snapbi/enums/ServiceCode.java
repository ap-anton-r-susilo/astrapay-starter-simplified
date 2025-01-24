package com.astrapay.starter.snapbi.enums;

import lombok.Getter;

@Getter
public enum ServiceCode {
    CARD_REGISTRATION("v1.0/registration-card-bind","01"),
    CARD_REGISTRATION_SET_LIMIT("v1.0/registration/card-bind-limit","02"),
    CARD_REGISTRATION_INQUIRY("v1.0/registration-card-inquiry","03"),
    VERIFY_OTP_DIRECT_INTEGRATION("v1.0/otp-verification","04"),
    CARD_REGISTRATION_UNBINDING("v1.0/registration-card-unbind","05"),
    ACCOUNT_CREATION("v1.0/registration-account-creation","06"),
    ACCOUNT_BINDING("v1.0/registration-account-binding","07"),
    ACCOUNT_BINDING_INQUIRY("v1.0/registration-account-inquiry","08"),
    ACCOUNT_UNBINDING("v1.0/registration-account-unbinding","09"),
    GET_OAUTH_URL("v1.0/get-auth-code","10"),
    BALANCE_INQUIRY("v1.0/balance-inquiry","11"),
    TRANSACTION_HISTORY_LIST("v1.0/transaction-history-list","12"),
    TRANSACTION_HISTORY_DETAIL("v1.0/transaction-history-detail","13"),
    BANK_STATEMENT("v1.0/bank-statement","14"),
    INTERNAL_ACCOUNT_INQUIRY("v1.0/account-inquiry-internal","15"),
    EXTERNAL_ACCOUNT_INQUIRY("v1.0/account-inquiry-external","16"),
    TRIGGER_INTRABANK_TRANSFER("v1.0/transfer-intrabank","17"),
    TRIGGER_INTERBANK_TRANSFER("v1.0/transfer-interbank","18"),
    REQUEST_FOR_PAYMENT("v1.0/transfer-request-for-payment","19"),
    TRIGGER_INTERBANK_BULK_TRANSFER("v1.0/transfer-interbank-bulk","20"),
    TRIGGER_INTERBANK_BULK_TRANSFER_NOTIFY("v1.0/transfer-interbank-bulk/notify","21"),
    TRANSFER_RTGS("v1.0/transfer-rtgs","22"),
    TRANSFER_SKNBI("v1.0/transfer-skn","23"),
    VIRTUAL_ACCOUNT_INQUIRY("v1.0/transfer-va/inquiry","24"),
    VIRTUAL_ACCOUNT_PAYMENT("v1.0/transfer-va/payment","25"),
    VIRTUAL_ACCOUNT_INQUIRY_STATUS("v1.0/transfer-va/status","26"),
    VIRTUAL_ACCOUNT_CREATE_VA("v1.0/transfer-va/create-va","27"),
    VIRTUAL_ACCOUNT_UPDATE_VA("v1.0/transfer-va/update-va","28"),
    VIRTUAL_ACCOUNT_UPDATE_STATUS_VA("v1.0/transfer-va/update-status","29"),
    VIRTUAL_ACCOUNT_INQUIRY_VA("v1.0/transfer-va/inquiry-va","30"),
    VIRTUAL_ACCOUNT_DELETE_VA("v1.0/transfer-va/delete-va","31"),
    VIRTUAL_ACCOUNT_GET_REPORT("v1.0/transfer-va/inquiry-intrabank","32"),
    VIRTUAL_ACCOUNT_PAYMENT_TO_VA_FROM_INTRA_BANK("v1.0/transfer-va/payment-intrabank","33"),
    NOTIFICATION_FOR_PAYMENT_TO_VA_FROM_INTRA_BANK("v1.0/transfer-va/notify-payment-intrabank","34"),
    ACCOUNT_INQUIRY_CUSTOMER_TOP_UP("v1.0/transfer-va/report","35"),
    CUSTOMER_TOP_UP("v1.0/transfer/status","35"),
    CUSTOMER_TOP_UP_INQUIRY_STATUS("v1.0/emoney/account-inquiry","37"),
    SUBMIT_BULK_CASH_IN("v1.0/emoney/topup","38"),
    NOTIFY_BULK_CASH_IN("v1.0/emoney/topup-status","39"),
    TRANSFER_TO_BANK_ACCOUNT_INQUIRY("v1.0/emoney/bulk-cashin-payment","40"),
    TRANSFER_TO_BANK_PAYMENT_TRANSACTION("v1.0/emoney/bulk-cashin-notify","41"),
    TRANSFER_TO_OTC_CREATE_PAYMENT("v1.0/emoney/bank-account-inquiry","42"),
    TRANSFER_TO_OTC_TRANSFER_STATUS("v1.0/emoney/transfer-bank","43"),
    TRANSFER_TO_OTC_CANCEL_PAYMENT("v1.0/emoney/otc-cashout","44"),
    GENERATE_QR_MPM("v1.0/emoney/otc-status","45"),
    DECODE_QR_MPM("v1.0/emoney/otc-cancel","46"),
    PAYMENT_REDIRECT("v1.0/qr/qr-mpm-generate","47"),
    PAYMENT_HOST_TO_HOST("v1.0/qr/qr-mpm-decode","48"),
    QUERY_PAYMENT("v1.0/qr/apply-ott","49"),
    PAYMENT_NOTIFY("v1.0/qr/qr-mpm-payment","50"),
    CANCEL_PAYMENT("v1.0/qr/qr-mpm-query","51"),
    REFUND_PAYMENT("v1.0/qr/qr-mpm-notify","52"),
    TRANSACTION_STATUS_INQUIRY("v1.0/qr/qr-mpm-status","53"),
    DIRECT_DEBIT_PAYMENT_NOTIFY("v1.0/debit/payment-host-to-host","54"),
    DIRECT_DEBIT_PAYMENT_CANCEL("v1.0/debit/status","55"),
    DIRECT_DEBIT_PAYMENT_REFUND("v1.0/debit/notify","56"),
    GENERATE_QR_CPM("v1.0/debit/cancel","57"),
    CPM_PAYMENT("v1.0/debit/refund","58"),
    QR_CPM_GENERATE("v1.0/qr/qr-cpm-generate","59"),
    QR_CPM_PAYMENT("v1.0/qr/qr-cpm-payment","60"),
    QR_CPM_QUERY("v1.0/qr/qr-cpm-query","61"),
    QR_CPM_CANCEL("v1.0/qr/qr-cpm-cancel","62"),
    AUTH_PAYMENT("v1.0/qr/qr-cpm-notify","63"),
    PAYMENT_QUERY("v1.0/auth/query","64"),
    CAPTURE("v1.0/auth/capture","65"),
    CAPTURE_QUERY("v1.0/auth/capture-query","66"),
    AUTH_VOID("v1.0/auth/void","67"),
    VOID_QUERY("v1.0/auth/void-query","68"),
    REFUND("v1.0/auth/refund","69"),
    REGISTRATION_E_MANDATE("v1.0/debit/fast-emandate","70"),
    REGISTRATION_PAYMENT("v1.0/debit/fast-payment","71"),
    NOTIFY("v1.0/debit/fast-notify","72"),
    TRIGGER_SKNBI_NOTIFY("v1.0/transfer-skn/notify","75"),
    TRIGGER_RTGS_NOTIFY("v1.0/transfer-rtgs/notify","76"),
    QR_MPM_CANCEL("v1.0/qr/qr-mpm-cancel","77"),
    QR_MPM_REFUND("v1.0/qr/qr-mpm-refund","78"),
    QR_CPM_NOTIFY("v1.0/qr/qr-cpm-notify","79"),
    DIRECT_DEBIT_PAYMENT_STATUS("v1.0/qr/qr-mpm-status","78"),
    QR_CPM_REFUND("v1.0/qr/qr-cpm-refund","80"),
    OTP("v1.0/otp","81"),
    URL_OUTSIDE_SNAP_BI("73");

    public final String key;
    public final String label;

    ServiceCode(String key, String label) {
        this.key = key;
        this.label = label;
    }

    ServiceCode(String label){
        this.key = null;
        this.label = label;
    }
}