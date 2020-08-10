package test.zwx.com.dialogleaktest;

public class MyMessage {
    final String message;

    Object obj;

    public MyMessage(String message, Object obj) {
        this.message = message;
        this.obj = obj;
    }
}