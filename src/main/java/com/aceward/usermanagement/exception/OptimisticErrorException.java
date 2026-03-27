package com.aceward.usermanagement.exception;

/**
 * 楽観的ロックエラー例外。
 * 
 * @author h-suyama
 * @version 1.0.0
 */
public class OptimisticErrorException extends Exception {

    /**
     * コンストラクタ。
     */
    public OptimisticErrorException() {
        super();
    }

    /**
     * コンストラクタ。
     * 
     * @param message メッセージ
     */
    public OptimisticErrorException(String message) {
        super(message);
    }

    /**
     * コンストラクタ。
     * 
     * @param message メッセージ
     * @param e Throwable
     */
    public OptimisticErrorException(String message, Throwable e) {
        super(message, e);
    }
}
