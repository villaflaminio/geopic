package com.flaminiovilla.geopic.exception;

public class ReferralPersonException extends RuntimeException{
    public enum referralPersonExceptionCode{
        REFERRAL_PERSON_ALREADY_EXIST,
        SECRETARY_NOT_EXIST,
        REFERRAL_PERSON_DELETE_ERROR,
        REFERRAL_PERSON_ID_NOT_EXIST,
        ADD_TO_REGION_FAILED,
        ACTION_NOT_PERMITTED

    }

    public ReferralPersonException(ReferralPersonException.referralPersonExceptionCode message) {
        super(message.toString());
    }
}
