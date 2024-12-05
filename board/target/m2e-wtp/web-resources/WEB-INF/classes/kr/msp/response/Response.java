package kr.msp.response;

public class Response<H extends ResponseHeader, T> {
    private H head;
    private T body;

    public Response(H header, T body) {
        this.head = header;
        this.body = body;
    }

    public H getHeader() {
        return head;
    }

    public T getBody() {
        return body;
    }
}